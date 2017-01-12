package cn.com.zte.emeeting.app.views.calendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.com.zte.mobileemeeting.R;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 日历gridview中的每一个item显示的textview
 *
 * @author Vincent Lee
 *
 */
public class CalendarAdapter extends BaseAdapter {
	private final static String TAG = CalendarAdapter.class.getCanonicalName();
	private boolean isLeapyear = false; // 是否为闰年
	private int daysOfMonth = 0; // 某月的天数
	private int dayOfWeek = 0; // 具体某一天是星期几
	private int lastDaysOfMonth = 0; // 上一个月的总天数
	private Context context;
	private int dayNumberLength = 42;
	private List<DayNumber> dayNumbers = new ArrayList<CalendarAdapter.DayNumber>(
			dayNumberLength);
	private SpecialCalendar specialCalendar = null;

	private int currentYear;
	private int currentMonth;

	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
	private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private List<String> flagList = new ArrayList<String>(); // 用于标记需要标记的日期


	private int showYear; // 用于在头部显示的年份
	private int showMonth; // 用于在头部显示的月份
	private String animalsYear;
	private int leapMonth; // 闰哪一个月
	private String cyclical = ""; // 天干地支
	// 系统当前时间
	private String systemDate;
	private int systemYear;
	private int systemMonth;
	private int systemDay;

	
	public CalendarAdapter() {
		Date date = new Date();
		systemDate = sdf.format(date); // 当期日期
		Calendar calendar = Calendar.getInstance();
		systemYear = calendar.get(Calendar.YEAR);
		systemMonth = calendar.get(Calendar.MONTH)+1;
		systemDay = calendar.get(Calendar.DAY_OF_MONTH);
		Log.d(TAG, "当前日期：" + systemDate + " | " + systemYear + "-"
				+ systemMonth + "-" + systemDay);
	}

	public CalendarAdapter(Context context,int jumpMonth, int jumpYear, int year_c,
			int month_c, int day_c,List<String> flagList) {
		this();
		this.context = context;
		specialCalendar = new SpecialCalendar();
		this.flagList = flagList;
		
		int stepYear = year_c + jumpYear;
		int stepMonth = month_c + jumpMonth;
		if (stepMonth > 0) {
			// 往下一个月滑动
			if (stepMonth % 12 == 0) {
				stepYear = year_c + stepMonth / 12 - 1;
				stepMonth = 12;
			} else {
				stepYear = year_c + stepMonth / 12;
				stepMonth = stepMonth % 12;
			}
		} else {
			// 往上一个月滑动
			stepYear = year_c - 1 + stepMonth / 12;
			stepMonth = stepMonth % 12 + 12;
			if (stepMonth % 12 == 0) {
				
			}
		}
		currentYear = stepYear; // 得到当前的年份
		currentMonth = stepMonth; // 得到本月
		getCalendar(currentYear, currentMonth);
	}
	
	public CalendarAdapter(Context context,int jumpMonth, int jumpYear, int year_c,
			int month_c, int day_c) {
		this();
		this.context = context;
		specialCalendar = new SpecialCalendar();
		
		int stepYear = year_c + jumpYear;
		int stepMonth = month_c + jumpMonth;
		if (stepMonth > 0) {
			// 往下一个月滑动
			if (stepMonth % 12 == 0) {
				stepYear = year_c + stepMonth / 12 - 1;
				stepMonth = 12;
			} else {
				stepYear = year_c + stepMonth / 12;
				stepMonth = stepMonth % 12;
			}
		} else {
			// 往上一个月滑动
			stepYear = year_c - 1 + stepMonth / 12;
			stepMonth = stepMonth % 12 + 12;
			if (stepMonth % 12 == 0) {

			}
		}

		currentYear = stepYear; // 得到当前的年份
		currentMonth = stepMonth; // 得到本月
		getCalendar(currentYear, currentMonth);
	}

	public CalendarAdapter(Context context, Resources resources, int year,
			int month, int day) {
		this();
		this.context = context;
		specialCalendar = new SpecialCalendar();
		currentYear = year;// 得到跳转到的年份
		currentMonth = month; // 得到跳转到的月份
		getCalendar(currentYear, currentMonth);
	}

	// @Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dayNumbers.size();
	}

	// @Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (dayNumbers!=null&&dayNumbers.size()>position) {
			return dayNumbers.get(position);
		}
		return null;
	}

	// @Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	
	public void refreshView(){
		if (flagList==null||flagList.size()==0||dayNumbers==null||dayNumbers.size()==0) {
			return;
		}
		
		notifyDataSetChanged();
	}
	
	/**
	 * 记录每个view临时年月日的变量
	 */
	private int tmpyeah;
	private int tmpmonth;
	private int tmpday;
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CalendarItemViewHolder viewHolder = new CalendarItemViewHolder();

		convertView = View.inflate(context, R.layout.calendar_item, null);
//		listViews.add(convertView);
		// 阳历日期文本控件
		TextView dateTextView = (TextView) convertView
				.findViewById(R.id.alert_date_text_id);
		
		dateTextView.setPressed(false);

		DayNumber dayNumber = dayNumbers.get(position);
		String dateString = dayNumber.day + ""; // dayNumber[position].split("\\.")[0];
		setDateTextColor(convertView,dateTextView,  position,
				daysOfMonth, dayOfWeek,dateString);

		tmpyeah = dayNumber.getYear();
		tmpmonth = dayNumber.getMonth();
		tmpday = dayNumber.getDay();
		for (int i = 0; i < flagList.size(); i++) {
			String dateFlag = flagList.get(i);
			try {
				if (Integer.parseInt(dateFlag.split("-")[0])==tmpyeah&&
						Integer.parseInt(dateFlag.split(" ")[0].split("-")[1])==tmpmonth&&
								Integer.parseInt(dateFlag.split(" ")[0].split("-")[2])==tmpday) {
					setCheckedBackground(convertView);
				}
			} catch (Exception e) {
				Log.d(TAG, "解析错误 ："+dateFlag);
				e.printStackTrace();
			}
		}
		
		if (dayNumber.getYear()==systemYear&&dayNumber.getMonth()==systemMonth&&systemDay == dayNumber.getDay()) {
			setCurrentBackground(convertView);
		}
		
		convertView.setTag(viewHolder);
		final View dateView = convertView;
		ViewTreeObserver viewTreeObserver = dateView.getViewTreeObserver();
		viewTreeObserver
				.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
					public boolean onPreDraw() {
						dateView.getViewTreeObserver().removeOnPreDrawListener(
								this);
						int height = dateView.getMeasuredHeight();
						int width = dateView.getMeasuredWidth();
						ViewGroup.LayoutParams layoutParams = dateView
								.getLayoutParams();
						layoutParams.width = height;
						dateView.setLayoutParams(layoutParams);
						return true;
					}
				});
		return dateView;
	}

	/**
	 * 设置字体颜色
	 */
	public void setDateTextColor(View view,TextView dateTextView,
			 int position, int daysOfMonth,
			int dayOfWeek,String dateString) {
		if (position < daysOfMonth + dayOfWeek && position >= dayOfWeek) {
			dateTextView.setText(dateString);
			dateTextView.setTextColor(R.drawable.selector_date_text_color);
			// 当前月信息显示
//			dateTextView.setTextColor(Color.parseColor("#666666"));// 当月字体设黑
//			if (position % 7 == 0 || position % 7 == 6) {
//				// 当前月信息显示
//				dateTextView.setTextColor(Color.parseColor("#b1b1b1"));// 当月字体设黑
//			}
		}else {
			view.setVisibility(View.INVISIBLE);
		}
	}

	// 得到某年的某月的天数且这月的第一天是星期几
	public void getCalendar(int year, int month) {
		isLeapyear = specialCalendar.isLeapYear(year); // 是否为闰年
		daysOfMonth = specialCalendar.getDaysOfMonth(isLeapyear, month); // 某月的总天数
		dayOfWeek = specialCalendar.getWeekdayOfMonth(year, month); // 某月第一天为星期几
		lastDaysOfMonth = specialCalendar.getDaysOfMonth(isLeapyear, month - 1); // 上一个月的总天数
		getDayNumber(year, month);
	}

	/**
	 * 设置选中时的背景色
	 *
	 * @param view
	 * @return view
	 */
	public View setCheckedBackground(View view) {
		LinearLayout linearLayout = (LinearLayout) view;
		linearLayout.setBackgroundResource(R.drawable.bgselector_date);
		TextView dateTextView = (TextView) linearLayout.getChildAt(0);
//		dateTextView.setTextColor(R.drawable.selector_date_text_color);
		dateTextView.setPressed(true);
		return view;
	}
	
	/**
	 * 设置当天的背景色
	 */
	public View setCurrentBackground(View view) {
		LinearLayout linearLayout = (LinearLayout) view;
		linearLayout.setBackgroundResource(R.drawable.bgselector_date_press);
		TextView dateTextView = (TextView) linearLayout.getChildAt(0);
		dateTextView.setTextColor(context.getResources().getColor(R.color.white));
		return view;
	}

	/**
	 * 原始样式view
	 *
	 * @param oldView
	 * @param newView
	 */
	public void recoverStyle(View view, int[] defaultViewPosition) {
		LinearLayout viewLinearLayout = (LinearLayout) view;
		viewLinearLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
		TextView viewDateTextView = (TextView) viewLinearLayout.getChildAt(0);
		setDateTextColor(view,viewDateTextView, 
				defaultViewPosition[0], defaultViewPosition[1],
				defaultViewPosition[2],viewDateTextView.getText().toString());

	}

	// 将一个月中的每一天的值添加入数组dayNumber中
	private void getDayNumber(int year, int month) {
		//下一个月的1号
		int j = 1;
		for (int i = 0; i < dayNumberLength; i++) {
			DayNumber dayNumber = new DayNumber();
			dayNumber.setYear(year);
			if (i < dayOfWeek) { // 前一个月
				int temp = lastDaysOfMonth - dayOfWeek + 1;
				dayNumber.setMonth(month - 1);
				dayNumber.setDay(temp + i);

			} else if (i < daysOfMonth + dayOfWeek) { // 本月
				int day = i - dayOfWeek + 1; // 得到的日期
				dayNumber.setMonth(month);
				dayNumber.setDay(day);
				// 对于当前月才去标记当前日期
				setShowYear(year);
				setShowMonth(month);
			}else {
				break;
			}
//			else { // 下一个月
//				dayNumber.setMonth(month + 1);
//				dayNumber.setDay(j);
//				j++;
//			}
			dayNumbers.add(dayNumber);
		}

	}

	public void matchScheduleDate(int year, int month, int day) {

	}

	/**
	 * 点击每一个item时返回item中的日期
	 *
	 * @param position
	 * @return
	 */
	public DayNumber getDateByClickItem(int position) {
		return dayNumbers.get(position);
	}

	/**
	 * 在点击gridView时，得到这个月中第一天的位置
	 *
	 * @return
	 */
	public int getStartPositon() {
		return dayOfWeek;
	}

	/**
	 * 在点击gridView时，得到这个月中最后一天的位置
	 *
	 * @return
	 */
	public int getEndPosition() {
		return (dayOfWeek + daysOfMonth + 7) - 1;
	}

	public int getShowYear() {
		return showYear;
	}

	public void setShowYear(int showYear) {
		this.showYear = showYear;
	}

	public int getShowMonth() {
		return showMonth;
	}

	public void setShowMonth(int showMonth) {
		this.showMonth = showMonth;
	}

	public String getAnimalsYear() {
		return animalsYear;
	}

	public void setAnimalsYear(String animalsYear) {
		this.animalsYear = animalsYear;
	}

	public int getLeapMonth() {
		return leapMonth;
	}

	public void setLeapMonth(int leapMonth) {
		this.leapMonth = leapMonth;
	}

	public String getCyclical() {
		return cyclical;
	}

	public void setCyclical(String cyclical) {
		this.cyclical = cyclical;
	}

	public List<String> getFlagList() {
		return flagList;
	}

	public void setFlagList(List<String> flagList) {
		this.flagList = flagList;
	}

	public static class CalendarItemViewHolder {
		public boolean ifExistsAlert = false;
	}

	public class DayNumber {
		private int year;
		private int month;
		private int day;
		private int week;
		private String chinaDayString;

		public int getYear() {
			return year;
		}

		public void setYear(int year) {
			this.year = year;
		}

		public int getMonth() {
			return month;
		}

		public void setMonth(int month) {
			if (month < 0) {
				this.year -= 1;
				this.month = 12;
				return;
			}

			if (month > 12) {
				this.year += 1;
				this.month = month - 12;
				return;
			}
			this.month = month;
		}

		public int getDay() {
			return day;
		}

		public void setDay(int day) {
			this.day = day;
		}

		public int getWeek() {
			return week;
		}

		public void setWeek(int week) {
			this.week = week;
		}

		public String getChinaDayString() {
			return chinaDayString;
		}

		public void setChinaDayString(String chinaDayString) {
			this.chinaDayString = chinaDayString;
		}
	}
}
