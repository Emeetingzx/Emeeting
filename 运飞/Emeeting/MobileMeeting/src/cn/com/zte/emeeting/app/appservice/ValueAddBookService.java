package cn.com.zte.emeeting.app.appservice;

import android.content.Context;
import cn.com.zte.android.app.appservice.BaseAppService;
import cn.com.zte.android.http.HttpManager;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.emeeting.app.request.instrument.GetAddValueServiceOperateRequest;
import cn.com.zte.emeeting.app.request.instrument.GetAddValueServiceRegionInfosRequest;
import cn.com.zte.emeeting.app.request.instrument.GetFoodAndRefreshmentsInfosRequest;
import cn.com.zte.emeeting.app.request.instrument.GetMyAddValueServiceInfosRequest;
import cn.com.zte.emeeting.app.request.instrument.GetReserveAddValueServiceRequest;
import cn.com.zte.emeeting.app.request.instrument.GetReservePhoneOrVideoMeetingRoomRequest;
import cn.com.zte.emeeting.app.response.entity.MeetingInfo;
import cn.com.zte.emeeting.app.response.instrument.GetAddValueServiceOperateResponse;
import cn.com.zte.emeeting.app.response.instrument.GetAddValueServiceRegionInfosResponse;
import cn.com.zte.emeeting.app.response.instrument.GetFoodAndRefreshmentsInfosResponse;
import cn.com.zte.emeeting.app.response.instrument.GetMyAddValueServiceInfosResponse;
import cn.com.zte.emeeting.app.response.instrument.GetReserveAddValueServiceResponse;
import cn.com.zte.emeeting.app.response.instrument.GetReservePhoneOrVideoMeetingRoomResponse;
import cn.com.zte.emeeting.app.util.NetWorkUtil;
import cn.com.zte.mobilebasedata.entity.PageInput;
import cn.com.zte.mobileemeeting.R;

/**
 * 增值服务预定
 * 
 * @author LangK
 * 
 */
public class ValueAddBookService extends BaseAppService {
	private Context context;

	public ValueAddBookService(Context context) {
		super(context);
		this.context = context;
	}

	/**
	 * 增值服务预定网络请求 
	 * @param handler
	 * @param time	预定时间
	 * @param phone	联系电话
	 * @param address 服务地址
	 * @param foodids	点心和水果ID
	 */
	public void bookValueAddService(
			BaseAsyncHttpResponseHandler<GetReserveAddValueServiceResponse> handler,
			String time,String regionid,String phone,String address,String foodids) {
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
			handler.onPopUpHttpErrorDialogPre("", "", context.getString(R.string.no_network_prompt_str));
			return;
		}
		GetReserveAddValueServiceRequest request = new GetReserveAddValueServiceRequest(context,time,regionid,address, phone, foodids);
		HttpManager.post(context, request, handler);
	}
	
	/**
	 * 获取点心和水果
	 * @param handler
	 * @param input
	 */
	public void getFoodAndRefreshments(BaseAsyncHttpResponseHandler<GetFoodAndRefreshmentsInfosResponse> handler,PageInput input){
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
			handler.onPopUpHttpErrorDialogPre("", "", context.getString(R.string.no_network_prompt_str));
			return;
		}
		GetFoodAndRefreshmentsInfosRequest request = new GetFoodAndRefreshmentsInfosRequest(context, input);
		HttpManager.post(context, request, handler);
	}
	
	/**
	 * 获取增值服务地区
	 * @param handler
	 * @param input
	 */
	public void getAddValueRegion(BaseAsyncHttpResponseHandler<GetAddValueServiceRegionInfosResponse> handler){
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
			handler.onPopUpHttpErrorDialogPre("", "", context.getString(R.string.no_network_prompt_str));
			return;
		}
		GetAddValueServiceRegionInfosRequest request = new GetAddValueServiceRegionInfosRequest(context);
		HttpManager.post(context, request, handler);
	}
	
	/**
	 * 获取我的增值服务
	 * @param handler
	 * @param input
	 */
	public void getMyselfAddValueService(BaseAsyncHttpResponseHandler<GetMyAddValueServiceInfosResponse> handler,PageInput input){
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
			handler.onPopUpHttpErrorDialogPre("", "", context.getString(R.string.no_network_prompt_str));
			return;
		}
		GetMyAddValueServiceInfosRequest request = new GetMyAddValueServiceInfosRequest(context, input);
		HttpManager.post(context, request, handler);
	}
	
	/**
	 * 用户操作退订服务订单
	 * @param handler
	 * @param id
	 */
	public void operateAddValue(BaseAsyncHttpResponseHandler<GetAddValueServiceOperateResponse> handler,String id){
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
			handler.onPopUpHttpErrorDialogPre("", "", context.getString(R.string.no_network_prompt_str));
			return;
		}
		GetAddValueServiceOperateRequest request = new GetAddValueServiceOperateRequest(context, id, "1", "1");
		HttpManager.post(context, request, handler);
	}
	
}
