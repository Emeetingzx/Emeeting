package cn.com.zte.emeeting.app.appservice;

import android.content.Context;
import cn.com.zte.android.app.appservice.BaseAppService;
import cn.com.zte.android.http.HttpManager;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.emeeting.app.request.instrument.GetMeetingInfoRequest;
import cn.com.zte.emeeting.app.response.instrument.GetMeetingInfoResponse;
import cn.com.zte.emeeting.app.util.NetWorkUtil;
import cn.com.zte.mobileemeeting.R;

/**
 * 视频会议详细信息逻辑处理类
 * 
 * @author liu.huanbo
 * 
 */
public class PhoneMeetingDetailService extends BaseAppService {

	public PhoneMeetingDetailService(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
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
}
