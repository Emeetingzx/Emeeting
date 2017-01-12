/**
 * 
 */
package cn.com.zte.emeeting.app.adapter;

import java.util.List;

import cn.com.zte.android.app.fragment.BaseFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * 该类为ViewPager的Fragment适配器
 * @author wf
 */
public class ServiceFragmentPagerAdapter extends FragmentPagerAdapter {
	private List<BaseFragment> list ;
	
	public ServiceFragmentPagerAdapter(FragmentManager fm,List<BaseFragment> list) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.list=list;
	}

	/**
	 * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
	 */
	@Override
	public BaseFragment getItem(int arg0) {
		if(list.isEmpty()||list==null) return null;
		return list.get(arg0);
	}

	/**
	 * @see android.support.v4.view.PagerAdapter#getCount()
	 */
	@Override
	public int getCount() {
		if(list.isEmpty()||list==null) return 0;
		return list.size();
	}

}
