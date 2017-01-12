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
 * 该类为:会议室总览activity的逻辑处理类
 * @author wf
 */
public class MeetingRoomUseStateService extends BaseAppService {

	
	/**
	 * 该类构造方法:
	 * @param @param context
	 */
	public MeetingRoomUseStateService(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	/** 获取会议室状态数据*/
	public List<ItemEntity> getMeetingRoomDataList()
	{
		List<ItemEntity> list = new ArrayList<ItemEntity>();
		List<String[]> listTime = new ArrayList<String[]>();
		listTime.add(new String[]{"2015-08-16 09:30","2015-08-16 11:20"});
		listTime.add(new String[]{"2015-08-16 12:25","2015-08-16 13:15"});
		listTime.add(new String[]{"2015-08-16 15:15","2015-08-16 15:20"});
		listTime.add(new String[]{"2015-08-16 18:20","2015-08-16 19:05"});
		listTime.add(new String[]{"2015-08-16 19:06","2015-08-16 19:16"});
		
		for(int i = 0;i<20;i++)
		{
			Map<Integer,List<TimeScale>> m = new HashMap<Integer, List<TimeScale>>();
			
			for(int j=0;j<listTime.size();j++)
			{
				EmeetingTimeUtil.parseTimeToList(m, listTime.get(j)[0], listTime.get(j)[1]);
			}
			ItemEntity item = new ItemEntity("会议室_"+i, m);
			list.add(item);
		}
		return list;
	}

}
