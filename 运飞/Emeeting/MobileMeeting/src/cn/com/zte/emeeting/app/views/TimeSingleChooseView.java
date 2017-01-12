package cn.com.zte.emeeting.app.views;

import java.text.ParseException;
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
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

/**
 * 日期选择布局
 * 
 * @author LangK
 * 
 */
public class TimeSingleChooseView extends LinearLayout {

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
	 * 总有4个边距
	 */
	/**
	 * 每个边距
	 */
	private final static int itemMarge = 20;
	/**
	 * 总共有3个控件
	 */
	private final static int itemNumber = 3;
	/**
	 * 周集合
	 */
	private final String[] weeks = new String[] { "周日", "周一", "周二", "周三", "周四",
			"周五", "周六" };
	/**
	 * 小时集合
	 */
	private final String[] hours = new String[]{"08","09","10","11","12","13","14","15","16","17","18","19","20"};

	/**
	 * 分钟集合
	 */
	private final String[] minutes = new String[]{"00","05","15","20","25","30","35","40","45","50","55"};

	public TimeSingleChooseView(Context context) {
		super(context);
		this.mContext = context;
		initView();
	}

	public TimeSingleChooseView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		initView();
	}

	public TimeSingleChooseView(Context context, AttributeSet attrs, int defStyle) {
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
		
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < 15; i++) {
			calendar.setTime(currentDate);
			calendar.add(Calendar.DAY_OF_MONTH, i);
			StringBuilder sb = new StringBuilder();
			sb.append(dateFormat.format(calendar.getTime())).append(" ").append(weeks[calendar.get(Calendar.DAY_OF_WEEK)-1]);
			dayList.add(new TextInfo(i, sb.toString(), true, 16));
		}
		
		for (int i = 0; i < hours.length; i++) {
			hourList.add(new TextInfo(i, hours[i], true, 16));
		}
		for (int i = 0; i < minutes.length; i++) {
			minuteList.add(new TextInfo(i, minutes[i], true, 16));
		}
	}
	
	/**
	 * 设置当前日期和时间
	 */
	@SuppressLint("SimpleDateFormat")
	public void setCurrentDateAndTime(String dateString){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		try {
			date = format.parse(dateString);
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initView() {
		initData();
		setGravity(Gravity.CENTER_VERTICAL);
		/**
		 * View高度
		 */
		int viewHeight = 300;
		
		setOrientation(LinearLayout.HORIZONTAL);
		DisplayMetrics dm = new DisplayMetrics();
		((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		int itemWidth = (screenWidth) *37 / 100;
	
		dayWheelView = new WheelView(mContext);
		WheelTextAdapter adapter = new WheelTextAdapter(mContext);
		adapter.setData(dayList);
		adapter.setItemSize(itemWidth, 100);
		dayWheelView.setAdapter(adapter);
		LinearLayout.LayoutParams dateLayoutParams = new LinearLayout.LayoutParams(itemWidth, viewHeight);
		dateLayoutParams.leftMargin = screenWidth*13/100;
		addView(dayWheelView,dateLayoutParams);
		
		/**
		 * 后面2个item的宽度
		 */
		int timeWidth = screenWidth*22/100/2;
		
		RelativeLayout startLayout = new RelativeLayout(mContext);
		LinearLayout startLinearLayout = new LinearLayout(mContext);
		TextView startTextView = new TextView(mContext);
		startTextView.setPadding(0, 0, 0, 15);
		startTextView.setText(":");
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
		
//		TextView endTextView = new TextView(mContext);
//		endTextView.setText(":");
//		endTextView.setTextSize(16);
//		endTextView.setTextColor(mContext.getResources().getColor(R.color.SFContentTextColor));
//		RelativeLayout.LayoutParams endTextParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//		endTextParams.addRule(RelativeLayout.CENTER_VERTICAL);
//		addView(endTextView,endTextParams);
		
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
		RelativeLayout.LayoutParams startTextParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		startTextParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		startLayout.addView(startTextView,startTextParams);
		
		LinearLayout.LayoutParams startLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, viewHeight);
		startLayoutParams.leftMargin = screenWidth*10/100;
		startLayoutParams.rightMargin = screenWidth*18/100;
		addView(startLayout,startLayoutParams);
	}

	
	/**
	 * 获取时间
	 * @return 返回格式  日期 开始时间 结束时间
	 * 比如2015-09-06 14:30
	 * -1 未知错误
	 * 0 时间选择错误
	 */
	public String getTime() {
		// TODO Auto-generated method stub
		try {
			StringBuilder sBuilder = new StringBuilder();
			String date = dayList.get(dayWheelView.getSelectedItemPosition()).mText.split(" ")[0];
			String startTime = hourList.get(startHourWheelView.getSelectedItemPosition()).mText+":"+minuteList.get(startMinuteWheelView.getSelectedItemPosition()).mText;
			sBuilder.append(date);
			sBuilder.append(" ").append(startTime);
			return sBuilder.toString();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "-1";
		}
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
				textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
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

}
