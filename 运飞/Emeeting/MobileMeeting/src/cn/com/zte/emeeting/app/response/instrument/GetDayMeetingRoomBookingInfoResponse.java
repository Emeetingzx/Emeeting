package cn.com.zte.emeeting.app.response.instrument;

import java.util.List;

import cn.com.zte.emeeting.app.response.entity.MeetingRoomInfo;
import cn.com.zte.mobilebasedata.entity.AppReturnData;

import com.google.gson.annotations.Expose;


/** 
 * 【分页】获取指定条件会议室预定情况列表接口
 * @author LangK
 * */
public class GetDayMeetingRoomBookingInfoResponse extends AppReturnData<List<MeetingRoomInfo>>{

	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1706141271522658141L;

}
