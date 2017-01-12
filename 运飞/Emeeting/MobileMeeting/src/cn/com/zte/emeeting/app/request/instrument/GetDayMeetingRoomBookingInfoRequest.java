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
 *【分页】获取指定条件会议室预定情况列表接口请求类
 * @author LangK
 *
 */
public class GetDayMeetingRoomBookingInfoRequest extends AppHttpRequest{


	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1056878707687130274L;

	/**
	 * 【分页】获取指定条件会议室预定情况列表接口请求类
	 * @param context
	 * @param httpsFlag
	 * @param CityId	城市ID
	 * @param ParkId	园区ID
	 * @param BuildId	建筑ID
	 * @param QueryDate	查询日期
	 * @param pageinput
	 */
	public GetDayMeetingRoomBookingInfoRequest(Context context, boolean httpsFlag,String CityId,String ParkId,String BuildId,String QueryDate,PageInput pageinput) {
		super(context, httpsFlag);
		setWebServiceMethod(HttpUtil.EmeetingQueryInterface);
		setC(HttpUtil.GetDayMeetingRoomBookingInfo);
		FilterInfo cityIdFilter = new FilterInfo("CityId",CityId);
		FilterInfo parkIdFilter = new FilterInfo("ParkId",ParkId);
		FilterInfo buildIdFilter = new FilterInfo("BuildId",BuildId);
		FilterInfo queryDateNameFilter = new FilterInfo("QueryDate",QueryDate);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(cityIdFilter);
		list.add(parkIdFilter);
		list.add(buildIdFilter);
		list.add(queryDateNameFilter);
		setF(list);
		setP(pageinput);
	}

	/**
	 * 【分页】获取指定条件会议室预定情况列表接口请求类
	 * @param context
	 * @param CityId	城市ID
	 * @param ParkId	园区ID
	 * @param BuildId	建筑ID
	 * @param QueryDate	查询日期
	 * @param pageinput
	 */
	public GetDayMeetingRoomBookingInfoRequest(Context context,String CityId,String ParkId,String BuildId,String QueryDate,PageInput pageinput) {
		super(context, HttpUtil.HTTPSFLAG);
		setWebServiceMethod(HttpUtil.EmeetingQueryInterface);
		setC(HttpUtil.GetDayMeetingRoomBookingInfo);
		FilterInfo cityIdFilter = new FilterInfo("CityId",CityId);
		FilterInfo parkIdFilter = new FilterInfo("ParkId",ParkId);
		FilterInfo buildIdFilter = new FilterInfo("BuildId",BuildId);
		FilterInfo queryDateNameFilter = new FilterInfo("QueryDate",QueryDate);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(cityIdFilter);
		list.add(parkIdFilter);
		list.add(buildIdFilter);
		list.add(queryDateNameFilter);
		setF(list);
		setP(pageinput);
	}
}
