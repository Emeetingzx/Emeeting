package cn.com.zte.emeeting.app.views;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.com.zte.emeeting.app.views.wheel.TextInfo;
import cn.com.zte.emeeting.app.views.wheel.TosGallery;
import cn.com.zte.emeeting.app.views.wheel.TosGallery.OnEndFlingListener;
import cn.com.zte.emeeting.app.views.wheel.WheelView;
import cn.com.zte.mobileemeeting.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 会议预订日期选择布局
 * @author LangK
 * 
 */
public class TimeDoubleIntChooseView extends LinearLayout {

	private Context mContext;
	/**
	 * 天
	 */
	private WheelView dayWheelView;
	/**
	 * 开始时间的小时
	 */
	private WheelView startHourWheelView;
	/**
	 * 开始时间的分钟
	 */
	private WheelView startMinuteWheelView;
	/**
	 * 结束时间的小时
	 */
	private WheelView endHourWheelView;
	/**
	 * 结束时间的分钟
	 */
	private WheelView endMinuteWheelView;
	
	/**
	 * 日期集合
	 */
	List<TextInfo> dayList;
	/**
	 * 时间小时集合
	 */
	List<TextInfo> hourList;
	/**
	 * 时间分钟集合
	 */
	List<TextInfo> minuteList;
	/**
	 * 时间分钟集合
	 */
	List<TextInfo> maxMinuteList;
	/**
	 * 总有4个边距
	 */
	/**
	 * 每个边距
	 */
	private static int itemMarge = 20;
	/**
	 * 周集合
	 */
	private final String[] weeks = new String[] { "周日", "周一", "周二", "周三", "周四",
			"周五", "周六" };
	/**
	 * 小时集合
	 */
	private final String[] hours = new String[]{"08","09","10","11","12","13","14","15","16","17","18","19","20","21"};

	/**
	 * 分钟集合
	 */
	private final String[] minutes = new String[]{"00","30"};
	/**
	 * 分钟集合
	 */
	private final String[] maxMinutes = new String[]{"00"};
	/**
	 * 最多可预订天数
	 */
	private static int days = 3;
	/**
	 * 最多可预订天数
	 */
	public static int getDays() {
		return TimeDoubleIntChooseView.days;
	}
	/**
	 * 最多可预订天数
	 */
	public static void setDays(int days) {
		TimeDoubleIntChooseView.days = days;
	}

	public TimeDoubleIntChooseView(Context context) {
		super(context);
		this.mContext = context;
		initView();
	}

	public TimeDoubleIntChooseView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		initView();
	}

	public TimeDoubleIntChooseView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		initView();
	}

	@SuppressLint("SimpleDateFormat")
	private void initData() {
		// TODO Auto-generated method stub
		dayList = new ArrayList<TextInfo>();
		hourList = new ArrayList<TextInfo>();
		minuteList = new ArrayList<TextInfo>();
		maxMinuteList = new ArrayList<TextInfo>();
		
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		for (int i = 0; i < days; i++) {
			calendar.setTime(currentDate);
			calendar.add(Calendar.DAY_OF_MONTH, i);
			StringBuilder sb = new StringBuilder();
			sb.append(dateFormat.format(calendar.getTime()));
			sb.append(" " + weeks[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
			dayList.add(new TextInfo(i, sb.toString(), true, 14));
//			if ((calendar.get(Calendar.DAY_OF_WEEK) - 1)==0||(calendar.get(Calendar.DAY_OF_WEEK) - 1==6)) {
//				days++;
//			}
		}
		
		for (int i = 0; i < hours.length; i++) {
			hourList.add(new TextInfo(i, hours[i], true, 14));
		}
		for (int i = 0; i < minutes.length; i++) {
			minuteList.add(new TextInfo(i, minutes[i], true, 14));
		}
		for (int i = 0; i < maxMinutes.length; i++) {
			maxMinuteList.add(new TextInfo(i, maxMinutes[i], true, 14));
		}
	}

	private void initView() {
		initData();
		/**
		 * View高度
		 */
		int viewHeight = 300;
		
		setOrientation(LinearLayout.HORIZONTAL);
		DisplayMetrics dm = new DisplayMetrics();
		((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		int itemWidth = (screenWidth)*36 / 100;
		dayWheelView = new WheelView(mContext);
		WheelTextAdapter adapter = new WheelTextAdapter(mContext);
		adapter.setData(dayList);
		adapter.setItemSize(itemWidth, 100);
		dayWheelView.setAdapter(adapter);
		LinearLayout.LayoutParams dayParams=new LinearLayout.LayoutParams(itemWidth,viewHeight);
		dayParams.leftMargin = screenWidth*5/100;
		addView(dayWheelView,dayParams);
		/**
		 * 后面4个item的宽度
		 */
		int timeWidth = (screenWidth) / 10;
		itemMarge = screenWidth*5/100;
		
		RelativeLayout startLayout = new RelativeLayout(mContext);
		LinearLayout startLinearLayout = new LinearLayout(mContext);
		TextView startTextView = new TextView(mContext);
		startTextView.setPadding(0, 0, 0, 3);
		startTextView.setText(":");
		startTextView.setGravity(Gravity.CENTER);
		startTextView.setTextSize(16);
		startTextView.setTextColor(mContext.getResources().getColor(R.color.SFContentTextColor));
		
		/**
		 * 开始时间的小时
		 */
		startHourWheelView = new WheelView(mContext);
		WheelTextAdapter startHouradapter = new WheelTextAdapter(mContext);
		startHouradapter.setData(hourList);
		startHouradapter.setItemSize(timeWidth, 100);
		startHourWheelView.setAdapter(startHouradapter);
		LinearLayout.LayoutParams startHourParams = new LinearLayout.LayoutParams(timeWidth, viewHeight);
		startLinearLayout.addView(startHourWheelView,startHourParams);
		
		/**
		 * 开始时间的分钟
		 */
		startMinuteWheelView = new WheelView(mContext);
		WheelTextAdapter startMinutedapter = new WheelTextAdapter(mContext);
		startMinutedapter.setData(minuteList);
		startMinutedapter.setItemSize(timeWidth, 100);
		startMinuteWheelView.setAdapter(startMinutedapter);
		LinearLayout.LayoutParams startMinuteParams = new LinearLayout.LayoutParams(timeWidth, viewHeight);
		startLinearLayout.addView(startMinuteWheelView,startMinuteParams);
		
		startLayout.addView(startLinearLayout);
		RelativeLayout.LayoutParams startTextParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
		startTextParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		startLayout.addView(startTextView,startTextParams);
		
		LinearLayout.LayoutParams startLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, viewHeight);
		startLayoutParams.leftMargin = itemMarge;
		addView(startLayout,startLayoutParams);
		
		
		
		/**
		 * 开始和结束时间中间的-
		 */
		TextView textView = new TextView(mContext);
		textView.setText("－");
		textView.setTextColor(mContext.getResources().getColor(R.color.SFContentTextColor));
		textView.setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(itemMarge, viewHeight);
		textParams.gravity = Gravity.CENTER;
		addView(textView,textParams);
		
		
		RelativeLayout endLayout = new RelativeLayout(mContext);
		LinearLayout endLinearLayout = new LinearLayout(mContext);
		TextView endTextView = new TextView(mContext);
		endTextView.setPadding(0, 0, 0, 3);
		endTextView.setText(":");
		endTextView.setTextSize(16);
		endTextView.setTextColor(mContext.getResources().getColor(R.color.SFContentTextColor));
		/**
		 * 结束时间的小时
		 */
		endHourWheelView = new WheelView(mContext);
		WheelTextAdapter endHouradapter = new WheelTextAdapter(mContext);
		endHouradapter.setData(hourList);
		endHouradapter.setItemSize(timeWidth, 100);
		endHourWheelView.setAdapter(endHouradapter);
		LinearLayout.LayoutParams endHourParams = new LinearLayout.LayoutParams(timeWidth, viewHeight);
		endLinearLayout.addView(endHourWheelView,endHourParams);
		
		/**
		 * 结束时间的分钟
		 */
		endMinuteWheelView = new WheelView(mContext);
		WheelTextAdapter endMinutedapter = new WheelTextAdapter(mContext);
		endMinutedapter.setData(minuteList);
		endMinutedapter.setItemSize(timeWidth, 100);
		endMinuteWheelView.setAdapter(endMinutedapter);
		LinearLayout.LayoutParams endMinuteParams = new LinearLayout.LayoutParams(timeWidth, viewHeight);
		endLinearLayout.addView(endMinuteWheelView,endMinuteParams);
		
		endLayout.addView(endLinearLayout);
		RelativeLayout.LayoutParams endTextParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		endTextParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		endLayout.addView(endTextView,endTextParams);
		
		LinearLayout.LayoutParams endLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, viewHeight);
		endLayoutParams.rightMargin = screenWidth*8/100;
		addView(endLayout,endLayoutParams);
		
		initEvent();
	}
	
	/**
	 * 初始化事件
	 */
	private void initEvent() {
		/* 防止开始小时时间大于结束小时时间*/
		startHourWheelView.setOnEndFlingListener(new OnEndFlingListener() {
			
			@Override
			public void onEndFling(TosGallery v) {
				// TODO Auto-generated method stub
				int startposition = v.getSelectedItemPosition();
				int endposition = endHourWheelView.getSelectedItemPosition();
				if (startposition>endposition) {
					endHourWheelView.setSelection(startposition);
				}
				
				if (startposition==hours.length-1) {
					((WheelTextAdapter)startMinuteWheelView.getAdapter()).setData(maxMinuteList);
					((WheelTextAdapter)endMinuteWheelView.getAdapter()).setData(maxMinuteList);
				}else {
					((WheelTextAdapter)startMinuteWheelView.getAdapter()).setData(minuteList);
				}
				((WheelTextAdapter)startMinuteWheelView.getAdapter()).notifyDataSetChanged();
				((WheelTextAdapter)endMinuteWheelView.getAdapter()).notifyDataSetChanged();
			}
		});
		endHourWheelView.setOnEndFlingListener(new OnEndFlingListener() {
			
			@Override
			public void onEndFling(TosGallery v) {
				// TODO Auto-generated method stub
				int startposition = v.getSelectedItemPosition();
				int endposition = startHourWheelView.getSelectedItemPosition();
				if (startposition<endposition) {
					startHourWheelView.setSelection(startposition);
				}
				
				if (startposition==hours.length-1) {
					((WheelTextAdapter)endMinuteWheelView.getAdapter()).setData(maxMinuteList);
				}else {
					((WheelTextAdapter)endMinuteWheelView.getAdapter()).setData(minuteList);
				}
				((WheelTextAdapter)endMinuteWheelView.getAdapter()).notifyDataSetChanged();
				
			}
		});
		/* 防止开始分钟时间大于结束分钟时间*/
		startMinuteWheelView.setOnEndFlingListener(new OnEndFlingListener() {
			
			@Override
			public void onEndFling(TosGallery v) {
				int startposition = startHourWheelView.getSelectedItemPosition();
				if (startposition==hours.length-1) {
					if (v.getSelectedItemPosition()==1) {
						v.setSelection(0);
					}
				}
			}
		});
		endMinuteWheelView.setOnEndFlingListener(new OnEndFlingListener() {
			
			@Override
			public void onEndFling(TosGallery v) {
				int endposition = endHourWheelView.getSelectedItemPosition();
				if (endposition==hours.length-1) {
					if (v.getSelectedItemPosition()==1) {
						v.setSelection(0);
					}
				}
			}
		});
	}
	
	/**
	 * 设置当前日期和时间
	 *@param dateString 日期格式必须为yyyy-MM-dd HH:mm HH:mm
	 */
	@SuppressLint("SimpleDateFormat")
	public void setCurrentDateAndTime(String dateString){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		try {
			String[] dates =dateString.split(" ");
			String endTime = dates[2];
			String startTime = dateString.replace(" "+endTime, "");
			
			date = format.parse(startTime);
			calendar.setTime(date);
			String dayString = dateFormat.format(date);
			for (int i = 0; i < dayList.size(); i++) {
				if (dayList.get(i).mText.contains(dayString)) {
					dayWheelView.setSelection(i, true);
				}
			}
			int hourString = calendar.get(Calendar.HOUR_OF_DAY);
			for (int i = 0; i < hourList.size(); i++) {
				if (hourString==Integer.parseInt(hourList.get(i).mText)) {
					startHourWheelView.setSelection(i, true);
				}
			}
			int minuteString = calendar.get(Calendar.MINUTE);
			for (int i = 0; i < minuteList.size(); i++) {
				if (minuteString==Integer.parseInt(minuteList.get(i).mText)) {
					startMinuteWheelView.setSelection(i, true);
				}
			}
			
			String endHour = endTime.split(":")[0];
			for (int i = 0; i < hourList.size(); i++) {
				if (endHour.equals(hourList.get(i).mText)) {
					endHourWheelView.setSelection(i, true);
				}
			}
			String endMinute = endTime.split(":")[1];
			for (int i = 0; i < minuteList.size(); i++) {
				if (endMinute.equals(minuteList.get(i).mText)) {
					endMinuteWheelView.setSelection(i, true);
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取时间
	 * @return 返回格式  日期 开始时间 结束时间
	 * 比如2015-09-06 14:30 16:30
	 * -1 未知错误
	 * 0 时间选择错误
	 */
	public String getTime() {
		// TODO Auto-generated method stub
		StringBuilder sBuilder = new StringBuilder();
		String date = dayList.get(dayWheelView.getSelectedItemPosition()).mText;
		String startTime = hourList.get(startHourWheelView.getSelectedItemPosition()).mText+":"+minuteList.get(startMinuteWheelView.getSelectedItemPosition()).mText;
		String endTime = hourList.get(endHourWheelView.getSelectedItemPosition()).mText+":"+minuteList.get(endMinuteWheelView.getSelectedItemPosition()).mText;
		try {
			String[] startDates = startTime.split(":");
			String[] endDates = endTime.split(":");
			int startHour = Integer.parseInt(startDates[0]);
			int startMin = Integer.parseInt(startDates[1]);
			int endHour = Integer.parseInt(endDates[0]);
			int endMin = Integer.parseInt(endDates[1]);
			if (endHour<startHour) {
				return "0";
			}
			if (endHour==startHour) {
				if (endMin<=startMin) {
					return "0";
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "-1";
		}
		sBuilder.append(date.split(" ")[0]);
		sBuilder.append(" ").append(startTime).append(" ").append(endTime);
		return sBuilder.toString();
	}

	public static float pixelToDp(Context context, float val) {
		float density = context.getResources().getDisplayMetrics().density;
		return val * density;
	}

	protected class WheelTextAdapter extends BaseAdapter {
		int mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
		int mHeight = 50;
		Context mContext = null;
		List<TextInfo> mData = null;

		public WheelTextAdapter(Context context) {
			mContext = context;
			mHeight = (int) pixelToDp(context, mHeight);
		}

		public void setData(List<TextInfo> dayList) {
			mData = dayList;
			this.notifyDataSetChanged();
		}

		public void setItemSize(int width, int height) {
			mWidth = width;
			mHeight = height;
		}

		@Override
		public int getCount() {
			return (null != mData) ? mData.size() : 0;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView textView = null;

			if (null == convertView) {
				convertView = new TextView(mContext);
				convertView.setLayoutParams(new TosGallery.LayoutParams(mWidth,
						mHeight));
				textView = (TextView) convertView;
				textView.setGravity(Gravity.CENTER);
				textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
				textView.setTextColor(Color.BLACK);
			}

			if (null == textView) {
				textView = (TextView) convertView;
			}

			TextInfo info = mData.get(position);
			textView.setText(info.mText);
			textView.setTextSize(info.mTextSize);
			return convertView;
		}
	}

	/**
	 * @see android.view.View#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
	}
}
