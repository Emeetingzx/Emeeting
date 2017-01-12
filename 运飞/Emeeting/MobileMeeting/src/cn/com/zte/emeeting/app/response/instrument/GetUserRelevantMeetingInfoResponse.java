package cn.com.zte.emeeting.app.response.instrument;

import java.util.List;
import java.util.Map;

import cn.com.zte.emeeting.app.response.entity.MeetingInfo;
import cn.com.zte.mobilebasedata.entity.AppReturnData;

import com.google.gson.annotations.Expose;


/** 
 * 获取与我相关的会议接口
 * @author LangK
 * */
public class GetUserRelevantMeetingInfoResponse extends AppReturnData<List<MeetingInfo>>{

	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1706141271522658141L;

}
