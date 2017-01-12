package cn.com.zte.emeeting.app.request.instrument;


import android.content.Context;

import com.google.gson.annotations.Expose;

import cn.com.zte.mobilebasedata.request.AppHttpRequest;
import cn.com.zte.mobilebasedata.request.HttpUtil;

/**
 * 获取服务器时间接口请求类
 * @author LangK
 *
 */
public class GetServerTimeRequest extends AppHttpRequest{


	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1056878607687130274L;

	public GetServerTimeRequest(Context context, boolean httpsFlag) {
		super(context, httpsFlag);
		setWebServiceMethod(HttpUtil.PublicInterface);
		setC(HttpUtil.GetServerData);
	}
	
	public GetServerTimeRequest(Context context) {
		super(context, HttpUtil.HTTPSFLAG);
		setWebServiceMethod(HttpUtil.PublicInterface);
		setC(HttpUtil.GetServerData);
	}
}
