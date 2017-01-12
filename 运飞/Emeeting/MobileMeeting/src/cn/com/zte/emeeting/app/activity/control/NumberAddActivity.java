package cn.com.zte.emeeting.app.activity.control;

import com.ab.util.AbAppUtil;
import com.ab.util.AbStrUtil;
import com.lidroid.xutils.util.LogUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
 * @ClassName: NumberAddActivity
 * @Description: TODO 【号码添加页面】
 * @author Pan.Jianbo
 * @date 2016年4月6日 下午2:44:14
 *
 */
public class NumberAddActivity extends AppActivity implements OnClickListener {

	/** 山下文环境 */
	private Context mContext;

	/** 头部TopBar控件 */
	@InjectView(R.id.topbar_joinMeeting)
	private TopBar topBar;

	/** 号码输入框 */
	@InjectView(R.id.ed_joinMeeting_input)
	private EditText ed_joinMeeting_input;

	/** 加入会议按钮 */
	@InjectView(R.id.btn_joinMeeting)
	private Button btn_joinMeeting;

	/** 提示信息 */
	@InjectView(R.id.tv_joinmeeting_tip)
	private TextView tv_joinmeeting_tip;

	/** 邀请手机通讯录信息 */
	@InjectView(R.id.btn_invite_contact)
	private Button btn_invite_contact;
	
	/**邀请外线固话手机号码加入*/
	@InjectView(R.id.tv_Out_PhoneNumber)
	private TextView tv_Out_PhoneNumber;

	/**邀请外线固话或手机号码加入提示*/
	@InjectView(R.id.tv_Out_PhoneNumberHint)
	private TextView tv_Out_PhoneNumberHint;
	
	/** 页面标识 */
	private int pageCode = 0;

	/** 广播接收值 */
	private UpdateReceiver doUpdate;
	
	/** 邀请会议加入公共请求处理类 */
	private PublicInvitaMeetingRequest publicInvitaMeetingRequest;
	
	/** 会议类型 */
	private String Type = null;
	
	/** 会议ID */
	private String id = null;
	
	@Override
	protected void initContentView() {
		super.initContentView();
		setContentView(R.layout.activity_join_meeting);
		mContext = this;
		publicInvitaMeetingRequest = new PublicInvitaMeetingRequest(mContext);
		initUpdateReceiver();
	}

	@Override
	protected void initData() {
		super.initData();
		
		try {
			pageCode = getIntent().getIntExtra("pageCode", 0);
		} catch (Exception e) {
			pageCode = 0;
			e.printStackTrace();
		}
		
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
		/** 初始化页面显示元素 */
		initPageCodeByView(pageCode);
	}

	@Override
	protected void initViewEvents() {
		super.initViewEvents();
		
		btn_joinMeeting.setOnClickListener(this);
		btn_invite_contact.setOnClickListener(this);
		
		/** TopBar左侧监听事件 */
		topBar.setViewOnClickListener(TopBar.leftBtnLogo, new ButtonOnClick() {
			
			@Override
			public void onClick(View view) {
				new AbAppUtil().closeSoftInput(mContext);
				finish();
			}
		});
	}
	
	/**
	 * 根据页面标识，显示不同的页面元素
	 * @param code
	 */
	private void initPageCodeByView(int code){
		switch (code) {
		case DataConst.VIDEO_CODE: // 视频终端号
			setTextByCode(true);
			Type = DataConst.ZERO;
			break;
        case DataConst.PHONE_CODE: // 电话终端号
        	setTextByCode(false);
        	Type = DataConst.TWO;
        	break;
		default:
			break;
		}
	}

	/**
	 * 根据不同的标识显示不同的字段
	 * @param bool
	 */
	private void setTextByCode(boolean bool) {
		if (bool) {
			btn_invite_contact.setVisibility(View.GONE);
			tv_Out_PhoneNumber.setVisibility(View.GONE);
			tv_Out_PhoneNumberHint.setVisibility(View.GONE);
			tv_joinmeeting_tip.setText(getResourceString(R.string.AMHintVideo));
		} else {
			btn_invite_contact.setVisibility(View.VISIBLE);
			tv_Out_PhoneNumber.setVisibility(View.VISIBLE);
			tv_Out_PhoneNumberHint.setVisibility(View.VISIBLE);
			tv_joinmeeting_tip.setText(getResourceString(R.string.AMHint));
		}
	}
	
	
	@Override
	public void onClick(View v) {
		new AbAppUtil().closeSoftInput(mContext);
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.btn_joinMeeting: // 加入会议监听事件
			String phoneNumber = ed_joinMeeting_input.getText().toString();
		    judgePhoneNumber(pageCode, phoneNumber);
			break;
        case R.id.btn_invite_contact: // 邀请手机通讯录成员加入
        	intent.setClass(mContext, ContactListActivity.class);
        	intent.putExtra("id", id);
        	startActivityForResult(intent, DataConst.REQUESTCODE_CONTACTS);
        	break;
		default:
			break;
		}
		
	}
	
	/**
	 * 输入框格式判断
	 * 
	 * @param code
	 * @param phoneNumber
	 */
	private void judgePhoneNumber(int code, String phoneNumber) {
		switch (code) {
		case DataConst.VIDEO_CODE: // 视频终端号
			if (!AbStrUtil.isEmpty(phoneNumber)) { // 判断是否为空
				if (AbStrUtil.isNumber(phoneNumber)) {// 判断是否只是数字
					judgeGKNumber(ed_joinMeeting_input.getText().toString());
				} else {
					DialogManager.showToast(mContext, getResourceString(R.string.NumberFormatInvalid));
				}
			} else {
				DialogManager.showToast(mContext, getResourceString(R.string.AMInfoIsEmpty));
			}
			break;
		case DataConst.PHONE_CODE: // 电话终端号
			if (!AbStrUtil.isEmpty(phoneNumber)) {// 判断是否为空
				if (AbStrUtil.isNumber(phoneNumber)) {// 判断是否只是数字
					initCodePhoneNumber(phoneNumber);
				} else {
					DialogManager.showToast(mContext, getResourceString(R.string.NumberFormatInvalid));
				}
			} else {
				DialogManager.showToast(mContext, getResourceString(R.string.AMInfoIsEmpty));
			}
			break;
		default:
			break;
		}
	}
	
	/**
	 * 判断外线号码
	 * 
	 * @param phoneNumber
	 */
	private void initCodePhoneNumber(String phoneNumber) {
		if (!AbStrUtil.isEmpty(phoneNumber)) {
			int code = AbStrUtil.strLength(phoneNumber);
			switch (code) {
			case 11:
				LogUtils.e("11位号码");
				judgesPhoneNumber(phoneNumber);
				break;
			case 12:
				LogUtils.e("12位号码");
				if (AbStrUtil.cutString(phoneNumber, 1).equals("0")) {
					joinMetting(phoneNumber);
				} else {
                    DialogManager.showToast(mContext, getResourceString(R.string.AMPhoneTel));
				}
				break;
			case 8:
				LogUtils.e("8位市话");
				joinMetting(phoneNumber);
				break;
			default:
				LogUtils.e("没有满足判断条件");
				DialogManager.showToast(mContext, getResourceString(R.string.AMNumberError));
				break;
			}
		}
	}
	
	/**
	 * 判断号码格式
	 * @param number
	 */
	private void judgesPhoneNumber(String number) {

		if (!AbStrUtil.isEmpty(number)) {
			if (AbStrUtil.cutString(number, 1).equals("0")) { // 固话
				LogUtils.e("固话号");
				joinMetting(number);
			} else if (AbStrUtil.cutString(number, 1).equals("1")) { // 手机号
				LogUtils.e("手机号");
				if (AbStrUtil.isMobileNo(number)) {
					joinMetting(number);
				} else {
                    DialogManager.showToast(mContext, getResourceString(R.string.AMPermissionNumberError));
				}
			} else {
				 DialogManager.showToast(mContext, getResourceString(R.string.AMNumberError));
			}
		}

	}
	
	/**
	 * GK号码判断
	 * @param number
	 */
	private void judgeGKNumber(String number) {
		if (!AbStrUtil.isEmpty(number)) {
			if (AbStrUtil.cutString(number, 4).equals("0852")) { // 国际GK号码
				LogUtils.e("国际GK号码");
				joinMetting(number);
			} else if (AbStrUtil.cutString(number, 1).equals("0")) {
                LogUtils.e("国内GK号码");
                int code = AbStrUtil.strLength(number);
				if (code > 7) {
					DialogManager.showToast(mContext, getResourceString(R.string.AMGKNumberError));
				} else {
					joinMetting(number);
				}
			} else {
				DialogManager.showToast(mContext, getResourceString(R.string.AMGKNumberError));
			}
		}
	}
	
	
	
	/**
	 * 邀请会议接口请求返回
	 * @param number
	 */
	private void joinMetting(String number) {

		publicInvitaMeetingRequest.getDataInfo(id,Type, number, DataConst.NULL, new OnRequestSuccess() {

			@Override
			public void OnSuccessCallBack(String code) {

				if (!AbStrUtil.isEmpty(code)) {
					if (code.equals(DataConst.YES)) {
                       Intent intent = new Intent(DataConst.CONTROL_FILTER);
                       sendBroadcast(intent);
                       finish();
					}
				}
			}

			@Override
			public void OnConfirm(String code) {
				
				if (!AbStrUtil.isEmpty(code)) {
					if (code.equals(DataConst.YES)) {
                       Intent intent = new Intent(DataConst.CONTROL_FILTER);
                       sendBroadcast(intent);
                       finish();
					}
				}
			}
		});

	}
	
	
	
	/**
	 * 
	 * @Title: initUpdateReceiver
	 * @Description: TODO 【注册广播】
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void initUpdateReceiver() {

		doUpdate = new UpdateReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(DataConst.CONTROL_FILTER);
		registerReceiver(doUpdate, filter);
	}
	
	/**
	 * 
	 * @ClassName: UpdateReceiver
	 * @Description: TODO 【广播接收机制】
	 * @author Pan.Jianbo
	 * @date 2015-9-16 上午9:01:36
	 * 
	 */
	public class UpdateReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(DataConst.CONTROL_FILTER)) {
				finish();
			}
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(doUpdate);
	}
	
	
	
}
