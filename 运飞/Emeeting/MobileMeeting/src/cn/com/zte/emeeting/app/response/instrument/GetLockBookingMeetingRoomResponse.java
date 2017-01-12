package cn.com.zte.emeeting.app.response.instrument;

import java.util.Map;

import cn.com.zte.mobilebasedata.entity.AppReturnData;

import com.google.gson.annotations.Expose;


/** 
 * 【公共】锁定预定会议室接口请求结果接收处理类
 * @author LangK
 * */
public class GetLockBookingMeetingRoomResponse extends AppReturnData<Map<String,String>>{

	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1706141271522658141L;

}
