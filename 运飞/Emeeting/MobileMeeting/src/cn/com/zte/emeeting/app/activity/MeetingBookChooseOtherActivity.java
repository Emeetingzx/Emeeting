/**
 * 
 */
package cn.com.zte.emeeting.app.activity;

import cn.com.zte.emeeting.app.base.activity.AppActivity;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import cn.com.zte.android.app.activity.BaseActivity;
import cn.com.zte.android.common.util.JsonUtil;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.emeeting.app.appservice.MeetingBookChooseOthersService;
import cn.com.zte.emeeting.app.appservice.MeetingBookService;
import cn.com.zte.emeeting.app.base.MyApplication;
import cn.com.zte.emeeting.app.dialog.DlgBookSuccess;
import cn.com.zte.emeeting.app.dialog.DlgChooseList;
import cn.com.zte.emeeting.app.dialog.DlgBookSuccess.OnBookSuccessDlgListener;
import cn.com.zte.emeeting.app.dialog.DlgChooseList.ItemListener;
import cn.com.zte.emeeting.app.response.instrument.GetLockBookingMeetingRoomResponse;
import cn.com.zte.emeeting.app.response.instrument.GetSubmitBookingMeetingRoomResponse;
import cn.com.zte.emeeting.app.util.DataConst;
import cn.com.zte.emeeting.app.util.EmeetingToast;
import cn.com.zte.emeeting.app.util.LogTools;
import cn.com.zte.emeeting.app.views.TopBar;
import cn.com.zte.emeeting.app.views.TopBar.ButtonOnClick;
import cn.com.zte.mobileemeeting.R;

/**
 * 该类用于
 * @author wf
 */
public class MeetingBookChooseOtherActivity extends AppActivity implements OnClickListener {
	private static final String TAG="MeetingBookChooseOtherActivity";
	
	@InjectView(R.id.btn_ok_meetingbook_chooseother)
	private Button btn_ok_meetingbook_chooseother;
	
	@InjectView(R.id.topbar_meetingbook_choose_other)
	private TopBar topbar_meetingbook_choose_other;
	
	/** 参会领导所在布局*/
	@InjectView(R.id.rl_choose_leader)
	private View rl_choose_leader;
	
	/** 会议级别所在布局*/
	@InjectView(R.id.rl_choose_meetinglevel)
	private View rl_choose_meetinglevel;
	
	/** 会议级别显示的textView*/
	@InjectView(R.id.tv_choose_meetinglevel)
	private TextView tv_choose_meetinglevel;
	
	/** 会议名称显示的textView*/
	@InjectView(R.id.tv_chooseother_meetingname)
	private TextView tv_chooseother_meetingname;
	
	/** 参会领导显示的textView*/
	@InjectView(R.id.tv_choose_leader)
	private TextView tv_choose_leader;
	
	private Context mContext;
	
	private MeetingBookService mService;
	
	private String meetingId="";
	/**
	 * @see cn.com.zte.android.app.activity.BaseActivity#initContentView()
	 */
	@Override
	protected void initContentView() {
		setContentView(R.layout.activity_meetingbook_choose_other);
		super.initContentView();
	}
	
	/**
	 * @see cn.com.zte.android.app.activity.BaseActivity#initData()
	 */
	@Override
	protected void initData() {
		mContext=this;
		mService = new MeetingBookService(mContext);
		meetingId= getIntent().getStringExtra("data");
		super.initData();
	}
	
	/**
	 * @see cn.com.zte.android.app.activity.BaseActivity#initViews()
	 */
	@Override
	protected void initViews() {
		initTopBar();
		super.initViews();
	}
	
	
	/** 初始化topbar */
	private void initTopBar() {
		topbar_meetingbook_choose_other.HiddenView(TopBar.rightBtnLogo);
		topbar_meetingbook_choose_other.setViewText(TopBar.titleLogo, getString(R.string.mbf_title_meetingbook));
		topbar_meetingbook_choose_other.setViewTextColor(TopBar.titleLogo, getResourceColor(R.color.TEXTCOLOR_TOPBAR_TITLE_TEXT));
		topbar_meetingbook_choose_other.setViewTextColor(TopBar.titleLogo, getResourceColor(R.color.TEXTCOLOR_TOPBAR_TITLE_TEXT));
		topbar_meetingbook_choose_other.setViewBackGround(TopBar.leftBtnLogo, R.drawable.back);
		topbar_meetingbook_choose_other.setViewOnClickListener(TopBar.leftBtnLogo, new ButtonOnClick() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
	}
	
	/**
	 * @see cn.com.zte.android.app.activity.BaseActivity#initViewEvents()
	 */
	@Override
	protected void initViewEvents() {
		btn_ok_meetingbook_chooseother.setOnClickListener(this);
		rl_choose_leader.setOnClickListener(this);
		rl_choose_meetinglevel.setOnClickListener(this);
		super.initViewEvents();
	}

	/**
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_ok_meetingbook_chooseother:
			if(checkInput())
			{
			bookMeeting();
			}
			break;
		case R.id.rl_choose_leader:
			chooseLeader();
			break;
		case R.id.rl_choose_meetinglevel:
			chooseMeetingLevel();
			break;

		default:
			break;
		}
	}

	/**
	 * 该方法用于:检查输入
	 * @param @return
	 * @return boolean
	 */
	private boolean checkInput() {
		
		if(tv_chooseother_meetingname.getText().toString().trim().length()<1)
		{
			EmeetingToast.show(mContext, R.string.msg_please_input_meetingname);
			return false;
		}
		
		return true;
	}

	/**
	 * 该方法用于:选择会议级别
	 * @param 
	 * @return void
	 */
	private void chooseMeetingLevel() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 该方法用于:预定会议
	 * @param 
	 * @return void
	 */
	private void bookMeeting() {
		if(!TextUtils.isEmpty(meetingId))
		{
			mService.submitBookMeeting(meetingId, choosedLeader, "", tv_chooseother_meetingname.getText().toString().trim(),handlerBookMeeting);
		}else
		{
			EmeetingToast.show(mContext,R.string.msg_please_lockmeetingroom_again);
		}
	}
	
	/** 当会议预定成功后执行*/
	private void onBookMeetingSuccessed()
	{
		DlgBookSuccess dlg = new DlgBookSuccess(mContext, null, new OnBookSuccessDlgListener() {
			
			@Override
			public void onRightClicked() {
				
				
				for(Activity a:MyApplication.GetApp().getActivityList())
				{
					if(a instanceof FindMeetingRoomActivity|| a instanceof ShakeActivity)
					{
						a.finish();
					}
				}
				
				finish();
				Intent it = new Intent(DataConst.ACTION_HOMEMENU_SWITCH);
				it.putExtra("data",MainActivity.MELOGO);
				sendBroadcast(it);
				
//				Intent it_refresh = new Intent(DataConst.ACTION_MYMEETING_REFRESH);
//				sendBroadcast(it_refresh);
			}
			
			@Override
			public void onLeftClicked() {
				
				for(Activity a:MyApplication.GetApp().getActivityList())
				{
					if(a instanceof FindMeetingRoomActivity)
					{
						a.finish();
					}
				}
				
				Intent it = new Intent(DataConst.ACTION_HOMEMENU_SWITCH);
				it.putExtra("data",MainActivity.GMLOGO);
				it.putExtra("isShake",getIntent().getBooleanExtra("isShake",false));
				sendBroadcast(it);
				
				
//				Intent it_refresh = new Intent(DataConst.ACTION_MYMEETING_REFRESH);
//				sendBroadcast(it_refresh);
				
				finish();
			}
		});
		dlg.show();
	}
	
	/** 选择的参会领导*/
	private String choosedLeader = "4";
	
	/** 选择领导*/
	private void chooseLeader()
	{
		DlgChooseList dlg = new DlgChooseList(mContext, mService.getListLeader(),new ItemListener() {
			@Override
			public void onItemChoosed(int position, String item) {
				tv_choose_leader.setText(item);
				choosedLeader = (position+1)+"";
				if(position==0)
				{
					tv_choose_meetinglevel.setText(getString(R.string.mymeeting_meetinglevel_A));
				}else if(position == 1){
					tv_choose_meetinglevel.setText(getString(R.string.mymeeting_meetinglevel_B));
				}else {
					tv_choose_meetinglevel.setText(getString(R.string.mymeeting_meetinglevel_C));
				}
			}
		});
		dlg.setChooseStr(tv_choose_leader.getText().toString().trim());
		dlg.show();
	}
	
//	A级（二层领导）、B级（三层）、C级（四层领导和其他）
	
	
	
	/** 预定会议handler*/
	private BaseAsyncHttpResponseHandler<GetSubmitBookingMeetingRoomResponse> handlerBookMeeting= new BaseAsyncHttpResponseHandler<GetSubmitBookingMeetingRoomResponse>(true){
		@Override
		public void onSuccessTrans(GetSubmitBookingMeetingRoomResponse res) {
			if(res.getD()!=null)
			{
				LogTools.i(TAG,"提交会议预定");
				System.out.println(JsonUtil.toJson(res.getD()));
				onBookMeetingSuccessed();
			}else
			{
//				EmeetingToast.show(mContext, "提交会议预定失败");
			}
		};
		
//		@Override
//		public void onPopUpErrorDialog(String strTitle, String strCode, String strMsg) {
//			if(!TextUtils.isEmpty(strMsg))
//			{
//			EmeetingToast.show(mContext,strMsg);
//			}
//		};
		@Override
		public void onFailureTrans(GetSubmitBookingMeetingRoomResponse responseModelVO) {
			try {
				if(responseModelVO!=null&&!TextUtils.isEmpty(responseModelVO.getM()))
				{
					EmeetingToast.show(mContext, responseModelVO.getM());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
}
