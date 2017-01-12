package cn.com.zte.emeeting.app.activity;

import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.zte.emeeting.app.activity.control.ContactListActivity;
import cn.com.zte.emeeting.app.base.activity.AppActivity;
import cn.com.zte.emeeting.app.views.TopBar;
import cn.com.zte.emeeting.app.views.TopBar.ButtonOnClick;
import cn.com.zte.mobileemeeting.R;

public class MeetingControlActivity extends AppActivity implements OnClickListener  {
	private static final String TAG = MeetingControlActivity.class.getSimpleName();
	
	@InjectView(R.id.topbar_room_control)
	private TopBar topbar;
	
	@InjectView(R.id.rel_layout)
	private RelativeLayout rel_layout;
	
	@InjectView(R.id.rel_invite_meeting_room)
	private RelativeLayout rel_invite_meeting_room;
	
	@InjectView(R.id.rel_invite_phone_friends)
	private RelativeLayout rel_invite_phone_friends;
	
	@InjectView(R.id.rel_dail_contact)
	private RelativeLayout rel_dail_contact;
	
	
	
	@InjectView(R.id.rel_search_layout)
	private RelativeLayout rel_search_layout;
	
	@InjectView(R.id.et_input)
	private EditText et_input;
	
	@InjectView(R.id.tv_join_meeting)
	private TextView tv_join_meeting;
	
	@InjectView(R.id.tv_join_meeting_tips)
	private TextView tv_join_meeting_tips;
	
	@InjectView(R.id.tv_call_tips)
	private TextView tv_call_tips;
	
	
	@InjectView(R.id.tv_invite_contact_list)
	private TextView tv_invite_contact_list;
	
	
	@Override
	protected void initContentView() {
		super.initContentView();
		setContentView(R.layout.activity_meeting_control);
	}

	@Override
	protected void initViews() {
		super.initViews();
		initTopBar();
	}

	
	@Override
	protected void initViewEvents() {
		super.initViewEvents();
		initViewOnClickListener();
	}
	
	private void initViewOnClickListener() {
		rel_invite_meeting_room.setOnClickListener(this);
		rel_invite_phone_friends.setOnClickListener(this);
		rel_dail_contact.setOnClickListener(this);
		tv_join_meeting.setOnClickListener(this);
		tv_invite_contact_list.setOnClickListener(this);
	}


	private void initTopBar() {
		topbar.setViewBackGround(TopBar.leftBtnLogo, R.drawable.back);
		topbar.setViewOnClickListener(TopBar.leftBtnLogo, new ButtonOnClick() {

			@Override
			public void onClick(View view) {
				finish();
			}
		});
		
		topbar.setViewText(TopBar.titleLogo, getString(R.string.title_meeting_control));
		topbar.setViewTextColor(TopBar.titleLogo, getResourceColor(R.color.TEXTCOLOR_TOPBAR_TITLE_TEXT));
		
	}
	
	@Override
	public void onClick(View v) {
		
		rel_layout.setVisibility(View.INVISIBLE);
		rel_search_layout.setVisibility(View.VISIBLE);
		
		switch (v.getId()) {
		
		case R.id.rel_invite_meeting_room: 
			tv_join_meeting_tips.setText(getString(R.string.tips_input_meeting_port));
			
			break;
			
		case R.id.rel_invite_phone_friends:
			tv_invite_contact_list.setVisibility(View.VISIBLE);
			tv_join_meeting_tips.setText(getString(R.string.tips_input_num));
			
			break;
			
		case R.id.rel_dail_contact:
			tv_join_meeting_tips.setText(getString(R.string.tips_support_call));
			tv_call_tips.setVisibility(View.VISIBLE);
			
			break;
			
		case R.id.tv_join_meeting:
			startActivity(new Intent(mContext,CheckInActivity.class));
			this.finish();
			
			break;
			
		case R.id.tv_invite_contact_list: 
			startActivity(new Intent(MeetingControlActivity.this, ContactListActivity.class));
			this.finish();
			
			break;

		default:
			break;
		}
		
	}

	
}
