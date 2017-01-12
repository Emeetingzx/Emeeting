package cn.com.zte.emeeting.app.request.instrument;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.gson.annotations.Expose;

import cn.com.zte.mobilebasedata.entity.FilterInfo;
import cn.com.zte.mobilebasedata.request.AppHttpRequest;
import cn.com.zte.mobilebasedata.request.HttpUtil;

/**
 * 获取增值服务地区信息接口请求类
 * @author LangK
 *
 */
public class GetAddValueServiceRegionInfosRequest extends AppHttpRequest{


	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1056878607687130274L;

	/**
	 * 增值服务操作接口请求类
	 * @param context
	 * @param httpsFlag
	 */
	public GetAddValueServiceRegionInfosRequest(Context context, boolean httpsFlag) {
		super(context, httpsFlag);
		setWebServiceMethod(HttpUtil.PublicInterface);
		setC(HttpUtil.GetAddValueServiceRegionInfos);
	}
	
	/**
	 * 增值服务操作接口请求类
	 * @param context
	 * @param httpsFlag
	 */
	public GetAddValueServiceRegionInfosRequest(Context context) {
		super(context, HttpUtil.HTTPSFLAG);
		setWebServiceMethod(HttpUtil.PublicInterface);
		setC(HttpUtil.GetAddValueServiceRegionInfos);
	}
	
}
