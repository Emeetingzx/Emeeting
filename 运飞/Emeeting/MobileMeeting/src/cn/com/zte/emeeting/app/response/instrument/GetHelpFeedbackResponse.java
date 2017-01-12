package cn.com.zte.emeeting.app.response.instrument;


import cn.com.zte.mobilebasedata.entity.AppReturnData;

import com.google.gson.annotations.Expose;


/** 
 * 【公共】帮助反馈接口请求结果接收处理类
 * @author LangK
 * */
public class GetHelpFeedbackResponse extends AppReturnData<String>{

	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1706141271522658141L;

}
