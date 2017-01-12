package cn.com.zte.emeeting.app.fragment;

import cn.com.zte.android.pushclient.PushManager;
import roboguice.inject.InjectView;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.zte.android.app.fragment.BaseFragment;
import cn.com.zte.android.resource.inflater.BaseLayoutInflater;
import cn.com.zte.emeeting.app.activity.AboutActivity;
import cn.com.zte.emeeting.app.activity.HelpMeActivity;
import cn.com.zte.emeeting.app.activity.MainActivity;
import cn.com.zte.emeeting.app.activity.MeetingControlActivity;
import cn.com.zte.emeeting.app.activity.SuggestionFeedbackActivity;
import cn.com.zte.emeeting.app.appservice.WelComeService;
import cn.com.zte.emeeting.app.base.ConfigrationList;
import cn.com.zte.emeeting.app.dialog.PushCommonDialog;
import cn.com.zte.emeeting.app.util.DataConst;
import cn.com.zte.emeeting.app.util.EmeetingToast;
import cn.com.zte.emeeting.app.util.NetWorkUtil;
import cn.com.zte.emeeting.app.util.SharedPreferenceUtil;
import cn.com.zte.emeeting.app.views.TopBar;
import cn.com.zte.emeeting.app.views.TopBar.ButtonOnClick;
import cn.com.zte.mobilebasedata.request.HttpUtil;
import cn.com.zte.mobileemeeting.R;

/**
 * 设置 详细内容展示容器
 * 
 * @author liu.huanbo
 * */
public class SetFragment extends BaseFragment implements OnClickListener {

	/** TAG信息 */
	private final String TAG = SetFragment.class.getSimpleName();

	/** 上下文 */
	private Context mContext;

	/** Topbar */
	@InjectView(R.id.setfragment_topbar)
	private TopBar setFragmentTopbar;
	/** 版本更新内外网标志true为外网，false为内网 */
	// public static boolean versionUpdateDetectionLogo = true;

	/** 意见反馈 */
	@InjectView(R.id.rel_suggestion_feedback)
	private RelativeLayout rel_suggestion_feedback;

	/** 使用指南 */
	@InjectView(R.id.rel_guide)
	private RelativeLayout rel_guide;

	/** 关于 */
	@InjectView(R.id.rel_about)
	private RelativeLayout rel_about;

	/** 检测新版本 */
	@InjectView(R.id.rel_app_version)
	private RelativeLayout rel_app_version;

	/** 退出当前登陆 */
	@InjectView(R.id.btn_logout)
	private Button btn_logout;

	/** 版本更新标识 */
	private String isUpdateString;

	/** 版本更新 */
	@InjectView(R.id.tv_setFragment_version)
	private TextView tv_setFragment_version;

	@Override
	protected View onCreateView(BaseLayoutInflater inflater, ViewGroup view,
			Bundle bundle) {
		mContext = getActivity();
		return inflater.inflate(R.layout.fragment_setting, null);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	protected void initData() {
		super.initData();
		regBroadcastReceiver();
		verUpdate();
	}

	@Override
	protected void initViews() {
		super.initViews();
		// 初始化topBar
		initTopBar();
	}

	private void initTopBar() {
		setFragmentTopbar.setViewBackGround(TopBar.leftBtnLogo,
				R.drawable.icon_home_menu);
		setFragmentTopbar.HiddenView(TopBar.rightBtnLogo);
		setFragmentTopbar.setViewText(TopBar.titleLogo, getString(R.string.tv_message_set));
		setFragmentTopbar.setViewTextColor(TopBar.titleLogo,
				getResourceColor(R.color.TEXTCOLOR_TOPBAR_TITLE_TEXT));

	}

	@Override
	protected void initViewEvents() {
		super.initViewEvents();
		rel_suggestion_feedback.setOnClickListener(this);
		rel_guide.setOnClickListener(this);
		rel_about.setOnClickListener(this);
		rel_app_version.setOnClickListener(this);
		btn_logout.setOnClickListener(this);

		setFragmentTopbar.setViewOnClickListener(TopBar.leftBtnLogo,
				new ButtonOnClick() {

					@Override
					public void onClick(View view) {
						InputMethodManager inputManager = (InputMethodManager) mContext
								.getSystemService(Context.INPUT_METHOD_SERVICE);
						if (inputManager.isActive()) {
							inputManager.hideSoftInputFromWindow(
									btn_logout.getWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS);
						}
						// 返回菜单栏
						MainActivity mainActivity = (MainActivity) getActivity();
						mainActivity.showLeftView();
					}
				});

		// initSwitch();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rel_suggestion_feedback:// 意见反馈
			startActivity(new Intent(mContext, SuggestionFeedbackActivity.class));

			break;
		case R.id.rel_guide:// 使用指南
			startActivity(new Intent(mContext, HelpMeActivity.class));
			break;
		case R.id.rel_about:// 关于
			startActivity(new Intent(mContext, AboutActivity.class));
			/*---下面这一行应该删除 上面那行应该使用---*/
//			getActivity().startActivity(new Intent(getActivity(), MeetingControlActivity.class));
			break;
		case R.id.rel_app_version:// 版本更新
			if (NetWorkUtil.netIsConnect(mContext)) {

				detectionVersion();
			} else {
				EmeetingToast.show(mContext, getString(R.string.tv_message_no_net));
			}

			break;
		case R.id.btn_logout:// 退出当前账号
			dialogDetail();

			break;

		default:
			break;
		}
	}

	/** 预定情况 */
	private void dialogDetail() {
		final PushCommonDialog commonDialog = new PushCommonDialog(mContext);
		commonDialog.show();
		commonDialog.sureBtnClick(new PushCommonDialog.OnClick() {

			@Override
			public void btnClick() {
				// TODO Auto-generated method stub
//				PushManager.getInstance(mContext).unregister(new WelComeService(mContext).getUserInfo().getUID());
//				new WelComeService(mContext).ssoLogout();
				commonDialog.dismiss();
				getActivity().finish();
				
			}
		});
		commonDialog.cancelBtnClick(new PushCommonDialog.OnClick() {

			@Override
			public void btnClick() {
				// TODO Auto-generated method stub
				commonDialog.dismiss();
			}
		});
	}

	/** 检测版本 */
	private void detectionVersion() {
		new WelComeService(mContext).VersionUpdateDetection(
				versionUpdateDetectionHandler, HttpUtil.ISOUTSIDENET);
	}

	/**
	 * 初始化版本更新
	 */
	private void verUpdate() {

		isUpdateString = new SharedPreferenceUtil(ConfigrationList.APPCONFIG,
				mContext).getString(ConfigrationList.ISUPDATE);
		if (isUpdateString.equals("") || isUpdateString.equals("N")) {
			// 没有新版本，隐藏new标志
			tv_setFragment_version.setVisibility(View.INVISIBLE);
			// tv_version.setVisibility(View.GONE);
		} else if (isUpdateString.equals("Y")) {
			// 有新版本
			tv_setFragment_version.setVisibility(View.VISIBLE);
		}
	}

	/** 更新版本返回结果接收 */
	@SuppressLint("HandlerLeak")
	Handler versionUpdateDetectionHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WelComeService.NotHasNewVersionLogo:
//				Log.i(TAG, "没有新版本");
				EmeetingToast.show(mContext,getString(R.string.tv_message_now_version)+"（" + getVersionName()
						+ "）");
				tv_setFragment_version.setVisibility(View.INVISIBLE);
				isUpdateString = "N";
				break;
			case WelComeService.CancelNewOptionVersionLogo:
//				Log.i(TAG, "有可选的新版本");
				isUpdateString = "Y";
				tv_setFragment_version.setVisibility(View.VISIBLE);
				break;
			case WelComeService.PopUpErrorLogo:
//				Log.i(TAG, "版本更新出现异常");
				EmeetingToast.show(mContext, getString(R.string.tv_message_net_error));
				break;
			case WelComeService.NetworkUseErrorLogo:
//				Log.i(TAG, "外网版本更新出现异常");
				new WelComeService(mContext).VersionUpdateDetection(
						versionUpdateDetectionHandler, false);
				break;
			default:
				break;
			}
		}
	};

	/***
	 * 获取当前应用的版本号：
	 * 
	 * @throws NameNotFoundException
	 */
	private String getVersionName() {
		String version;
		try {
			// 获取packagemanager的实例
			PackageManager packageManager = mContext.getPackageManager();
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			PackageInfo packInfo = packageManager.getPackageInfo(getActivity()
					.getPackageName(), 0);
			version = packInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			version = "";
		}
		return version;
	}

	private MyBroadcastReceiver mBroadcastReceiver;

	private void regBroadcastReceiver() {
		mBroadcastReceiver = new MyBroadcastReceiver();
		IntentFilter filter = new IntentFilter(DataConst.ACTION_CLOSE);
		mContext.registerReceiver(mBroadcastReceiver, filter);
	}

	/**
	 * 广播接收器
	 * */
	private class MyBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			{
				try {
					if (intent.getAction().equals(DataConst.ACTION_CLOSE)) {
						EmeetingToast.show(mContext, getString(R.string.tv_message_sugges_succes));
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
