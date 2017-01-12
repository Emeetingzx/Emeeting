/******************************************************************************
 * Copyright (C) 2014 ZTE Co.,Ltd
 * All Rights Reserved.
 * 本软件为中兴通讯股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package cn.com.zte.mobilebasedata.request;

import android.content.Context;
import cn.com.zte.android.appupdate.config.UpdateServerConfig;
import cn.com.zte.android.common.log.Log;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.android.securityauth.config.SSOAuthConfig;
import cn.com.zte.android.securityauth.http.SSOTokenHttpResponse;
import cn.com.zte.emeeting.app.util.MyToast;

/**
 * TODO 简单地描述功能. <br/>
 * 日期: 2014-9-1 上午9:56:12 <br/>
 * 
 * @author wangenzi
 * @version 1.0
 * @since JDK 1.6
 * @history 2014-9-1 wangenzi 新建
 */
public class CheckNetAsyncHttpResponseHandler<T extends SSOTokenHttpResponse>
		extends BaseAsyncHttpResponseHandler<T> {

	/** Tag */
	private static final String TAG = CheckNetAsyncHttpResponseHandler.class
			.getSimpleName();

	private boolean isCheckOuter;

	private Context objContext;

	public CheckNetAsyncHttpResponseHandler(Context objContext,
			boolean isCheckOuter) {
		super(false);
		this.objContext = objContext;
		this.isCheckOuter = isCheckOuter;
	}

	/**
	 * onSuccess说明Http状态码为200.
	 * 
	 * @see cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler#onSuccess(java.lang.String)
	 */
	@Override
	public void onSuccess(String content) {
		if (isCheckOuter) {
			Log.i(TAG, "configToOuterNet");
			SSOAuthConfig.configToOuterNet();
			UpdateServerConfig.configToOuterNet();
			HttpUtil.getInstance().setSERVEREIP(HttpUtil.NetworkIP);
//			MyToast.show(objContext, "您已切换到外网网络环境");
		} else {
			Log.i(TAG, "configToInnerNet");
			SSOAuthConfig.configToInnerNet();
			UpdateServerConfig.configToInnerNet();
			HttpUtil.getInstance().setSERVEREIP(HttpUtil.IntranetIP);
//			MyToast.show(objContext, "您已切换到内网网络环境");
		}

		onNetEnable(objContext, isCheckOuter);
	}

	/**
	 * Http通讯出现异常时，说明网络不通. <br/>
	 * 日期: 2014-6-2 下午7:38:06 <br/>
	 * 
	 * @author wangenzi
	 * @param strTitle
	 * @param strCode
	 * @param strMsg
	 * @since JDK 1.6
	 */
	public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
			String strMsg) {
		onNetUnValible(objContext, isCheckOuter);
	}

	/**
	 * 网络不可用. <br/>
	 * 日期: 2014-9-1 上午9:44:25 <br/>
	 * 
	 * @author wangenzi
	 * @param objContext
	 * @param isCheckOuter
	 * @since JDK 1.6
	 */
	public void onNetUnValible(Context objContext, boolean isCheckOuter) {

	};

	/**
	 * 网络可用. <br/>
	 * 日期: 2014-9-1 上午9:44:25 <br/>
	 * 
	 * @author wangenzi
	 * @param objContext
	 * @param isCheckOuter
	 * @since JDK 1.6
	 */
	public void onNetEnable(Context objContext, boolean isCheckOuter) {

	}

	public boolean isCheckOuter() {
		return isCheckOuter;
	}

	public void setCheckOuter(boolean isCheckOuter) {
		this.isCheckOuter = isCheckOuter;
	};

}