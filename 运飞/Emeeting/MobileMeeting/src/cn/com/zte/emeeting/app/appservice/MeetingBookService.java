/**
 * 
 */
package cn.com.zte.emeeting.app.appservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import cn.com.zte.android.app.appservice.BaseAppService;
import cn.com.zte.android.http.HttpManager;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.emeeting.app.request.entity.ScreeningCondition;
import cn.com.zte.emeeting.app.request.instrument.GetDayMeetingRoomBookingInfoRequest;
import cn.com.zte.emeeting.app.request.instrument.GetEndMeetingRoomRequest;
import cn.com.zte.emeeting.app.request.instrument.GetLockBookingMeetingRoomRequest;
import cn.com.zte.emeeting.app.request.instrument.GetMeetingRoomBookingInfoRequest;
import cn.com.zte.emeeting.app.request.instrument.GetNearParkAddressInfoRequest;
import cn.com.zte.emeeting.app.request.instrument.GetServerTimeRequest;
import cn.com.zte.emeeting.app.request.instrument.GetSubmitBookingMeetingRoomRequest;
import cn.com.zte.emeeting.app.request.instrument.GetValidBookDateRequest;
import cn.com.zte.emeeting.app.response.entity.MeetingRoomInfo;
import cn.com.zte.emeeting.app.response.instrument.GetDayMeetingRoomBookingInfoResponse;
import cn.com.zte.emeeting.app.response.instrument.GetEndMeetingRoomResponse;
import cn.com.zte.emeeting.app.response.instrument.GetLockBookingMeetingRoomResponse;
import cn.com.zte.emeeting.app.response.instrument.GetMeetingRoomBookingInfoResponse;
import cn.com.zte.emeeting.app.response.instrument.GetMeetingRoomDetailResponse;
import cn.com.zte.emeeting.app.response.instrument.GetNearParkAddressInfoResponse;
import cn.com.zte.emeeting.app.response.instrument.GetServerTimeResponse;
import cn.com.zte.emeeting.app.response.instrument.GetSubmitBookingMeetingRoomResponse;
import cn.com.zte.emeeting.app.response.instrument.GetValidBookDateResponse;
import cn.com.zte.emeeting.app.util.NetWorkUtil;
import cn.com.zte.mobilebasedata.entity.FilterInfo;
import cn.com.zte.mobilebasedata.entity.PageInput;
import cn.com.zte.mobileemeeting.R;

/**
 * 该类为:会议预订(退订不在此)相关逻辑处理类
 * 
 * @author wf
 */
public class MeetingBookService extends BaseAppService {

	/**
	 * 该类构造方法:
	 * 
	 * @param @param context
	 */
	public MeetingBookService(Context context) {
		super(context);

	}

	/** 获取城市列表 */
	public List<String> getListCity() {
		List<String> listCities = new ArrayList<String>();
		String[] cities = { "深圳", "西安", "上海", "南京", "成都" };
		for (String c : cities) {
			listCities.add(c);
		}
		return listCities;
	}

	public List<MeetingRoomInfo> getMeetingroomListTmp() {
		List<MeetingRoomInfo> list = new ArrayList<MeetingRoomInfo>();
		for (int i = 0; i < 20; i++) {
			list.add(new MeetingRoomInfo());
		}
		return list;
	}

	/** 领导类型列表 */
	public List<String> getListLeader() {
		List<String> listLeaders = new ArrayList<String>();
		String[] cities = { "二层领导", "三层领导", "四层领导", "其他" };
		for (String c : cities) {
			listLeaders.add(c);
		}
		return listLeaders;
	}

	//
	// /** 获取会议室列表*/
	// public void getMeetingroomList(Map<String,String> filter,PageInput
	// pageinput,BaseAsyncHttpResponseHandler<GetDayMeetingRoomBookingInfoResponse>
	// responseHandler)
	// {
	// // String CityId,String ParkId,String BuildId,String QueryDate
	// String cityId = filter.get("CityId");
	// String parkId = filter.get("ParkId");
	// String buildId = filter.get("BuildId");
	// String queryDate = filter.get("QueryDate");
	// GetDayMeetingRoomBookingInfoRequest request = new
	// GetDayMeetingRoomBookingInfoRequest(context, cityId, parkId, buildId,
	// queryDate, pageinput);
	// HttpManager.post(context, request, responseHandler);
	// // List<MeetingRoomInfo> list = new ArrayList<MeetingRoomInfo>();
	// // for(int i = 0;i<20;i++)
	// // {
	// // list.add(new MeetingRoomInfo());
	// // }
	// // return list;
	// }
	
	/**
	 * 获取最大可预订时间
	 * @param handler
	 */
	public void getValidBookDate(BaseAsyncHttpResponseHandler<GetValidBookDateResponse> handler){
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
			handler.onPopUpHttpErrorDialogPre("", "", context.getString(R.string.no_network_prompt_str));
			return;
		}
		GetValidBookDateRequest request = new GetValidBookDateRequest(
				context);
		HttpManager.post(context, request, handler);
		
	}

	/** 锁定预订会议室 */
	public void lockMeetingRoom(
			String roomId,
			String startDate,
			String endDate,
			BaseAsyncHttpResponseHandler<GetLockBookingMeetingRoomResponse> responseHandler) {
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
			responseHandler.onPopUpHttpErrorDialogPre("", "", context.getString(R.string.no_network_prompt_str));
			return;
		}
		GetLockBookingMeetingRoomRequest request = new GetLockBookingMeetingRoomRequest(
				context, roomId, startDate, endDate);
		HttpManager.post(context, request, responseHandler);
	}

	/** 提交预定会议室 */
	public void submitBookMeeting(
			String emeetingId,
			String attendLeaderLevel,
			String memberNos,
			String meetingName,
			BaseAsyncHttpResponseHandler<GetSubmitBookingMeetingRoomResponse> responseHandler) {
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
			responseHandler.onPopUpHttpErrorDialogPre("", "", context.getString(R.string.no_network_prompt_str));
			return;
		}
		GetSubmitBookingMeetingRoomRequest request = new GetSubmitBookingMeetingRoomRequest(
				context, emeetingId, attendLeaderLevel, memberNos, meetingName);
		HttpManager.post(context, request, responseHandler);
	}

	/** 获取会议室列表 */
	public void getMeetingRoomList(
			PageInput pageInput,
			String meetingRoomId,
			String queryDate,
			String beginDate,
			String endDate,
			ScreeningCondition data,
			BaseAsyncHttpResponseHandler<GetMeetingRoomBookingInfoResponse> responseHandler) {
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
			responseHandler.onPopUpHttpErrorDialogPre("", "", context.getString(R.string.no_network_prompt_str));
			return;
		}
		GetMeetingRoomBookingInfoRequest request = new GetMeetingRoomBookingInfoRequest(
				context, pageInput, meetingRoomId, queryDate, beginDate,
				endDate, data);
		HttpManager.post(context, request, responseHandler);
	}

	/** 获取会议室详情 */
	public void getMeetingRoomDetail(
			PageInput pageInput,
			String meetingRoomId,
			String queryDate,
			String beginDate,
			String endDate,
			ScreeningCondition data,
			BaseAsyncHttpResponseHandler<GetMeetingRoomDetailResponse> responseHandler) {
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
			responseHandler.onPopUpHttpErrorDialogPre("", "", context.getString(R.string.no_network_prompt_str));
			return;
		}
		GetMeetingRoomBookingInfoRequest request = new GetMeetingRoomBookingInfoRequest(
				context, pageInput, meetingRoomId, queryDate, beginDate,
				endDate, data);
		HttpManager.post(context, request, responseHandler);
	}

	/** 获取服务器时间 */
	public void getServerData(
			BaseAsyncHttpResponseHandler<GetServerTimeResponse> responseHandler) {
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
			responseHandler.onPopUpHttpErrorDialogPre("", "", context.getString(R.string.no_network_prompt_str));
			return;
		}
		GetServerTimeRequest request = new GetServerTimeRequest(context);
		HttpManager.post(context, request, responseHandler);
	}

	/** 获取附近园区 */
	public void getNearPark(
			String locationStr,
			BaseAsyncHttpResponseHandler<GetNearParkAddressInfoResponse> responseHandler) {
		if (!NetWorkUtil.noNetworkPromptProcessing(context)) {
			responseHandler.onPopUpHttpErrorDialogPre("", "",context.getString(R.string.no_network_prompt_str));
			return;
		}
		GetNearParkAddressInfoRequest request = new GetNearParkAddressInfoRequest(
				context, locationStr);
		HttpManager.post(context, request, responseHandler);
	}
}
