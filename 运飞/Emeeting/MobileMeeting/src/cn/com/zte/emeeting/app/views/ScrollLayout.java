package cn.com.zte.emeeting.app.views;

import com.nineoldandroids.view.ViewHelper;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class ScrollLayout extends ScrollView {

	private GestureDetector mGestureDetector;
	View.OnTouchListener mGestureListener;

	public ScrollLayout(Context context) {
		super(context);
		mGestureDetector = new GestureDetector(context, new YScrollDetector());
		setFadingEdgeLength(0);
	}

	public ScrollLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mGestureDetector = new GestureDetector(context, new YScrollDetector());
		setFadingEdgeLength(0);
	}

	public ScrollLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mGestureDetector = new GestureDetector(context, new YScrollDetector());
		setFadingEdgeLength(0);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (ev.getPointerCount() > 1) {
			return false;
		}
		return super.onInterceptTouchEvent(ev);
	}

	// @Override
	// public boolean onInterceptTouchEvent(MotionEvent ev) {
	// return super.onInterceptTouchEvent(ev)
	// && mGestureDetector.onTouchEvent(ev);
	// }

	// Return false if we're scrolling in the x direction
	class YScrollDetector extends SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			if (Math.abs(distanceY) > Math.abs(distanceX)) {
				return true;
			}
			return false;
		}
	}

}
