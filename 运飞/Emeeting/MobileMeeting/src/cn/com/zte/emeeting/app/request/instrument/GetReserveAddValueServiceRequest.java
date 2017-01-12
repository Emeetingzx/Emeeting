package cn.com.zte.emeeting.app.request.instrument;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.gson.annotations.Expose;

import cn.com.zte.mobilebasedata.entity.FilterInfo;
import cn.com.zte.mobilebasedata.request.AppHttpRequest;
import cn.com.zte.mobilebasedata.request.HttpUtil;

/**
 * 预定增值服务接口请求类
 * @author LangK
 *
 */
public class GetReserveAddValueServiceRequest extends AppHttpRequest{


	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1056878607687130274L;

	/**	
	 * 预定增值服务接口请求类
	 * @param context
	 * @param httpsFlag
	 * @param serverDate服务时间	serviceDate	
	 * @param serviceAddr服务地点	serviceAddress	
	 * @param phone联系电话	phone
	 * @param foodids茶点ID传	foodIds	多个茶点ID使用逗号隔开
	 */
	public GetReserveAddValueServiceRequest(Context context, boolean httpsFlag,String serverDate,
			String serviceRegionID,String serviceAddr,String phone,String foodids) {
		super(context, httpsFlag);
		setWebServiceMethod(HttpUtil.PublicInterface);
		setC(HttpUtil.ReserveAddValueService);
		FilterInfo serdateFilter = new FilterInfo("serviceDate",serverDate);
		FilterInfo seraddrFilter = new FilterInfo("serviceAddress",serviceAddr);
		FilterInfo phoneFilter = new FilterInfo("phone",phone);
		FilterInfo foodidFilter = new FilterInfo("foodIds",foodids);
		FilterInfo regionFilter = new FilterInfo("serviceRegionID",serviceRegionID);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(serdateFilter);
		list.add(seraddrFilter);
		list.add(phoneFilter);
		list.add(regionFilter);
		list.add(foodidFilter);
		setF(list);
	}
	
	/**	
	 * 预定增值服务接口请求类
	 * @param context
	 * @param httpsFlag
	 * @param serverDate服务时间	serviceDate	
	 * @param serviceAddr服务地点	serviceAddress	
	 * @param phone联系电话	phone
	 * @param foodids茶点ID传	foodIds	多个茶点ID使用逗号隔开
	 */
	public GetReserveAddValueServiceRequest(Context context,String serverDate,
			String serviceRegionID,String serviceAddr,String phone,String foodids) {
		super(context, HttpUtil.HTTPSFLAG);
		setWebServiceMethod(HttpUtil.PublicInterface);
		setC(HttpUtil.ReserveAddValueService);
		FilterInfo serdateFilter = new FilterInfo("serviceDate",serverDate);
		FilterInfo seraddrFilter = new FilterInfo("serviceAddress",serviceAddr);
		FilterInfo phoneFilter = new FilterInfo("phone",phone);
		FilterInfo foodidFilter = new FilterInfo("foodIds",foodids);
		FilterInfo regionFilter = new FilterInfo("serviceRegionID",serviceRegionID);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(serdateFilter);
		list.add(seraddrFilter);
		list.add(regionFilter);
		list.add(phoneFilter);
		list.add(foodidFilter);
		setF(list);
	}
}
