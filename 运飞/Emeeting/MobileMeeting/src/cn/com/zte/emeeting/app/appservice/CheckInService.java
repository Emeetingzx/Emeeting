package cn.com.zte.emeeting.app.appservice;

import android.content.Context;
import cn.com.zte.android.app.appservice.BaseAppService;
import cn.com.zte.android.http.HttpManager;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.emeeting.app.request.instrument.GetAttendanceOperationRequest;
import cn.com.zte.emeeting.app.response.instrument.GetAttendanceOperationResponse;
import cn.com.zte.emeeting.app.util.NetWorkUtil;
import cn.com.zte.mobileemeeting.R;

public class CheckInService extends BaseAppService{

	public CheckInService(Context context) {
		super(context);
	}
	
	/**
	 * 签到
	 * @param context 
	 * @param MeetingId 会议id
	 * @param CodeInfo 扫码信息
	 * @param handler
	 */
	public void CheckIn(Context context,String MeetingId,String CodeInfo,BaseAsyncHttpResponseHandler<GetAttendanceOperationResponse> handler){
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
			handler.onPopUpHttpErrorDialogPre("", "", context.getString(R.string.no_network_prompt_str));
			return;
		}
		GetAttendanceOperationRequest request = new GetAttendanceOperationRequest(context, CodeInfo, MeetingId);
		HttpManager.post(context, request, handler);
	}
}
