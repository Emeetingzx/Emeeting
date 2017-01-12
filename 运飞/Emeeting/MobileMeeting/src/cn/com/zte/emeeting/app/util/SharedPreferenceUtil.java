package cn.com.zte.emeeting.app.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * SharedProference Util 类
 *
 */
public class SharedPreferenceUtil {
	
	private String ShareName;
	
	private Context mContext;
	

	private static final String PROJECTNAME_STRING = "emeeting_";
	
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
	 * getValue from name
	 * @param name 
	 * @return	value
	 */
	public Long getLong(String name){
		name = PROJECTNAME_STRING+name;
		if (mContext==null) {
			return 0L;
		}
		return mContext.getSharedPreferences(ShareName, Activity.MODE_PRIVATE).getLong(name, 0L);
	}
	
	/**
	 * getValue from name
	 * @param name 
	 * @return	value
	 */
	public int getInt(String name,int defaultValue){
		name = PROJECTNAME_STRING+name;
		if (mContext==null) {
			return defaultValue;
		}
		return mContext.getSharedPreferences(ShareName, Activity.MODE_PRIVATE).getInt(name, defaultValue);
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
	
	/**
	 * setName and Value
	 * @param name
	 * @param value
	 */
	public void setNameAndValueForLong(String name,Long value){
		name = PROJECTNAME_STRING+name;
		Editor editor = mContext.getSharedPreferences(ShareName, Activity.MODE_PRIVATE).edit();
		editor.putLong(name, value);
		editor.commit();
	}

	
	/**
	 * setName and Value
	 * @param name
	 * @param value
	 */
	public void setNameAndValueForInt(String name,int value){
		name = PROJECTNAME_STRING+name;
		Editor editor = mContext.getSharedPreferences(ShareName, Activity.MODE_PRIVATE).edit();
		editor.putInt(name, value);
		editor.commit();
	}

}
