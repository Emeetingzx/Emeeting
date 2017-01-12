package cn.com.zte.emeeting.app.request.instrument;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.gson.annotations.Expose;

import cn.com.zte.mobilebasedata.entity.FilterInfo;
import cn.com.zte.mobilebasedata.request.AppHttpRequest;
import cn.com.zte.mobilebasedata.request.HttpUtil;

/**
 * 会议室邀请确认接口请求类
 * 
 * @author LangK
 * 
 */
public class GetDoInvitaMeetingRequest extends AppHttpRequest {

	@Expose(serialize = false, deserialize = false)
	private static final long serialVersionUID = 1056878607687130274L;

	
	
	/**
	 * 会议室邀请确认接口
	 * @param context
	 * @param httpsFlag
	 * @param ID	会议ID
	 * @param Number	终端编号	
	 */
	public GetDoInvitaMeetingRequest(Context context,
			boolean httpsFlag, String ID,String Number) {
		super(context, httpsFlag);
		setWebServiceMethod(HttpUtil.MeetingControl);
		setC(HttpUtil.DoInvitaMeeting);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(new FilterInfo("ID", ID));
		list.add(new FilterInfo("Number", Number));
		setF(list);

	}
	
	/**
	 * 会议室邀请确认接口
	 * @param context
	 * @param httpsFlag
	 * @param ID	会议ID
	 * @param Number	终端编号	
	 */
	public GetDoInvitaMeetingRequest(Context context, String ID,String Number) {
		super(context, HttpUtil.HTTPSFLAG);
		setWebServiceMethod(HttpUtil.MeetingControl);
		setC(HttpUtil.DoInvitaMeeting);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(new FilterInfo("ID", ID));
		list.add(new FilterInfo("Number", Number));
		setF(list);

	}
	
}
