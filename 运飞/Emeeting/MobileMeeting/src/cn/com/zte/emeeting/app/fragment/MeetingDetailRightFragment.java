/**
 * 
 */
package cn.com.zte.emeeting.app.fragment;

import java.util.List;

import roboguice.inject.InjectView;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zte.android.app.fragment.BaseFragment;
import cn.com.zte.android.resource.inflater.BaseLayoutInflater;
import cn.com.zte.emeeting.app.adapter.MeetingDetailRoomDetailAdapter;
import cn.com.zte.emeeting.app.database.entity.shared.DBMeetingRoomInfo;
import cn.com.zte.emeeting.app.response.entity.MeetingInfo;
import cn.com.zte.emeeting.app.util.DataConst;
import cn.com.zte.emeeting.app.util.EmeetingUtil;
import cn.com.zte.emeeting.app.util.ObjectCopyUtil;
import cn.com.zte.mobileemeeting.R;

/**
 * 该类用于:会议室详情
 * @author wf
 */
public class MeetingDetailRightFragment extends BaseFragment {
	
	@InjectView(R.id.tv_meetingplace_nothings)
	private TextView tv_meetingplace_nothings;
	
	@InjectView(R.id.tv_meetingdetail_meetingscale)
	private TextView tv_meetingdetail_meetingscale;
	
	@InjectView(R.id.tv_meetingdetail_meetingprojector)
	private TextView tv_meetingdetail_meetingprojector;
	
	@InjectView(R.id.tv_meetingdetail_tv)
	private TextView tv_meetingdetail_tv;
	
	@InjectView(R.id.tv_meetingdetail_phone)
	private TextView tv_meetingdetail_phone;
	
	@InjectView(R.id.lv_meetingrooms_detail)
	private ListView lv_meetingrooms_detail;
	
	@InjectView(R.id.ll_meetingroom_detail)
	private View ll_meetingroom_detail;
	
	private DBMeetingRoomInfo meetingRoomDB;
	
	private Context mContext;
	@Override
	protected View onCreateView(BaseLayoutInflater arg0, ViewGroup arg1,
			Bundle arg2) {
		// TODO Auto-generated method stub
		return arg0.inflate(R.layout.fragment_meetingdetail_right, null);
	}
	
	/**
	 * @see cn.com.zte.android.app.fragment.BaseFragment#initData()
	 */
	@Override
	protected void initData() {
		mContext = getActivity();
		regReceiver();
		tv_meetingplace_nothings.setVisibility(View.GONE);
		initList(meetingInfo);
//		tv_meetingdetail_meetingscale.setText(meetingRoom.getMRS());
//		tv_meetingdetail_meetingprojector.setText(EmeetingUtil.getMeetingRoomProjectorState(meetingRoom.getPJS()));
//		tv_meetingdetail_tv.setText(EmeetingUtil.getMeetingRoomTvState(meetingRoom.getTVS()));
//		tv_meetingdetail_phone.setText(EmeetingUtil.getMeetingRoomPhoneState(meetingRoom.getPS()));
		super.initData();
	}
	
	
	private void initList(MeetingInfo meetingInfo)
	{
		if(meetingInfo!=null)
		{
			List<DBMeetingRoomInfo> listMeetingRooms = EmeetingUtil.getMeetingRoomList(meetingInfo.getBMRIDS());
			if(listMeetingRooms!=null&&!listMeetingRooms.isEmpty())
			{
				if(listMeetingRooms.size()==1)
				{
					ll_meetingroom_detail.setVisibility(View.VISIBLE);
					lv_meetingrooms_detail.setVisibility(View.GONE);
					meetingRoomDB=listMeetingRooms.get(0);
					if(meetingRoomDB!=null)
					{
						tv_meetingdetail_meetingscale.setText(EmeetingUtil.getMeetingRoomScale(meetingRoomDB.getMRS()));
						tv_meetingdetail_meetingprojector.setText(EmeetingUtil.getMeetingRoomProjectorState(meetingRoomDB.getPJS()));
						tv_meetingdetail_phone.setText(EmeetingUtil.getMeetingRoomPhoneState(meetingRoomDB.getPS()));
						tv_meetingdetail_tv.setText(EmeetingUtil.getMeetingRoomTvState(meetingRoomDB.getTVS()));
					}
				}else
				{
					ll_meetingroom_detail.setVisibility(View.GONE);
					lv_meetingrooms_detail.setVisibility(View.VISIBLE);
					lv_meetingrooms_detail.setAdapter(new MeetingDetailRoomDetailAdapter(mContext, listMeetingRooms));
				}
			}else
			{
				lv_meetingrooms_detail.setVisibility(View.GONE);
				ll_meetingroom_detail.setVisibility(View.GONE);
				tv_meetingplace_nothings.setVisibility(View.VISIBLE);
			}
		}
	}
	
	/**
	 * @see cn.com.zte.android.app.fragment.BaseFragment#initViews()
	 */
	@Override
	protected void initViews() {
		super.initViews();
	}

	/**
	 * @see cn.com.zte.android.app.fragment.BaseFragment#onDestroy()
	 */
	@Override
	public void onDestroy() {
		if(mReceiver!=null)
		{
			mContext.unregisterReceiver(mReceiver);
		}
		super.onDestroy();
	}
	
	private MyReceiver mReceiver ;
	
	/** 注册广播*/
	private void regReceiver()
	{
		mReceiver = new MyReceiver();
		IntentFilter filter  = new IntentFilter(DataConst.ACTION_MEETING_DETAIL_REFRESH);
		mContext.registerReceiver(mReceiver, filter);
	}
	
	/** 广播接收器*/
	private class MyReceiver extends BroadcastReceiver{
		/**
		 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
		 */
		@Override
		public void onReceive(Context context, Intent intent) {
			
			if(intent.getAction().equals(DataConst.ACTION_MEETING_DETAIL_REFRESH))
			{
				MeetingInfo	meetingDetail =(MeetingInfo) intent.getSerializableExtra("data");
				initList(meetingDetail);
			}
		}
		
	}
	MeetingInfo meetingInfo;
	public void setMeetingInfo(MeetingInfo meetingEntity) {
		// TODO Auto-generated method stub
		this.meetingInfo = meetingEntity;
//		meetingInfo = ObjectCopyUtil.copy(meetingEntity,meetingInfo);
	}
}
