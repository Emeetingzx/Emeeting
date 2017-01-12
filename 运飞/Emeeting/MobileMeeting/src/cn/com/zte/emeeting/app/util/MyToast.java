/**
 * 
 */
package cn.com.zte.emeeting.app.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 该类用于:toast提示,防止重复提示
 * 
 */
public class MyToast {

	private static final int duration = Toast.LENGTH_SHORT;
	
	private static Toast mToast = null;

	public static void show(Context context, String text, int duration) {
		if (mToast == null) {
			mToast = Toast.makeText(context, text, duration);
		} else {
			mToast.setText(text);
			mToast.setDuration(duration);
		}
		mToast.show();
	}
	
	public static void show(Context context, int resId, int duration) {
		if (mToast == null) {
			mToast = Toast.makeText(context, resId, duration);
		} else {
			mToast.setText(resId);
			mToast.setDuration(duration);
		}
		mToast.show();
	}
	
	public static void show(Context context, String text)
	{
		show(context, text, duration);
	}
	
	public static void show(Context context, int text)
	{
		show(context, text, duration);
	}
}
