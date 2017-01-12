package cn.com.zte.emeeting.app.response.instrument;

import java.util.Map;

import cn.com.zte.emeeting.app.response.entity.MeetingInfo;
import cn.com.zte.mobilebasedata.entity.AppReturnData;

import com.google.gson.annotations.Expose;


/** 
 * 获取会议详细信息接口
 * @author LangK
 * */
public class GetMeetingInfoResponse extends AppReturnData<MeetingInfo>{

	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1706141271522658141L;

}
