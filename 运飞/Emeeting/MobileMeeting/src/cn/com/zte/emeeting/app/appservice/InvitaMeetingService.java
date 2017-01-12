package cn.com.zte.emeeting.app.appservice;

import android.content.Context;
import cn.com.zte.android.app.appservice.BaseAppService;
import cn.com.zte.android.http.HttpManager;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.emeeting.app.request.instrument.GetDoInvitaMeetingRequest;
import cn.com.zte.emeeting.app.request.instrument.GetInvitaMeetingRequest;
import cn.com.zte.emeeting.app.response.instrument.GetDoInvitaMeetingResponse;
import cn.com.zte.emeeting.app.response.instrument.GetInvitaMeetingResponse;

/**
 * 
 * @ClassName: InvitaMeetingService
 * @Description: TODO 【会议邀请逻辑处理类】
 * @author Pan.Jianbo
 * @date 2016年4月11日 下午4:02:53
 * 
 */
public class InvitaMeetingService extends BaseAppService {

	/** 上下文环境 */
	private Context mContext;

	public InvitaMeetingService(Context context) {
		super(context);
		this.mContext = context;
	}

	/**
	 * 3.2.9.4新增（14）-会议邀请接口
	 * 
	 * @param responseHand
	 * @param Type
	 * @param Number
	 * @param Name
	 */
	public void GetInvitaMeeting(
			BaseAsyncHttpResponseHandler<GetInvitaMeetingResponse> responseHand,
			String Type, String Number, String Name, String ID) {
		GetInvitaMeetingRequest invitaMettingRequest = new GetInvitaMeetingRequest(
				mContext, ID, Type, Name, Number);
		HttpManager.post(mContext, invitaMettingRequest, responseHand);
	}

	/**
	 * 3.2.9.8新增（16）- 会议室邀请确认接口
	 * 
	 * @param responseHand
	 * @param ID
	 * @param Number
	 */
	public void DoInvitaMeeting(
			BaseAsyncHttpResponseHandler<GetDoInvitaMeetingResponse> responseHand,
			String ID, String Number) {
		GetDoInvitaMeetingRequest doInvitaMeetingRequest = new GetDoInvitaMeetingRequest(
				mContext, ID, Number);
		HttpManager.post(mContext, doInvitaMeetingRequest, responseHand);
	}

}
