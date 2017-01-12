/**
 * 
 */
package cn.com.zte.emeeting.app.appservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import cn.com.zte.android.app.appservice.BaseAppService;
import cn.com.zte.emeeting.app.response.entity.ItemEntity;
import cn.com.zte.emeeting.app.util.EmeetingTimeUtil;
import cn.com.zte.emeeting.app.util.EmeetingTimeUtil.TimeScale;

/**
 * 该类为:筛选界面逻辑处理类
 * @author wf
 */
public class MeetingFilterService extends BaseAppService {

	
	/**
	 * 该类构造方法:
	 * @param @param context
	 */
	public MeetingFilterService(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	/** 楼层列表*/
	public List<String> getListFloors()
	{
		List<String> list = new ArrayList<String>();
		list.add("不限");
		for(int i=1;i<=17;i++)
		{
			list.add(i+"楼");
		}
		return list;
	}
	
	
//	/** 人数列表,显示的字符串*/
//	public List<String> getListPersonNumName()
//	{
//		List<String> list = new ArrayList<String>();
//		list.add("不限");
//		list.add("10人以下");
//		list.add("10-30人");
//		list.add("30-60人");
//		list.add("60人以上");
//		return list;
//	}
//	
//	
//	/** 人数列表,提交给服务器的标识码*/
//	public List<String> getListPersonNumCode()
//	{
//		List<String> list = new ArrayList<String>();
//		list.add("0");
//		list.add("10");
//		list.add("20");
//		list.add("100");
//		list.add("200");
//		return list;
//	}
//	
	

}
