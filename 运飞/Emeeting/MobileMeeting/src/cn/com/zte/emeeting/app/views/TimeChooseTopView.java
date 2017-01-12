package cn.com.zte.emeeting.app.views;

import cn.com.zte.emeeting.app.util.DataConst;
import cn.com.zte.emeeting.app.util.SharedPreferenceUtil;
import cn.com.zte.mobileemeeting.R;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 顶部日期选择视图
 * @author LangK
 *
 */
public class TimeChooseTopView extends LinearLayout{

	
	private Context mContext;
	
	private ImageView noTimeView;
	
	private onClickEventListener clickEventListener;
	/**
	 * 日期选择控件
	 */
	private TimeDoubleIntChooseView timeview;
	
	public TimeChooseTopView(Context context) {
		super(context);
		this.mContext = context;
		initView();
	}
	
	

	public TimeChooseTopView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		initView();
	}
	
	public TimeChooseTopView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		initView();
	}
	
	private void initView() {
		TimeDoubleIntChooseView.setDays(new SharedPreferenceUtil(DataConst.APPCONFIG,mContext).getInt(DataConst.APPCONFIG_MAXDAY, 3));
		View view = LayoutInflater.from(mContext).inflate(R.layout.view_timechoose_top, null);
//		LinearLayout content = (LinearLayout) view.findViewById(R.id.timechoose_timelayout);
		timeview = (TimeDoubleIntChooseView) view.findViewById(R.id.timechooseview);
//		timeview = new TimeDoubleChooseView(mContext);
		noTimeView = (ImageView) view.findViewById(R.id.timechoose_notime_image);
		view.findViewById(R.id.timechoose_notime).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (clickEventListener!=null) {
					clickEventListener.noTime();
				}
			}
		});
		view.findViewById(R.id.timechoose_conf_layout).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (clickEventListener!=null) {
					clickEventListener.onSure(timeview.getTime());
				}
			}
		});
		view.findViewById(R.id.timechoose_cancle_layout).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (clickEventListener!=null) {
					setNoTimeSelected();
					clickEventListener.onCancel();
				}
			}
		});
		
//		content.addView(timeview);
		addView(view,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
	}
	
	/**
	 * 时间不限选中
	 */
	public void setNoTimeSelected(){
		noTimeView.setImageResource(R.drawable.icon_notime_select);
	}
	/**
	 * 时间不限没有选中
	 */
	public void setNoTimeUnSelect(){
		noTimeView.setImageResource(R.drawable.icon_notime_unselect);
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
	 *@param dateString 日期格式必须为yyyy-MM-dd HH:mm HH:mm
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
		public void onCancel();
		
		/**
		 * 时段不限
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
