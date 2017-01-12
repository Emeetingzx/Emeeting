/**
 * 
 */
package cn.com.zte.emeeting.app.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.zte.mobileemeeting.R;
/**
 * 显示定位dlg
 * @author wf
 */
public  class DlgLocationAndGetMeeting extends Dialog {
	
	private Context mContext;
	
	private Button btn_choose_meeting_area;
	private TextView tv_title_dlg_location;
	private View.OnClickListener listener;
	/**
	 * 该类构造方法:
	 * @param @param context
	 */
	public DlgLocationAndGetMeeting(Context context,View.OnClickListener listener) {
		super(context,R.style.dlgStyle_clock);
		this.mContext= context;
		this.listener = listener;
		initView();
	}

	private void initView() {
		setContentView(R.layout.dlg_location_getmeetinglist);
		btn_choose_meeting_area=(Button) findViewById(R.id.btn_choose_meeting_area);
		btn_choose_meeting_area.setVisibility(View.GONE);
		if(listener!=null)
		{
			btn_choose_meeting_area.setOnClickListener(listener);
		}
		tv_title_dlg_location=(TextView) findViewById(R.id.tv_title_dlg_location);
        WindowManager manager = ((Activity) mContext).getWindowManager();
		Display display = manager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.width = (int) display.getWidth();
		lp.height=LinearLayout.LayoutParams.FILL_PARENT;
        this.getWindow().setAttributes(lp);
		this.getWindow().setGravity(Gravity.CENTER);
		this.setCancelable(false);
//		this.getWindow().setWindowAnimations(R.style.dlgAnimation_up);
	}
	
	public void setLocationFailed(){
		btn_choose_meeting_area.setVisibility(View.VISIBLE);
		String msg_failed = mContext.getResources().getString(R.string.meeting_location_failed);
		tv_title_dlg_location.setText(msg_failed);
	}
	
}
