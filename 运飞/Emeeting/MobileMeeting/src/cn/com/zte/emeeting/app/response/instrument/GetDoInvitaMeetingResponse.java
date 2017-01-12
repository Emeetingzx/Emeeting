package cn.com.zte.emeeting.app.response.instrument;

import java.util.List;

import cn.com.zte.emeeting.app.response.entity.MeetingRoomInfo;
import cn.com.zte.mobilebasedata.entity.AppReturnData;

import com.google.gson.annotations.Expose;


/** 
 * 锁定会议室时获取会议室详细信息,包含其中的会议信息列表,以用来显示哪些时间是可用的.
 * @author LangK
 * */
public class GetDoInvitaMeetingResponse extends AppReturnData<String>{

	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1706141271522658141L;

}
