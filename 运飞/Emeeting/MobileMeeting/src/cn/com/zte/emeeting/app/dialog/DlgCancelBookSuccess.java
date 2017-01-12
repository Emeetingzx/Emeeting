/**
 * 
 */
package cn.com.zte.emeeting.app.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Shader.TileMode;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Html;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.zte.mobileemeeting.R;
/**
 * 用于退订成功提示
 * @author wf
 */
public  class DlgCancelBookSuccess extends Dialog {
	
	private Context mContext;
	
	private String title;
	
	
	/**
	 * 该类构造方法:
	 * @param @param context
	 */
	public DlgCancelBookSuccess(Context context,String title) {
		super(context,R.style.dlgStyle_clock);
		this.mContext= context;
		this.title=title;
		initView();
	}

	private void initView() {
		setContentView(R.layout.dlg_cancel_book_success);
		TextView tv_title=(TextView) findViewById(R.id.tv_title_cancel_book_dlg);
		
		if(!TextUtils.isEmpty(title))
		{
			tv_title.setText(title);
		}
        WindowManager manager = ((Activity) mContext).getWindowManager();
		Display display = manager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.width = (int) display.getWidth();
		lp.height=LinearLayout.LayoutParams.FILL_PARENT;
        this.getWindow().setAttributes(lp);
		this.getWindow().setGravity(Gravity.CENTER);
//		this.getWindow().setWindowAnimations(R.style.dlgAnimation_up);
	}
	
	
	/** 显示millseconds 毫秒后关闭*/
	public void showToast(final int millseconds)
	{
		show();
		new Thread(){
			@Override
			public void run() {
				SystemClock.sleep(millseconds);
				handlerShow.sendEmptyMessage(CODE_DISMISS);
			};
		}.start();
	}
	
	private Handler handlerShow=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case CODE_DISMISS:
				cancel();
				break;

			default:
				break;
			}
		};
	};
	
	private static final int CODE_DISMISS=1;
}
