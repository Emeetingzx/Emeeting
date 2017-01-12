package cn.com.zte.emeeting.app.request.instrument;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.gson.annotations.Expose;

import cn.com.zte.mobilebasedata.entity.FilterInfo;
import cn.com.zte.mobilebasedata.request.AppHttpRequest;
import cn.com.zte.mobilebasedata.request.HttpUtil;

/**
 * 提交预定会议室接口请求类
 * 
 * @author LangK
 * 
 */
public class GetSubmitBookingMeetingRoomRequest extends AppHttpRequest {

	@Expose(serialize = false, deserialize = false)
	private static final long serialVersionUID = 1056878707687130274L;

	/**
	 * 提交预定会议室接口请求类
	 * @param context
	 * @param httpsFlag
	 * @param EmeetingId
	 *            预定会议ID
	 * @param AttendLeaderLevel
	 *            参会领导层级
	 * @param MemberNos
	 *            参会人员数组
	 * @param MeetingName
	 *            会议主题名称
	 */
	public GetSubmitBookingMeetingRoomRequest(Context context,
			boolean httpsFlag, String EmeetingId, String AttendLeaderLevel,
			String MemberNos, String MeetingName) {
		super(context, httpsFlag);
		setWebServiceMethod(HttpUtil.PublicInterface);
		setC(HttpUtil.SubmitBookingMeetingRoom);
		FilterInfo emeetingIdFilter = new FilterInfo("EmeetingId", EmeetingId);
		FilterInfo attendLeaderLevelFilter = new FilterInfo(
				"AttendLeaderLevel", AttendLeaderLevel);
		FilterInfo memberNosFilter = new FilterInfo("MemberNos", MemberNos);
		FilterInfo meetingNameFilter = new FilterInfo("MeetingName",
				MeetingName);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(emeetingIdFilter);
		list.add(attendLeaderLevelFilter);
		list.add(memberNosFilter);
		list.add(meetingNameFilter);
		setF(list);
	}

	/**
	 * 提交预定会议室接口请求类
	 * @param context
	 * @param httpsFlag
	 * @param EmeetingId
	 *            预定会议ID
	 * @param AttendLeaderLevel
	 *            参会领导层级
	 * @param MemberNos
	 *            参会人员数组
	 * @param MeetingName
	 *            会议主题名称
	 */
	public GetSubmitBookingMeetingRoomRequest(Context context,
			String EmeetingId, String AttendLeaderLevel, String MemberNos,
			String MeetingName) {
		super(context, HttpUtil.HTTPSFLAG);
		setWebServiceMethod(HttpUtil.PublicInterface);
		setC(HttpUtil.SubmitBookingMeetingRoom);
		FilterInfo emeetingIdFilter = new FilterInfo("EmeetingId", EmeetingId);
		FilterInfo attendLeaderLevelFilter = new FilterInfo(
				"AttendLeaderLevel", AttendLeaderLevel);
		FilterInfo memberNosFilter = new FilterInfo("MemberNos", MemberNos);
		FilterInfo meetingNameFilter = new FilterInfo("MeetingName",
				MeetingName);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(emeetingIdFilter);
		list.add(attendLeaderLevelFilter);
		list.add(memberNosFilter);
		list.add(meetingNameFilter);
		setF(list);
	}
}
