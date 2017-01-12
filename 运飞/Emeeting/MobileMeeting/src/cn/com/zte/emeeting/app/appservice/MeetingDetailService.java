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
import cn.com.zte.emeeting.app.request.instrument.GetMeetingInfoRequest;
import cn.com.zte.emeeting.app.response.instrument.GetCancelMeetingRoomResponse;
import cn.com.zte.emeeting.app.response.instrument.GetEndMeetingRoomResponse;
import cn.com.zte.emeeting.app.response.entity.MeetingRoomInfo;
import cn.com.zte.emeeting.app.response.instrument.GetMeetingInfoResponse;
import cn.com.zte.emeeting.app.response.instrument.GetUserRelevantMeetingInfoResponse;
import cn.com.zte.emeeting.app.util.NetWorkUtil;
import cn.com.zte.mobileemeeting.R;

/**
 * 该类为:会议详细信息逻辑处理类
 * 
 * @author wf
 */
public class MeetingDetailService extends BaseAppService {

	public MeetingDetailService(Context context) {
		super(context);

	}

	/**
	 * 通过会议室id去本地获取会议室信息
	 * */
	public MeetingRoomInfo getMeetingRoomInfo(String meetingroomId) {
		// GetUserRelevantMeetingInfoRequest request = new
		// GetUserRelevantMeetingInfoRequest(context, meetingType, meetingTime,
		// pageinput);
		// HttpManager.post(context, request, responseHandler);
		return new MeetingRoomInfo();
	}

	/**
	 * 获取会议详细信息
	 * */
	public void getMeetingDetail(String MeetingId,
			BaseAsyncHttpResponseHandler<GetMeetingInfoResponse> responseHandler) {
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
			responseHandler.onPopUpHttpErrorDialogPre("", "", context.getString(R.string.no_network_prompt_str));
			return;
		}
		GetMeetingInfoRequest request = new GetMeetingInfoRequest(context,
				MeetingId);
		HttpManager.post(context, request, responseHandler);
	}

	/** 退订会议 */
	public void cancelBookMeeting(
			String EmeetingId,
			String RoomId,
			BaseAsyncHttpResponseHandler<GetCancelMeetingRoomResponse> responseHandler) {
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
			responseHandler.onPopUpHttpErrorDialogPre("", "", context.getString(R.string.no_network_prompt_str));
			return;
		}
		GetCancelMeetingRoomRequest request = new GetCancelMeetingRoomRequest(
				context, EmeetingId, RoomId);
		HttpManager.post(context, request, responseHandler);
	}

	/** 结束会议 */
	public void stopMeeting(
			String EmeetingId,
			String RoomId,
			BaseAsyncHttpResponseHandler<GetEndMeetingRoomResponse> responseHandler) {
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
			responseHandler.onPopUpHttpErrorDialogPre("", "", context.getString(R.string.no_network_prompt_str));
			return;
		}
		GetEndMeetingRoomRequest request = new GetEndMeetingRoomRequest(
				context, EmeetingId, RoomId);
		HttpManager.post(context, request, responseHandler);
	}
}
