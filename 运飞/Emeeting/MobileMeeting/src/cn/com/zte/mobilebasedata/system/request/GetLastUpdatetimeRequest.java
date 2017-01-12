package cn.com.zte.mobilebasedata.system.request;


import android.content.Context;

import com.google.gson.annotations.Expose;

import cn.com.zte.mobilebasedata.request.AppHttpRequest;
import cn.com.zte.mobilebasedata.request.HttpUtil;

/**
 * 获取用户数据更新时间接口请求类
 * @author LangK
 *
 */
public class GetLastUpdatetimeRequest extends AppHttpRequest{


	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1056878607687130274L;

	public GetLastUpdatetimeRequest(Context context, boolean httpsFlag) {
		super(context, httpsFlag);
		setWebServiceMethod(HttpUtil.BasisInterface);
		setC(HttpUtil.GetLastUpdatetime);
	}
	
	public GetLastUpdatetimeRequest(Context context) {
		super(context, HttpUtil.HTTPSFLAG);
		setWebServiceMethod(HttpUtil.BasisInterface);
		setC(HttpUtil.GetLastUpdatetime);
	}
}
