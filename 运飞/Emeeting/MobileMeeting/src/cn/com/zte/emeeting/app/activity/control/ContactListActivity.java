package cn.com.zte.emeeting.app.activity.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ab.util.AbStrUtil;
import com.lidroid.xutils.util.LogUtils;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import cn.com.zte.android.common.util.JsonUtil;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.android.widget.dialog.DialogManager;
import cn.com.zte.emeeting.app.activity.control.PublicInvitaMeetingRequest.OnRequestSuccess;
import cn.com.zte.emeeting.app.adapter.ContactListAdapter;
import cn.com.zte.emeeting.app.adapter.ContactListAdapter.OnClickListenerJoin;
import cn.com.zte.emeeting.app.appservice.ContactListService;
import cn.com.zte.emeeting.app.appservice.MeetingControlFragmentService;
import cn.com.zte.emeeting.app.base.activity.AppActivity;
import cn.com.zte.emeeting.app.dialog.DialogLoadBar;
import cn.com.zte.emeeting.app.entity.system.ContactInfo;
import cn.com.zte.emeeting.app.response.entity.MeetingJoinInfo;
import cn.com.zte.emeeting.app.response.instrument.GetMeetingJoinInfoResponse;
import cn.com.zte.emeeting.app.util.DataConst;
import cn.com.zte.emeeting.app.views.PinyinComparator;
import cn.com.zte.emeeting.app.views.TopBar;
import cn.com.zte.emeeting.app.views.TopBar.ButtonOnClick;
import cn.com.zte.mobileemeeting.R;
import roboguice.inject.InjectView;

/**
 * 
 * @ClassName: ContactListActivity
 * @Description: TODO 【获取手机联系人页面】
 * @author Pan.Jianbo
 * @date 2016年4月7日 上午8:30:42
 * 
 */
public class ContactListActivity extends AppActivity {

	/** 上下文环境 */
	private Context mContext;

	/** 头部TopBar控件 */
	@InjectView(R.id.topbar_contactlist)
	private TopBar topBar;

	/** ListView控件 */
	@InjectView(R.id.lv_contact_list)
	private ListView lv_contact_list;

	/** 邀请会议加入公共请求处理类 */
	private PublicInvitaMeetingRequest publicInvitaMeetingRequest;

	/** 返回标识 */
	private boolean flage = false;

	/** 获取手机联系人逻辑处理类 */
	private ContactListService contactListService;

	/** 手机联系人适配器 */
	private ContactListAdapter adapter;

	/** 加载数据框 */
	private DialogLoadBar dialogLoadBar;

	/** 手机联系人数据集合 */
	private List<ContactInfo> infoList = null;

	/** 会议ID */
	private String id = null;
	
	/** 逻辑处理类 */
	private MeetingControlFragmentService mService;
	
	@Override
	protected void initContentView() {
		super.initContentView();
		setContentView(R.layout.activity_ask_contacts);
		mContext = this;
		publicInvitaMeetingRequest = new PublicInvitaMeetingRequest(mContext);
		contactListService = new ContactListService(mContext);
		mService = new MeetingControlFragmentService(mContext);
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
		
		infoList = new ArrayList<ContactInfo>();
		dialogLoadBar = new DialogLoadBar(mContext);
		dialogLoadBar.show();

		adapter = new ContactListAdapter(mContext, infoList, new OnClickListenerJoin() {

					@Override
					public void OnOnClickListenerJoinBack(View view, int position, ContactInfo contactInfo) {
						
						if (infoList.get(position).isSelected()) {
                            DialogManager.showToast(mContext, getResourceString(R.string.AMJoinPersion));
						} else {
	//						joinMetting(contactInfo.getPhoneNumber(), contactInfo.getContactName(), position);
							String number = contactInfo.getPhoneNumber().replaceAll(" ", "");  
							judgePhoneNumber(number, contactInfo.getContactName(), position);
						}
					}
				});

		lv_contact_list.setAdapter(adapter);

		new Thread() {
			@Override
			public void run() {
				super.run();
				/** 获取手机联系人，及联系人姓名拼音 */
				if (contactListService.getPhoneContacts()!=null && contactListService.getPhoneContacts().size()>0){
					infoList.addAll(contactListService.getFristPinYin(contactListService.getPhoneContacts()));
				}
				
				LogUtils.i("排序前："+JsonUtil.toJson(infoList));
				
				Collections.sort(infoList, new PinyinComparator());
				
				LogUtils.d("排序后:"+JsonUtil.toJson(infoList));
				
				hand.sendEmptyMessage(0);

			}
		}.start();

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

		/** TopBar左侧监听事件 */
		topBar.setViewOnClickListener(TopBar.leftBtnLogo, new ButtonOnClick() {

			@Override
			public void onClick(View view) {

				back(flage);
			}
		});
	}

	/**
	 * 接收主线程更新数据的消息
	 */
	Handler hand = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				hand.sendEmptyMessage(2);
				break;
			case 1:
				adapter.notifyDataSetChanged();
				dialogLoadBar.dismiss();
				break;
			case 2:
				if (!AbStrUtil.isEmpty(id)){
					mService.getAllMeetingJoinInfo(getMeetingJoinHandler, id);
				}
				break;
			default:
				break;
			}

		};
	};

	/**
	 * 邀请会议接口请求返回
	 * 
	 * @param number
	 */
	private void joinMetting(String number, String name, final int position) {

		publicInvitaMeetingRequest.getDataInfo(id, DataConst.TWO, number, name,
				new OnRequestSuccess() {

			        /**
			         * 邀请会议
			         */
					@Override
					public void OnSuccessCallBack(String code) {

						if (!AbStrUtil.isEmpty(code)) {
							if (code.equals(DataConst.YES)) {
								flage = true;
								infoList.get(position).setSelected(true);
								adapter.notifyDataSetChanged();
							} 
						}
					}

					/**
					 * 邀请会议确认
					 */
					@Override
					public void OnConfirm(String code) {
						if (!AbStrUtil.isEmpty(code)) {
							if (code.equals(DataConst.YES)) {
								flage = true;
								infoList.get(position).setSelected(true);
								adapter.notifyDataSetChanged();
							} 
						}
					}
				});

	}

	/**
	 * 返回判断
	 * 
	 * @param bool
	 */
	private void back(boolean bool) {
		if (bool) {
			Intent intent = new Intent(DataConst.CONTROL_FILTER);
			sendBroadcast(intent);
			finish();
		} else {
			finish();
		}
	}

	/**
	 * 手机好友加入会议室，号码判断
	 * @param number
	 * @param name
	 * @param position
	 */
	private void judgePhoneNumber(String number, String name, int position) {
		if (!AbStrUtil.isEmpty(number)) {
			int code = AbStrUtil.strLength(number);
			if (code > 11) {
				LogUtils.e("手机号码大于11位");
				int numberLen = AbStrUtil.strLength(number);
				if (numberLen == 14) {
					LogUtils.e("号码等于14位");
					/*if (AbStrUtil.cutString(number, 4).equals("+861")){
						joinMetting(number, name, position);
					} else*/ 
					if (AbStrUtil.cutString(number, 3).equals("+86")){
						LogUtils.e("+86开头");
						String newNumber = number.substring(3);
						LogUtils.i("截取之后的字符:" + newNumber);
						joinMetting(newNumber, name, position);
					} else if (AbStrUtil.cutString(number, 3).equals("086")){
						LogUtils.e("086开头");
						String newNumber = number.substring(3);
						LogUtils.i("截取之后的字符:" + newNumber);
						joinMetting(newNumber, name, position);
					} else {
						DialogManager.showToast(mContext, getResourceString(R.string.AMPermissionADD));
					}
				} else if (numberLen == 12) {
					LogUtils.e("号码等于12位");
					if (AbStrUtil.cutString(number, 1).equals("0")){
						LogUtils.e("固话号码");
	                    joinMetting(number, name, position);
					} else {
					    DialogManager.showToast(mContext, getResourceString(R.string.AMPermissionADD));
					}
				} else {
				   DialogManager.showToast(mContext, getResourceString(R.string.AMPermissionADD));
				}
			} else if (code == 11) {
				LogUtils.e("手机号码等于11位");
				if (AbStrUtil.cutString(number, 1).equals("1")) {
					LogUtils.e("手机号码");
                    joinMetting(number, name, position);
				} else if (AbStrUtil.cutString(number, 1).equals("0")){
					LogUtils.e("固话号码");
                    joinMetting(number, name, position);
				} else {
                    DialogManager.showToast(mContext, getResourceString(R.string.AMPermissionADD));
				}
			} /*else if (code == 12){
				LogUtils.e("号码等于12位");
				if (AbStrUtil.cutString(number, 1).equals("0")){
					LogUtils.e("固话号码");
                    joinMetting(number, name, position);
				} else {
				    DialogManager.showToast(mContext, getResourceString(R.string.AMPermissionADD));
				}
			}*/ else if (code == 8){
				LogUtils.e("号码等于8位市话");
				 joinMetting(number, name, position);
			} else {
				LogUtils.e("其他条件");
				DialogManager.showToast(mContext, getResourceString(R.string.AMPermissionADD));
			}
		}
	}
	
	
	/**
	 * 获取会议下所有会场信息接口返回处理
	 */
	BaseAsyncHttpResponseHandler<GetMeetingJoinInfoResponse> getMeetingJoinHandler = new BaseAsyncHttpResponseHandler<GetMeetingJoinInfoResponse>() {
		
		/**
		 * 列表请求成功
		 */
		public void onSuccessTrans(GetMeetingJoinInfoResponse responseModelVO) {
			LogUtils.e("列表请求成功");
			List<MeetingJoinInfo> list = new ArrayList<MeetingJoinInfo>();
			if (responseModelVO.getD() != null) {
				list.addAll(responseModelVO.getD());
				for (int i = 0; i < list.size(); i++) {
					String number = list.get(i).getTN();
					for (int j = 0; j < infoList.size(); j++) {
                        String phoneNumber = infoList.get(j).getContactName();
                        if (!AbStrUtil.isEmpty(number) && number.equals(phoneNumber)){
							LogUtils.e("存在相同的号码：" + number);
                        	infoList.get(j).setSelected(true);
                        	break;
                        }
					}
				}
				hand.sendEmptyMessage(1);
			} else {
				hand.sendEmptyMessage(1);
			}
				
		};

		/**
		 * 列表请求失败
		 */
		public void onFailureTrans(GetMeetingJoinInfoResponse responseModelVO) {
			LogUtils.e("列表请求失败");
		};

		/**
		 * 列表请求网络异常
		 */
		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode, String strMsg) {
			LogUtils.e("列表请求网络异常");
		};
	};
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			back(flage);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
