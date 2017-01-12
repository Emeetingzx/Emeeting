/**
 * 
 */
package cn.com.zte.emeeting.app.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import cn.com.zte.emeeting.app.util.EmeetingTimeUtil.TimeScale;
import cn.com.zte.emeeting.app.views.ViewShowUseTimeDraw;

import com.j256.ormlite.stmt.query.In;
/**
 * 该类用于:会议室所有列表使用情况展示适配器
 * @author wf
 */
public class MeetingRoomItemAdapter extends BaseAdapter {
	
	private Map<Integer,List<TimeScale>> dataList;
	
	private Context context;
	
//	private LayoutInflater inflater;
	
	public MeetingRoomItemAdapter(Map<Integer,List<TimeScale>> dataList, Context context) {
		super();
		this.dataList = dataList;
		this.context = context;
//		this.inflater = LayoutInflater.from(context);
	}

	/**
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
//		if(dataList==null)return 0;
//		return dataList.size();
		return 14;
	}

//	/**
//	 * @see android.widget.Adapter#getItem(int)
//	 */
//	@Override
//	public List<T>TimeScale getItem(int position) {
//		// TODO Auto-generated method stub
//		return dataList.get(position);
//	}

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
		
		if(convertView==null)
		{
			convertView =	new ViewShowUseTimeDraw(context);
		}
		convertView.setMinimumHeight(100);
		convertView.setMinimumWidth(100);
		((ViewShowUseTimeDraw)convertView).setTimeUsed((position+8)+"",dataList.get(position+8));
		return convertView;
	}

	/**
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

//	private class ViewHolder{
//		private ViewShowUseTime v_showusetime;
//	}
}
