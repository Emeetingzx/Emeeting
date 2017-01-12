package cn.com.zte.emeeting.app.activity;

import java.util.ArrayList;
import java.util.List;

import cn.com.zte.emeeting.app.fragment.MeetingDetailControlFragment;
import roboguice.inject.InjectView;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import cn.com.zte.android.app.fragment.BaseFragment;
import cn.com.zte.android.common.util.JsonUtil;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.emeeting.app.adapter.MeetingFragmentPagerAdapter;
import cn.com.zte.emeeting.app.appservice.MeetingDetailService;
import cn.com.zte.emeeting.app.appservice.MyMeetingService;
import cn.com.zte.emeeting.app.base.activity.AppActivity;
import cn.com.zte.emeeting.app.fragment.MeetingDetailLeftFragment;
import cn.com.zte.emeeting.app.fragment.MeetingDetailRightFragment;
import cn.com.zte.emeeting.app.response.entity.MeetingInfo;
import cn.com.zte.emeeting.app.response.instrument.GetMeetingInfoResponse;
import cn.com.zte.emeeting.app.util.DataConst;
import cn.com.zte.emeeting.app.util.EmeetingToast;
import cn.com.zte.emeeting.app.util.LogTools;
import cn.com.zte.emeeting.app.views.TopBar;
import cn.com.zte.emeeting.app.views.TopBar.ButtonOnClick;
import cn.com.zte.mobileemeeting.R;

public class MeetingDetailActivity extends AppActivity implements OnClickListener {

	/** topbar */
	@InjectView(R.id.meeting_detailed_topbar)
	private TopBar topbar;
	
	/** 详细内容展示的Viewpager */
	@InjectView(R.id.vp_meetingdetail)
	private ViewPager vp_meetingdetail;
	
	/** 会议详情 选择标签 */
	@InjectView(R.id.ll_meetingdetail_choosebar_left)
	private View ll_meetingdetail_choosebar_left;
	
	/** 会议室详情 选择标签 */
	@InjectView(R.id.ll_meetingdetail_choosebar_right)
	private View ll_meetingdetail_choosebar_right;

	/** 会议控制 选择标签 */
	@InjectView(R.id.ll_meetingdetail_choosebar_control)
	private View ll_meetingdetail_choosebar_control;
	
	/** 左侧选择标签 倒三角 */
	@InjectView(R.id.iv_meetingdetail_left)
	private ImageView iv_meetingdetail_left;
	
	/** 右侧选择标签 倒三角 */
	@InjectView(R.id.iv_meetingdetail_right)
	private ImageView iv_meetingdetail_right;

	/** 右侧选择标签 倒三角 */
	@InjectView(R.id.iv_meetingdetail_control)
	private ImageView iv_meetingdetail_control;
	

	private MeetingInfo meetingEntity ;

	private Context mContext;
	private MyMeetingService mService;
	
	protected void initContentView() {
		super.initContentView();
		setContentView(R.layout.activity_meeting_detail);

	}

	@Override
	protected void initData() {
		mContext=this;
		mService = new MyMeetingService(mContext);
		super.initData();
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		try {
			if (intent!=null&&intent.getSerializableExtra("data")!=null) {
				meetingEntity = (MeetingInfo) intent.getSerializableExtra("data");
				new MeetingDetailService(mContext).getMeetingDetail(meetingEntity.getMID(), handlerGetMeetingDetail);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		super.initViews();
		initTopBar();
		meetingEntity = (MeetingInfo) getIntent().getSerializableExtra("data");
		new MeetingDetailService(mContext).getMeetingDetail(meetingEntity.getMID(), handlerGetMeetingDetail);
	}
	
	private MeetingFragmentPagerAdapter adapter;
	
	/** 初始化标签页*/
	private void initFragment()
	{
		//
		LogTools.i(TAG, "刷新:meetingEntity:"+meetingEntity);
		List<BaseFragment> list =new ArrayList<BaseFragment>();
		MeetingDetailLeftFragment leftFragment =new MeetingDetailLeftFragment();
		MeetingDetailRightFragment rightFragment =new MeetingDetailRightFragment();
		MeetingDetailControlFragment controlFragment =new MeetingDetailControlFragment();
		leftFragment.setMeetingInfo(meetingEntity);
		rightFragment.setMeetingInfo(meetingEntity);
		controlFragment.setMeetingInfo(meetingEntity);
		list.add(leftFragment);
		list.add(rightFragment);
		if (meetingEntity.getIEC()!=null&&meetingEntity.getIEC().equals(MeetingInfo.IECY)) {
			list.add(controlFragment);
			ll_meetingdetail_choosebar_control.setVisibility(View.VISIBLE);
		}else {
			ll_meetingdetail_choosebar_control.setVisibility(View.GONE);
		}
		if (adapter!=null) {
			adapter.setPagerItems(list);
			vp_meetingdetail.setAdapter(adapter);
		}else {
			adapter = new MeetingFragmentPagerAdapter(getSupportFragmentManager(), list);
			vp_meetingdetail.setAdapter(adapter);
		}
		
		vp_meetingdetail.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				setChooseLabel(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		vp_meetingdetail.setCurrentItem(0);
		setChooseLabel(0);
	}


	/** 初始化topbar */
	private void initTopBar() {
		topbar.HiddenView(TopBar.rightBtnLogo);
		topbar.setViewText(TopBar.titleLogo, getString(R.string.mymeeting_title_meetingdetail));
		topbar.setViewTextColor(TopBar.titleLogo, getResourceColor(R.color.TEXTCOLOR_TOPBAR_TITLE_TEXT));
		topbar.setViewTextColor(TopBar.titleLogo, getResourceColor(R.color.TEXTCOLOR_TOPBAR_TITLE_TEXT));
		topbar.setViewBackGround(TopBar.leftBtnLogo, R.drawable.back);
		topbar.setViewOnClickListener(TopBar.leftBtnLogo, new ButtonOnClick() {
			@Override
			public void onClick(View view) {
				MeetingDetailActivity.this.finish();
			}
		});
	}

	@Override
	protected void initViewEvents() {
		ll_meetingdetail_choosebar_left.setOnClickListener(this);
		ll_meetingdetail_choosebar_right.setOnClickListener(this);
		ll_meetingdetail_choosebar_control.setOnClickListener(this);
		super.initViewEvents();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_meetingdetail_choosebar_left:
			vp_meetingdetail.setCurrentItem(0);
			break;
		case R.id.ll_meetingdetail_choosebar_right:
			vp_meetingdetail.setCurrentItem(1);
			break;
		case R.id.ll_meetingdetail_choosebar_control:
			vp_meetingdetail.setCurrentItem(2);
			break;

		default:
			break;
		}
	}

	


	/** 设置选择标签*/
	private void setChooseLabel(int index){
		if(index==0)
		{
			ll_meetingdetail_choosebar_left.setSelected(true);
			ll_meetingdetail_choosebar_right.setSelected(false);
			ll_meetingdetail_choosebar_control.setSelected(false);
			iv_meetingdetail_left.setVisibility(View.VISIBLE);
			iv_meetingdetail_right.setVisibility(View.GONE);
			iv_meetingdetail_control.setVisibility(View.GONE);
		}else if(index==1)
		{
			ll_meetingdetail_choosebar_left.setSelected(false);
			ll_meetingdetail_choosebar_right.setSelected(true);
			ll_meetingdetail_choosebar_control.setSelected(false);
			iv_meetingdetail_left.setVisibility(View.GONE);
			iv_meetingdetail_right.setVisibility(View.VISIBLE);
			iv_meetingdetail_control.setVisibility(View.GONE);
		}else{
			ll_meetingdetail_choosebar_left.setSelected(false);
			ll_meetingdetail_choosebar_right.setSelected(false);
			ll_meetingdetail_choosebar_control.setSelected(true);
			iv_meetingdetail_left.setVisibility(View.GONE);
			iv_meetingdetail_right.setVisibility(View.GONE);
			iv_meetingdetail_control.setVisibility(View.VISIBLE);
		}
	}

	
	
	

	
	
	/** 获取会议详情handler*/
	private BaseAsyncHttpResponseHandler<GetMeetingInfoResponse> handlerGetMeetingDetail= new BaseAsyncHttpResponseHandler<GetMeetingInfoResponse>(true){
		@Override
		public void onSuccessTrans(GetMeetingInfoResponse res) {
			if(res.getD()!=null)
			{
				LogTools.i(TAG,"会议详情获取");
				System.out.println(JsonUtil.toJson(res.getD()));
				meetingEntity=res.getD();
				initFragment();
				//发送刷新详情广播
//				Intent it = new Intent(DataConst.ACTION_MEETING_DETAIL_REFRESH);
//				it.putExtra("data", meetingEntity);
//				sendBroadcast(it);
			}else
			{
				EmeetingToast.show(mContext,R.string.msg_request_failed);
			}
		};
//		
//		@Override
//		public void onFailure(Throwable th, String content) {
//			EmeetingToast.show(mContext, "请求失败");
//		};
		@Override
		public void onFailureTrans(GetMeetingInfoResponse responseModelVO) {
			if(responseModelVO!=null&&TextUtils.isEmpty(responseModelVO.getM()))
			{
				EmeetingToast.show(mContext, responseModelVO.getM());
			}
		};
		
		@Override
		public void onPopUpErrorDialog(String strTitle, String strCode, String strMsg) {
			if(!TextUtils.isEmpty(strMsg))
			{
				EmeetingToast.show(mContext, strMsg);
			}
		};
		
		@Override
		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode, String strMsg) {
			if(!TextUtils.isEmpty(strMsg))
			{
				EmeetingToast.showHttp(mContext, strMsg);
			}
		};
		
	};
	
	/** 刷新重置字fragment数据**/
	public void refreshChildFragment()
	{
		new MeetingDetailService(mContext).getMeetingDetail(meetingEntity.getMID(), handlerGetMeetingDetail);
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		finishSelf();
		super.onDestroy();
	}
	
	/**
	 * 关闭界面之前判断主页是否已经存在内存，如不存在，需要跳转到主页
	 */
	private void finishSelf(){
		Intent intent = new Intent(mContext,MainActivity.class);
		intent.putExtra(MainActivity.FLAG,MainActivity.MELOGO);
		startActivity(intent);
	}
	
}
