/**
 * 
 */
package cn.com.zte.emeeting.app.util;

import android.util.Log;

/**
 * 该类用于
 * @author wf
 */
public class LogTools {
	
	/** 是否打印log信息 */
	private static final boolean isAllowLog=true;
	
	/** 打印log信息*/
	public static void i(String tag,String msg){
		if(isAllowLog)
		{
			Log.i(tag, msg);
		}
	}
	
	/** 打印log信息*/
	public static void d(String tag,String msg){
		if(isAllowLog)
		{
			Log.d(tag, msg);
		}
	}
	
	/** 打印log信息*/
	public static void e(String tag,String msg){
		if(isAllowLog)
		{
			Log.e(tag, msg);
		}
	}}
