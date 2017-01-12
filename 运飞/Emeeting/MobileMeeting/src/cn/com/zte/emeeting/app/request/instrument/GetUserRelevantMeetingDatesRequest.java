package cn.com.zte.emeeting.app.request.instrument;



import android.content.Context;

import com.google.gson.annotations.Expose;

import cn.com.zte.mobilebasedata.request.AppHttpRequest;
import cn.com.zte.mobilebasedata.request.HttpUtil;

/**
 *	获取与我有关的所有会议所在日期接口
 * @author LangK
 *
 */
public class GetUserRelevantMeetingDatesRequest extends AppHttpRequest{


	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1056878707687130274L;
	
	
	/**
	 * 获取与我有关的所有会议所在日期接口
	 */
	public GetUserRelevantMeetingDatesRequest(Context context, boolean httpsFlag) {
		super(context, httpsFlag);
		setWebServiceMethod(HttpUtil.MyEmeetingInterface);
		setC(HttpUtil.GetUserRelevantMeetingDates);
	}
	
	/**
	 * 获取与我有关的所有会议所在日期接口
	 */
	public GetUserRelevantMeetingDatesRequest(Context context) {
		super(context, HttpUtil.HTTPSFLAG);
		setWebServiceMethod(HttpUtil.MyEmeetingInterface);
		setC(HttpUtil.GetUserRelevantMeetingDates);
	}
}
