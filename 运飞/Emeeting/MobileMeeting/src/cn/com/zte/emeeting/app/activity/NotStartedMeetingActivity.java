package cn.com.zte.emeeting.app.activity;

import android.view.View;

import com.lidroid.xutils.view.annotation.ViewInject;

import cn.com.zte.emeeting.app.base.activity.AppActivity;
import cn.com.zte.emeeting.app.views.TopBar;
import cn.com.zte.emeeting.app.views.TopBar.ButtonOnClick;
import cn.com.zte.mobileemeeting.R;

public class NotStartedMeetingActivity extends AppActivity{

	/**
	 * 顶部标题
	 */
	@ViewInject(R.id.TopBar_notStartedMeeting)
	private TopBar topBar;
	
	@Override
	protected void initContentView() {
		super.initContentView();
		setContentView(R.layout.activity_notstarted_meeting);
		initTopbar();
	}
	
	private void initTopbar() {
		topBar.setViewBackGround(TopBar.leftBtnLogo, R.drawable.back);
		topBar.setViewOnClickListener(TopBar.leftBtnLogo, new ButtonOnClick() {
			
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		topBar.setViewText(TopBar.titleLogo, "签到表");
		topBar.setViewTextColor(TopBar.titleLogo, getResourceColor(R.color.white));
	}
}
