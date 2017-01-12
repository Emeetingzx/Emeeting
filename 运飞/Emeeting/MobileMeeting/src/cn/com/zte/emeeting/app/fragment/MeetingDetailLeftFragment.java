/**
 * 
 */
package cn.com.zte.emeeting.app.fragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.com.zte.emeeting.app.util.DateFormatUtil;
import roboguice.inject.InjectView;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zte.android.app.fragment.BaseFragment;
import cn.com.zte.android.common.util.JsonUtil;
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

/**
 * 该类为会议详情界面
 * 
 * @author wf
 */
public class MeetingDetailLeftFragment extends BaseFragment {
	private static final String TAG = "MeetingDetailLeftFragment";

	private MeetingDetailService mService;

	private Context mContext;

	private MeetingInfo meetingDetail;

	/** 会议主题 */
	@InjectView(R.id.tv_meetingdetail_title)
	private TextView tv_meetingdetail_title;

	/** 会议时间 */
	@InjectView(R.id.tv_meetingdetail_date)
	private TextView tv_meetingdetail_date;

	/** 会议时长 */
	@InjectView(R.id.tv_meetingdetail_duration)
	private TextView tv_meetingdetail_duration;

	/** 组织者 */
	@InjectView(R.id.tv_meetingdetail_organizer)
	private TextView tv_meetingdetail_organizer;

	/** 参会领导 */
	@InjectView(R.id.tv_meetingdetail_leader)
	private TextView tv_meetingdetail_leader;

	/** 会议级别 */
	@InjectView(R.id.tv_meetingdetail_meetinglevel)
	private TextView tv_meetingdetail_meetinglevel;

	/** 会议地点 */
	@InjectView(R.id.tv_meetingdetail_meetingplace)
	private TextView tv_meetingdetail_meetingplace;

	/** 多个会议地点所在布局 */
	@InjectView(R.id.ll_meetingdetail_meetingplace)
	private LinearLayout ll_meetingdetail_meetingplace;
	
	/**
	 * 签到表
	 */
	@InjectView(R.id.layout_meetingdetail_attendance)
	private RelativeLayout attendanceLayout;

	/** 退订按钮 */
	@InjectView(R.id.btn_cancelbook_meetingdetail)
	private Button btn_cancelbook_meetingdetail;

	/** 会议详情中 多个会议地址的集合 */
	List<DBMeetingRoomInfo> listRooms = new ArrayList<DBMeetingRoomInfo>();
	/** 会议Id */
	private String MeetingId = null;
	/** 组织者姓名 */
	private String organizer_name = null;
	
	/** 签到 */
	private CheckInService checkInService = new CheckInService(mContext);
	
	/**
	 * 签到跳转标识
	 */
	private boolean flag = true;
	
	@Override
	protected View onCreateView(BaseLayoutInflater arg0, ViewGroup arg1,
			Bundle arg2) {
		// TODO Auto-generated method stub
		return arg0.inflate(R.layout.fragment_meetingdetail_left, null);
	}

	/**
	 * @see cn.com.zte.android.app.fragment.BaseFragment#initData()
	 */
	@Override
	protected void initData() {
		mContext = getActivity();
		regReceiver();
		mService = new MeetingDetailService(mContext);

		initCancelBookBtn();
		initMeetingDetail();
		super.initData();
	}

	
	@Override
	protected void initViewEvents() {
		btn_cancelbook_meetingdetail
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						UserInfo info = new WelComeService(mContext).getUserInfo();
						if (info!=null&&info.getUID().equals(meetingDetail.getOPN())) {
//							if ("101037490".equals(meetingDetail.getOPN())) {
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
		attendanceLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//签到表
				Intent intent = new Intent(getActivity(),CheckInActivity.class);
				intent.putExtra("MeetingId",MeetingId);
				intent.putExtra("organizer_name", organizer_name);
				intent.putExtra("BD", meetingDetail.getBD());
				intent.putExtra("ED", meetingDetail.getED());
				startActivity(intent);
			}
		});
	}

	/**
	 * 签到结果处理
	 */
	BaseAsyncHttpResponseHandler<GetAttendanceOperationResponse> handler = new BaseAsyncHttpResponseHandler<GetAttendanceOperationResponse>(true){

		@Override
		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			super.onPopUpHttpErrorDialogPre(strTitle, strCode, strMsg);
			EmeetingToast.show(mContext, getString(R.string.respones_error));
		}


		@Override
		public void onFailureTrans(
				GetAttendanceOperationResponse responseModelVO) {
			super.onFailureTrans(responseModelVO);
			if (responseModelVO.getM()!=null&&!responseModelVO.getM().equals("")){
				EmeetingToast.show(mContext, responseModelVO.getM());
			}else{
				EmeetingToast.show(mContext,getString(R.string.MeetingChecked_fail));
			}
//			Log.d("zl", "签到失败"+responseModelVO.getM());
//				Intent intent = new Intent(getActivity(),ScanSuccessActivity.class);
//				intent.putExtra("isSuccess", "0");
//				intent.putExtra("checkedType", "0");
//				intent.putExtra("ResultInfo", responseModelVO.getM());
//				intent.putExtra("BD", meetingDetail.getBD());
//				intent.putExtra("ED", meetingDetail.getED());
//				startActivity(intent);
		}

		@Override
		public void onSuccessTrans(
				GetAttendanceOperationResponse responseModelVO) {
			super.onSuccessTrans(responseModelVO);
//			Log.d("zl", "签到成功信息："+responseModelVO.toString());
//				if (responseModelVO.getS().equals("true")) {
//						Intent intent = new Intent(getActivity(),ScanSuccessActivity.class);
//						intent.putExtra("isSuccess", "1");
//						intent.putExtra("checkedNum", responseModelVO.getM());
//						intent.putExtra("checkedType", "0");
//						intent.putExtra("BD", meetingDetail.getBD());
//						intent.putExtra("ED", meetingDetail.getED());
//						startActivity(intent);
//				}
			EmeetingToast.show(mContext,getString(R.string.MeetingChecked_success));
			btn_cancelbook_meetingdetail.setVisibility(View.INVISIBLE);
		}
		
	};

	/**
	 * 该方法用于:初始化退订按钮
	 * 
	 * @param
	 * @return void
	 */
	private void initCancelBookBtn() {
		if (meetingDetail == null)
			return;
		
		
		UserInfo info = new WelComeService(mContext).getUserInfo();
		if (info!=null&&info.getUID().equals(meetingDetail.getOPN())) {
//			if ("101037490".equals(meetingDetail.getOPN())) {
			attendanceLayout.setVisibility(View.VISIBLE);
			if (meetingDetail.getOS().equals(DataConst.OP_STATE_CAN_CANCELBOOK)) {
				btn_cancelbook_meetingdetail
						.setText(getString(R.string.mymeeting_opstate_cancelbook));
			} else if (meetingDetail.getOS().equals(DataConst.OP_STATE_CAN_STOP)) {
				btn_cancelbook_meetingdetail
						.setText(getString(R.string.mymeeting_opstate_stop));
			} else if (meetingDetail.getOS().equals(DataConst.OP_STATE_DISENABLE)) {
				btn_cancelbook_meetingdetail.setVisibility(View.GONE);
			}
		}else {
			attendanceLayout.setVisibility(View.GONE);
			if (meetingDetail.getISI()!=null&&meetingDetail.getISI().equals("Y")) {
				btn_cancelbook_meetingdetail.setVisibility(View.GONE);
			}else if(isComTime(meetingDetail)){
				btn_cancelbook_meetingdetail.setText(R.string.meeting_attendance);
				btn_cancelbook_meetingdetail.setVisibility(View.VISIBLE);
			}else{
				btn_cancelbook_meetingdetail.setText(R.string.meeting_attendance);
				btn_cancelbook_meetingdetail.setVisibility(View.GONE);
			}
		}
	}

	/**
	 * 当前时间是否大于结束时间或者小于开始时间
	 * @return
     */
	private boolean isComTime(MeetingInfo meetingDetail){
		if (meetingDetail==null) return false;
		if (meetingDetail.getBD()==null) return false;
		if (meetingDetail.getED()==null) return false;
		Date currentDate = DateFormatUtil.getServerTime(mContext);
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = df1.format(currentDate);
		if(DateFormatUtil.compareDate(dateString,meetingDetail.getBD())&&(DateFormatUtil.compareDate(meetingDetail.getED(),dateString))){
			return true;
		}else{
			return false;
		}
	}

	/** 初始化会议详细信息 */
	private void initMeetingDetail() {
		if (meetingDetail == null)
			return;
		Log.d("zl", "会议Id:"+meetingDetail.getMID());
		MeetingId = meetingDetail.getMID();
		organizer_name = meetingDetail.getOPCN();
		tv_meetingdetail_title.setText(meetingDetail.getMN());
		tv_meetingdetail_organizer.setText(meetingDetail.getOPCN());
		tv_meetingdetail_date.setText(getMeetingTime(meetingDetail.getBD(),
				meetingDetail.getED()));
		if (!TextUtils.isEmpty(meetingDetail.getMT())) {
			tv_meetingdetail_duration.setText(meetingDetail.getMT()
					+ getString(R.string.mbf_hour));
		}
		tv_meetingdetail_meetinglevel.setText(EmeetingUtil
				.getMeetingLevelName(meetingDetail.getML()));
		tv_meetingdetail_leader.setText(EmeetingUtil
				.getLeaderLevelName(meetingDetail.getPMLL()));

		List<DBMeetingRoomInfo> tmpListRoom = EmeetingUtil
				.getMeetingRoomList(meetingDetail.getBMRIDS());
		listRooms.clear();
		listRooms.addAll(tmpListRoom);
		initMeetingRoomView(listRooms);
	}

	/** 处理多个会议室时的退订界面及逻辑 **/
	private void initMeetingRoomView(List<DBMeetingRoomInfo> listroom) {

		if (listroom.size() > 1) {
			ll_meetingdetail_meetingplace.setVisibility(View.VISIBLE);
			tv_meetingdetail_meetingplace.setVisibility(View.GONE);

			ViewsUtil.setAdapterForLayout(ll_meetingdetail_meetingplace,
					new MeetingDetailRoomListAdapter(mContext, listroom,
							new ItemMeetingRoomListener() {
								@Override
								public void onItemCanceled(int position,
										DBMeetingRoomInfo meetingRoom) {
									// mService.cancelBookMeeting(meetingDetail.getMID(),
									// meetingRoom.getID(), new
									// HanderCancelBook(meetingRoom));
									cancelBook(meetingRoom);
								}
							}, meetingDetail));
		} else {
			ll_meetingdetail_meetingplace.setVisibility(View.GONE);
			tv_meetingdetail_meetingplace.setVisibility(View.VISIBLE);
			if (listroom.size() == 1) {
				LogTools.i("sql", "查询到会议室name:" + listroom.get(0).getMRC());
				tv_meetingdetail_meetingplace.setText(listroom.get(0).getMRC());
			}
		}
	}

	/**
	 * 该方法用于:取消退订
	 * 
	 * @param
	 * @return void
	 */
	private void cancelBook() {

		if (meetingDetail == null) {
			EmeetingToast.show(mContext, R.string.msg_no_meeting_to_cancelbook);
			return;
		}

		DlgConfirmBook dlg = new DlgConfirmBook(mContext,
				meetingDetail.getMN(), new OnConfirmDlgListener() {

					@Override
					public void onConfirmed() {

						String meetingId = meetingDetail.getMID();

						if (meetingDetail.getOS().equals(
								DataConst.OP_STATE_CAN_CANCELBOOK)) {
							mService.cancelBookMeeting(meetingId, "",
									handerCancelBook);
						} else if (meetingDetail.getOS().equals(
								DataConst.OP_STATE_CAN_STOP)) {
							mService.stopMeeting(meetingId, "",
									handerStopMeeting);
						}

						// if(meetingEntity!=null)
						// {
						// mService.cancelBookMeeting(meetingEntity.getMID(),
						// meetingEntity.getBMRIDS(), handerCancelBook);
						// mService.stopMeeting(meetingEntity.getMID(),
						// meetingEntity.getBMRIDS(), handerStopMeeting);
						// }
					}

					@Override
					public void onCanceled() {

					}
				});

		String leftTitle = getString(R.string.msg_confirm_cancelbook);
		if (meetingDetail.getOS().equals(DataConst.OP_STATE_CAN_CANCELBOOK)) {
			leftTitle = getString(R.string.msg_confirm_cancelbook);
		} else if (meetingDetail.getOS().equals(DataConst.OP_STATE_CAN_STOP)) {
			leftTitle = getString(R.string.msg_confirm_stop);
		} else if (meetingDetail.getOS().equals(DataConst.OP_STATE_DISENABLE)) {
		}
		dlg.setLeftTitle(leftTitle);
		dlg.show();
	}

	/**
	 * 该方法用于:退订会议室
	 * 
	 * @param @param meetingRoom
	 * @return void
	 */
	protected void cancelBook(final DBMeetingRoomInfo meetingRoom) {
		DlgConfirmBook dlg = new DlgConfirmBook(mContext, meetingRoom.getMRC(),
				new OnConfirmDlgListener() {

					@Override
					public void onConfirmed() {
						// mService.cancelBookMeeting(meetingDetail.getMID(),
						// meetingRoom.getID(), new
						// HanderCancelBook(meetingRoom));
						if (meetingDetail.getOS().equals(
								DataConst.OP_STATE_CAN_CANCELBOOK)) {
							mService.cancelBookMeeting(meetingDetail.getMID(),
									meetingRoom.getID(), new HanderCancelBook(
											meetingRoom));
						} else if (meetingDetail.getOS().equals(
								DataConst.OP_STATE_CAN_STOP)) {
							mService.stopMeeting(meetingDetail.getMID(),
									meetingRoom.getID(),
									new HanderStopMeetingRoom(meetingRoom));
						} else {
							mService.cancelBookMeeting(meetingDetail.getMID(),
									meetingRoom.getID(), new HanderCancelBook(
											meetingRoom));
						}
					}

					@Override
					public void onCanceled() {

					}
				});

		dlg.show();
		String leftTitle = getString(R.string.msg_confirm_cancelbook_meetingroom);
		if (meetingDetail.getOS().equals(DataConst.OP_STATE_CAN_CANCELBOOK)) {
			leftTitle = getString(R.string.msg_confirm_cancelbook_meetingroom);
		} else if (meetingDetail.getOS().equals(DataConst.OP_STATE_CAN_STOP)) {
			leftTitle = getString(R.string.msg_confirm_stop_meetingroom);
		} else if (meetingDetail.getOS().equals(DataConst.OP_STATE_DISENABLE)) {

		}
		dlg.setLeftTitle(leftTitle);
	}

	/** 获得会议时间 */
	private String getMeetingTime(String beginTime, String endTime) {
		if (TextUtils.isEmpty(beginTime) || TextUtils.isEmpty(endTime))
			return "";

		SimpleDateFormat tmpDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date beginDate = tmpDf.parse(beginTime);
			Date endDate = tmpDf.parse(endTime);

			// long meetingDuration=endDate.getTime()-beginDate.getTime();
			// String strMeetingDuration = meetingDuration/1000/60/60+"小时";
			// tv_meetingdetail_duration.setText(strMeetingDuration);

			SimpleDateFormat df_date = new SimpleDateFormat("yyyy-MM-dd");
			String meetingDate_1 = df_date.format(beginDate);
			String meetingDate_2 = df_date.format(endDate);
			if (!meetingDate_1.equals(meetingDate_2)) {
				SimpleDateFormat df_time = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm");
				return df_time.format(beginDate) + "-"
						+ df_time.format(endDate);
			} else {
				SimpleDateFormat df_time = new SimpleDateFormat("HH:mm");
				return meetingDate_1 + "　" + df_time.format(beginDate) + "-"
						+ df_time.format(endDate);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	// /** 获取会议详情handler*/
	// private BaseAsyncHttpResponseHandler<GetMeetingInfoResponse>
	// handlerGetMeetingDetail= new
	// BaseAsyncHttpResponseHandler<GetMeetingInfoResponse>(true){
	// @Override
	// public void onSuccessTrans(GetMeetingInfoResponse res) {
	// if(res.getD()!=null)
	// {
	// LogTools.i(TAG,"会议详情获取");
	// System.out.println(JsonUtil.toJson(res.getD()));
	// meetingDetail=res.getD();
	// initMeetingDetail();
	// }else
	// {
	// EmeetingToast.show(mContext, "会议详情获取失败");
	// }
	// };
	//
	// @Override
	// public void onFailure(Throwable th, String content) {
	// EmeetingToast.show(mContext, "请求失败");
	// };
	// @Override
	// public void onPopUpErrorDialog(String strTitle, String strCode, String
	// strMsg) {
	// if(strMsg==null) strMsg="请求失败";
	// EmeetingToast.show(mContext, strMsg);
	// };
	// };
	//

	/** 会议退订handler */
	private BaseAsyncHttpResponseHandler<GetCancelMeetingRoomResponse> handerCancelBook = new BaseAsyncHttpResponseHandler<GetCancelMeetingRoomResponse>(
			true) {
		// @Override
		// public void onFailure(Throwable th, String content) {
		// EmeetingToast.show(mContext, "请求失败");
		// };
		@Override
		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			if (!TextUtils.isEmpty(strMsg)) {
				EmeetingToast.showHttp(mContext, strMsg);
			}
		};

		@Override
		public void onFailureTrans(GetCancelMeetingRoomResponse responseModelVO) {
			if (responseModelVO != null
					&& TextUtils.isEmpty(responseModelVO.getM())) {
				EmeetingToast.show(mContext, responseModelVO.getM());
			}
		};

		@Override
		public void onPopUpErrorDialog(String strTitle, String strCode,
				String strMsg) {
			if (!TextUtils.isEmpty(strMsg)) {
				EmeetingToast.show(mContext, strMsg);
			}
		};

		@Override
		public void onSuccessTrans(GetCancelMeetingRoomResponse res) {
			if (res.getD() != null) {
				// 刷新我的会议
				Intent it_refresh = new Intent(
						DataConst.ACTION_MYMEETING_REFRESH_REMOVE);
				it_refresh.putExtra("data", meetingDetail);
				mContext.sendBroadcast(it_refresh);

				LogTools.i(TAG, "会议退订");
				System.out.println(JsonUtil.toJson(res.getD()));
				DlgCancelBookSuccess dlg = new DlgCancelBookSuccess(mContext,
						getString(R.string.msg_cancelbook_meeting_success));
				dlg.setOnCancelListener(new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						getActivity().finish();
					}
				});
				dlg.showToast(1500);
			}
		};
	};

	/** 结束会议handler */
	private BaseAsyncHttpResponseHandler<GetEndMeetingRoomResponse> handerStopMeeting = new BaseAsyncHttpResponseHandler<GetEndMeetingRoomResponse>(
			true) {
		@Override
		public void onSuccessTrans(GetEndMeetingRoomResponse res) {
			if (res.getD() != null) {
				// 刷新我的会议
				Intent it_refresh = new Intent(
						DataConst.ACTION_MYMEETING_REFRESH_REMOVE);
				it_refresh.putExtra("data", meetingDetail);
				mContext.sendBroadcast(it_refresh);

				LogTools.i(TAG, "结束会议");
				System.out.println(JsonUtil.toJson(res.getD()));
				DlgCancelBookSuccess dlg = new DlgCancelBookSuccess(mContext,
						getString(R.string.msg_stop_meeting_success));
				dlg.setOnCancelListener(new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						getActivity().finish();
					}
				});
				dlg.showToast(1500);
			}
		};

		@Override
		public void onFailureTrans(GetEndMeetingRoomResponse responseModelVO) {
			if (responseModelVO != null && responseModelVO.getM() != null
					&& !responseModelVO.getM().equals("")) {
				EmeetingToast.show(mContext, responseModelVO.getM());
			}else {
				EmeetingToast.show(mContext, HttpUtil.SERVER_REQUEST_FAIL);
			}
		};

		@Override
		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			EmeetingToast.show(mContext, HttpUtil.SERVER_REQUEST_FAIL);
		};
	};

	/** 结束会议室handler */
	private class HanderStopMeetingRoom extends
			BaseAsyncHttpResponseHandler<GetEndMeetingRoomResponse> {
		private DBMeetingRoomInfo meetingRoom;

		/**
		 * 该类构造方法:
		 * 
		 * @param
		 */
		public HanderStopMeetingRoom(DBMeetingRoomInfo meetingRoom) {
			super(true);
			this.meetingRoom = meetingRoom;
		}

		@Override
		public void onSuccessTrans(GetEndMeetingRoomResponse res) {
			if (res.getD() != null) {
				// 刷新我的会议
				listRooms.remove(meetingRoom);
				initMeetingRoomView(listRooms);
				EmeetingToast.show(mContext,
						getString(R.string.msg_stop_meetingroom_success));
				try {
					((MeetingDetailActivity) getActivity())
							.refreshChildFragment();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		@Override
		public void onFailure(Throwable th, String content) {
			EmeetingToast
					.show(mContext, getString(R.string.msg_request_failed));
		};

		@Override
		public void onPopUpErrorDialog(String strTitle, String strCode,
				String strMsg) {
			if (strMsg == null)
				strMsg = getString(R.string.msg_request_failed);
			EmeetingToast.show(mContext, strMsg);
		};

		@Override
		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			if (!TextUtils.isEmpty(strMsg)) {
				EmeetingToast.showHttp(mContext, strMsg);
			}
		};
	};

	/** 会议室退订handler */
	private class HanderCancelBook extends
			BaseAsyncHttpResponseHandler<GetCancelMeetingRoomResponse> {

		private DBMeetingRoomInfo meetingRoom;

		/**
		 * 该类构造方法:
		 * 
		 * @param
		 */
		public HanderCancelBook(DBMeetingRoomInfo meetingRoom) {
			super(true);
			this.meetingRoom = meetingRoom;
		}

		@Override
		public void onFailure(Throwable th, String content) {
			EmeetingToast
					.show(mContext, getString(R.string.msg_request_failed));
		};

		@Override
		public void onPopUpErrorDialog(String strTitle, String strCode,
				String strMsg) {
			if (strMsg == null)
				strMsg = getString(R.string.msg_request_failed);
			EmeetingToast.show(mContext, strMsg);
		};

		@Override
		public void onSuccessTrans(GetCancelMeetingRoomResponse res) {
			if (res.getD() != null) {
				// 刷新我的会议
				listRooms.remove(meetingRoom);
				initMeetingRoomView(listRooms);
				EmeetingToast.show(mContext,
						getString(R.string.msg_cancelbook_meetingroom_success));
				try {
					((MeetingDetailActivity) getActivity())
							.refreshChildFragment();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		@Override
		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			if (!TextUtils.isEmpty(strMsg)) {
				EmeetingToast.showHttp(mContext, strMsg);
			}
		};
	};

	/**
	 * @see cn.com.zte.android.app.fragment.BaseFragment#onDestroy()
	 */
	@Override
	public void onDestroyView() {
		if (mReceiver != null) {
			mContext.unregisterReceiver(mReceiver);
		}
		super.onDestroyView();
	}

	private MyReceiver mReceiver;

	/** 注册广播 */
	private void regReceiver() {
		mReceiver = new MyReceiver();
		IntentFilter filter = new IntentFilter(
				DataConst.ACTION_MEETING_DETAIL_REFRESH);
		mContext.registerReceiver(mReceiver, filter);
	}

	/** 广播接收器 */
	private class MyReceiver extends BroadcastReceiver {
		/**
		 * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
		 *      android.content.Intent)
		 */
		@Override
		public void onReceive(Context context, Intent intent) {

			if (intent.getAction().equals(
					DataConst.ACTION_MEETING_DETAIL_REFRESH)) {
				meetingDetail = (MeetingInfo) intent
						.getSerializableExtra("data");
				initMeetingDetail();
				initCancelBookBtn();
			}
		}

	}
	
	public void setMeetingInfo(MeetingInfo meetingEntity) {
		// TODO Auto-generated method stub
		this.meetingDetail = meetingEntity;
	}
	@Override
	public void onResume() {
		super.onResume();
		flag = true;
	}
}
