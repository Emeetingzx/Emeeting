package cn.com.zte.emeeting.app.views;

import cn.com.zte.android.cache.ImageLoader;
import cn.com.zte.emeeting.app.response.entity.FoodAndRefreshmentsInfo;
import cn.com.zte.mobilebasedata.request.HttpUtil;
import cn.com.zte.mobileemeeting.R;
import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 增值服务的服务内容
 * @author LangK
 *
 */
public class ViewServiceFood extends LinearLayout {

	private Context mContext;
	
	private ImageView imageView;
	private ImageView imageSelectView;
	
	private TextView nameView;
	
	private FoodAndRefreshmentsInfo info;
	
	public ViewServiceFood(Context context) {
		super(context);
		this.mContext = context;
		initView();
	}
	public ViewServiceFood(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		initView();
	}
	public ViewServiceFood(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		initView();
	}

	
	private void initView() {
		// TODO Auto-generated method stub
		LayoutInflater.from(mContext).inflate(R.layout.view_service_book_food, this);
		imageView = (ImageView) findViewById(R.id.service_book_food_img);
		imageSelectView = (ImageView) findViewById(R.id.service_book_food_img_select);
		nameView = (TextView) findViewById(R.id.service_book_food_text);
	}
	
	
	/**
	 * 设置食品或者点心对象
	 * @param entity
	 */
	public void setFoodEntity(FoodAndRefreshmentsInfo entity){
		if (entity==null) {
			return ;
		}
		this.info = entity;
		if (entity.getAVSP()!=null&&!entity.getAVSP().equals("")) {
			Log.d("TAG", HttpUtil.getImageHost()+entity.getAVSP());
			ImageLoader.loadWebImage(mContext, imageView, Uri.parse(HttpUtil.getImageHost()+entity.getAVSP()),R.drawable.icon_service_default);
		}else {
			imageView.setImageResource(R.drawable.icon_service_default);
		}
		if (entity.getAVSN()!=null&&!entity.getAVSN().equals("")) {
			nameView.setText(entity.getAVSN());
		}
	}
	/**
	 * 获取食品信息
	 * @return
	 */
	public FoodAndRefreshmentsInfo getFoodInfo() {
		return info;
	}
	/**
	 * 设置是否被选中
	 */
	public void setSelected(boolean selected){
		if (selected) {
			imageSelectView.setVisibility(View.VISIBLE);
		}else {
			imageSelectView.setVisibility(View.GONE);
		}
//		imageView.setSelected(selected);
		nameView.setSelected(selected);
	}
}
