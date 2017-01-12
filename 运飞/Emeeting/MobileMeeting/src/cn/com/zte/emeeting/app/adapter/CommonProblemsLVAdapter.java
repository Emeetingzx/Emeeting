package cn.com.zte.emeeting.app.adapter;

import java.util.List;
import cn.com.zte.android.app.adapter.BaseModelAdapter;
import cn.com.zte.android.app.common.helper.BaseViewHolder;
import cn.com.zte.android.cache.ImageLoader;
import cn.com.zte.android.widget.dialog.DialogManager;
import cn.com.zte.emeeting.app.activity.HelpMeDetailActivity;
import cn.com.zte.emeeting.app.response.entity.CommonProblemsInfo;
import cn.com.zte.mobileemeeting.R;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 *  帮助详情界面常见问题ListView适配器
 *  @author sun.li
 *  */
public class CommonProblemsLVAdapter extends BaseModelAdapter<CommonProblemsInfo>{
	
	
	public CommonProblemsLVAdapter(Context context, int layoutResourceId,
			List<CommonProblemsInfo> objects) {
		super(context, layoutResourceId, objects);
	}
	
	@Override
	protected View initConvertView(int position, View convertView) {
		if(convertView == null){
			convertView = LayoutInflater.from(this.getContext()).inflate(
					this.getLayoutResourceId(), null);
		}	
		
		if(convertView.getTag() == null){
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.common_problems_list_item_text = (TextView)convertView.findViewById(R.id.common_problems_list_item_text);
			viewHolder.common_problems_list_item_layout = (RelativeLayout) convertView.findViewById(R.id.common_problems_list_item_layout);
			convertView.setTag(viewHolder);
		}
		return convertView;
	}
	
	@Override
	protected void initViews(int position, BaseViewHolder viewHolder,
			CommonProblemsInfo item) {
		super.initViews(position, viewHolder, item);
		ViewHolder view = (ViewHolder)viewHolder;
		if(item!=null){
			if(item.getCommonProblemsContent()!=null && !item.getCommonProblemsContent().equals("")){
				view.common_problems_list_item_text.setText(item.getCommonProblemsContent());
			}
		}	
	}
	
	@Override
	protected void initViewEvents(int position, BaseViewHolder viewHolder,
			CommonProblemsInfo item) {
		super.initViewEvents(position, viewHolder, item);
		ViewHolder view = (ViewHolder)viewHolder;
		
		if(item.getCommonProblemsLogo() >= 0){
			final int logo = item.getCommonProblemsLogo();
			
			view.common_problems_list_item_layout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(getContext(), HelpMeDetailActivity.class);
					intent.putExtra("HelpMeTypeLogo", logo);
					getContext().startActivity(intent);
				}
			});
		}
		
	}
	
     private static class ViewHolder extends BaseViewHolder{
    	 
    	 /** 常见问题标题*/
    	 private TextView common_problems_list_item_text;
    	 
    	 /** 常见问题按钮容器*/
    	 private RelativeLayout common_problems_list_item_layout;
	}
	
}
