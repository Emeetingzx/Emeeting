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
 * 定位查询附近园区地址信息接口请求类
 * 
 * @author LangK
 * 
 */
public class GetNearParkAddressInfoRequest extends AppHttpRequest {

	@Expose(serialize = false, deserialize = false)
	private static final long serialVersionUID = 1056878707687130274L;

	/**
	 * 定位查询附近园区地址信息接口请求类
	 * 
	 * @param context
	 * @param LatitudeAndLongitude
	 *            定位用户当前经纬度坐标已逗号隔开，例：【经度,维度】
	 * @param EmployeeNo
	 *            员工工号
	 */
	public GetNearParkAddressInfoRequest(Context context, boolean httpsFlag,
			String LatitudeAndLongitude) {
		super(context, httpsFlag);
		setWebServiceMethod(HttpUtil.SharkOffInterface);
		setC(HttpUtil.GetNearParkAddressInfo);
		FilterInfo latitudeAndLongitudeFilter = new FilterInfo(
				"LatitudeAndLongitude", LatitudeAndLongitude);
		// FilterInfo employeeNoFilter = new
		// FilterInfo("EmployeeNo",EmployeeNo);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(latitudeAndLongitudeFilter);
		// list.add(employeeNoFilter);
		setF(list);
	}

	/**
	 * 定位查询附近园区地址信息接口请求类
	 * 
	 * @param context
	 * @param LatitudeAndLongitude
	 *            定位用户当前经纬度坐标已逗号隔开，例：【经度,维度】
	 * @param EmployeeNo
	 *            员工工号
	 */
	public GetNearParkAddressInfoRequest(Context context,
			String LatitudeAndLongitude) {
		super(context, HttpUtil.HTTPSFLAG);
		setWebServiceMethod(HttpUtil.SharkOffInterface);
		setC(HttpUtil.GetNearParkAddressInfo);
		FilterInfo latitudeAndLongitudeFilter = new FilterInfo(
				"LatitudeAndLongitude", LatitudeAndLongitude);
		// FilterInfo employeeNoFilter = new
		// FilterInfo("EmployeeNo",EmployeeNo);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(latitudeAndLongitudeFilter);
		// list.add(employeeNoFilter);
		setF(list);
	}
}
