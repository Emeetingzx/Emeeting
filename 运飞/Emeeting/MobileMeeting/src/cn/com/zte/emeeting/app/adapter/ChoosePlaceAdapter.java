/**
 * 
 */
package cn.com.zte.emeeting.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.com.zte.emeeting.app.database.entity.shared.DBMeetingRoomAddress;
import cn.com.zte.mobileemeeting.R;


/**
 * 该类用于:会议室所有列表使用情况展示适配器
 * @author wf
 */
public class ChoosePlaceAdapter extends BaseAdapter {
	
	private List<DBMeetingRoomAddress> dataList;
	
	private Context context;
	
	private int level=1;//1,2,3分别表示1,2,3级列表;
	
	private int choosePosition=-1;
	
	
	public ChoosePlaceAdapter(List<DBMeetingRoomAddress> dataList, Context context,int level) {
		super();
		this.dataList = dataList;
		this.context = context;
		this.level=level;
		this.choosePosition=-1;
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
	public DBMeetingRoomAddress getItem(int position) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		int convertViewID =R.layout.item_choose_place_level1;
		
		if(level==2)
		{
			convertViewID=R.layout.item_choose_place_level2;
		}else if(level==3)
		{
			convertViewID=R.layout.item_choose_place_level3;
		}
		
		if(convertView==null)
		{
			convertView=	LayoutInflater.from(context).inflate(convertViewID,null);
		}
		
		ViewHolder holder = null;
		
		if(convertView.getTag()==null)
		{
			holder = new ViewHolder();
			holder.tv_itemchooseplace_name=(TextView) convertView.findViewById(R.id.tv_itemchooseplace_name);
			holder.rl_bg_itemchooseplace=convertView.findViewById(R.id.rl_bg_itemchooseplace);
			if(level==2)
			{
				holder.iv_itemchooseplace=convertView.findViewById(R.id.iv_itemchooseplace);
			}
			convertView.setTag(holder);
		}else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tv_itemchooseplace_name.setText(getItem(position).getASC());
		if(choosePosition==position)
		{
			holder.rl_bg_itemchooseplace.setSelected(true);
			if(level==2)
			{
				holder.iv_itemchooseplace.setVisibility(View.VISIBLE);
			}
		}else
		{
			holder.rl_bg_itemchooseplace.setSelected(false);
			if(level==2)
			{
				holder.iv_itemchooseplace.setVisibility(View.GONE);
			}
		}
		
		
//		holder.autoll_item.setChildViewList(getItem(position).getViewList());
		
		return convertView;
	}

	
	private class ViewHolder{
		private TextView tv_itemchooseplace_name;
		private View iv_itemchooseplace;
		private View rl_bg_itemchooseplace;
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
	
	/** 设置选中的地址*/
	public void setChooseAddress(DBMeetingRoomAddress address)
	{
		if(address==null)return;
		for(int i = 0;i<dataList.size();i++)
		{
			if(address.getID().equals(dataList.get(i).getID()))
			{
				setChoosePosition(i);
				return;
			}
		}
	}
	
	
}
