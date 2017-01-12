package cn.com.zte.emeeting.app.activity;

import cn.com.zte.emeeting.app.base.activity.AppActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import cn.com.zte.android.app.activity.BaseActivity;
import cn.com.zte.emeeting.app.views.TopBar;
import cn.com.zte.emeeting.app.views.TopBar.ButtonOnClick;
import cn.com.zte.mobileemeeting.R;

/**
 * 使用指南界面
 * 
 * @author liu.huanbo
 * 
 */
public class HelpMeActivity extends AppActivity implements OnClickListener {
	/** 帮助topbar */
	@InjectView(R.id.topbar_helpme)
	private TopBar topbar_helpme;
	/** 预定帮助 */
	@InjectView(R.id.rel_help_reserve)
	private RelativeLayout rel_help_reserve;
	/** 摇一摇帮助 */
	@InjectView(R.id.rel_help_shake)
	private RelativeLayout rel_help_shake;
	/** 电话视频帮助 */
	@InjectView(R.id.rel_help_phone)
	private RelativeLayout rel_help_phone;
	/** 增值帮助 */
	@InjectView(R.id.rel_help_add)
	private RelativeLayout rel_help_add;

	@Override
	protected void initContentView() {
		super.initContentView();
		setContentView(R.layout.activity_helpme);
	}

	@Override
	protected void initData() {
		super.initData();
	}

	@Override
	protected void initViews() {
		super.initViews();

		initTopbar();

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
	protected void initViewEvents() {
		super.initViewEvents();
		rel_help_reserve.setOnClickListener(this);
		rel_help_shake.setOnClickListener(this);
		rel_help_phone.setOnClickListener(this);
		rel_help_add.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rel_help_reserve:// 跳转预定
			intentHelpDetail(getString(R.string.tv_help_reserve),
					getString(R.string.tv_help_reserve_detail));
			break;
		case R.id.rel_help_shake:// 跳转摇一摇
			intentHelpDetail(getString(R.string.tv_help_shake),
					getString(R.string.tv_help_shake_detail));
			break;
		case R.id.rel_help_phone:// 电话视频会议
			intentHelpDetail(getString(R.string.tv_help_phone),
					getString(R.string.tv_help_phone_detail));
			break;
		case R.id.rel_help_add:// 增值服务
			intentHelpDetail(getString(R.string.tv_help_add),
					getString(R.string.tv_help_add_detail));
			break;

		default:
			break;
		}
	}

	private void initTopbar() {
		topbar_helpme.setViewOnClickListener(TopBar.leftBtnLogo,
				new ButtonOnClick() {

					@Override
					public void onClick(View view) {
						finish();
					}
				});
		topbar_helpme.setViewBackGround(TopBar.leftBtnLogo, R.drawable.back);
		topbar_helpme.HiddenView(TopBar.rightBtnLogo);
		topbar_helpme
				.setViewText(TopBar.titleLogo, getString(R.string.tv_help));
		topbar_helpme.setViewTextColor(TopBar.titleLogo,
				getResourceColor(R.color.TEXTCOLOR_TOPBAR_TITLE_TEXT));

	}

	/** 跳转到帮助详情界面 */
	private void intentHelpDetail(String title, String detail) {
		Intent intent = new Intent(this, HelpMeDetailActivity.class);
		intent.putExtra("title", title);
		intent.putExtra("detail", detail);
		startActivity(intent);

	}
}
