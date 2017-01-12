package cn.com.zte.emeeting.app.request.instrument;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.gson.annotations.Expose;

import cn.com.zte.mobilebasedata.entity.FilterInfo;
import cn.com.zte.mobilebasedata.request.AppHttpRequest;
import cn.com.zte.mobilebasedata.request.HttpUtil;

/**
 * 增值服务操作接口请求类
 * @author LangK
 *
 */
public class GetAddValueServiceOperateRequest extends AppHttpRequest{


	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1056878607687130274L;

	/**
	 * 增值服务操作接口请求类
	 * @param context
	 * @param httpsFlag
	 * @param serviceOrderId	服务订单ID
	 * @param operateUserType	操作人员类型 普通用户/管理员
	 * @param operateType	操作类型		用户退订/管理员受理（接单）/管理员提交完成（服务）/管理员退单
	 */
	public GetAddValueServiceOperateRequest(Context context, boolean httpsFlag,String serviceOrderId,
		String operateUserType,String operateType) {
		super(context, httpsFlag);
		setWebServiceMethod(HttpUtil.PublicInterface);
		setC(HttpUtil.AddValueServiceOperate);
		FilterInfo serdateFilter = new FilterInfo("serviceOrderId",serviceOrderId);
		FilterInfo seraddrFilter = new FilterInfo("operateType",operateType);
		FilterInfo phoneFilter = new FilterInfo("operateUserType",operateUserType);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(serdateFilter);
		list.add(seraddrFilter);
		list.add(phoneFilter);
		setF(list);
	}
	
	/**
	 * 增值服务操作接口请求类
	 * @param context
	 * @param httpsFlag
	 * @param serviceOrderId	服务订单ID
	 * @param operateUserType	操作人员类型 普通用户/管理员
	 * @param operateType	操作类型		用户退订/管理员受理（接单）/管理员提交完成（服务）/管理员退单
	 */
	public GetAddValueServiceOperateRequest(Context context, String serviceOrderId,
		String operateUserType,String operateType) {
		super(context, HttpUtil.HTTPSFLAG);
		setWebServiceMethod(HttpUtil.PublicInterface);
		setC(HttpUtil.AddValueServiceOperate);
		FilterInfo serdateFilter = new FilterInfo("serviceOrderId",serviceOrderId);
		FilterInfo seraddrFilter = new FilterInfo("operateType",operateType);
		FilterInfo phoneFilter = new FilterInfo("operateUserType",operateUserType);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(serdateFilter);
		list.add(seraddrFilter);
		list.add(phoneFilter);
		setF(list);
	}
}
