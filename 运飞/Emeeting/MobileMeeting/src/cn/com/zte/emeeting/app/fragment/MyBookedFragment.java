package cn.com.zte.emeeting.app.fragment;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectView;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import cn.com.zte.android.app.fragment.BaseFragment;
import cn.com.zte.android.common.util.JsonUtil;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.android.resource.inflater.BaseLayoutInflater;
import cn.com.zte.emeeting.app.adapter.MyMeetingLvAdapter;
import cn.com.zte.emeeting.app.adapter.MyMeetingLvAdapter.MyMeetingItemListener;
import cn.com.zte.emeeting.app.appservice.MyMeetingService;
import cn.com.zte.emeeting.app.dialog.DlgConfirmBook;
import cn.com.zte.emeeting.app.dialog.DlgConfirmBook.OnConfirmDlgListener;
import cn.com.zte.emeeting.app.response.entity.MeetingInfo;
import cn.com.zte.emeeting.app.response.instrument.GetCancelMeetingRoomResponse;
import cn.com.zte.emeeting.app.response.instrument.GetEndMeetingRoomResponse;
import cn.com.zte.emeeting.app.response.instrument.GetUserRelevantMeetingInfoResponse;
import cn.com.zte.emeeting.app.util.DataConst;
import cn.com.zte.emeeting.app.util.EmeetingToast;
import cn.com.zte.emeeting.app.util.LogTools;
import cn.com.zte.emeeting.app.views.ViewListEmpty;
import cn.com.zte.emeeting.app.views.pulllistview.PullToRefreshLayout;
import cn.com.zte.emeeting.app.views.pulllistview.PullToRefreshLayout.OnRefreshListener;
import cn.com.zte.mobilebasedata.entity.PageInput;
import cn.com.zte.mobileemeeting.R;

public class MyBookedFragment extends BaseFragment{
	private static final String TAG="MyBookedFragment";
	
	
	@InjectView(R.id.emptyview_lv_empty)
	private ViewListEmpty  emptyview_lv_empty;
	
	/** 上下文 */
	private Context mContext;
	
	/** 我预定的会议的集合 */
	
	private List<MeetingInfo> listMeetings = new ArrayList<MeetingInfo>();
	
	/** listview */
	/** listview */
	@InjectView(R.id.refresh_listview)
	private PullToRefreshLayout refresh_listview;
	
	/**我预定会议适配器*/
	private MyMeetingLvAdapter adapter;
	
	private MyMeetingService mService;
	
	private String meetingType =DataConst.MEETINGBTYPE_MYBOOKED;
	
	
	
	
	@Override
	protected View onCreateView(BaseLayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	
		return inflater.inflate(R.layout.fragment_mybooked_meeting,null);
	}
	
	
	@Override
	protected void initData() {
		mContext = getActivity();
		mService=new MyMeetingService(mContext);
		super.initData();
	}
	
	
	
	private MyMeetingItemListener listenerMyMeeting=new MyMeetingItemListener() {
		
		@Override
		public void onItemClicked(int position, MeetingInfo meeting) {
//			Intent it = new Intent(mContext, MeetingDetailActivity.class);
//			it.putExtra("data", meeting);
//			startActivity(it);
		}
		
		@Override
		public void onButtonClicked(int position, MeetingInfo meeting) {
			cancelBook(meeting);
		}
	};
	
	private PageInput pageInput;
	
	private static final String PAGESIZE = "20";
	
	/** 初始化ListView*/
	private void initListView()
	{
		pageInput = new PageInput();
		pageInput.setPSIZE(PAGESIZE);
		pageInput.setPNO("1");
		
		refresh_listview.isPullUp(true);
		refresh_listview.isPullDown(true);
		
//		adapter = new MyMeetingLvAdapter(mContext,listMeetings,listenerMyMeeting);
//		refresh_listview.setAdapter(adapter);
		
		refresh_listview.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
				// TODO Auto-generated method stub
				pageInput.setPNO("1");
				mService.getListMyMeeting(meetingType, pageInput, handerGetMyMeeting);
			}
			
			@Override
			public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
				// TODO Auto-generated method stub
				pageInput.pageNoAdd();
				mService.getListMyMeeting(meetingType, pageInput, handerGetMyMeeting);
			}
		});
		
	}
	
	
	
	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		super.initViews();
		
		initListView();
		mService.getListMyMeeting(meetingType, pageInput, handerGetMyMeeting);
		
		emptyview_lv_empty.setEmptyText(getResourceString(R.string.msg_no_meeting));
		emptyview_lv_empty.setOnRefreshClick(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pageInput.setPNO("1");
				emptyview_lv_empty.setRefreshEnable(false);
				mService.getListMyMeeting(meetingType, pageInput, handerGetMyMeeting);
			}
		} );
	}
	

	/**
	 * 该方法用于:取消退订
	 * @param meeting 要退订的会议对象
	 * @param 
	 * @return void
	 */
	private void cancelBook(final MeetingInfo meeting) {
		DlgConfirmBook dlg = new DlgConfirmBook(mContext, meeting.getMN(), new OnConfirmDlgListener() {
			@Override
			public void onConfirmed() {
				String meetingId=meeting.getMID();
				if(meeting.getOS().equals(DataConst.OP_STATE_CAN_CANCELBOOK))
				{
					mService.cancelBookMeeting(meetingId,"", new HanderCancelBook(meeting));
				}else if(meeting.getOS().equals(DataConst.OP_STATE_CAN_STOP))
				{
					mService.stopMeeting(meetingId,"",new HanderStopMeeting(meeting));
				}
			}
			
			@Override
			public void onCanceled() {
				
			}
		});
		String leftTitle = getString(R.string.msg_confirm_cancelbook);
		if(meeting.getOS().equals(DataConst.OP_STATE_CAN_CANCELBOOK))
		{
			leftTitle= getString(R.string.msg_confirm_cancelbook);
		}else if(meeting.getOS().equals(DataConst.OP_STATE_CAN_STOP))
		{
			leftTitle= getString(R.string.msg_confirm_stop);
		}
		dlg.setLeftTitle(leftTitle);
		dlg.show();
	}

	@Override
	protected void initViewEvents() {
		super.initViewEvents();
	}

	
	
	
	/** 我预定的会议handler*/
	private BaseAsyncHttpResponseHandler<GetUserRelevantMeetingInfoResponse> handerGetMyMeeting= new BaseAsyncHttpResponseHandler<GetUserRelevantMeetingInfoResponse>(){
		@Override
		public void onSuccessTrans(GetUserRelevantMeetingInfoResponse res) {
			super.onSuccessTrans(res);
			
			refresh_listview.refreshFinish(PullToRefreshLayout.SUCCEED);
			refresh_listview.loadmoreFinish(PullToRefreshLayout.SUCCEED);
			
			
			if(res.getD()!=null)
			{
//				lv_my_booked_meetings.setEmptyView(tv_emptyview_lv);
				LogTools.i(TAG,"我预定的会议");
				System.out.println(JsonUtil.toJson(res.getD()));
				List<MeetingInfo> tmpList = res.getD();
				if (pageInput.getPNO().equals("1")) {
					listMeetings.clear();
					listMeetings.addAll(tmpList);
					adapter = new MyMeetingLvAdapter(mContext, listMeetings, listenerMyMeeting);
					refresh_listview.setAdapter(adapter);
				}else {
					listMeetings.addAll(tmpList);
					adapter.notifyDataSetChanged();
				}
				
				
				
				showEmptyView();
				
			}
		};
		
		@Override
		public void onFailureTrans(GetUserRelevantMeetingInfoResponse responseModelVO) {
			if (responseModelVO!=null&&responseModelVO.getM()!=null) {
				EmeetingToast.show(mContext, responseModelVO.getM());
			}
			showEmptyView();
			
			refresh_listview.refreshFinish(PullToRefreshLayout.FAIL);
			refresh_listview.loadmoreFinish(PullToRefreshLayout.FAIL);
			
		};
		
		/**
		 * 网络异常
		 */
		@Override
		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			showEmptyView();
			if(!TextUtils.isEmpty(strMsg))
			{
				EmeetingToast.showHttp(mContext, strMsg);
			}
			refresh_listview.refreshFinish(PullToRefreshLayout.FAIL);
			refresh_listview.loadmoreFinish(PullToRefreshLayout.FAIL);
			
		};
	};
	
	
	/** 显示是否有数据View*/
	private void showEmptyView()
	{
		emptyview_lv_empty.setRefreshEnable(true);
		if(adapter==null)return;
		if(adapter.getCount()>0)
		{
			emptyview_lv_empty.setVisibility(View.GONE);
			refresh_listview.setVisibility(View.VISIBLE);
		}else
		{
			emptyview_lv_empty.setVisibility(View.VISIBLE);
			refresh_listview.setVisibility(View.GONE);
		}
	}
	
	/** 退订会议handler*/
	private class HanderCancelBook extends BaseAsyncHttpResponseHandler<GetCancelMeetingRoomResponse>{
		private MeetingInfo meeting;
		public HanderCancelBook(MeetingInfo meeting) {
			super(true);
			this.meeting = meeting;
		}
//		String msg = "退订会议";
		@Override
		public void onSuccessTrans(GetCancelMeetingRoomResponse res) {
			if(res.getD()!=null)
			{
				Intent it_refresh = new Intent(DataConst.ACTION_MYMEETING_REFRESH_REMOVE);
				it_refresh.putExtra("data", meeting);
				mContext.sendBroadcast(it_refresh);
				
//				LogTools.i(TAG,"退订会议成功");
				EmeetingToast.show(mContext, R.string.msg_cancelbook_meeting_success);
				System.out.println(JsonUtil.toJson(res.getD()));
				listMeetings.remove(meeting);
				adapter.notifyDataSetChanged();
				showEmptyView();
				
			}
		};
		
		/**
		 * @see cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler#onFailureTrans(cn.com.zte.android.http.model.BaseHttpResponse)
		 */
		@Override
		public void onFailureTrans(GetCancelMeetingRoomResponse responseModelVO) {
			String strMsg = responseModelVO.getM();
			if(!TextUtils.isEmpty(strMsg))
			{
				EmeetingToast.show(mContext, strMsg);
			}
//			super.onFailureTrans(responseModelVO);
		}
		
		@Override
		public void onPopUpTransErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			if(!TextUtils.isEmpty(strMsg))
			{
				EmeetingToast.show(mContext, strMsg);
			}
			super.onPopUpTransErrorDialogPre(strTitle, strCode, strMsg);
		}
		
		/**
		 * 网络异常
		 */
		@Override
		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			if(!TextUtils.isEmpty(strMsg))
			{
				EmeetingToast.showHttp(mContext, strMsg);
			}
		};
	};
	
	/** 结束会议handler*/
	private class HanderStopMeeting extends BaseAsyncHttpResponseHandler<GetEndMeetingRoomResponse>{
//		String msg = "结束会议";
		
		private MeetingInfo meeting;
		
		public HanderStopMeeting(MeetingInfo meeting) {
			super(true);
			this.meeting = meeting;
		}


		@Override
		public void onSuccessTrans(GetEndMeetingRoomResponse res) {
			if(res.getD()!=null)
			{
				Intent it_refresh = new Intent(DataConst.ACTION_MYMEETING_REFRESH_REMOVE);
				it_refresh.putExtra("data", meeting);
				mContext.sendBroadcast(it_refresh);
				
//				LogTools.i(TAG,msg);
				System.out.println(JsonUtil.toJson(res.getD()));
				EmeetingToast.show(mContext, R.string.msg_stop_meeting_success);
				listMeetings.remove(meeting);
				adapter.notifyDataSetChanged();
				showEmptyView();
			}
		};
		
		/**
		 * @see cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler#onFailureTrans(cn.com.zte.android.http.model.BaseHttpResponse)
		 */
		@Override
		public void onFailureTrans(GetEndMeetingRoomResponse responseModelVO) {
			String strMsg = responseModelVO.getM();
			if(!TextUtils.isEmpty(strMsg))
			{
				EmeetingToast.show(mContext, strMsg);
			}
			super.onFailureTrans(responseModelVO);
		}
		/**
		 * @see cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler#onPopUpTransErrorDialogPre(java.lang.String, java.lang.String, java.lang.String)
		 */
		@Override
		public void onPopUpTransErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			if(!TextUtils.isEmpty(strMsg))
			{
				EmeetingToast.show(mContext, strMsg);
			}
			super.onPopUpTransErrorDialogPre(strTitle, strCode, strMsg);
		}
		
		/**
		 * 网络异常
		 */
		@Override
		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			if(!TextUtils.isEmpty(strMsg))
			{
				EmeetingToast.showHttp(mContext, strMsg);
			}
		};
	};
	
	
	/**
	 * @see android.support.v4.app.Fragment#onStart()
	 */
	@Override
	public void onStart() {
		regReceiver();
		super.onStart();
	}
	
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		if(mReceiver!=null)
		{
			mContext.unregisterReceiver(mReceiver);
		}
		super.onStop();
	}
	
	/**
	 * @see cn.com.zte.android.app.fragment.BaseFragment#onDestroy()
	 */
	@Override
	public void onDestroy() {
		
		super.onDestroy();
	}
	
	private MyReceiver mReceiver ;
	
	/** 注册广播*/
	private void regReceiver()
	{
		mReceiver = new MyReceiver();
		IntentFilter filter  = new IntentFilter(DataConst.ACTION_MYMEETING_REFRESH_REMOVE);
		filter.addAction(DataConst.ACTION_MYMEETING_REFRESH);
		filter.addAction(DataConst.ACTION_MEETING_DETAIL_REFRESH);
		mContext.registerReceiver(mReceiver, filter);
	}
	
	/** 广播接收器*/
	private class MyReceiver extends BroadcastReceiver{

		/**
		 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
		 */
		@Override
		public void onReceive(Context context, Intent intent) {
			
			if(intent.getAction().equals(DataConst.ACTION_MYMEETING_REFRESH))
			{
				pageInput.setPNO("1");
				mService.getListMyMeeting(meetingType, pageInput, handerGetMyMeeting);
			}
			
			/** 刷新列表里面详情*/
			if(intent.getAction().equals(DataConst.ACTION_MEETING_DETAIL_REFRESH))
			{
				MeetingInfo meeting=(MeetingInfo) intent.getSerializableExtra("data");
				if(meeting!=null&&!TextUtils.isEmpty(meeting.getMID())){
					if(listMeetings!=null)
					{
						for(int i = 0;i<listMeetings.size();i++)
						{

							if(meeting.getMID().equals(listMeetings.get(i).getMID()))
							{
								listMeetings.set(i, meeting);
								adapter.notifyDataSetChanged();
								
								showEmptyView();
								break;
							}
						}
					}
				}
			}
			
			if(intent.getAction().equals(DataConst.ACTION_MYMEETING_REFRESH_REMOVE))
			{
				MeetingInfo meeting=(MeetingInfo) intent.getSerializableExtra("data");
				if(meeting!=null&&!TextUtils.isEmpty(meeting.getMID())){
					if(listMeetings!=null)
					{
						for(MeetingInfo m:listMeetings)
						{

							if(meeting.getMID().equals(m.getMID()))
							{
								listMeetings.remove(m);
								adapter.notifyDataSetChanged();
								
								showEmptyView();
								break ;
							}
						}
					}
				}
				
			}
			
		}
		
	}
	
	/**刷新列表数据*/
	public void refreshListViewData()
	{
		pageInput.setPNO("1");
		mService.getListMyMeeting(meetingType, pageInput, handerGetMyMeeting);
	}
}
