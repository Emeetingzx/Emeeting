package cn.com.zte.emeeting.app.views.pulllistview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;

public class PullableListView extends ListView implements Pullable
{
	/**
	 * 是否可以加载更多
	 */
	private boolean isCanLoadMore = true;

	public PullableListView(Context context)
	{
		super(context);
	}

	public PullableListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public PullableListView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	@Override
	public boolean canPullDown()
	{
		try {
			if (getCount() == 0)
			{
				// 没有item的时候也可以下拉刷新
				return true;
			} else if (getFirstVisiblePosition() == 0
					&& getChildAt(0).getTop() >= 0)
			{
				// 滑到ListView的顶部了
				return true;
			} else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	@Override
	public boolean canPullUp()
	{
		try {
			if (!isCanLoadMore) {
				return false;
			}
			if (getCount() == 0)
			{
				// 没有item的时候也可以上拉加载
				return true;
			} else if (getLastVisiblePosition() == (getCount() - 1))
			{
				// 滑到底部了
				if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
						&& getChildAt(
								getLastVisiblePosition()
										- getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
					return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return false;
		
	}

	
	public boolean isCanLoadMore() {
		return isCanLoadMore;
	}

	/**
	 * 是否可以加载更多
	 * @param isCanLoadMore
	 */
	public void setCanLoadMore(boolean isCanLoadMore) {
		this.isCanLoadMore = isCanLoadMore;
	}
}
