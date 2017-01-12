/**
 * 
 */
package cn.com.zte.emeeting.app.dialog;

import android.app.Activity;
import android.app.AlertDialog;
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
 * 消息提示
 * @author wf
 */
public  class DlgToast extends Dialog {
	
	private Context mContext;
	
	private String title;
	
	
	/**
	 * 该类构造方法:
	 * @param @param context
	 */
	public DlgToast(Context context,String title) {
		super(context,R.style.dlgStyle_clock);
		this.mContext= context;
		this.title=title;
		initView();
	}

	private void initView() {
		
		if(title==null)
		{
			title = "";
		}
		
		if(title.length()<=8)
		{
			setContentView(R.layout.dlg_toast);
		}else
		{
			setContentView(R.layout.dlg_toast_big);
		}
		TextView tv_title=(TextView) findViewById(R.id.tv_title_cancel_book_dlg);
		View contentView=findViewById(R.id.ll_dlg);
		tv_title.setText(title);
		

        WindowManager manager = ((Activity) mContext).getWindowManager();
		Display display = manager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.width = LinearLayout.LayoutParams.WRAP_CONTENT;
		lp.height=LinearLayout.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(lp);
		this.getWindow().setGravity(Gravity.CENTER);
		this.setCancelable(true);
		this.setCanceledOnTouchOutside(true);
		
		contentView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
//		this.getWindow().setWindowAnimations(R.style.dlgAnimation_up);
	}
	
	
	/** 显示millseconds 毫秒后关闭*/
	public void showToast(final int millseconds)
	{
		show();
		new Thread(){
			@Override
			public void run() {
				try {
					Thread.sleep(millseconds);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					SystemClock.sleep(millseconds);
				}
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
