package cn.com.zte.emeeting.app.adapter;

import java.util.List;

import com.lidroid.xutils.db.converter.DateColumnConverter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.zte.emeeting.app.activity.MeetingDetailActivity;
import cn.com.zte.emeeting.app.activity.MeetingDetailMessageActivity;
import cn.com.zte.emeeting.app.response.entity.MeetingInfo;
import cn.com.zte.emeeting.app.util.DataConst;
import cn.com.zte.emeeting.app.util.EmeetingUtil;
import cn.com.zte.mobileemeeting.R;

/**
 * 我的会议列表adapter
 */

public class MyMeetingLvAdapter extends BaseAdapter {
	/** 上下文 */
	private Context mContext;
	/** 数据源 */
	private List<MeetingInfo> list;
	/** 布局解析器 */
	private LayoutInflater inflater;
	
	/** item 监听器**/
	private MyMeetingItemListener listener;
	
	private String btnTextCancelbook;
	private String btnTextStop;
	
	private int type =0;

	public MyMeetingLvAdapter(Context mContext, List<MeetingInfo> listMeetings,MyMeetingItemListener listener,int type) {
		this.mContext = mContext;
		this.list = listMeetings;
		this.type=type;
		System.out.println("构造方法type:"+this.type);
		inflater = LayoutInflater.from(mContext);
		btnTextCancelbook=mContext.getResources().getString(R.string.mymeeting_opstate_cancelbook);
		btnTextStop=mContext.getResources().getString(R.string.mymeeting_opstate_stop);
		this.listener=listener;
	}
	
	public MyMeetingLvAdapter(Context mContext, List<MeetingInfo> listMeetings,MyMeetingItemListener listener) {
		this.mContext = mContext;
		this.list = listMeetings;
		inflater = LayoutInflater.from(mContext);
		btnTextCancelbook=mContext.getResources().getString(R.string.mymeeting_opstate_cancelbook);
		btnTextStop=mContext.getResources().getString(R.string.mymeeting_opstate_stop);
		this.listener=listener;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public MeetingInfo getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		final MeetingInfo item = getItem(position);
		if(convertView==null)
		{
			convertView = inflater.inflate(R.layout.item_mymeeting_lv, null);
		}
		
		if (convertView.getTag()==null) {
			holder = new ViewHolder();
			holder.ll_item_lv_mymeeting = convertView
					.findViewById(R.id.ll_item_lv_mymeeting);
			holder.btn_item_mymeeting = (Button) convertView.findViewById(R.id.btn_item_mymeeting);
			holder.tv_item_placename_mymeeting = (TextView) convertView.findViewById(R.id.tv_item_placename_mymeeting);
			holder.tv_item_meetingtime_mymeeting = (TextView) convertView.findViewById(R.id.tv_item_meetingtime_mymeeting);
			holder.tv_item_person_mymeeting = (TextView) convertView.findViewById(R.id.tv_item_person_mymeeting);
			holder.tv_item_meetingname_mymeeting = (TextView) convertView.findViewById(R.id.tv_item_meetingname_mymeeting);
			holder.iv_item_lv_mymeeting_meetingtype = (ImageView) convertView.findViewById(R.id.iv_item_lv_mymeeting_meetingtype);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		setText(holder.tv_item_meetingname_mymeeting, item.getMN());
		setText(holder.tv_item_person_mymeeting, item.getOPCN());
		setText(holder.tv_item_meetingtime_mymeeting, EmeetingUtil.getMeetingTime(item.getBD(), item.getED()));
		setText(holder.tv_item_placename_mymeeting,getMeetingRoomName(item));
		holder.iv_item_lv_mymeeting_meetingtype.setImageResource(getMeetingTypeIco(item));
		
		
		System.out.println(item.getOS()+"");
		System.out.println("type:"+this.type);

		
		if(this.type!=0)
		{
			holder.btn_item_mymeeting.setVisibility(View.GONE);
		}else
		{
			
			if(DataConst.OP_STATE_DISENABLE.equals(item.getOS()))
			{
				holder.btn_item_mymeeting.setVisibility(View.GONE);
			}else
			{
				holder.btn_item_mymeeting.setVisibility(View.VISIBLE);
				if(DataConst.OP_STATE_CAN_CANCELBOOK.equals(item.getOS()))
				{
//					holder.btn_item_mymeeting.setText("退订");
					holder.btn_item_mymeeting.setText(btnTextCancelbook);
				}else if(DataConst.OP_STATE_CAN_STOP.equals(item.getOS()))
				{
//					holder.btn_item_mymeeting.setText("结束");
					holder.btn_item_mymeeting.setText(btnTextStop);
				}
			}
		}
		
		holder.ll_item_lv_mymeeting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(TextUtils.isEmpty(item.getMTP())){
					Intent it = new Intent(mContext, MeetingDetailActivity.class);
					it.putExtra("data", item);
					mContext.startActivity(it);
				}
				
				if(item.getMTP().equals("2")||item.getMTP().equals("6"))
				{
					Intent it = new Intent(mContext, MeetingDetailMessageActivity.class);
					it.putExtra("MID", item.getMID());
					mContext.startActivity(it);
				}else
				{
					Intent it = new Intent(mContext, MeetingDetailActivity.class);
					it.putExtra("data", item);
					mContext.startActivity(it);
				}
				
//				if(listener!=null)
//				{
//					listener.onItemClicked(position, getItem(position));
//				}
			}
		});
		holder.btn_item_mymeeting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(listener!=null)
				{
					listener.onButtonClicked(position, getItem(position));
				}
			}
		});
		
		return convertView;

	}
	
	private void setText(TextView tv,String text)
	{
		if(!TextUtils.isEmpty(text))
		{
			tv.setText(text);
		}
	}
	/** 获取会议类型图标*/
	private int getMeetingTypeIco(MeetingInfo item)
	{
		if(DataConst.MT_PHONE.equals(item.getMTP()))
		{
			return R.drawable.ico_meetingtype_phone;
		}else if(DataConst.MT_VIDEO.equals(item.getMTP()))
		{
			return R.drawable.ico_meetingtype_video;
		}else
		{
			return R.drawable.ico_meetingtype_normal;
		}
	}
	/** 获取会议室名称*/
	private String getMeetingRoomName(MeetingInfo item)
	{
		if(DataConst.MT_PHONE.equals(item.getMTP()))
		{
			return "电话会议桥";
		}else if(DataConst.MT_VIDEO.equals(item.getMTP()))
		{
			return "视频会议桥";
		}else
		{
			return EmeetingUtil.getMeetingRoomNames(item.getBMRIDS());
		}
	}

	class ViewHolder {
		View ll_item_lv_mymeeting;
		Button btn_item_mymeeting;
		TextView tv_item_placename_mymeeting;
		TextView tv_item_meetingtime_mymeeting;
		TextView tv_item_person_mymeeting;
		TextView tv_item_meetingname_mymeeting;
		ImageView iv_item_lv_mymeeting_meetingtype;
	}

	
	public interface MyMeetingItemListener{
		void onButtonClicked(int position,MeetingInfo meeting);
		void onItemClicked(int position,MeetingInfo meeting);
	}


	
}
