/**
 * 
 */
package cn.com.zte.emeeting.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import cn.com.zte.emeeting.app.database.entity.shared.DBMeetingRoomInfo;
import cn.com.zte.emeeting.app.dialog.ShakeDialog.BtnClick;
import cn.com.zte.emeeting.app.response.entity.MeetingInfo;
import cn.com.zte.emeeting.app.util.DataConst;
import cn.com.zte.emeeting.app.util.EmeetingUtil;
import cn.com.zte.mobileemeeting.R;


/**
 * 该类用于:会议详情中多个会议室地址列表
 * @author wf
 */
public class MeetingDetailRoomListAdapter extends BaseAdapter {
	
	private List<DBMeetingRoomInfo> dataList;
	
	
	private Context context;
	
	private ItemMeetingRoomListener listener;
	
	private MeetingInfo meetingDetail;
	
	public MeetingDetailRoomListAdapter( Context context,List<DBMeetingRoomInfo> dataList,ItemMeetingRoomListener listener,MeetingInfo meetingDetail) {
		super();
		this.listener = listener;
		this.dataList =dataList;
		this.context = context;
		this.meetingDetail = meetingDetail;
	}

	/**
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		if(dataList==null)return 0;
		return dataList.size();
	}

	/**
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public DBMeetingRoomInfo getItem(int position) {
		// TODO Auto-generated method stub
		return dataList.get(position);
	}

	/**
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		int convertViewID =R.layout.item_lv_meetingdetail_meetingroom;
		
		
		if(convertView==null)
		{
			convertView=	LayoutInflater.from(context).inflate(convertViewID,null);
		}
		
		ViewHolder holder = null;
		
		if(convertView.getTag()==null)
		{
			holder = new ViewHolder();
			holder.tv_item_meetingroomname=(TextView) convertView.findViewById(R.id.tv_item_meetingroomname);
			holder.btn_item_meetingdetail_cancelbook=(Button) convertView.findViewById(R.id.btn_item_meetingdetail_cancelbook);
			convertView.setTag(holder);
		}else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tv_item_meetingroomname.setText(getItem(position).getMRC());
		holder.btn_item_meetingdetail_cancelbook.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(listener!=null)
				{
					listener.onItemCanceled(position, getItem(position));
				}
			}
		});
		
		if(meetingDetail.getOS().equals(DataConst.OP_STATE_CAN_CANCELBOOK))
		{
			holder.btn_item_meetingdetail_cancelbook.setText(R.string.mymeeting_opstate_cancelbook);
		}else if(meetingDetail.getOS().equals(DataConst.OP_STATE_CAN_STOP))
		{
			holder.btn_item_meetingdetail_cancelbook.setText(R.string.mymeeting_opstate_stop);
		}else if(meetingDetail.getOS().equals(DataConst.OP_STATE_DISENABLE))
		{
			holder.btn_item_meetingdetail_cancelbook.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}

	
	private class ViewHolder{
		private TextView tv_item_meetingroomname;
		private Button btn_item_meetingdetail_cancelbook;
	}


	public interface ItemMeetingRoomListener{
		void onItemCanceled(int position,DBMeetingRoomInfo  meetingRoom);
	}
	
}
