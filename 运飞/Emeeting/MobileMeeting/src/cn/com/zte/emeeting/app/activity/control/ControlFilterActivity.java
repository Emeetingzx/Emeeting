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
import android.widget.RelativeLayout;
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
 * @ClassName: ControlFilterActivity
 * @Description: TODO 【会议控制-选择类型界面】
 * @author Pan.Jianbo
 * @date 2016年4月6日 上午9:06:54
 *
 */
public class ControlFilterActivity extends AppActivity implements OnClickListener {

	/** 上下文环境 */
	private Context mContext;

	/** 头部TopBar控件 */
	@InjectView(R.id.topbar_control)
	private TopBar topBar;

	/** 视频会议父容器 */
	@InjectView(R.id.AM_RelativeLayout_Video)
	private RelativeLayout AM_RelativeLayout_Video;

	/** 手机好友父容器 */
	@InjectView(R.id.AM_RelativeLayout_Phone)
	private RelativeLayout AM_RelativeLayout_Phone;

	/** 固定电话父容器 */
	@InjectView(R.id.AM_RelativeLayout_TelePhone)
	private RelativeLayout AM_RelativeLayout_TelePhone;
	
	/** 输入框 */
	@InjectView(R.id.cw_et_input)
	private EditText cw_et_input;

	/** 加入会场按钮 */
	@InjectView(R.id.cw_btn_Join)
	private Button cw_btn_Join;
	
	/** 广播接收值 */
	private UpdateReceiver doUpdate;
	
	/** 会议邀请公共接口请求逻辑处理类 */
	private PublicInvitaMeetingRequest publicInvitaMeetingRequest;
	
	/** 邀请的类型 */
	private String Type = null;
	
	/** 会议ID */
	private String id = null;
	
	@Override
	protected void initContentView() {
		super.initContentView();
		setContentView(R.layout.activity_control_add_way);
		mContext = this;
		publicInvitaMeetingRequest = new PublicInvitaMeetingRequest(mContext);
		initUpdateReceiver();
		
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
		topBar.setViewBackGround(TopBar.leftBtnLogo, R.drawable.back);
		topBar.HiddenView(TopBar.rightBtnLogo);
		topBar.setViewText(TopBar.titleLogo, getString(R.string.AMTitleStr));
		topBar.setViewTextColor(TopBar.titleLogo, getResourceColor(R.color.white));
	}

	@Override
	protected void initViewEvents() {
		super.initViewEvents();
		AM_RelativeLayout_Video.setOnClickListener(this);
		AM_RelativeLayout_Phone.setOnClickListener(this);
		AM_RelativeLayout_TelePhone.setOnClickListener(this);
		cw_btn_Join.setOnClickListener(this);

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
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.AM_RelativeLayout_Video: // 视频会议
			intent.setClass(mContext, NumberAddActivity.class);
			intent.putExtra("pageCode", DataConst.VIDEO_CODE);
			intent.putExtra("id", id);
			startActivity(intent);
			break;
		case R.id.AM_RelativeLayout_Phone: // 内线会议
			intent.setClass(mContext, TelPhoneActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
			break;
		case R.id.AM_RelativeLayout_TelePhone: // 外线会议
            intent.setClass(mContext, NumberAddActivity.class);
			intent.putExtra("pageCode", DataConst.PHONE_CODE);
			intent.putExtra("id", id);
			startActivity(intent);
			break;
		case R.id.cw_btn_Join: // 加入会场按钮
			if (!AbStrUtil.isEmpty(cw_et_input.getText().toString())) { // 判断号码是否为空
				if (AbStrUtil.isNumber(cw_et_input.getText().toString())) { // 判断是否只是数字
					judgeType(cw_et_input.getText().toString());
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
	 * 判断电话类型
	 * 
	 * @param number
	 */
	private void judgeType(String number) {
		if (!AbStrUtil.isEmpty(number)) {
			int code = AbStrUtil.strLength(number);
			switch (code) {
			case 6: // GK号
				LogUtils.e("GK号长度为6");
				Type = DataConst.ZERO;
				joinMetting(Type, cw_et_input.getText().toString());
				break;
			case 11: // 手机号
				LogUtils.e("长度为11");
				judgesPhoneNumber(number);
				break;
			case 9: // GK号
				LogUtils.e("GK号长度为9");
				Type = DataConst.ZERO;
				joinMetting(Type, cw_et_input.getText().toString());
				break;
			case 7: // GK号
				LogUtils.e("GK号长度为7");
				Type = DataConst.ZERO;
				joinMetting(Type, cw_et_input.getText().toString());
				break;
			case 12: // 固话
				LogUtils.e("长度为12");
				judgesPhoneNumber(number);
				break;
			default:
				LogUtils.e("没有满足判断条件");
				DialogManager.showToast(mContext, getResourceString(R.string.NumberFormatInvalid));
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
				Type = DataConst.TWO;
				joinMetting(Type, cw_et_input.getText().toString());
			} else if (AbStrUtil.cutString(number, 1).equals("1")) { // 手机号
				LogUtils.e("手机号");
				if (AbStrUtil.isMobileNo(number)) {
					Type = DataConst.ONE;
					joinMetting(Type, cw_et_input.getText().toString());
				} else {
                    DialogManager.showToast(mContext, getResourceString(R.string.NumberFormatInvalid));
				}
			} else {
				 DialogManager.showToast(mContext, getResourceString(R.string.NumberFormatInvalid));
			}
		}

	}
	
	/**
	 * 加入会议接口请求返回
	 * @param type
	 * @param number
	 */
	private void joinMetting(String type, String number) {
		
		publicInvitaMeetingRequest.getDataInfo(id, type, number, "", new OnRequestSuccess() {

			@Override
			public void OnSuccessCallBack(String code) {
                if (!AbStrUtil.isEmpty(code)){
                	if (code.equals(DataConst.YES)){ // 加入成功
                		finish();
                	} 
                }
			}

			@Override
			public void OnConfirm(String code) {
				 if (!AbStrUtil.isEmpty(code)){
	                	if (code.equals(DataConst.YES)){ // 加入成功
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
