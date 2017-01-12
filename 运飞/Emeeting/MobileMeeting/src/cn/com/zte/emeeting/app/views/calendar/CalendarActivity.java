package cn.com.zte.emeeting.app.views.calendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import roboguice.inject.InjectView;

import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.emeeting.app.activity.AllMeetingActivity;
import cn.com.zte.emeeting.app.appservice.MyMeetingService;
import cn.com.zte.emeeting.app.base.ConfigrationList;
import cn.com.zte.emeeting.app.base.activity.AppActivity;
import cn.com.zte.emeeting.app.response.entity.UserRelevantMeetingDateInfo;
import cn.com.zte.emeeting.app.response.instrument.GetUserRelevantMeetingDatesResponse;
import cn.com.zte.emeeting.app.views.TopBar;
import cn.com.zte.emeeting.app.views.ViewCalenderPrompt;
import cn.com.zte.emeeting.app.views.TopBar.ButtonOnClick;
import cn.com.zte.emeeting.app.views.calendar.MonthLayout.OnDateSelectedListener;
import cn.com.zte.mobileemeeting.R;

import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

public class CalendarActivity extends AppActivity {

	
	/**
	 * 是否选择日期
	 */
	public static final String ISCHOOSEDATE = "isChooseDate";
	/**
	 * 日期选择结果
	 */
	public static final String DATERESULT_STRING = "DateResult";
	
	@InjectView(R.id.calender_boby)
	private LinearLayout bobyLayout;
	@InjectView(R.id.calendar_topbar)
	private TopBar topBar;
	/**
	 * 会议提醒
	 */
	@InjectView(R.id.calender_prompt)
	private ViewCalenderPrompt calenderPrompt;

	/**
	 * 所有有会议的日期
	 */
	private List<UserRelevantMeetingDateInfo> list = new ArrayList<UserRelevantMeetingDateInfo>();
	/**
	 * 会议总数
	 */
	private int meetCount = 0;
	/**
	 * 当前两个月有会议的日期
	 */
	private List<String> meetList = new ArrayList<String>();
	
	private MyMeetingService mService;
	@Override
	protected void initContentView() {
		// TODO Auto-generated method stub
		super.initContentView();
		setContentView(R.layout.activity_calender);
		mService = new MyMeetingService(mContext);
		mService.getAllMeetingDate(handerAllMeetingDate);
		mContext = this;
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		super.initData();
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		for (int i = 0; i < 12; i++) {
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, i);
			MonthLayout layout = new MonthLayout(this, calendar.getTime());
			layout.setOnDateSelectedListener(new OnDateSelectedListener() {
				@Override
				public void selectDay(DayNumber dayNumber) {
					// TODO Auto-generated method stub
					if (dayNumber != null) {
						StringBuilder stringBuilder = new StringBuilder();
						stringBuilder.append(dayNumber.getYear()).append("-")
								.append(dayNumber.getMonth()).append("-")
								.append(dayNumber.getDay());
						Intent intent = new Intent(
								ConfigrationList.DateChoosedReceive);
						intent.putExtra(DATERESULT_STRING, stringBuilder.toString());
						sendBroadcast(intent);
						Log.d(TAG, stringBuilder.toString());
//						finish();
						goAllMeeting(stringBuilder.toString());
						return;
					} else {
						Log.d(TAG, "程序异常");
					}
				}
			});
			bobyLayout.addView(layout, new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT));
		}
	}
	
	/** 跳转到所有会议界面 */
	private void goAllMeeting(String date) {
		Intent it = new Intent(mContext, AllMeetingActivity.class);
		it.putExtra("data", date);
		startActivity(it);
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		super.initViews();
		topBar.setViewBackGround(TopBar.leftBtnLogo, R.drawable.back);
		topBar.setViewOnClickListener(TopBar.leftBtnLogo, new ButtonOnClick() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		topBar.HiddenView(TopBar.rightBtnLogo);
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
		topBar.setViewText(TopBar.titleLogo, format.format(date));
	}

	private void setSelectedDate(List<String> list) {
		for (int i = 0; i < bobyLayout.getChildCount(); i++) {
			((MonthLayout) bobyLayout.getChildAt(i)).setSelectDay(list);
		}
	}

	/**
	 * 筛选共有有会议的日期
	 */
	private void filterCurrentMeetList() {
		meetCount = 0;
		meetList.clear();
		for (int i = 0; i < list.size(); i++) {
			String date = list.get(i).getMD();
			try {
				meetCount = meetCount + Integer.parseInt(list.get(i).getTDMN());
				meetList.add(list.get(i).getMD());
			} catch (Exception e) {
				Log.d(TAG, "日期解析错误");
				e.printStackTrace();
			}

		}
		
		setSelectedDate(meetList);

		calenderPrompt.setMonthText("");
		calenderPrompt.setNumberText(meetCount + "");
		showPopuView();
	}

	/**
	 * 显示会议总数提醒
	 */
	private void showPopuView() {
		handler.removeMessages(0);
		calenderPrompt.setVisibility(View.VISIBLE);
		handler.sendEmptyMessageDelayed(0, 5000);
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				calenderPrompt.setVisibility(View.GONE);
				break;
			case 1:
				showPopuView();
				break;
			default:
				break;
			}

		};
	};

	/** 获取与我相关的会议所在日期handler */
	private BaseAsyncHttpResponseHandler<GetUserRelevantMeetingDatesResponse> handerAllMeetingDate = new BaseAsyncHttpResponseHandler<GetUserRelevantMeetingDatesResponse>() {
		@Override
		public void onSuccessTrans(GetUserRelevantMeetingDatesResponse res) {
			if (res.getD() != null) {
				list = res.getD();
				filterCurrentMeetList();
			}
		};

		public void onFailureTrans(
				GetUserRelevantMeetingDatesResponse responseModelVO) {
			filterCurrentMeetList();
		};

		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			filterCurrentMeetList();
		};
	};
}
