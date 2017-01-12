package cn.com.zte.emeeting.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.com.zte.emeeting.app.database.entity.shared.DBMeetingRoomInfo;
import cn.com.zte.mobileemeeting.R;

/**
 * 地点适配器
 * 
 * @author liu.huanbo
 * 
 */
public class GdvPlaceAdapter extends BaseAdapter {
	/** 上下文 */
	private Context mContext;
	/** 地点 */
	private List<DBMeetingRoomInfo> listPlace;
	/** 布局解析器 */
	LayoutInflater layoutInflater;
	/** 默认第一个选中 */
	private int clickTemp = -1;
	/** 监听器 */
	private ItemTmpListener listener;

	public GdvPlaceAdapter(Context mContext, List<DBMeetingRoomInfo> listPlace,
			ItemTmpListener listener) {
		this.mContext = mContext;
		this.listPlace = listPlace;
		layoutInflater = LayoutInflater.from(mContext);
		this.listener = listener;
	}

	public int getClickTemp() {
		return clickTemp;
	}

	public GdvPlaceAdapter(Context mContext, List<DBMeetingRoomInfo> listPlace) {
		this.mContext = mContext;
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
		return listPlace.get(position).getMRC();
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
			holder.btnPlace = (TextView) convertView
					.findViewById(R.id.btn_click);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.btnPlace.setText(listPlace.get(position).getMRC());

		final ViewHolder tmpHolder = holder;

		holder.btnPlace.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (listener != null) {
					listener.onItemClicked(position, getItem(position));
				}
			}
		});
		// 位置相同变色
		if (clickTemp == position) {
			tmpHolder.btnPlace.setBackgroundColor(mContext.getResources()
					.getColor(R.color.PVFPreBgColor));
			tmpHolder.btnPlace.setTextColor(mContext.getResources().getColor(
					R.color.white));
		} else {
			tmpHolder.btnPlace.setBackgroundDrawable(mContext.getResources()
					.getDrawable(R.drawable.bg_shake_but));
			tmpHolder.btnPlace.setTextColor(mContext.getResources().getColor(
					R.color.black));
		}

		return convertView;

	}

	/** holder类 */
	class ViewHolder {

		private TextView btnPlace;
	}

	/** 点击item监听 */
	public interface ItemTmpListener {
		void onItemClicked(int position, String item);
	}
}
