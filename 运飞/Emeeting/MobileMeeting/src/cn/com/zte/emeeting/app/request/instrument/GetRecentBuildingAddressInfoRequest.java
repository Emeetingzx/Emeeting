package cn.com.zte.emeeting.app.request.instrument;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.gson.annotations.Expose;

import cn.com.zte.mobilebasedata.entity.FilterInfo;
import cn.com.zte.mobilebasedata.request.AppHttpRequest;
import cn.com.zte.mobilebasedata.request.HttpUtil;

/**
 * 获取离用户最近公司建筑地址信息接口请求类
 * 
 * @author LangK
 * 
 */
public class GetRecentBuildingAddressInfoRequest extends AppHttpRequest {

	@Expose(serialize = false, deserialize = false)
	private static final long serialVersionUID = 1056878607687130274L;

	/**
	 * 获取离用户最近公司建筑地址信息接口
	 * @param context
	 * @param httpsFlag
	 * @param LatitudeAndLongitude	定位用户当前经纬度坐标已逗号隔开，例：【经度,维度】
	 */
	public GetRecentBuildingAddressInfoRequest(Context context,
			boolean httpsFlag, String LatitudeAndLongitude) {
		super(context, httpsFlag);
		setWebServiceMethod(HttpUtil.PublicInterface);
		setC(HttpUtil.GetRecentBuildingAddressInfo);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(new FilterInfo("LatitudeAndLongitude", LatitudeAndLongitude));
		setF(list);

	}
	
	/**
	 * 获取离用户最近公司建筑地址信息接口
	 * @param context
	 * @param httpsFlag
	 * @param LatitudeAndLongitude	定位用户当前经纬度坐标已逗号隔开，例：【经度,维度】
	 */
	public GetRecentBuildingAddressInfoRequest(Context context,String LatitudeAndLongitude) {
		super(context, HttpUtil.HTTPSFLAG);
		setWebServiceMethod(HttpUtil.PublicInterface);
		setC(HttpUtil.GetRecentBuildingAddressInfo);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(new FilterInfo("LatitudeAndLongitude", LatitudeAndLongitude));
		setF(list);
		
	}
}
