package cn.com.zte.emeeting.app.activity;

import cn.com.zte.emeeting.app.views.scan.CaptureFragment;
import roboguice.inject.InjectView;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import cn.com.zte.android.app.fragment.BaseFragment;
import cn.com.zte.emeeting.app.base.activity.AppActivity;
import cn.com.zte.emeeting.app.fragment.HomeMenuFragment;
import cn.com.zte.emeeting.app.fragment.MeetingBookFragment;
import cn.com.zte.emeeting.app.fragment.MyMeetingFragment;
import cn.com.zte.emeeting.app.fragment.PhoneVideoFragment;
import cn.com.zte.emeeting.app.fragment.SetFragment;
import cn.com.zte.emeeting.app.fragment.ShakeFragment;
import cn.com.zte.emeeting.app.fragment.ValueAddServiceFragment;
import cn.com.zte.emeeting.app.util.DataConst;
import cn.com.zte.emeeting.app.views.LateralMoveView;
import cn.com.zte.mobileemeeting.R;

/**
 * 首页界面
 * 
 * @author LangK
 * */
public class MainActivity extends AppActivity{

	/** 上下文*/
	private Context mContext;
	
	/** 页面父容器 */
	@InjectView(R.id.home_lateral_move_view)
	private LateralMoveView home_lateral_move_view;

	private LayoutInflater layoutInflater;
	
	/** 首页快速预定容器标志*/
	public static final int GMLOGO = 0X521;
	/** 首页扫码签到容器标志*/
	public static final int SCLOGO = 0X527;
	/** 首页设置容器标志*/
	public static final int SETLOGO = 0X522;
	/** 首页电话会议桥容器标志*/
	public static final int PHONELOGO = 0X525;
	/** 首页增值服务容器标志*/
	public static final int VALOGO = 0X523;
	/** 首页我的会议容器标志*/
	public static final int MELOGO = 0X524;
	/** 首页摇一摇容器标志*/
	public static final int SKLOGO = 0X526;

	public static final String FLAG = "FLAG";
	/**
	 * 菜单栏容器
	 */
	private HomeMenuFragment homeMenuFragment;
	
	private MyBroadcastReceiver mBroadcastReceiver;
	
	@Override
	protected void initContentView() {
		super.initContentView();
		Log.i(TAG, "initContentView");
		setContentView(R.layout.activity_main);
		mContext = this;
		layoutInflater = LayoutInflater.from(mContext);
	}

	/**
	 * Because Android Studio can't used roboGuice,so all view init in initData method
	 */
	@Override
	protected void initData() {
		Log.i(TAG, "initData");
		mBroadcastReceiver = new MyBroadcastReceiver();
		IntentFilter filter = new IntentFilter(DataConst.ACTION_HOMEMENU_SWITCH);
		mContext.registerReceiver(mBroadcastReceiver, filter);
		super.initData();
	}
	
	@Override
	protected void initMenu() {
		Log.i(TAG, "initMenu");
		super.initMenu();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onResume");
		super.onResume();
//		if (homeMenuFragment!=null) {
//			homeMenuFragment.refreshView();
//		}
	}
	
	SetFragment met;
	@Override
	protected void initViews() {
		super.initViews();
		Log.i(TAG, "initViews");
		/* 只允许左侧菜单出现，屏蔽右侧菜单。 */
		if (home_lateral_move_view == null) {
			home_lateral_move_view = (LateralMoveView) findViewById(R.id.home_lateral_move_view);
		}
		home_lateral_move_view.setLeftView(layoutInflater.inflate(
				R.layout.fragment_home_left, null));
		home_lateral_move_view.setCenterView(layoutInflater.inflate(
				R.layout.fragment_home_center, null));
		home_lateral_move_view.setRightView(layoutInflater.inflate(
				R.layout.fragment_home_right, null));
		home_lateral_move_view.setCanSliding(false, false);

		FragmentTransaction t = this.getSupportFragmentManager()
				.beginTransaction();
		homeMenuFragment = new HomeMenuFragment();
//		SetFragment met = new SetFragment();
		t.replace(R.id.left_frame, homeMenuFragment);
		t.commitAllowingStateLoss();
		
		firstAddFragment();

	}
	
	@Override
	protected void initViewEvents() {
		super.initViewEvents();
		Log.i(TAG, "initViewEvents");
	}
	/**
	 * 会议预定
	 */
	private MeetingBookFragment meetingBookFragment = new MeetingBookFragment();
	/**
	 * 扫一扫
	 */
	private CaptureFragment scanFragment = new CaptureFragment();
	/**
	 * 设置
	 */
	private SetFragment setFragment = new SetFragment();
	/**
	 * 增值服务
	 */
	private ValueAddServiceFragment valueAddServiceFragment = new ValueAddServiceFragment();
	/**
	 * 我的会议
	 */
	private MyMeetingFragment myMeetingFragment = new MyMeetingFragment();
	/**
	 * 电话会议预定
	 */
	private PhoneVideoFragment phoneVideoFragment = new PhoneVideoFragment();
	/**
	 * 摇一摇
	 */
	private ShakeFragment shakeFragment = new ShakeFragment();
	
	private BaseFragment currentFragment = null;
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		if (currentFragment==null||meetingBookFragment==null||
				shakeFragment==null||phoneVideoFragment==null||
				myMeetingFragment==null||valueAddServiceFragment==null||
				setFragment==null||scanFragment==null) {
			displayCenterContent(GMLOGO);
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		Log.d(TAG, "onNewIntent");
		if (intent!=null&&intent.getIntExtra(FLAG, 0)!=0) {
			int flag = intent.getIntExtra(FLAG, 0);
			displayFragment(flag);
			handler.sendMessageDelayed(handler.obtainMessage(0, flag),1000);
			if (flag==VALOGO) {
				if (valueAddServiceFragment==null) {
					valueAddServiceFragment = new ValueAddServiceFragment();
				}
				valueAddServiceFragment.setViewPagerItem(1);
			}
		}
	}
	
	/** 替换中心布局显示的内容
	 * @param centerContentLogo 中间布局标志
	 * */
	public void displayCenterContent(int centerContentLogo){
		switch (centerContentLogo) {
			case GMLOGO:
				if (meetingBookFragment==null) {
					meetingBookFragment =  new MeetingBookFragment();
				}
				switchFragment(meetingBookFragment);
				currentFragment = meetingBookFragment;
				break;
			case SCLOGO:
				if (scanFragment==null) {
					scanFragment =  new CaptureFragment();
				}
				switchFragment(scanFragment);
				currentFragment = scanFragment;
				break;
			case SETLOGO:
				if (setFragment==null) {
					setFragment =  new SetFragment();
				}
				switchFragment(setFragment);
				currentFragment = setFragment;
				break;
			case VALOGO:
				if (valueAddServiceFragment==null) {
					valueAddServiceFragment =  new ValueAddServiceFragment();
				}
				switchFragment(valueAddServiceFragment);
				currentFragment = valueAddServiceFragment;
				break;
			case MELOGO:
				if (myMeetingFragment==null) {
					myMeetingFragment =  new MyMeetingFragment();
				}
				switchFragment(myMeetingFragment);
				currentFragment = myMeetingFragment;
				break;
			case PHONELOGO:
				if (phoneVideoFragment==null) {
					phoneVideoFragment =  new PhoneVideoFragment();
				}
				switchFragment(phoneVideoFragment);
				currentFragment = phoneVideoFragment;
				break;
			case SKLOGO:
				if (shakeFragment==null) {
					shakeFragment =  new ShakeFragment();
				}
				switchFragment(shakeFragment);
				currentFragment = shakeFragment;
				break;
		default:
			break;  
		} 
	}
	
	/**
	 * 初始化或者通知栏点击进入会议详情等二级界面返回时调用该方法
	 * 第一次添加fragment
	 * 会议详情等二级界面返回时，会传相关的页签，调用该方法切换页签
	 */
	private void firstAddFragment() {
		// TODO Auto-generated method stub
		if (getIntent()!=null&&getIntent().getIntExtra(FLAG, 0)!=0) {
			int flag = getIntent().getIntExtra(FLAG, 0);
			displayFragment(flag);
			handler.sendMessageDelayed(handler.obtainMessage(0, flag),1000);
			return;
		}
		displayFragment(SETLOGO);
		handler.sendMessageDelayed(handler.obtainMessage(0, SETLOGO),1000);
//		FragmentTransaction t = this.getSupportFragmentManager()
//				.beginTransaction();
//		t.add(R.id.center_frame, meetingBookFragment).commitAllowingStateLoss();
//		currentFragment = meetingBookFragment;
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 0:
				int flag = (Integer) msg.obj;
				homeMenuFragment.processSelectForMain(flag);
				break;
			}
		};
	};
	
	/**
	 * 切换fragment显示或隐藏
	 */
	private void switchFragment(BaseFragment fragment) {
		if (fragment!=null) {
			FragmentTransaction t = this.getSupportFragmentManager()
					.beginTransaction();
			if (!fragment.isAdded()) { // 先判断是否被add过
				if (currentFragment==null) {
					t.add(R.id.center_frame, fragment).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
				}else {
					t.hide(currentFragment).add(R.id.center_frame, fragment).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
				}
			} else {
				t.hide(currentFragment).show(fragment).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
			}
		}
		
	}
	
	/** 显示或隐藏左边菜单 */
	public void showLeftView() {
		home_lateral_move_view.showLeftView();
	}
	
	/** 显示或隐藏左边菜单 */
	public void hiddenLeft() {
		home_lateral_move_view.hideLeftView();
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {

		if (keyCode == event.KEYCODE_BACK) {
			Dialog dialog = new AlertDialog.Builder(MainActivity.this)
					.setMessage("确认退出？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) { 
									Intent intent = new Intent(
											Intent.ACTION_MAIN);
									intent.addCategory(Intent.CATEGORY_HOME);
									intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									startActivity(intent);
									System.exit(0);
								}

							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									// 点击"取消"按钮
								}
							}).create();// 创建
			// 显示对话框
			dialog.show();
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 显示主容器
	 * @param state
	 */
	public void displayFragment(int state) {
		switch (state){
			case SCLOGO:
				displayCenterContent(SCLOGO);
				break;
			case VALOGO:
				displayCenterContent(VALOGO);
				break;
			case GMLOGO:
				displayCenterContent(GMLOGO);
				break;
			case SETLOGO:
				displayCenterContent(SETLOGO);
				break;
			case MELOGO:
				displayCenterContent(MELOGO);
				break;
			case PHONELOGO:
				displayCenterContent(PHONELOGO);
				break;
			case SKLOGO:
				displayCenterContent(SKLOGO);
				break;
		}
	}
	
	
	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy");
		if(mBroadcastReceiver!=null)
		{
			mContext.unregisterReceiver(mBroadcastReceiver);
		}
		super.onDestroy();
	}
	
	/**
	 * 广播接收器
	 * */
	private class MyBroadcastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(DataConst.ACTION_HOMEMENU_SWITCH.equals(intent.getAction()))
			{

				int id = intent.getIntExtra("data",GMLOGO);
				if(id==MELOGO)
				{
					homeMenuFragment.processSelectForMain(id);
					displayFragment(id);
				}else if(id==GMLOGO)
				{
					if(!intent.getBooleanExtra("isShake", false))
					{
						homeMenuFragment.processSelectForMain(id);
						displayFragment(id);
					}
				}
			}
		}
	}
	
	
}