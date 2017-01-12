package cn.com.zte.emeeting.app.views.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.com.zte.mobileemeeting.R;


import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 月份控件
 * 
 * @author LangK
 * 
 */
public class MonthLayout extends LinearLayout {

	private static final String TAG = MonthLayout.class.getSimpleName();
	/**
	 * 月份文字
	 */
	private TextView monthText;
	/**
	 * 上下文
	 */
	private Context mContext;
	/**
	 * 月份父布局
	 */
	private LinearLayout bobyLayout;

	private SpecialCalendar specialCalendar = new SpecialCalendar();
	/**
	 * 日期
	 */
	private Date date;
	/**
	 * 每个月最多的数
	 */
	private int dayNumberLength = 42;
	/**
	 * 数据源
	 */
	private List<DayNumber> list;
	/**
	 * 当天日期
	 */
	private Date currentDate;
	private Calendar currentCalendar = Calendar.getInstance();
	
	private OnDateSelectedListener onDateSelectedListener;
	
	/**
	 * 屏幕宽度 
	 */
	private int screenWidth;
	/**
	 * 当天的位置tag
	 */
	private int todayTag = -1;

	public MonthLayout(Context context, Date date) {
		super(context);
		this.mContext = context;
		this.date = date;
		initView();
	}

	public MonthLayout(Context context, AttributeSet attrs, Date date) {
		super(context, attrs);
		this.mContext = context;
		this.date = date;
		initView();
	}

	public MonthLayout(Context context, AttributeSet attrs, int defStyle,
			Date date) {
		super(context, attrs, defStyle);
		this.mContext = context;
		this.date = date;
		initView();
	}

	private void initView() {
		DisplayMetrics dmMetrics = new DisplayMetrics();
		WindowManager wmManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		wmManager.getDefaultDisplay().getMetrics(dmMetrics);
		screenWidth = dmMetrics.widthPixels;
		
		currentDate = new Date();
		currentCalendar.setTime(currentDate);
		LayoutInflater.from(mContext).inflate(R.layout.view_month_layout, this);
		monthText = (TextView) findViewById(R.id.view_monty_text);
		bobyLayout = (LinearLayout) findViewById(R.id.view_monty_layout);
		addMonthLayout();
	}

	/**
	 * 增加月份实体
	 */
	private void addMonthLayout() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		monthText.setText(month + "月");
		list = getCalendar(year, month);
		int index;
		for (int i = 0; i < 6; i++) {
			if (i*7>=list.size()) {
				break;
			}
			LinearLayout rowLayout = new LinearLayout(mContext);
			rowLayout.setOrientation(LinearLayout.HORIZONTAL);
			rowLayout.setWeightSum(7);
			rowLayout.setGravity(Gravity.CENTER_VERTICAL);
			for (int j = 0; j < 7; j++) {
				index = i*7+j;
				View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_calendar, null);
				TextView textView = (TextView) itemView.findViewById(R.id.alert_date_text_id);
				if (index>=list.size()) {
					textView.setText("");
				}else {
					try {
						if (!list.get(index).getDay().equals("")) {
							if (currentCalendar.get(Calendar.YEAR)==list.get(index).getYear()&&currentCalendar.get(Calendar.MONTH) == list.get(index).getMonth()-1&&currentCalendar.get(Calendar.DAY_OF_MONTH) == Integer.parseInt(list.get(index).getDay())) {
//								setCurrentBackground(itemView);
								todayTag = index;
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					
					textView.setText(list.get(index).getDay());
				}
				itemView.setTag(index);
				itemView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (onDateSelectedListener!=null) {
							try {
								int tagIndex = (Integer)v.getTag();
								onDateSelectedListener.selectDay(list.get(tagIndex));
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
						}
					}
				});
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
				params.weight = 1;
				if (screenWidth>1000) {
					params.leftMargin = 10;
					params.rightMargin = 10;
				}else {
					params.leftMargin = 5;
					params.rightMargin = 5;
				}
				params.topMargin = 5;
				params.bottomMargin = 5;
				if (textView.getText().equals("")) {
					itemView.setVisibility(View.INVISIBLE);
				}
				itemView.setEnabled(false);
				rowLayout.addView(itemView,params);
			}
			bobyLayout.addView(rowLayout,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
		}
		if (todayTag != -1) {
			setCurrentBackground(bobyLayout.findViewWithTag(todayTag));
		}
	}

	// 得到某年的某月的天数且这月的第一天是星期几
	public List<DayNumber> getCalendar(int year, int month) {
		boolean isLeapyear = specialCalendar.isLeapYear(year); // 是否为闰年
		int daysOfMonth = specialCalendar.getDaysOfMonth(isLeapyear, month); // 某月的总天数
		int dayOfWeek = specialCalendar.getWeekdayOfMonth(year, month); // 某月第一天为星期几
		int lastDaysOfMonth = specialCalendar.getDaysOfMonth(isLeapyear,
				month - 1); // 上一个月的总天数
		return getDayNumber(year, month, dayOfWeek, lastDaysOfMonth,
				daysOfMonth);
	}

	/**
	 * 将一个月中的每一天的值添加入数组dayNumber中
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param dayOfWeek
	 *            某月第一天为星期几
	 * @param lastDaysOfMonth
	 *            上一个月的总天数
	 * @param daysOfMonth
	 *            某月的总天数
	 * @return
	 */
	private List<DayNumber> getDayNumber(int year, int month, int dayOfWeek,
			int lastDaysOfMonth, int daysOfMonth) {
		List<DayNumber> dayNumbers = new ArrayList<DayNumber>();
		// 下一个月的1号
		for (int i = 0; i < dayNumberLength; i++) {
			DayNumber dayNumber = new DayNumber();
			dayNumber.setYear(year);
			if (i < dayOfWeek) { // 前一个月
				dayNumber.setMonth(month - 1);
				dayNumber.setDay("");
			} else if (i < daysOfMonth + dayOfWeek) { // 本月
				int day = i - dayOfWeek + 1; // 得到的日期
				dayNumber.setMonth(month);
				dayNumber.setDay(day+"");
			} else {
				break;
			}
			dayNumbers.add(dayNumber);
		}
		return dayNumbers;

	}
	int tmpyeah;
	int tmpmonth;
	int tmpday;
	
	/**
	 * 设置选择状态
	 * @param flagList String格式yyyy-MM-dd
	 */
	public void setSelectDay(List<String> flagList){
		if (flagList==null||flagList.size()==0) {
			return;
		}
		try {
			for (int i = 0; i < list.size(); i++) {
				DayNumber dayNumber = list.get(i);
				tmpyeah = dayNumber.getYear();
				tmpmonth = dayNumber.getMonth();
				tmpday = dayNumber.getDay().equals("")?0:Integer.parseInt(dayNumber.getDay());
				for (int j = 0; j < flagList.size(); j++) {
					String dateFlag = flagList.get(j);
					try {
						if (Integer.parseInt(dateFlag.split("-")[0])==tmpyeah&&
								Integer.parseInt(dateFlag.split(" ")[0].split("-")[1])==tmpmonth&&
										Integer.parseInt(dateFlag.split(" ")[0].split("-")[2])==tmpday) {
							if (currentCalendar.get(Calendar.YEAR)==tmpyeah&&currentCalendar.get(Calendar.MONTH)+1==tmpmonth&&currentCalendar.get(Calendar.DAY_OF_MONTH)==tmpday) {
								bobyLayout.findViewWithTag(i).setEnabled(true);
							}else {
								setCheckedBackground(bobyLayout.findViewWithTag(i));
							}
						}
					} catch (Exception e) {
						Log.d(TAG, "解析错误 ："+dateFlag);
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 设置选中时的背景色
	 * @param view
	 * @return view
	 */
	public void setCheckedBackground(View view) {
		LinearLayout linearLayout = (LinearLayout) view;
		linearLayout.setBackgroundResource(R.drawable.bgselector_date);
		TextView dateTextView = (TextView) linearLayout.getChildAt(0);
		dateTextView.setTextColor(mContext.getResources().getColor(R.color.AllTextColor));
		linearLayout.setEnabled(true);
	}
	
	/**
	 * 设置当天的背景色
	 */
	public void setCurrentBackground(View view) {
		LinearLayout linearLayout = (LinearLayout) view;
		linearLayout.setBackgroundResource(R.drawable.bgselector_date_press);
		TextView dateTextView = (TextView) linearLayout.getChildAt(0);
		dateTextView.setTextColor(Color.WHITE);
	}
	
	public OnDateSelectedListener getOnDateSelectedListener() {
		return onDateSelectedListener;
	}

	public void setOnDateSelectedListener(OnDateSelectedListener onDateSelectedListener) {
		this.onDateSelectedListener = onDateSelectedListener;
	}

	public interface OnDateSelectedListener{
		public void selectDay(DayNumber dayNumber);
	}
}
