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
import cn.com.zte.emeeting.app.views.TimeChooseBotView;
import cn.com.zte.emeeting.app.views.TimeChooseBotView.onClickEventListener;
import cn.com.zte.emeeting.app.views.TimeDoubleChooseView;
import cn.com.zte.emeeting.app.views.TimeChooseTopView;
import cn.com.zte.mobileemeeting.R;

/**
 * 该类用于:公共控件 时间选择
 * @author LangK
 */
public class DialogBotTimeChoose extends Dialog {
	
	
	private Context mContext;
	
	private onClickEventListener clickEventListener;
	
	private TimeChooseBotView view ;
	
	/**
	 * 该类构造方法:
	 * @param context
	 */
	public DialogBotTimeChoose(Context context) {
		super(context,R.style.dlgStyle_Transparent_exit_edit);
		this.mContext = context;
		initView();
	}

	/**
	 * 该方法用于:
	 */
	private void initView() {
		view = new TimeChooseBotView(mContext);
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
		// TODO Auto-generated method stub
		if (topOrBottom==Gravity.BOTTOM) {
			this.getWindow().setGravity(Gravity.BOTTOM);
			this.getWindow().setWindowAnimations(R.style.dlgAnimation);
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
