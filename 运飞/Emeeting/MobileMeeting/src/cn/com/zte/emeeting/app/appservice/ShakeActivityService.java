package cn.com.zte.emeeting.app.appservice;

import android.content.Context;
import cn.com.zte.android.app.appservice.BaseAppService;
import cn.com.zte.android.http.HttpManager;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.emeeting.app.request.instrument.GetNearParkAddressInfoRequest;
import cn.com.zte.emeeting.app.request.instrument.GetNearParkMeetingRoomInfoRequest;
import cn.com.zte.emeeting.app.response.instrument.GetNearParkAddressInfoResponse;
import cn.com.zte.emeeting.app.response.instrument.GetNearParkMeetingRoomInfoResponse;
import cn.com.zte.emeeting.app.util.NetWorkUtil;
import cn.com.zte.mobilebasedata.entity.PageInput;
import cn.com.zte.mobileemeeting.R;

/**
 * 摇一摇界面逻辑
 * 
 * @author liu.huanbo
 * 
 */
public class ShakeActivityService extends BaseAppService {
	/** 上下文 */
	private Context context;

	public ShakeActivityService(Context context) {
		super(context);
		this.context = context;
	}

	/** 定位查询附近园区地址信息接口 */
	public void GetNearParkAddressInfo(
			BaseAsyncHttpResponseHandler<GetNearParkAddressInfoResponse> response,
			String LatitudeAndLongitude) {
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
			response.onPopUpHttpErrorDialogPre("", "", context.getString(R.string.no_network_prompt_str));
			return;
		}
		GetNearParkAddressInfoRequest request = new GetNearParkAddressInfoRequest(
				context, LatitudeAndLongitude);
		HttpManager.post(context, request, response);
	}

	/** 分页查询定位或选择园区可预订会议室信息接口 */
	public void GetNearParkMeetingRoomInfo(
			BaseAsyncHttpResponseHandler<GetNearParkMeetingRoomInfoResponse> response,
			String LatitudeAndLongitude, String AddressId, String MeetingTime,
			PageInput pageInput) {
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
			response.onPopUpHttpErrorDialogPre("", "", context.getString(R.string.no_network_prompt_str));
			return;
		}
		GetNearParkMeetingRoomInfoRequest request = new GetNearParkMeetingRoomInfoRequest(
				context, false, LatitudeAndLongitude, AddressId, MeetingTime,"Y",
				pageInput);
		HttpManager.post(context, request, response);
	}
	
}
