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
 * 预订成功dlg
 * @author wf
 */
public  class DlgBookSuccess extends Dialog {
	
	private Context mContext;
	
	private String title;
	
	
	private OnBookSuccessDlgListener listener;
	/**
	 * 该类构造方法:
	 * @param @param context
	 */
	public DlgBookSuccess(Context context,String title,OnBookSuccessDlgListener listener) {
		super(context,R.style.dlgStyle_clock);
		this.mContext= context;
		this.title=title;
		this.listener = listener;
		initView();
	}

	private void initView() {
		setContentView(R.layout.dlg_book_success);
		TextView tv_title=(TextView) findViewById(R.id.tv_dlg_confirm_book_title);
		Button btn_left=(Button) findViewById(R.id.btn_dlg_left);
		Button btn_right=(Button) findViewById(R.id.btn_dlg_right);
		
		if(!TextUtils.isEmpty(title))
		{
			tv_title.setText(title);
		}
		
		btn_left.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
				if(listener!=null)
				{
					listener.onLeftClicked();
				}
			}
		});
		
		btn_right.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
				if(listener!=null)
				{
					listener.onRightClicked();
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
	
	public interface OnBookSuccessDlgListener{
		void onLeftClicked();
		void onRightClicked();
	}
}
