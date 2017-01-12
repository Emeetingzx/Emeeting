package cn.com.zte.emeeting.app.response.instrument;

import java.util.List;
import java.util.Map;

import cn.com.zte.emeeting.app.database.entity.shared.DBMeetingRoomAddress;
import cn.com.zte.mobilebasedata.entity.AppReturnData;

import com.google.gson.annotations.Expose;


/** 
 * 获取会议室预定情况详细信息接口请求结果接收处理类
 * @author LangK
 * */
public class GetNearParkAddressInfoResponse extends AppReturnData<List<DBMeetingRoomAddress>>{

	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1706141271522658141L;

}
