package cn.com.zte.emeeting.app.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.com.zte.android.app.fragment.BaseFragment;
import cn.com.zte.emeeting.app.adapter.MeetingFragmentPagerAdapter;
import cn.com.zte.emeeting.app.adapter.MeetingFragmentPagerAdapter.OnReloadListener;
import cn.com.zte.emeeting.app.fragment.MeetingDetailControlFragment;
import cn.com.zte.emeeting.app.fragment.MeetingDetailLeftFragment;
import cn.com.zte.emeeting.app.fragment.MeetingDetailRightFragment;
import cn.com.zte.emeeting.app.fragment.MeetingVedioDetailFragment;
import roboguice.inject.InjectView;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.emeeting.app.appservice.MeetingDetailService;
import cn.com.zte.emeeting.app.appservice.PhoneMeetingDetailService;
import cn.com.zte.emeeting.app.base.activity.AppActivity;
import cn.com.zte.emeeting.app.dialog.DlgCancelBookSuccess;
import cn.com.zte.emeeting.app.dialog.DlgConfirmBook;
import cn.com.zte.emeeting.app.dialog.DlgConfirmBook.OnConfirmDlgListener;
import cn.com.zte.emeeting.app.fragment.PhoneVideoFragment;
import cn.com.zte.emeeting.app.response.entity.MeetingInfo;
import cn.com.zte.emeeting.app.response.instrument.GetCancelMeetingRoomResponse;
import cn.com.zte.emeeting.app.response.instrument.GetEndMeetingRoomResponse;
import cn.com.zte.emeeting.app.response.instrument.GetMeetingInfoResponse;
import cn.com.zte.emeeting.app.util.DataConst;
import cn.com.zte.emeeting.app.util.LogTools;
import cn.com.zte.emeeting.app.views.CustomToast;
import cn.com.zte.emeeting.app.views.TopBar;
import cn.com.zte.emeeting.app.views.TopBar.ButtonOnClick;
import cn.com.zte.mobilebasedata.request.HttpUtil;
import cn.com.zte.mobileemeeting.R;

/**
 * 电话会议桥详细信息
 * 
 * @author liu.huanbo
 * 
 */
public class MeetingDetailMessageActivity extends AppActivity implements OnClickListener{

	/** 上下文 */
	private Context mContext;

	MeetingInfo info;
	/** topbar */
	@InjectView(R.id.phone_message_detail_topbar)
	private TopBar topbar;

	String id;

	/** 会议详情 选择标签 */
	@InjectView(R.id.ll_meetingdetail_choosebar_left)
	private View ll_meetingdetail_choosebar_left;

	/** 会议控制 选择标签 */
	@InjectView(R.id.ll_meetingdetail_choosebar_control)
	private View ll_meetingdetail_choosebar_control;
	
	/**
	 * 会控选择控件
	 */
	@InjectView(R.id.ll_meetingdetail_choosebar)
	private LinearLayout ll_meetingdetail_choosebar;

	/** 左侧选择标签 倒三角 */
	@InjectView(R.id.iv_meetingdetail_left)
	private ImageView iv_meetingdetail_left;

	/** 右侧选择标签 倒三角 */
	@InjectView(R.id.iv_meetingdetail_control)
	private ImageView iv_meetingdetail_control;

	/** 详细内容展示的Viewpager */
	@InjectView(R.id.vp_meetingdetail)
	private ViewPager vp_meetingdetail;
	
	
	private MeetingFragmentPagerAdapter adapter;
	
	@Override
	protected void initContentView() {
		super.initContentView();
		mContext = this;
		setContentView(R.layout.activity_phone_detail_message);
	}

	@Override
	protected void initData() {
		super.initData();
		try {
			id = getIntent().getStringExtra("MID");
		} catch (Exception e) {
			id = "";
			e.printStackTrace();
		}

		if (id != null && !id.equals("")) {

			new PhoneMeetingDetailService(mContext).getMeetingDetail(id,
					handler);
		} else {
			Toast.makeText(mContext, getString(R.string.tv_message_detail_no_id), Toast.LENGTH_SHORT)
					.show();
		}

	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		Log.d(TAG, "onNewIntent");
		if (intent!=null) {
			try {
				id = intent.getStringExtra("MID");
			} catch (Exception e) {
				id = "";
				e.printStackTrace();
			}

			if (id != null && !id.equals("")) {
				new PhoneMeetingDetailService(mContext).getMeetingDetail(id,
						handler);
			} else {
				Toast.makeText(mContext, getString(R.string.tv_message_detail_no_id), Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	

	/** 初始化标签页*/
	private void initFragment()
	{
		List<BaseFragment> list = new ArrayList<BaseFragment>();
		MeetingVedioDetailFragment leftFragment =new MeetingVedioDetailFragment();
		MeetingDetailControlFragment controlFragment =new MeetingDetailControlFragment();
		leftFragment.setMeetingInfo(info);
		controlFragment.setMeetingInfo(info);
		list.add(leftFragment);
		if (info.getIEC()!=null&&info.getIEC().equals(MeetingInfo.IECY)) {
			list.add(controlFragment);
			ll_meetingdetail_choosebar.setVisibility(View.VISIBLE);
		}else {
			ll_meetingdetail_choosebar.setVisibility(View.GONE);
		}
		if (adapter!=null) {
			adapter.setPagerItems(list);
			vp_meetingdetail.setAdapter(adapter);
		}else {
			adapter = new MeetingFragmentPagerAdapter(getSupportFragmentManager(), list);
			vp_meetingdetail.setAdapter(adapter);
		}
		vp_meetingdetail.setCurrentItem(0);
		setChooseLabel(0);
		Log.d(TAG, info.getMID());
		Log.d(TAG, ((MeetingVedioDetailFragment)list.get(0)).getInfo().getMID());
	}

	/** 设置选择标签*/
	private void setChooseLabel(int index){
		if(index==0)
		{
			ll_meetingdetail_choosebar_left.setSelected(true);
			ll_meetingdetail_choosebar_control.setSelected(false);
			iv_meetingdetail_left.setVisibility(View.VISIBLE);
			iv_meetingdetail_control.setVisibility(View.GONE);
		}else{
			ll_meetingdetail_choosebar_left.setSelected(false);
			ll_meetingdetail_choosebar_control.setSelected(true);
			iv_meetingdetail_left.setVisibility(View.GONE);
			iv_meetingdetail_control.setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void initViewEvents() {
		super.initViewEvents();
		vp_meetingdetail.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

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
		ll_meetingdetail_choosebar_control.setOnClickListener(this);
		ll_meetingdetail_choosebar_left.setOnClickListener(this);
	}

	@Override
	protected void initViews() {
		super.initViews();
		initTopBar();
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
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

	/** 初始化TopBar */
	private void initTopBar() {
		topbar.setViewText(TopBar.titleLogo, getString(R.string.tv_message_detail_meeting_detail));
		topbar.setViewBackGround(TopBar.leftBtnLogo, R.drawable.ico_g_back);
		topbar.setViewOnClickListener(TopBar.leftBtnLogo, new ButtonOnClick() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}



	/** 获取会议详细信息接口 */
	BaseAsyncHttpResponseHandler<GetMeetingInfoResponse> handler = new BaseAsyncHttpResponseHandler<GetMeetingInfoResponse>(
			true) {

		// 成功
		public void onSuccessTrans(GetMeetingInfoResponse responseModelVO) {

			if (responseModelVO .getD()!= null) {
				info = responseModelVO.getD();
				initFragment();
			}else {
				CustomToast.show(mContext, R.string.get_meeting_fail);
			}
		};

		public void onFailureTrans(GetMeetingInfoResponse responseModelVO) {

			if (responseModelVO != null && responseModelVO.getM() != null
					&& !responseModelVO.getM().equals("")) {
				Toast.makeText(mContext, responseModelVO.getM(),
						Toast.LENGTH_LONG).show();

			}

		};

		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {

			Toast.makeText(mContext, HttpUtil.SERVER_REQUEST_FAIL,
					Toast.LENGTH_LONG).show();

		};

	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ll_meetingdetail_choosebar_left:
				vp_meetingdetail.setCurrentItem(0);
				break;
			case R.id.ll_meetingdetail_choosebar_control:
				vp_meetingdetail.setCurrentItem(1);
				break;

			default:
				break;
		}
	}
}
