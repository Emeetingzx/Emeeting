package cn.com.zte.mobilebasedata.system.request;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.gson.annotations.Expose;

import cn.com.zte.mobilebasedata.entity.FilterInfo;
import cn.com.zte.mobilebasedata.entity.PageInput;
import cn.com.zte.mobilebasedata.request.AppHttpRequest;
import cn.com.zte.mobilebasedata.request.HttpUtil;

/**
 * 获取会议室地址信息接口请求类
 * @author LangK
 *
 */
public class GetSysMeetingRoomAddressRequest extends AppHttpRequest{


	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = 1056879607687130274L;

	public GetSysMeetingRoomAddressRequest(Context context, boolean httpsFlag,String lastUpdateDate,PageInput pageInput) {
		super(context, httpsFlag);
		setWebServiceMethod(HttpUtil.BasisInterface);
		setC(HttpUtil.GetSysMeetingRoomAddress);
		setP(pageInput);
		FilterInfo filterInfo = new FilterInfo("LastUpdateDate",lastUpdateDate);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(filterInfo);
		setF(list);
		
	}
	
	public GetSysMeetingRoomAddressRequest(Context context,String lastUpdateDate,PageInput pageInput) {
		super(context, HttpUtil.HTTPSFLAG);
		setWebServiceMethod(HttpUtil.BasisInterface);
		setC(HttpUtil.GetSysMeetingRoomAddress);
		setP(pageInput);
		FilterInfo filterInfo = new FilterInfo("LastUpdateDate",lastUpdateDate);
		List<FilterInfo> list = new ArrayList<FilterInfo>();
		list.add(filterInfo);
		setF(list);
	}
}
