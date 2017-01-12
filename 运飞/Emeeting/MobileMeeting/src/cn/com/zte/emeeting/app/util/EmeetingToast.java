/**
 * 
 */
package cn.com.zte.emeeting.app.util;

import cn.com.zte.android.widget.dialog.DialogManager;
import cn.com.zte.emeeting.app.base.MyApplication;
import cn.com.zte.emeeting.app.dialog.DlgToast;
import cn.com.zte.emeeting.app.dialog.DlgToastBig;
import android.content.Context;
import android.widget.Toast;

/**
 * 该类用于:toast提示
 * 
 */
public class EmeetingToast {
	private final static int time = 1500;
	public static void show(Context context,String msg,int duration)
	{
			try {
				new DlgToast(context, msg).showToast(duration);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public static void show(Context context,String msg)
	{
		show(context,msg,time);
	}
	
	public static void show(Context context,int msgid)
	{
		String msg = context.getResources().getString(msgid);
		show(context,msg,time);
	}
	
	/** 提示http异常*/
	public static void showHttp(Context context,String msg)
	{
		DialogManager.showToast(context, msg);
	}
}
