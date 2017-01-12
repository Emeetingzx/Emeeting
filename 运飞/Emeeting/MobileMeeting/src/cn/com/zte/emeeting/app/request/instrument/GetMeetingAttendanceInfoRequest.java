package cn.com.zte.emeeting.app.request.instrument;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.gson.annotations.Expose;

import cn.com.zte.mobilebasedata.entity.FilterInfo;
import cn.com.zte.mobilebasedata.request.AppHttpRequest;
import cn.com.zte.mobilebasedata.request.HttpUtil;

/**
 * 获取签到人员信息接口请求类
 * @author LangK
 *
 */
public class GetMeetingAttendanceInfoRequest extends AppHttpRequest{


	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1056878606687130274L;

	/**
	 * 获取签到人员信息接口请求类
	 * @param context
	 * @param httpsFlag
	 * @param meetingid	会议ID
	 */
	public GetMeetingAttendanceInfoRequest(Context context, boolean httpsFlag,String meetingid) {
		super(context, httpsFlag);
		setWebServiceMethod(HttpUtil.MeetingControl);
		setC(HttpUtil.GetMeetingAttendanceInfo);
		FilterInfo midFilter = new FilterInfo("ID",meetingid);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(midFilter);
		setF(list);
	}
	
	/**
	 * 获取签到人员信息接口请求类
	 * @param context
	 * @param httpsFlag
	 * @param meetingid	会议ID
	 */
	public GetMeetingAttendanceInfoRequest(Context context,String meetingid) {
		super(context, HttpUtil.HTTPSFLAG);
		setWebServiceMethod(HttpUtil.MeetingControl);
		setC(HttpUtil.GetMeetingAttendanceInfo);
		FilterInfo midFilter = new FilterInfo("ID",meetingid);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(midFilter);
		setF(list);
	}
}
