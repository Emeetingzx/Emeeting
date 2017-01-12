package cn.com.zte.emeeting.app.response.instrument;

import java.util.List;
import java.util.Map;

import cn.com.zte.emeeting.app.response.entity.AdminAddValueInfo;
import cn.com.zte.emeeting.app.response.entity.MeetingJoinInfo;
import cn.com.zte.mobilebasedata.entity.AppReturnData;

import com.google.gson.annotations.Expose;


/** 
 * 获取参会的会议室/人员对象实体接口请求结果接收处理类
 * @author LangK
 * */
public class GetMeetingJoinInfoResponse extends AppReturnData<List<MeetingJoinInfo>>{

	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1707141271522658141L;

}
