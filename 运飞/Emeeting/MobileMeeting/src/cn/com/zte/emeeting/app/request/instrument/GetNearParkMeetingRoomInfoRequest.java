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
 *	查询定位或选择园区可预订会议室信息接口
 * @author LangK
 *
 */
public class GetNearParkMeetingRoomInfoRequest extends AppHttpRequest{


	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1056878707687130274L;

	
	/**
	 * 查询定位或选择园区可预订会议室信息接口
	 * @param context
	 * @param LatitudeAndLongitude 定位用户当前经纬度坐标已逗号隔开，例：【经度,维度】
	 * @param EmployeeNo 员工工号
	 * @param WhetherRandomSequence	是否随机排序	默认为N,摇一摇必须传Y
	 */
	public GetNearParkMeetingRoomInfoRequest(Context context, boolean httpsFlag,String LatitudeAndLongitude,String AddressId,String MeetingTime,String WhetherRandomSequence,PageInput pageInput) {
		super(context, httpsFlag);
		setWebServiceMethod(HttpUtil.SharkOffInterface);
		setC(HttpUtil.GetNearParkMeetingRoomInfo);
		FilterInfo latitudeAndLongitudeFilter = new FilterInfo("LatitudeAndLongitude",LatitudeAndLongitude);
		FilterInfo meetingTimeFilter = new FilterInfo("MeetingTime",MeetingTime);
		FilterInfo addressIdFilter = new FilterInfo("AddressId",AddressId);
		FilterInfo wheterFilter = new FilterInfo("WhetherRandomSequence",WhetherRandomSequence);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(latitudeAndLongitudeFilter);
		list.add(meetingTimeFilter);
		list.add(addressIdFilter);
		list.add(wheterFilter);
		setF(list);
		setP(pageInput);
	}
	
	/**
	 * 查询定位或选择园区可预订会议室信息接口
	 * @param context
	 * @param LatitudeAndLongitude 定位用户当前经纬度坐标已逗号隔开，例：【经度,维度】
	 * @param AddressId	地址ID
	 * @param MeetingTime	会议时长
	 * @param WhetherRandomSequence	是否随机排序	默认为N,摇一摇必须传Y
	 */
	public GetNearParkMeetingRoomInfoRequest(Context context,String LatitudeAndLongitude,String AddressId,String MeetingTime,String WhetherRandomSequence,PageInput pageInput) {
		super(context, HttpUtil.HTTPSFLAG);
		setWebServiceMethod(HttpUtil.SharkOffInterface);
		setC(HttpUtil.GetNearParkMeetingRoomInfo);
		FilterInfo latitudeAndLongitudeFilter = new FilterInfo("LatitudeAndLongitude",LatitudeAndLongitude);
		FilterInfo meetingTimeFilter = new FilterInfo("MeetingTime",MeetingTime);
		FilterInfo addressIdFilter = new FilterInfo("AddressId",AddressId);
		FilterInfo wheterFilter = new FilterInfo("WhetherRandomSequence",WhetherRandomSequence);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(latitudeAndLongitudeFilter);
		list.add(meetingTimeFilter);
		list.add(addressIdFilter);
		list.add(wheterFilter);
		setF(list);
		setP(pageInput);
	}
}
