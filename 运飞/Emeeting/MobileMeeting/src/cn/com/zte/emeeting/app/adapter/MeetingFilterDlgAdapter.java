/**
 * 
 */
package cn.com.zte.emeeting.app.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.com.zte.mobileemeeting.R;


/**
 * 该类用于:选择列表dlg适配器
 * @author wf
 */
public class MeetingFilterDlgAdapter extends BaseAdapter {
	
	private List<String> dataList;
	
	private Context context;
	
	public static final int defaultChoosePositon =0;
	
	private int choosePosition = defaultChoosePositon;
	
	private List<Integer> listChoosePostion ;
	/**
	 * 是否允许多选
	 * */
	private final boolean allowMulChoose;
	
	private ItemMeetingFilterListener listener;
	
	public MeetingFilterDlgAdapter( Context context,List<String> dataList,ItemMeetingFilterListener listener,boolean allowMulChoose) {
		super();
		this.dataList = dataList;
		this.context = context;
		this.listener = listener;
		this.allowMulChoose = allowMulChoose;
		listChoosePostion= new ArrayList<Integer>();
		listChoosePostion.add(choosePosition);
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
	public String getItem(int position) {
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
		
		int convertViewID =R.layout.item_meetingfilter_floor;
		
		
		if(convertView==null)
		{
			convertView=	LayoutInflater.from(context).inflate(convertViewID,null);
		}
		
		ViewHolder holder = null;
		
		if(convertView.getTag()==null)
		{
			holder = new ViewHolder();
			holder.tv_item_gv_meetinfilter=(TextView) convertView.findViewById(R.id.tv_item_gv_meetinfilter);
			holder.rl_bg_item_gv_meetinfilter=convertView.findViewById(R.id.rl_bg_item_gv_meetinfilter);
			convertView.setTag(holder);
		}else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tv_item_gv_meetinfilter.setText(getItem(position));
		
		if(allowMulChoose)
		{
			//若选中列表中有此position,则设置选中状态,否则 相反
			holder.rl_bg_item_gv_meetinfilter.setSelected(listChoosePostion.contains(position));
		}else
		{
			holder.rl_bg_item_gv_meetinfilter.setSelected(position==choosePosition);
		}
		
		holder.rl_bg_item_gv_meetinfilter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(listener!=null){
					listener.onItemClicked(position, getItem(position));
				}
			}
		});
		return convertView;
	}

	
	private class ViewHolder{
		private TextView tv_item_gv_meetinfilter;
		private View rl_bg_item_gv_meetinfilter;
	}

	public interface ItemMeetingFilterListener{
		void onItemClicked(int position,String item);
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
		if(!allowMulChoose)
		{
		this.choosePosition = choosePosition;
		}else
		{
			
			if(!listChoosePostion.contains(choosePosition))
			{
				listChoosePostion.add(choosePosition);
			}
			

			if(choosePosition==defaultChoosePositon)
			{
				listChoosePostion.clear();
				listChoosePostion.add(defaultChoosePositon);
			}else if(listChoosePostion.contains(defaultChoosePositon)&&listChoosePostion.size()>1)
			{
				listChoosePostion.remove(Integer.valueOf(defaultChoosePositon));
			}
		}
		notifyDataSetChanged();
	}

	/**
	 * @return the listChoosePostion
	 */
	public List<Integer> getListChoosePostion() {
		return listChoosePostion;
	}
	
	/**
	 * 允许多选中,取消选中用此方法
	 * */
	public boolean cancelChoosePosition(int position){
		
		boolean isRemoved = listChoosePostion.remove(Integer.valueOf(position));
		if(allowMulChoose)
		{
			if(listChoosePostion.size()==0)
			{
				setChoosePosition(defaultChoosePositon);
			}
		}
		notifyDataSetChanged();
		return isRemoved;
	}
	
	
	
}
