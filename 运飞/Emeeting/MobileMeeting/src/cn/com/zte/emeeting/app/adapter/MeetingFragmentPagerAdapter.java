/**
 * 
 */
package cn.com.zte.emeeting.app.adapter;

import java.util.ArrayList;
import java.util.List;

import cn.com.zte.android.app.fragment.BaseFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

/**
 * 该类为ViewPager的Fragment适配器
 * 
 * @author wf
 */
public class MeetingFragmentPagerAdapter extends FragmentPagerAdapter {
	private List<BaseFragment> list;
	private FragmentManager fm;
	/**
	 * 当数据发生改变时的回调接口
	 */
	private OnReloadListener mListener;

	public MeetingFragmentPagerAdapter(FragmentManager fm,
			List<BaseFragment> list) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.fm = fm;
		this.list = list;
	}

	/**
	 * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
	 */
	@Override
	public BaseFragment getItem(int arg0) {
		if (list.isEmpty() || list == null)
			return null;
		return list.get(arg0);
	}


	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}

	/**
	 * @see android.support.v4.view.PagerAdapter#getCount()
	 */
	@Override
	public int getCount() {
		if (list.isEmpty() || list == null)
			return 0;
		return list.size();
	}

	/**
	 * 重新设置页面内容
	 * 
	 * @param items
	 */
	public void setPagerItems(List<BaseFragment> items) {
		if (items != null) {
			if (list != null) {
				FragmentTransaction ft = fm.beginTransaction();
				for (int i = 0; i < list.size(); i++) {
					ft.remove(list.get(i));
				}
				ft.commit();
				ft = null;
				fm.executePendingTransactions();
			}
			list = items;
		}
	}

	/**
	 * 当页面数据发生改变时你可以调用此方法
	 */
	public void reLoad() {
		if (list != null) {
			FragmentTransaction ft = fm.beginTransaction();
			for (int i = 0; i < list.size(); i++) {
				ft.remove(list.get(i));
			}
			ft.commit();
			ft = null;
			fm.executePendingTransactions();
		}
	}

	public void setOnReloadListener(OnReloadListener listener) {
		this.mListener = listener;
	}

	/**
	 * @author 回调接口
	 */
	public interface OnReloadListener {
		public void onReload();
	}

}
