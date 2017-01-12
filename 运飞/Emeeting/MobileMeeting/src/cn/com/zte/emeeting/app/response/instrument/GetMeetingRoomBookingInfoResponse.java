package cn.com.zte.emeeting.app.response.instrument;

import java.util.List;

import cn.com.zte.emeeting.app.response.entity.MeetingRoomInfo;
import cn.com.zte.mobilebasedata.entity.AppReturnData;

import com.google.gson.annotations.Expose;


/** 
 * 获取某会议室某天预定情况详情信息接口请求结果接收处理类
 * @author LangK
 * */
public class GetMeetingRoomBookingInfoResponse extends AppReturnData<List<MeetingRoomInfo>>{

	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1706141271522658141L;

}
