/******************************************************************************
 * Copyright (C) 2014 ZTE Co.,Ltd
 * All Rights Reserved.
 * 本软件为中兴通讯股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package cn.zte.com.office.reader;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import cn.com.zte.office.util.Define;
import cn.com.zte.office.util.SettingPreference;
import cn.com.zte.office.util.Util;

/**
 * OfficeUtil. <br/>
 * 日期: 2015-6-5 下午4:17:31 <br/>
 * 
 * @author wangenzi
 * @version 1.0
 * @since JDK 1.6
 * @history 2015-6-5 wangenzi 新建
 */
public class OfficeUtil {

	/** Tag */
	private static final String TAG = OfficeUtil.class.getSimpleName();

	// 设置参数常量信息
	private final static String[] SETTING = { Define.AT_SAVE, Define.AT_SAVEAS,
			Define.AT_COPY, Define.AT_CUT, Define.AT_PASTE, Define.AT_SHARE,
			Define.AT_PRINT, Define.AT_SPELLCHECK, Define.AT_MULTIDOCCHANGE,
			Define.AT_QUICK_CLOSE_REVISEMODE, Define.AT_EDIT_REVISION,
			Define.AT_CURSOR_MODEL };

	public static void initOfficeParams(Context ctx){
		SettingPreference settingPreference = new SettingPreference(ctx);
		// settingPreference.setdrmWpsJsondata(WPS_JSON_DATA);
		// settingPreference.setdrmWpsJsondata(WPS_PASS);
		settingPreference.setSettingParam(Define.KEY, ctx.getPackageName());
		for (int i = 0, size = SETTING.length; i < size; i++) {
			settingPreference.setSettingParam(SETTING[i], false);
		}
	}
	/**
	 * 使用. <br/>
	 * 日期: 2015-6-5 下午4:22:14 <br/>
	 * 
	 * @author wangenzi
	 * @param ctx
	 * @param docPath
	 * @param waterMask
	 * @return 是否已安装WPS PRO 版本
	 * @since JDK 1.6
	 */
	public static boolean openFileWithWps(Context ctx, String docPath,
			WaterMask waterMask) {
		boolean wpsProFlag = isWPSInstalled(ctx);
		if (!wpsProFlag) {
			Log.d(TAG, "Please install WPS PRO");
			return false;
		}

		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString(Define.OPEN_MODE, Define.READ_ONLY);
		bundle.putBoolean(Define.SEND_SAVE_BROAD, true);
		bundle.putBoolean(Define.SEND_CLOSE_BROAD, true);
		bundle.putBoolean(Define.CLEAR_BUFFER, true);
		bundle.putBoolean(Define.CLEAR_TRACE, true);
		bundle.putBoolean(Define.ENTER_REVISE_MODE, false);
		String packageName = ctx.getPackageName();
		bundle.putString(Define.THIRD_PACKAGE, packageName);

		if (waterMask != null) {
			bundle.putString("WaterMaskText", waterMask.getWaterMaskText()); // 设置水印字符串内容
			bundle.putInt("WaterMaskColor", waterMask.argb()); // 设置水印透明度值以及颜色
		}

		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);

		if (checkPackage(ctx, Define.PACKAGENAME_KING_PRO)) {
			intent.setClassName(Define.PACKAGENAME_KING_PRO, Define.CLASSNAME);
		} else {
			return false;
		}

		File file = new File(docPath);

		Uri uri = Uri.fromFile(file);
		intent.setData(uri);
		intent.putExtras(bundle);
		ctx.startActivity(intent);

		return true;
	}

	/**
	 * 检查是否安装了WPS PRO. <br/>
	 * 日期: 2015-6-5 下午4:36:40 <br/>
	 * 
	 * @author wangenzi
	 * @param ctx
	 * @return
	 * @since JDK 1.6
	 */
	public static boolean isWPSInstalled(Context ctx) {
		if (checkPackage(ctx, Define.PACKAGENAME_KING_PRO)) {
			Log.d(TAG, "WPS PRO Installed packageName: "
					+ Define.PACKAGENAME_KING_PRO);
			return true;
		} else {
			Log.d(TAG, "WPS PRO not Installed");
			return false;
		}
	}

	public static boolean checkPackage(Context context, String packageName) {
		if (packageName == null || "".equals(packageName))
			return false;

		try {
			context.getPackageManager().getApplicationInfo(packageName,
					PackageManager.GET_UNINSTALLED_PACKAGES);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
	}

}
