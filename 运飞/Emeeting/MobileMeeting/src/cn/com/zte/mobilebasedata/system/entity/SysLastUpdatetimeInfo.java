package cn.com.zte.mobilebasedata.system.entity;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import cn.com.zte.android.app.common.vo.BaseVO;

/**
 * 更新时间对象
 * @author sun.li
 * */
@DatabaseTable(tableName = "SysLastUpdatetimeInfo")
public class SysLastUpdatetimeInfo extends BaseVO {

	/** 序列化ID*/
	@Expose(serialize = false, deserialize = false)
	private static final long serialVersionUID = 5480999215199223132L;
	
	/**
	 * 	数据名称
	 */
	@DatabaseField(columnName = "Name", id = true)
	private String NAME;
	
	/**
	 * 	最后更新时间
	 */
	@DatabaseField(columnName = "LDT")
	private String LDT;
	

	/**  
	 * 获取最后更新时间
	 * @return lDT lDT  
	 */
	public String getLDT() {
		return LDT;
	}

	/**  
	 * 设置最后更新时间
	 * @param lDT lDT  
	 */
	public void setLDT(String lDT) {
		LDT = lDT;
	}


	/**
	 * 	数据名称
	 */
	public String getName() {
		return NAME;
	}


	/**
	 * 	数据名称
	 */
	public void setName(String name) {
		NAME = name;
	}


}
