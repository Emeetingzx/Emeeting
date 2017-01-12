/**
 * 
 */
package cn.com.zte.emeeting.app.dialog;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import cn.com.zte.android.common.util.JsonUtil;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.emeeting.app.adapter.MeetingBookQueryResultAdapter;
import cn.com.zte.emeeting.app.appservice.MeetingBookService;
import cn.com.zte.emeeting.app.base.ConfigrationList;
import cn.com.zte.emeeting.app.request.entity.ScreeningCondition;
import cn.com.zte.emeeting.app.response.entity.MeetingInfo;
import cn.com.zte.emeeting.app.response.entity.MeetingRoomInfo;
import cn.com.zte.emeeting.app.response.instrument.GetMeetingRoomBookingInfoResponse;
import cn.com.zte.emeeting.app.response.instrument.GetMeetingRoomDetailResponse;
import android.widget.TextView;
import cn.com.zte.emeeting.app.util.DateFormatUtil;
import cn.com.zte.emeeting.app.util.EmeetingToast;
import cn.com.zte.emeeting.app.util.EmeetingUtil;
import cn.com.zte.emeeting.app.util.LogTools;
import cn.com.zte.emeeting.app.util.SharedPreferenceUtil;
import cn.com.zte.emeeting.app.views.ClockChooseView;
import cn.com.zte.mobilebasedata.entity.PageInput;
import cn.com.zte.mobileemeeting.R;

/**
 * 该类用于:会议室预订
 * 
 * @author wf
 */
public class DlgMeetingBook extends Dialog {

	private Context mContext;

	private DlgMeetingListener listener;

	private MeetingRoomInfo meetingRoom;

	private ClockChooseView timeView;

	private Date chooseDate;

	private String selectDate;
	
	private TimeOutListener timeOutListener;
	
	private List<Integer> selectList;
	
	private List<Integer> disList;

	private boolean isShake = false;

	
	/**
	 * 初始化表盘
	 * 
	 * @param context
	 * @param isShake	是否是摇一摇模块的预定
	 * @param chooseDate
	 *            选择的日期
	 * @param meetingRoom
	 *            会议室对象
	 * @param selectDate
	 *            默认选中的时间段 日期格式必须为yyyy-MM-dd HH:mm HH:mm
	 * @param listener
	 */
	public DlgMeetingBook(Context context,Boolean isShake, Date chooseDate,
			MeetingRoomInfo meetingRoom, String selectDate,
			DlgMeetingListener listener,TimeOutListener timeOutListener) {
		super(context, R.style.dlgStyle_clock);
		this.isShake = isShake;
		this.timeOutListener = timeOutListener;
		this.mContext = context;
		this.listener = listener;
		this.selectDate = selectDate;
		this.meetingRoom = meetingRoom;
		this.chooseDate = chooseDate;
		initView();
	}
	
	/**
	 * 初始化表盘
	 * 
	 * @param context
	 * @param isShake	是否是摇一摇模块的预定
	 * @param chooseDate
	 *            选择的日期
	 * @param meetingRoom
	 *            会议室对象
	 * @param selectDate
	 *            默认选中的时间段 日期格式必须为yyyy-MM-dd HH:mm HH:mm
	 * @param listener
	 */
	public DlgMeetingBook(Context context, Date chooseDate,
			MeetingRoomInfo meetingRoom, String selectDate,
			DlgMeetingListener listener,TimeOutListener timeOutListener) {
		super(context, R.style.dlgStyle_clock);
		this.timeOutListener = timeOutListener;
		this.mContext = context;
		this.listener = listener;
		this.selectDate = selectDate;
		this.meetingRoom = meetingRoom;
		this.chooseDate = chooseDate;
		initView();
	}

	/**
	 * 该方法用于:
	 * 
	 * @param
	 * @return void
	 */
	private void initView() {
		setContentView(R.layout.dlg_meetingroom_book);
		View btn_cancel_dlg_clock = findViewById(R.id.btn_cancel_dlg_clock);
		View btn_ok_dlg_clock = findViewById(R.id.btn_ok_dlg_clock);
		timeView = (ClockChooseView) findViewById(R.id.clockchooseview_dlg);
		TextView tv_dlg_meetingbook_meetingscale = (TextView) findViewById(R.id.tv_dlg_meetingbook_meetingscale);
		TextView tv_dlg_meetingbook_meetingprojector = (TextView) findViewById(R.id.tv_dlg_meetingbook_meetingprojector);
		TextView tv_dlg_meetingbook_tv = (TextView) findViewById(R.id.tv_dlg_meetingbook_tv);
		TextView tv_dlg_meetingbook_phone = (TextView) findViewById(R.id.tv_dlg_meetingbook_phone);
		TextView tv_dlg_meetingbook_name = (TextView) findViewById(R.id.tv_dlg_meetingbook_name);

		timeView.setCurrentDate(chooseDate);
		if (meetingRoom != null) {
			tv_dlg_meetingbook_meetingscale.setText(meetingRoom.getMRS());
			tv_dlg_meetingbook_meetingprojector.setText(EmeetingUtil
					.getMeetingRoomProjectorState(meetingRoom.getPJS()));
			tv_dlg_meetingbook_tv.setText(EmeetingUtil
					.getMeetingRoomTvState(meetingRoom.getTVS()));
			tv_dlg_meetingbook_phone.setText(EmeetingUtil
					.getMeetingRoomPhoneState(meetingRoom.getPS()));
			tv_dlg_meetingbook_name.setText(meetingRoom.getMRN());
		}

		btn_cancel_dlg_clock.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
				if (listener != null) {
					listener.onCancelClicked();
				}
			}
		});

		btn_ok_dlg_clock.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// dismiss();
				if (listener != null) {
					listener.onOkButtonClicked("", timeView);
				}
			}
		});

		WindowManager manager = ((Activity) mContext).getWindowManager();
		Display display = manager.getDefaultDisplay();
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.width = (int) display.getWidth();
		lp.height = LinearLayout.LayoutParams.FILL_PARENT;
		this.getWindow().setAttributes(lp);
		this.getWindow().setGravity(Gravity.CENTER);
		// this.getWindow().setWindowAnimations(R.style.dlgAnimation_up);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		new MeetingBookService(mContext).getMeetingRoomDetail(new PageInput(),
				meetingRoom.getMRID(), df.format(chooseDate), "", "",
				new ScreeningCondition(), new HandlerGetMeetingRoomList(true));
		// setDisClick(meetingRoom);
	}

	public interface DlgMeetingListener {
		void onOkButtonClicked(Object o, ClockChooseView timeView);
		void onCancelClicked();
	}

	/**
	 * 设置表盘上选中的位置 此方法必须在设置不可点击之前调用
	 * 
	 * @param dateString
	 *            日期格式必须为yyyy-MM-dd HH:mm HH:mm
	 */
	@SuppressLint("SimpleDateFormat")
	private void setSelectedClick(String dateString) {
		if (dateString == null) {
			return;
		}
		try {
			// 已选集合
			selectList = new ArrayList<Integer>();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Calendar calendar = Calendar.getInstance();
			Calendar currentCalendar = Calendar.getInstance();
			Date currentDate = new Date();
			currentCalendar.setTime(currentDate);
			Date date = new Date();
			String[] dates = dateString.split(" ");
			String endTime = dates[2];
			String startTime = dates[1];
			String dayString = dates[0];
			date = format.parse(dayString);
			calendar.setTime(date);
			Calendar startCalendar = Calendar.getInstance();
			Calendar endCalendar = Calendar.getInstance();
			startCalendar.setTime(format2.parse(dayString + " " + startTime));
			endCalendar.setTime(format2.parse(dayString + " " + endTime));
			getSelectClick(startCalendar, endCalendar, selectList);
			
			if (disList!=null&&selectList!=null) {
				//偏移量
				int skewingNum = 0;
				for (int i = 0; i < selectList.size(); i++) {
					if (disList.contains(selectList.get(i))) {
						if (i-skewingNum == 0) {
							skewingNum++;
						}else {
							if (timeOutListener!=null) {
								timeOutListener.onTimeOut();
							}
						}
					}
				}
				
				if (skewingNum>0) {
					for (int i = 0; i < skewingNum; i++) {
						int additem = selectList.get(selectList.size()-1) +1;
						if (additem > 26||disList.contains(additem)) {
							if (timeOutListener!=null) {
								timeOutListener.onTimeOut();
							}
						}else {
							selectList.remove(0);
							selectList.add(additem);
						}
					}
				}
				
			}
			
			timeView.setSelectlick(selectList);
			timeView.resetChooseTime();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		}

	}

	/**
	 * 设置表盘上不可点击的位置
	 * 
	 * @param list
	 */
	@SuppressLint("SimpleDateFormat")
	private void setDisClick(MeetingRoomInfo info) {

		// List<MeetingInfo> tMeetingInfos = new ArrayList<MeetingInfo>();
		// MeetingInfo info2 = new MeetingInfo();
		// info2.setBD("2015-10-16 15:35:00");
		// info2.setED("2015-10-16 16:35:00");
		// tMeetingInfos.add(info2);
		// info.setHBMIS(tMeetingInfos);

		// 不可选集合
		disList = new ArrayList<Integer>();
		// 当前时间
		Date date;
		if (isShake) {
			date = DateFormatUtil.getServerTime(mContext);
		}else {
			date = DateFormatUtil.getServerTime(mContext);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Calendar beginCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		Calendar startCalendar = Calendar.getInstance();
		// 判断是否是当天，如是当天需要当前之前的时间不可选
		if (chooseDate == null) {
			startCalendar.setTime(date);
			startCalendar.set(Calendar.HOUR_OF_DAY, 8);
			startCalendar.set(Calendar.MINUTE, 00);
			getClick(startCalendar, calendar, disList);
		} else {
			startCalendar.setTime(chooseDate);
			if (calendar.get(Calendar.YEAR) == startCalendar.get(Calendar.YEAR)
					&& calendar.get(Calendar.DAY_OF_YEAR) == startCalendar
							.get(Calendar.DAY_OF_YEAR)) {
				startCalendar.setTime(calendar.getTime());
				startCalendar.set(Calendar.HOUR_OF_DAY, 8);
				startCalendar.set(Calendar.MINUTE, 00);
				getClick(startCalendar, calendar, disList);
			}
		}

		// 提取会议室对象里面已经预定的会议时段
		if (info != null && info.getHBMIS() != null) {
			SimpleDateFormat format2 = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			List<MeetingInfo> meetingInfos = info.getHBMIS();
			Date beginDate = null;
			Date endDate = null;
			try {
				for (int i = 0; i < meetingInfos.size(); i++) {
					beginDate = format2.parse(meetingInfos.get(i).getBD());
					endDate = format2.parse(meetingInfos.get(i).getED());
					beginCalendar.setTime(beginDate);
					endCalendar.setTime(endDate);
					if ((beginCalendar.get(Calendar.DAY_OF_YEAR) == endCalendar
							.get(Calendar.DAY_OF_YEAR))
							&& (endCalendar.get(Calendar.DAY_OF_YEAR) == startCalendar
									.get(Calendar.DAY_OF_YEAR))
							&& (endCalendar.get(Calendar.YEAR) == startCalendar
									.get(Calendar.YEAR))
							&& (beginCalendar.get(Calendar.YEAR) == startCalendar
									.get(Calendar.YEAR))) {
						getClick(beginCalendar, endCalendar, disList);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		timeView.setDisClick(disList);

		setSelectedClick(selectDate);
	}
	
	/**
	 * 获取开始到结束时间在列表中的位置
	 */
	private void getSelectClick(Calendar beginCalendar, Calendar endCalendar,
			List<Integer> list) {
		int beginhour = beginCalendar.get(Calendar.HOUR_OF_DAY);
		int beginminute = beginCalendar.get(Calendar.MINUTE);
		int endhour = endCalendar.get(Calendar.HOUR_OF_DAY);
		int endminute = endCalendar.get(Calendar.MINUTE);
		StringBuilder begintime = new StringBuilder();
		//摇一摇过了半小时需要选中下一个整点
		if (isShake) {
			if (beginminute>=30) {
				beginhour++;
			}
		}else {
			if (beginminute>30) {
				beginhour++;
			}
		}
		
		if (beginhour < 10) {
			begintime.append("0").append(beginhour).append(":");
		} else {
			begintime.append(beginhour).append(":");
		}
		if (isShake) {
			if (beginminute < 30) {
				begintime.append("30");
			} else {
				begintime.append("00");
			}
		}else {
			if (beginminute==0) {
				begintime.append("00");
			}else if (beginminute <= 30) {
				begintime.append("30");
			} else {
				begintime.append("00");
			}
		}
		

		StringBuilder endtime = new StringBuilder();
		if (isShake) {
			if (endminute>=30) {
				endhour++;
			}
		}else {
			if (endminute>30) {
				endhour++;
			}
		}
		
		if (endhour < 10) {
			endtime.append("0").append(endhour).append(":");
		} else {
			endtime.append(endhour).append(":");
		}
		if (isShake) {
			if (endminute < 30) {
				endtime.append("30");
			} else {
				endtime.append("00");
			}
		}else {
			if (endminute == 0) {
				endtime.append("00");
			} else if (endminute <= 30) {
				endtime.append("30");
			} else {
				endtime.append("00");
			}
		}

		int beginPosition = 0;
		int endPosition = 0;
		
		
		if (beginhour<8) {
			beginPosition = 0;
		}
		if (endhour>=21) {
			endPosition = timeView.getTimeStrings().length;
		}
		
		for (int j = 0; j < timeView.getTimeStrings().length; j++) {
			if (begintime.toString().equals(timeView.getTimeStrings()[j])) {
				beginPosition = j + 1;
			} else if (endtime.toString().equals(timeView.getTimeStrings()[j])) {
				endPosition = j + 1;
			}
		}
		if (beginPosition > 0 && endPosition > 0) {
			for (int j = beginPosition; j < endPosition; j++) {
				list.add(j);
			}
		}
	}


	/**
	 * 获取开始到结束时间在列表中的位置
	 */
	private void getClick(Calendar beginCalendar, Calendar endCalendar,
			List<Integer> list) {
		int beginhour = beginCalendar.get(Calendar.HOUR_OF_DAY);
		int beginminute = beginCalendar.get(Calendar.MINUTE);
		int endhour = endCalendar.get(Calendar.HOUR_OF_DAY);
		int endminute = endCalendar.get(Calendar.MINUTE);
		StringBuilder begintime = new StringBuilder();
		if (beginhour < 10) {
			begintime.append("0").append(beginhour).append(":");
		} else {
			begintime.append(beginhour).append(":");
		}
		if (beginminute < 30) {
			begintime.append("00");
		} else {
			begintime.append("30");
		}

		StringBuilder endtime = new StringBuilder();
		if (isShake) {
			if (endminute > 30) {
				endhour++;
			}
		}else {
			if (endminute > 30) {
				endhour++;
			}
		}
		
		if (endhour < 10) {
			endtime.append("0").append(endhour).append(":");
		} else {
			endtime.append(endhour).append(":");
		}
		if (isShake) {
			if (endminute == 0) {
				endtime.append("00");
			} else if (endminute <= 30) {
				endtime.append("30");
			} else {
				endtime.append("00");
			}
		}else{
			if (endminute == 0) {
				endtime.append("00");
			} else if (endminute <= 30) {
				endtime.append("30");
			} else {
				endtime.append("00");
			}
		}

		int beginPosition = 0;
		int endPosition = 0;
		
		
		if (beginhour<8) {
			beginPosition = 0;
		}
		if (endhour>=21) {
			endPosition = timeView.getTimeStrings().length;
		}
		
		for (int j = 0; j < timeView.getTimeStrings().length; j++) {
			if (begintime.toString().equals(timeView.getTimeStrings()[j])) {
				beginPosition = j + 1;
			} else if (endtime.toString().equals(timeView.getTimeStrings()[j])) {
				endPosition = j + 1;
			}
		}
		if (beginPosition > 0 && endPosition > 0) {
			for (int j = beginPosition; j < endPosition; j++) {
				list.add(j);
			}
		}
	}

	public TimeOutListener getTimeOutListener() {
		return timeOutListener;
	}

	/**
	 * @param timeOutListener
	 */
	public void setTimeOutListener(TimeOutListener timeOutListener) {
		this.timeOutListener = timeOutListener;
	}

	/** 获取会议室列表handler */
	private class HandlerGetMeetingRoomList extends
			BaseAsyncHttpResponseHandler<GetMeetingRoomDetailResponse> {

		/**
		 * 该类构造方法:
		 * 
		 * @param
		 */
		private HandlerGetMeetingRoomList(boolean isShowDlg) {
			// TODO Auto-generated constructor stub
			super(isShowDlg);
		}

		@Override
		public void onSuccessTrans(GetMeetingRoomDetailResponse res) {

			if (res.getD() != null) {
				LogTools.i("dlgmeeting", "获取会议室详情");
				System.out.println(JsonUtil.toJson(res.getD()));
				MeetingRoomInfo tmpMeeting = res.getD();
				meetingRoom.setHBMIS(tmpMeeting.getHBMIS());
			}
			try {
				setDisClick(meetingRoom);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};

		@Override
		public void onFailureTrans(GetMeetingRoomDetailResponse responseModelVO) {
			// TODO Auto-generated method stub
			super.onFailureTrans(responseModelVO);
			try {
				setDisClick(meetingRoom);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode, String strMsg) {
			try {
				setDisClick(meetingRoom);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!TextUtils.isEmpty(strMsg))
			{
				EmeetingToast.showHttp(mContext, strMsg);
			}
		};

	};
	
	/**
	 * 时间是否超时
	 */
	public interface TimeOutListener{
		public void onTimeOut();
	}

}
