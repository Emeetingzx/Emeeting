package cn.com.zte.emeeting.app.request.instrument;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.gson.annotations.Expose;

import cn.com.zte.mobilebasedata.entity.FilterInfo;
import cn.com.zte.mobilebasedata.entity.PageInput;
import cn.com.zte.mobilebasedata.request.AppHttpRequest;
import cn.com.zte.mobilebasedata.request.HttpUtil;

/**
 * 【分页】获取管理员相关增值服务信息接口请求类
 * 
 * @author LangK
 * 
 */
public class GetAdminAddValueServiceInfosRequest extends AppHttpRequest {

	@Expose(serialize = false, deserialize = false)
	private static final long serialVersionUID = 1056878607687130274L;

	/**
	 * 【分页】获取管理员相关增值服务信息接口请求类
	 * 
	 * @param context
	 * @param httpsFlag
	 * @param relatedItemType
	 *            相关项类型 待处理 已接单 已服务
	 */
	public GetAdminAddValueServiceInfosRequest(Context context,
			boolean httpsFlag, String relatedItemType, PageInput input) {
		super(context, httpsFlag);
		setWebServiceMethod(HttpUtil.PublicInterface);
		setC(HttpUtil.GetAdminAddValueServiceInfos);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(new FilterInfo("relatedItemType", relatedItemType));
		setF(list);
		setP(input);
	}

	/**
	 * 【分页】获取管理员相关增值服务信息接口请求类
	 * 
	 * @param context
	 * @param httpsFlag
	 * @param relatedItemType
	 *            相关项类型 待处理 已接单 已服务
	 */
	public GetAdminAddValueServiceInfosRequest(Context context,
			String relatedItemType, PageInput input) {
		super(context, HttpUtil.HTTPSFLAG);
		setWebServiceMethod(HttpUtil.PublicInterface);
		setC(HttpUtil.GetAdminAddValueServiceInfos);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(new FilterInfo("relatedItemType", relatedItemType));
		setF(list);
		setP(input);
	}
}
