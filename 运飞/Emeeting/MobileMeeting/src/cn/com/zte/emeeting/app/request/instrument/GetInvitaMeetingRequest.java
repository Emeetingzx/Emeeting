package cn.com.zte.emeeting.app.request.instrument;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.gson.annotations.Expose;

import cn.com.zte.mobilebasedata.entity.FilterInfo;
import cn.com.zte.mobilebasedata.request.AppHttpRequest;
import cn.com.zte.mobilebasedata.request.HttpUtil;

/**
 * 会议邀请接口请求类
 * @author LangK
 *
 */
public class GetInvitaMeetingRequest extends AppHttpRequest{


	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1056878606687130274L;

	/**
	 * 会议邀请接口请求类
	 * @param context
	 * @param httpsFlag
	 * @param Type	邀请的类型 0：会议室 1：手机联系人 2：固话
	 * @param Name	联系人姓名
	 * @param Number	会议室GK号码/手机号/固话号 
	 */
	public GetInvitaMeetingRequest(Context context, boolean httpsFlag,String id,String Type,String Name,String Number) {
		super(context, httpsFlag);
		setWebServiceMethod(HttpUtil.MeetingControl);
		setC(HttpUtil.InvitaMeeting);
		FilterInfo idFilter = new FilterInfo("ID",id);
		FilterInfo typeFilter = new FilterInfo("Type",Type);
		FilterInfo nameFilter = new FilterInfo("Name",Name);
		FilterInfo numberFilter = new FilterInfo("Number",Number);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(typeFilter);
		list.add(nameFilter);
		list.add(numberFilter);
		list.add(idFilter);
		setF(list);
	}
	
	/**
	 * 会议邀请接口请求类
	 * @param context
	 * @param Type	邀请的类型 0：会议室 1：手机联系人 2：固话
	 * @param Name	联系人姓名
	 * @param Number	会议室GK号码/手机号/固话号 
	 */
	public GetInvitaMeetingRequest(Context context,String id,String Type,String Name,String Number) {
		super(context, HttpUtil.HTTPSFLAG);
		setWebServiceMethod(HttpUtil.MeetingControl);
		setC(HttpUtil.InvitaMeeting);
		FilterInfo idFilter = new FilterInfo("ID",id);
		FilterInfo typeFilter = new FilterInfo("Type",Type);
		FilterInfo nameFilter = new FilterInfo("Name",Name);
		FilterInfo numberFilter = new FilterInfo("Number",Number);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(typeFilter);
		list.add(nameFilter);
		list.add(numberFilter);
		list.add(idFilter);
		setF(list);
	}
}
