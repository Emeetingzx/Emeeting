package cn.com.zte.emeeting.app.activity;

import cn.com.zte.emeeting.app.base.activity.AppActivity;
import roboguice.inject.InjectView;
import android.view.View;
import android.widget.TextView;
import cn.com.zte.android.app.activity.BaseActivity;
import cn.com.zte.emeeting.app.views.TopBar;
import cn.com.zte.emeeting.app.views.TopBar.ButtonOnClick;
import cn.com.zte.mobileemeeting.R;

/**
 * 帮助详情界面
 * 
 * @author liu.huanbo
 */
public class HelpMeDetailActivity extends AppActivity {
	/** 帮助详情topbar */
	@InjectView(R.id.topbar_detail_helpme)
	private TopBar topbar_detail_helpme;
	/**标题*/
	String title;
	/**内容详细介绍*/
	String detail;
	/**标题*/
	@InjectView(R.id.tv_title)
	private TextView tv_title;
	/**内容详细介绍*/
	@InjectView(R.id.tv_detail)
	private TextView tv_detail;
	@Override
	protected void initContentView() {
		super.initContentView();
		setContentView(R.layout.activity_helpme_detail);
	}
	
	@Override
	protected void initData() {
		super.initData();
		 try {
			title=getIntent().getStringExtra("title");
		} catch (Exception e) {
			title="";
			e.printStackTrace();
		}
		 try {
			detail=getIntent().getStringExtra("detail");
		} catch (Exception e) {
			detail="";
			e.printStackTrace();
		}
	}
	@Override
	protected void initViews() {
		super.initViews();

		topbar_detail_helpme.setViewOnClickListener(TopBar.leftBtnLogo,
				new ButtonOnClick() {

					@Override
					public void onClick(View view) {
						finish();
					}
				});
		topbar_detail_helpme.setViewBackGround(TopBar.leftBtnLogo, R.drawable.back);
		topbar_detail_helpme.HiddenView(TopBar.rightBtnLogo);
		topbar_detail_helpme.setViewText(TopBar.titleLogo, getString(R.string.tv_help));
		topbar_detail_helpme.setViewTextColor(TopBar.titleLogo,
				getResourceColor(R.color.TEXTCOLOR_TOPBAR_TITLE_TEXT));
		
		tv_title.setText(title);
		tv_detail.setText(detail);
	}
	
	@Override
	protected void initViewEvents() {
		super.initViewEvents();
	}
	@Override
	protected void initMenu() {
		super.initMenu();
	}
	
	@Override
	protected void initMenuEvents() {
		super.initMenuEvents();
	}

}
