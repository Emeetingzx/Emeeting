package cn.com.zte.emeeting.app.response.instrument;

import java.util.List;
import java.util.Map;

import cn.com.zte.emeeting.app.response.entity.FoodAndRefreshmentsInfo;
import cn.com.zte.emeeting.app.response.entity.MyAddValueInfo;
import cn.com.zte.mobilebasedata.entity.AppReturnData;

import com.google.gson.annotations.Expose;


/** 
 * 【分页】获取我的增值服务信息接口请求结果接收处理类
 * @author LangK
 * */
public class GetMyAddValueServiceInfosResponse extends AppReturnData<List<MyAddValueInfo>>{

	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1706141271522658141L;

}
