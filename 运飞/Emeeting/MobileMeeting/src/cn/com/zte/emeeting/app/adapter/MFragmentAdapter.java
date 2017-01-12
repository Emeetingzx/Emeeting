/**
 * 
 */
package cn.com.zte.emeeting.app.adapter;

import java.util.List;

import cn.com.zte.android.app.fragment.BaseFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * 该类用于
 * @author wf
 */
public class MFragmentAdapter extends FragmentStatePagerAdapter {
	private List<BaseFragment> list ;
	private FragmentManager fm;
	
	public MFragmentAdapter(FragmentManager fm,List<BaseFragment> list) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.list=list;
	}

	/**
	 * @see android.support.v4.app.FragmentStatePagerAdapter#getItem(int)
	 */
	@Override
	public Fragment getItem(int arg0) {
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
