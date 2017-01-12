package cn.com.zte.emeeting.app.appservice;

import android.content.Context;
import cn.com.zte.android.app.appservice.BaseAppService;
import cn.com.zte.android.http.HttpManager;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.emeeting.app.request.instrument.GetMeetingAttendanceInfoRequest;
import cn.com.zte.emeeting.app.response.instrument.GetMeetingAttendanceInfoResponse;
import cn.com.zte.emeeting.app.util.NetWorkUtil;
import cn.com.zte.mobileemeeting.R;

/**
 * 签到表逻辑处理类
 * @author zhangli
 *
 */
public class CheckedInTableService extends BaseAppService{

	public CheckedInTableService(Context context) {
		super(context);
	} 

	/**
	 * 
	 * @param MeetingId 会议Id
	 * @param responseHandler 
	 */
	public void getCheckedTableInfo(String MeetingId,BaseAsyncHttpResponseHandler<GetMeetingAttendanceInfoResponse> responseHandler){
		
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
			responseHandler.onPopUpHttpErrorDialogPre("", "", context.getString(R.string.no_network_prompt_str));
			return;
		}
		
		GetMeetingAttendanceInfoRequest request = new GetMeetingAttendanceInfoRequest(context, MeetingId);
		HttpManager.post(context, request, responseHandler);
	}
	
}
