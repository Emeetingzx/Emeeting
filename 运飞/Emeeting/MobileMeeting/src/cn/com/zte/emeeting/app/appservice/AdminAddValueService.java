package cn.com.zte.emeeting.app.appservice;

import android.content.Context;
import cn.com.zte.android.app.appservice.BaseAppService;
import cn.com.zte.android.http.HttpManager;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.emeeting.app.request.instrument.GetAddValueServiceOperateRequest;
import cn.com.zte.emeeting.app.request.instrument.GetAdminAddValueServiceInfosRequest;
import cn.com.zte.emeeting.app.request.instrument.GetFoodAndRefreshmentsInfosRequest;
import cn.com.zte.emeeting.app.request.instrument.GetMyAddValueServiceInfosRequest;
import cn.com.zte.emeeting.app.request.instrument.GetReserveAddValueServiceRequest;
import cn.com.zte.emeeting.app.request.instrument.GetReservePhoneOrVideoMeetingRoomRequest;
import cn.com.zte.emeeting.app.response.entity.MeetingInfo;
import cn.com.zte.emeeting.app.response.instrument.GetAddValueServiceOperateResponse;
import cn.com.zte.emeeting.app.response.instrument.GetAdminAddValueServiceInfosResponse;
import cn.com.zte.emeeting.app.response.instrument.GetFoodAndRefreshmentsInfosResponse;
import cn.com.zte.emeeting.app.response.instrument.GetMyAddValueServiceInfosResponse;
import cn.com.zte.emeeting.app.response.instrument.GetReserveAddValueServiceResponse;
import cn.com.zte.emeeting.app.response.instrument.GetReservePhoneOrVideoMeetingRoomResponse;
import cn.com.zte.emeeting.app.util.NetWorkUtil;
import cn.com.zte.mobilebasedata.entity.PageInput;
import cn.com.zte.mobileemeeting.R;

/**
 * 增值服务管理员界面
 * 
 * @author LangK
 * 
 */
public class AdminAddValueService extends BaseAppService {
	private Context context;

	public AdminAddValueService(Context context) {
		super(context);
		this.context = context;
	}

	/**
	 * 获取待处理增值服务
	 * @param handler
	 * @param input
	 * 常量为 1
	 */
	public void getUnProcessAddValueService(BaseAsyncHttpResponseHandler<GetAdminAddValueServiceInfosResponse> handler,PageInput input){
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
			handler.onPopUpHttpErrorDialogPre("", "", context.getString(R.string.no_network_prompt_str));
			return;
		}
		GetAdminAddValueServiceInfosRequest request = new GetAdminAddValueServiceInfosRequest(context,"1",input);
		HttpManager.post(context, request, handler);
	}
	
	/**
	 * 获取已完成增值服务
	 * @param handler
	 * @param input
	 * 常量为 3
	 */
	public void getDoneAddValueService(BaseAsyncHttpResponseHandler<GetAdminAddValueServiceInfosResponse> handler,PageInput input){
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
			handler.onPopUpHttpErrorDialogPre("", "", context.getString(R.string.no_network_prompt_str));
			return;
		}
		GetAdminAddValueServiceInfosRequest request = new GetAdminAddValueServiceInfosRequest(context,"3",input);
		HttpManager.post(context, request, handler);
	}
	/**
	 * 获取已接单增值服务
	 * @param handler
	 * @param input
	 * 常量为 2
	 */
	public void getReceiveAddValueService(BaseAsyncHttpResponseHandler<GetAdminAddValueServiceInfosResponse> handler,PageInput input){
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
			handler.onPopUpHttpErrorDialogPre("", "", context.getString(R.string.no_network_prompt_str));
			return;
		}
		GetAdminAddValueServiceInfosRequest request = new GetAdminAddValueServiceInfosRequest(context,"2",input);
		HttpManager.post(context, request, handler);
	}
	
	
	/**
	 * 管理员操作服务订单
	 * @param handler
	 * @param id
	 * @param operate  2管理员受理（接单）3管理员提交完成（服务）4管理员退单
	 */
	public void operateAddValue(BaseAsyncHttpResponseHandler<GetAddValueServiceOperateResponse> handler,String id,String operate){
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
			handler.onPopUpHttpErrorDialogPre("", "", context.getString(R.string.no_network_prompt_str));
			return;
		}
		GetAddValueServiceOperateRequest request = new GetAddValueServiceOperateRequest(context, id, "2",operate);
		HttpManager.post(context, request, handler);
	}
}
