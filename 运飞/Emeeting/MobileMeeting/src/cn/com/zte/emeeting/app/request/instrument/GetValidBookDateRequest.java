package cn.com.zte.emeeting.app.request.instrument;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.gson.annotations.Expose;

import cn.com.zte.mobilebasedata.entity.FilterInfo;
import cn.com.zte.mobilebasedata.request.AppHttpRequest;
import cn.com.zte.mobilebasedata.request.HttpUtil;

/**
 * 获取可预订会议最长时间接口请求类
 * @author LangK
 *
 */
public class GetValidBookDateRequest extends AppHttpRequest{


	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1056878606687130274L;

	/**
	 * 获取可预订会议最长时间接口请求类
	 * @param context
	 * @param httpsFlag
	 */
	public GetValidBookDateRequest(Context context, boolean httpsFlag) {
		super(context, httpsFlag);
		setWebServiceMethod(HttpUtil.PublicInterface);
		setC(HttpUtil.GetValidBookDate);
	}
	
	/**
	 * 获取可预订会议最长时间接口请求类
	 * @param context
	 * @param httpsFlag
	 */
	public GetValidBookDateRequest(Context context) {
		super(context, HttpUtil.HTTPSFLAG);
		setWebServiceMethod(HttpUtil.PublicInterface);
		setC(HttpUtil.GetValidBookDate);
	}
}
