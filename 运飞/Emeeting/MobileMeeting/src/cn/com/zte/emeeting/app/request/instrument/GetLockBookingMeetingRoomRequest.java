package cn.com.zte.emeeting.app.request.instrument;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.gson.annotations.Expose;

import cn.com.zte.mobilebasedata.entity.FilterInfo;
import cn.com.zte.mobilebasedata.request.AppHttpRequest;
import cn.com.zte.mobilebasedata.request.HttpUtil;

/**
 *锁定预定会议室接口请求类
 * @author LangK
 *
 */
public class GetLockBookingMeetingRoomRequest extends AppHttpRequest{


	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1056878707687130274L;

	/**
	 * 锁定预定会议室接口
	 * @param context
	 * @param httpsFlag
	 * @param RoomId 会议室ID
	 * @param BeginDate 开始会议时间
	 * @param EndDate 结束会议时间
	 */
	public GetLockBookingMeetingRoomRequest(Context context, boolean httpsFlag,String RoomId,String BeginDate,String EndDate) {
		super(context, httpsFlag);
		setWebServiceMethod(HttpUtil.PublicInterface);
		setC(HttpUtil.LockBookingMeetingRoom);
		FilterInfo beginDateFilter = new FilterInfo("BeginDate",BeginDate);
		FilterInfo endDateFilter = new FilterInfo("EndDate",EndDate);
		FilterInfo roomIdFilter = new FilterInfo("RoomId",RoomId);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(roomIdFilter);
		list.add(endDateFilter);
		list.add(beginDateFilter);
		setF(list);
	}
	
	/**
	 * 锁定预定会议室接口
	 * @param context
	 * @param httpsFlag
	 * @param RoomId 会议室ID
	 * @param BeginDate 开始会议时间
	 * @param EndDate 结束会议时间
	 */
	public GetLockBookingMeetingRoomRequest(Context context,String RoomId,String BeginDate,String EndDate) {
		super(context, HttpUtil.HTTPSFLAG);
		setWebServiceMethod(HttpUtil.PublicInterface);
		setC(HttpUtil.LockBookingMeetingRoom);
		FilterInfo beginDateFilter = new FilterInfo("BeginDate",BeginDate);
		FilterInfo endDateFilter = new FilterInfo("EndDate",EndDate);
		FilterInfo roomIdFilter = new FilterInfo("RoomId",RoomId);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(roomIdFilter);
		list.add(endDateFilter);
		list.add(beginDateFilter);
		setF(list);
	}
}
