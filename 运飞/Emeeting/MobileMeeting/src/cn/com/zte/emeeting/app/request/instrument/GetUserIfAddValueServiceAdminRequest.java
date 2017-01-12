package cn.com.zte.emeeting.app.request.instrument;

import android.content.Context;
import com.google.gson.annotations.Expose;
import cn.com.zte.mobilebasedata.request.AppHttpRequest;
import cn.com.zte.mobilebasedata.request.HttpUtil;

/**
 * 获取用户是否为增值服务管理员接口请求类
 * @author LangK
 */
public class GetUserIfAddValueServiceAdminRequest extends AppHttpRequest {

	@Expose(serialize = false, deserialize = false)
	private static final long serialVersionUID = 1056878707687130274L;

	/**
	 * 获取用户是否为增值服务管理员接口
	 * @param context
	 * @param httpsFlag
	 */
	public GetUserIfAddValueServiceAdminRequest(Context context,
			boolean httpsFlag) {
		super(context, httpsFlag);
		setWebServiceMethod(HttpUtil.PublicInterface);
		setC(HttpUtil.GetUserIfAddValueServiceAdmin);
	}

	/**
	 * 获取用户是否为增值服务管理员接口
	 * @param context
	 */
	public GetUserIfAddValueServiceAdminRequest(Context context) {
		super(context, HttpUtil.HTTPSFLAG);
		setWebServiceMethod(HttpUtil.PublicInterface);
		setC(HttpUtil.GetUserIfAddValueServiceAdmin);
	}

}
