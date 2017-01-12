package cn.com.zte.emeeting.app.util;

import cn.com.zte.emeeting.app.base.MyApplication;
import cn.com.zte.mobileemeeting.R;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络工具类
 * 
 * @author liu.huanbo
 * 
 */
public class NetWorkUtil {

	/** 网络是否连接 */
	public static boolean netIsConnect(Context context) {
		if (context != null) {

			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			NetworkInfo networkInfo = connectivityManager
					.getActiveNetworkInfo();

			if (networkInfo != null) {

				return networkInfo.isAvailable();
			}

		}
		return false;

	}

	/** 当前手机无网络提示处理 */
	public static boolean noNetworkPromptProcessing(Context mContext) {
		if (mContext == null) {
			mContext = MyApplication.GetApp().getApplicationContext();
		}
		boolean isR = netIsConnect(mContext);
//		if (!isR) {
//			MyToast.show(mContext,
//					mContext.getString(R.string.no_network_prompt_str));
//		}
		return isR;
	}

}
