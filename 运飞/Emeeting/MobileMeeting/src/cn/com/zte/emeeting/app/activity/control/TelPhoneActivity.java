package cn.com.zte.emeeting.app.activity.control;

import com.ab.util.AbAppUtil;
import com.ab.util.AbStrUtil;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import cn.com.zte.android.widget.dialog.DialogManager;
import cn.com.zte.emeeting.app.activity.control.PublicInvitaMeetingRequest.OnRequestSuccess;
import cn.com.zte.emeeting.app.base.activity.AppActivity;
import cn.com.zte.emeeting.app.util.DataConst;
import cn.com.zte.emeeting.app.views.TopBar;
import cn.com.zte.emeeting.app.views.TopBar.ButtonOnClick;
import cn.com.zte.mobileemeeting.R;
import roboguice.inject.InjectView;

/**
 * 
 * @ClassName: TelPhoneActivity
 * @Description: TODO 【添加固话页面】
 * @author Pan.Jianbo
 * @date 2016年4月6日 下午4:15:05
 *
 */
public class TelPhoneActivity extends AppActivity implements OnClickListener {

	/** 上下文环境 */
	private Context mContext;

	/** topBar控件 */
	@InjectView(R.id.topbar_telePhoneMeeting)
	private TopBar topBar;

	/** 固话输入框 */
	@InjectView(R.id.tp_et_ss)
	private EditText tp_et_ss;

	/** 加入会场 */
	@InjectView(R.id.tp_bt_joinVenue)
	private Button tp_bt_joinVenue;

	/** 邀请会议加入公共请求处理类 */
	private PublicInvitaMeetingRequest publicInvitaMeetingRequest;
	
	/** 会议ID */
	private String id = null;
	
	@Override
	protected void initContentView() {
		super.initContentView();
		setContentView(R.layout.activity_telephone_meeting);
		mContext = this;
		publicInvitaMeetingRequest = new PublicInvitaMeetingRequest(mContext);
	}

	@Override
	protected void initData() {
		super.initData();
		
		try {
			id = getIntent().getStringExtra("id");
		} catch (Exception e) {
			id = "";
			e.printStackTrace();
		}
	}

	@Override
	protected void initViews() {
		super.initViews();
		/** 初始化TopBar控件 */
		topBar.setViewBackGround(TopBar.leftBtnLogo, R.drawable.back);
		topBar.HiddenView(TopBar.rightBtnLogo);
		topBar.setViewText(TopBar.titleLogo, getString(R.string.AMTitleStr));
		topBar.setViewTextColor(TopBar.titleLogo, getResourceColor(R.color.white));
	}

	@Override
	protected void initViewEvents() {
		super.initViewEvents();
		tp_bt_joinVenue.setOnClickListener(this);
		
		
		/** TopBar左侧监听事件 */
		topBar.setViewOnClickListener(TopBar.leftBtnLogo, new ButtonOnClick() {
			
			@Override
			public void onClick(View view) {
				new AbAppUtil().closeSoftInput(mContext);
				finish();
			}
		});
	}

	@Override
	public void onClick(View v) {
		new AbAppUtil().closeSoftInput(mContext);
		switch (v.getId()) {
		case R.id.tp_bt_joinVenue: // 加入会场
			if (isEmpty()) {
				if (AbStrUtil.isNumber(tp_et_ss.getText().toString())) {
					judgePhoneNumber(tp_et_ss.getText().toString());
				} else {
					DialogManager.showToast(mContext, getResourceString(R.string.NumberFormatInvalid));
				}
			}
			break;
		default:
			break;
		}
	}
	
	/**
	 * 固话号码格式判断
	 * @param phoneNumber
	 */
	private void judgePhoneNumber(String phoneNumber) {
		
		if (AbStrUtil.cutString(phoneNumber, 1).equals("0")) {
			int code = AbStrUtil.strLength(phoneNumber);
			if (code < 20) {
				joinMetting(phoneNumber);
			} else {
				DialogManager.showToast(mContext, getResourceString(R.string.AMNumberError));
			}
		} else {
			DialogManager.showToast(mContext, getResourceString(R.string.AMNumberError));
		}
	}
	
	
	/**
	 * 判断必填项
	 * @return
	 */
	private boolean isEmpty() {
		if (AbStrUtil.isEmpty(tp_et_ss.getText().toString())) {
			DialogManager.showToast(mContext, getResourceString(R.string.AMPhoneInfoIsEmpty));
			return false;
		}
		return true;
	}
	
	
	/**
	 * 邀请会议接口请求返回
	 * @param number
	 */
	private void joinMetting(String number) {
		
		publicInvitaMeetingRequest.getDataInfo(id, DataConst.ONE, number, DataConst.NULL, new OnRequestSuccess() {
			
			@Override
			public void OnSuccessCallBack(String code) {
				
				if (!AbStrUtil.isEmpty(code)){
					if (code.equals(DataConst.YES)) {
						Intent intent = new Intent(DataConst.CONTROL_FILTER);
						sendBroadcast(intent);
						finish();
					}
				}
			}

			@Override
			public void OnConfirm(String code) {
			
				if (!AbStrUtil.isEmpty(code)){
					if (code.equals(DataConst.YES)) {
						Intent intent = new Intent(DataConst.CONTROL_FILTER);
						sendBroadcast(intent);
						finish();
					}
				}
			}
		});
		
	}
	
	
}
