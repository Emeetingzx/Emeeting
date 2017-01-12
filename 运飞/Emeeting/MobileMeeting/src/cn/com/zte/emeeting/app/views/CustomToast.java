package cn.com.zte.emeeting.app.views;


import cn.com.zte.emeeting.app.util.DensityUtil;
import cn.com.zte.mobileemeeting.R;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

/**
 * 自定义提示框
 * 
 * @author LiuZhiYun
 * 
 *         2015-11-2
 */
public class CustomToast {

	private static CustomToast mToast = null;
	/**
	 * 文本显示控件ID
	 */
	private static final int TEXTVIEWID = 100;
	/**
	 * 底部高度
	 */
	private static final int BOTTOMHIGHT = 80;
	
	public static final int LENGTH_SHORT = 2000;
	public static final int LENGTH_LONG = 3500;

	private final Handler mHandler = new Handler();
	private int mDuration = LENGTH_SHORT;
	private int mGravity = Gravity.CENTER;
	private int mX, mY;
	private float mHorizontalMargin;
	private float mVerticalMargin;
	private View mView;
	private View mNextView;

	private WindowManager mWM;
	private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();

	
	/**
	 * 显示资源文件
	 * @param context
	 * @param resoure
	 */
	public static void show(Context context, int resoure) {
		show(context, context.getString(resoure));
	}
	/**
	 * 显示提示文字
	 * @param context
	 * @param resoure
	 */
	public static void show(Context context, String text) {
		if (mToast == null) {
			mToast = makeText(context, text, LENGTH_SHORT);
			mToast.setGravity(Gravity.BOTTOM, 0,
					DensityUtil.dip2px(context, BOTTOMHIGHT));
		} else {
			((TextView) (mToast.getView().findViewById(TEXTVIEWID))).setText(text);
			mToast.setDuration(LENGTH_SHORT);
		}
		mToast.show();
	}
	
	/**
	 * 自定义显示时长
	 * @param context
	 * @param text
	 * @param duration
	 */
	public static void makeTextAndShow(Context context, CharSequence text,
			int duration) {
		makeText(context,text,duration).show();
	}

	/**
	 * 获取实例化方法
	 * @param context
	 * @param text
	 * @param duration
	 * @return
	 */
	private static CustomToast makeText(Context context, CharSequence text,
			int duration) {
		CustomToast result = new CustomToast(context);

		LinearLayout mLayout = new LinearLayout(context);
		TextView tv = new TextView(context);
		tv.setText(text);
		tv.setId(TEXTVIEWID);
		tv.setTextSize(16);
		tv.setTextColor(Color.WHITE);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params.leftMargin = 20;
		params.rightMargin = 20;
		params.topMargin = 5;
		params.bottomMargin = 5;
		mLayout.setBackgroundResource(R.drawable.shape_item_toast_bg);
		mLayout.addView(tv,params);
		result.mNextView = mLayout;
		result.mDuration = duration;
		return result;
	}

	
	/**
	 * 构造方法
	 * @param context
	 */
	public CustomToast(Context context) {
		init(context);
	}

	/**
	 * Set the view to show.
	 * 
	 * @see #getView
	 */
	public void setView(View view) {
		mNextView = view;
	}

	/**
	 * Return the view.
	 * 
	 * @see #setView
	 */
	public View getView() {
		return mNextView;
	}

	/**
	 * Set how long to show the view for.
	 * 
	 * @see #LENGTH_SHORT
	 * @see #LENGTH_LONG
	 */
	public void setDuration(int duration) {
		mDuration = duration;
	}

	/**
	 * Return the duration.
	 * 
	 * @see #setDuration
	 */
	public int getDuration() {
		return mDuration;
	}

	/**
	 * Set the margins of the view.
	 * 
	 * @param horizontalMargin
	 *            The horizontal margin, in percentage of the container width,
	 *            between the container's edges and the notification
	 * @param verticalMargin
	 *            The vertical margin, in percentage of the container height,
	 *            between the container's edges and the notification
	 */
	public void setMargin(float horizontalMargin, float verticalMargin) {
		mHorizontalMargin = horizontalMargin;
		mVerticalMargin = verticalMargin;
	}

	/**
	 * Return the horizontal margin.
	 */
	public float getHorizontalMargin() {
		return mHorizontalMargin;
	}

	/**
	 * Return the vertical margin.
	 */
	public float getVerticalMargin() {
		return mVerticalMargin;
	}

	/**
	 * Set the location at which the notification should appear on the screen.
	 * 
	 * @see android.view.Gravity
	 * @see #getGravity
	 */
	public void setGravity(int gravity, int xOffset, int yOffset) {
		mGravity = gravity;
		mX = xOffset;
		mY = yOffset;
	}

	/**
	 * Get the location at which the notification should appear on the screen.
	 * 
	 * @see android.view.Gravity
	 * @see #getGravity
	 */
	public int getGravity() {
		return mGravity;
	}

	/**
	 * Return the X offset in pixels to apply to the gravity's location.
	 */
	public int getXOffset() {
		return mX;
	}

	/**
	 * Return the Y offset in pixels to apply to the gravity's location.
	 */
	public int getYOffset() {
		return mY;
	}

	/**
	 * schedule handleShow into the right thread
	 */
	public void show() {
		try {
			mHandler.removeCallbacks(mHide);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		mHandler.post(mShow);
		if (mDuration > 0) {
			mHandler.postDelayed(mHide, mDuration);
		}
	}

	/**
	 * schedule handleHide into the right thread
	 */
	public void hide() {
		mHandler.post(mHide);
	}

	private final Runnable mShow = new Runnable() {
		public void run() {
			handleShow();
		}
	};

	private final Runnable mHide = new Runnable() {
		public void run() {
			handleHide();
		}
	};

	private void init(Context context) {
		final WindowManager.LayoutParams params = mParams;
		mParams.gravity = Gravity.BOTTOM;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
				| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
		params.format = PixelFormat.TRANSLUCENT;
		params.windowAnimations = android.R.style.Animation_Toast;
		params.type = WindowManager.LayoutParams.TYPE_TOAST;

		mWM = (WindowManager) context.getApplicationContext().getSystemService(
				Context.WINDOW_SERVICE);
	}

	private void handleShow() {

		if (mView != mNextView) {
			// remove the old view if necessary
			handleHide();
			mView = mNextView;
			// mWM = WindowManagerImpl.getDefault();
			final int gravity = mGravity;
			mParams.gravity = gravity;
			if ((gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.FILL_HORIZONTAL) {
				mParams.horizontalWeight = 1.0f;
			}
			if ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.FILL_VERTICAL) {
				mParams.verticalWeight = 1.0f;
			}
			mParams.x = mX;
			mParams.y = mY;
			mParams.verticalMargin = mVerticalMargin;
			mParams.horizontalMargin = mHorizontalMargin;
			if (mView.getParent() != null) {
				mWM.removeView(mView);
			}
			mWM.addView(mView, mParams);
		}
	}

	private void handleHide() {
		if (mView != null) {
			if (mView.getParent() != null) {
				mWM.removeView(mView);
			}
			mView = null;
		}
	}
}
