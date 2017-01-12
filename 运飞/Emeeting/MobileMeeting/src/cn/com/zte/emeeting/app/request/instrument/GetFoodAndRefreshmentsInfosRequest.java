package cn.com.zte.emeeting.app.request.instrument;

import android.content.Context;
import com.google.gson.annotations.Expose;

import cn.com.zte.mobilebasedata.entity.PageInput;
import cn.com.zte.mobilebasedata.request.AppHttpRequest;
import cn.com.zte.mobilebasedata.request.HttpUtil;

/**
 * 获取食品茶点信息接口请求类
 * @author LangK
 */
public class GetFoodAndRefreshmentsInfosRequest extends AppHttpRequest {

	@Expose(serialize = false, deserialize = false)
	private static final long serialVersionUID = 1056878707687130274L;

	/**
	 *获取食品茶点信息接口
	 * @param context
	 * @param httpsFlag
	 */
	public GetFoodAndRefreshmentsInfosRequest(Context context,
			boolean httpsFlag,PageInput input) {
		super(context, httpsFlag);
		setWebServiceMethod(HttpUtil.PublicInterface);
		setC(HttpUtil.GetFoodAndRefreshmentsInfos);
		setP(input);
	}

	/**
	 * 获取食品茶点信息接口
	 * @param context
	 */
	public GetFoodAndRefreshmentsInfosRequest(Context context,PageInput input) {
		super(context, HttpUtil.HTTPSFLAG);
		setWebServiceMethod(HttpUtil.PublicInterface);
		setC(HttpUtil.GetFoodAndRefreshmentsInfos);
		setP(input);
	}

}
