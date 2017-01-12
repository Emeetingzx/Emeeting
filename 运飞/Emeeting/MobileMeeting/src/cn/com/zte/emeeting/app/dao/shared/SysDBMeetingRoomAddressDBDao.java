package cn.com.zte.emeeting.app.dao.shared;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import cn.com.zte.android.app.application.BaseApplication;
import cn.com.zte.android.orm.DBManager;
import cn.com.zte.android.orm.dao.SharedBaseDAO;
import cn.com.zte.emeeting.app.database.entity.shared.DBMeetingRoomAddress;

/**
 * 会议室地址表操作类
 * 
 * @author sunli
 * 
 * */
public class SysDBMeetingRoomAddressDBDao extends
		SharedBaseDAO<DBMeetingRoomAddress> {

	public SysDBMeetingRoomAddressDBDao() {
		super(DBMeetingRoomAddress.class);
	}

	/** 向DBMeetingRoomAddress插入或修改一条数据 */
	public void insertOrUpdatData(DBMeetingRoomAddress lui) {
		try {
			insertOrUpdate(lui);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 向DBMeetingRoomAddress批量插入或修改一条数据 (原始列表已经按照时间降序)
	 * */
	public void batchInsertOrUpdatData(List<DBMeetingRoomAddress> luis) {
		try {
			if (luis != null && luis.size() > 0) {
				batchInsertOrUpdate(luis);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 获取会议室地址根据ID
	 * 
	 * @param name
	 * @return
	 */
	public DBMeetingRoomAddress getDataByID(String id) {
		try {
			// 组件查询条件
			QueryBuilder queryBuilder = getEntityDao().queryBuilder();
			queryBuilder.where().eq("ID", id);
			List<DBMeetingRoomAddress> lstResult = queryBuilder.query();
			if (lstResult != null && lstResult.size() > 0) {
				return lstResult.get(0);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取会议室地址根据ID
	 * 
	 * @param name
	 * @return
	 */
	public List<DBMeetingRoomAddress>  getMeetingRoomByID(String id) {
		try {
			// 组件查询条件
			QueryBuilder queryBuilder = getEntityDao().queryBuilder();
			queryBuilder.where().eq("ID", id);
			List<DBMeetingRoomAddress> lstResult = queryBuilder.query();
			if (lstResult != null && lstResult.size() > 0) {
				return lstResult;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	/** 查询DBMeetingRoomAddress表中所有数据 */
	public List<DBMeetingRoomAddress> selectAllData() {
		List<DBMeetingRoomAddress> bds = new ArrayList<DBMeetingRoomAddress>();
		try {
			bds = (List<DBMeetingRoomAddress>) queryForAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bds;
	}

	/** 查询DBMeetingRoomAddress表第一条数据 */
	public DBMeetingRoomAddress selectFirstData() {
		DBMeetingRoomAddress bd = new DBMeetingRoomAddress();
		// 查询
		try {
			// 组件查询条件
			QueryBuilder queryBuilder = getEntityDao().queryBuilder();
			queryBuilder.orderBy("LDT", false);
			List<DBMeetingRoomAddress> lstResult = queryBuilder.query();
			if (lstResult != null && lstResult.size() > 0) {
				bd = (DBMeetingRoomAddress) lstResult.get(0);
			} else {
				bd = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bd;
	}

	/**
	 * 查询DBMeetingRoomAddress表中数据总数
	 * 
	 * @return int
	 * */
	public int dataCount() {
		int count = 0;
		try {
			count = (int) countOf();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	/** 从DBMeetingRoomAddress删除一条数据 */
	public void deleteData(DBMeetingRoomAddress bd) {
		try {
			/* return int */
			delete(bd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 从DBMeetingRoomAddress批量删除数据 */
	public void batchDeleteData(List<DBMeetingRoomAddress> bds) {
		try {
			/* return int */
			batchDelete(bds);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 从DBMeetingRoomAddress删除全部数据 */
	public void deleteAllData() {
		try {
			DeleteBuilder deleteBuilder = getEntityDao().deleteBuilder();
			deleteBuilder.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public DBManager getDBManager() {
		return BaseApplication.getInstance().getDbManager();
	}

	/** 获取城市列表*/
	public List<DBMeetingRoomAddress> getCityList()
	{
		List<DBMeetingRoomAddress> list = new ArrayList<DBMeetingRoomAddress>();
		try {
			QueryBuilder queryBuider = getEntityDao().queryBuilder();
			queryBuider.where().eq("LevelId", "1").and().eq("IsValidId", "1");
			queryBuider.orderBy("OrderID", true);
			list = queryBuider.query();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<DBMeetingRoomAddress>();
		}
		return list;
	}
	
	/** 获取二级三级列表*/
	public List<DBMeetingRoomAddress> getPlaceList(DBMeetingRoomAddress parentEntity)
	{
		
		List<DBMeetingRoomAddress> list = new ArrayList<DBMeetingRoomAddress>();
		try {
			if(TextUtils.isEmpty(parentEntity.getID()))return list;
			
			QueryBuilder queryBuider = getEntityDao().queryBuilder();
			queryBuider.where().eq("PID", parentEntity.getID()).and().eq("IsValidId", "1");
			queryBuider.orderBy("OrderID", true);
			list = queryBuider.query();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<DBMeetingRoomAddress>();
		}
		return list;
	}
	
	/** 获取楼层列表*/
	public List<DBMeetingRoomAddress> getFloorList(DBMeetingRoomAddress parentEntity)
	{
		
		List<DBMeetingRoomAddress> list = new ArrayList<DBMeetingRoomAddress>();
		try {
			
			if(TextUtils.isEmpty(parentEntity.getID()))return list;
			
			QueryBuilder queryBuider = getEntityDao().queryBuilder();
			queryBuider.where().eq("PID", parentEntity.getID()).and().eq("IsValidId", "1")
			.and().eq("LevelId","4");
			queryBuider.orderBy("OrderID", true);
			list = queryBuider.query();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<DBMeetingRoomAddress>();
		}
		return list;
	}
	
	/** 获取3级地址的父级地址*/
	public DBMeetingRoomAddress getParentAddressOfLevel3(DBMeetingRoomAddress childEntity)
	{
		DBMeetingRoomAddress address;
		try {
			if(TextUtils.isEmpty(childEntity.getID()))return null;
			QueryBuilder queryBuider = getEntityDao().queryBuilder();
			queryBuider.where().eq("ID", childEntity.getPID()).and().eq("IsValidId", "1")
			.and().eq("LevelId","2");
			queryBuider.orderBy("OrderID", true);
			address = (DBMeetingRoomAddress) queryBuider.query().get(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return address;
	}
	/** 获取2级地址的父级地址*/
	public DBMeetingRoomAddress getParentAddressOfLevel2(DBMeetingRoomAddress childEntity)
	{
		DBMeetingRoomAddress address;
		try {
			if(TextUtils.isEmpty(childEntity.getID()))return null;
			QueryBuilder queryBuider = getEntityDao().queryBuilder();
			queryBuider.where().eq("ID",  childEntity.getPID()).and().eq("IsValidId", "1")
			.and().eq("LevelId","1");
			queryBuider.orderBy("OrderID", true);
			address = (DBMeetingRoomAddress) queryBuider.query().get(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return address;
	}
}
