package cn.com.zte.emeeting.app.appservice;

import android.content.Context;
import cn.com.zte.android.app.appservice.BaseAppService;
import cn.com.zte.android.http.HttpManager;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.emeeting.app.request.instrument.GetReservePhoneOrVideoMeetingRoomRequest;
import cn.com.zte.emeeting.app.response.entity.MeetingInfo;
import cn.com.zte.emeeting.app.response.instrument.GetReservePhoneOrVideoMeetingRoomResponse;
import cn.com.zte.emeeting.app.util.NetWorkUtil;
import cn.com.zte.mobileemeeting.R;

/**
 * 电话视频会议逻辑
 * 
 * @author liu.huanbo
 * 
 */
public class PhoneFragmentService extends BaseAppService {
	private Context context;

	public PhoneFragmentService(Context context) {
		super(context);

		this.context = context;
	}

	/** 预定电话与视频会议接口 */
	public void ReservePhoneOrVideoMeetingRoom(
			BaseAsyncHttpResponseHandler<GetReservePhoneOrVideoMeetingRoomResponse> handler,
			MeetingInfo info) {
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
				handler.onPopUpHttpErrorDialogPre("", "", context.getString(R.string.no_network_prompt_str));
			return;
		}
		GetReservePhoneOrVideoMeetingRoomRequest request = new GetReservePhoneOrVideoMeetingRoomRequest(
				context, info);
		HttpManager.post(context, request, handler);
	}
}
