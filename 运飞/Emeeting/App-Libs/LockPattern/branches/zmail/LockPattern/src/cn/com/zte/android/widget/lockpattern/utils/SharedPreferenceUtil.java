package cn.com.zte.android.widget.lockpattern.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences.Editor;

/**
 * SharedProference Util 类
 * @author LangK
 *
 */
public class SharedPreferenceUtil {
	
	private String ShareName;
	
	private Context mContext;
	
	/**
	 * 本地登录用户信息保存
	 */
	public static final String  USERINFO = "UserInfo";
	/**
	 * 用户信息文件里面用户ID
	 */
	public static final String UID = "uid";
	

	private static final String PROJECTNAME_STRING = "ZMail_";
	
	/**
	 * 
	 * @param shareName ShareProferenceName
	 * @param mContext	Context object
	 */
	public SharedPreferenceUtil(String shareName, Context mContext) {
		super();
		this.ShareName = shareName;
		this.mContext = mContext;
	}
	
	/**
	 * getValue from name
	 * @param name 
	 * @return	value
	 */
	public String getString(String name){
		name = PROJECTNAME_STRING+name;
		if (mContext==null) {
			return "";
		}
		return mContext.getSharedPreferences(ShareName, Activity.MODE_PRIVATE).getString(name, "");
	}
	
	/**
	 * setName and Value
	 * @param name
	 * @param value
	 */
	public void setNameAndValue(String name,String value){
		name = PROJECTNAME_STRING+name;
		Editor editor = mContext.getSharedPreferences(ShareName, Activity.MODE_PRIVATE).edit();
		editor.putString(name, value);
		editor.commit();
	}


}
