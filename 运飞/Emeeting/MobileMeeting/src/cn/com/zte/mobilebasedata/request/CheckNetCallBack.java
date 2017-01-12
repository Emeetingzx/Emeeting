/******************************************************************************
 * Copyright (C) 2014 ZTE Co.,Ltd
 * All Rights Reserved.
 * 本软件为中兴通讯股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package cn.com.zte.mobilebasedata.request;

import cn.com.zte.android.http.HttpManager;
import cn.com.zte.emeeting.app.util.DataConst;

/**
 * 网络检测完成回调. <br/>
 * 日期: 2014-9-1 上午10:06:26 <br/>
 * 
 * @author wangenzi
 * @version 1.0
 * @since JDK 1.6
 * @history 2014-9-1 wangenzi 新建
 */
public class CheckNetCallBack {

	/**
	 * 外网检测完成事件. <br/>
	 * 日期: 2014-9-1 上午10:07:42 <br/>
	 * 
	 * @author wangenzi
	 * @param isCheckOuter
	 * @param isEnable
	 * @since JDK 1.6
	 */
	public void onNetCheckFinish(boolean isCheckOuter, boolean isEnable) {
		// 设置超时
		HttpManager.setTimeout(HttpUtil.TimeOut);
	}
	
	/**
	 * 内网检测完成事件. <br/>
	 * 日期: 2014-9-1 上午10:07:42 <br/>
	 * 
	 * @author wangenzi
	 * @param isCheckOuter
	 * @param isEnable
	 * @since JDK 1.6
	 */
	public void onIntranetCheckFinish(boolean isCheckOuter, boolean isEnable) {
		// 设置超时
		HttpManager.setTimeout(HttpUtil.TimeOut);
	}

}
