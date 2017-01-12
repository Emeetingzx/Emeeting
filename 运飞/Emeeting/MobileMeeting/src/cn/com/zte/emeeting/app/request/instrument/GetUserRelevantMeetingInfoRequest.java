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
 *	【分页】获取与我相关的会议接口
 * @author LangK
 *
 */
public class GetUserRelevantMeetingInfoRequest extends AppHttpRequest{


	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1056878707687130274L;
	
	
	/**
	 * 获取与我相关的会议接口
	 * @param context
	 * @param MeetingBelonging 会议归属
	 * @param MeetingDate	会议日期
	 * @param pageInput
	 */
	public GetUserRelevantMeetingInfoRequest(Context context, boolean httpsFlag,String MeetingBelonging,String MeetingDate,PageInput pageInput) {
		super(context, httpsFlag);
		setWebServiceMethod(HttpUtil.MyEmeetingInterface);
		setC(HttpUtil.GetUserRelevantMeetingInfo);
		FilterInfo meetingBelongingFilter = new FilterInfo("MeetingBelonging",MeetingBelonging);
		FilterInfo meetingDateFilter = new FilterInfo("MeetingDate",MeetingDate);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(meetingBelongingFilter);
		list.add(meetingDateFilter);
		setF(list);
		setP(pageInput);
	}
	
	/**
	 * 获取与我相关的会议接口
	 * @param context
	 * @param MeetingBelonging 会议归属
	 * @param MeetingDate	会议日期
	 * @param pageInput
	 */
	public GetUserRelevantMeetingInfoRequest(Context context,String MeetingBelonging,String MeetingDate,PageInput pageInput) {
		super(context, HttpUtil.HTTPSFLAG);
		setWebServiceMethod(HttpUtil.MyEmeetingInterface);
		setC(HttpUtil.GetUserRelevantMeetingInfo);
		FilterInfo meetingBelongingFilter = new FilterInfo("MeetingBelonging",MeetingBelonging);
		FilterInfo meetingDateFilter = new FilterInfo("MeetingDate",MeetingDate);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(meetingBelongingFilter);
		list.add(meetingDateFilter);
		setF(list);
		setP(pageInput);
	}
}
