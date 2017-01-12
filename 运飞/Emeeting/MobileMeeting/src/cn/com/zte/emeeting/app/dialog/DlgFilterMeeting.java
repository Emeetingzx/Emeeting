/**
 * 
 */
package cn.com.zte.emeeting.app.dialog;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.LinearLayout;
import cn.com.zte.android.common.util.JsonUtil;
import cn.com.zte.emeeting.app.adapter.MeetingFilterDlgAdapter;
import cn.com.zte.emeeting.app.adapter.MeetingFilterDlgAdapter.ItemMeetingFilterListener;
import cn.com.zte.emeeting.app.appservice.ChoosePlaceService;
import cn.com.zte.emeeting.app.appservice.MeetingFilterService;
import cn.com.zte.emeeting.app.database.entity.shared.DBMeetingRoomAddress;
import cn.com.zte.emeeting.app.request.entity.ScreeningCondition;
import cn.com.zte.emeeting.app.util.EmeetingUtil;
import cn.com.zte.emeeting.app.util.LogTools;
import cn.com.zte.emeeting.app.views.SigleSelectionLayout;
import cn.com.zte.emeeting.app.views.TopBar;
import cn.com.zte.emeeting.app.views.TopBar.ButtonOnClick;
import cn.com.zte.mobileemeeting.R;

/**
 * 该类用于:会议室条件筛选
 * @author wf
 */
public  class DlgFilterMeeting extends Dialog {
	
	private Context mContext;
	
	private List<View[]> viewList = new ArrayList<View[]>();
	
	private MeetingFilterService mService;
	
	private MeetingFilterDlgAdapter adapter_floor;
	private MeetingFilterDlgAdapter adapter_peoplenum;
	
	private SigleSelectionLayout single_layout_phone;
	private SigleSelectionLayout single_layout_tv;
	private SigleSelectionLayout single_layout_projector;
	
	private DlgFilterListener listener;
	
	/** 楼层数据源的parent实体*/
	private DBMeetingRoomAddress addressEntity;
	
	/** 楼层选择数据源*/
	private List<DBMeetingRoomAddress> listFloor;
	/**
	 * 该类构造方法
	 * @param @param context
	 */
	public DlgFilterMeeting(Context context,DBMeetingRoomAddress addressEntity,DlgFilterListener listener) {
		super(context,R.style.dlgStyle_filter);
		this.mContext= context;
		mService= new MeetingFilterService(mContext);
		this.listener = listener;
		this.addressEntity =addressEntity;
		LogTools.i("DlgFilter", "addressEntity:"+addressEntity);
		listFloor= new ArrayList<DBMeetingRoomAddress>();
		//第一个实体为不限
		DBMeetingRoomAddress first=new DBMeetingRoomAddress();
		first.setID("");
		first.setASC(mContext.getString(R.string.dlg_condition_no_limit));
		
		if(addressEntity!=null)
		{
			listFloor.add(first);
			LogTools.i("DlgFilter", "addressEntity:"+JsonUtil.toJson(addressEntity));
			List<DBMeetingRoomAddress> tmpList = new ChoosePlaceService(mContext).getListFloor(addressEntity);
			listFloor.addAll(tmpList);
		}else
		{
			listFloor.add(first);
		}
		LogTools.i("DlgFilter", "listFloor:"+JsonUtil.toJson(listFloor));
		initView();
	}

	/**
	 * 该方法用于:
	 * @param 
	 * @return void
	 */
	private void initView() {
		setContentView(R.layout.layout_filter_dlg);
		TopBar topBar  = (TopBar)findViewById(R.id.topbar_dlg_filter);
		LinearLayout ll_left = (LinearLayout) findViewById(R.id.ll_dlg_filter_left);
		LinearLayout ll_right = (LinearLayout) findViewById(R.id.ll_dlg_filter_right);
		View btn_dlg_filter_ok = findViewById(R.id.btn_dlg_filter_ok);
		
		GridView gv_filter_dlg_peoplenum= (GridView) findViewById(R.id.gv_filter_dlg_peoplenum);
		GridView gv_filter_dlg_floor= (GridView) findViewById(R.id.gv_filter_dlg_floor);
		
		single_layout_phone = (SigleSelectionLayout) findViewById(R.id.single_layout_phone);
		single_layout_tv = (SigleSelectionLayout) findViewById(R.id.single_layout_tv);
		single_layout_projector = (SigleSelectionLayout) findViewById(R.id.single_layout_projector);
		
		
		View tv_dlg_filter_left_1 = findViewById(R.id.tv_dlg_filter_left_1);
		View tv_dlg_filter_left_2 = findViewById(R.id.tv_dlg_filter_left_2);
		View tv_dlg_filter_left_3 = findViewById(R.id.tv_dlg_filter_left_3);
		
		View ll_filter_dlg_right_1 = findViewById(R.id.ll_filter_dlg_right_1);
		View ll_filter_dlg_right_2 = findViewById(R.id.ll_filter_dlg_right_2);
		View ll_filter_dlg_right_3 = findViewById(R.id.ll_filter_dlg_right_3);
		
		viewList.add(new View[]{tv_dlg_filter_left_1,ll_filter_dlg_right_1});
		viewList.add(new View[]{tv_dlg_filter_left_2,ll_filter_dlg_right_2});
		viewList.add(new View[]{tv_dlg_filter_left_3,ll_filter_dlg_right_3});
		
		for(int i =0;i<viewList.size();i++ )
		{
			final int position = i;
			viewList.get(position)[0].setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showContent(position);
				}
			});
		}
		
		showContent(0);
		
//		topBar.HiddenView(TopBar.rightBtnLogo);
		topBar.setViewText(TopBar.rightBtnLogo,mContext.getString(R.string.dlg_title_clear_filter));
		topBar.setViewText(TopBar.titleLogo, mContext.getString(R.string.dlg_title_filter));
		topBar.setViewBackGround(TopBar.leftBtnLogo, R.drawable.back);
		topBar.setViewOnClickListener(TopBar.leftBtnLogo, new ButtonOnClick() {
			
			@Override
			public void onClick(View view) {
				dismiss();
			}
		});
		topBar.setViewOnClickListener(TopBar.rightBtnLogo, new ButtonOnClick() {
			
			@Override
			public void onClick(View view) {
				clearFilterData();
			}
		});
		
		btn_dlg_filter_ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ScreeningCondition entity = new ScreeningCondition();
				if(adapter_floor.getChoosePosition()<=adapter_floor.defaultChoosePositon)
				{
					entity.setMRAIDS("");
				}
				{
					if(listFloor==null||listFloor.isEmpty())
					{
						entity.setMRAIDS("");
					}
					List<Integer> listChoosed=adapter_floor.getListChoosePostion();
					List<String> listMeetingRoomIds= new ArrayList<String>();
					
					for(Integer i:listChoosed)
					{
						if(i<listFloor.size())
						{
							listMeetingRoomIds.add(listFloor.get(i).getID());
						}else
						{
							LogTools.e("DlgFilter","楼层选择数组越界");
						}
					}
					
					entity.setMRAIDS(listMeetingRoomIds.toString().replace("[","").replace("]", "").replace(" ", ""));
					
				}
				entity.setPN("0");
				if(adapter_peoplenum.getChoosePosition()<EmeetingUtil.getListPersonNumCode().size()&&adapter_peoplenum.getChoosePosition()>=0)
				{
					entity.setPN(EmeetingUtil.getListPersonNumCode().get(adapter_peoplenum.getChoosePosition()));
				}
				entity.setPS(single_layout_phone.getSelectionState()+"");
				entity.setPJS(single_layout_projector.getSelectionState()+"");
				entity.setTVS(single_layout_tv.getSelectionState()+"");
				
				LogTools.i("DlgFilter","确定的筛选对象:"+JsonUtil.toJson(entity));
				listener.onConfirm(entity);
				dismiss();
			}
		});
		
		/** 处理楼层列表数据源*/
		List<String> listFloorStrs;
		listFloorStrs = new ArrayList<String>();
		for(DBMeetingRoomAddress e:listFloor)
		{
			listFloorStrs.add(e.getASC());
		}
		
		adapter_floor = new MeetingFilterDlgAdapter(mContext, listFloorStrs, new ItemMeetingFilterListener() {
			
			@Override
			public void onItemClicked(int position, String item) {
				//已选中的再次点击,就是取消
				if(adapter_floor.getListChoosePostion().contains(position))
				{
					adapter_floor.cancelChoosePosition(position);
				}else
				{
					adapter_floor.setChoosePosition(position);
				}
				
				System.out.println(adapter_floor.getListChoosePostion().toString());
			}
		}, true);
		
//		0->不限
//		10->10人以下
//		20->10-30人
//		100->30-60人
//		200->60人以上
		
		adapter_peoplenum = new MeetingFilterDlgAdapter(mContext, EmeetingUtil.getListPersonNumName(), new ItemMeetingFilterListener() {
			
			@Override
			public void onItemClicked(int position, String item) {
				//已选中的再次点击,就是取消
				if(adapter_peoplenum.getChoosePosition()==position)
				{
					adapter_peoplenum.setChoosePosition(adapter_peoplenum.defaultChoosePositon);
				}else
				{
					adapter_peoplenum.setChoosePosition(position);
				}
			}
		}, false);
		
		gv_filter_dlg_floor.setAdapter(adapter_floor);
		gv_filter_dlg_peoplenum.setAdapter(adapter_peoplenum);

        WindowManager manager = ((Activity) mContext).getWindowManager();
		Display display = manager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.width = (int) display.getWidth();
		
        this.getWindow().setAttributes(lp);
		this.getWindow().setGravity(Gravity.BOTTOM);
		this.getWindow().setWindowAnimations(R.style.dlgAnimation_up);
		
		
//        this.setAnimationStyle(R.style.PopupAnimation);
		
	}
	
	/**
	 * 该方法用于:清除筛选数据
	 * @param 
	 * @return void
	 */
	protected void clearFilterData() {
		single_layout_phone.setSelectionState(0);
		single_layout_tv.setSelectionState(0);
		single_layout_projector.setSelectionState(0);
		adapter_floor.setChoosePosition(adapter_floor.defaultChoosePositon);
		adapter_peoplenum.setChoosePosition(adapter_peoplenum.defaultChoosePositon);
	}

	/** 选择展示内容*/
	private void showContent(int position)
	{
		for(View[] viewArr:viewList)
		{
			viewArr[0].setSelected(false);
			viewArr[1].setVisibility(View.GONE);
		}
		
		viewList.get(position)[0].setSelected(true);
		viewList.get(position)[1].setVisibility(View.VISIBLE);
	}
	
	public interface DlgFilterListener{
		void onConfirm(ScreeningCondition entity);
	}
	
}
