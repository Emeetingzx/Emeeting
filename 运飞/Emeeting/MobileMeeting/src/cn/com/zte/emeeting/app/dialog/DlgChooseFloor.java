/**
 * 
 */
package cn.com.zte.emeeting.app.dialog;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import cn.com.zte.mobileemeeting.R;

/**
 * 该类用于:选择楼层等(已不用)
 * @author wf
 */
public class DlgChooseFloor extends Dialog {
	
	private ChooseListener listener;
	private List<String> listData;

	/**
	 * 该类构造方法:
	 * @param @param context
	 */
	public DlgChooseFloor(Context context,List<String> listData,ChooseListener listener) {
		 super(context,R.style.dlgStyle_Transparent_exit_edit);
		 this.listener=listener;
		 this.listData = listData;
		initView();
		
	}

	/**
	 * 该方法用于:
	 * @param 
	 * @return void
	 */
	private void initView() {
		View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dlg_choose_floor, null);
		setContentView(contentView);
		GridView gv = (GridView) findViewById(R.id.gv_dlg_choose_floor);
		final View rl_dlg_choose_floor_bg = findViewById(R.id.rl_dlg_choose_floor_bg);
		
//        WindowManager manager = ((Activity) context).getWindowManager();
//		Display display = manager.getDefaultDisplay();
//        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
//		lp.width = (int) display.getWidth();
//        this.getWindow().setAttributes(lp);
		this.getWindow().setGravity(Gravity.BOTTOM);
		gv.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.item_lv_filter_floor,R.id.tv_item_filter_floor,listData));
//        this.setAnimationStyle(R.style.PopupAnimation);
		this.getWindow().setWindowAnimations(R.style.dlgAnimation_up);
		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(listener!=null)
				{
					listener.onItemChoose(position);
					System.out.println(rl_dlg_choose_floor_bg.getTop());
				}
			}
		});
		
		
		contentView.setOnTouchListener(new OnTouchListener() {  
            
            public boolean onTouch(View v, MotionEvent event) {  
                  
                int top = rl_dlg_choose_floor_bg.getTop();
                int y=(int) event.getY();  
                if(event.getAction()==MotionEvent.ACTION_UP){  
                    if(y<top){  
                        dismiss();  
                    }  
                }                 
                return true;  
            }  
        });
	}
	
	public interface ChooseListener{
		void onItemChoose(int position);
	};

	
}
