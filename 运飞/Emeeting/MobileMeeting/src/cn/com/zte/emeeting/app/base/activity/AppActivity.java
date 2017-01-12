package cn.com.zte.emeeting.app.base.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import cn.com.zte.android.app.activity.BaseActivity;
import cn.com.zte.android.http.HttpManager;
import cn.com.zte.android.securityauth.config.SSOAuthConfig;
import cn.com.zte.emeeting.app.appservice.WelComeService;
import cn.com.zte.emeeting.app.base.MyApplication;
import cn.com.zte.mobilebasedata.request.CheckNetCallBack;
import cn.com.zte.mobilebasedata.request.HttpUtil;
import cn.com.zte.emeeting.app.util.SharedPreferenceUtil;

/**
 * 基础Activity
 * 
 * @author LangK
 * 
 */
public class AppActivity extends BaseActivity {
	
	/** 上下文*/
	protected Context mContext;
	
	protected String TAG = getClass().getSimpleName();
	
	
	/** 是否弹出手势密码验证 */
	protected boolean needGesturesPWDIsR = false;
	/**
	 * 切到后台5分钟后需要输入手势密码
	 */
	private static final long gesturesPWDTime = 5*60*1000;
	/**
	 * 手势密码是否已经关掉
	 */
	public static boolean isGesturesPwdFinish = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.GetApp().addActivity(this);
		mContext = this;
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");
		checkInnerOrOutSide();
		
		try {
			/* 避免应用字体大小随系统变化*/
			Resources resource = getResources();
			Configuration c =resource.getConfiguration();
			c.fontScale = 1.0f;
			resource.updateConfiguration(c, resource.getDisplayMetrics());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");
		SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss");     
		Date   curDate   =   new   Date(System.currentTimeMillis());//获取当前时间     
		String   onPauseTimestr   =   formatter.format(curDate); 
		Log.i(TAG, "onPauseTime:"+onPauseTimestr);
		new SharedPreferenceUtil("Time", getApplicationContext()).setNameAndValue("ActivityOnPauseTime", onPauseTimestr);
	}
	
//	@Override
//	public void finish() {
//		super.finish();
//	}
//	
	
	/** 内外网检测*/
	private void checkInnerOrOutSide() {
		// 设置超时
		HttpManager.setTimeout(HttpUtil.VersionUpdateTimeOut);

		// 网络检测回调
		CheckNetCallBack callBack = new CheckNetCallBack() {
			@Override
			public void onNetCheckFinish(boolean isCheckOuter, boolean isEnable) {
				if (isEnable) {
					HttpUtil.ISOUTSIDENET = true;
					Log.i(TAG, "您已切换到外网网络环境");
					HttpUtil.getInstance().setSERVEREIP(HttpUtil.NetworkIP);
					SSOAuthConfig.configToOuterNet();
				}
			}

			@Override
			public void onIntranetCheckFinish(boolean isCheckOuter,
					boolean isEnable) {
				super.onIntranetCheckFinish(isCheckOuter, isEnable);
				if (isEnable) {
					HttpUtil.ISOUTSIDENET = false;
					Log.i(TAG, "您已切换到内网网络环境");
					HttpUtil.getInstance().setSERVEREIP(HttpUtil.IntranetIP);
					SSOAuthConfig.configToInnerNet();
				} else {
					Log.d(TAG, "无法连接到服务器,请检查你的网络或者稍后重试");
//					MyToast.show(mContext, "无法连接到服务器,请检查你的网络或者稍后重试");
				}
			}

		};
		// 检测外网内网
		WelComeService.checkOuterInnerNet(mContext, callBack);
	}
	
}
