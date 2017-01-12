package cn.com.zte.emeeting.app.response.instrument;

import java.util.List;

import cn.com.zte.emeeting.app.response.entity.AddValueRegionInfo;
import cn.com.zte.mobilebasedata.entity.AppReturnData;
import com.google.gson.annotations.Expose;


/** 
 * 获取增值服务地区信息接口结果接收处理类
 * @author LangK
 * */
public class GetAddValueServiceRegionInfosResponse extends AppReturnData<List<AddValueRegionInfo>>{

	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1706141271522658141L;

}
