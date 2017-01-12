package cn.com.zte.emeeting.app.request.instrument;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.gson.annotations.Expose;

import cn.com.zte.mobilebasedata.entity.FilterInfo;
import cn.com.zte.mobilebasedata.request.AppHttpRequest;
import cn.com.zte.mobilebasedata.request.HttpUtil;

/**
 * 帮助反馈接口请求类
 * @author LangK
 *
 */
public class GetHelpFeedbackRequest extends AppHttpRequest{


	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1056878707687130274L;

	/**
	 * @param context
	 */
	public GetHelpFeedbackRequest(Context context, boolean httpsFlag,String FeedbackInfo,String ContactInfo) {
		super(context, httpsFlag);
		setWebServiceMethod(HttpUtil.PublicInterface);
		setC(HttpUtil.HelpFeedback);
		FilterInfo filterInfo1 = new FilterInfo("FeedbackInfo",FeedbackInfo);
		FilterInfo contactFilterInfo = new FilterInfo("ContactInfo",ContactInfo);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(filterInfo1);
		list.add(contactFilterInfo);
		setF(list);
	}
	
	/**
	 * @param context
	 */
	public GetHelpFeedbackRequest(Context context,String FeedbackInfo,String ContactInfo) {
		super(context, HttpUtil.HTTPSFLAG);
		setWebServiceMethod(HttpUtil.PublicInterface);
		setC(HttpUtil.HelpFeedback);
		FilterInfo filterInfo1 = new FilterInfo("FeedbackInfo",FeedbackInfo);
		FilterInfo contactFilterInfo = new FilterInfo("ContactInfo",ContactInfo);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(filterInfo1);
		list.add(contactFilterInfo);
		setF(list);
	}
}
