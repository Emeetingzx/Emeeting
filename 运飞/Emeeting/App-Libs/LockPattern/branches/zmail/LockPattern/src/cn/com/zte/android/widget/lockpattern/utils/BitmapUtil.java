package cn.com.zte.android.widget.lockpattern.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/** 图片操作工具类 */
public class BitmapUtil {

	/**
	 * 缩放图片
	 * 
	 * @param bm
	 *            {@value 图片资源对象}
	 * @param newWidth
	 *            {@value 图片缩放后的宽度}
	 * @param newHeight
	 *            {@value 图片缩放后的高度}
	 * */
	public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
		// 获得图片的宽高
		int width = bm.getWidth();
		int height = bm.getHeight();
		// 计算缩放比例
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);

		// 得到新的图片
		Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
				true);
		return newbm;
	}

	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	public static File getFileFromBytes(byte[] b, String outputFile) {
		BufferedOutputStream stream = null;
		File file = null;
		try {
			file = new File(outputFile);
			FileOutputStream fstream = new FileOutputStream(file);
			stream = new BufferedOutputStream(fstream);
			stream.write(b);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return file;
	}

	public static int getResIDByResName(Activity activity, String iconName) {
		Resources resources = activity.getResources();
		int indentify = resources.getIdentifier(activity.getPackageName()
				+ ":drawable/" + iconName, null, null);
		return indentify;
	}

	public static Bitmap deCodeFromByte(byte[] data) {
		Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
		return bitmap;
	}

	public static Bitmap scaleBitmap(String filePath, int w, int h) {
		Options o = new Options();
		o.inJustDecodeBounds = true;
		Bitmap tmp = BitmapFactory.decodeFile(filePath, o);
		o.inJustDecodeBounds = false;
		int width = (int) Math.ceil(o.outWidth / (float) w);
		int height = (int) Math.ceil(o.outHeight / (float) h);
		if (width > 1 && height > 1) {
			if (height > width) {
				o.inSampleSize = height;
			} else {
				o.inSampleSize = width;
			}
		}
		tmp = BitmapFactory.decodeFile(filePath, o);

		return tmp;
	}

	public static boolean saveImg(Bitmap bitmap, File file, int i) {
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (i == 1) {
				if (bitmap.compress(Bitmap.CompressFormat.JPEG, 70, out)) {
					out.flush();
					out.close();
				}
			} else if (i == 2) {
				if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
					out.flush();
					out.close();
				}
			}

			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static Bitmap getBmp(File file) throws FileNotFoundException {

		if (file == null)
			return null;
		Options o = new Options();
		o.inJustDecodeBounds = true;
		Bitmap tmp = BitmapFactory.decodeFile(file.getAbsolutePath(), o);
		o.inJustDecodeBounds = false;

		int rate = (int) (o.outHeight / (float) o.outWidth);
		if (rate <= 0) {
			rate = 1;
		}
		o.inSampleSize = rate;
		o.inPurgeable = true;
		o.inInputShareable = true;

		tmp = BitmapFactory.decodeFile(file.getAbsolutePath(), o);

		return tmp;
	}

	public static Bitmap rotate(Bitmap bitmap, int degree) {
		Matrix matrix = new Matrix();
		matrix.postRotate(degree);
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	}

	public static Bitmap createReflectedBitmap(Bitmap srcBitmap) {
		if (null == srcBitmap) {
			return null;
		}

		// The gap between the reflection bitmap and original bitmap.
		final int REFLECTION_GAP = 4;

		int srcWidth = srcBitmap.getWidth();
		int srcHeight = srcBitmap.getHeight();
		int reflectionWidth = srcBitmap.getWidth();
		int reflectionHeight = srcBitmap.getHeight() / 2;

		if (0 == srcWidth || srcHeight == 0) {
			return null;
		}

		// The matrix
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		try {
			// The reflection bitmap, width is same with original's, height is
			// half of original's.
			Bitmap reflectionBitmap = Bitmap.createBitmap(srcBitmap, 0,
					srcHeight / 2, srcWidth, srcHeight / 2, matrix, false);

			if (null == reflectionBitmap) {
				return null;
			}

			// Create the bitmap which contains original and reflection bitmap.
			Bitmap bitmapWithReflection = Bitmap.createBitmap(reflectionWidth,
					srcHeight + reflectionHeight + REFLECTION_GAP,
					Config.ARGB_8888);

			if (null == bitmapWithReflection) {
				return null;
			}

			// Prepare the canvas to draw stuff.
			Canvas canvas = new Canvas(bitmapWithReflection);

			// Draw the original bitmap.
			canvas.drawBitmap(srcBitmap, 0, 0, null);

			// Draw the reflection bitmap.
			canvas.drawBitmap(reflectionBitmap, 0, srcHeight + REFLECTION_GAP,
					null);

			// srcBitmap.recycle();
			reflectionBitmap.recycle();

			Paint paint = new Paint();
			paint.setAntiAlias(true);
			LinearGradient shader = new LinearGradient(0, srcHeight, 0,
					bitmapWithReflection.getHeight() + REFLECTION_GAP,
					0x70FFFFFF, 0x00FFFFFF, TileMode.MIRROR);
			paint.setShader(shader);
			paint.setXfermode(new PorterDuffXfermode(
					android.graphics.PorterDuff.Mode.DST_IN));

			// Draw the linear shader.
			canvas.drawRect(0, srcHeight, srcWidth,
					bitmapWithReflection.getHeight() + REFLECTION_GAP, paint);

			return bitmapWithReflection;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 得到设备屏幕的宽度
	 */
	public static int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 得到设备屏幕的高度
	 */
	public static int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	/**
	 * 得到设备的密度
	 */
	public static float getScreenDensity(Context context) {
		return context.getResources().getDisplayMetrics().density;
	}

	/**
	 * 把密度转换为像素
	 */

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 根据指定的图像路径和大小来获取缩略图 此方法有两点好处： 1.
	 * 使用较小的内存空间，第一次获取的bitmap实际上为null，只是为了读取宽度和高度，
	 * 第二次读取的bitmap是根据比例压缩过的图像，第三次读取的bitmap是所要的缩略图。 2.
	 * 缩略图对于原图像来讲没有拉伸，这里使用了2.2版本的新工具ThumbnailUtils，使 用这个工具生成的图像不会被拉伸。
	 * 
	 * @param imagePath
	 *            图像的路径
	 * @param width
	 *            指定输出图像的宽度
	 * @param height
	 *            指定输出图像的高度
	 * @return 生成的缩略图
	 */
	public static Bitmap getImageThumbnail(String imagePath, int width,
			int height) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 获取这个图片的宽和高，注意此处的bitmap为null
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		options.inJustDecodeBounds = false; // 设为 false
		// 计算缩放比
		int h = options.outHeight;
		int w = options.outWidth;
		int beWidth = w / width;
		int beHeight = h / height;
		int be = 1;
		if (beWidth < beHeight) {
			be = beWidth;
		} else {
			be = beHeight;
		}
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = be;
		// 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		// 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

	/**
	 * 获取网络图片资源
	 * 
	 * @param url
	 * @return
	 */
	public static void getHttpBitmap(Handler handler, String imageUri) {
		final Handler h = handler;
		final String ImageUri = imageUri;
		// 显示网络上的图片
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Bitmap bitmap = null;
				try {
					URL myFileUrl = new URL(ImageUri);
					HttpURLConnection conn = (HttpURLConnection) myFileUrl
							.openConnection();
					conn.setDoInput(true);
					conn.connect();
					InputStream is = conn.getInputStream();
					bitmap = BitmapFactory.decodeStream(is);
					is.close();
					Log.i("imageUrl", "image download finished." + ImageUri);
				} catch (IOException e) {
					bitmap = null;
					Log.i("imageUrlError", e.toString());
				}
				Message msg = new Message();
				msg.what = 0;
				msg.obj = bitmap;
				h.sendMessage(msg);
			}
		}).start();
	}

	/**
	 * 获取Uri的图片
	 * 
	 * @param act
	 * @param uri
	 * @param size
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Bitmap getThumbnail(Activity act, Uri uri, int size)
			throws FileNotFoundException, IOException {
		InputStream input = act.getContentResolver().openInputStream(uri);
		BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
		onlyBoundsOptions.inJustDecodeBounds = true;
		onlyBoundsOptions.inDither = true;// optional
		onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// optional
		BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
		input.close();
		if ((onlyBoundsOptions.outWidth == -1)
				|| (onlyBoundsOptions.outHeight == -1))
			return null;
		int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight
				: onlyBoundsOptions.outWidth;
		double ratio = (originalSize > size) ? (originalSize / size) : 1.0;
		BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
		bitmapOptions.inDither = true;// optional
		bitmapOptions.inJustDecodeBounds = true;
		bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// optional
		input = act.getContentResolver().openInputStream(uri);
		Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
		input.close();
		return bitmap;
	}

	private static int getPowerOfTwoForSampleRatio(double ratio) {
		int k = Integer.highestOneBit((int) Math.floor(ratio));
		if (k == 0)
			return 1;
		else
			return k;
	}

	/** Drawable转Bitmap */
	public static Bitmap drawableToBitmap(Drawable drawable) {

		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		// canvas.setBitmap(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	/** Bitmap转Drawable */
	public static Drawable convertBitmap2Drawable(Bitmap bitmap) {
		BitmapDrawable bd = new BitmapDrawable(bitmap);
		// 因为BtimapDrawable是Drawable的子类，最终直接使用bd对象即可。
		return bd;
	}
}
