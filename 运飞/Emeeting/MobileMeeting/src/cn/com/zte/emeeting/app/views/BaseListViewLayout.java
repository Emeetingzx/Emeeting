package cn.com.zte.emeeting.app.views;

import java.util.ArrayList;
import java.util.List;

import cn.com.zte.emeeting.app.util.BitmapUtil;
import cn.com.zte.mobileemeeting.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 自定义附带滑动刷新功能ListView控件
 * 
 * @author sunli
 * */
public class BaseListViewLayout extends RelativeLayout {

	private String TAG = BaseListViewLayout.class.getSimpleName();

	private LayoutInflater mInflater;
	/**
	 * 整体视图
	 */
	private View view;

	/** 列表数据显示控件ListView */
	private BaseListView base_listview_layout_listview;

	public BaseListView getBaseListView() {
		return base_listview_layout_listview;
	}

	/** 下拉开始刷新数据提示信息显示布局 */
	private LinearLayout pull_refresh_layout;

	/** 上滑开始刷新数据提示信息显示布局 */
	private LinearLayout slide_refresh_layout;

	private BaseRefreshDataMeans bsrdMeans;

	/** 是否允许点击上滑刷新，默认不允许 */
	private boolean allowedClickRefresh = false;

	/** 是否允许上滑刷新，默认允许 */
	private boolean allowedSlideRefresh = true;

	/** 是否允许下拉刷新，默认允许 */
	private boolean allowedPullRefresh = true;

	/** 上滑刷新总次数 */
	private int slideRefreshTotalCount = 0;

	/** 数据更新中提示内容布局 */
	private LinearLayout blvl_clickrefresh_layout;

	/** 点击更新数据按钮 */
	private TextView blvl_clickrefresh_text;

	/** 点击更新数据外层布局 */
	private RelativeLayout clickrefresh_layout;

	/** 用户添加的HeaderView集合 */
	private List<View> headerViews;

	/** 用户添加的FooterView集合 */
	private List<View> footerViews;

	private Context mContext;

	/** 手指是否抬起 */
	private boolean whetherTheUp = false;

	public BaseListViewLayout(Context context) {
		super(context);
		this.mContext = context;
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		initView();
	}

	public BaseListViewLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		initView();
	}

	public BaseListViewLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		initView();
	}

	/**
	 * 初始化界面控件
	 * */
	private void initView() {
		view = mInflater.inflate(R.layout.base_listview_layout_view, null);
		base_listview_layout_listview = (BaseListView) view
				.findViewById(R.id.base_listview_layout_listview);
		base_listview_layout_listview.setBaseListViewLayout(this);
		initViewEvents();
		addView(view);
	}

	/** 初始化控件事件 */
	private void initViewEvents() {
		base_listview_layout_listview
				.setOnScrollListener(new AbsListView.OnScrollListener() {
					@Override
					public void onScrollStateChanged(AbsListView view,
							int scrollState) {

						/** 当不滚动时 */
						if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
							Log.i(TAG, view.getLastVisiblePosition() + "   "
									+ view.getFirstVisiblePosition() + "   "
									+ view.getCount());
							if (allowedSlideRefresh) {
								/** 判断是否滚动到底部 */
								if (view.getLastVisiblePosition() >= view
										.getCount() - 1
										&& slide_refresh_layout.getVisibility() == View.VISIBLE) {
//									if (whetherTheUp) {
										/** 加载更多功能的代码 */
										Log.i(TAG, "上滑刷新");
										if (allowedClickRefresh) {
											base_listview_layout_listview.slideRefreshCount = base_listview_layout_listview.slideRefreshCount + 1;
											if (base_listview_layout_listview.slideRefreshCount == slideRefreshTotalCount) {
												/** 刷新次数超过刷新总次数 */
												bsrdMeans.SlideRefreshData();
												swicthCickRefreshView();
											} else if (base_listview_layout_listview.slideRefreshCount < slideRefreshTotalCount) {
												if (bsrdMeans != null) {
													base_listview_layout_listview
															.setEnabled(false);
													bsrdMeans
															.SlideRefreshData();
												}
											}
										} else {
											if (bsrdMeans != null) {
												base_listview_layout_listview
														.setEnabled(false);
												bsrdMeans.SlideRefreshData();
											}
										}
//									}
								}
							} else {
								slide_refresh_layout.setVisibility(View.GONE);
							}
							if (allowedPullRefresh) {
//								if (whetherTheUp) {
									/** 判断是否滚动到顶部 */
									if (view.getFirstVisiblePosition() <= 0
											&& pull_refresh_layout
													.getVisibility() == View.VISIBLE) {
										Log.i(TAG, "下拉刷新");
										if (bsrdMeans != null) {
											base_listview_layout_listview
													.setEnabled(false);
											base_listview_layout_listview.slideRefreshCount = 0;
											bsrdMeans.PullRefreshData();
										}
									}
//								}
							} else {
								pull_refresh_layout.setVisibility(View.GONE);
							}
						}
					}

					@Override
					public void onScroll(AbsListView view,
							int firstVisibleItem, int visibleItemCount,
							int totalItemCount) {
						// TODO Auto-generated method stub

					}

				});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			whetherTheUp = false;
			
			break;
		case MotionEvent.ACTION_UP:
			whetherTheUp = true;
			break;

		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	/** 切换点击更新模式 */
	private void swicthCickRefreshView() {
		slide_refresh_layout.setVisibility(View.GONE);
		base_listview_layout_listview.sr_layout = null;
		blvl_clickrefresh_layout.setVisibility(View.INVISIBLE);
		blvl_clickrefresh_text.setVisibility(View.VISIBLE);
	}

	/** 设置ListViewAdapter */
	public void setLVAdapter(ListAdapter adapter) {
		/** 下拉刷新数据提示内容布局 */
		View headerView = mInflater.inflate(R.layout.base_listview_header_view,
				null);
		pull_refresh_layout = (LinearLayout) headerView
				.findViewById(R.id.base_listview_header_layout);
		pull_refresh_layout.setVisibility(View.GONE);
		base_listview_layout_listview.pr_layout = pull_refresh_layout;
		base_listview_layout_listview.addHeaderView(headerView);
		/** 添加刷新提示以外的HeaderView */
		if (headerViews != null && headerViews.size() > 0) {
			for (int i = 0; i < headerViews.size(); i++) {
				base_listview_layout_listview.addHeaderView(headerViews.get(i));
			}
		}
		/** 添加刷新提示以外的FooterView */
		if (footerViews != null && footerViews.size() > 0) {
			for (int i = 0; i < footerViews.size(); i++) {
				base_listview_layout_listview.addHeaderView(footerViews.get(i));
			}
		}
		/** 上滑刷新数据提示内容布局 */
		View footView = mInflater.inflate(R.layout.base_listview_foot_view,
				null);
		slide_refresh_layout = (LinearLayout) footView
				.findViewById(R.id.base_listview_foot_layout);
		slide_refresh_layout.setVisibility(View.GONE);
		base_listview_layout_listview.sr_layout = slide_refresh_layout;
		base_listview_layout_listview.addFooterView(footView);
		/** 点击刷新数据提示内容布局 */
		View clickRefreshView = mInflater.inflate(
				R.layout.base_listview_foot_clickrefresh_view, null);
		blvl_clickrefresh_layout = (LinearLayout) clickRefreshView
				.findViewById(R.id.blvl_clickrefresh_layout);
		blvl_clickrefresh_text = (TextView) clickRefreshView
				.findViewById(R.id.blvl_clickrefresh_text);
		blvl_clickrefresh_layout.setVisibility(View.GONE);
		blvl_clickrefresh_text.setVisibility(View.GONE);
		clickrefresh_layout = (RelativeLayout) clickRefreshView
				.findViewById(R.id.clickrefresh_layout);
		clickrefresh_layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(TAG, "点击刷新");
				if (bsrdMeans != null) {
					if (blvl_clickrefresh_layout != null) {
						blvl_clickrefresh_layout.setVisibility(View.VISIBLE);
					}
					if (blvl_clickrefresh_text != null) {
						blvl_clickrefresh_text.setVisibility(View.INVISIBLE);
					}
					if (clickrefresh_layout != null) {
						clickrefresh_layout.setEnabled(false);
					}
					base_listview_layout_listview.setEnabled(false);
					bsrdMeans.SlideRefreshData();
				}
			}
		});
		base_listview_layout_listview.addFooterView(clickRefreshView);

		base_listview_layout_listview.setAdapter(adapter);
	}

	/** 设置ListViewItem点击事件 */
	public void setLVOnItemClick(AdapterView.OnItemClickListener onItemClick) {
		base_listview_layout_listview.setOnItemClickListener(onItemClick);
	}

	/** 设置滑动触发事件 */
	public void setLVRefreshDataMeans(BaseRefreshDataMeans means) {
		this.bsrdMeans = means;
	}

	/** 设置是否允许上滑点击刷新 */
	public void setAllowedClickRefresh(boolean allowedClickRefresh) {
		this.allowedClickRefresh = allowedClickRefresh;
	}

	/** 设置是否允许上滑刷新 */
	public void setAllowedSlideRefresh(boolean allowedSlideRefresh) {
		this.allowedSlideRefresh = allowedSlideRefresh;
	}

	/** 设置是否允许下拉刷新 */
	public void setAllowedPullRefresh(boolean allowedPullRefresh) {
		this.allowedPullRefresh = allowedPullRefresh;
	}

	/**
	 * 取得是否允许点击上滑刷新，默认不允许
	 * 
	 * @return the allowedClickRefresh
	 */
	public boolean isAllowedClickRefresh() {
		return allowedClickRefresh;
	}

	/**
	 * 取得是否允许上滑刷新，默认允许
	 * 
	 * @return the allowedSlideRefresh
	 */
	public boolean isAllowedSlideRefresh() {
		return allowedSlideRefresh;
	}

	/**
	 * 取得是否允许下拉刷新，默认允许
	 * 
	 * @return the allowedPullRefresh
	 */
	public boolean isAllowedPullRefresh() {
		return allowedPullRefresh;
	}

	/** 设置上滑刷新总次数 */
	public void setSlideRefreshCount(int slideRefreshTotalCount) {
		this.slideRefreshTotalCount = slideRefreshTotalCount;
		base_listview_layout_listview.slideRefreshTotalCount = slideRefreshTotalCount;
	}

	/** ListView添加HeaderView */
	public void addLVHeaderView(View view) {
		if (headerViews == null) {
			headerViews = new ArrayList<View>();
		}
		headerViews.add(view);
	}

	/** 隐藏标识下拉刷新数据的HeaderView */
	public void hideRefreshDataHeaderView() {
		base_listview_layout_listview.setEnabled(true);
		if (pull_refresh_layout != null) {
			pull_refresh_layout.setVisibility(View.GONE);
		}
	}

	/** 隐藏标识上滑刷新数据的FootView */
	public void hideRefreshDataFootView() {
		base_listview_layout_listview.setEnabled(true);
		if (slide_refresh_layout != null) {
			slide_refresh_layout.setVisibility(View.GONE);
		}
	}

	/** ListView添加FootView */
	public void addLVFootView(View view) {
		if (footerViews == null) {
			footerViews = new ArrayList<View>();
		}
		footerViews.add(view);
	}

	/** 隐藏标识点击刷新数据的View */
	public void hideClickRefreshDataView() {
		base_listview_layout_listview.setEnabled(true);
		if (blvl_clickrefresh_layout != null
				&& blvl_clickrefresh_layout.getVisibility() != View.GONE) {
			blvl_clickrefresh_layout.setVisibility(View.INVISIBLE);
		}
		if (blvl_clickrefresh_text != null
				&& blvl_clickrefresh_text.getVisibility() != View.GONE) {
			blvl_clickrefresh_text.setVisibility(View.VISIBLE);
		}
		if (clickrefresh_layout != null) {
			clickrefresh_layout.setEnabled(true);
		}
	}

	/**
	 * 设置ListView分割线高度 已弃用
	 */
	public void setListViewDividerHeight(int dividerHeight) {
		final float scale = getContext().getResources().getDisplayMetrics().density;
		base_listview_layout_listview.setDividerHeight((int) (dividerHeight
				* scale + 0.5f));
	}

	/**
	 * 设置ListView分割线 已弃用
	 * 
	 * @param dividerHeight为资源文件ID
	 * */
	public void setListViewDivider(int dividerImageId) {
		base_listview_layout_listview.setDivider(mContext.getResources()
				.getDrawable(dividerImageId));
	}

}
