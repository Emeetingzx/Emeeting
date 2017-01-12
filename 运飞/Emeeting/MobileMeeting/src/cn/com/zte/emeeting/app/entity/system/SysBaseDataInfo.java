package cn.com.zte.emeeting.app.entity.system;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import cn.com.zte.android.app.common.vo.BaseVO;

/**
 * 基础数据对象实体类
 * 
 * @author sun.li
 * @comment 作为本地Shared数据库基础数据信息表对应类
 * */
@DatabaseTable(tableName = "SysBaseDataInfo")
public class SysBaseDataInfo extends BaseVO{

	@Expose(serialize = false, deserialize = false)
	private static final long serialVersionUID = -898275971313576673L;
	
	/** ID*/
	@DatabaseField(columnName = "ID", id = true)
	private String ID;
	
	/** 父ID*/
	@DatabaseField(columnName = "PID")
	private String PID;
	
	/** 编码(不重复)*/
	@DatabaseField(columnName = "Code")
	private String Code;
	
	/** 显示值*/
	@DatabaseField(columnName = "Value")
	private String Value;

	/**
	 *取得iD
	 * @return the iD
	 */
	public String getID() {
		return ID;
	}

	/**
	 *设置iD
	 * @param iD the iD to set
	 */
	public void setID(String iD) {
		ID = iD;
	}

	/**
	 *取得父ID
	 * @return the pID
	 */
	public String getPID() {
		return PID;
	}

	/**
	 *设置父ID
	 * @param pID the pID to set
	 */
	public void setPID(String pID) {
		PID = pID;
	}

	/**
	 *取得编码(不重复)
	 * @return the code
	 */
	public String getCode() {
		return Code;
	}

	/**
	 *设置编码(不重复)
	 * @param code the code to set
	 */
	public void setCode(String code) {
		Code = code;
	}

	/**
	 *取得显示值
	 * @return the value
	 */
	public String getValue() {
		return Value;
	}

	/**
	 *设置显示值
	 * @param value the value to set
	 */
	public void setValue(String value) {
		Value = value;
	}
	
	

}
