package cn.com.zte.mobilebasedata.system.response;

import java.util.List;

import cn.com.zte.mobilebasedata.entity.AppReturnData;
import cn.com.zte.mobilebasedata.system.entity.SysLastUpdatetimeInfo;

import com.google.gson.annotations.Expose;


/** 
 * 【基础】获取数据更新时间接口请求结果接收处理类
 * @author LangK
 * */
public class GetLastUpdateTimeResponse extends AppReturnData<List<SysLastUpdatetimeInfo>>{

	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1706141271522658141L;

}
