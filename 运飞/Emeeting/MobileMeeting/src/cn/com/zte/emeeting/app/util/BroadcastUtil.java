package cn.com.zte.emeeting.app.util;

import android.content.Context;
import android.content.Intent;

/**
 * 广播发送工具类
 * @author LangK
 *
 */
public class BroadcastUtil {
	/**
	 * 通知增值服务管理员界面未接订单列表刷新
	 */
	public static final String AVServiceUndoNotifi = "AVServiceUndoNotifi";
	
	/**
	 * 通知增值服务管理员界面已接订单列表刷新
	 */
	public static final String AVServiceReceiveNotifi = "AVServiceReceiveNotifi";
	/**
	 * 通知增值服务我的服务界面列表刷新
	 */
	public static final String AVServiceMySelfNotifi = "AVServiceMySelfNotifi";
	/**
	 * 通知增值服务管理员界面已完成订单列表刷新
	 */
	public static final String AVServiceDoneNotifi = "AVServiceDoneNotifi";

	/**
	 * 发送广播
	 * @param context
	 * @param notification
	 */
	public static void sendBroadcase(Context context,String notification){
		Intent intent = new Intent(notification);
		context.sendBroadcast(intent);
	}
	
}
