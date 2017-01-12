package cn.com.zte.emeeting.app.dao.shared;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import cn.com.zte.android.app.application.BaseApplication;
import cn.com.zte.android.orm.DBManager;
import cn.com.zte.android.orm.dao.SharedBaseDAO;
import cn.com.zte.mobilebasedata.system.entity.SysLastUpdatetimeInfo;

/**
 * 数据更新状态表操作类
 * 
 * @author sunli
 * 
 * */
public class SysLastUpdatetimeInfoDBDao extends
		SharedBaseDAO<SysLastUpdatetimeInfo> {

	public SysLastUpdatetimeInfoDBDao() {
		super(SysLastUpdatetimeInfo.class);
	}

	/** 向SysLastUpdatetimeInfo插入或修改一条数据 */
	public void insertOrUpdatData(SysLastUpdatetimeInfo lui) {
		try {
			insertOrUpdate(lui);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 向SysLastUpdatetimeInfo批量插入或修改一条数据 (原始列表已经按照时间降序)
	 * */
	public void batchInsertOrUpdatData(List<SysLastUpdatetimeInfo> luis) {
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
	 * 获取某张表的最后更新时间
	 * 
	 * @param name
	 * @return
	 */
	public String getLastDate(String name) {
		try {
			// 组件查询条件
			QueryBuilder queryBuilder = getEntityDao().queryBuilder();
			queryBuilder.where().eq("Name", name);
			queryBuilder.orderBy("LDT", false);
			List<SysLastUpdatetimeInfo> lstResult = queryBuilder.query();
			if (lstResult != null && lstResult.size() > 0) {
				return lstResult.get(0).getLDT();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "";
	}
	
	/** 查询LastUpdatetimeInfo表中Name对应信息 */
	public SysLastUpdatetimeInfo selectNameData(String name) {
		SysLastUpdatetimeInfo lui = new SysLastUpdatetimeInfo();
		// 查询
		try {
			// 组件查询条件
			QueryBuilder queryBuilder = getEntityDao().queryBuilder();
			Where where = queryBuilder.where();
			where.eq("Name", name);
			List<SysLastUpdatetimeInfo> luis = queryBuilder.query();
			if(luis!=null && luis.size()>0){
				lui = luis.get(0);
			}else{
				lui = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lui;
	}

	/** 查询SysLastUpdatetimeInfo表中所有数据 */
	public List<SysLastUpdatetimeInfo> selectAllData() {
		List<SysLastUpdatetimeInfo> bds = new ArrayList<SysLastUpdatetimeInfo>();
		try {
			bds = (List<SysLastUpdatetimeInfo>) queryForAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bds;
	}

	/** 查询SysLastUpdatetimeInfo表第一条数据 */
	public SysLastUpdatetimeInfo selectFirstData() {
		SysLastUpdatetimeInfo bd = new SysLastUpdatetimeInfo();
		// 查询
		try {
			// 组件查询条件
			QueryBuilder queryBuilder = getEntityDao().queryBuilder();
			queryBuilder.orderBy("LDT", false);
			List<SysLastUpdatetimeInfo> lstResult = queryBuilder.query();
			if (lstResult != null && lstResult.size() > 0) {
				bd = (SysLastUpdatetimeInfo) lstResult.get(0);
			} else {
				bd = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bd;
	}

	/**
	 * 查询SysLastUpdatetimeInfo表中数据总数
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

	/** 从SysLastUpdatetimeInfo删除一条数据 */
	public void deleteData(SysLastUpdatetimeInfo bd) {
		try {
			/* return int */
			delete(bd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 从SysLastUpdatetimeInfo批量删除数据 */
	public void batchDeleteData(List<SysLastUpdatetimeInfo> bds) {
		try {
			/* return int */
			batchDelete(bds);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 从SysLastUpdatetimeInfo删除全部数据 */
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
