package cn.com.zte.mobilebasedata.system.response;

import java.util.List;

import cn.com.zte.emeeting.app.database.entity.shared.DBMeetingRoomInfo;
import cn.com.zte.mobilebasedata.entity.AppReturnData;
import cn.com.zte.mobilebasedata.system.entity.SysLastUpdatetimeInfo;

import com.google.gson.annotations.Expose;


/** 
 * 【基础】获取会议室基础信息数据接口请求结果接收处理类
 * @author LangK
 * */
public class GetSysMeetingRoomInfoResponse extends AppReturnData<List<DBMeetingRoomInfo>>{

	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1706141271722658141L;

}
