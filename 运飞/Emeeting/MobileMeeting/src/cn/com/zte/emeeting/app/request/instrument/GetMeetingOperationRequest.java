package cn.com.zte.emeeting.app.request.instrument;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.gson.annotations.Expose;

import cn.com.zte.mobilebasedata.entity.FilterInfo;
import cn.com.zte.mobilebasedata.request.AppHttpRequest;
import cn.com.zte.mobilebasedata.request.HttpUtil;

/**
 * 会议操作接口请求类
 * @author LangK
 *
 */
public class GetMeetingOperationRequest extends AppHttpRequest{


	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1056878606687130274L;

	

	/**
	 *  会议操作接口请求类
	 * @param context
	 * @param httpsFlag
	 * @param ID 会议ID	
	 * @param Number	终端号码
	 * @param TermId	终端ID
	 * @param OperationType	操作类型 0:挂断1：呼叫2：静音3：取消静音4 ：删除
	 */
	public GetMeetingOperationRequest(Context context, boolean httpsFlag,String ID,String Number,String TermId,String OperationType,String type) {
		super(context, httpsFlag);
		setWebServiceMethod(HttpUtil.MeetingControl);
		setC(HttpUtil.MeetingOperation);
		FilterInfo idFilter = new FilterInfo("ID",ID);
		FilterInfo nameFilter = new FilterInfo("Number",Number);
		FilterInfo numberFilter = new FilterInfo("TermId",TermId);
		FilterInfo typeFilter = new FilterInfo("Type",type);
		FilterInfo operationFilter = new FilterInfo("OperationType",OperationType);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(idFilter);
		list.add(typeFilter);
		list.add(nameFilter);
		list.add(operationFilter);
		list.add(numberFilter);
		setF(list);
	}
	
	/**
	 *  会议操作接口请求类
	 * @param context
	* @param ID 会议ID	
	 * @param Number	终端号码
	 * @param TermId	终端ID
	 * @param OperationType	操作类型 0:挂断1：呼叫2：静音3：取消静音4 ：删除
	 */
	public GetMeetingOperationRequest(Context context,String ID,String Number,String TermId,String OperationType,String type) {
		super(context, HttpUtil.HTTPSFLAG);
		setWebServiceMethod(HttpUtil.MeetingControl);
		setC(HttpUtil.MeetingOperation);
		FilterInfo idFilter = new FilterInfo("ID",ID);
		FilterInfo nameFilter = new FilterInfo("Number",Number);
		FilterInfo numberFilter = new FilterInfo("TermId",TermId);
		FilterInfo typeFilter = new FilterInfo("Type",type);
		FilterInfo operationFilter = new FilterInfo("OperationType",OperationType);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(idFilter);
		list.add(typeFilter);
		list.add(nameFilter);
		list.add(operationFilter);
		list.add(numberFilter);
		setF(list);
	}
}
