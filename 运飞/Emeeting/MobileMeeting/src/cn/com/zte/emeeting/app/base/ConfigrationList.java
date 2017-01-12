package cn.com.zte.emeeting.app.base;

import cn.com.zte.emeeting.app.util.SharedPreferenceUtil;
import android.content.Context;

/***
 * APP配置清单
 * 
 * @author Administrator
 * 
 */
public class ConfigrationList {
	/** 应用配置文件 Y开启 N关闭消息提醒 */
	public static final String ISOPENMESSAGEREMIND = "isOpenMessageRemind";
	/** 应用配置文件 Y开启 N关闭声音 */
	public static final String ISOPENVOICE = "isOpenVoice";
	/** 应用配置文件 Y开启 N关闭震动 */
	public static final String ISOPENVIBRATE = "isOpenVibrate";
	/**
	 * 应用配置文件
	 */
	public static final String APPCONFIG = "appconfig";
	/**
	 * 应用配置文件 Y 有更新 N 无更新
	 */
	public static final String ISUPDATE = "isupdate";
	
	/**
	 * 用户信息配置表
	 */
	public static final String USERINFO = "userinfo";
	
	/**
	 * 增值服务信息配置表
	 */
	public static final String PLACEINFO = "placeinfo";
	/**
	 * 上次选中的服务地区ID
	 */
	public static final String PLACEID = "placeid";
	/**
	 * 当前用户是否为管理员
	 */
	public static final String ISADMIN = "isAdmin";

	/**
	 * 当前系统时间与服务器时间差
	 */
	public static final String TimeDifference = "timeDifference";
	
	/**
	 * 是否打开消息提醒
	 * 
	 * @param mContext
	 * @return
	 */

	public static boolean isOpenMessageRemind(Context mContext) {

		String resule = new SharedPreferenceUtil(APPCONFIG, mContext)
				.getString(ISOPENMESSAGEREMIND);

		if (resule != null && resule.equals("N")) {
			return false;
		} else {

			return true;
		}
	}

	/**
	 * 设置消息提醒
	 * 
	 * @param mContext
	 * @param isOpenMessageRemind
	 */

	public static void setOpenMessageRemind(Context mContext,
			boolean isOpenMessageRemind) {

		if (mContext == null) {
			return;
		}
		if (isOpenMessageRemind) {
			new SharedPreferenceUtil(APPCONFIG, mContext).setNameAndValue(
					ISOPENMESSAGEREMIND, "Y");
		} else {
			new SharedPreferenceUtil(APPCONFIG, mContext).setNameAndValue(
					ISOPENMESSAGEREMIND, "N");
		}
	}

	/**
	 * 是否打开声音
	 * 
	 * @param mContext
	 * @return
	 */
	public static boolean isOpenVoice(Context mContext) {

		String result = new SharedPreferenceUtil(APPCONFIG, mContext)
				.getString(ISOPENVOICE);
		if (result != null && result.equals("N")) {
			return false;
		} else {
			return true;
		}

	}

	/***
	 * 设置声音
	 * 
	 * @param mContext
	 * @param isOpenVoice
	 */
	public static void setVoice(Context mContext, boolean isOpenVoice) {
		if (mContext == null) {
			return;
		}
		if (isOpenVoice) {

			new SharedPreferenceUtil(APPCONFIG, mContext).setNameAndValue(
					ISOPENVOICE, "Y");

		} else {
			new SharedPreferenceUtil(APPCONFIG, mContext).setNameAndValue(
					ISOPENVOICE, "N");
		}
	}

	/**
	 * 是否打开震动
	 * 
	 * @param mContext
	 * @return
	 */
	public static boolean isOpenVibrate(Context mContext) {

		String result = new SharedPreferenceUtil(APPCONFIG, mContext)
				.getString(ISOPENVIBRATE);
		if (result != null && result.equals("N")) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 设置震动
	 * 
	 * @param mContext
	 * @param isOpenVibrate
	 */
	public static void setVibrate(Context mContext, boolean isOpenVibrate) {

		if (mContext == null) {

			return;
		}
		if (isOpenVibrate) {

			new SharedPreferenceUtil(APPCONFIG, mContext).setNameAndValue(
					ISOPENVIBRATE, "Y");
		} else {
			new SharedPreferenceUtil("", mContext).setNameAndValue(
					ISOPENVIBRATE, "N");
		}
	}

	/*
	 * 广播action配置
	 */

	/**
	 * 日期选择完成后发送广播
	 */
	public static final String DateChoosedReceive = "DateChooseReceive";

}
