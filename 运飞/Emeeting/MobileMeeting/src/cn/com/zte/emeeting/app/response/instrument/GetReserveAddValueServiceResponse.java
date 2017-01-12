package cn.com.zte.emeeting.app.response.instrument;

import java.util.Map;

import cn.com.zte.mobilebasedata.entity.AppReturnData;

import com.google.gson.annotations.Expose;


/** 
 * 预定增值服务接口
 * @author LangK
 * */
public class GetReserveAddValueServiceResponse extends AppReturnData<Map<String,String>>{

	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1706141271522658141L;

}
