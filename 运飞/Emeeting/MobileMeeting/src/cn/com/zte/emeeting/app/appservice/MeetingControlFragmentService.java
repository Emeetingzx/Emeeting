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
import cn.com.zte.emeeting.app.request.instrument.GetMeetingJoinInfoRequest;
import cn.com.zte.emeeting.app.request.instrument.GetMeetingOperationRequest;
import cn.com.zte.emeeting.app.request.instrument.GetMeetingProlongInfoRequest;
import cn.com.zte.emeeting.app.request.instrument.GetMeetingProlongRequest;
import cn.com.zte.emeeting.app.request.instrument.GetUserRelevantMeetingDatesRequest;
import cn.com.zte.emeeting.app.request.instrument.GetUserRelevantMeetingInfoRequest;
import cn.com.zte.emeeting.app.response.instrument.GetCancelMeetingRoomResponse;
import cn.com.zte.emeeting.app.response.instrument.GetEndMeetingRoomResponse;
import cn.com.zte.emeeting.app.response.instrument.GetMeetingJoinInfoResponse;
import cn.com.zte.emeeting.app.response.instrument.GetMeetingOperationResponse;
import cn.com.zte.emeeting.app.response.instrument.GetMeetingProlongInfoResponse;
import cn.com.zte.emeeting.app.response.instrument.GetMeetingProlongResponse;
import cn.com.zte.emeeting.app.response.instrument.GetUserRelevantMeetingDatesResponse;
import cn.com.zte.emeeting.app.response.instrument.GetUserRelevantMeetingInfoResponse;
import cn.com.zte.emeeting.app.util.NetWorkUtil;
import cn.com.zte.mobilebasedata.entity.PageInput;
import cn.com.zte.mobilebasedata.request.HttpUtil;
import cn.com.zte.mobileemeeting.R;

/**
 * 该类为:会控主界面逻辑处理类
 * 
 * @author LangK
 */
public class MeetingControlFragmentService extends BaseAppService {

	/**
	 * 该类构造方法:
	 * 
	 * @param @param context
	 */
	public MeetingControlFragmentService(Context context) {
		super(context);

	}

	/** 获取该会议下的所有会场信息 */
	public void getAllMeetingJoinInfo(
			BaseAsyncHttpResponseHandler<GetMeetingJoinInfoResponse> responseHandler,
			String id) {
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
			responseHandler.onPopUpHttpErrorDialogPre("", "",
					context.getString(R.string.no_network_prompt_str));
			return;
		}
		GetMeetingJoinInfoRequest request = new GetMeetingJoinInfoRequest(
				context, id);
		HttpManager.post(context, request, responseHandler);
	}
	
	/** 获取会议延长信息请求 */
	public void extendMeeting(
			BaseAsyncHttpResponseHandler<GetMeetingProlongInfoResponse> responseHandler,
			String id) {
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
			responseHandler.onPopUpHttpErrorDialogPre("", "",
					context.getString(R.string.no_network_prompt_str));
			return;
		}
		GetMeetingProlongInfoRequest request = new GetMeetingProlongInfoRequest(
				context, id);
		HttpManager.post(context, request, responseHandler);
	}
	
	/** 获取会议延长信息请求 */
	public void doExtendMeeting(
			BaseAsyncHttpResponseHandler<GetMeetingProlongResponse> responseHandler,
			String id) {
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
			responseHandler.onPopUpHttpErrorDialogPre("", "",
					context.getString(R.string.no_network_prompt_str));
			return;
		}
		GetMeetingProlongRequest request = new GetMeetingProlongRequest(
				context, id);
		HttpManager.post(context, request, responseHandler);
	}
	
	
	/**
	 *  会议操作接口请求
	 * @param responseHandler
	 * @param ID 会议ID	
	 * @param Number	终端号码
	 * @param TermId	终端ID
	 * @param OperationType	操作类型 0:挂断1：呼叫2：静音3：取消静音 4 ：删除
	 * @param type 终端类型
	 */
	public void operationMeetingJoin(
			BaseAsyncHttpResponseHandler<GetMeetingOperationResponse> responseHandler,
			String ID,String Number,String TermId,String OperationType,String type) {
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
			responseHandler.onPopUpHttpErrorDialogPre("", "",
					context.getString(R.string.no_network_prompt_str));
			return;
		}
		HttpManager.setSoTimeout(HttpUtil.CallSockctTimeOut);
		GetMeetingOperationRequest request = new GetMeetingOperationRequest(
				context, ID, Number, TermId, OperationType,type);
		HttpManager.post(context, request, responseHandler);
		HttpManager.setSoTimeout(HttpUtil.SocketTimeOut);
	}
	

}
