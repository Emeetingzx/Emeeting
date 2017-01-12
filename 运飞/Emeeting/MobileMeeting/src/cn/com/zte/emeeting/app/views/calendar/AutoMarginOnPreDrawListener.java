package cn.com.zte.emeeting.app.views.calendar;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;

/**
 * 根据参考view 获取参考view的高度，并修改被修改view的margin值
 *
 * Create by Xu wenQiang 2014-11-19
 */
public class AutoMarginOnPreDrawListener implements OnPreDrawListener {
	private final static String TAG = "AutoMarginOnPreDrawListener";
	// 被设置margin的view
	private View changeView;
	// 设置margin的参照view，将根据此view的宽或高度为基础
	private View referenceView;
	// 设置哪一侧的margin 1 左外边距 2 上外边距 3 右外边距 4 下外边距
	private int orientation = 1;
	// 补加的值
	private int addValue = 0;
	// 动作类型 1 设置外边距(margin) 2 设置宽度(width) 3 设置高度(height)
	private int actionType = 1;

	/**
	 * 设置margin的构造方法
	 *
	 * @param changeView
	 *            被设置margin的view
	 * @param referenceView
	 *            设置margin的参照view，将根据此view的宽或高度为基础
	 * @param orientation
	 *            设置哪一侧的margin 1 左外边距 2 上外边距 3 右外边距 4 下外边距
	 * @param addValue
	 *            补加的margin值
	 *
	 */
	public AutoMarginOnPreDrawListener(View changeView, View referenceView,
			int orientation, int addValue) {
		this.changeView = changeView;
		this.referenceView = referenceView;
		this.orientation = orientation;
		this.addValue = addValue;
		this.actionType = 1;
	}

	/**
	 * 设置宽度的构造方法
	 *
	 * @param changeView
	 *            被设置margin的view
	 * @param referenceView
	 *            设置margin的参照view，将根据此view的宽为基础
	 * @param addValue
	 *            补加的宽度值
	 *
	 */
	public AutoMarginOnPreDrawListener(View changeView, View referenceView,
			int addValue) {
		this.changeView = changeView;
		this.referenceView = referenceView;
		this.addValue = addValue;
		this.actionType = 2;
	}

	/**
	 * 设置高度的构造方法
	 *
	 * @param changeView
	 *            被设置margin的view
	 * @param referenceView
	 *            设置margin的参照view，将根据此view的宽为基础
	 * @param addValue
	 *            补加的宽度值
	 *
	 */
	public AutoMarginOnPreDrawListener(View changeView, View referenceView) {
		this.changeView = changeView;
		this.referenceView = referenceView;
		this.actionType = 3;
	}

	// @Override
	public boolean onPreDraw() {
		this.referenceView.getViewTreeObserver().removeOnPreDrawListener(this);
		Log.i(TAG, "参照View的高度为: " + referenceView.getMeasuredHeight());
		Log.i(TAG, "参照View的宽度为: " + referenceView.getMeasuredWidth());
		switch (this.actionType) {
		case 1:
			setMargin();
			break;

		case 2:
			setWidth();
			break;
		case 3:
			setHeight();
			break;
		default:
			break;
		}
		return true;
	}

	/**
	 * 设置margin
	 *
	 */
	private void setMargin() {
		Log.i(TAG, "设置margin");
		ViewGroup.MarginLayoutParams changeViewLayoutParams = (ViewGroup.MarginLayoutParams) this.changeView
				.getLayoutParams();
		switch (orientation) {
		case 1:
			changeViewLayoutParams.setMargins(referenceView.getMeasuredWidth()
					+ this.addValue, 0, 0, 0);
			break;
		case 2:
			changeViewLayoutParams.setMargins(0,
					referenceView.getMeasuredHeight() + this.addValue, 0, 0);
			break;
		case 3:
			changeViewLayoutParams.setMargins(0, 0,
					referenceView.getMeasuredWidth() + this.addValue, 0);
			break;
		case 4:
			changeViewLayoutParams.setMargins(0, 0, 0,
					referenceView.getMeasuredHeight() + this.addValue);
			break;
		default:
			break;
		}
	}

	/**
	 * 设置宽度
	 *
	 */
	private void setWidth() {
		Log.i(TAG, "设置宽度");
		ViewGroup.LayoutParams layoutParams = getLayoutParams();
		layoutParams.width = this.referenceView.getMeasuredWidth();
		this.changeView.setLayoutParams(layoutParams);
	}

	/**
	 * 设置高度
	 *
	 */
	private void setHeight() {
		Log.i(TAG, "设置高度");
		ViewGroup.LayoutParams layoutParams = getLayoutParams();
		layoutParams.height = this.referenceView.getMeasuredHeight();
		this.changeView.setLayoutParams(layoutParams);
	}

	/**
	 * 获取LayoutParams
	 *
	 */
	private ViewGroup.LayoutParams getLayoutParams() {
		return (ViewGroup.LayoutParams) this.changeView.getLayoutParams();
	}
}
