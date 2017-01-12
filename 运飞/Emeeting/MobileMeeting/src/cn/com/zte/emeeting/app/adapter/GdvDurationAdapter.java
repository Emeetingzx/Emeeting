package cn.com.zte.emeeting.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.com.zte.mobileemeeting.R;

/**
 * 时长适配器
 * 
 * @author liu.huanbo
 * 
 */

public class GdvDurationAdapter extends BaseAdapter {

	/** 上下文 */
	private Context mContext;
	/** 时长数据源 */
	private List<String> listDuration;
	/** 布局解析器 */
	LayoutInflater layoutInflater;
	/** 默认第一个选中 */
	private int clickTemp = -1;

	public GdvDurationAdapter(Context context, List<String> listDuration) {

		this.mContext = context;
		this.listDuration = listDuration;
		layoutInflater = LayoutInflater.from(mContext);
	}

	public void setSelection(int position) {
		clickTemp = position;
		notifyDataSetChanged();
	}

	public int getClickTemp() {
		return clickTemp;
	}

	@Override
	public int getCount() {
		return listDuration.size();
	}

	@Override
	public Object getItem(int position) {
		return listDuration.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.item_gridview, null);
			holder.btnDuration = (TextView) convertView
					.findViewById(R.id.btn_click);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.btnDuration.setText(listDuration.get(position));
		
		
		if (clickTemp == position||(clickTemp==-1&&position==0)) {
				holder.btnDuration.setBackgroundColor(mContext.getResources()
						.getColor(R.color.PVFPreBgColor));
				holder.btnDuration.setTextColor(mContext.getResources()
						.getColor(R.color.white));
		}else{
			holder.btnDuration.setBackgroundDrawable(mContext
					.getResources().getDrawable(R.drawable.bg_shake_but));
			holder.btnDuration.setTextColor(mContext.getResources()
					.getColor(R.color.black));
		}
		return convertView;
	}

	/** holder类 */
	class ViewHolder {
		/** 时间按钮 */
		TextView btnDuration;
	}

}
