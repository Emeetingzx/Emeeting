package cn.com.zte.emeeting.app.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;

import cn.com.zte.mobileemeeting.R;
import cn.com.zte.emeeting.app.util.ViewsUtil;

import com.nineoldandroids.view.ViewHelper;

public class SlidingMenu extends HorizontalScrollView
{
	/**
	 * 屏幕宽度
	 */
	private int mScreenWidth;
	/**
	 * dp
	 */
	private int mMenuRightPadding;
	/**
	 * 菜单的宽度
	 */
	private int mMenuWidth;
	private int mHalfMenuWidth;

	private boolean isOpen;

	private boolean once;

	private ViewGroup mMenu;
	private ViewGroup mContent;
	private Button mContentOver;
	
	private InputMethodManager iManager;

	private Context mContext;
	/**
	 * 往下滑动距离
	 */
	private static final int SCROLLDOWN = 0;
	
	public SlidingMenu(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public SlidingMenu(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		this.mContext = context;
		mScreenWidth = getResources().getDisplayMetrics().widthPixels;

		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.SlidingMenu, defStyle, 0);
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++)
		{
			int attr = a.getIndex(i);
			switch (attr)
			{
			case R.styleable.SlidingMenu_rightPadding:
				// 默认50
				mMenuRightPadding = a.getDimensionPixelSize(attr,
						(int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_DIP, 50f,
								getResources().getDisplayMetrics()));// 默认为10DP
				break;
			}
		}
		a.recycle();
	}

	public SlidingMenu(Context context)
	{
		this(context, null, 0);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		/**
		 * 显示的设置一个宽度
		 */
		if (!once)
		{
			RelativeLayout wrapper = (RelativeLayout) getChildAt(0);
			mMenu = (ViewGroup) wrapper.getChildAt(0);
			mContent = (ViewGroup) wrapper.getChildAt(1);
			mContentOver = (Button) wrapper.getChildAt(2);
			
//			mMenuWidth = mScreenWidth - mMenuRightPadding;
			mMenuWidth = ViewsUtil.dip2px(mContext, 238);
			mMenuRightPadding = mScreenWidth - mMenuWidth;
			mHalfMenuWidth = mMenuWidth / 2;
			mMenuWidth = mScreenWidth;
			mMenu.getLayoutParams().width = mMenuWidth;
			mContent.getLayoutParams().width = mScreenWidth;
			mContentOver.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					closeMenu();
				}
			});
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		super.onLayout(changed, l, t, r, b);
		if (changed)
		{
			// 将菜单隐藏
			this.scrollTo(mMenuWidth, 0);
			once = true;
		}
	}
	
	/*拦截touch事件*/
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (!isOpen) {
			return false;
		}
		return super.onInterceptTouchEvent(ev);
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		if (!isOpen) {
			return false;
		}
		int action = ev.getAction();
		switch (action)
		{
		// Up时，进行判断，如果显示区域大于菜单宽度一半则完全显示，否则隐藏
		case MotionEvent.ACTION_UP:
			int scrollX = getScrollX();
			if (scrollX > mHalfMenuWidth)
			{
				this.smoothScrollTo(mMenuWidth, 0);
				isOpen = false;
			} else
			{
				this.smoothScrollTo(0, 0);
				isOpen = true;
			}
			return true;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * 打开菜单
	 */
	public void openMenu()
	{
		if (isOpen)
			return;
		iManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (iManager.isActive()) {
			iManager.hideSoftInputFromWindow(getWindowToken(), 0);
		}
		
		this.smoothScrollTo(0, 0);
		isOpen = true;
		mContentOver.getLayoutParams().width = mContent.getWidth();
		mContentOver.getLayoutParams().height = mContent.getHeight();
		mContentOver.setPivotX(mContent.getPivotX());
		mContentOver.setPivotY(mContent.getPivotY());
		mContentOver.setVisibility(View.VISIBLE);
	}

	/**
	 * 关闭菜单
	 */
	public void closeMenu()
	{
		if (isOpen)
		{
			this.smoothScrollTo(mMenuWidth, 0);
			isOpen = false;
			mContentOver.setVisibility(View.GONE);
		}
	}

	/**
	 * 切换菜单状态
	 */
	public void toggle()
	{
		if (isOpen)
		{
			closeMenu();
			
		} else
		{
			openMenu();
		}
	}

	/**
	 * 是否打开菜单
	 * @return
	 */
	public boolean isOpen(){
		return isOpen;
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt)
	{
		super.onScrollChanged(l, t, oldl, oldt);
		float scale = l * 1.0f / mMenuWidth;
		float leftScale = 1 - 0.3f * scale;
		float rightScale = 0.75f + scale * 0.25f;
		
		float leftMarginScale = 1 - scale;
		int leftMargin = (int) (mMenuRightPadding*leftMarginScale);
		int downMargin = (int) (SCROLLDOWN*leftMarginScale);
		
		ViewHelper.setScaleX(mMenu, leftScale);
		ViewHelper.setScaleY(mMenu, leftScale);
		ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
		ViewHelper.setTranslationX(mMenu, mMenuWidth * scale * 0.7f);

		ViewHelper.setPivotX(mContent, 0);
		ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);
		ViewHelper.setScaleX(mContent, rightScale);
		ViewHelper.setScaleY(mContent, rightScale);
		ViewHelper.setTranslationY(mContent, leftMargin/4+downMargin);
		ViewHelper.setTranslationY(mContentOver, leftMargin/4+downMargin);
		ViewHelper.setTranslationX(mContent, -leftMargin);
		ViewHelper.setTranslationX(mContentOver, -leftMargin);
		
		if (l == mMenuWidth) {
			mContentOver.setVisibility(View.GONE);
		}
		
	}

}
