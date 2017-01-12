package cn.com.zte.emeeting.app.request.instrument;


import android.content.Context;

import com.google.gson.annotations.Expose;

import cn.com.zte.android.common.util.JsonUtil;
import cn.com.zte.emeeting.app.response.entity.MeetingInfo;
import cn.com.zte.mobilebasedata.request.AppHttpRequest;
import cn.com.zte.mobilebasedata.request.HttpUtil;

/**
 * 预定电话/视频会议桥会议室接口请求类
 * 
 * @author LangK
 * 
 */
public class GetReservePhoneOrVideoMeetingRoomRequest extends AppHttpRequest {

	@Expose(serialize = false, deserialize = false)
	private static final long serialVersionUID = 1056878707687130274L;

	/**
	 * 预定电话/视频会议桥会议室
	 * @param context
	 * @param httpsFlag
	 * @param info	预定电话/视频会议桥会议室
	 */
	public GetReservePhoneOrVideoMeetingRoomRequest(Context context,
			boolean httpsFlag, MeetingInfo info) {
		super(context, httpsFlag);
		setWebServiceMethod(HttpUtil.PublicInterface);
		setC(HttpUtil.ReservePhoneOrVideoMeetingRoom);
		if (info!=null) {
			setD(JsonUtil.toJson(info));
		}
	}

	/**
	 * 预定电话/视频会议桥会议室
	 * @param context
	 * @param httpsFlag
	 * @param info	预定电话/视频会议桥会议室
	 */
	public GetReservePhoneOrVideoMeetingRoomRequest(Context context, MeetingInfo info) {
		super(context, HttpUtil.HTTPSFLAG);
		setWebServiceMethod(HttpUtil.PublicInterface);
		setC(HttpUtil.ReservePhoneOrVideoMeetingRoom);
		if (info!=null) {
			setD(JsonUtil.toJson(info));
		}
	}
}
