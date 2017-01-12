package cn.com.zte.emeeting.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.zte.emeeting.app.response.entity.MeetingInfo;
import cn.com.zte.mobileemeeting.R;
/**
 * 参会人员适配器
 * 
 * @author liu.huanbo
 *
 */
public class JoinPersonAdapter extends BaseAdapter {
	/** 上下文 */
	private Context mContext;
	/** 集合列表 */
	private List<MeetingInfo> lists;
	/** 解析器 */
	LayoutInflater layoutInflater;

	public JoinPersonAdapter(Context mContext, List<MeetingInfo> lists) {
		this.mContext = mContext;
		this.lists = lists;
		layoutInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	@Override
	public Object getItem(int arg0) {
		return lists.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder;

		if (arg1 == null) {

			holder = new ViewHolder();
			arg1 = layoutInflater.inflate(R.layout.item_join_person, null);
			holder.ivPhoto = (ImageView) arg1
					.findViewById(R.id.iv_head_portrait);
			holder.tvName = (TextView) arg1.findViewById(R.id.tv_name);
			holder.vtId = (TextView) arg1.findViewById(R.id.tv_id);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();

		}
		holder.tvName.setText(lists.get(arg0).getOPCN());
		holder.vtId.setText(lists.get(arg0).getOPN());
		holder.ivPhoto.setImageDrawable(mContext.getResources().getDrawable(
				R.drawable.ic_launcher));
		return arg1;
	}

	class ViewHolder {
		/** 图像 */
		ImageView ivPhoto;
		/** 姓名 */
		TextView tvName;
		/** 工号 */
		TextView vtId;
	}
}
