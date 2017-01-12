package cn.com.zte.emeeting.app.dao.shared;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import cn.com.zte.android.app.application.BaseApplication;
import cn.com.zte.android.common.util.JsonUtil;
import cn.com.zte.android.orm.DBManager;
import cn.com.zte.android.orm.dao.SharedBaseDAO;
import cn.com.zte.emeeting.app.database.entity.shared.DBMeetingRoomAddress;
import cn.com.zte.emeeting.app.database.entity.shared.DBMeetingRoomInfo;
import cn.com.zte.emeeting.app.util.LogTools;

/**
 * 会议室信息表操作类
 * 
 * @author sunli
 * 
 * */
public class SysDBMeetingRoomInfoDBDao extends
		SharedBaseDAO<DBMeetingRoomInfo> {

	public SysDBMeetingRoomInfoDBDao() {
		super(DBMeetingRoomInfo.class);
	}

	/** 向DBMeetingRoomInfo插入或修改一条数据 */
	public void insertOrUpdatData(DBMeetingRoomInfo lui) {
		try {
			insertOrUpdate(lui);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 向DBMeetingRoomInfo批量插入或修改一条数据 (原始列表已经按照时间降序)
	 * */
	public void batchInsertOrUpdatData(List<DBMeetingRoomInfo> luis) {
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
	public DBMeetingRoomInfo getDataByID(String id) {
		try {
			// 组件查询条件
			QueryBuilder queryBuilder = getEntityDao().queryBuilder();
			queryBuilder.where().eq("ID", id);
			List<DBMeetingRoomInfo> lstResult = queryBuilder.query();
			
			LogTools.i("sql",queryBuilder.prepareStatementString());
//			LogTools.i("sql",lstResult.size()+"");
//			LogTools.i("sql",JsonUtil.toJson(lstResult));
			if (lstResult != null && lstResult.size() > 0) {
				return lstResult.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	
	
	/**
	 * 获取会议室地址根据会议室编号
	 * 
	 * @param name
	 * @return
	 */
	public DBMeetingRoomInfo getDataByNo(String no) {
		try {
			// 组件查询条件
			QueryBuilder queryBuilder = getEntityDao().queryBuilder();
			queryBuilder.where().eq("MeetingRoomNo", no);
			List<DBMeetingRoomInfo> lstResult = queryBuilder.query();
			if (lstResult != null && lstResult.size() > 0) {
				return lstResult.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/** 查询DBMeetingRoomInfo表中所有数据 */
	public List<DBMeetingRoomInfo> selectAllData() {
		List<DBMeetingRoomInfo> bds = new ArrayList<DBMeetingRoomInfo>();
		try {
			bds = (List<DBMeetingRoomInfo>) queryForAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bds;
	}

	/** 查询DBMeetingRoomInfo表第一条数据 */
	public DBMeetingRoomInfo selectFirstData() {
		DBMeetingRoomInfo bd = new DBMeetingRoomInfo();
		// 查询
		try {
			// 组件查询条件
			QueryBuilder queryBuilder = getEntityDao().queryBuilder();
			queryBuilder.orderBy("LDT", false);
			List<DBMeetingRoomInfo> lstResult = queryBuilder.query();
			if (lstResult != null && lstResult.size() > 0) {
				bd = (DBMeetingRoomInfo) lstResult.get(0);
			} else {
				bd = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bd;
	}

	/**
	 * 查询DBMeetingRoomInfo表中数据总数
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

	/** 从DBMeetingRoomInfo删除一条数据 */
	public void deleteData(DBMeetingRoomInfo bd) {
		try {
			/* return int */
			delete(bd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 从DBMeetingRoomInfo批量删除数据 */
	public void batchDeleteData(List<DBMeetingRoomInfo> bds) {
		try {
			/* return int */
			batchDelete(bds);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 从DBMeetingRoomInfo删除全部数据 */
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

}
