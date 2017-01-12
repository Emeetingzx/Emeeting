package cn.com.zte.emeeting.app.request.instrument;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.gson.annotations.Expose;

import cn.com.zte.mobilebasedata.entity.FilterInfo;
import cn.com.zte.mobilebasedata.request.AppHttpRequest;
import cn.com.zte.mobilebasedata.request.HttpUtil;

/**
 * 结束会议室接口请求类
 * @author LangK
 *
 */
public class GetEndMeetingRoomRequest extends AppHttpRequest{


	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1056878707687130274L;

	/**
	 * @param context
	 * @param EmeetingId 会议ID
	 * @param RoomId 会议室ID	
	   * 　　输入会议室ID，服务端会结束对应会议下对应会议室。
　　    * 　　不输入会议室ID，服务端会结束对应回一下所有会议室。
	 */
	public GetEndMeetingRoomRequest(Context context, boolean httpsFlag,String EmeetingId,String RoomId) {
		super(context, httpsFlag);
		setWebServiceMethod(HttpUtil.PublicInterface);
		setC(HttpUtil.EndMeetingRoom);
		FilterInfo filterInfo = new FilterInfo("EmeetingId",EmeetingId);
		FilterInfo filterInfo1 = new FilterInfo("RoomId",RoomId);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(filterInfo);
		list.add(filterInfo1);
		setF(list);
	}
	
	/**
	 * @param context
	 * @param EmeetingId 会议ID
	 * @param RoomId 会议室ID	
	 *	输入会议室ID，服务端会结束对应会议下对应会议室。
　　    * 　　不输入会议室ID，服务端会结束对应回一下所有会议室。
	 */
	public GetEndMeetingRoomRequest(Context context,String EmeetingId,String RoomId) {
		super(context, HttpUtil.HTTPSFLAG);
		setWebServiceMethod(HttpUtil.PublicInterface);
		setC(HttpUtil.EndMeetingRoom);
		FilterInfo filterInfo = new FilterInfo("EmeetingId",EmeetingId);
		FilterInfo filterInfo1 = new FilterInfo("RoomId",RoomId);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(filterInfo);
		list.add(filterInfo1);
		setF(list);
	}
}
