package cn.com.zte.emeeting.app.request.instrument;


import android.content.Context;

import com.google.gson.annotations.Expose;

import cn.com.zte.mobilebasedata.entity.PageInput;
import cn.com.zte.mobilebasedata.request.AppHttpRequest;
import cn.com.zte.mobilebasedata.request.HttpUtil;

/**
 * 获取我的增值服务信息接口请求类
 * @author LangK
 *
 */
public class GetMyAddValueServiceInfosRequest extends AppHttpRequest{


	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1056878607687130274L;

	/**
	 * 获取我的增值服务信息接口请求类
	 * @param context
	 * @param httpsFlag
	 */
	public GetMyAddValueServiceInfosRequest(Context context, boolean httpsFlag,PageInput input) {
		super(context, httpsFlag);
		setWebServiceMethod(HttpUtil.PublicInterface);
		setC(HttpUtil.GetMyAddValueServiceInfos);
		setP(input);
	}
	/**
	 * 获取我的增值服务信息接口请求类
	 * @param context
	 */
	public GetMyAddValueServiceInfosRequest(Context context,PageInput input) {
		super(context, HttpUtil.HTTPSFLAG);
		setWebServiceMethod(HttpUtil.PublicInterface);
		setC(HttpUtil.GetMyAddValueServiceInfos);
		setP(input);
	}
}
