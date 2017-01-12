/******************************************************************************
 * Copyright (C) 2014 ZTE Co.,Ltd
 * All Rights Reserved.
 * 本软件为中兴通讯股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package cn.com.zte.emeeting.app.util;

import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.ProgressBar;
import cn.com.zte.android.cache.ImageLoader;
import cn.com.zte.android.common.constants.CommonConstants;
import cn.com.zte.android.common.log.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;

/**
 * 直接生成圆形bitmap的RoundImageLoader. <br/>
 * 日期: 2015-6-11 上午11:48:23 <br/>
 * 
 * @author wangenzi
 * @version 1.0
 * @since JDK 1.6
 * @history 2015-6-11 wangenzi 新建
 */
public class RoundImageLoader {

	/** Tag */
	private static final String TAG = RoundImageLoader.class.getSimpleName();

	/**
	 * 加载webUri. <br/>
	 * 日期: 2014-6-10 下午6:56:20 <br/>
	 * 
	 * @author wangenzi
	 * @param context
	 * @param view
	 * @param uri
	 * @param progressBar
	 * @param defaultMissingImgResId
	 *            图片不存在时默认显示的图片资源ID
	 * @since JDK 1.6
	 */
	public static void loadWebImage(final Context context,
			final ImageView view, final Uri uri,
			final int defaultMissingImgResId) {
//		loadWebImage(context, view, uri, null, defaultMissingImgResId, null,
//				null);
//		ImageLoader.loadWebImage(context, view, uri, null, defaultMissingImgResId, null,
//				null);
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), defaultMissingImgResId);
		FinalBitmap.create(context).display(view, uri.toString(),bitmap,bitmap);
//		ImageLoader.loadWebImage(context, view, uri,
//				defaultMissingImgResId);
	}

	/**
	 * 加载webUri. <br/>
	 * 日期: 2014-6-10 下午6:56:20 <br/>
	 * 
	 * @author wangenzi
	 * @param context
	 * @param view
	 * @param uri
	 * @param progressBar
	 * @param defaultMissingImgResId
	 *            图片不存在时默认显示的图片资源ID
	 * @since JDK 1.6
	 */
	public static void loadWebImage(final Context context,
			final ImageView view, final Uri uri, ProgressBar progressBar,
			final int defaultMissingImgResId) {
		loadWebImage(context, view, uri, null, defaultMissingImgResId, null,
				null);
	}

	/**
	 * 加载webUri. <br/>
	 * 日期: 2014-6-10 下午6:56:20 <br/>
	 * 
	 * @author wangenzi
	 * @param context
	 * @param view
	 * @param uri
	 * @param progressBar
	 * @param defaultMissingImgResId
	 *            图片不存在时默认显示的图片资源ID
	 * @param iWidth
	 * @param iHeight
	 * @since JDK 1.6
	 */
	public static void loadWebImage(final Context context,
			final ImageView view, final Uri uri, final ProgressBar progressBar,
			final int defaultMissingImgResId, Integer iWidth, Integer iHeight) {
		// http
		if (!isHttpScheme(uri)) {
			Log.w(TAG, "unsupported uri");
			return;
		}

		String strUri = uri.toString();
		Log.d(TAG, "loadWebImage:" + strUri);

		// 用于直接生成圆形bitmap round(radius)
		int defaultWidth = view.getWidth();
		int defaultHeight = view.getHeight();

		if (defaultWidth <= 0 || defaultHeight <= 0) {
			view.measure(0, 0);
			defaultWidth = view.getMeasuredWidth();
			defaultHeight = view.getMeasuredHeight();
		}

		int radius = (defaultWidth < defaultHeight ? defaultWidth
				: defaultHeight) / 2;

		Log.i(TAG, "radius = " + radius);

		if (radius <= 60) {
			radius = 60; // default
			Log.w(TAG, "radius is <= 0");
		}

		// 使用AQuery异步加载
		AQuery aq = new AQuery(context);

		BitmapAjaxCallback cb = new BitmapAjaxCallback() {

			// @Override
			// public Bitmap transform(String url, byte[] data, AjaxStatus
			// status) {
			// Bitmap objBm = super.transform(url, data, status);
			// Bitmap objRoundBm = createRoundedCornerBitmap(objBm);
			// return objRoundBm;
			// }
			//
			// private Bitmap createRoundedCornerBitmap(Bitmap bitmap) {
			// int bmWidth = bitmap.getWidth();
			// int bmHeight = bitmap.getHeight();
			//
			// int pixels = (bmWidth < bmHeight ? bmWidth : bmHeight) / 2;
			//
			// if (pixels <= 0) {
			// Log.w(TAG, "createRoundedCornerBitmap pixels is <= 0");
			// }
			//
			// Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
			// bitmap.getHeight(), Config.ARGB_8888);
			// Canvas canvas = new Canvas(output);
			//
			// final int color = 0xff424242;
			// final Paint paint = new Paint();
			// final Rect rect = new Rect(0, 0, bitmap.getWidth(),
			// bitmap.getHeight());
			// final RectF rectF = new RectF(rect);
			// final float roundPx = pixels;
			//
			// paint.setAntiAlias(true);
			// canvas.drawARGB(0, 0, 0, 0);
			// paint.setColor(color);
			// canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
			//
			// paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			// canvas.drawBitmap(bitmap, rect, rect, paint);
			//
			// return output;
			// }

			@Override
			public void callback(String url, ImageView iv, Bitmap bm,
					AjaxStatus status) {
				if (200 == status.getCode()) {
					super.callback(url, iv, bm, status);
				} else {
					Log.w(TAG, "BitmapAjaxCallback  status code not 200");
				}
			}
		};

		// 直接生成圆形bitmap round(radius)
		cb.url(strUri).memCache(true).fileCache(true).fallback(0).animation(0)
				.round(radius);

		// 内存缓存，文件缓存,加载失败，默认图片
		if (progressBar == null) {
			aq.id(view).image(cb);
		} else {
			aq.id(view).progress(progressBar).image(cb);
		}

		if (iWidth != null && iWidth > 0) {
			aq.width(iWidth);
		}

		if (iHeight != null && iHeight > 0) {
			aq.height(iHeight);
		}
	}

	/**
	 * isHttpScheme. <br/>
	 * 日期: 2015-2-7 上午10:30:16 <br/>
	 * 
	 * @author wangenzi
	 * @param uri
	 * @return
	 * @since JDK 1.6
	 */
	public static boolean isHttpScheme(Uri uri) {
		if (uri == null) {
			return false;
		}
		if (CommonConstants.URI_SCHEME_HTTP.equals(uri.getScheme())) {
			Log.d(TAG, "isHttpSchema:" + "http");
			return true;
		}

		if (CommonConstants.URI_SCHEME_HTTPS.equals(uri.getScheme())) {
			Log.d(TAG, "isHttpSchema:" + "https");
			return true;
		}

		return false;
	}

}
