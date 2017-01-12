package cn.com.zte.emeeting.app.activity;

import roboguice.inject.InjectView;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.View;
import android.widget.TextView;
import cn.com.zte.emeeting.app.base.activity.AppActivity;
import cn.com.zte.emeeting.app.views.TopBar;
import cn.com.zte.emeeting.app.views.TopBar.ButtonOnClick;
import cn.com.zte.mobileemeeting.R;

/**
 * 关于我们界面
 * 
 * @author liu.huanbo
 * 
 */
public class AboutActivity extends AppActivity {
	/** 关于topbar */
	@InjectView(R.id.topbar_about)
	private TopBar aboutTopBar;
	/** 版本号 */
	@InjectView(R.id.tv_version)
	private TextView tv_version;

	@Override
	protected void initContentView() {
		super.initContentView();
		setContentView(R.layout.activity_about);
	}

	@Override
	protected void initData() {
		super.initData();

		tv_version.setText(getVersionName());

	}

	@Override
	protected void initMenu() {
		super.initMenu();
	}

	@Override
	protected void initMenuEvents() {
		super.initMenuEvents();
	}

	@Override
	protected void initViews() {
		super.initViews();
		initTopbar();
	}

	@Override
	protected void initViewEvents() {
		super.initViewEvents();
	}

	private void initTopbar() {

		aboutTopBar.setViewBackGround(TopBar.leftBtnLogo, R.drawable.back);
		aboutTopBar.HiddenView(TopBar.rightBtnLogo);
		aboutTopBar.setViewText(TopBar.titleLogo,
				getString(R.string.tv_about_me));
		aboutTopBar.setViewTextColor(TopBar.titleLogo,
				getResourceColor(R.color.TEXTCOLOR_TOPBAR_TITLE_TEXT));
		aboutTopBar.setViewOnClickListener(TopBar.leftBtnLogo,
				new ButtonOnClick() {

					@Override
					public void onClick(View view) {
						finish();
					}
				});

	}

	/***
	 * 获取当前应用的版本号：
	 * 
	 * @throws NameNotFoundException
	 */
	private String getVersionName() {
		String version;
		try {
			// 获取packagemanager的实例
			PackageManager packageManager = getPackageManager();
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			PackageInfo packInfo = packageManager.getPackageInfo(
					this.getPackageName(), 0);
			version = packInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			version = "";
		}
		return version;
	}
}
