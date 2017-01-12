package cn.com.zte.emeeting.app.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.com.zte.emeeting.app.response.entity.AddValueRegionInfo;
import cn.com.zte.mobileemeeting.R;

/**
 * 会议类型adapter
 * 
 * @author liu.huanbo
 * 
 */

public class AddValueServicePlaceAdapter extends BaseAdapter {
	/** 上下文 */
	private Context mContext;
	/** 数据源 */
	private List<AddValueRegionInfo> strs;
	/** 布局解析器 */
	LayoutInflater inflater;
	/** 默认无选中 */
	private int clickTemp = -1;

	private String selectString;
	
	public AddValueServicePlaceAdapter(Context mContext, List<AddValueRegionInfo> listTypes,String selectString) {
		this.mContext = mContext;
		this.selectString = selectString;
		strs = listTypes;
		inflater = LayoutInflater.from(mContext);
	}

	/** 选中位置 */
	public void selection(int position) {
		clickTemp = position;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return strs.size();
	}

	@Override
	public Object getItem(int position) {
		return strs.get(position);
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
			convertView = inflater.inflate(R.layout.item_dialog_others, null);
			holder.textView = (TextView) convertView
					.findViewById(R.id.tv_dialog_other);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textView.setText(strs.get(position).getAVSRN());
		
		if (selectString!=null&&!selectString.equals("")) {
			if (strs.get(position).getAVSRN().equals(selectString)) {
				holder.textView.setTextColor(mContext.getResources().getColor(
						R.color.PVFContentChooseTextColor));
			}else {
				holder.textView.setTextColor(mContext.getResources().getColor(
						R.color.PVFContentTextColor));
			}
		}else {
			if (clickTemp == position) {
				holder.textView.setTextColor(mContext.getResources().getColor(
						R.color.PVFContentChooseTextColor));
			} else {
				holder.textView.setTextColor(mContext.getResources().getColor(
						R.color.PVFContentTextColor));
			}
		}
		
		return convertView;

	}

	class ViewHolder {
		/** 文字tv */
		TextView textView;
	}

}
