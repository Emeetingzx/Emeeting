package cn.com.zte.android.widget.lockpattern.utils;

import android.content.Context;
import android.util.Log;
/**
 * 用户信息工具类
 * @author LangK
 *
 */
public class UserInfoUtil {

	/**
	 * 单例
	 */
	private static UserInfoUtil infoUtil;
	/**
	 * 用户工号
	 */
	private String userID;
	/**
	 * 上下文
	 */
	private Context mContext;
	
	public UserInfoUtil(Context context,String id){
		this.mContext = context;
		this.userID = id;
	}
	
//	public static UserInfoUtil getUserInfoUtil(Context context,String userid){
//		if (infoUtil==null) {
//			infoUtil = new UserInfoUtil(context,userid);
//		}
//		return infoUtil;
//	}
	/**
	 * 获取sharedpreference工具类
	 * @return
	 */
	public SharedPreferenceUtil getPreferenceUtil(){
		Log.i("WelComeActivity", "当前用户工号20："+userID);
		SharedPreferenceUtil curutil = new SharedPreferenceUtil(SharedPreferenceUtil.USERINFO, mContext);
		curutil.setNameAndValue(SharedPreferenceUtil.UID,userID);
		SharedPreferenceUtil util = new SharedPreferenceUtil(userID+"_"+SharedPreferenceUtil.USERINFO, mContext);
		return util;
	}
	
}
