package cn.com.zte.emeeting.app.views;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.com.zte.mobileemeeting.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * 顶部日期选择视图
 * @author LangK
 *
 */
public class TimeChooseBotView extends LinearLayout{

	
	private Context mContext;
	
	private onClickEventListener clickEventListener;
	/**
	 * 日期选择控件
	 */
	private TimeSingleChooseView timeview;
	
	public TimeChooseBotView(Context context) {
		super(context);
		this.mContext = context;
		initView();
	}
	
	

	public TimeChooseBotView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		initView();
	}
	
	public TimeChooseBotView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		initView();
	}
	
	private void initView() {
		View view = LayoutInflater.from(mContext).inflate(R.layout.view_timechoose_bot, null);
		LinearLayout content = (LinearLayout) view.findViewById(R.id.timechoose_timelayout);
		timeview = new TimeSingleChooseView(mContext);
		view.findViewById(R.id.timechoose_cancle).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (clickEventListener!=null) {
					clickEventListener.noTime();
				}
			}
		});
		view.findViewById(R.id.timechoose_conf).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (clickEventListener!=null) {
					clickEventListener.onSure(timeview.getTime());
				}
			}
		});
		
		content.addView(timeview);
		addView(view,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
	}
	/**
	 * 获取点击事件
	 * @param clickEventListener
	 */
	public onClickEventListener getClickEventListener() {
		return clickEventListener;
	}

	
	/**
	 * 设置当前日期和时间
	 */
	public void setCurrentDateAndTime(String dateString){
		timeview.setCurrentDateAndTime(dateString);
	}

	/**
	 * 设置点击事件
	 * @param clickEventListener
	 */
	public void setClickEventListener(onClickEventListener clickEventListener) {
		this.clickEventListener = clickEventListener;
	}

	/**
	 * 事件监听
	 * @author LangK
	 *
	 */
	public interface onClickEventListener{
		/**
		 * 取消选择
		 */
		public void noTime();
		/**
		 * 确定时间
		 * @param time	时间
		 * 返回格式 日期 开始时间 结束时间 比如2015-09-06 14:30 16:30 -1 未知错误 0 时间选择错误
		 */
		public void onSure(String time);
		
	}
}
