/**
 * 
 */
package cn.com.zte.emeeting.app.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Shader.TileMode;
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
 * 用于:确认是否退订
 * @author wf
 */
public  class DlgConfirmBook extends Dialog {
	
	private Context mContext;
	
	private String title;
	
	private String titleLeft ;
	
	private String titleRight;
	
	private OnConfirmDlgListener listener;
	
	private TextView tv_title;
	/**
	 * 该类构造方法:
	 * @param @param context
	 */
	public DlgConfirmBook(Context context,String title,OnConfirmDlgListener listener) {
		super(context,R.style.dlgStyle_clock);
		this.mContext= context;
		this.title=title;
		this.listener = listener;
		initView();
	}

	private void initView() {
		setContentView(R.layout.dlg_confirm_book);
		tv_title=(TextView) findViewById(R.id.tv_dlg_confirm_book_title);
		Button btn_cancel_dlg=(Button) findViewById(R.id.btn_cancel_dlg);
		Button btn_ok_dlg=(Button) findViewById(R.id.btn_ok_dlg);
		
		titleLeft = mContext.getResources().getString(R.string.mymeeting_dlg_confirmbook);
		titleRight = mContext.getResources().getString(R.string.mymeeting_dlg_confirmbook_tmp);
		
		titleLeft="<font color='#333333'>"+titleLeft+"</font>"; 
		titleRight="<font color='#333333'>"+titleRight+"</font>"; 
		
		if(!TextUtils.isEmpty(title))
		{
			String titleWhole = titleLeft+"<font color='#01aeff'>“"+title+"”</font>"+titleRight;
			tv_title.setText(Html.fromHtml(titleWhole));
		}
		
		btn_cancel_dlg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
				if(listener!=null)
				{
					listener.onCanceled();
				}
			}
		});
		
		btn_ok_dlg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
				if(listener!=null)
				{
					listener.onConfirmed();
				}
			}
		});

        WindowManager manager = ((Activity) mContext).getWindowManager();
		Display display = manager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.width = (int) display.getWidth();
		lp.height=LinearLayout.LayoutParams.FILL_PARENT;
        this.getWindow().setAttributes(lp);
		this.getWindow().setGravity(Gravity.CENTER);
//		this.getWindow().setWindowAnimations(R.style.dlgAnimation_up);
	}
	
	public interface OnConfirmDlgListener{
		void onCanceled();
		void onConfirmed();
	}
	
	/** 设置左侧标题*/
	public void setLeftTitle(String titleLeft)
	{
		titleRight = mContext.getResources().getString(R.string.mymeeting_dlg_confirmbook_tmp);
		
		titleLeft="<font color='#333333'>"+titleLeft+"</font>"; 
		titleRight="<font color='#333333'>"+titleRight+"</font>"; 
		
		if(!TextUtils.isEmpty(title))
		{
			String titleWhole = titleLeft+"<font color='#01aeff'>“"+title+"”</font>"+titleRight;
			tv_title.setText(Html.fromHtml(titleWhole));
		}
	}
	
	
}
