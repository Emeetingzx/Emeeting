package cn.com.zte.emeeting.app.activity;

import java.util.ArrayList;
import java.util.List;

import cn.com.zte.emeeting.app.base.activity.AppActivity;
import roboguice.inject.InjectView;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import cn.com.zte.android.app.activity.BaseActivity;
import cn.com.zte.android.app.fragment.BaseFragment;
import cn.com.zte.emeeting.app.adapter.MeetingFragmentPagerAdapter;
import cn.com.zte.emeeting.app.fragment.ServiceDoneFragment;
import cn.com.zte.emeeting.app.fragment.ServiceReceiveFragment;
import cn.com.zte.emeeting.app.fragment.ServiceUnProcessFragment;
import cn.com.zte.emeeting.app.views.TopBar;
import cn.com.zte.emeeting.app.views.TopBar.ButtonOnClick;
import cn.com.zte.mobileemeeting.R;

/**
 * 增值服务管理员 页面
 * 
 * @author LangK
 * */
public class ServiceAdminActivity extends AppActivity implements OnClickListener {

	/** TAG信息 */
	private final String TAG = ServiceAdminActivity.class.getSimpleName();

	/** 上下文 */
	private Context mContext;
	
	
	/** 我预定的 */
	@InjectView(R.id.rl_service_admin_choosebar_left)
	private View leftLayout;
	
	/** 我组织的 */
	@InjectView(R.id.rl_service_admin_choosebar_center)
	private View centerLayout;
	
	/** 我组织的 */
	@InjectView(R.id.rl_service_admin_choosebar_right)
	private View rightLayout;
	
	/** 我预定的  下面小三角*/
	@InjectView(R.id.iv_service_admin_choosebar_left)
	private View leftImageView;
	
	/** 我组织的  下面小三角*/
	@InjectView(R.id.iv_service_admin_choosebar_center)
	private View centerImageView;
	
	/** 我组织的  下面小三角*/
	@InjectView(R.id.iv_service_admin_choosebar_right)
	private View rightImageView;

	
	private List<View[]> listChooseBar=new ArrayList<View[]>();
	
	/**切换的viewpager*/
	@InjectView(R.id.vp_service_admin)
	private ViewPager viewPager;
	private List<BaseFragment> listFragment = new ArrayList<BaseFragment>();
	
	/** TopBar */
	@InjectView(R.id.service_admin_topbar)
	private TopBar topBar;
	
	
	
	
	@Override
	protected void initContentView() {
		// TODO Auto-generated method stub
		super.initContentView();
		setContentView(R.layout.activity_service_admin);
		mContext = this;
	}

	@Override
	public void onResume() {
		Log.i(TAG, "onResume");
		super.onResume();
	}

	@Override
	protected void initData() {
		Log.i(TAG, "初始化数据~");
		super.initData();
		
	}

	@Override
	protected void initViews() {
		super.initViews();
		listChooseBar.add(new View[]{leftLayout,leftImageView});
		listChooseBar.add(new View[]{centerLayout,centerImageView});
		listChooseBar.add(new View[]{rightLayout,rightImageView});
		
		setChooseLabel(0);
		initTopBar();
		// 初始化Fragment
		initListFragment();
	}

	/** 初始化TopBar */
	private void initTopBar() {
		topBar.setViewBackGround(TopBar.leftBtnLogo, R.drawable.back);
		topBar.setViewOnClickListener(TopBar.leftBtnLogo, new ButtonOnClick() {
			
			@Override
			public void onClick(View view) {
				// 返回菜单栏
				finish();
			}
		});
		topBar.setViewText(TopBar.titleLogo, "管理员");
		topBar.setViewTextColor(TopBar.titleLogo, getResourceColor(R.color.TEXTCOLOR_TOPBAR_TITLE_TEXT));
	}
	
	private void initListFragment() {
		Log.i(TAG, "初始化3个页面");
		
		listFragment.add(new ServiceUnProcessFragment());
		listFragment.add(new ServiceReceiveFragment());
		listFragment.add(new ServiceDoneFragment());
		
		MeetingFragmentPagerAdapter adapter = new MeetingFragmentPagerAdapter(getSupportFragmentManager(),listFragment);
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				setChooseLabel(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		viewPager.setCurrentItem(0);
		viewPager.getParent().requestDisallowInterceptTouchEvent(false);
		viewPager.setOffscreenPageLimit(2);
	}
	

	@Override
	protected void initViewEvents() {
		super.initViewEvents();
		leftLayout.setOnClickListener(this);
		centerLayout.setOnClickListener(this);
		rightLayout.setOnClickListener(this);

	}

	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_service_admin_choosebar_left:
			viewPager.setCurrentItem(0);
			break;
		case R.id.rl_service_admin_choosebar_center:
			viewPager.setCurrentItem(1);
			break;
		case R.id.rl_service_admin_choosebar_right:
			viewPager.setCurrentItem(2);
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
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		finishSelf();
		super.onDestroy();
	}
	
	/**
	 * 关闭界面之前判断主页是否已经存在内存，如不存在，需要跳转到主页
	 */
	private void finishSelf(){
		Log.d(TAG, "go main page");
		Intent intent = new Intent(mContext,MainActivity.class);
		intent.putExtra(MainActivity.FLAG,MainActivity.VALOGO);
		startActivity(intent);
	}

}
