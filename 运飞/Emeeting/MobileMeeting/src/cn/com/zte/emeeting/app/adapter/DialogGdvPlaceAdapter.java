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
 * 地点适配器
 * 
 * @author liu.huanbo
 * 
 */
public class DialogGdvPlaceAdapter extends BaseAdapter {
	/** 上下文 */
	private Context mContext;
	/** 地点 */
	private List<DBMeetingRoomAddress> listPlace;
	/** 布局解析器 */
	LayoutInflater layoutInflater;
	/** 默认第一个选中 */
	private int clickTemp = -1;

	public int getClickTemp() {
		return clickTemp;
	}

	public DialogGdvPlaceAdapter(Context context,
			List<DBMeetingRoomAddress> listPlace) {
		this.mContext = context;
		this.listPlace = listPlace;
		layoutInflater = LayoutInflater.from(mContext);
	}

	/** 选择位置刷新数据 */
	public void setSelection(int position) {
		clickTemp = position;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return listPlace.size();
	}

	@Override
	public String getItem(int position) {
		return listPlace.get(position).getID();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.item_gridview, null);
			holder.btnPlace = (TextView) convertView
					.findViewById(R.id.btn_click);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.btnPlace.setText(listPlace.get(position).getASC());
		if (clickTemp == position || (clickTemp == -1 && position == 0)) {
			holder.btnPlace.setBackgroundColor(mContext.getResources()
					.getColor(R.color.PVFPreBgColor));
			holder.btnPlace.setTextColor(mContext.getResources().getColor(
					R.color.white));
		} else {
			holder.btnPlace.setBackgroundDrawable(mContext.getResources()
					.getDrawable(R.drawable.bg_shake_but));
			holder.btnPlace.setTextColor(mContext.getResources().getColor(
					R.color.black));
		}
		return convertView;

	}

	/** holder类 */
	class ViewHolder {
		private TextView btnPlace;
	}

}
