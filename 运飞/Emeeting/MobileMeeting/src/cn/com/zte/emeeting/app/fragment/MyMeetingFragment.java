package cn.com.zte.emeeting.app.fragment;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import cn.com.zte.android.app.fragment.BaseFragment;
import cn.com.zte.android.resource.inflater.BaseLayoutInflater;
import cn.com.zte.emeeting.app.activity.MainActivity;
import cn.com.zte.emeeting.app.adapter.MeetingFragmentPagerAdapter;
import cn.com.zte.emeeting.app.util.LogTools;
import cn.com.zte.emeeting.app.views.TopBar;
import cn.com.zte.emeeting.app.views.TopBar.ButtonOnClick;
import cn.com.zte.emeeting.app.views.calendar.CalendarActivity;
import cn.com.zte.mobileemeeting.R;

/**
 * 我的会议 页面
 * 
 * @author wtb
 * */
public class MyMeetingFragment extends BaseFragment implements OnClickListener {

	/** 上下文 */
	private Context mContext;
	
	/** TopBar */
	@InjectView(R.id.my_meeting_fragment_topbar)
	private TopBar topbar;
	
	/** 我预定的 */
	@InjectView(R.id.rl_mymeeting_choosebar_left)
	private View rl_mymeeting_choosebar_left;
	
	/** 我组织的 */
	@InjectView(R.id.rl_mymeeting_choosebar_center)
	private View rl_mymeeting_choosebar_center;
	
	/** 我组织的 */
	@InjectView(R.id.rl_mymeeting_choosebar_right)
	private View rl_mymeeting_choosebar_right;
	
	/** 我预定的  下面小三角*/
	@InjectView(R.id.iv_mymeeting_choosebar_left)
	private View iv_mymeeting_choosebar_left;
	
	/** 我组织的  下面小三角*/
	@InjectView(R.id.iv_mymeeting_choosebar_center)
	private View iv_mymeeting_choosebar_center;
	
	/** 我组织的  下面小三角*/
	@InjectView(R.id.iv_mymeeting_choosebar_right)
	private View iv_mymeeting_choosebar_right;
	/**
	 * 是否自动刷新数据
	 */
	public static boolean isRefresh = false;
	
	private List<View[]> listChooseBar=new ArrayList<View[]>();
	
	/**切换的viewpager*/
	@InjectView(R.id.vp_mymeeting)
	private ViewPager vp_mymeeting;
	private List<BaseFragment> listFragment = new ArrayList<BaseFragment>();
	
	
	
	
	@Override
	protected View onCreateView(BaseLayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext = getActivity();
		return inflater.inflate(R.layout.fragment_my_meeting, null);
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d("TAG", "MyMeetingFragment onresume");
		if (isRefresh) {
			isRefresh = false;
			new Handler(){
				public void handleMessage(android.os.Message msg) {
					if(msg.what==0)
					{
//						initListFragment();
						if(listFragment!=null&&!listFragment.isEmpty())
						{
							try {
								((MyBookedFragment)listFragment.get(0)).refreshListViewData();
								((MyOrganizeFragment)listFragment.get(1)).refreshListViewData();
								((MyAtendFragment)listFragment.get(2)).refreshListViewData();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				};
			}.sendEmptyMessageDelayed(0,300);
		}
	}

	@Override
	protected void initData() {
		super.initData();
		
	}

	@Override
	protected void initViews() {
		super.initViews();
		listChooseBar.add(new View[]{rl_mymeeting_choosebar_left,iv_mymeeting_choosebar_left});
		listChooseBar.add(new View[]{rl_mymeeting_choosebar_center,iv_mymeeting_choosebar_center});
		listChooseBar.add(new View[]{rl_mymeeting_choosebar_right,iv_mymeeting_choosebar_right});
		// 初始化Fragment
		initTopBar();
//		initListFragment();
		
		new Handler(){
			public void handleMessage(android.os.Message msg) {
				if(msg.what==0)
				{
					initListFragment();
				}
			};
		}.sendEmptyMessageDelayed(0,250);
	}

	/** 初始化TopBar */
	private void initTopBar() {
		topbar.setViewBackGround(TopBar.leftBtnLogo, R.drawable.icon_home_menu);
		topbar.setViewOnClickListener(TopBar.leftBtnLogo, new ButtonOnClick() {
			
			@Override
			public void onClick(View view) {
				// 返回菜单栏
				MainActivity mainActivity = (MainActivity) getActivity();
				mainActivity.showLeftView();
			}
		});
		topbar.setViewText(TopBar.titleLogo, getString(R.string.mymeeting_title));
		topbar.setViewTextColor(TopBar.titleLogo, getResourceColor(R.color.TEXTCOLOR_TOPBAR_TITLE_TEXT));
		topbar.setViewText(TopBar.rightBtnLogo, getString(R.string.mymeeting_title_right));
		topbar.setViewTextColor(TopBar.rightBtnLogo, getResourceColor(R.color.TEXTCOLOR_TOPBAR_RIGHT_TEXT));
		topbar.setViewOnClickListener(TopBar.rightBtnLogo, new ButtonOnClick() {
			
			@Override
			public void onClick(View view) {
				// 显示日历 在日历中显示所有的会议
				Intent intent = new Intent(mContext,CalendarActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private void initListFragment() {
		listFragment.clear();
		listFragment.add(new MyBookedFragment());
		listFragment.add(new MyOrganizeFragment());
		listFragment.add(new MyAtendFragment());
//		MeetingFragmentPagerAdapter adapter = new MeetingFragmentPagerAdapter(getChildFragmentManager(),listFragment);
		MeetingFragmentPagerAdapter adapter = new MeetingFragmentPagerAdapter(getChildFragmentManager(),listFragment);
		vp_mymeeting.setAdapter(adapter);
		vp_mymeeting.setOffscreenPageLimit(2);
		vp_mymeeting.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				setChooseLabel(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
//				System.out.println("arg0:"+arg0 +"   arg1:"+arg1 +"  arg2:"+arg2);
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		vp_mymeeting.setCurrentItem(0);
		setChooseLabel(0);
		vp_mymeeting.getParent().requestDisallowInterceptTouchEvent(false);
	}
	

	@Override
	protected void initViewEvents() {
		super.initViewEvents();
		rl_mymeeting_choosebar_left.setOnClickListener(this);
		rl_mymeeting_choosebar_center.setOnClickListener(this);
		rl_mymeeting_choosebar_right.setOnClickListener(this);

	}

	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_mymeeting_choosebar_left:
			vp_mymeeting.setCurrentItem(0);
			break;
		case R.id.rl_mymeeting_choosebar_center:
			vp_mymeeting.setCurrentItem(1);
			break;
		case R.id.rl_mymeeting_choosebar_right:
			vp_mymeeting.setCurrentItem(2);
			break;
		default:
			break;
		}
	}

	
	/** 设置选择标签*/
	private void setChooseLabel(int index){
		for(int i = 0;i<listChooseBar.size();i++)
		{
			View[] chooseBar = listChooseBar.get(i);
			chooseBar[0].setSelected(false);
			chooseBar[1].setVisibility(View.GONE);
		}
		
		View[] chooseBar = listChooseBar.get(index);
		chooseBar[0].setSelected(true);
		chooseBar[1].setVisibility(View.VISIBLE);
	}
	
	/**
	 * @see android.support.v4.app.Fragment#onHiddenChanged(boolean)
	 */
	@Override
	public void onHiddenChanged(boolean hidden) {
		LogTools.i("MyMeetingFragment","hidden:"+hidden);
		if(!hidden)
		{
			new Handler(){
				public void handleMessage(android.os.Message msg) {
					if(msg.what==0)
					{
//						initListFragment();
						if(listFragment!=null&&!listFragment.isEmpty())
						{
							try {
								((MyBookedFragment)listFragment.get(0)).refreshListViewData();
								((MyOrganizeFragment)listFragment.get(1)).refreshListViewData();
								((MyAtendFragment)listFragment.get(2)).refreshListViewData();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				};
			}.sendEmptyMessageDelayed(0,300);
		}
		super.onHiddenChanged(hidden);
	}
	


}
