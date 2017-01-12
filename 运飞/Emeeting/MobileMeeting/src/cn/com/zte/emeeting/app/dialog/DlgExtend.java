/**
 * 
 */
package cn.com.zte.emeeting.app.dialog;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Shader.TileMode;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zte.emeeting.app.adapter.ExtendConflictAdapter;
import cn.com.zte.emeeting.app.response.entity.MeetingProLong;
import cn.com.zte.mobileemeeting.R;
/**
 * 延长会议没有冲突dlg
 * @author Langk
 */
public  class DlgExtend extends Dialog {
	
	private Context mContext;
	
	private OnBookSuccessDlgListener listener;
	
	private List<MeetingProLong> list;
	
	private String title;
	
	private String endtime;
	/**
	 * 该类构造方法:
	 * @param @param context
	 */
	public DlgExtend(Context context,OnBookSuccessDlgListener listener) {
		super(context,R.style.dlgStyle_clock);
		this.mContext= context;
		this.listener = listener;
		initView();
	}
	
	/**
	 * 该类构造方法:
	 * @param @param context
	 */
	public DlgExtend(Context context,String title,String endtime,List<MeetingProLong> list,OnBookSuccessDlgListener listener) {
		super(context,R.style.dlgStyle_clock);
		this.mContext= context;
		this.listener = listener;
		this.list = list;
		this.title = title;
		this.endtime = endtime;
		initView();
	}

	private void initView() {
		setContentView(R.layout.dlg_extend_success);
		ListView listView = (ListView) findViewById(R.id.list_dlg_confirm_extend);
		TextView tv_title=(TextView) findViewById(R.id.tv_dlg_confirm_book_title);
		Button btn_left=(Button) findViewById(R.id.btn_dlg_left);
		Button btn_right=(Button) findViewById(R.id.btn_dlg_right);
		boolean isPrlNC = true;
		if (list!=null&&list.size()>0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getPLT()!=null&&!list.get(i).getPLT().equals("30")) {
					isPrlNC = false;
				}
			}
			if (isPrlNC) {
				listView.setVisibility(View.GONE);
			}else {
				listView.setVisibility(View.VISIBLE);
				ExtendConflictAdapter adapter = new ExtendConflictAdapter(mContext,endtime,list);
				listView.setAdapter(adapter);
				tv_title.setText(R.string.meeting_extend_conflict_sure);
			}
		}else {
			listView.setVisibility(View.GONE);
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
		DisplayMetrics dm = new DisplayMetrics();
		display.getMetrics(dm);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.width = (int) dm.widthPixels;
		lp.height=LinearLayout.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(lp);
		this.getWindow().setGravity(Gravity.BOTTOM);
		this.getWindow().setWindowAnimations(R.style.dlgAnimation_up);
	}
	
	public interface OnBookSuccessDlgListener{
		void onLeftClicked();
		void onRightClicked();
	}
}
