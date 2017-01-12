/**
 * 
 */
package cn.com.zte.emeeting.app.appservice;

import java.util.List;
import android.content.Context;
import cn.com.zte.android.app.appservice.BaseAppService;
import cn.com.zte.emeeting.app.dao.shared.SysDBMeetingRoomAddressDBDao;
import cn.com.zte.emeeting.app.database.entity.shared.DBMeetingRoomAddress;

/**
 * 该类为:GetMeetFragment逻辑处理类
 * @author wf
 */
public class ChoosePlaceService extends BaseAppService {

	
	/**
	 * 该类构造方法:
	 * @param @param context
	 */
	public ChoosePlaceService(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
//	/** 获取城市列表*/
//	public List<String> getListCity()
//	{
//		List<String> listCities = new ArrayList<String>();
//		String [] cities = {"深圳","西安","上海","南京","北京"};
//		for(String c:cities)
//		{
//			listCities.add(c);
//		}
//		return listCities;
//	}
//	
//	/** 获取地区列表*/
//	public List<String> getListArea()
//	{
//		List<String> listCities = new ArrayList<String>();
//		String [] cities = {"科技园","工业园","同富裕","石岩","大梅沙","布吉","河源"};
//		for(String c:cities)
//		{
//			listCities.add(c);
//		}
//		return listCities;
//	}
//	
//	/** 获取建筑列表*/
//	public List<String> getListBuilding()
//	{
//		List<String> listCities = new ArrayList<String>();
//		String [] cities = {"研发楼","A座","B1座","B2座","B3座","万德莱"};
//		for(String c:cities)
//		{
//			listCities.add(c);
//		}
//		return listCities;
//	}
	
	/** 获取*/
	public List<DBMeetingRoomAddress> getListPlaceCity()
	{
		SysDBMeetingRoomAddressDBDao dao=new SysDBMeetingRoomAddressDBDao();
		return dao.getCityList();
	}
	
	/** 获取*/
	public List<DBMeetingRoomAddress> getListPlace(DBMeetingRoomAddress parentEntity)
	{
		SysDBMeetingRoomAddressDBDao dao=new SysDBMeetingRoomAddressDBDao();
		return dao.getPlaceList(parentEntity);
	}
	
	
	/** 获取*/
	public List<DBMeetingRoomAddress> getListFloor(DBMeetingRoomAddress parentEntity)
	{
		SysDBMeetingRoomAddressDBDao dao=new SysDBMeetingRoomAddressDBDao();
		return dao.getFloorList(parentEntity);
	}
	
	/** 获取父地址(父地址为二级)*/
	public DBMeetingRoomAddress getParentOfChild_2(DBMeetingRoomAddress childEntity)
	{
		SysDBMeetingRoomAddressDBDao dao=new SysDBMeetingRoomAddressDBDao();
		return dao.getParentAddressOfLevel3(childEntity);
	}
	
	/** 获取父地址(父地址为一级)*/
	public DBMeetingRoomAddress getParentOfChild_1(DBMeetingRoomAddress childEntity)
	{
		SysDBMeetingRoomAddressDBDao dao=new SysDBMeetingRoomAddressDBDao();
		return dao.getParentAddressOfLevel2(childEntity);
	}
}
