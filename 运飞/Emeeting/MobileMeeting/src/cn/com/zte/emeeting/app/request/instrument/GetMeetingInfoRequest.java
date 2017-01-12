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
 *	获取会议详细信息接口
 * @author LangK
 *
 */
public class GetMeetingInfoRequest extends AppHttpRequest{


	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1056878707687130274L;
	
	/**
	 * 获取会议详细信息接口
	 * @param context
	 * @param MeetingId	会议ID
	 */
	public GetMeetingInfoRequest(Context context, boolean httpsFlag,String MeetingId) {
		super(context, httpsFlag);
		setWebServiceMethod(HttpUtil.MyEmeetingInterface);
		setC(HttpUtil.GetMeetingInfo);
		FilterInfo meetingIdFilter = new FilterInfo("MeetingId",MeetingId);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(meetingIdFilter);
		setF(list);
	}
	
	/**
	 * 获取会议详细信息接口
	 * @param context
	 * @param MeetingId	会议ID
	 */
	public GetMeetingInfoRequest(Context context,String MeetingId) {
		super(context, HttpUtil.HTTPSFLAG);
		setWebServiceMethod(HttpUtil.MyEmeetingInterface);
		setC(HttpUtil.GetMeetingInfo);
		FilterInfo meetingIdFilter = new FilterInfo("MeetingId",MeetingId);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(meetingIdFilter);
		setF(list);
	}
}
