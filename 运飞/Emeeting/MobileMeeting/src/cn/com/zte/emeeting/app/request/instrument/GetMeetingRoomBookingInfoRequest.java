package cn.com.zte.emeeting.app.request.instrument;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.gson.annotations.Expose;

import cn.com.zte.android.common.util.JsonUtil;
import cn.com.zte.emeeting.app.request.entity.ScreeningCondition;
import cn.com.zte.mobilebasedata.entity.FilterInfo;
import cn.com.zte.mobilebasedata.entity.PageInput;
import cn.com.zte.mobilebasedata.request.AppHttpRequest;
import cn.com.zte.mobilebasedata.request.HttpUtil;

/**
 *	获取某天符合条件会议室信息接口请求类
 * @author LangK
 *
 */
public class GetMeetingRoomBookingInfoRequest extends AppHttpRequest{


	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1056878707687130274L;

	/**
	 * 获取某天符合条件会议室信息接口请求类
	 * @param context
	 * @param httpsFlag	
	 * @param MeetingRoomId	会议室ID		必传
	 * @param QueryDate	查询日期		必传
	 * @param BeginDate	开始时间	
	 * @param EndDate	结束时间		YYYY-MM-DD hh:mm:ss
	 * @param data	筛选条件对象实体
	 */
	public GetMeetingRoomBookingInfoRequest(Context context,PageInput pageInput, boolean httpsFlag,String MeetingRoomId,String QueryDate,String BeginDate,String EndDate,ScreeningCondition data) {
		super(context, httpsFlag);
		setWebServiceMethod(HttpUtil.EmeetingQueryInterface);
		setC(HttpUtil.GetMeetingRoomBookingInfo);
		FilterInfo meetingRoomIdFilter = new FilterInfo("MeetingRoomId",MeetingRoomId);
		FilterInfo queryDateNameFilter = new FilterInfo("QueryDate",QueryDate);
		FilterInfo beginFilter = new FilterInfo("BeginDate",BeginDate);
		FilterInfo endFilter = new FilterInfo("EndDate",EndDate);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(meetingRoomIdFilter);
		list.add(beginFilter);
		list.add(endFilter);
		list.add(queryDateNameFilter);
		setF(list);
		setP(pageInput);
		if (data!=null) {
			setD(JsonUtil.toJson(data));
		}
	}
	
	/**
	 * 获取某天符合条件会议室信息接口请求类
	 * @param context
	 * @param MeetingRoomId	会议室ID		必传
	 * @param QueryDate	查询日期		必传
	 * @param BeginDate	开始时间	
	 * @param EndDate	结束时间		YYYY-MM-DD hh:mm:ss
	 * @param data	筛选条件对象实体
	 */
	public GetMeetingRoomBookingInfoRequest(Context context,PageInput pageInput,String MeetingRoomId,String QueryDate,String BeginDate,String EndDate,ScreeningCondition data) {
		super(context, HttpUtil.HTTPSFLAG);
		setWebServiceMethod(HttpUtil.EmeetingQueryInterface);
		setC(HttpUtil.GetMeetingRoomBookingInfo);
		FilterInfo meetingRoomIdFilter = new FilterInfo("MeetingRoomId",MeetingRoomId);
		FilterInfo queryDateNameFilter = new FilterInfo("QueryDate",QueryDate);
		FilterInfo beginFilter = new FilterInfo("BeginDate",BeginDate);
		FilterInfo endFilter = new FilterInfo("EndDate",EndDate);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(meetingRoomIdFilter);
		list.add(beginFilter);
		list.add(endFilter);
		list.add(queryDateNameFilter);
		setF(list);
		setP(pageInput);
		if (data!=null) {
			setD(JsonUtil.toJson(data));
		}
	}
}
