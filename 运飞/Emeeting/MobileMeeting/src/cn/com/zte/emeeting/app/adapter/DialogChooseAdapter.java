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
 * 会议类型adapter
 * 
 * @author liu.huanbo
 * 
 */

public class DialogChooseAdapter extends BaseAdapter {
	/** 上下文 */
	private Context mContext;
	/** 数据源 */
	private List<String> strs;
	/** 布局解析器 */
	LayoutInflater inflater;
	/** 默认无选中 */
	private int clickTemp = -1;

	private String selectString;

	public DialogChooseAdapter(Context context, List<String> listTypes,
			String selectString) {
		this.mContext = context;
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
		String str1 = strs.get(position);
		if (str1.equals(mContext.getResources().getString(
				R.string.tv_message_detail_two_leader))) {
			holder.textView.setText(mContext.getResources().getString(
					R.string.tv_message_detail_two_leader));
		} else if (str1.equals(mContext.getResources().getString(
				R.string.tv_message_detail_three_leader))) {

			holder.textView.setText(mContext.getResources().getString(
					R.string.tv_message_detail_three_leader));
		} else if (str1.equals(mContext.getResources().getString(
				R.string.tv_message_detail_four_leader))) {

			holder.textView.setText(mContext.getResources().getString(
					R.string.tv_message_detail_four_leader));
		} else if (str1.equals(mContext.getResources().getString(
				R.string.tv_message_detail_other_leader))) {

			holder.textView.setText(mContext.getResources().getString(
					R.string.tv_message_detail_other_leader));
		}
		String type = strs.get(position);
		if (type.equals(mContext.getResources().getString(
				R.string.tv_message_detail_routine_meeting))) {
			holder.textView.setText(mContext.getResources().getString(
					R.string.tv_message_detail_routine_meeting));
		} else if (type.equals(mContext.getResources().getString(
				R.string.tv_message_detail_phone_meeting))) {
			holder.textView.setText(mContext.getResources().getString(
					R.string.tv_message_detail_phone_meeting));

		} else if (type.equals(mContext.getResources().getString(
				R.string.tv_message_detail_train_meeting))) {
			holder.textView.setText(mContext.getResources().getString(
					R.string.tv_message_detail_train_meeting));

		} else if (type.equals(mContext.getResources().getString(
				R.string.tv_message_detail_net_meeting))) {
			holder.textView.setText(mContext.getResources().getString(
					R.string.tv_message_detail_net_meeting));

		} else if (type.equals(mContext.getResources().getString(
				R.string.tv_message_detail_cloud_meeting))) {
			holder.textView.setText(mContext.getResources().getString(
					R.string.tv_message_detail_cloud_meeting));
		} else if (type.equals(mContext.getResources().getString(
				R.string.tv_message_detail_video_meeting))) {
			holder.textView.setText(mContext.getResources().getString(
					R.string.tv_message_detail_video_meeting));
		}
		if (selectString != null && !selectString.equals("")) {
			holder.textView
					.setSelected(strs.get(position).equals(selectString));
		} else {
			holder.textView.setSelected(clickTemp == position);
		}

		return convertView;

	}

	class ViewHolder {
		/** 文字tv */
		TextView textView;
	}

}
