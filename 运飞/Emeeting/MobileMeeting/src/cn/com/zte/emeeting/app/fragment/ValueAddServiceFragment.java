package cn.com.zte.emeeting.app.fragment;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.utils.Utils;

import roboguice.inject.InjectView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zte.android.app.fragment.BaseFragment;
import cn.com.zte.android.resource.inflater.BaseLayoutInflater;
import cn.com.zte.emeeting.app.activity.MainActivity;
import cn.com.zte.emeeting.app.activity.ServiceAdminActivity;
import cn.com.zte.emeeting.app.adapter.MeetingFragmentPagerAdapter;
import cn.com.zte.emeeting.app.adapter.ServiceFragmentPagerAdapter;
import cn.com.zte.emeeting.app.base.ConfigrationList;
import cn.com.zte.emeeting.app.util.DensityUtil;
import cn.com.zte.emeeting.app.util.SharedPreferenceUtil;
import cn.com.zte.emeeting.app.views.TopBar;
import cn.com.zte.emeeting.app.views.TopBar.ButtonOnClick;
import cn.com.zte.mobileemeeting.R;

/**
 * 增值服务
 * 
 * @author LangK
 * */
public class ValueAddServiceFragment extends BaseFragment implements
		OnClickListener {

	/** TAG信息 */
	private final String TAG = ValueAddServiceFragment.class.getSimpleName();

	/** 上下文 */
	private Context mContext;

	/** TopBar */
	@InjectView(R.id.valueadd_fragment_topbar)
	private TopBar topBar;

	/** 服务预定 */
	@InjectView(R.id.rl_service_choosebar_left)
	private RelativeLayout rl_serviceBookView;

	/** 我的服务 */
	@InjectView(R.id.rl_service_choosebar_right)
	private RelativeLayout rl_myserviceView;

	/** 游标 */
	@InjectView(R.id.im_valueadd_booking)
	private ImageView imLeft;

	@InjectView(R.id.im_valueadd_myself)
	private ImageView imRight;

	/** 切换的viewpager */
	@InjectView(R.id.vp_service)
	private ViewPager viewPager;
	private List<BaseFragment> listFragment = new ArrayList<BaseFragment>();

	private List<View[]> listChooseBar = new ArrayList<View[]>();
	/**
	 * 服务预定
	 */
	private ValueAddBookFragment valueAddBookFragment= new ValueAddBookFragment();
	/**
	 * 我的服务
	 */
	private ValueAddMyselfFragment valueAddMyselfFragment= new ValueAddMyselfFragment();
	
	@Override
	protected View onCreateView(BaseLayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		mContext = getActivity();
		return inflater.inflate(R.layout.fragment_value_add_service);
	}

	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		Log.d("TAG", "onHiddenChanged");
		if (!hidden) {
			valueAddBookFragment.reCheck();
		}
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d("TAG", "onPause");
	}

	@Override
	protected void initViews() {
		super.initViews();
		listChooseBar.add(new View[] { rl_serviceBookView, imLeft });
		listChooseBar.add(new View[] { rl_myserviceView, imRight });
		initTopBar();
		setChooseLabel(0);
		new Handler(){
			public void handleMessage(android.os.Message msg) {
				initListFragment();
			};
		}.sendEmptyMessageDelayed(0, 200);
	}

	/** 初始化TopBar */
	private void initTopBar() {
		topBar.setViewBackGround(TopBar.leftBtnLogo, R.drawable.icon_home_menu);
		topBar.setViewOnClickListener(TopBar.leftBtnLogo, new ButtonOnClick() {
			@Override
			public void onClick(View view) {
				InputMethodManager inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
				if (inputManager.isActive()) {
					inputManager.hideSoftInputFromWindow(imLeft.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				}
				// 返回菜单栏
				MainActivity mainActivity = (MainActivity) getActivity();
				mainActivity.showLeftView();
			}
		});
		topBar.setViewText(TopBar.titleLogo, getString(R.string.service_addValue_service));
		topBar.setViewTextColor(TopBar.titleLogo,
				getResourceColor(R.color.TEXTCOLOR_TOPBAR_TITLE_TEXT));
		topBar.setViewBackGround(TopBar.rightBtnLogo, R.drawable.icon_admin);
		topBar.setViewTextColor(TopBar.rightBtnLogo,
				getResourceColor(R.color.TEXTCOLOR_TOPBAR_RIGHT_TEXT));
		topBar.setViewOnClickListener(TopBar.rightBtnLogo, new ButtonOnClick() {

			@Override
			public void onClick(View view) {
				// 显示日历 在日历中显示所有的会议
				Intent intent = new Intent(mContext, ServiceAdminActivity.class);
				startActivity(intent);
			}
		});
		
		if (!new SharedPreferenceUtil(ConfigrationList.USERINFO, mContext).getString(ConfigrationList.ISADMIN).equals("Y")) {
			topBar.HiddenView(TopBar.rightBtnLogo);
		}else {
			topBar.showView(TopBar.rightBtnLogo);
		}
		
	}

	
	/**
	 * 初始化Fragment
	 */
	private void initListFragment() {
		Log.i(TAG, "初始化2个页面");
		listFragment.add(valueAddBookFragment);
		listFragment.add(valueAddMyselfFragment);
		ServiceFragmentPagerAdapter adapter = new ServiceFragmentPagerAdapter(
				getChildFragmentManager(), listFragment);
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

	}
	
	/**
	 * 设置显示页签
	 * @param pisition
	 */
	public void setViewPagerItem(int pisition){
		viewPager.setCurrentItem(pisition);
	}

	@Override
	protected void initViewEvents() {
		super.initViewEvents();
		rl_serviceBookView.setOnClickListener(this);
		rl_myserviceView.setOnClickListener(this);

	}

	/** 设置选择标签 */
	private void setChooseLabel(int index) {
		for (int i = 0; i < listChooseBar.size(); i++) {
			View[] chooseBar = listChooseBar.get(i);
			chooseBar[0].setSelected(false);
			chooseBar[1].setVisibility(View.GONE);
		}

		View[] chooseBar = listChooseBar.get(index);
		chooseBar[0].setSelected(true);
		chooseBar[1].setVisibility(View.VISIBLE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_service_choosebar_left:
			viewPager.setCurrentItem(0);
			break;
		case R.id.rl_service_choosebar_right:
			viewPager.setCurrentItem(1);
			break;
		default:
			break;
		}
	}
}
