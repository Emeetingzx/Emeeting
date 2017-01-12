package cn.com.zte.emeeting.app.views;

import cn.com.zte.emeeting.app.util.DensityUtil;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * 滑动布局
 * 
 * @author 6396000419
 * 
 */
public class SlidingLayout extends RelativeLayout {


	public SlidingLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public SlidingLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public SlidingLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		// super.onLayout(changed, l, t, r, b);
		int childLeft = 0;
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			if (child.getVisibility() != View.GONE) {
				final int childWidth = child.getMeasuredWidth();
				child.layout(childLeft, 0, childLeft + childWidth,
						child.getMeasuredHeight());
				childLeft += childWidth;
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		getChildAt(0).measure(widthMeasureSpec, heightMeasureSpec);
		View child = getChildAt(1);
		int widthSpec = getChildMeasureSpec(widthMeasureSpec, child.getPaddingLeft()+child.getPaddingRight(), child.getLayoutParams().width);
		int heightSpec = MeasureSpec.makeMeasureSpec(getChildAt(0).getMeasuredHeight(), MeasureSpec.EXACTLY);
		child.measure(widthSpec, heightSpec);
	}
	
	@Override
	protected void measureChild(View child, int parentWidthMeasureSpec,
			int parentHeightMeasureSpec) {
		// TODO Auto-generated method stub
		super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);
	}

}
