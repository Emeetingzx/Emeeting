/**
 * 
 */
package cn.com.zte.emeeting.app.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.inject.Inject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.com.zte.android.app.fragment.BaseFragment;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.android.resource.inflater.BaseLayoutInflater;
import cn.com.zte.emeeting.app.activity.MeetingDetailActivity;
import cn.com.zte.emeeting.app.activity.control.ControlFilterActivity;
import cn.com.zte.emeeting.app.adapter.MeetingControlAdapter;
import cn.com.zte.emeeting.app.adapter.MeetingControlAdapter.EventListener;
import cn.com.zte.emeeting.app.adapter.MeetingDetailRoomListAdapter;
import cn.com.zte.emeeting.app.adapter.MeetingDetailRoomListAdapter.ItemMeetingRoomListener;
import cn.com.zte.emeeting.app.appservice.MeetingControlFragmentService;
import cn.com.zte.emeeting.app.appservice.MeetingDetailService;
import cn.com.zte.emeeting.app.database.entity.shared.DBMeetingRoomInfo;
import cn.com.zte.emeeting.app.dialog.DlgConfirmBook;
import cn.com.zte.emeeting.app.dialog.DlgExtend;
import cn.com.zte.emeeting.app.dialog.DlgConfirmBook.OnConfirmDlgListener;
import cn.com.zte.emeeting.app.dialog.DlgExtend.OnBookSuccessDlgListener;
import cn.com.zte.emeeting.app.response.entity.MeetingInfo;
import cn.com.zte.emeeting.app.response.entity.MeetingJoinInfo;
import cn.com.zte.emeeting.app.response.instrument.GetCancelMeetingRoomResponse;
import cn.com.zte.emeeting.app.response.instrument.GetEndMeetingRoomResponse;
import cn.com.zte.emeeting.app.response.instrument.GetMeetingJoinInfoResponse;
import cn.com.zte.emeeting.app.response.instrument.GetMeetingOperationResponse;
import cn.com.zte.emeeting.app.response.instrument.GetMeetingProlongInfoResponse;
import cn.com.zte.emeeting.app.response.instrument.GetMeetingProlongResponse;
import cn.com.zte.emeeting.app.util.DataConst;
import cn.com.zte.emeeting.app.util.EmeetingToast;
import cn.com.zte.emeeting.app.util.EmeetingUtil;
import cn.com.zte.emeeting.app.util.LogTools;
import cn.com.zte.emeeting.app.util.ObjectCopyUtil;
import cn.com.zte.emeeting.app.util.ViewsUtil;
import cn.com.zte.emeeting.app.views.CustomToast;
import cn.com.zte.emeeting.app.views.ListViewForScrollView;
import cn.com.zte.emeeting.app.views.SlideCutListView;
import cn.com.zte.emeeting.app.views.SlideCutListView.RemoveDirection;
import cn.com.zte.emeeting.app.views.SlideCutListView.RemoveListener;
import cn.com.zte.mobileemeeting.R;
import roboguice.inject.InjectView;

/**
 * 该类为会议控制界面
 * 
 * @author LangK
 */
public class MeetingDetailControlFragment extends BaseFragment {
	private static final String TAG = "MeetingDetailControlFragment";

	/**
	 * 网络请求间隔
	 */
	private static final long TIMEINTERVAL = 8000;

	private Context mContext;

	private MeetingInfo meetingInfo;

	/**
	 * search layout
	 */
	@InjectView(R.id.meeting_control_search)
	private RelativeLayout searchLayout;

	/**
	 * time extend button
	 */
	@InjectView(R.id.meeting_control_time_extend)
	private Button extendButton;

	/**
	 * listview for scrollview
	 */
	@InjectView(R.id.meeting_control_listview)
	private SlideCutListView listView;

	private List<MeetingJoinInfo> list = new ArrayList<MeetingJoinInfo>();

	private MeetingControlAdapter adapter;

	private MeetingControlFragmentService mService;

    protected boolean isCreated = false;

    private final Timer timer = new Timer();
    private MyTimerTask task;
    private String meetingID = "";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            if (getUserVisibleHint()){
                if (meetingID!=null&&!meetingID.equals("")) {
                	Log.d(TAG,"net request");
                	mService.getAllMeetingJoinInfo(getMeetingJoinHandler, meetingID);
				}else {
                	Log.d(TAG,"meeting id is null don't net request");
				}
            }
        }
    };

    @Override
	protected View onCreateView(BaseLayoutInflater arg0, ViewGroup arg1,
			Bundle arg2) {
		// TODO Auto-generated method stub
        isCreated = true;
        Log.d(TAG,"onCreateView");
		return arg0.inflate(R.layout.fragment_meeting_control, null);
	}

	/**
	 * @see BaseFragment#initData()
	 */
	@Override
	protected void initData() {
		mContext = getActivity();
		mService = new MeetingControlFragmentService(mContext);
		adapter = new MeetingControlAdapter(mContext, list);
		listView.setAdapter(adapter);
		if (meetingInfo != null) {
			meetingID = meetingInfo.getMID();
			initMeetingDetail();
			if (meetingInfo.getIPRL()!=null&&meetingInfo.getIPRL().equals("Y")) {
				extendButton.setVisibility(View.VISIBLE);
			}else {
				extendButton.setVisibility(View.GONE);
			}
		}
        super.initData();
	}

	@Override
	protected void initViewEvents() {
		// TODO Auto-generated method stub
		super.initViewEvents();
		extendButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (meetingID!=null&&!meetingID.equals("")) {
					mService.extendMeeting(proLongInfoHandler,
						meetingID);
				}else {
					CustomToast.show(mContext, "获取会议信息失败");
				}
			}
		});
		searchLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext,
						ControlFilterActivity.class);
				intent.putExtra("id",meetingInfo!=null?meetingInfo.getMID():"" );
				startActivity(intent);
			}
		});
		adapter.setEventListener(new EventListener() {

			@Override
			public void removeItem(int position, MeetingJoinInfo info) {
				// TODO Auto-generated method stub
//				CustomToast.show(mContext, "点击删除菜单");
				listView.restroeView();
				operationMeetingJoin(meetingInfo.getMID(), info.getNO(), info.getID(), MeetingJoinInfo.DELETEOPARETION,info.getTT());
			
			}

			@Override
			public void callEvent(int position, MeetingJoinInfo info) {
				// TODO Auto-generated method stub
				operationMeetingJoin(meetingInfo.getMID(), info.getNO(), info.getID(), MeetingJoinInfo.CALLOPARETION,info.getTT());
				info.setST(MeetingJoinInfo.CALLINGSTATE);
				adapter.notifyDataSetChanged();
			}

			@Override
			public void hangupEvent(int position, MeetingJoinInfo info) {
				// TODO Auto-generated method stub
				operationMeetingJoin(meetingInfo.getMID(), info.getNO(), info.getID(), MeetingJoinInfo.HANGUPOPARETION,info.getTT());
			
			}

			@Override
			public void queitEvent(int position, MeetingJoinInfo info) {
				// TODO Auto-generated method stub
				operationMeetingJoin(meetingInfo.getMID(), info.getNO(), info.getID(), MeetingJoinInfo.QUEITOPARETION,info.getTT());
			
			}

			@Override
			public void restoreCallEvent(int position, MeetingJoinInfo info) {
				// TODO Auto-generated method stub
				operationMeetingJoin(meetingInfo.getMID(), info.getNO(), info.getID(), MeetingJoinInfo.RESTORECALLOPARETION,info.getTT());
			
			}

		});
	}

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isCreated) return;
        if (isVisibleToUser){
            Log.d(TAG,"setUserVisibleHint show");
            startRecycle();
        }else{
            Log.d(TAG,"setUserVisibleHint hide");
            stopRecycle();
        }
    }

    /**
     * 开始循环
     */
    private void startRecycle(){
        if (timer!=null){
            if (task!=null)task.cancel();
        }
        task = new MyTimerTask();
        timer.schedule(task, 0, TIMEINTERVAL);
    }

    /**
     * 停止循环
     */
    private void stopRecycle(){
        if (task!=null)task.cancel();
    }

	@Override
	public void onResume() {
		super.onResume();
        Log.d(TAG,"onResume");

        startRecycle();
	}

	@Override
	public void onPause() {
		super.onPause();
        Log.d(TAG,"onPause");
        stopRecycle();
	}

	/**
	 * 获取会场信息
	 */
	public void getMeetingJoinDetail() {
		mService.getAllMeetingJoinInfo(getMeetingJoinHandler, meetingInfo.getMID());
	}

	/**
	 * 操作会场信息
	 * 
	 * @param ID 会议ID
	 * @param Number	终端号码
	 * @param TermId	终端ID
	 * @param OperationType	操作类型 0:挂断1：呼叫2：静音3：取消静音 4 ：删除
	 * @param type 终端类型
	 */
	public void operationMeetingJoin(String ID,String Number,String TermId,String OperationType,String type){
		mService.operationMeetingJoin(operationHandler, ID, Number, TermId, OperationType,type);
	}

	/** init meetingroom control info */
	private void initMeetingDetail() {
		if (adapter != null) {
			adapter.notifyDataSetChanged();
		}
	}

	/**
	 * @see BaseFragment#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 获取会议下所有会场信息接口返回处理
	 */
	BaseAsyncHttpResponseHandler<GetMeetingJoinInfoResponse> getMeetingJoinHandler = new BaseAsyncHttpResponseHandler<GetMeetingJoinInfoResponse>() {
		public void onSuccessTrans(GetMeetingJoinInfoResponse responseModelVO) {
			if (responseModelVO.getD() != null) {
				list.clear();
				list.addAll(responseModelVO.getD());
				initMeetingDetail();
			} else {
				Log.d(TAG, "获取会场信息为空");
				list.clear();
				initMeetingDetail();
			}
		};

		public void onFailureTrans(GetMeetingJoinInfoResponse responseModelVO) {
//			if (responseModelVO != null && responseModelVO.getM() != null
//					&& !responseModelVO.getM().equals("")) {
//				CustomToast.show(mContext, responseModelVO.getM());
//			} else {
//				CustomToast.show(mContext, R.string.respones_false);
//			}
			list.clear();
			initMeetingDetail();
			Log.d(TAG, "接口请求失败！");
		};

		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
//			CustomToast.show(mContext, R.string.respones_error);
			Log.d(TAG, "接口请求网络异常！");
		};
	};

	/**
	 * 操作会场信息接口返回处理
	 */
	BaseAsyncHttpResponseHandler<GetMeetingOperationResponse> operationHandler = new BaseAsyncHttpResponseHandler<GetMeetingOperationResponse>() {
		public void onSuccessTrans(GetMeetingOperationResponse responseModelVO) {
			startRecycle();
		};

		public void onFailureTrans(GetMeetingOperationResponse responseModelVO) {
			if (responseModelVO != null && responseModelVO.getM() != null
					&& !responseModelVO.getM().equals("")) {
				CustomToast.show(mContext, responseModelVO.getM());
			} else {
				CustomToast.show(mContext, R.string.respones_false);
			}
		};

		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			CustomToast.show(mContext, R.string.respones_error);
		};
	};
	
	/**
	 * 延长会议冲突dlg
	 */
	private DlgExtend diaExtend;
	
	/**
	 * 获取会议延长信息接口返回处理
	 */
	BaseAsyncHttpResponseHandler<GetMeetingProlongInfoResponse> proLongInfoHandler = new BaseAsyncHttpResponseHandler<GetMeetingProlongInfoResponse>(true) {
		public void onSuccessTrans(GetMeetingProlongInfoResponse responseModelVO) {
			if (responseModelVO.getD() != null) {
				//展示冲突信息
				diaExtend = new DlgExtend(mContext,getString(R.string.meeting_extend_conflict_sure),meetingInfo.getED(),responseModelVO.getD(), new OnBookSuccessDlgListener() {
					
					@Override
					public void onRightClicked() {
						// TODO Auto-generated method stub
						diaExtend.dismiss();
					}
					
					@Override
					public void onLeftClicked() {
						// TODO Auto-generated method stub
						mService.doExtendMeeting(proLongHandler, meetingID);
					}
				} );
			} else {
				diaExtend = new DlgExtend(mContext, new OnBookSuccessDlgListener() {
					
					@Override
					public void onRightClicked() {
						// TODO Auto-generated method stub
						diaExtend.dismiss();
					}
					
					@Override
					public void onLeftClicked() {
						// TODO Auto-generated method stub
						mService.doExtendMeeting(proLongHandler, meetingID);
					}
				} );
			}
			diaExtend.show();
		};

		public void onFailureTrans(GetMeetingProlongInfoResponse responseModelVO) {
			if (responseModelVO != null && responseModelVO.getM() != null
					&& !responseModelVO.getM().equals("")) {
				CustomToast.show(mContext, responseModelVO.getM());
			} else {
				CustomToast.show(mContext, R.string.respones_false);
			}
		};

		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			CustomToast.show(mContext, R.string.respones_error);
		};
	};
	/**
	 * 会议延长信息接口返回处理
	 */
	BaseAsyncHttpResponseHandler<GetMeetingProlongResponse> proLongHandler = new BaseAsyncHttpResponseHandler<GetMeetingProlongResponse>(true) {
		public void onSuccessTrans(GetMeetingProlongResponse responseModelVO) {
			CustomToast.show(mContext, R.string.meeting_extend_success);
			extendButton.setVisibility(View.GONE);
			MyMeetingFragment.isRefresh = true;
		};

		public void onFailureTrans(GetMeetingProlongResponse responseModelVO) {
			if (responseModelVO != null && responseModelVO.getM() != null
					&& !responseModelVO.getM().equals("")) {
				CustomToast.show(mContext, responseModelVO.getM());
			} else {
				CustomToast.show(mContext, R.string.respones_false);
			}
		};

		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			CustomToast.show(mContext, R.string.respones_error);
		};
	};

    class MyTimerTask extends TimerTask{
        @Override
        public void run() {
            // TODO Auto-generated method stub
            Log.i(TAG, "run...");
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }

    }

	public void setMeetingInfo(MeetingInfo info) {
		// TODO Auto-generated method stub
		this.meetingInfo = info;
//		meetingInfo = ObjectCopyUtil.copy(info,meetingInfo);
	}

}
