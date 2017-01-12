package cn.com.zte.emeeting.app.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import cn.com.zte.mobileemeeting.R;
/** 网络请求进度dlg*/
public class DlgProgress extends Dialog {
	/**上下文对象*/
	private Context context;
	
	
	public DlgProgress(Context context) {
		super(context, R.style.dlgStyle_no_animation);
		this.context = context;
		setContentView(R.layout.dlg_progress);
		init();
	}

	@SuppressWarnings("deprecation")
	private void init() {
		WindowManager manager = ((Activity) context).getWindowManager();
		Display display = manager.getDefaultDisplay();
		WindowManager.LayoutParams lp = this.getWindow().getAttributes();
		lp.width = (int) display.getWidth();
		this.getWindow().setAttributes(lp);
		this.getWindow().setGravity(Gravity.CENTER);
		this.setCancelable(false);
//		this.getWindow().setWindowAnimations(R.style.dlgAnimation);
	}


}
