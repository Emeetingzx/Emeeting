package cn.com.zte.emeeting.app.response.instrument;

import java.util.Map;

import cn.com.zte.emeeting.app.response.entity.MeetingOperationInfo;
import cn.com.zte.mobilebasedata.entity.AppReturnData;

import com.google.gson.annotations.Expose;


/** 
 * 【公共】退订会议室接口请求结果接收处理类
 * @author LangK
 * */
public class GetCancelMeetingRoomResponse extends AppReturnData<MeetingOperationInfo>{

	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1706141271522658141L;

}
