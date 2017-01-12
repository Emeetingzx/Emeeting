package cn.com.zte.emeeting.app.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * 自定义ListView
 * 
 * @author sunli
 * */
public class BaseListView extends ListView

{

	private static final int MAX_Y_OVERSCROLL_DISTANCE = 100;

	private Context mContext;

	public static int mMaxYOverscrollDistance;

	/** 下拉开始刷新数据提示信息显示布局 */
	public LinearLayout pr_layout;

	/** 上滑开始刷新数据提示信息显示布局 */
	public LinearLayout sr_layout;

	public LinearLayout sri_layout;

	/** 上滑刷新总次数 */
	public int slideRefreshTotalCount = 0;

	/** 上滑刷新次数 */
	public int slideRefreshCount = 0;

	/** 父容器 */
	private BaseListViewLayout blvl;

	public BaseListView(Context context)

	{

		super(context);

		mContext = context;

		initBounceListView();

	}

	public BaseListView(Context context, AttributeSet attrs)

	{

		super(context, attrs);

		mContext = context;

		initBounceListView();

	}

	public BaseListView(Context context, AttributeSet attrs, int defStyle)

	{

		super(context, attrs, defStyle);

		mContext = context;

		initBounceListView();

	}

	private void initBounceListView()

	{

		final DisplayMetrics metrics = mContext.getResources()
				.getDisplayMetrics();

		final float density = metrics.density;

		mMaxYOverscrollDistance = (int) (density * MAX_Y_OVERSCROLL_DISTANCE);

	}

	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
			int scrollY, int scrollRangeX, int scrollRangeY,
			int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent)

	{
		/* 下拉触发 */
		if (scrollY < -mMaxYOverscrollDistance / 5) {
			boolean pullLogo = true;
			if (blvl != null) {
				try {
					pullLogo = ((BaseListViewLayout) blvl)
							.isAllowedPullRefresh();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (pr_layout != null) {
				if (pullLogo) {
					Log.i("pr_layout", "visible");
					pr_layout.setVisibility(View.VISIBLE);
				} else {
					Log.i("pr_layout", "gone");
					pr_layout.setVisibility(View.GONE);
				}
			}
		}
		/* 上滑触发 */
		if (scrollY > mMaxYOverscrollDistance / 5) {
			boolean slideLogo = true;
			if (blvl != null) {
				try {
					slideLogo = ((BaseListViewLayout) blvl)
							.isAllowedSlideRefresh();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (sr_layout != null) {
				if (slideLogo) {
					Log.i("srlayout", "visible");
					sr_layout.setVisibility(View.VISIBLE);
				} else {
					Log.i("srlayout", "gone");
					sr_layout.setVisibility(View.GONE);
				}
			}
		}
		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY,
				scrollRangeX, scrollRangeY, maxOverScrollX,
				mMaxYOverscrollDistance, isTouchEvent);
	}

	public void setBaseListViewLayout(BaseListViewLayout blvl) {
		this.blvl = blvl;
	}

	public BaseListViewLayout getBaseListViewLayout() {
		return this.blvl;
	}

}
