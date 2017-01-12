package cn.com.zte.emeeting.app.views;

import cn.com.zte.mobileemeeting.R;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ViewListEmpty extends LinearLayout{
	
	/**
	 * 文本
	 */
	private TextView textView;
	
	/**
	 * 刷新
	 */
	private TextView refreshView;
	
	private ProgressBar progressBar;
	
	private Context mContext;

	public ViewListEmpty(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		initView();
		// TODO Auto-generated constructor stub
	}

	public ViewListEmpty(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		initView();
		// TODO Auto-generated constructor stub
	}

	public ViewListEmpty(Context context) {
		super(context);
		this.mContext = context;
		initView();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 初始化视图
	 */
	private void initView() {
		// TODO Auto-generated method stub
		LayoutInflater.from(mContext).inflate(R.layout.view_list_empty, this);
		textView = (TextView) findViewById(R.id.view_list_empty_text);
		refreshView = (TextView) findViewById(R.id.view_list_empty_refresh);
		progressBar = (ProgressBar) findViewById(R.id.view_list_empty_progress);
		progressBar.setVisibility(View.GONE);
	}
	
	/**
	 * 设置显示文本
	 */
	public void setEmptyText(String text) {
		if (!TextUtils.isEmpty(text)) {
			textView.setText(text);
		}
	}
	
	/**
	 * 设置刷新事件
	 * @param listener	View的click事件监听
	 */
	public void setOnRefreshClick(OnClickListener listener){
		if (listener!=null) {
			refreshView.setOnClickListener(listener);
		}
	}
	
	/**
	 * 设置刷新事件是否可用
	 * @param isEnable
	 */
	public void setRefreshEnable(boolean isEnable) {
		if (isEnable) {
			progressBar.setVisibility(View.GONE);
		}else {
			progressBar.setVisibility(View.VISIBLE);
		}
		refreshView.setEnabled(isEnable);
	}

}
