/**
 * 
 */
package cn.com.zte.emeeting.app.appservice;

import android.content.Context;
import cn.com.zte.android.app.appservice.BaseAppService;
import cn.com.zte.android.http.HttpManager;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.emeeting.app.request.instrument.GetCancelMeetingRoomRequest;
import cn.com.zte.emeeting.app.request.instrument.GetEndMeetingRoomRequest;
import cn.com.zte.emeeting.app.request.instrument.GetUserRelevantMeetingDatesRequest;
import cn.com.zte.emeeting.app.request.instrument.GetUserRelevantMeetingInfoRequest;
import cn.com.zte.emeeting.app.response.instrument.GetCancelMeetingRoomResponse;
import cn.com.zte.emeeting.app.response.instrument.GetEndMeetingRoomResponse;
import cn.com.zte.emeeting.app.response.instrument.GetUserRelevantMeetingDatesResponse;
import cn.com.zte.emeeting.app.response.instrument.GetUserRelevantMeetingInfoResponse;
import cn.com.zte.emeeting.app.util.NetWorkUtil;
import cn.com.zte.mobilebasedata.entity.PageInput;
import cn.com.zte.mobileemeeting.R;

/**
 * 该类为:我的会议逻辑处理类,所有会议 逻辑处理类
 * @author wf
 */
public class MyMeetingService extends BaseAppService {

	
	/**
	 * 该类构造方法:
	 * @param @param context
	 */
	public MyMeetingService(Context context) {
		super(context);
		
	}

	
	/** 获取指定类型,指定日期的 与我有关的会议也可获取所有会议
	 *  @param meetingType  0->所有与我有关的会议；1->我预定的会议；2->我组织的会议；3->我参加的会议
	 *  @param 非必填，此字段用于用户查询某一天所有相关的会议信息，如为null则表示查询服务器当前时间以后的相关的会议信息
	 * */
	public void getListMyMeeting(String meetingType,String meetingTime,PageInput pageinput,BaseAsyncHttpResponseHandler<GetUserRelevantMeetingInfoResponse> 
	responseHandler)
	{
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
			responseHandler.onPopUpHttpErrorDialogPre("", "",context.getString(R.string.no_network_prompt_str));
			return;
		}
		GetUserRelevantMeetingInfoRequest request = new GetUserRelevantMeetingInfoRequest(context, meetingType, meetingTime, pageinput);
		HttpManager.post(context, request, responseHandler);
	}
	
	/** 获取指定类型的会议
	 *  @param meetingType  0->所有与我有关的会议；1->我预定的会议；2->我组织的会议；3->我参加的会议
	 * */
	public void getListMyMeeting(String meetingType,PageInput pageinput,BaseAsyncHttpResponseHandler<GetUserRelevantMeetingInfoResponse> 
	responseHandler)
	{
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
			responseHandler.onPopUpHttpErrorDialogPre("", "", context.getString(R.string.no_network_prompt_str));
			return;
		}
		GetUserRelevantMeetingInfoRequest request = new GetUserRelevantMeetingInfoRequest(context, meetingType, "", pageinput);
		HttpManager.post(context, request, responseHandler);
	}
	
	/** 退订会议*/
	public void cancelBookMeeting(String EmeetingId,String RoomId,BaseAsyncHttpResponseHandler<GetCancelMeetingRoomResponse> 
	responseHandler){
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
			responseHandler.onPopUpHttpErrorDialogPre("", "", context.getString(R.string.no_network_prompt_str));
			return;
		}
		GetCancelMeetingRoomRequest request = new GetCancelMeetingRoomRequest(context, EmeetingId, RoomId);
		HttpManager.post(context, request, responseHandler);
	}
	
	/** 结束会议*/
	public void stopMeeting(String EmeetingId,String RoomId,BaseAsyncHttpResponseHandler<GetEndMeetingRoomResponse> 
	responseHandler){
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
			responseHandler.onPopUpHttpErrorDialogPre("", "",context.getString(R.string.no_network_prompt_str));
			return;
		}
		GetEndMeetingRoomRequest request = new GetEndMeetingRoomRequest(context, EmeetingId, RoomId);
		HttpManager.post(context, request, responseHandler);
	}
	
	/** 获取与我相关会议所在日期*/
	public void getAllMeetingDate(BaseAsyncHttpResponseHandler<GetUserRelevantMeetingDatesResponse> 
	responseHandler){
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
			responseHandler.onPopUpHttpErrorDialogPre("", "",context.getString(R.string.no_network_prompt_str));
			return;
		}
		GetUserRelevantMeetingDatesRequest request = new GetUserRelevantMeetingDatesRequest(context);
		HttpManager.post(context, request, responseHandler);
	}
	
}
