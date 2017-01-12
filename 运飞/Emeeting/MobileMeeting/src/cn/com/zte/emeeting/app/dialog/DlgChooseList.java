/**
 * 
 */
package cn.com.zte.emeeting.app.dialog;

import java.util.List;

import cn.com.zte.emeeting.app.adapter.ChooseListDlgAdapter;
import cn.com.zte.emeeting.app.adapter.ChoosePlaceAdapter;
import cn.com.zte.emeeting.app.appservice.ChoosePlaceService;
import cn.com.zte.emeeting.app.views.TopBar;
import cn.com.zte.emeeting.app.views.TopBar.ButtonOnClick;
import cn.com.zte.mobileemeeting.R;
import cn.com.zte.zxing.client.android.FinishListener;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 该类用于:显示下面弹出的列表
 * @author wf
 */
public  class DlgChooseList extends Dialog {
	
	private ItemListener listener;
	
	private Context mContext;
	
	private ChoosePlaceService mService;
	
	private List<String> dataList;
	
	private ChooseListDlgAdapter adapter;
	/**
	 * 该类构造方法:
	 * @param @param context
	 */
	public DlgChooseList(Context context,List<String> dataList,ItemListener listener) {
		super(context,R.style.dlgStyle_filter);
		this.mContext= context;
		this.listener=listener;
		this.dataList=dataList;
		adapter=new ChooseListDlgAdapter(mContext, dataList);
		initView();
	}

	/**
	 * 该方法用于:
	 * @param 
	 * @return void
	 */
	private void initView() {
		setContentView(R.layout.dlg_choose_list);
		
		
		ListView  lv_dlg_choose_list= (ListView) findViewById(R.id.lv_dlg_choose_list);
		Button btn_cancel_dlgchoose_list=(Button) findViewById(R.id.btn_cancel_dlgchoose_list);
//		final TextView tv_title =(TextView) findViewById(R.id.tv_dlg_choose_city_title);
		

        WindowManager manager = ((Activity) mContext).getWindowManager();
		Display display = manager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.width = (int) display.getWidth();
		
        this.getWindow().setAttributes(lp);
		this.getWindow().setGravity(Gravity.BOTTOM);
		this.getWindow().setWindowAnimations(R.style.dlgAnimation_up);
		
		btn_cancel_dlgchoose_list.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		
		lv_dlg_choose_list.setAdapter(adapter);
		lv_dlg_choose_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				if(listener!=null)
				{
					listener.onItemChoosed(position, dataList.get(position));
				}
				dismiss();
			}
		});
	}
	
	public interface ItemListener{
		void  onItemChoosed(int position,String item);
	}
	
	/** 设置选中的文本*/
	public void setChooseStr(String chooseStr)
	{
		if(adapter!=null)
		{
			adapter.setChooseStr(chooseStr);
		}
	}
}
