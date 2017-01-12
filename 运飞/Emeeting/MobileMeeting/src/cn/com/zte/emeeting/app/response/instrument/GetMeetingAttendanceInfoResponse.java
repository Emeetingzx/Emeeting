package cn.com.zte.emeeting.app.response.instrument;

import java.util.List;
import java.util.Map;

import cn.com.zte.emeeting.app.response.entity.AdminAddValueInfo;
import cn.com.zte.emeeting.app.response.entity.MeetingAttendanceInfo;
import cn.com.zte.emeeting.app.response.entity.MeetingJoinInfo;
import cn.com.zte.mobilebasedata.entity.AppReturnData;

import com.google.gson.annotations.Expose;


/** 
 * 获取参会人员签到对象实体接口请求结果接收处理类
 * @author LangK
 * */
public class GetMeetingAttendanceInfoResponse extends AppReturnData<List<MeetingAttendanceInfo>>{

	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1707141271522658141L;

}
