/**
 * 
 */
package cn.com.zte.emeeting.app.dialog;


import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import cn.com.zte.emeeting.app.views.TimeChooseBotDoubleView;
import cn.com.zte.emeeting.app.views.TimeChooseBotDoubleView.onClickEventListener;
import cn.com.zte.emeeting.app.views.TimeChooseBotView;
import cn.com.zte.emeeting.app.views.TimeDoubleChooseView;
import cn.com.zte.emeeting.app.views.TimeChooseTopView;
import cn.com.zte.mobileemeeting.R;

/**
 * 该类用于:公共控件 时间选择
 * @author LangK
 */
public class DialogBotDoubleTimeChoose extends Dialog {
	
	
	private Context mContext;
	
	private boolean isAllDay = false;
	
	private onClickEventListener clickEventListener;
	
	private TimeChooseBotDoubleView view ;
	
	/**
	 * 该类构造方法:
	 * @param context
	 */
	public DialogBotDoubleTimeChoose(Context context,boolean flag) {
		super(context,R.style.dlgStyle_Transparent_exit_edit);
		this.mContext = context;
		this.isAllDay = flag;
		initView();
	}
	
	
	/**
	 * 该类构造方法:
	 * @param context
	 */
	public DialogBotDoubleTimeChoose(Context context) {
		super(context,R.style.dlgStyle_Transparent_exit_edit);
		this.mContext = context;
		initView();
	}

	/**
	 * 该方法用于:
	 */
	private void initView() {
		view = new TimeChooseBotDoubleView(mContext,isAllDay);
		WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();
		display.getMetrics(dm);
		WindowManager.LayoutParams lp = this.getWindow().getAttributes();
		lp.width =  dm.widthPixels;
		lp.height =   dm.heightPixels;
		this.getWindow().setAttributes(lp);
		this.getWindow().setGravity(Gravity.BOTTOM);
		this.getWindow().setWindowAnimations(R.style.downtotopAnimation);
		
		view.setClickEventListener(this.clickEventListener);
		
		setContentView(view,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
	}
	
	/**
	 * 设置当前日期和时间
	 *@param dateString 日期格式必须为yyyy-MM-dd HH:mm HH:mm
	 */
	public void setCurrentDateAndTime(String dateString){
		if (!TextUtils.isEmpty(dateString)) {
			view.setCurrentDateAndTime(dateString);
		}
	}
	
	/**
	 * 设置布局在顶部还是底部
	 * 默认是在顶部
	 * @param topOrBottom
	 */
	private void setGravity(int topOrBottom) {
		if (topOrBottom==Gravity.BOTTOM) {
			this.getWindow().setGravity(Gravity.BOTTOM);
			this.getWindow().setWindowAnimations(R.style.downtotopAnimation);
		}else {
			this.getWindow().setGravity(Gravity.TOP);
			this.getWindow().setWindowAnimations(R.style.toptodownAnimation);
		}
	}
	
	@Override
	public void show() {
		super.show();
	}

	public onClickEventListener getClickEventListener() {
		return clickEventListener;
	}

	public void setClickEventListener(onClickEventListener clickEventListener) {
		this.clickEventListener = clickEventListener;
		view.setClickEventListener(this.clickEventListener);
	}
	
}
