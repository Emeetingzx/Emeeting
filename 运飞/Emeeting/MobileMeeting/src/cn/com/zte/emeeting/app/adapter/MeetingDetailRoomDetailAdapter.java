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
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.zte.emeeting.app.adapter.MeetingDetailRoomListAdapter.ItemMeetingRoomListener;
import cn.com.zte.emeeting.app.database.entity.shared.DBMeetingRoomInfo;
import cn.com.zte.emeeting.app.util.EmeetingUtil;
import cn.com.zte.mobileemeeting.R;


/**
 * 该类用于:多个会议室时会议详情界面
 * @author wf
 */
public class MeetingDetailRoomDetailAdapter extends BaseAdapter {
	
	private List<DBMeetingRoomInfo> dataList;
	
	private Context context;
	
	private int choosePosition = -1;
	
	public MeetingDetailRoomDetailAdapter( Context context,List<DBMeetingRoomInfo> dataList) {
		super();
		this.dataList =dataList;
		this.context = context;
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
		
		int convertViewID =R.layout.item_lv_meetingroom_detail;
		
		
		if(convertView==null)
		{
			convertView=	LayoutInflater.from(context).inflate(convertViewID,null);
		}
		
		ViewHolder holder = null;
		
		if(convertView.getTag()==null)
		{
			holder = new ViewHolder();
			holder.iv_ico_item_expand=(ImageView) convertView.findViewById(R.id.iv_ico_item_expand);
			holder.tv_item_meetingroomname_detail=(TextView) convertView.findViewById(R.id.tv_item_meetingroomname_detail);
			holder.tv_meetingdetail_meetingscale=(TextView) convertView.findViewById(R.id.tv_meetingdetail_meetingscale);
			holder.tv_meetingdetail_tv=(TextView) convertView.findViewById(R.id.tv_meetingdetail_tv);
			holder.tv_meetingdetail_phone=(TextView) convertView.findViewById(R.id.tv_meetingdetail_phone);
			holder.tv_meetingdetail_meetingprojector=(TextView) convertView.findViewById(R.id.tv_meetingdetail_meetingprojector);
			holder.rl_item_meetingroomdetail_top=convertView.findViewById(R.id.rl_item_meetingroomdetail_top);
			holder.ll_item_meetingroomdetail_content=convertView.findViewById(R.id.ll_item_meetingroomdetail_content);
			convertView.setTag(holder);
		}else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		DBMeetingRoomInfo meetingRoomDB=getItem(position);
		holder.tv_item_meetingroomname_detail.setText(getItem(position).getMRC());
		if(meetingRoomDB!=null)
		{
			holder.tv_meetingdetail_meetingscale.setText(EmeetingUtil.getMeetingRoomScale(meetingRoomDB.getMRS()));
			holder.tv_meetingdetail_meetingprojector.setText(EmeetingUtil.getMeetingRoomProjectorState(meetingRoomDB.getPJS()));
			holder.tv_meetingdetail_phone.setText(EmeetingUtil.getMeetingRoomPhoneState(meetingRoomDB.getPS()));
			holder.tv_meetingdetail_tv.setText(EmeetingUtil.getMeetingRoomTvState(meetingRoomDB.getTVS()));
		}
		
		if(choosePosition == position)
		{
			holder.rl_item_meetingroomdetail_top.setSelected(true);
			holder.iv_ico_item_expand.setSelected(true);
			holder.ll_item_meetingroomdetail_content.setVisibility(View.VISIBLE);
		}else
		{
			holder.rl_item_meetingroomdetail_top.setSelected(false);
			holder.iv_ico_item_expand.setSelected(false);
			holder.ll_item_meetingroomdetail_content.setVisibility(View.GONE);
		}
		
		holder.rl_item_meetingroomdetail_top.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(choosePosition==position)
				{
					setChoosePosition(-1);
				}else
				{
					setChoosePosition(position);
				}
			}
		});
		return convertView;
	}

	
	private class ViewHolder{
		private ImageView iv_ico_item_expand;
		private TextView tv_item_meetingroomname_detail;
		private TextView tv_meetingdetail_meetingscale;
		private TextView tv_meetingdetail_tv;
		private TextView tv_meetingdetail_phone;
		private TextView tv_meetingdetail_meetingprojector;
		private View rl_item_meetingroomdetail_top;
		private View ll_item_meetingroomdetail_content;
	}


	/**
	 * @return the choosePosition
	 */
	public int getChoosePosition() {
		return choosePosition;
	}

	/**
	 * @param choosePosition the choosePosition to set
	 */
	public void setChoosePosition(int choosePosition) {
		this.choosePosition = choosePosition;
		notifyDataSetChanged();
	}

	
}
