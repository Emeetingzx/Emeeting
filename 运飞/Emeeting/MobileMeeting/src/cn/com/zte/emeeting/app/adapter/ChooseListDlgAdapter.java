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
import android.widget.TextView;
import cn.com.zte.mobileemeeting.R;


/**
 * 该类用于:选择列表dlg适配器
 * @author wf
 */
public class ChooseListDlgAdapter extends BaseAdapter {
	
	private List<String> dataList;
	
	private Context context;
	
	/** 默认进入选中的文本*/
	private String chooseStr="";
//	private ItemChooseListListener listener;
	
//	public ChooseListDlgAdapter( Context context,List<String> dataList,ItemChooseListListener listener) {
//		super();
//		this.dataList = dataList;
//		this.context = context;
//		this.listener = listener;
//	}
	
	public ChooseListDlgAdapter( Context context,List<String> dataList) {
		super();
		this.dataList = dataList;
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
		
		int convertViewID =R.layout.item_choose_list_dlg;
		
		
		if(convertView==null)
		{
			convertView=	LayoutInflater.from(context).inflate(convertViewID,null);
		}
		
		ViewHolder holder = null;
		
		if(convertView.getTag()==null)
		{
			holder = new ViewHolder();
			holder.tv_item_chooselist_dlg=(TextView) convertView.findViewById(R.id.tv_item_chooselist_dlg);
			holder.rl_bg_item_chooselist_dlg=convertView.findViewById(R.id.rl_bg_item_chooselist_dlg);
			convertView.setTag(holder);
		}else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tv_item_chooselist_dlg.setText(getItem(position));
		
		if(chooseStr.equals(getItem(position)))
		{
			holder.tv_item_chooselist_dlg.setSelected(true);
		}else
		{
			holder.tv_item_chooselist_dlg.setSelected(false);
		}
		
//		holder.rl_bg_item_chooselist_dlg.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				if(listener!=null){
//					listener.onItemClicked(position, getItem(position));
//				}
//			}
//		});
		return convertView;
	}

	
	private class ViewHolder{
		private TextView tv_item_chooselist_dlg;
		private View rl_bg_item_chooselist_dlg;
	}


	/**
	 * @return the chooseStr
	 */
	public String getChooseStr() {
		return chooseStr;
	}

	/**
	 * @param chooseStr the chooseStr to set
	 */
	public void setChooseStr(String chooseStr) {
		this.chooseStr = chooseStr;
		notifyDataSetChanged();
	}

//	public interface ItemChooseListListener{
//		void onItemClicked(int position,String item);
//	}
	
	
}
