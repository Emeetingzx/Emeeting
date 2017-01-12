package cn.com.zte.emeeting.app.request.instrument;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.gson.annotations.Expose;

import cn.com.zte.mobilebasedata.entity.FilterInfo;
import cn.com.zte.mobilebasedata.request.AppHttpRequest;
import cn.com.zte.mobilebasedata.request.HttpUtil;

/**
 * 人员签到接口请求类
 * @author LangK
 *
 */
public class GetAttendanceOperationRequest extends AppHttpRequest{


	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1056878606687130274L;

	/**
	 * 人员签到接口请求类
	 * @param context
	 * @param httpsFlag
	 * @param meetingid	会议ID
	 * @param CodeInfo	二维码信息
	 */
	public GetAttendanceOperationRequest(Context context, boolean httpsFlag,String CodeInfo,String meetingID) {
		super(context, httpsFlag);
		setWebServiceMethod(HttpUtil.MeetingControl);
		setC(HttpUtil.AttendanceOperation);
		FilterInfo midFilter = new FilterInfo("meetingID",meetingID);
		FilterInfo codeinfoFilter = new FilterInfo("CodeInfo",CodeInfo);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(midFilter);
		list.add(codeinfoFilter);
		setF(list);
	}
	
	/**
	 * 人员签到接口请求类
	 * @param context
	 * @param httpsFlag
	 * @param meetingid	会议ID
	 * @param CodeInfo	二维码信息
	 */
	public GetAttendanceOperationRequest(Context context,String CodeInfo,String meetingID) {
		super(context, HttpUtil.HTTPSFLAG);
		setWebServiceMethod(HttpUtil.MeetingControl);
		setC(HttpUtil.AttendanceOperation);
		FilterInfo midFilter = new FilterInfo("meetingID",meetingID);
		FilterInfo codeinfoFilter = new FilterInfo("CodeInfo",CodeInfo);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(midFilter);
		list.add(codeinfoFilter);
		setF(list);
	}
}
