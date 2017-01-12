/**
 * 
 */
package cn.com.zte.emeeting.app.dialog;

import java.util.ArrayList;
import java.util.List;

import cn.com.zte.emeeting.app.adapter.ChoosePlaceAdapter;
import cn.com.zte.emeeting.app.appservice.ChoosePlaceService;
import cn.com.zte.emeeting.app.database.entity.shared.DBMeetingRoomAddress;
import cn.com.zte.emeeting.app.views.TopBar;
import cn.com.zte.emeeting.app.views.TopBar.ButtonOnClick;
import cn.com.zte.mobileemeeting.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 该类用于:选择地点
 * @author wf
 */
public  class DlgPlaceChoose extends Dialog implements OnItemClickListener {
	
	private ItemListener listener;
	private List<DBMeetingRoomAddress> listFirstLevel=new ArrayList<DBMeetingRoomAddress>();
	private List<DBMeetingRoomAddress> listLevel2=new ArrayList<DBMeetingRoomAddress>();
	private List<DBMeetingRoomAddress> listLevel3=new ArrayList<DBMeetingRoomAddress>();
	
	private ChoosePlaceAdapter adapter1;
	private ChoosePlaceAdapter adapter2;
	private ChoosePlaceAdapter adapter3;
	
	private ListView lv1;
	private ListView lv2;
	private ListView lv3;
	
	private Context mContext;
	
	private ChoosePlaceService mService;
	private DBMeetingRoomAddress chooseAddress;
	
	
	
	/**
	 * 该类构造方法:
	 * @param @param context
	 */
	public DlgPlaceChoose(Context context,DBMeetingRoomAddress chooseAddress,ItemListener listener) {
		super(context,R.style.dlgStyle_filter);
		this.mContext= context;
		this.listener=listener;
		this.chooseAddress = chooseAddress;
		mService=new ChoosePlaceService(mContext);
		listFirstLevel = mService.getListPlaceCity();
		
		adapter1 = new ChoosePlaceAdapter(listFirstLevel, context, 1);
		adapter2 = new ChoosePlaceAdapter(listLevel2, context, 2);
		adapter3 = new ChoosePlaceAdapter(listLevel3, context, 3);
		initView();
		initData();
	}

	
	/**
	 * 该方法用于:初始化地址数据
	 * @param 
	 * @return void
	 */
	private void initData() {
		if(chooseAddress==null)return;
		
		
		try {
			DBMeetingRoomAddress p2 = mService.getParentOfChild_2(chooseAddress);
			DBMeetingRoomAddress p1 = mService.getParentOfChild_1(p2);
			
			if(p1!=null)
			{
				adapter1.setChooseAddress(p1);
				
				listLevel2.clear();
				listLevel2.addAll(mService.getListPlace(p1));
				adapter2.notifyDataSetChanged();
				adapter2.setChooseAddress(p2);
				
				listLevel3.clear();
				listLevel3.addAll(mService.getListPlace(p2));
				adapter2.notifyDataSetChanged();
				adapter3.setChooseAddress(chooseAddress);
			}
			
		} catch (Exception e) {
			//如果出现查询不到父级,则不再处理
			e.printStackTrace();
		}
		
	}


	/**
	 * 该方法用于:
	 * @param 
	 * @return void
	 */
	private void initView() {
		setContentView(R.layout.layout_place_choose);
		TopBar topbar  = (TopBar)findViewById(R.id.topbar_dlg_placechoose);
		topbar.setViewBackGround(TopBar.leftBtnLogo, R.drawable.back);
		topbar.setViewOnClickListener(TopBar.leftBtnLogo, new ButtonOnClick() {
			@Override
			public void onClick(View view) {
				dismiss();
			}
		});
		
		if(chooseAddress==null)
		{
			topbar.setLeftButtonGone();
		}
		
		topbar.setViewText(TopBar.titleLogo, mContext.getString(R.string.dlg_title_chooseplace));
		
		lv1 = (ListView) findViewById(R.id.lv_chooseplace_level_first);
		lv2 = (ListView) findViewById(R.id.lv_chooseplace_level_second);
		lv3 = (ListView) findViewById(R.id.lv_chooseplace_level_third);
//		final TextView tv_title =(TextView) findViewById(R.id.tv_dlg_choose_city_title);
		
		

        WindowManager manager = ((Activity) mContext).getWindowManager();
		Display display = manager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.width = (int) display.getWidth();
		
        this.getWindow().setAttributes(lp);
		this.getWindow().setGravity(Gravity.BOTTOM);
		this.getWindow().setWindowAnimations(R.style.dlgAnimation_up);
		
		
		setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
				
				if(chooseAddress==null)
				{
					if(arg2.getKeyCode()==KeyEvent.KEYCODE_BACK)
					{
						return true;
					}
				}
				return false;
			}
		});
		
		lv1.setAdapter(adapter1);
		lv2.setAdapter(adapter2);
		lv3.setAdapter(adapter3);
		
		lv1.setOnItemClickListener(this);
		lv2.setOnItemClickListener(this);
		
//        this.setAnimationStyle(R.style.PopupAnimation);
		
		lv3.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(listener!=null)
				{
					listener.onItemChoosed(position,((ChoosePlaceAdapter)lv3.getAdapter()).getItem(position));
				}
				dismiss();
			}
		});
		if(adapter1.getCount()>0)
		{
			lv1.performItemClick(null, 0, 0);
			lv2.performItemClick(null, 0, 0);
		}
		
	}
	
	public interface ItemListener{
		void  onItemChoosed(int position,DBMeetingRoomAddress item);
	}

	/**
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (parent.getId()) {
		case R.id.lv_chooseplace_level_first:
		{
			adapter1.setChoosePosition(position);
			listLevel2.clear();
			listLevel2.addAll(mService.getListPlace(adapter1.getItem(position)));
			adapter2.notifyDataSetChanged();
			adapter2.setChoosePosition(-1);
//			listLevel3.clear();
//			adapter3.notifyDataSetChanged();
//			adapter3.setChoosePosition(-1);
			if(listLevel2.size()>0)
			{
				lv2.performItemClick(null, 0, 0);
			}
		}
			break;
		case R.id.lv_chooseplace_level_second:
		{
			adapter2.setChoosePosition(position);
			listLevel3.clear();
			listLevel3.addAll(mService.getListPlace(adapter2.getItem(position)));
			adapter3.notifyDataSetChanged();
			adapter3.setChoosePosition(-1);
		}
			break;

		default:
			break;
		}
	}

	
	
}
