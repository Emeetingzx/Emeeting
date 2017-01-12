/**
 * 
 */
package cn.com.zte.emeeting.app.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.zte.emeeting.app.response.entity.MeetingRoomInfo;
import cn.com.zte.emeeting.app.views.CircleBar;
import cn.com.zte.mobileemeeting.R;


/**
 * 该类用于:会议室查询结果
 * @author wf
 */
public class MeetingBookQueryResultAdapter extends BaseAdapter {
	
	private List<MeetingRoomInfo> dataList;
	
	private Context context;
	
	private MeetingBookListener listener;
	
	private String chooseTime ;
	
	public MeetingBookQueryResultAdapter(List<MeetingRoomInfo> dataList, Context context,MeetingBookListener listener,String chooseTime) {
		super();
		this.dataList = dataList;
		this.context = context;
		this.listener=listener;
		this.chooseTime=chooseTime;
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
	public MeetingRoomInfo getItem(int position) {
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
		
		
		if(convertView==null)
		{
			convertView=	LayoutInflater.from(context).inflate(R.layout.item_meetingbook_queryresult_lv,null);
		}
		
		ViewHolder holder = null;
		
		if(convertView.getTag()==null)
		{
			holder = new ViewHolder();
			holder.ll_item_lv_meetingbook=convertView.findViewById(R.id.ll_item_lv_meetingbook);
			holder.btn_mbg_book_meeting=(Button) convertView.findViewById(R.id.btn_mbg_book_meeting);
			holder.circlebar_item_mbf_meetinglv=(CircleBar) convertView.findViewById(R.id.circlebar_item_mbf_meetinglv);
			holder.iv_left_item_mbf_meetinglv=(ImageView) convertView.findViewById(R.id.iv_left_item_mbf_meetinglv);
			
			holder.iv_item_mbf_meetinglv_phone=(ImageView) convertView.findViewById(R.id.iv_item_mbf_meetinglv_phone);
			holder.iv_item_mbf_meetinglv_projector=(ImageView) convertView.findViewById(R.id.iv_item_mbf_meetinglv_projector);
			holder.iv_item_mbf_meetinglv_tv=(ImageView) convertView.findViewById(R.id.iv_item_mbf_meetinglv_tv);
			
			holder.tv_item_mbf_meetinglv_scale=(TextView) convertView.findViewById(R.id.tv_item_mbf_meetinglv_scale);
			holder.tv_item_mbf_meetinglv_name=(TextView) convertView.findViewById(R.id.tv_item_mbf_meetinglv_name);
			
			convertView.setTag(holder);
		}else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		if(dataList.size()!=0)
		{
			float timeScale = 0;
			
			try {
				
				if(!TextUtils.isEmpty(getItem(position).getMROR()))
				{
					timeScale =	Float.parseFloat(getItem(position).getMROR());
				}else
				{
					timeScale = 1;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			holder.circlebar_item_mbf_meetinglv.setProgress((int) (timeScale*100));
			
			String meetingroomName = getItem(position).getMRN();
			if(!TextUtils.isEmpty(meetingroomName))
			{
				meetingroomName = meetingroomName.replace("-", "－").replace(" ","");
			}
			holder.tv_item_mbf_meetinglv_name.setText(meetingroomName);
			holder.tv_item_mbf_meetinglv_scale.setText(getItem(position).getMRS());
			
			holder.iv_item_mbf_meetinglv_phone.setEnabled(getEnabled(getItem(position).getPS()));
			holder.iv_item_mbf_meetinglv_tv.setEnabled(getEnabled(getItem(position).getTVS()));
			holder.iv_item_mbf_meetinglv_projector.setEnabled(getEnabled(getItem(position).getPJS()));
		}
		
		if(TextUtils.isEmpty(chooseTime))//时段不限
		{
			holder.circlebar_item_mbf_meetinglv.setVisibility(View.VISIBLE);
			holder.iv_left_item_mbf_meetinglv.setVisibility(View.GONE);
		}else
		{
			holder.circlebar_item_mbf_meetinglv.setVisibility(View.GONE);
			holder.iv_left_item_mbf_meetinglv.setVisibility(View.VISIBLE);
		}
			holder.ll_item_lv_meetingbook.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(listener!=null)
					{
						listener.onMeetingBook(position,getItem(position));
					}
				}
			});
			holder.btn_mbg_book_meeting.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(listener!=null)
					{
						listener.onMeetingBook(position,getItem(position));
					}
				}
			});
		
//		holder.autoll_item.setChildViewList(getItem(position).getViewList());
		System.out.println(position);
		return convertView;
	}
	
	/**获取电话等有无状况*/
	private boolean getEnabled(String flag)
	{
		if(TextUtils.isEmpty(flag))
		{
			return false;
		}else
		{
			if("0".equals(flag))
			{
				return false;
			}
		}
		
		return true;
	}
	
	private class ViewHolder{
		private View ll_item_lv_meetingbook;
		private Button btn_mbg_book_meeting;
		private CircleBar circlebar_item_mbf_meetinglv;
		private ImageView iv_left_item_mbf_meetinglv;
		
		private ImageView iv_item_mbf_meetinglv_phone;
		private ImageView iv_item_mbf_meetinglv_projector;
		private ImageView iv_item_mbf_meetinglv_tv;
		private TextView tv_item_mbf_meetinglv_scale;
		private TextView tv_item_mbf_meetinglv_name;
	}

	public interface MeetingBookListener{
		public void onMeetingBook(int position,MeetingRoomInfo item);
	}

	/**
	 * @param chooseTime the chooseTime to set
	 */
	public void setChooseTime(String chooseTime) {
		this.chooseTime = chooseTime;
		notifyDataSetChanged();
	}
	
	
}
