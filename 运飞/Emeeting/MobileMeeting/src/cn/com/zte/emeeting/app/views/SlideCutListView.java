package cn.com.zte.emeeting.app.views;

import cn.com.zte.emeeting.app.util.DensityUtil;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.View.MeasureSpec;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/**
 * 可左右滑动的listview
 * 
 * @author LangK
 * 
 */
public class SlideCutListView extends ListView {
	/**
	 * 当前滑动的ListView　position
	 */
	private int slidePosition;
	/**
	 * 手指按下X的坐标
	 */
	private int downY;
	/**
	 * 手指按下Y的坐标
	 */
	private int downX;
	
	/**
	 * 手指按下X的坐标,不再改变
	 */
	private int findownY;
	/**
	 * 手指按下Y的坐标,不再改变
	 */
	private int findownX;
	/**
	 * 屏幕宽度
	 */
	private int screenWidth;
	/**
	 * ListView的item
	 */
	private View itemView;
	/**
	 * ListView的item的中间布局
	 */
	private View scrollView;
	/**
	 * ListView的item的菜单布局
	 */
	// private View menuScrollView;
	/**
	 * 滑动类
	 */
	private Scroller scroller;
	private static final int SNAP_VELOCITY = 600;
	/**
	 * 速度追踪对象
	 */
	private VelocityTracker velocityTracker;
	/**
	 * 是否响应滑动，默认为不响应
	 */
	private boolean isSlide = false;
	/**
	 * 认为是用户滑动的最小距离
	 */
	private int mTouchSlop;
	/**
	 * 移除item后的回调接口
	 */
	private RemoveListener mRemoveListener;
	/**
	 * 用来指示item滑出屏幕的方向,向左或者向右,用一个枚举值来标记
	 */
	private RemoveDirection removeDirection;

	/**
	 * 滑动最大的宽度
	 */
	private int slideMaxWidth = 100;

	// 滑动删除方向的枚举值
	public enum RemoveDirection {
		NORMAL, LEFT;
	}

	public SlideCutListView(Context context) {
		this(context, null);
	}

	public SlideCutListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SlideCutListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		screenWidth = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
				.getWidth();
		scroller = new Scroller(context);
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
		slideMaxWidth = DensityUtil.dip2px(context, 100);
	}

	/**
	 * 恢复所有滑出来删除按钮的item
	 */
	public void restroeView() {
		for (int i = 0; i < getChildCount(); i++) {
			View view = (((LinearLayout) getChildAt(i)).getChildAt(0));
			Object object = view.getTag();
			if (object != null
					&& (RemoveDirection) object == RemoveDirection.LEFT) {
				view.scrollTo(0, 0);
				view.setTag(RemoveDirection.NORMAL);
			}
		}
	}

	/**
	 * 设置滑动删除的回调接口
	 * 
	 * @param removeListener
	 */
	public void setRemoveListener(RemoveListener removeListener) {
		this.mRemoveListener = removeListener;
	}

	private long downTime;

	/**
	 * 分发事件，主要做的是判断点击的是那个item, 以及通过postDelayed来设置响应左右滑动事件
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			addVelocityTracker(event);
			downTime = System.currentTimeMillis();

			// 假如scroller滚动还没有结束，我们直接返回
			if (!scroller.isFinished()) {
				return super.dispatchTouchEvent(event);
			}
			downX = (int) event.getX();
			downY = (int) event.getY();
			findownY  = (int) event.getY();
			findownX  = (int) event.getX();
			slidePosition = pointToPosition(downX, downY);

			// 无效的position, 不做任何处理
			if (slidePosition == AdapterView.INVALID_POSITION) {
				Log.d("TAG", "INVALID_POSITION");
				return super.dispatchTouchEvent(event);
			}

			// 恢复列表中已经滑动开删除菜单的item
			for (int i = 0; i < getChildCount(); i++) {
				scrollView = (((LinearLayout) getChildAt(i)).getChildAt(0));
				Object object = scrollView.getTag();
				if (object != null
						&& (RemoveDirection) object == RemoveDirection.LEFT) {
					if (i != slidePosition - getFirstVisiblePosition()) {
						scrollRight();
						scrollView.setTag(RemoveDirection.NORMAL);
						return false;
					}
				}
			}

			// 获取我们点击的item view
			itemView = getChildAt(slidePosition - getFirstVisiblePosition());
			scrollView = (((LinearLayout) itemView).getChildAt(0));
			if(scrollView.getTag()==null){
				scrollView.setTag(RemoveDirection.NORMAL);
			}
			removeDirection = (RemoveDirection) scrollView.getTag();
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			if (Math.abs(getScrollVelocity()) > SNAP_VELOCITY
					|| (Math.abs(event.getX() - downX) > mTouchSlop)) {
				isSlide = true;
			}
//			if (Math.abs(getScrollVelocity()) > SNAP_VELOCITY
//					|| (Math.abs(event.getX() - downX) > mTouchSlop && Math
//							.abs(event.getY() - downY) < mTouchSlop)) {
//				isSlide = true;
//
//			}
			break;
		}
		case MotionEvent.ACTION_UP:
			recycleVelocityTracker();
			long diffTime = System.currentTimeMillis() - downTime;
			if (diffTime < 500) {
				if (Math.abs(event.getX() - findownX) < 100
						&& Math.abs(event.getY() - findownY) < 100) {
					// 点击事件
					if (findownX<screenWidth-slideMaxWidth) {
						if (removeDirection == RemoveDirection.LEFT) {
							scrollRight();
							scrollView.setTag(RemoveDirection.NORMAL);
							isSlide = false;
							return true;
						}
					}
					
				}
			}
			break;
		}

		return super.dispatchTouchEvent(event);
	}

	/**
	 * 滑动效果时间
	 */
	private int DELTA = 500;
	
	/**
	 * 往右滑动，getScrollX()返回的是左边缘的距离，就是以View左边缘为原点到开始滑动的距离，所以向右边滑动为负值
	 */
	private void scrollRight() {
		final int delta = (- scrollView.getScrollX());
		scroller.startScroll(scrollView.getScrollX(), 0, delta, 0,
				DELTA);
		postInvalidate(); // 刷新itemView
	}

	/**
	 * 向左滑动，根据上面我们知道向左滑动为正值
	 */
	private void scrollLeft() {
		final int delta = (slideMaxWidth - scrollView.getScrollX());
		// 调用startScroll方法来设置一些滚动的参数，我们在computeScroll()方法中调用scrollTo来滚动item
		scroller.startScroll(scrollView.getScrollX(), 0, delta, 0,
				DELTA);
		postInvalidate(); // 刷新itemView
	}

	/**
	 * 根据手指滚动itemView的距离来判断是滚动到开始位置还是向左或者向右滚动
	 */
	private void scrollByDistanceX() {
		// 如果向左滚动的距离大于屏幕的二分之一，就让其删除
		if (scrollView.getScrollX() >= screenWidth / 2) {
			scrollLeft();
			scrollView.setTag(RemoveDirection.LEFT);
		} else {
			// 滚回到原始位置,为了偷下懒这里是直接调用scrollTo滚动
			scrollRight();
			scrollView.setTag(RemoveDirection.NORMAL);
		}

	}

	/**
	 * 处理我们拖动ListView item的逻辑
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (isSlide && slidePosition != AdapterView.INVALID_POSITION) {
			requestDisallowInterceptTouchEvent(true);
			addVelocityTracker(ev);
			final int action = ev.getAction();
			int x = (int) ev.getX();
			switch (action) {
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_MOVE:

				MotionEvent cancelEvent = MotionEvent.obtain(ev);
				cancelEvent
						.setAction(MotionEvent.ACTION_CANCEL
								| (ev.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
				onTouchEvent(cancelEvent);
				int deltaX = downX - x;
				// 手指拖动itemView滚动, deltaX大于0向左滚动，小于0不滑动
				if (removeDirection == RemoveDirection.LEFT || deltaX > 0) {
					downX = x;
					if (scrollView.getScrollX() < 0) {
						scrollView.scrollBy(0, 0);
					} else {
						scrollView.scrollBy(deltaX, 0);
					}
				}
				return true; // 拖动的时候ListView不滚动
			case MotionEvent.ACTION_UP:
				
				int velocityX = getScrollVelocity();
				if (velocityX > SNAP_VELOCITY
						|| Math.abs(scrollView.getScrollX()) < slideMaxWidth / 2) {
					scrollRight();
					scrollView.setTag(RemoveDirection.NORMAL);
				} else if (velocityX < -SNAP_VELOCITY
						|| Math.abs(scrollView.getScrollX()) > slideMaxWidth / 2) {
					scrollLeft();
					scrollView.setTag(RemoveDirection.LEFT);
				} else {
					scrollByDistanceX();
				}

				recycleVelocityTracker();
				// 手指离开的时候就不响应左右滚动
				isSlide = false;
				break;
			}
		}

		// 否则直接交给ListView来处理onTouchEvent事件
		return super.onTouchEvent(ev);
	}

	@Override
	public void computeScroll() {
		// 调用startScroll的时候scroller.computeScrollOffset()返回true，
		if (scroller.computeScrollOffset()) {
			// 让ListView item根据当前的滚动偏移量进行滚动
			scrollView.scrollTo(scroller.getCurrX(), scroller.getCurrY());
			postInvalidate();

			// 滚动动画结束的时候调用回调接口
			if (scroller.isFinished()) {
				if (mRemoveListener != null) {
					mRemoveListener.removeItem(removeDirection, slidePosition);
				}
			}
		}
	}

	/**
	 * 添加用户的速度跟踪器
	 * 
	 * @param event
	 */
	private void addVelocityTracker(MotionEvent event) {
		if (velocityTracker == null) {
			velocityTracker = VelocityTracker.obtain();
		}

		velocityTracker.addMovement(event);
	}

	/**
	 * 移除用户速度跟踪器
	 */
	private void recycleVelocityTracker() {
		if (velocityTracker != null) {
			velocityTracker.recycle();
			velocityTracker = null;
		}
	}

	/**
	 * 获取X方向的滑动速度,大于0向右滑动，反之向左
	 * 
	 * @return
	 */
	private int getScrollVelocity() {
		velocityTracker.computeCurrentVelocity(1000);
		int velocity = (int) velocityTracker.getXVelocity();
		return velocity;
	}

	/**
	 * 
	 * 当ListView item滑出屏幕，回调这个接口 我们需要在回调方法removeItem()中移除该Item,然后刷新ListView
	 * 
	 * @author LangK
	 * 
	 */
	public interface RemoveListener {
		public void removeItem(RemoveDirection direction, int position);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}