package cn.com.zte.emeeting.app.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.zte.mobileemeeting.R;

public class TabViewPager extends LinearLayout {
		/* 数据段begin */
		public final static String TAG = "TabViewPager";
		/**
		 * 上下文
		 */
		private Context mContext;
		/**
		 * 顶部tab布局
		 */
		private LinearLayout mTabHost;
		/**
		 * 下面显示的线条
		 */
		private ImageView mUnderline;

		private ViewPager mViewPager;
		/**
		 * 字体大小
		 */
		private static final int TEXTSIZE = 20;

		private onTabChanged onTabChanged;

		// tab及underline宽度，也是underline的最小移动距离
		private int mTabWidth;

		/* 数据段end */

		/* 函数段begin */
		public TabViewPager(Context context, AttributeSet attrs) {
			super(context, attrs);

			mContext = context;

			inflate(mContext, R.layout.tab_view_pager, this);
			initViews();
		}

		private void initViews() {
			mTabHost = (LinearLayout) findViewById(R.id.tab_host);
			mUnderline = (ImageView) findViewById(R.id.tab_underline);
			mViewPager = (ViewPager) findViewById(R.id.view_pager);
		}

		/**
		 * 使页签点击事件失效
		 */
		public void invalidateTabClick() {
			for (int i = 0; i < mTabHost.getChildCount(); i++) {
				mTabHost.getChildAt(i).setEnabled(false);
			}
		}
		
		/*------------------------ by wtb start -------------------------------*/
		/**
		 * 使页签滑动事件失效
		 */
		public void invalidateTabScroll() {
			mViewPager.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					for(int i = 0; i < mTabHost.getChildCount(); i++){
						if(mViewPager.getCurrentItem() == i){
							return true;
						}
					}
					return false;
				}
			});
		}
		/**
		 * 使页签滑动事件有效
		 */
		public void validateTabScroll() {
			mViewPager.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					for(int i = 0; i < mTabHost.getChildCount(); i++){
						if(mViewPager.getCurrentItem() == i){
							return false;
						}
					}
					return true;
				}
			});
		}
		
		/*------------------------ by wtb end ---------------------------------*/

		/**
		 * 使页签点击事件有效
		 */
		public void validateTabClick() {
			for (int i = 0; i < mTabHost.getChildCount(); i++) {
				mTabHost.getChildAt(i).setEnabled(true);
			}
		}
		
		
		/**
		 * @param tabTitles
		 *            tab显示的标题
		 * @param parentWidth
		 *            父容器的宽
		 */
		public void initTabs(String[] tabTitles, int parentWidth) {
			if (mTabHost != null) {
				mTabHost.removeAllViews();
			}
			LinearLayout.LayoutParams tabHostLayoutParams;
			TextView tab;

			// 设置宽度
			if (tabTitles.length > 0) {
				mTabWidth = parentWidth / tabTitles.length;
				tabHostLayoutParams = new LinearLayout.LayoutParams(mTabWidth,
						LinearLayout.LayoutParams.WRAP_CONTENT);
			} else {
				return;
			}

			// 动态添加tab
			for (int loopVal = 0; loopVal < tabTitles.length; loopVal++) {
				tab = new TextView(mContext);
				tab.setText(tabTitles[loopVal]);
				tab.setTextSize(TEXTSIZE);
				tab.setBackgroundResource(R.drawable.bg_tab_text);
				if (loopVal == 0) {
					tab.setTextColor(Color.parseColor("#00C2C4"));
				} else {
					tab.setTextColor(Color.parseColor("#222222"));
				}
				tabHostLayoutParams.weight = 1;
				tabHostLayoutParams.gravity = Gravity.CENTER_VERTICAL;
				tab.setLayoutParams(tabHostLayoutParams);
				tab.setPadding(0, 20, 0, 20);
				tab.setGravity(Gravity.CENTER);

				tab.setOnClickListener(new TabOnClickListener(loopVal));

				mTabHost.addView(tab);
			}

			// 设置underline宽度，使得下划线与tab宽度保持一致
			RelativeLayout.LayoutParams frameLayoutParams = new RelativeLayout.LayoutParams(mTabWidth, 2);
			frameLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.tab_line);
			Bitmap b = null;// 设置tab的宽度和高度
			if(bitmap.getWidth()>= mTabWidth){
				b = Bitmap.createBitmap(bitmap, 0, 0, mTabWidth, 2);
			}else{
				b = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), 2);;
			}
			mUnderline.setImageBitmap(b);
		}

		public void setAdapter(PagerAdapter pagerAdapter) {
			mViewPager.setAdapter(pagerAdapter);
			// 滑动viewPager时也要执行mUnderline的移动动画
			mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
				private int currentPosition = -1;
				private int nextPosition = -1;

				@Override
				public void onPageSelected(int position) {
					nextPosition = position;
					for (int i = 0; i < mTabHost.getChildCount(); i++) {
						if (i == position) {
							((TextView) mTabHost.getChildAt(i)).setTextColor(Color.parseColor("#00C2C4"));
						} else {
							((TextView) mTabHost.getChildAt(i)).setTextColor(Color.BLACK);
						}
					}
					if (onTabChanged != null) {
						onTabChanged.onTabChanged();
					}
				}

				@Override
				public void onPageScrolled(int position, float positionOffset,
						int positionOffsetPixels) {
					currentPosition = position;
					// new一个矩阵
					Matrix matrix = new Matrix();
					// 设置激活条的最终位置
					matrix.setTranslate(mTabWidth * (position), 0);
					// 在滑动的过程中，计算出激活条应该要滑动的距离
					float t = (mTabWidth) * positionOffset;

					// 使用post追加数值
					matrix.postTranslate(t, 0);

					mUnderline.setImageMatrix(matrix);
				}

				@Override
				public void onPageScrollStateChanged(int state) {
				}
			});
		}

		/**
		 * 得到Viewpager当前显示的选项
		 * 
		 * @return 当前选择
		 */
		public int getCurrentItem() {
			return mViewPager.getCurrentItem();
		}

		public void setCurrentItem(int position) {
			// 记录当前的位置后再设置选中位置
			mViewPager.setCurrentItem(position);

		}

		/* 函数段end */

		public onTabChanged getOnTabChanged() {
			return onTabChanged;
		}

		public void setOnTabChanged(onTabChanged onTabChanged) {
			this.onTabChanged = onTabChanged;
		}

		/* 内部类begin */
		private class TabOnClickListener implements OnClickListener {
			private int viewPosition = -1;

			public TabOnClickListener(int position) {
				viewPosition = position;
			}

			@Override
			public void onClick(View v) {
				setCurrentItem(viewPosition);
			}
		}

		public interface onTabChanged {
			public void onTabChanged();
		}

	}