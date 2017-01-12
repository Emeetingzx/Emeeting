package cn.com.zte.emeeting.app.views.dialog;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ab.util.AbAppUtil;
import com.ab.view.pullview.AbListViewFooter;
import com.ab.view.pullview.AbListViewHeader;

/**
 * 
 * @ClassName: AbViewUtil
 * @Description: TODO 【View工具类】
 * @author Pan.Jianbo
 * @date 2015-8-5 下午2:45:39
 * 
 */

public class AbViewUtil {

	/**
	 * 无效值
	 */
	public static final int INVALID = Integer.MIN_VALUE;

	/**
	 * 测量这个view 最后通过getMeasuredWidth()获取宽度和高度.
	 * 
	 * @param view
	 *            要测量的view
	 * @return 测量过的view
	 */
	public static void measureView(View view) {
		ViewGroup.LayoutParams p = view.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		view.measure(childWidthSpec, childHeightSpec);
	}

	/**
	 * 获得这个View的宽度 测量这个view，最后通过getMeasuredWidth()获取宽度.
	 * 
	 * @param view
	 *            要测量的view
	 * @return 测量过的view的宽度
	 */
	public static int getViewWidth(View view) {
		measureView(view);
		return view.getMeasuredWidth();
	}

	/**
	 * 获得这个View的高度 测量这个view，最后通过getMeasuredHeight()获取高度.
	 * 
	 * @param view
	 *            要测量的view
	 * @return 测量过的view的高度
	 */
	public static int getViewHeight(View view) {
		measureView(view);
		return view.getMeasuredHeight();
	}

	/**
	 * 从父亲布局中移除自己
	 * 
	 * @param v
	 */
	public static void removeSelfFromParent(View v) {
		ViewParent parent = v.getParent();
		if (parent != null) {
			if (parent instanceof ViewGroup) {
				((ViewGroup) parent).removeView(v);
			}
		}
	}

	/**
	 * 描述：dip转换为px.
	 * 
	 * @param context
	 *            the context
	 * @param dipValue
	 *            the dip value
	 * @return px值
	 */
	public static float dip2px(Context context, float dipValue) {
		DisplayMetrics mDisplayMetrics = AbAppUtil.getDisplayMetrics(context);
		return applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue,
				mDisplayMetrics);
	}

	/**
	 * 描述：px转换为dip.
	 * 
	 * @param context
	 *            the context
	 * @param pxValue
	 *            the px value
	 * @return dip值
	 */
	public static float px2dip(Context context, float pxValue) {
		DisplayMetrics mDisplayMetrics = AbAppUtil.getDisplayMetrics(context);
		return pxValue / mDisplayMetrics.density;
	}

	/**
	 * 描述：sp转换为px.
	 * 
	 * @param context
	 *            the context
	 * @param spValue
	 *            the sp value
	 * @return sp值
	 */
	public static float sp2px(Context context, float spValue) {
		DisplayMetrics mDisplayMetrics = AbAppUtil.getDisplayMetrics(context);
		return applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue,
				mDisplayMetrics);
	}

	/**
	 * 描述：px转换为sp.
	 * 
	 * @param context
	 *            the context
	 * @param spValue
	 *            the sp value
	 * @return sp值
	 */
	public static float px2sp(Context context, float pxValue) {
		DisplayMetrics mDisplayMetrics = AbAppUtil.getDisplayMetrics(context);
		return pxValue / mDisplayMetrics.scaledDensity;
	}

	/**
	 * TypedValue官方源码中的算法，任意单位转换为PX单位
	 * 
	 * @param unit
	 *            TypedValue.COMPLEX_UNIT_DIP
	 * @param value
	 *            对应单位的值
	 * @param metrics
	 *            密度
	 * @return px值
	 */
	public static float applyDimension(int unit, float value,
			DisplayMetrics metrics) {
		switch (unit) {
		case TypedValue.COMPLEX_UNIT_PX:
			return value;
		case TypedValue.COMPLEX_UNIT_DIP:
			return value * metrics.density;
		case TypedValue.COMPLEX_UNIT_SP:
			return value * metrics.scaledDensity;
		case TypedValue.COMPLEX_UNIT_PT:
			return value * metrics.xdpi * (1.0f / 72);
		case TypedValue.COMPLEX_UNIT_IN:
			return value * metrics.xdpi;
		case TypedValue.COMPLEX_UNIT_MM:
			return value * metrics.xdpi * (1.0f / 25.4f);
		}
		return 0;
	}

	/**
	 * 
	 * 描述：是否需要Scale.
	 * 
	 * @param view
	 * @return
	 */
	public static boolean isNeedScale(View view) {
		if (view instanceof AbListViewHeader) {
			return false;
		}

		if (view instanceof AbListViewFooter) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @Title: setListViewHeightBasedOnChildren
	 * @Description: TODO 【动态设置ListView的高度】
	 * @param @param listView 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	/**
	 * 
	 * @Title: isShouldHideInput
	 * @Description: TODO 【判断点击区域】
	 * @param @param v
	 * @param @param event
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean isShouldHideInput(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] leftTop = { 0, 0 };
			// 获取输入框当前的location位置
			v.getLocationInWindow(leftTop);
			int left = leftTop[0];
			int top = leftTop[1];
			int bottom = top + v.getHeight();
			int right = left + v.getWidth();
			if (event.getX() > left && event.getX() < right
					&& event.getY() > top && event.getY() < bottom) {
				// 点击的是输入框区域，保留点击EditText的事件
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

}
