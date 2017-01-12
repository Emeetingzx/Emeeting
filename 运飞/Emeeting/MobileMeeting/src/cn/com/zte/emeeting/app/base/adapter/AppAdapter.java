package cn.com.zte.emeeting.app.base.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 
 * @ClassName: AppAdapter
 * @Description: TODO 【BaseAdapter基类】
 * @author Pan.Jianbo
 * @param <T>
 * @date 2015-9-26 下午10:02:24
 * 
 */
public abstract class AppAdapter<T> extends BaseAdapter {

	/** 数据集合 */
	private List<T> list;

	/** 上下环境 */
	protected Context context;

	/**
	 * 构造方法
	 * 
	 * @param context
	 */
	public AppAdapter(Context context) {
		init(context, new ArrayList<T>());
	}

	/**
	 * 构造方法
	 * 
	 * @param context
	 * @param list
	 */
	public AppAdapter(Context context, List<T> list) {
		init(context, list);
	}

	/**
	 * 
	 * @Title: init
	 * @Description: TODO 【初始化方法】
	 * @param @param context
	 * @param @param list 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void init(Context context, List<T> list) {
		this.list = list;
		this.context = context;
	}

	/**
	 * 
	 * @Title: getList
	 * @Description: TODO 【获取数据集合】
	 * @param @return 设定文件
	 * @return List<T> 返回类型
	 * @throws
	 */
	public List<T> getList() {
		return list;
	}

	/**
	 * 
	 * @Title: setList
	 * @Description: TODO 【设置数据集合】
	 * @param @param list 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void setList(List<T> list) {
		this.list = list;
	}

	/**
	 * 
	 * @Title: clear
	 * @Description: TODO 【清空数据】
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void clear() {
		this.list.clear();
		notifyDataSetChanged();
	}

	/**
	 * 
	 * @Title: addAll
	 * @Description: TODO 【添加数据】
	 * @param @param list 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void addAll(List<T> list) {
		if (list != null) {
			this.list.addAll(list);
			notifyDataSetChanged();
		}
	}

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		T t = list.get(position);
		if (null == convertView) {
			convertView = inflate(getContentView());
		}
		onInitView(convertView, position, t);
		return convertView;
	}

	/**
	 * 
	 * @Title: inflate
	 * @Description: TODO 【加载布局】
	 * @param @param layoutResID
	 * @param @return 设定文件
	 * @return View 返回类型
	 * @throws
	 */
	private View inflate(int layoutResID) {
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(layoutResID, null);
		return view;
	}

	/**
	 * 
	 * @Title: getContentView
	 * @Description: TODO 【获取item布局】
	 * @param @return 设定文件
	 * @return int 返回类型
	 * @throws
	 */
	public abstract int getContentView();

	/**
	 * 
	 * @Title: onInitView
	 * @Description: TODO 【数据初始化】
	 * @param @param view
	 * @param @param position 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public abstract void onInitView(View view, int position, T item);

	/**
	 * 
	 * @Title: getResourcesString
	 * @Description: TODO 【获取String资源文件】
	 * @param @param id
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	protected String getResourcesString(int id) {
		return context.getResources().getString(id);
	}

	/**
	 * 
	 * @Title: getResourcesColor
	 * @Description: TODO 【获取color资源文件】
	 * @param @param color
	 * @param @return 设定文件
	 * @return int 返回类型
	 * @throws
	 */
	protected int getResourcesColor(int color) {
		return context.getResources().getColor(color);
	}

}
