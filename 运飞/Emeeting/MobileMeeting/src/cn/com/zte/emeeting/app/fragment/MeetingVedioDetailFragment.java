/**
 * 
 */
package cn.com.zte.emeeting.app.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.com.zte.android.app.fragment.BaseFragment;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.android.resource.inflater.BaseLayoutInflater;
import cn.com.zte.android.securityauth.model.UserInfo;
import cn.com.zte.emeeting.app.activity.CheckInActivity;
import cn.com.zte.emeeting.app.activity.MeetingDetailActivity;
import cn.com.zte.emeeting.app.activity.ScanSuccessActivity;
import cn.com.zte.emeeting.app.adapter.MeetingDetailRoomListAdapter;
import cn.com.zte.emeeting.app.adapter.MeetingDetailRoomListAdapter.ItemMeetingRoomListener;
import cn.com.zte.emeeting.app.appservice.CheckInService;
import cn.com.zte.emeeting.app.appservice.MeetingDetailService;
import cn.com.zte.emeeting.app.appservice.WelComeService;
import cn.com.zte.emeeting.app.database.entity.shared.DBMeetingRoomInfo;
import cn.com.zte.emeeting.app.dialog.DialogLoadBar;
import cn.com.zte.emeeting.app.dialog.DlgCancelBookSuccess;
import cn.com.zte.emeeting.app.dialog.DlgConfirmBook;
import cn.com.zte.emeeting.app.dialog.DlgConfirmBook.OnConfirmDlgListener;
import cn.com.zte.emeeting.app.response.entity.MeetingInfo;
import cn.com.zte.emeeting.app.response.instrument.GetAttendanceOperationResponse;
import cn.com.zte.emeeting.app.response.instrument.GetCancelMeetingRoomResponse;
import cn.com.zte.emeeting.app.response.instrument.GetEndMeetingRoomResponse;
import cn.com.zte.emeeting.app.util.DataConst;
import cn.com.zte.emeeting.app.util.EmeetingToast;
import cn.com.zte.emeeting.app.util.EmeetingUtil;
import cn.com.zte.emeeting.app.util.LogTools;
import cn.com.zte.emeeting.app.util.ObjectCopyUtil;
import cn.com.zte.emeeting.app.util.ViewsUtil;
import cn.com.zte.mobilebasedata.request.HttpUtil;
import cn.com.zte.mobileemeeting.R;
import roboguice.inject.InjectView;

/**
 * 该类为会议详情界面
 * 
 * @author wf
 */
public class MeetingVedioDetailFragment extends BaseFragment {
	private static final String TAG = "MeetingDetailControlFragment";

	private MeetingDetailService mService;

	private Context mContext;

	private MeetingInfo info = new MeetingInfo();

	String id;
	public static final int CODE_HANDLER = 1;

	/** 会议主题 */
	@InjectView(R.id.meeting_title)
	private TextView meeting_title;
	/** 会议类型 */
	@InjectView(R.id.meeting_type)
	private TextView meeting_type;
	/** 参会领导 */
	@InjectView(R.id.meeting_leader)
	private TextView meeting_leader;
	/** 会议级别 */
	@InjectView(R.id.meeting_level)
	private TextView meeting_level;
	/** 起止时间 */
	@InjectView(R.id.meeting_time)
	private TextView meeting_time;
	/** 接入编号 */
	@InjectView(R.id.access_number)
	private TextView access_number;
	/** 会议编号 */
	@InjectView(R.id.meeting_num)
	private TextView meeting_num;
	/** 会议密码 */
	@InjectView(R.id.meeting_pwd)
	private TextView meeting_pwd;
	/** 参会方数 */
	@InjectView(R.id.meeting_fangshu)
	private TextView meeting_fangshu;
	/** 组织者姓名 */
	@InjectView(R.id.organizer_name)
	private TextView organizer_name;
	/** 会议编号布局 */
	@InjectView(R.id.lin_meeting_num)
	private LinearLayout lin_meeting_num;
	/** 会议密码布局 */
	@InjectView(R.id.rel_meeting_pwd)
	private RelativeLayout rel_meeting_pwd;
	/** 退定 */
	@InjectView(R.id.btn_push)
	private Button btn_push;
	
	/**
	 * 签到跳转标示，防止多次点击多次跳转
	 */
	private boolean flag = true;
	/**
	 * 签到表
	 */
	@InjectView(R.id.layout_meetingdetail_attendance)
	private RelativeLayout attendanceLayout;

	/** 会议开始时间 */
	String str;
	/** 会议结束时间 */
	String strEd;
	
	/** 签到 */
	private CheckInService checkInService = new CheckInService(mContext);
	
	private String MeetingId = null;
	
	private Handler handler2 = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub

			switch (msg.what) {
			case CODE_HANDLER:
				Log.d("zl", "操作状态"+info.getOS()+"是否签到"+info.getISI());
				if (info.getOS() != null && info.getOS().equals("0")) {
					btn_push.setText(R.string.meeting_attendance);
					if (info.getISI()!=null&&info.getISI().equals("Y")) {
						btn_push.setVisibility(View.GONE);
					}
				} else if (info.getOS() != null && info.getOS().equals("1")) {
					btn_push.setText(getString(R.string.tv_message_detail_push));
				} else if (info.getOS() != null && info.getOS().equals("2")) {
					btn_push.setText(getString(R.string.tv_message_detail_end));
				}
				

				break;

			default:
				break;
			}
		}

	};

	@Override
	protected View onCreateView(BaseLayoutInflater arg0, ViewGroup arg1,
			Bundle arg2) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onCreateView");
		return arg0.inflate(R.layout.fragment_vedio_meeting_detail, null);
	}

	/**
	 * @see BaseFragment#initData()
	 */
	@Override
	protected void initData() {
		Log.d(TAG, "initData");
		mContext = getActivity();
		if (info!=null) {
			initMeetingInfo();
		}
		super.initData();
	}
	
	
	@Override
	public void onPause() {
		Log.d(TAG, "onPause");
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	public void onDestroyView() {
		Log.d(TAG, "onDestroyView");
		// TODO Auto-generated method stub
		super.onDestroyView();
	}


	private void initMeetingInfo() {
		if (info!=null) {
			MeetingId = info.getMID();
			Log.d("zl", "会议Id:"+info.getMID());
		}else {
			return;
		}
		
		if (PhoneVideoFragment.isShow) {
			lin_meeting_num.setVisibility(View.VISIBLE);
			rel_meeting_pwd.setVisibility(View.VISIBLE);
		} else {
			lin_meeting_num.setVisibility(View.GONE);
			rel_meeting_pwd.setVisibility(View.GONE);
		}
		meeting_title.setText(info.getMN());

		if (info.getMTP() != null && info.getMTP().equals("1")) {
			meeting_type
					.setText(getString(R.string.tv_message_detail_routine_meeting));
		} else if (info.getMTP() != null && info.getMTP().equals("2")) {
			meeting_type
					.setText(getString(R.string.tv_message_detail_phone_meeting));
			lin_meeting_num.setVisibility(View.VISIBLE);
			rel_meeting_pwd.setVisibility(View.VISIBLE);
			access_number.setText(info.getAP());
		} else if (info.getMTP() != null && info.getMTP().equals("3")) {
			meeting_type
					.setText(getString(R.string.tv_message_detail_train_meeting));

		} else if (info.getMTP() != null && info.getMTP().equals("4")) {
			meeting_type
					.setText(getString(R.string.tv_message_detail_net_meeting));
		} else if (info.getMTP() != null && info.getMTP().equals("5")) {

			meeting_type
					.setText(getString(R.string.tv_message_detail_cloud_meeting));

		} else if (info.getMTP() != null && info.getMTP().equals("6")) {
			meeting_type
					.setText(getString(R.string.tv_message_detail_video_meeting));
			lin_meeting_num.setVisibility(View.GONE);
			rel_meeting_pwd.setVisibility(View.GONE);
			if (info.getMPWD() == null || info.getMPWD().equals("")) {
				access_number.setText(info.getMNB());
			} else {
				access_number.setText(info.getMNB() + "*" + info.getMPWD());
			}

		}

		if (info.getPMLL() != null && info.getPMLL().equals("1")) {
			meeting_leader
					.setText(getString(R.string.tv_message_detail_two_leader));
		} else if (info.getPMLL() != null && info.getPMLL().equals("2")) {
			meeting_leader
					.setText(getString(R.string.tv_message_detail_three_leader));
		} else if (info.getPMLL() != null && info.getPMLL().equals("3")) {
			meeting_leader
					.setText(getString(R.string.tv_message_detail_four_leader));
		} else if (info.getPMLL() != null && info.getPMLL().equals("4")) {
			meeting_leader
					.setText(getString(R.string.tv_message_detail_other_leader));
		}

		if (info.getML() != null && info.getML().equals("1")) {
			meeting_level
					.setText(getString(R.string.tv_message_detail_a_level));
		} else if (info.getML() != null && info.getML().equals("2")) {
			meeting_level
					.setText(getString(R.string.tv_message_detail_b_level));
		} else if (info.getML() != null && info.getML().equals("3")) {
			meeting_level
					.setText(getString(R.string.tv_message_detail_c_level));
		}

		meeting_num.setText(info.getMNB());
		meeting_pwd.setText(info.getMPWD());
		organizer_name.setText(info.getOPCN());
		meeting_fangshu.setText(info.getPN());
		SimpleDateFormat old_formatter = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat formatterEd = new SimpleDateFormat("HH:mm");

		try {
			Date date = old_formatter.parse(info.getBD());
			Date dateEd = old_formatter.parse(info.getED());
			str = formatter.format(date);
			strEd = formatterEd.format(dateEd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		meeting_time.setText(str + " - " + strEd);
		handler2.sendEmptyMessage(CODE_HANDLER);
		
		UserInfo userInfo = new WelComeService(mContext).getUserInfo();
		if (userInfo!=null&&userInfo.getUID().equals(info.getOPN())) {
//			if ("101037490".equals(info.getOPN())) {
			attendanceLayout.setVisibility(View.VISIBLE);
		}else {
			attendanceLayout.setVisibility(View.GONE);
		}
	}

	@Override
	protected void initViewEvents() {
		super.initViewEvents();
		if (info != null) {

			btn_push.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					UserInfo userinfo = new WelComeService(mContext).getUserInfo();
					if (userinfo!=null&&userinfo.getUID().equals(info.getOPN())) {
//					if ("101037490".equals(info.getOPN())) {
						cancelBook();
					}else {
						if (flag) {
							flag = false;
						//签到按钮，签到逻辑
						checkInService.CheckIn(mContext, MeetingId, null, handler);
						}
					}
				}
			});

		}

		attendanceLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 签到表
				Intent intent = new Intent(getActivity(),CheckInActivity.class);
				intent.putExtra("MeetingId",MeetingId);
				intent.putExtra("organizer_name", organizer_name.getText());
				intent.putExtra("BD", info.getBD());
				intent.putExtra("ED", info.getED());
				startActivity(intent);
			}
		});
	}
	/**
	 * 签到结果处理
	 */
	BaseAsyncHttpResponseHandler<GetAttendanceOperationResponse> handler = new BaseAsyncHttpResponseHandler<GetAttendanceOperationResponse>(){

		@Override
		public void onPopUpErrorDialog(String strTitle, String strCode,
				String strMsg) {
			super.onPopUpErrorDialog(strTitle, strCode, strMsg);
//			EmeetingToast.show(mContext, strMsg);
		}

		@Override
		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			super.onPopUpHttpErrorDialogPre(strTitle, strCode, strMsg);
		}


		@Override
		public void onFailureTrans(
				GetAttendanceOperationResponse responseModelVO) {
			super.onFailureTrans(responseModelVO);
			if (responseModelVO.getS().equals("false")) {
					Log.d("zl", "失败信息："+responseModelVO.getM());
					Intent intent = new Intent(getActivity(),ScanSuccessActivity.class);
					intent.putExtra("isSuccess", "0");
					intent.putExtra("checkedType", "0");
					intent.putExtra("ResultInfo", responseModelVO.getM());
					intent.putExtra("BD", info.getBD());
					intent.putExtra("ED", info.getED());
					startActivity(intent);
				}
		}

		@Override
		public void onSuccessTrans(
				GetAttendanceOperationResponse responseModelVO) {
			super.onSuccessTrans(responseModelVO);
				if (responseModelVO.getS().equals("true")) {
						Log.d("签到信息", "是否成功："+responseModelVO.getS()+"返回m："+responseModelVO.getM());
						Intent intent = new Intent(getActivity(),ScanSuccessActivity.class);
						intent.putExtra("isSuccess", "1");
						intent.putExtra("checkedNum", responseModelVO.getM());
						intent.putExtra("checkedType", "0");
						intent.putExtra("BD", info.getBD());
						intent.putExtra("ED", info.getED());
						startActivity(intent);
				}
		}
		
	};

	

	/**
	 * 该方法用于:取消退订
	 * 
	 * @param
	 * @return void
	 */
	private void cancelBook() {
		final DlgConfirmBook dlg = new DlgConfirmBook(mContext, info.getMN(),
				new OnConfirmDlgListener() {

					@Override
					public void onConfirmed() {

						if (info != null && info.getOS().equals("1")) {

							new MeetingDetailService(mContext)
									.cancelBookMeeting(info.getMID(),
											info.getBMRIDS(), handlerCancel);
						} else if (info != null && info.getOS().equals("2")) {
							new MeetingDetailService(mContext).stopMeeting(
									info.getMID(), info.getBMRIDS(),
									handlerStop);
						} else {
							Toast.makeText(
									mContext,
									getString(R.string.tv_message_detail_meeting_no_do),
									Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onCanceled() {

					}
				});
		if (info != null && info.getOS().equals("1")) {
			dlg.setLeftTitle(getString(R.string.tv_message_detail_sure_push));
		} else if (info != null && info.getOS().equals("2")) {
			dlg.setLeftTitle(getString(R.string.tv_message_detail_sure_end));
		}

		dlg.show();
	}

	/** 退订接口 */
	BaseAsyncHttpResponseHandler<GetCancelMeetingRoomResponse> handlerCancel = new BaseAsyncHttpResponseHandler<GetCancelMeetingRoomResponse>(
			true) {
		// 退订成功
		public void onSuccessTrans(GetCancelMeetingRoomResponse responseModelVO) {

			if (responseModelVO.getD() != null) {
				// cancelBook();
				// 状态改变成不可操作
				if (info != null) {
					// 刷新我的会议
					Intent it_refresh = new Intent(
							DataConst.ACTION_MYMEETING_REFRESH_REMOVE);
					it_refresh.putExtra("data", info);
					mContext.sendBroadcast(it_refresh);

				}
				DlgCancelBookSuccess dlg = new DlgCancelBookSuccess(mContext,
						getString(R.string.tv_message_detail_success_push));
				dlg.setOnCancelListener(new DialogInterface.OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						// TODO Auto-generated method stub
						getActivity().finish();
					}
				});
				dlg.showToast(3000);
				info.setOS("0");
			}
		};

		// 退订失败
		public void onFailureTrans(GetCancelMeetingRoomResponse responseModelVO) {

			if (responseModelVO != null && responseModelVO.getM() != null
					&& !responseModelVO.getM().equals("")) {
				Toast.makeText(mContext, responseModelVO.getM(),
						Toast.LENGTH_LONG).show();

			}

		};

		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {

			Toast.makeText(mContext, HttpUtil.SERVER_REQUEST_FAIL,
					Toast.LENGTH_LONG).show();

		};

	};
	/** 结束接口 */
	BaseAsyncHttpResponseHandler<GetEndMeetingRoomResponse> handlerStop = new BaseAsyncHttpResponseHandler<GetEndMeetingRoomResponse>(
			true) {
		// 退订成功
		public void onSuccessTrans(GetEndMeetingRoomResponse responseModelVO) {

			if (responseModelVO.getD() != null) {
				if (info != null) {
					// 刷新我的会议
					Intent it_refresh = new Intent(
							DataConst.ACTION_MYMEETING_REFRESH_REMOVE);
					it_refresh.putExtra("data", info);
					mContext.sendBroadcast(it_refresh);

				}

				DlgCancelBookSuccess dlg = new DlgCancelBookSuccess(mContext,
						getString(R.string.tv_message_detail_success_end));
				dlg.setOnCancelListener(new DialogInterface.OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						// TODO Auto-generated method stub
						getActivity().finish();
					}
				});
				dlg.showToast(3000);
				if (info != null) {

					info.setOS("0");
				}
			}
		};

		// 退订失败
		public void onFailureTrans(GetEndMeetingRoomResponse responseModelVO) {

			if (responseModelVO != null && responseModelVO.getM() != null
					&& !responseModelVO.getM().equals("")) {
				EmeetingToast.show(mContext, responseModelVO.getM());
			}else {
				EmeetingToast.show(mContext, HttpUtil.SERVER_REQUEST_FAIL);
			}
		};

		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			EmeetingToast.show(mContext, HttpUtil.SERVER_REQUEST_FAIL);
		};

	};

	public void setMeetingInfo(MeetingInfo meetingEntity) {
		// TODO Auto-generated method stub
		this.info = meetingEntity;
	}
	
	public MeetingInfo getInfo(){
		return info;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		flag = true;
	}
}
