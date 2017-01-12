package cn.com.zte.android.widget.lockpattern.utils;

import cn.com.zte.android.widget.lockpattern.listener.ActionListener;
import cn.com.zte.android.widget.lockpattern.pattern.CreateGesturePasswordActivity;
import cn.com.zte.android.widget.lockpattern.pattern.UnlockGesturePasswordActivity;
import cn.com.zte.android.widget.lockpattern.view.LockPatternUtils;
import cn.com.zte.android.widget.pattern.R;
import android.app.Activity;
import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.AvoidXfermode.Mode;
import android.graphics.drawable.Drawable;
import android.widget.Toast;



/**
 * 手势解锁. <br/>
 * 日期: 2015-2-11 下午12:52:51 <br/>
 * @author Administrator
 * @version 1.0
 * @since JDK 1.6
 * @history 2015-2-11 Administrator 新建
 */
public class GesturesLock {

	public static final String LOCK_FILE_NAME = "lock_file_name";
	private static GesturesLock gl;
	static {
		gl = new GesturesLock();
	}

	public static ActionListener listener;
	public static ActionListener deleteListener;
	public static ActionListener forgetListener;
	public static ActionListener otherListener;
	public static ActionListener clistener;
	public static UserEntity userEntity;
	public static String tv_title;
	public static boolean validate = false; 
	public static boolean isShake = false; 
	public static boolean hasLock = false;

	/**
	 * 是否隐藏手势密码
	 */
	private boolean isHidePath = false;
	/**
	 * 是否隐藏顶部导航栏
	 */
	public static boolean isHideTop = true;

	/**
	 * 是否隐藏切换其他帐号
	 */
	public static boolean isHideOther = false;
	
	/**
	 * 是否可以返回
	 */
	public static boolean isBack = true;
	
	private GesturesLock() {
		
	}
	
	
	/**
	 * 获取GesturesLock实例. <br/>
	 * 日期: 2015-2-11 下午12:52:09 <br/>
	 * @author Administrator
	 * @return
	 * @since JDK 1.6
	 */
	public static GesturesLock getInstance(){
		return gl;
	}
	
	public void setForgetListener(ActionListener forgetListener){
		this.forgetListener = forgetListener;
	}
	
	public void setOtherListener(ActionListener forgetListener){
		this.otherListener = forgetListener;
	}
	
	public void setTitleName(String s){
		this.tv_title = s;
	}
	
	public void setIsShake(boolean b){
		this.isShake = b;
	}
	
	/**
	 * 设置手势输入错误尝试的次数. <br/>
	 * 日期: 2015-2-11 下午12:51:34 <br/>
	 * @author Administrator
	 * @param i
	 * @since JDK 1.6
	 */
	public void setWrongCount(int i){
		LockPatternUtils.FAILED_ATTEMPTS_BEFORE_TIMEOUT = i;
	}
	
	/**
	 * 设置至少手势图案需要连接的点数. <br/>
	 * 日期: 2015-2-11 下午12:48:22 <br/>
	 * @author Administrator
	 * @param i
	 * @since JDK 1.6
	 */
	public void setMinLockSize(int i){
		LockPatternUtils.MIN_LOCK_PATTERN_SIZE = i;
	}
	
	/**
	 * 创建手势密码
	 * @param context
	 * @param clistener
	 */
	public void create(Context context , String lockFileName, ActionListener clistener){
		SharedPreferences sp = context.getSharedPreferences("sp", Activity.MODE_PRIVATE);
		boolean isFirstCreate = sp.getBoolean("isFirstCreate", true);
		if(new LockPatternUtils(context,lockFileName).savedPatternExists()){
			Toast.makeText(context, R.string.turned_gestures_password, 0).show();
			return;
		}
		this.clistener = clistener;
		Intent intent = new Intent();
		intent.setClass(context, CreateGesturePasswordActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		intent.putExtra(LOCK_FILE_NAME, lockFileName);
		context.startActivity(intent);
		return;
	}
	
	/**
	 * 创建手势密码. <br/>
	 * 日期: 2015-2-11 下午12:49:28 <br/>
	 * @author Administrator
	 * @param context
	 * @param clistener
	 * @since JDK 1.6
	 */
	public void create(Context context , String lockFileName, ActionListener clistener ,UserEntity userEntity , String title){
		this.userEntity = userEntity;
		this.tv_title = title;
		create(context, lockFileName,clistener);
	}
	
	/** 手势密码是否已创建*/
	public boolean needCreate(Context context,String lockFileName){
		if(new LockPatternUtils(context,lockFileName).savedPatternExists()){
			return true;
		}
		return false;
	}
	
	private void deleteInner(final Context context,String lockFileName, final ActionListener listener){
		new LockPatternUtils(context,lockFileName).setSavedPatternExists(false);
		new LockPatternUtils(context,lockFileName).saveLockPattern(null);
		listener.callback(ActionListener.SUCCESS);
	}
	
	/**
	 * 删除手势解锁. <br/>
	 * 日期: 2015-2-11 上午11:34:45 <br/>
	 * @author Administrator
	 * @param context
	 * @param listener
	 * @param lockFileName 保存的手势文件名
	 * @since JDK 1.6
	 */
	public void delete(Context context , String lockFileName, ActionListener listener){
		if(!new LockPatternUtils(context,lockFileName).savedPatternExists()){
			Toast.makeText(context, R.string.gesture_not_set_a_password, 0).show();
			return;
		}else{
			deleteInner(context,lockFileName,listener);
		}
	}
	
	public boolean validate(Context context,String lockFileName, ActionListener listener){
		this.validate = false;
		if(new LockPatternUtils(context,lockFileName).savedPatternExists()){
			this.listener = listener;
			this.validate = true;
			Intent intent = new Intent();
			intent.setClass(context, UnlockGesturePasswordActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			intent.putExtra(LOCK_FILE_NAME, lockFileName);
			context.startActivity(intent);
			return this.validate;
		}
		return this.validate;
	}
	
	/**
	 * 校验密码. <br/>
	 * 日期: 2015-2-11 下午12:50:11 <br/>
	 * @author Administrator
	 * @param context
	 * @param listener
	 * @return
	 * @since JDK 1.6
	 */
	public boolean validate(Context context,String lockFileName,ActionListener listener, UserEntity userEntity , String title){
		this.userEntity = userEntity;
		this.tv_title = title;
		return validate(context, lockFileName,listener);
	}
	
	/**
	 * 重置手势. <br/>
	 * 日期: 2015-2-11 下午12:50:32 <br/>
	 * @author Administrator
	 * @param context
	 * @param clistener
	 * @since JDK 1.6
	 */
	public void reset(final Context context,final String lockFileName, final ActionListener clistener){
		if(!new LockPatternUtils(context,lockFileName).savedPatternExists()){
			Toast.makeText(context, R.string.unopened_gesture_password, 0).show();
		}else{
			deleteInner(context,lockFileName,new ActionListener() {
				@Override
				public void callback(int result) {
					/* 删除手势密码成功后，初始化手势密码*/
					hasLock = true;
					GesturesLock.getInstance().create(context, lockFileName,clistener);
				}
			});
			
//			validate(context, new ActionListener() {
//				@Override
//				public void callback() {
//					GesturesLock.getInstance().create(context, clistener);
//				}
//			});
		}
	}

	/**
	 * 获取是否隐藏手势密码
	 */
	public boolean isHidePath(Context context) {
		String nameString = new SharedPreferenceUtil(SharedPreferenceUtil.USERINFO, context).getString(SharedPreferenceUtil.UID);
		return new UserInfoUtil(context,nameString).getPreferenceUtil().getString("isHidePath").equals("Y");
	}

	/**
	 * 设置是否隐藏手势密码
	 */
	public void setHidePath(Context context,boolean isHidePath) {
		String nameString = new SharedPreferenceUtil(SharedPreferenceUtil.USERINFO, context).getString(SharedPreferenceUtil.UID);
		new UserInfoUtil(context,nameString).getPreferenceUtil().setNameAndValue("isHidePath",isHidePath?"Y":"N");
	}
}