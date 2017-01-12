package cn.com.zte.emeeting.app.views;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import cn.com.zte.emeeting.app.util.BitmapUtil;

/**
 * 圆形ImageView，可设置最多两个宽度不同且颜色不同的圆形边框。
 * 
 * @author Alan
 */
public class RoundUtilImageView extends ImageView {
	private Bitmap image;
	private Bitmap imageRound;
	private int radius;

	private String TAG = "RoundImageView";
	private int mBorderThickness = 2;
	private Context mContext;
	private int defaultColor = 0xFFFFFFFF;

	private int circleColor = 0xff2c90ff;
	// 如果只有其中一个有值，则只画一个圆形边框
	private int mBorderOutsideColor = 0;
	private int mBorderInsideColor = 0;
	// 控件默认长、宽
	private int defaultWidth = 0;
	private int defaultHeight = 0;
	/**
	 * 图片是否为空，如果为空则画一个圆形
	 */
	private boolean isEmpty = false;
	/**
	 * 是否存在边框
	 */
	private boolean isFrame = true;

	private static Map<Integer, SoftReference<Bitmap>> cachedResBitmap = new ConcurrentHashMap<Integer, SoftReference<Bitmap>>();

	public RoundUtilImageView(Context context) {
		super(context);
		mContext = context;
	}

	public RoundUtilImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}

	public RoundUtilImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
	}

	// @Override
	// protected void onDraw(Canvas canvas) {
	// if (isEmpty) {
	// drawCircle(canvas);
	// return;
	// }
	//
	// // Don't draw anything without an image
	// if (image == null || imageRound == null) {
	// drawCircle(canvas);
	// return;
	// }
	//
	// // Nothing to draw (Empty bounds)
	// if (image.getHeight() == 0 || image.getWidth() == 0) {
	// drawCircle(canvas);
	// return;
	// }
	//
	// // drawRoundImage
	// drawRoundImage(canvas);
	// }

	/**
	 * drawRoundImage. <br/>
	 * 日期: 2015-5-26 下午6:04:03 <br/>
	 * 
	 * @author wangenzi
	 * @param canvas
	 * @since JDK 1.6
	 */
	private void drawRoundImage(Canvas canvas) {
		Drawable drawable = getDrawable();

		if (drawable.getClass() == NinePatchDrawable.class)
			return;

		if (mBorderInsideColor != defaultColor
				&& mBorderOutsideColor != defaultColor) {// 定义画两个边框，分别为外圆边框和内圆边框
			// 画内圆
			drawCircleBorder(canvas, radius + mBorderThickness / 2,
					mBorderInsideColor);
			// 画外圆
			drawCircleBorder(canvas, radius + mBorderThickness
					+ mBorderThickness / 2, mBorderOutsideColor);
		} else if (mBorderInsideColor != defaultColor
				&& mBorderOutsideColor == defaultColor) {// 定义画一个边框 画内圆
			drawCircleBorder(canvas, radius + mBorderThickness / 2,
					mBorderInsideColor);
		} else if (mBorderInsideColor == defaultColor
				&& mBorderOutsideColor != defaultColor) {// 定义画一个边框 画外圆
			drawCircleBorder(canvas, radius + mBorderThickness / 2,
					mBorderOutsideColor);
		} else {// 没有边框
			Log.d(TAG, "没有边框");
		}

		canvas.drawBitmap(imageRound, defaultWidth / 2 - radius, defaultHeight
				/ 2 - radius, null);
		Log.d(TAG, "radius=" + radius + ",mBorderThickness / 2="
				+ mBorderThickness / 2 + "!!!");
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		super.setImageBitmap(bm);

	}

	@Override
	public void setImageDrawable(Drawable drawable) {
		super.setImageDrawable(drawable);

	}

	@Override
	public void setImageResource(int resId) {
		super.setImageResource(resId);

	}

	public void setImageURIPre(Uri uri) {
		Log.d(TAG, "setImageURI");
		super.setImageURI(uri);

		// Extract a Bitmap out of the drawable & set it as the main shader
		image = drawableToBitmap(getDrawable());

		// 生成圆形image
		if (image != null) {
			imageRound = generateRoundImage();
		}

		setImageBitmap(imageRound);
	}

	public void setImageResourcePre(int resId) {
		Log.d(TAG, "setImageResource");

		Bitmap cacheResBitmap = null;
		SoftReference<Bitmap> cacheBitMapRef = cachedResBitmap.get(Integer
				.valueOf(resId));
		if (cacheBitMapRef != null) {
			cacheResBitmap = cacheBitMapRef.get();
		}

		if (cacheResBitmap != null) {
			Log.d(TAG, "setImageResource load  cacheResBitmap resId = " + resId);
			setImageBitmap(cacheResBitmap);
		} else {
			Log.d(TAG, "setImageResource load  new ResBitmap resId = " + resId);
			Drawable da = mContext.getResources().getDrawable(resId);

			// Extract a Bitmap out of the drawable & set it as the main shader
			image = drawableToBitmap(da);

			// 生成圆形image
			if (image != null) {
				imageRound = generateRoundImage();
			}

			setImageBitmap(imageRound);

			cachedResBitmap.put(Integer.valueOf(resId),
					new SoftReference<Bitmap>(imageRound));
		}

	}

	public void setImageDrawablePre(Drawable drawable) {
		Log.d(TAG, "setImageDrawable");
		// Extract a Bitmap out of the drawable & set it as the main shader
		image = drawableToBitmap(drawable);

		// 生成圆形image
		if (image != null) {
			imageRound = generateRoundImage();
		}

		setImageBitmap(imageRound);
	}

	public void setImageBitmapPre(Bitmap resourse) {
		image = resourse;
		// 生成圆形image
		if (image != null) {
			imageRound = generateRoundImage();
		}

		setImageBitmap(imageRound);
	}

	/**
	 * 设置已经处理好圆形的bitmap. <br/>
	 * 日期: 2015-5-27 下午3:28:34 <br/>
	 * 
	 * @author wangenzi
	 * @param bitmap
	 * @since JDK 1.6
	 */
	public void setImageBitmapRound(Bitmap bitmap) {
		Log.d(TAG, "setImageBitmapLazy bitmap is null: " + (bitmap == null));
		// Extract a Bitmap out of the drawable & set it as the main shader

		setImageBitmap(bitmap);
	}

	/**
	 * 生成圆形Image. <br/>
	 * 日期: 2015-5-26 下午5:45:30 <br/>
	 * 
	 * @author wangenzi
	 * @since JDK 1.6
	 */
	private Bitmap generateRoundImage() {
		
		Drawable drawable = getDrawable();
		if (drawable == null) {
			drawable = BitmapUtil.convertBitmap2Drawable(image);
			if (drawable == null) {
				return null;
			}
		}

		this.measure(0, 0);

		int iMWidth = getMeasuredWidth();
		int iMHeight = getMeasuredHeight();

		if (iMWidth == 0 || iMHeight == 0) {
			return null;
		}

		if (drawable.getClass() == NinePatchDrawable.class)
			return null;
		if (defaultWidth == 0) {
			defaultWidth = iMWidth;

		}
		if (defaultHeight == 0) {
			defaultHeight = iMHeight;
		}

		if (mBorderInsideColor != defaultColor
				&& mBorderOutsideColor != defaultColor) {// 定义画两个边框，分别为外圆边框和内圆边框
			radius = (defaultWidth < defaultHeight ? defaultWidth
					: defaultHeight) / 2 - 2 * mBorderThickness;
		} else if (mBorderInsideColor != defaultColor
				&& mBorderOutsideColor == defaultColor) {// 定义画一个边框
			radius = (defaultWidth < defaultHeight ? defaultWidth
					: defaultHeight) / 2 - mBorderThickness;
		} else if (mBorderInsideColor == defaultColor
				&& mBorderOutsideColor != defaultColor) {// 定义画一个边框
			radius = (defaultWidth < defaultHeight ? defaultWidth
					: defaultHeight) / 2 - mBorderThickness;
		} else {// 没有边框
			Log.d(TAG, "没有边框");
			radius = (defaultWidth < defaultHeight ? defaultWidth
					: defaultHeight) / 2;
		}
		imageRound = getCroppedRoundBitmap(image, radius);

		return imageRound;
	}

	/**
	 * 获取裁剪后的圆形图片
	 * 
	 * @param radius
	 *            半径
	 */
	public Bitmap getCroppedRoundBitmap(Bitmap bmp, int radius) {
		Bitmap scaledSrcBmp;
		int diameter = radius * 2;
		if (bmp.getWidth() < diameter) {
			bmp = BitmapUtil.zoomImg(bmp, diameter, diameter);
		}

		// 为了防止宽高不相等，造成圆形图片变形，因此截取长方形中处于中间位置最大的正方形图片
		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();
		int squareWidth = 0, squareHeight = 0;
		int x = 0, y = 0;
		Bitmap squareBitmap;
		if (bmpHeight > bmpWidth) {// 高大于宽
			squareWidth = squareHeight = bmpWidth;
			x = 0;
			y = (bmpHeight - bmpWidth) / 2;
			// 截取正方形图片
			squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth,
					squareHeight);
		} else if (bmpHeight < bmpWidth) {// 宽大于高
			squareWidth = squareHeight = bmpHeight;
			x = (bmpWidth - bmpHeight) / 2;
			y = 0;
			squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth,
					squareHeight);
		} else {
			squareBitmap = bmp;
		}

		if (squareBitmap.getWidth() != diameter
				|| squareBitmap.getHeight() != diameter) {
			scaledSrcBmp = Bitmap.createScaledBitmap(squareBitmap, diameter,
					diameter, true);

		} else {
			scaledSrcBmp = squareBitmap;
		}
		Bitmap output = Bitmap.createBitmap(scaledSrcBmp.getWidth(),
				scaledSrcBmp.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		Paint paint = new Paint();
		Rect rect = new Rect(0, 0, scaledSrcBmp.getWidth(),
				scaledSrcBmp.getHeight());

		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawCircle(scaledSrcBmp.getWidth() / 2,
				scaledSrcBmp.getHeight() / 2, scaledSrcBmp.getWidth() / 2,
				paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(scaledSrcBmp, rect, rect, paint);
		// bitmap回收(recycle导致在布局文件XML看不到效果)
		// bmp.recycle();
		// squareBitmap.recycle();
		// scaledSrcBmp.recycle();
		bmp = null;
		squareBitmap = null;
		scaledSrcBmp = null;
		return output;
	}

	/**
	 * 边缘画圆
	 */
	private void drawCircleBorder(Canvas canvas, int radius, int color) {
		Paint paint = new Paint();
		/* 去锯齿 */
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		paint.setColor(color);
		/* 设置paint的　style　为STROKE：空心 */
		paint.setStyle(Paint.Style.STROKE);
		/* 设置paint的外框宽度 */
		paint.setStrokeWidth(mBorderThickness);
		canvas.drawCircle(defaultWidth / 2, defaultHeight / 2, radius, paint);
	}

	/**
	 * 边缘画圆
	 */
	private void drawCircle(Canvas canvas) {
		mBorderThickness = 3;
		if (defaultWidth == 0) {
			defaultWidth = getWidth();

		}
		if (defaultHeight == 0) {
			defaultHeight = getHeight();
		}
		int radius = (defaultWidth < defaultHeight ? defaultWidth
				: defaultHeight) / 2 - 2 * mBorderThickness;
		Paint paint = new Paint();
		/* 去锯齿 */
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		paint.setColor(circleColor);
		/* 设置paint的　style　为STROKE：空心 */
		paint.setStyle(Paint.Style.STROKE);
		/* 设置paint的外框宽度 */
		paint.setStrokeWidth(mBorderThickness);
		canvas.drawCircle(defaultWidth / 2, defaultHeight / 2, radius, paint);
	}

	/**
	 * Convert a drawable object into a Bitmap.
	 * 
	 * @param drawable
	 *            Drawable to extract a Bitmap from.
	 * @return A Bitmap created from the drawable parameter.
	 */
	public Bitmap drawableToBitmap(Drawable drawable) {
		if (drawable == null) // Don't do anything without a proper drawable
			return null;
		else if (drawable instanceof BitmapDrawable) { // Use the getBitmap()
														// method instead if
														// BitmapDrawable
			Log.i(TAG, "Bitmap drawable!");
			return ((BitmapDrawable) drawable).getBitmap();
		}

		int intrinsicWidth = drawable.getIntrinsicWidth();
		int intrinsicHeight = drawable.getIntrinsicHeight();

		if (!(intrinsicWidth > 0 && intrinsicHeight > 0))
			return null;

		try {
			// Create Bitmap object out of the drawable
			Bitmap bitmap = Bitmap.createBitmap(intrinsicWidth,
					intrinsicHeight, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
			drawable.draw(canvas);
			return bitmap;
		} catch (OutOfMemoryError e) {
			// Simply return null of failed bitmap creations
			Log.e(TAG, "Encountered OutOfMemoryError while generating bitmap!");
			return null;
		}
	}

	/**
	 * 图片是否为空，如果为空则画一个圆形
	 */
	public boolean isEmpty() {
		return isEmpty;
	}

	/**
	 * 图片是否为空，如果为空则画一个圆形
	 */
	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
		invalidate();
	}

	public boolean isFrame() {
		return isFrame;
	}

	public void setFrame(boolean isFrame) {
		this.isFrame = isFrame;
	}

}