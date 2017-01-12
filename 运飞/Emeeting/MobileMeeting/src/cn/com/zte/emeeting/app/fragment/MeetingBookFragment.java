package cn.com.zte.emeeting.app.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.platform.comapi.map.t;

import roboguice.inject.InjectView;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.zte.android.app.fragment.BaseFragment;
import cn.com.zte.android.common.util.JsonUtil;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.android.resource.inflater.BaseLayoutInflater;
import cn.com.zte.android.widget.dialog.DialogManager;
import cn.com.zte.emeeting.app.activity.MainActivity;
import cn.com.zte.emeeting.app.activity.MeetingBookChooseOtherActivity;
import cn.com.zte.emeeting.app.activity.ShakeActivity;
import cn.com.zte.emeeting.app.adapter.MeetingBookQueryResultAdapter;
import cn.com.zte.emeeting.app.adapter.MyServiceAdapter;
import cn.com.zte.emeeting.app.adapter.MeetingBookQueryResultAdapter.MeetingBookListener;
import cn.com.zte.emeeting.app.appservice.MeetingBookService;
import cn.com.zte.emeeting.app.base.MyApplication;
import cn.com.zte.emeeting.app.database.entity.shared.DBMeetingRoomAddress;
import cn.com.zte.emeeting.app.dialog.DialogTopTimeChoose;
import cn.com.zte.emeeting.app.dialog.DlgFilterMeeting;
import cn.com.zte.emeeting.app.dialog.DlgFilterMeeting.DlgFilterListener;
import cn.com.zte.emeeting.app.dialog.DlgLocationAndGetMeeting;
import cn.com.zte.emeeting.app.dialog.DlgMeetingBook;
import cn.com.zte.emeeting.app.dialog.DlgMeetingBook.DlgMeetingListener;
import cn.com.zte.emeeting.app.dialog.DlgMeetingBook.TimeOutListener;
import cn.com.zte.emeeting.app.dialog.DlgPlaceChoose;
import cn.com.zte.emeeting.app.dialog.DlgPlaceChoose.ItemListener;
import cn.com.zte.emeeting.app.entity.system.MyLocation;
import cn.com.zte.emeeting.app.request.entity.ScreeningCondition;
import cn.com.zte.emeeting.app.response.entity.MeetingRoomInfo;
import cn.com.zte.emeeting.app.response.instrument.GetLockBookingMeetingRoomResponse;
import cn.com.zte.emeeting.app.response.instrument.GetMeetingRoomBookingInfoResponse;
import cn.com.zte.emeeting.app.response.instrument.GetNearParkAddressInfoResponse;
import cn.com.zte.emeeting.app.response.instrument.GetServerTimeResponse;
import cn.com.zte.emeeting.app.response.instrument.GetValidBookDateResponse;
import cn.com.zte.emeeting.app.util.DataConst;
import cn.com.zte.emeeting.app.util.DateFormatUtil;
import cn.com.zte.emeeting.app.util.EmeetingToast;
import cn.com.zte.emeeting.app.util.EmeetingUtil;
import cn.com.zte.emeeting.app.util.LogTools;
import cn.com.zte.emeeting.app.util.SharedPreferenceUtil;
import cn.com.zte.emeeting.app.views.BaseListViewLayout;
import cn.com.zte.emeeting.app.views.BaseRefreshDataMeans;
import cn.com.zte.emeeting.app.views.ClockChooseView;
import cn.com.zte.emeeting.app.views.ViewListEmpty;
import cn.com.zte.emeeting.app.views.TimeChooseTopView.onClickEventListener;
import cn.com.zte.emeeting.app.views.TopBar;
import cn.com.zte.emeeting.app.views.TopBar.ButtonOnClick;
import cn.com.zte.emeeting.app.views.pulllistview.PullToRefreshLayout;
import cn.com.zte.emeeting.app.views.pulllistview.PullToRefreshLayout.OnRefreshListener;
import cn.com.zte.mobilebasedata.entity.PageInput;
import cn.com.zte.mobileemeeting.R;

/**
 * 会议预订Fragment
 * 
 * @author wf
 * */
public class MeetingBookFragment extends BaseFragment implements
		OnClickListener {
	private static final String TAG = "MeetingBookFragment";

	/** 结果显示所在的listview */
	@InjectView(R.id.refresh_listview)
	private PullToRefreshLayout refresh_listview;

	/** 显示当前选中时间的布局 */
	@InjectView(R.id.rl_mbf_currenttime)
	private RelativeLayout layout_timeChoose;

	/** listview无数据时显示 */
	@InjectView(R.id.emptyview_lv_empty)
	private ViewListEmpty emptyview_lv_empty;

	/** 显示当前选中时间的textview */
	@InjectView(R.id.tv_mbf_currenttime)
	private TextView tv_mbf_currenttime;

	/** 显示当前选中时间的textview */
	@InjectView(R.id.tv_mbf_chooseplace)
	private TextView tv_mbf_chooseplace;

	/** 选择地区按钮 */
	@InjectView(R.id.ll_mbf_filter_place)
	private View ll_mbf_filter_place;

	/** 筛选条件按钮 */
	@InjectView(R.id.ll_mbf_filter_others)
	private View ll_mbf_filter_others;

	/** 背景 */
	@InjectView(R.id.rl_mbf_meetinglist)
	private View rl_mbf_meetinglist;

	/** 前一天按钮 */
	@InjectView(R.id.rl_mbf_previous_day)
	private View rl_mbf_previous_day;

	/** 前一天箭头 */
	@InjectView(R.id.iv_mbf_previous_day)
	private View iv_mbf_previous_day;

	/** 前一天文本 */
	@InjectView(R.id.tv_mbf_previous_day)
	private View tv_mbf_previous_day;

	/** 后一天按钮 */
	@InjectView(R.id.rl_mbf_next_day)
	private View rl_mbf_next_day;

	/** 后一天箭头 */
	@InjectView(R.id.iv_mbf_next_day)
	private View iv_mbf_next_day;

	/** 后一天文本 */
	@InjectView(R.id.tv_mbf_next_day)
	private View tv_mbf_next_day;

	/** 标题栏 */
	@InjectView(R.id.topbar_meetingbook)
	private TopBar topbar_meetingbook;

	/** 上下文 */
	private Context mContext;

	/** 逻辑处理类 */
	private MeetingBookService mService;

	private List<Date> workDays = new ArrayList<Date>();

	private int chooseDateIndex = 0;

	/** 分隔符 */
	private static final String separator_date = "-";

	private String chooseTime = "8:00-10:00";// 选择的时间段

	// private SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
	/** 用于显示日期的日期格式 */
	private SimpleDateFormat dfDate = new SimpleDateFormat("M月dd日");
	private SimpleDateFormat dfTime = new SimpleDateFormat("HH:mm");

	private List<MeetingRoomInfo> listRooms = new ArrayList<MeetingRoomInfo>();

	private MeetingBookQueryResultAdapter adapter;
	/** 当前选中的地址对象 **/
	private DBMeetingRoomAddress currentAddress;// 当前选中的地址对象
	/** 当前筛选对象 **/
	private ScreeningCondition filterEntity;

	private PageInput pageInput;

	private static final String PAGESIZE = "20";

	/**
	 * 开始分钟
	 */
	private static final int startMin = 14;
	/**
	 * 开始小时
	 */
	private static final int startHour = 8;

	private MeetingBookListener listenerMeetingBook = new MeetingBookListener() {

		@Override
		public void onMeetingBook(int position, MeetingRoomInfo item) {
			meetingBook(item);
		}
	};

	@Override
	protected View onCreateView(BaseLayoutInflater arg0, ViewGroup arg1,
			Bundle arg2) {
		mContext = getActivity();
		mService = new MeetingBookService(mContext);
		return arg0.inflate(R.layout.fragment_meetingbook, null);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	protected void initData() {
		mService.getValidBookDate(handlerMaxTime);
		// lv_mbf_meetingbookquery_result.setEmptyView(tv_emptyview_lv_mbf);
		rl_mbf_meetinglist.setVisibility(View.VISIBLE);
		initWorkdays();
		setNextDayOrPreDay(-1);// 设置前一天按钮不可用
		// chooseTime =
		// dfTime.format(getTimeAfter(workDays.get(chooseDateIndex),
		// 0))+separator_date+dfTime.format(getTimeAfter(workDays.get(chooseDateIndex),60*2));
		initChooseTime();
		setChooseTimeText();
		adapter = new MeetingBookQueryResultAdapter(listRooms, mContext,
				listenerMeetingBook, chooseTime);

		regBroadcastReceiver();
		super.initData();

	}

	@Override
	protected void initViews() {
		initTopBar();

		initListView();

		startLocation();
		super.initViews();
	}

	/** 初始化ListView */
	private void initListView() {
		pageInput = new PageInput();
		pageInput.setPSIZE(PAGESIZE);
		pageInput.setPNO("1");

		refresh_listview.isPullUp(true);
		refresh_listview.isPullDown(true);

		refresh_listview.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
				// TODO Auto-generated method stub
				pageInput.setPNO("1");
				getMeetingList(false);
			}

			@Override
			public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
				// TODO Auto-generated method stub
				pageInput.pageNoAdd();
				getMeetingList(false);
			}
		});
	}

	private SimpleDateFormat df_server = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/** 是否为可以预订的时间 */
	private boolean isCanBookTime() {
		try {
			Date currentServerTime = DateFormatUtil.getServerTime(mContext);
			Calendar c = Calendar.getInstance();
			c.setTime(currentServerTime);
			long serverTime = currentServerTime.getTime();
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.add(Calendar.SECOND, 0);
			long minTime = c.getTimeInMillis();// 0:00
			c.set(Calendar.HOUR_OF_DAY, startHour);
			c.set(Calendar.MINUTE,startMin );
			c.add(Calendar.SECOND, 0);
			long maxTime = c.getTimeInMillis();// 8:19
			System.out.println("当前时间:" + df_server.format(currentServerTime));
			if (serverTime >= minTime && serverTime <= maxTime) {
				rl_mbf_meetinglist.setVisibility(View.GONE);
				emptyview_lv_empty.setRefreshEnable(true);
				emptyview_lv_empty.setVisibility(View.VISIBLE);
				refresh_listview.setVisibility(View.GONE);
				emptyview_lv_empty
						.setEmptyText(getResourceString(R.string.mbf_not_booktime));
				// EmeetingToast.show(mContext,getResourceString(R.string.mbf_not_booktime));
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		}
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				emptyview_lv_empty.setRefreshEnable(true);
			}
		};
	};

	/** 　获取会议列表 */
	private void getMeetingList(boolean isShowProgressDlg) {
		/** 如果不是可以预订的时间段,则不发起网络请求 */
		if (!isCanBookTime()) {
			emptyview_lv_empty.setRefreshEnable(false);
			handler.sendEmptyMessageDelayed(0, 500);
			return;
		}

		if (filterEntity == null) {
			filterEntity = new ScreeningCondition();
			filterEntity.setMRAIDS("");
			filterEntity.setPN("0");
			filterEntity.setPS("0");
			filterEntity.setPJS("0");
			filterEntity.setTVS("0");
		}

		if (TextUtils.isEmpty(filterEntity.getMRAIDS())) {
			if (currentAddress != null) {
				filterEntity.setMRAIDS(currentAddress.getID());
			}
		}

		if (TextUtils.isEmpty(filterEntity.getMRAIDS())) {
			filterPlace();
			return;
		}

		SimpleDateFormat df_server_short = new SimpleDateFormat("yyyy-MM-dd ");
		String queryDate = df_server.format(workDays.get(chooseDateIndex));
		String beginDate = "";
		String endDate = "";
		if (!TextUtils.isEmpty(chooseTime)) {
			String chooseHours[] = chooseTime.split(separator_date);
			String tmpQueryDate = df_server_short.format(workDays
					.get(chooseDateIndex));
			beginDate = tmpQueryDate + chooseHours[0] + ":00";
			endDate = tmpQueryDate + chooseHours[1] + ":00";
		}
		mService.getMeetingRoomList(pageInput, "", queryDate, beginDate,
				endDate, filterEntity, new HandlerGetMeetingRoomList(
						isShowProgressDlg));
	}

	private DlgLocationAndGetMeeting dlgLocation;

	private Handler handlerLocation = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				handleLocation();
			}
		};
	};

	private BaseAsyncHttpResponseHandler<GetNearParkAddressInfoResponse> handlerGetNearPark = new BaseAsyncHttpResponseHandler<GetNearParkAddressInfoResponse>() {
		/**
		 * @see cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler#onSuccessTrans(cn.com.zte.android.http.model.BaseHttpResponse)
		 */
		@Override
		public void onSuccessTrans(
				GetNearParkAddressInfoResponse responseModelVO) {

			List<DBMeetingRoomAddress> tmpList = responseModelVO.getD();
			if (tmpList != null && !tmpList.isEmpty()) {
				currentAddress = tmpList.get(0);// 定位获取到
			}
			onLocationFinish();

			super.onSuccessTrans(responseModelVO);
		}

		@Override
		public void onFailureTrans(
				GetNearParkAddressInfoResponse responseModelVO) {
			onLocationFinish();
			super.onFailureTrans(responseModelVO);
		}

		@Override
		public void onPopUpErrorDialog(String strTitle, String strCode,
				String strMsg) {
			onLocationFinish();
			super.onPopUpErrorDialog(strTitle, strCode, strMsg);
		}

	};

	/** 开始定位 */
	private void startLocation() {
		rl_mbf_meetinglist.setVisibility(View.VISIBLE);
		dlgLocation = new DlgLocationAndGetMeeting(mContext,
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						dlgLocation.dismiss();
						filterPlace();
					}
				});
		dlgLocation.show();
		new Thread() {
			@Override
			public void run() {
				SystemClock.sleep(3000);
				handlerLocation.sendEmptyMessage(1);
			};
		}.start();
	}

	/** 处理定位信息 */
	private void handleLocation() {
		MyLocation location = MyApplication.GetApp().getMyLocation();
		if (location.getLatitude() != 0)// 有经纬度信息
		{
			LogTools.i(TAG, "成功获取到经纬度信息");
			if (!dlgLocation.isShowing()) {
				dlgLocation.show();
			}
			// 先通过定位获取园区信息
			mService.getNearPark(
					location.getLongitude() + "," + location.getLatitude(),
					handlerGetNearPark);
		} else {
			LogTools.i(TAG, "未获取到经纬度信息");
			onLocationFinish();
			// 重新百度定位

		}

	}

	/**
	 * 该方法用于:经纬度定位结束后的处理
	 * 
	 * @param
	 * @return void
	 */
	private void onLocationFinish() {
		// 若定位没有获取到,则读取上次所在位置

		if (currentAddress == null) {
			currentAddress = EmeetingUtil.getUserLocation();
			LogTools.i(TAG, "尝试获取上次园区信息");
		}

		// 若之前都没有获取到位置信息,则需要用户选择地址
		if (currentAddress == null) {
			LogTools.i(TAG, "无上次园区信息,需要用户选择");
			if (!dlgLocation.isShowing()) {
				dlgLocation.show();
			}
			dlgLocation.setLocationFailed();
			return;
		}

		// 只要其中一种方式获取到园区信息,则关闭dlg,获取数据
		if (currentAddress != null) {
			LogTools.i(TAG, "园区信息已有,开始获取会议室列表");
			if (dlgLocation.isShowing()) {
				dlgLocation.dismiss();
			}
			EmeetingUtil.setUserLocation(currentAddress);
			tv_mbf_chooseplace.setText(currentAddress.getASC());
			pageInput.setPNO("1");
			getMeetingList(true);
		}
	}

	@SuppressLint("ResourceAsColor")
	private void initTopBar() {
		// topbar_meetingbook.HiddenView(TopBar.rightBtnLogo);
		topbar_meetingbook.setViewBackGround(TopBar.leftBtnLogo,
				R.drawable.icon_home_menu);
		topbar_meetingbook.setViewText(TopBar.titleLogo,
				getString(R.string.mbf_title));
		topbar_meetingbook.setViewTextColor(TopBar.titleLogo,
				getResourceColor(R.color.TEXTCOLOR_TOPBAR_TITLE_TEXT));

		topbar_meetingbook.setViewOnClickListener(TopBar.leftBtnLogo,
				new ButtonOnClick() {

					@Override
					public void onClick(View view) {
						// 返回菜单栏
						MainActivity mainActivity = (MainActivity) getActivity();
						mainActivity.showLeftView();
					}
				});
		topbar_meetingbook.setViewBackGround(TopBar.rightBtnLogo,
				R.drawable.ico_shake_meetinglist);
		topbar_meetingbook.setViewOnClickListener(TopBar.rightBtnLogo,
				new ButtonOnClick() {

					@Override
					public void onClick(View view) {
						// 返回菜单栏
						Intent it = new Intent(mContext, ShakeActivity.class);
						startActivity(it);
					}
				});
	}

	@Override
	protected void initViewEvents() {
		ll_mbf_filter_place.setOnClickListener(this);
		ll_mbf_filter_others.setOnClickListener(this);

		layout_timeChoose.setOnClickListener(this);

		rl_mbf_next_day.setOnClickListener(this);
		rl_mbf_previous_day.setOnClickListener(this);
		emptyview_lv_empty
				.setEmptyText(getResourceString(R.string.mbf_no_meetingrooms));
		emptyview_lv_empty.setOnRefreshClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pageInput.setPNO("1");
				emptyview_lv_empty.setRefreshEnable(false);
				getMeetingList(true);
			}
		});
		super.initViewEvents();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_mbf_currenttime:
			chooseTimeAndDate();
			break;
		case R.id.ll_mbf_filter_others: {
			{
				filterOthers();
			}
		}
			break;
		case R.id.ll_mbf_filter_place: {
			filterPlace();
		}
			break;
		case R.id.rl_mbf_next_day: {
			setNextDayOrPreDay(1);
			pageInput.setPNO("1");
			getMeetingList(true);
		}
			break;
		case R.id.rl_mbf_previous_day: {
			setNextDayOrPreDay(-1);
			pageInput.setPNO("1");
			getMeetingList(true);
		}
			break;
		default:
			break;
		}
	}

	/**
	 * 该方法用于:选择时间
	 * 
	 * @param
	 * @return void
	 */
	private void chooseTimeAndDate() {
		final DialogTopTimeChoose dialog = new DialogTopTimeChoose(mContext);

		dialog.setClickEventListener(new onClickEventListener() {
			@Override
			public void onSure(String time) {
				// TODO Auto-generated method stub
				Log.d(this.getClass().getSimpleName(), time);
				if (TextUtils.isEmpty(time) || time.equals("0")
						|| time.equals("-1")) {
					EmeetingToast
							.show(mContext,
									R.string.mbf_msg_endtime_need_bigger_than_starttime);
					return;
				}

				String strDate = "";
				String[] times = time.split(" ");
				if (times.length < 1) {
					EmeetingToast.show(mContext,
							R.string.mbf_msg_time_exception);
					return;
				} else {
					// 判断选择的时间是否大于当前时间
					{
						String strBeginTime = times[0] + " " + times[1];
						try {
							Date beginDateTime = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm").parse(strBeginTime);
							long currentTime = DateFormatUtil.getServerTime(
									mContext).getTime();
							if (currentTime > beginDateTime.getTime()) {
								EmeetingToast
										.show(mContext,
												R.string.mbf_msg_starttime_need_bigger_than_currenttime);
								return;
							}

						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					strDate = times[0];
					if (times.length == 2) {
						chooseTime = times[1];
					} else {
						chooseTime = times[1] + separator_date + times[2];
					}
				}

				try {
					Date tmpDate = new SimpleDateFormat("yyyy-MM-dd")
							.parse(strDate);
					strDate = dfDate.format(tmpDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				for (int i = 0; i < workDays.size(); i++) {
					if (dfDate.format(workDays.get(i)).equals(strDate)) {
						chooseDateIndex = i;
						break;
					}
				}
				setChooseTimeText();
				adapter.setChooseTime(chooseTime);
				setNextDayOrPreDay(0);
				pageInput.setPNO("1");
				getMeetingList(true);

				dialog.dismiss();
			}

			@Override
			public void noTime() {
				// TODO Auto-generated method stub
				dialog.dismiss();
				chooseTime = "";
				setChooseTimeText();
				// LogTools.i("chooseTime", "时段不限");
				pageInput.setPNO("1");
				getMeetingList(true);
			}

			@Override
			public void onCancel() {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		String tmpChooseTime = chooseTime;
		if (TextUtils.isEmpty(tmpChooseTime)) {
			tmpChooseTime = dfTime.format(getTimeAfter(
					workDays.get(chooseDateIndex), 0))
					+ separator_date
					+ dfTime.format(getTimeAfter(workDays.get(chooseDateIndex),
							60 * 2));
		}

		{
			SimpleDateFormat df_server_short = new SimpleDateFormat(
					"yyyy-MM-dd ");
			String queryDate = df_server.format(workDays.get(chooseDateIndex));
			String beginDate = "";
			String endDate = "";
			String chooseHours[] = tmpChooseTime.split(separator_date);
			String tmpQueryDate = df_server_short.format(workDays
					.get(chooseDateIndex));
			beginDate = chooseHours[0];
			endDate = chooseHours[1];
			String timeToSet = tmpQueryDate + beginDate + " " + endDate;
			dialog.setCurrentDateAndTime(timeToSet);
		}
		dialog.setNoTimeSelect(TextUtils.isEmpty(chooseTime));
		dialog.show();

	}

	/**
	 * 该方法用于:筛选地点
	 * 
	 * @param
	 * @return void
	 */
	private void filterPlace() {
		DlgPlaceChoose dlg = new DlgPlaceChoose(mContext, currentAddress,
				new ItemListener() {
					@Override
					public void onItemChoosed(int position,
							DBMeetingRoomAddress item) {

						if (currentAddress == null) {
							currentAddress = item;
							dlgFilterOther = null;
						} else {
							if (!currentAddress.getID().equals(item.getID())) {
								dlgFilterOther = null;
							}
							currentAddress = item;
						}
						EmeetingUtil.setUserLocation(currentAddress);

						if (filterEntity != null) {
							filterEntity.setMRAIDS(currentAddress.getID());
						}

						tv_mbf_chooseplace.setText(item.getASC());
						pageInput.setPNO("1");
						getMeetingList(true);
					}
				});
		dlg.show();
	}

	private DlgFilterMeeting dlgFilterOther;

	/**
	 * 该方法用于:筛选其他条件
	 * 
	 * @param
	 * @return void
	 */
	private void filterOthers() {
		// TODO Auto-generated method stub

		if (dlgFilterOther == null) {
			dlgFilterOther = new DlgFilterMeeting(mContext, currentAddress,
					new DlgFilterListener() {
						@Override
						public void onConfirm(ScreeningCondition o) {
							filterEntity = o;
							pageInput.setPNO("1");
							getMeetingList(true);
							// mService.getServerData(handlerServerTime);
						}
					});
		}
		if (!dlgFilterOther.isShowing()) {
			dlgFilterOther.show();
		}
	}

	private DlgMeetingBook dlgMeetingbook;

	/** 预订 */
	private void meetingBook(final MeetingRoomInfo item) {
		String timeToSet = "";
		if (!TextUtils.isEmpty(chooseTime)) {
			SimpleDateFormat df_server_short = new SimpleDateFormat(
					"yyyy-MM-dd ");
			// String queryDate =
			// df_server.format(workDays.get(chooseDateIndex));
			String beginDate = "";
			String endDate = "";
			String chooseHours[] = chooseTime.split(separator_date);
			String tmpQueryDate = df_server_short.format(workDays
					.get(chooseDateIndex));
			beginDate = chooseHours[0];
			endDate = chooseHours[1];
			timeToSet = tmpQueryDate + beginDate + " " + endDate;
			// 括号中的代码用来判断开始时间是否大于当前时间
			{
				try {
					Date beginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm")
							.parse(tmpQueryDate + chooseHours[0]);
					long currentTime = DateFormatUtil.getServerTime(mContext)
							.getTime();
					if (currentTime > beginTime.getTime()) {
						EmeetingToast
								.show(mContext,
										R.string.mbf_msg_starttime_need_bigger_than_currenttime_please_rechoose);
						return;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} else // chooseTime为空时表选择时段不限
		{
			// 括号中的代码用来判断开始时间是否大于当前时间

		}

		dlgMeetingbook = new DlgMeetingBook(mContext,
				workDays.get(chooseDateIndex), item, timeToSet,
				new DlgMeetingListener() {
					@Override
					public void onOkButtonClicked(Object o,
							ClockChooseView timeView) {

						if (TextUtils.isEmpty(timeView.getChooseTime())) {
							EmeetingToast
									.show(mContext,
											R.string.msg_choosed_time_not_serial_please_rechoose);
						} else if ("-1".equals(timeView.getChooseTime())) {
							EmeetingToast.show(mContext,
									R.string.msg_please_choose_periods_of_time);
						} else if ("0".equals(timeView.getChooseTime())) {
							EmeetingToast
									.show(mContext,
											R.string.msg_choosed_time_not_serial_please_rechoose);
						} else {
							// 确定之后,锁定会议室
							System.out.println(timeView.getChooseTime());
							if (TextUtils.isEmpty(timeView.getChooseTime())) {
								mService.lockMeetingRoom(item.getMRID(), "",
										"", handlerLockMeetingroom);
							} else {
								SimpleDateFormat df_server_short = new SimpleDateFormat(
										"yyyy-MM-dd ");
								String chooseHours[] = timeView.getChooseTime()
										.split(separator_date);
								String tmpQueryDate = df_server_short
										.format(workDays.get(chooseDateIndex));
								String beginDate = tmpQueryDate
										+ chooseHours[0] + ":00";
								String endDate = tmpQueryDate + chooseHours[1]
										+ ":00";

								{
									try {
										Date beginTime = new SimpleDateFormat(
												"yyyy-MM-dd HH:mm:ss")
												.parse(beginDate);
										long currentTime = DateFormatUtil
												.getServerTime(mContext)
												.getTime();
										if (currentTime > beginTime.getTime()) {
											// EmeetingToast.show(mContext,
											// "开始时间必须晚于服务器时间,请重新选择预订时间");
											EmeetingToast
													.show(mContext,
															R.string.mbf_msg_starttime_need_bigger_than_currenttime_please_rechoose);
											return;
										}
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								}
								mService.lockMeetingRoom(item.getMRID(),
										beginDate, endDate,
										handlerLockMeetingroom);
							}

							// Intent it = new Intent(mContext,
							// MeetingBookChooseOtherActivity.class);
							// startActivity(it);
							// dlgMeetingbook.dismiss();
						}
					}

					@Override
					public void onCancelClicked() {
						// TODO Auto-generated method stub

					}
				}, new TimeOutListener() {

					@Override
					public void onTimeOut() {
						// TODO Auto-generated method stub
						EmeetingToast.show(mContext,
								R.string.msg_meetingroom_timeout);
					}
				});
		// dlgMeetingbook.setDisClick(item);
		dlgMeetingbook.show();
	}

	/**
	 * 设置前一天或后一天
	 * */
	private void setNextDayOrPreDay(int nextOrPre) {
		if (chooseDateIndex + nextOrPre < workDays.size()
				&& chooseDateIndex + nextOrPre >= 0) {
			chooseDateIndex += nextOrPre;
		}

		rl_mbf_next_day.setEnabled(!(chooseDateIndex >= workDays.size() - 1));
		iv_mbf_next_day.setEnabled(!(chooseDateIndex >= workDays.size() - 1));
		tv_mbf_next_day.setEnabled(!(chooseDateIndex >= workDays.size() - 1));

		rl_mbf_previous_day.setEnabled(!(chooseDateIndex <= 0));
		iv_mbf_previous_day.setEnabled(!(chooseDateIndex <= 0));
		tv_mbf_previous_day.setEnabled(!(chooseDateIndex <= 0));

		setChooseTimeText();
	}

	/** 初始化三个工作日 */
	private void initWorkdays() {
		workDays.clear();
		int daysNum = new SharedPreferenceUtil(DataConst.APPCONFIG, mContext).getInt(DataConst.APPCONFIG_MAXDAY, 3);
		Calendar c = Calendar.getInstance();
		c.setTime(DateFormatUtil.getServerTime(mContext));
		/** 括号中的代码用来处理,当前时间在当日20点以后的情况 */
		{
			// 当日20:30以后,系统显示的默认预订时间为第二日的 8:00-10:00
			// 所以 时间数组第一个元素为第二日,而不再是当日
			if (c.get(Calendar.HOUR_OF_DAY) > 20
					|| (c.get(Calendar.HOUR_OF_DAY) == 20 && c
							.get(Calendar.MINUTE) >= 30)) {
				c.add(Calendar.DATE, 1);// 第一个元素为第二日
				c.set(Calendar.HOUR_OF_DAY, 0);// 小时数归0
				c.set(Calendar.MINUTE, 0);
				c.set(Calendar.SECOND, 0);
			}
		}

//		int dayOfweek = c.get(Calendar.DAY_OF_WEEK);
//		if (dayOfweek == Calendar.SUNDAY) {
//			daysNum = 4;
//		} else if (dayOfweek >= Calendar.MONDAY
//				&& dayOfweek <= Calendar.WEDNESDAY) {
//			daysNum = 3;
//		} else {
//			daysNum = 5;
//		}

		for (int i = 0; i < daysNum; i++) {
			Date tmpDate = c.getTime();
			workDays.add(tmpDate);
			c.add(Calendar.DATE, 1);
		}

	}

	/** 获取从指定日期过几天的工作日 */
	private Date getWorkDay(Date d, int afterdayNum) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DAY_OF_YEAR, afterdayNum);
		while (c.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY
				&& c.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
			return c.getTime();
		}
		return c.getTime();
	}

	/** 默认时间段的初始化 */
	private void initChooseTime() {
		chooseDateIndex = 0;
		chooseTime = dfTime.format(getTimeAfter(workDays.get(chooseDateIndex),
				0))
				+ separator_date
				+ dfTime.format(getTimeAfter(workDays.get(chooseDateIndex),
						60 * 2));
		if (workDays.get(chooseDateIndex).getHours() == 19) {
			chooseTime = dfTime.format(getTimeAfter(
					workDays.get(chooseDateIndex), 0))
					+ separator_date
					+ dfTime.format(getTimeAfter(workDays.get(chooseDateIndex),
							60));
		} else if (workDays.get(chooseDateIndex).getHours() == 20) {
			chooseTime = "20:30-21:00";
		} else if (workDays.get(chooseDateIndex).getHours() < 8) {
			chooseTime = "8:00-10:00";
		}
	}

	/**
	 * 获取指定时间 下一个整点后多少分钟的时间
	 * 
	 * @param d
	 *            开始时间
	 * @param minutes
	 *            间隔多少分钟
	 * */
	private Date getTimeAfter(Date d, int minutes) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.MINUTE, 0);// 分钟置零
		c.add(Calendar.HOUR, 1);
		c.add(Calendar.MINUTE, minutes);
		return c.getTime();
	}

	/** 判断今天 是否工作日 */
	private boolean isTodayWorkDay() {
		Calendar c = Calendar.getInstance();
		c.setTime(DateFormatUtil.getServerTime(mContext));
		if (c.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY
				&& c.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
			return true;
		} else {
			return false;
		}
	}

	/** 设置选择的时间文本 */
	private void setChooseTimeText() {
		tv_mbf_currenttime.setText(dfDate.format(workDays.get(chooseDateIndex))
				+ " " + chooseTime);
	}

	/** 获取会议室列表handler */
	private class HandlerGetMeetingRoomList extends
			BaseAsyncHttpResponseHandler<GetMeetingRoomBookingInfoResponse> {

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
		public void onSuccessTrans(GetMeetingRoomBookingInfoResponse res) {
			rl_mbf_meetinglist.setVisibility(View.GONE);

			refresh_listview.refreshFinish(PullToRefreshLayout.SUCCEED);
			refresh_listview.loadmoreFinish(PullToRefreshLayout.SUCCEED);

			if (res.getD() != null) {
				LogTools.i(TAG, "获取会议室列表");
				System.out.println(JsonUtil.toJson(res.getD()));
				List<MeetingRoomInfo> tmpList = res.getD();

				// listRooms.addAll(tmpList);
				// adapter.notifyDataSetChanged();

				if (pageInput.getPNO().equals("1")) {
					listRooms.clear();
					listRooms.addAll(tmpList);
					adapter = new MeetingBookQueryResultAdapter(listRooms,
							mContext, listenerMeetingBook, chooseTime);
					refresh_listview.setAdapter(adapter);
				} else {
					listRooms.addAll(tmpList);
					adapter.notifyDataSetChanged();
				}
				adapter.setChooseTime(chooseTime);

				showEmptyView();
			}
		};

		/**
		 * @see cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler#onFinish()
		 */
		@Override
		public void onFinish() {
			try {
				// TODO Auto-generated method stub
				super.onFinish();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/**
		 * @see cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler#onFailureTrans(cn.com.zte.android.http.model.BaseHttpResponse)
		 */
		@Override
		public void onFailureTrans(
				GetMeetingRoomBookingInfoResponse responseModelVO) {
			showEmptyView();
			refresh_listview.refreshFinish(PullToRefreshLayout.FAIL);
			refresh_listview.loadmoreFinish(PullToRefreshLayout.FAIL);
			if (responseModelVO != null
					&& !TextUtils.isEmpty(responseModelVO.getM())) {
				EmeetingToast.show(mContext, responseModelVO.getM());
			}
			rl_mbf_meetinglist.setVisibility(View.GONE);
			// super.onFailureTrans(responseModelVO);
		}

		@Override
		public void onPopUpErrorDialog(String strTitle, String strCode,
				String strMsg) {
			showEmptyView();
			refresh_listview.refreshFinish(PullToRefreshLayout.FAIL);
			refresh_listview.loadmoreFinish(PullToRefreshLayout.FAIL);
			if (!TextUtils.isEmpty(strMsg)) {
				EmeetingToast.show(mContext, strMsg);
			}
			rl_mbf_meetinglist.setVisibility(View.GONE);
		};

		/**
		 * @see cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler#onPopUpHttpErrorDialogPre(java.lang.String,
		 *      java.lang.String, java.lang.String)
		 */
		@Override
		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			showEmptyView();
			refresh_listview.refreshFinish(PullToRefreshLayout.FAIL);
			refresh_listview.loadmoreFinish(PullToRefreshLayout.FAIL);
			if (!TextUtils.isEmpty(strMsg)) {
				EmeetingToast.showHttp(mContext, strMsg);
			}
			rl_mbf_meetinglist.setVisibility(View.GONE);
			// super.onPopUpHttpErrorDialogPre(strTitle, strCode, strMsg);
		}

	};

	/** 锁定会议室handler */
	private BaseAsyncHttpResponseHandler<GetLockBookingMeetingRoomResponse> handlerLockMeetingroom = new BaseAsyncHttpResponseHandler<GetLockBookingMeetingRoomResponse>(
			true) {
		private String msg = "锁定会议室";

		@Override
		public void onSuccessTrans(GetLockBookingMeetingRoomResponse res) {
			if (res.getD() != null) {
				String meetingId = res.getD().get("EID");
				LogTools.i(TAG, msg);
				System.out.println(JsonUtil.toJson(res.getD()));
				Intent it = new Intent(mContext,
						MeetingBookChooseOtherActivity.class);
				it.putExtra("data", meetingId);
				startActivity(it);
				dlgMeetingbook.dismiss();
			}
		};

		public void onPopUpErrorDialog(String strTitle, String strCode,
				String strMsg) {
			if (!TextUtils.isEmpty(strMsg)) {
				EmeetingToast.show(mContext, strMsg);
			}
			// Intent it = new Intent(mContext,
			// MeetingBookChooseOtherActivity.class);
			// startActivity(it);
			// dlgMeetingbook.dismiss();
		};

		// public void onFailure(Throwable th, String content) {
		// EmeetingToast.show(mContext,msg+"失败");
		// // Intent it = new Intent(mContext,
		// MeetingBookChooseOtherActivity.class);
		// // startActivity(it);
		// dlgMeetingbook.dismiss();
		// };

		public void onFailureTrans(
				GetLockBookingMeetingRoomResponse responseModelVO) {
			if (responseModelVO != null
					&& !TextUtils.isEmpty(responseModelVO.getM())) {
				EmeetingToast.show(mContext, responseModelVO.getM(), 8000);
			}
		};

		/**
		 * @see cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler#onPopUpHttpErrorDialogPre(java.lang.String,
		 *      java.lang.String, java.lang.String)
		 */
		@Override
		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			if (!TextUtils.isEmpty(strMsg)) {
				EmeetingToast.showHttp(mContext, strMsg);
			}
			// super.onPopUpHttpErrorDialogPre(strTitle, strCode, strMsg);
		}
	};

	/** 获取最大可预订时间 */
	private BaseAsyncHttpResponseHandler<GetValidBookDateResponse> handlerMaxTime = new BaseAsyncHttpResponseHandler<GetValidBookDateResponse>() {
		@Override
		public void onSuccessTrans(GetValidBookDateResponse res) {
			if (res.getD() != null) {
				String maxDateString = res.getD().getMaxDate();
				String minDateString = res.getD().getMinDate();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

				try {
					Date maxDate, minDate;
					Calendar maxCalendar = Calendar.getInstance(),minCalendar = Calendar.getInstance();
					if (maxDateString != null) {
						maxDate = format.parse(maxDateString);
						maxCalendar.setTime(maxDate);
					}else {
						return;
					}
					if (minDateString != null) {
						minDate = format.parse(minDateString);
					}else {
						minDate = DateFormatUtil.getServerTime(mContext);
					}
					minCalendar.setTime(minDate);
					long diff = (maxCalendar.getTime().getTime()-minCalendar.getTime().getTime())
							/(1000*60*60*24);
					diff++;
					Log.d(TAG, "最多可预订天数 " + diff);
					if (diff>0) {
						new SharedPreferenceUtil(DataConst.APPCONFIG, mContext).setNameAndValueForInt(DataConst.APPCONFIG_MAXDAY, (int)diff);
						initWorkdays();
					}
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};
	};

	/** 显示是否有数据View */
	private void showEmptyView() {
		emptyview_lv_empty.setRefreshEnable(true);
		emptyview_lv_empty
				.setEmptyText(getResourceString(R.string.mbf_no_meetingrooms));
		if (adapter == null)
			return;
		if (adapter.getCount() > 0) {
			emptyview_lv_empty.setVisibility(View.GONE);
			refresh_listview.setVisibility(View.VISIBLE);
		} else {
			emptyview_lv_empty.setVisibility(View.VISIBLE);
			refresh_listview.setVisibility(View.GONE);
		}
	}

	private MyBroadcastReceiver mBroadcastReceiver;

	private void regBroadcastReceiver() {
		mBroadcastReceiver = new MyBroadcastReceiver();
		IntentFilter filter = new IntentFilter(DataConst.ACTION_HOMEMENU_SWITCH);
		mContext.registerReceiver(mBroadcastReceiver, filter);
	}

	@Override
	public void onDestroy() {
		if (mBroadcastReceiver != null) {
			mContext.unregisterReceiver(mBroadcastReceiver);
		}
		super.onDestroy();
	}

	/**
	 * 广播接收器
	 * */
	private class MyBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (DataConst.ACTION_HOMEMENU_SWITCH.equals(intent.getAction())) {
				dlgFilterOther = null;// 清空筛选条件
				filterEntity = null;// 清空筛选条件
			}
		}
	}

	/**
	 * @see android.support.v4.app.Fragment#onHiddenChanged(boolean)
	 */
	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (!hidden) {

			new Handler() {
				public void handleMessage(android.os.Message msg) {
					if (msg.what == 0) {
						initWorkdays();
						chooseDateIndex = 0;
						setNextDayOrPreDay(-1);// 设置前一天按钮不可用
						// chooseTime =
						// dfTime.format(getTimeAfter(workDays.get(chooseDateIndex),
						// 0))+separator_date+dfTime.format(getTimeAfter(workDays.get(chooseDateIndex),60*2));
						initChooseTime();
						setChooseTimeText();
						if (currentAddress != null) {
							getMeetingList(true);
						} else {
							startLocation();
						}
					}
				};
			}.sendEmptyMessageDelayed(0, 200);
		}
	}

}
