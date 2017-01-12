package cn.com.zte.emeeting.app.views;

import java.util.List;

import cn.com.zte.android.cache.ImageLoader;
import cn.com.zte.emeeting.app.response.entity.FoodAndRefreshmentsInfo;
import cn.com.zte.mobileemeeting.R;
import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 增值服务的服务内容布局
 * 
 * @author LangK
 * 
 */
public class ViewServiceFoodLayout extends LinearLayout {

	private Context mContext;
	/**
	 * 增值服务内容
	 */
	private ViewServiceFood serviceFood01;
	/**
	 * 增值服务内容
	 */
	private ViewServiceFood serviceFood02;
	/**
	 * 增值服务内容
	 */
	private ViewServiceFood serviceFood03;
	/**
	 * 当前位置
	 */
	private int count = 0;

	private List<String> selectList;

	public ViewServiceFoodLayout(Context context, List<String> selectList) {
		super(context);
		this.mContext = context;
		this.selectList = selectList;
		initView();
	}

	public ViewServiceFoodLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		initView();
	}

	public ViewServiceFoodLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		LayoutInflater.from(mContext).inflate(
				R.layout.view_service_book_food_layout, this);
		serviceFood01 = (ViewServiceFood) findViewById(R.id.service_book_food_layout01);
		serviceFood02 = (ViewServiceFood) findViewById(R.id.service_book_food_layout02);
		serviceFood03 = (ViewServiceFood) findViewById(R.id.service_book_food_layout03);
		
		initViewEvent();
	}

	/**
	 * 初始化事件
	 */
	private void initViewEvent() {
		// TODO Auto-generated method stub
		serviceFood01.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (serviceFood01.getFoodInfo()!=null) {
					if (selectList.contains(serviceFood01.getFoodInfo().getID())) {
						selectList.remove(serviceFood01.getFoodInfo().getID());
						serviceFood01.setSelected(false);
					}else {
						selectList.add(serviceFood01.getFoodInfo().getID());
						serviceFood01.setSelected(true);
					}
				}
			}
		});
		serviceFood02.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (serviceFood02.getFoodInfo()!=null) {
					if (selectList.contains(serviceFood02.getFoodInfo().getID())) {
						selectList.remove(serviceFood02.getFoodInfo().getID());
						serviceFood02.setSelected(false);
					}else {
						selectList.add(serviceFood02.getFoodInfo().getID());
						serviceFood02.setSelected(true);
					}
				}
			}
		});
		serviceFood03.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (serviceFood03.getFoodInfo()!=null) {
					if (selectList.contains(serviceFood03.getFoodInfo().getID())) {
						selectList.remove(serviceFood03.getFoodInfo().getID());
						serviceFood03.setSelected(false);
					}else {
						selectList.add(serviceFood03.getFoodInfo().getID());
						serviceFood03.setSelected(true);
					}
				}
			}
		});

	}

	/**
	 * 设置食品或者点心对象
	 * 
	 * @param entity
	 */
	public void addFoodEntity(FoodAndRefreshmentsInfo entity) {
		count++;
		switch (count) {
		case 1:
			serviceFood01.setFoodEntity(entity);
			break;
		case 2:
			serviceFood02.setFoodEntity(entity);
			serviceFood02.setVisibility(View.VISIBLE);
			break;
		case 3:
		default:
			serviceFood03.setFoodEntity(entity);
			serviceFood03.setVisibility(View.VISIBLE);
			break;
		}
	}
}
