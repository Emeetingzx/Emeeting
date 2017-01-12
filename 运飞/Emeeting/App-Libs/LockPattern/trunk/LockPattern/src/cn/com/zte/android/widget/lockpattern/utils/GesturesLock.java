package cn.com.zte.android.widget.lockpattern.utils;

import cn.com.zte.android.widget.lockpattern.listener.ActionListener;
import cn.com.zte.android.widget.lockpattern.pattern.CreateGesturePasswordActivity;
import cn.com.zte.android.widget.lockpattern.pattern.UnlockGesturePasswordActivity;
import cn.com.zte.android.widget.lockpattern.view.LockPatternUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

	private static GesturesLock gl;
	static {
		gl = new GesturesLock();
	}

	public static ActionListener listener;
	public static ActionListener deleteListener;
	public static ActionListener forgetListener;
	public static ActionListener clistener;
	public static Drawable titlePicture_unload;
	public static String tv_title;
	public static boolean validate = false; 
	public static boolean isShake = false; 
	public static boolean hasLock = false;
	
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
	
	public void setTitlePicture(Drawable d){
		this.titlePicture_unload = d;
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
	
	public void create(Context context , ActionListener clistener){
		SharedPreferences sp = context.getSharedPreferences("sp", Activity.MODE_PRIVATE);
		boolean isFirstCreate = sp.getBoolean("isFirstCreate", true);
		if(LockPatternUtils.getInstance(context).savedPatternExists()){
			Toast.makeText(context, "已开启手势密码，请不要重复设置", 0).show();
			return;
		}
		this.clistener = clistener;
		Intent intent = new Intent();
		intent.setClass(context, CreateGesturePasswordActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
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
	public void create(Context context , ActionListener clistener , Drawable d , String title){
		this.titlePicture_unload = d;
		this.tv_title = title;
		create(context, clistener);
	}
	
	/** 手势密码是否已创建*/
	public boolean needCreate(Context context){
		if(LockPatternUtils.getInstance(context).savedPatternExists()){
			return true;
		}
		return false;
	}
	
	private void deleteInner(final Context context,final ActionListener listener){
		LockPatternUtils.getInstance(context).setSavedPatternExists(false);
		LockPatternUtils.getInstance(context).saveLockPattern(null);
		listener.callback();
	}
	
	/**
	 * 删除手势解锁. <br/>
	 * 日期: 2015-2-11 上午11:34:45 <br/>
	 * @author Administrator
	 * @param context
	 * @param listener
	 * @since JDK 1.6
	 */
	public void delete(Context context , ActionListener listener){
		if(!LockPatternUtils.getInstance(context).savedPatternExists()){
			Toast.makeText(context, "没有设置手势密码，请先设置", 0).show();
			return;
		}else{
			deleteInner(context,listener);
		}
	}
	
	public boolean validate(Context context,ActionListener listener){
		this.validate = false;
		if(LockPatternUtils.getInstance(context).savedPatternExists()){
			this.listener = listener;
			this.validate = true;
			Intent intent = new Intent();
			intent.setClass(context, UnlockGesturePasswordActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
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
	public boolean validate(Context context,ActionListener listener, Drawable d , String title){
		this.titlePicture_unload = d;
		this.tv_title = title;
		return validate(context, listener);
	}
	
	/**
	 * 重置手势. <br/>
	 * 日期: 2015-2-11 下午12:50:32 <br/>
	 * @author Administrator
	 * @param context
	 * @param clistener
	 * @since JDK 1.6
	 */
	public void reset(final Context context,final ActionListener clistener){
		if(!LockPatternUtils.getInstance(context).savedPatternExists()){
			Toast.makeText(context, "未开启手势密码，请先开启", 0).show();
		}else{
			deleteInner(context,new ActionListener() {
				@Override
				public void callback() {
					/* 删除手势密码成功后，初始化手势密码*/
					hasLock = true;
					GesturesLock.getInstance().create(context, clistener);
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
}