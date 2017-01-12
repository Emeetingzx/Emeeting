package cn.com.zte.emeeting.app.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ab.util.AbImageUtil;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import roboguice.inject.InjectView;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.android.securityauth.model.UserInfo;
import cn.com.zte.emeeting.app.adapter.CheckInAdapter;
import cn.com.zte.emeeting.app.appservice.CheckedInTableService;
import cn.com.zte.emeeting.app.appservice.WelComeService;
import cn.com.zte.emeeting.app.base.activity.AppActivity;
import cn.com.zte.emeeting.app.response.entity.MeetingAttendanceInfo;
import cn.com.zte.emeeting.app.response.instrument.GetMeetingAttendanceInfoResponse;
import cn.com.zte.emeeting.app.util.DateFormatUtil;
import cn.com.zte.emeeting.app.util.EmeetingToast;
import cn.com.zte.emeeting.app.views.MyListView;
import cn.com.zte.emeeting.app.views.TopBar;
import cn.com.zte.emeeting.app.views.TopBar.ButtonOnClick;
import cn.com.zte.mobilebasedata.request.HttpUtil;
import cn.com.zte.mobileemeeting.R;

public class CheckInActivity extends AppActivity implements OnClickListener {
	
	private Context context = CheckInActivity.this;

	@InjectView(R.id.topbar_checkin)
	private TopBar topbar;

	/** 组织者布局 */
	@InjectView(R.id.rel_organizer)
	private RelativeLayout rel_organizer;

	/** 组织者姓名 */
	@InjectView(R.id.tv_organizer_name)
	private TextView tv_organizer_name;

	/** 组织者头像 */
	@InjectView(R.id.iv_organizer_head)
	private ImageView iv_organizer_head;

	/** 已签到布局 */
	@InjectView(R.id.rel_checkedin)
	private RelativeLayout rel_checkedin;

	/** 已签到人数目 */
	@InjectView(R.id.tv_checkedin_tip)
	private TextView tv_checkedin_tip;

	/** 已签到列表 */
	@InjectView(R.id.lv_checkedin_list)
	private MyListView lv_checkedin_list;

	/** 未签到布局 */
	@InjectView(R.id.rel_not_checkedin)
	private RelativeLayout rel_not_checkedin;

	/** 未签到人数目 */
	@InjectView(R.id.tv_not_checkin_tip)
	private TextView tv_not_checkin_tip;

	/** 未签到列表 */
	@InjectView(R.id.lv_not_checkin_list)
	private MyListView lv_not_checkin_list;

	/** 会议未开始 */
	@InjectView(R.id.iv_MeetingNotStart)
	private ImageView iv_MeetingNotStart;

	/** 已经签到 */
	private List<MeetingAttendanceInfo> checkedinList = new ArrayList<MeetingAttendanceInfo>();

	/** 未签到 */
	private List<MeetingAttendanceInfo> notCheckinList = new ArrayList<MeetingAttendanceInfo>();

	/**
	 * 总签到人数集合
	 */
	private List<MeetingAttendanceInfo> totalCheckedList = new ArrayList<MeetingAttendanceInfo>();

	/** 已签到人员的适配器 */
	private CheckInAdapter checkedInAdapter;

	/** 未签到人员的适配器 */
	private CheckInAdapter notCheckInAdapter;

	/**
	 * 签到列表的显示隐藏
	 */
	private boolean isShowChecked = true;
	/**
	 * 未签到列表的显示隐藏
	 */
	private boolean isShowUnChecked = true;

	/**
	 * 签到表逻辑处理类
	 */
	private CheckedInTableService checkedInTableService;

	/**
	 * 已签到人数
	 */
	private String hadCheckedNum = "0";

	/**
	 * 未签到人数
	 */
	private String unCheckedNum = "0";

	/**
	 * 应签到总人数
	 */
	private String totalCheckedNum = "0";
	
	/**
	 * 会议id
	 */
	private String meetindId = null;
	/**
	 * 组织者姓名
	 */
	private String origanizerName = null;
	
	@InjectView(R.id.scrollview)
	private ScrollView scrollView;
	
	/**
	 * 会议开始时间
	 */
	private String beginTime = null;
	/**
	 * 会议结束时间
	 */
	private String endTime = null;
	
	private HttpUtils httpUtils = new HttpUtils();
	
	private BitmapUtils bitmapUtils; 

	
	@Override
	protected void initContentView() {
		super.initContentView();
		setContentView(R.layout.activity_check_in);
		bitmapUtils = new BitmapUtils(context);
		bitmapUtils.configDiskCacheEnabled(true).configMemoryCacheEnabled(true).configThreadPoolSize(5).configDefaultLoadFailedImage(R.drawable.icon_default_user).configDefaultLoadingImage(R.drawable.icon_default_user);
		checkedInTableService = new CheckedInTableService(mContext);
		initTopBar();
		
		Intent intent = getIntent();
		meetindId = intent.getStringExtra("MeetingId");
		origanizerName = intent.getStringExtra("organizer_name");
	 	beginTime = intent.getStringExtra("BD");
	 	endTime = intent.getStringExtra("ED");
	 	
	}

	private void initTopBar() {
		topbar.setViewBackGround(TopBar.leftBtnLogo, R.drawable.back);
		topbar.setViewOnClickListener(TopBar.leftBtnLogo, new ButtonOnClick() {

			@Override
			public void onClick(View view) {
				finish();
			}
		});

		topbar.setViewText(TopBar.titleLogo,
				getResourceString(R.string.checkInTable));
		topbar.setViewTextColor(TopBar.titleLogo,
				getResourceColor(R.color.TEXTCOLOR_TOPBAR_TITLE_TEXT));
	}

	@Override
	protected void initData() {
		super.initData();
		if (beginTime != null && endTime != null) {
			
			if (!isInMeeting(beginTime, endTime)) {
				rel_organizer.setVisibility(View.GONE);
				scrollView.setVisibility(View.GONE);
				iv_MeetingNotStart.setVisibility(View.VISIBLE);
			}
		}
		
		if (meetindId != null) {
			
			checkedInTableService.getCheckedTableInfo(meetindId,
				checkedInTableHandler);
		}
		
		initCheckedinList();
		initNotCheckinList();
		initOrganizerInfo();
	}
	
	/**
	 * 会议是否已经开始
	 */
	private boolean isInMeeting(String beginTime,String endTime){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Log.d("zl", "beginTime:"+beginTime);
		Log.d("zl", "endTime："+endTime);
		try {
			long begin = sdf.parse(beginTime).getTime();
			long end = sdf.parse(endTime).getTime();
			Date serverTime = DateFormatUtil.getServerTime(mContext);
			String servertime = sdf.format(serverTime);
			long server = sdf.parse(servertime).getTime();
			Log.d("zl", "beginlong:"+begin);
			Log.d("zl", "endlong："+end);
			Log.d("zl", "serverlong:"+server);
			if (server >= begin && server <= end) {
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	/**
	 * 初始化组织者信息
	 */
	private void initOrganizerInfo() {
		if (origanizerName != null) {
			tv_organizer_name.setText(origanizerName);
		}
		UserInfo userInfo = new WelComeService(mContext).getUserInfo();
		if (userInfo.getUID() != null) {
//			if (true) {
			httpUtils.send(HttpMethod.GET, HttpUtil.url_moa_facephoto + "?userId=" +userInfo.getUID(), new RequestCallBack<String>() {
//				httpUtils.send(HttpMethod.GET, HttpUtil.url_moa_facephoto + "?userId=" +"10103749", new RequestCallBack<String>() {

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					Log.d("zl", "获取组织者头像地址失败");
					iv_organizer_head.setImageResource(R.drawable.icon_default_user);
				}

				@Override
				public void onSuccess(ResponseInfo<String> arg0) {
					Log.d("zl", "组织者头像地址："+arg0.result);
					bitmapUtils.display(iv_organizer_head, arg0.result, new BitmapLoadCallBack<View>() {
						
						@Override
						public void onLoadCompleted(View arg0,
								String arg1, Bitmap arg2,
								BitmapDisplayConfig arg3,
								BitmapLoadFrom arg4) {
							Log.d("zl", "组织者头像："+arg2.toString());
							Bitmap roundBitmap = AbImageUtil.toRoundBitmap(arg2);
							iv_organizer_head.setImageBitmap(roundBitmap);
						}

						@Override
						public void onLoadFailed(View arg0,
								String arg1, Drawable arg2) {
							iv_organizer_head.setImageResource(R.drawable.icon_default_user);
							Log.d("zl","头像加载失败"+arg1);
						}
					});
				}
			});
		}
	}


	/***
	 * 获取签到表信息handler
	 */
	private BaseAsyncHttpResponseHandler<GetMeetingAttendanceInfoResponse> checkedInTableHandler = new BaseAsyncHttpResponseHandler<GetMeetingAttendanceInfoResponse>() {

		@Override
		public void onFailureTrans(
				GetMeetingAttendanceInfoResponse responseModelVO) {
			if (responseModelVO != null
					&& !TextUtils.isEmpty(responseModelVO.getM())) {

				EmeetingToast.show(mContext, responseModelVO.getM());
				scrollView.setVisibility(View.GONE);
			}
		}

		@Override
		public void onPopUpErrorDialog(String strTitle, String strCode,
				String strMsg) {
			if (!TextUtils.isEmpty(strMsg)) {
				EmeetingToast.show(mContext, strMsg);
				scrollView.setVisibility(View.GONE);
			}
		}

		@Override
		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			if (!TextUtils.isEmpty(strMsg)) {
				EmeetingToast.show(mContext, strMsg);
				scrollView.setVisibility(View.GONE);
			}
		}

		@Override
		public void onSuccessTrans(
				GetMeetingAttendanceInfoResponse responseModelVO) {
			if (responseModelVO.getD() != null) {
				Log.d("zl", "获取签到表信息成功");

				for (int i = 0; i < responseModelVO.getD().size(); i++) {
					Log.d("zl", responseModelVO.getD().get(i).toString());
				}

				totalCheckedList = responseModelVO.getD();
				for (int j = 0; j < totalCheckedList.size(); j++) {
					if (totalCheckedList.get(j).getST().equals("1")) {
						checkedinList.add(totalCheckedList.get(j));
					} else {
						notCheckinList.add(totalCheckedList.get(j));
					}
				}
				hadCheckedNum = checkedinList.size() + "";
				unCheckedNum = notCheckinList.size() + "";
				totalCheckedNum = totalCheckedList.size() + "";
				tv_checkedin_tip.setText(getResourceString(R.string.hadCheckedIn)
						+ hadCheckedNum + getResourceString(R.string.separator)
						+ totalCheckedNum + getResourceString(R.string.ge));
				tv_not_checkin_tip
						.setText(getResourceString(R.string.uncheckIn)
								+ unCheckedNum
								+ getResourceString(R.string.separator)
								+ totalCheckedNum
								+ getResourceString(R.string.ge));
				
				checkedInAdapter.notifyDataSetChanged();
				notCheckInAdapter.notifyDataSetChanged();
			} else {
				EmeetingToast.show(mContext,
						getResourceString(R.string.msg_request_failed));
				scrollView.setVisibility(View.GONE);
			}
		}

	};


	@Override
	protected void initViews() {
		super.initViews();
	}

	@Override
	protected void initViewEvents() {
		super.initViewEvents();
		tv_checkedin_tip.setOnClickListener(this);
		tv_not_checkin_tip.setOnClickListener(this);
	}

	/**
	 * 初始化已经签到人员列表
	 */
	private void initCheckedinList() {
		checkedInAdapter = new CheckInAdapter(context, checkedinList);
		lv_checkedin_list.setAdapter(checkedInAdapter);
		checkedInAdapter.notifyDataSetChanged();
	}

	/**
	 * 初始化未签到人员列表
	 */
	private void initNotCheckinList() {
		notCheckInAdapter = new CheckInAdapter(context, notCheckinList);
		lv_not_checkin_list.setAdapter(notCheckInAdapter);
		notCheckInAdapter.notifyDataSetChanged();
	}

	/**
	 * 签到详情的展示隐藏
	 * 
	 * @param isShow
	 *            显示与否
	 * @param isChecked
	 *            签到的或未签到的
	 */
	private void isShowCheckListView(boolean isShow, boolean ischecked) {
		if (isShow) {
			if (ischecked) {
				lv_checkedin_list.setVisibility(View.VISIBLE);
			} else {
				lv_not_checkin_list.setVisibility(View.VISIBLE);
			}
		} else {
			if (ischecked) {
				lv_checkedin_list.setVisibility(View.GONE);
			} else {
				lv_not_checkin_list.setVisibility(View.GONE);
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_checkedin_tip:
			isShowCheckListView(isShowChecked, true);
			isShowChecked = !isShowChecked;
			break;
		case R.id.tv_not_checkin_tip:
			isShowCheckListView(isShowUnChecked, false);
			isShowUnChecked = !isShowUnChecked;
			break;
		default:
			break;
		}
	}
}
