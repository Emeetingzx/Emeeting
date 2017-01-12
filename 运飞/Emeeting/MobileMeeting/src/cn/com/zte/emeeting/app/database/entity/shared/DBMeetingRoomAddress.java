package cn.com.zte.emeeting.app.database.entity.shared;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import cn.com.zte.android.app.common.vo.BaseVO;
/**
 * 会议室地址对象
 * @author sun.li
 * */
@DatabaseTable(tableName = "MeetingRoomAddress")
public class DBMeetingRoomAddress extends BaseVO {

	/** 序列化ID*/
	@Expose(serialize = false, deserialize = false)
	private static final long serialVersionUID = 5480999215195223132L;
	
	/**
	 * ID Not null
	 */
	@DatabaseField(columnName = "ID", id = true)
	private String ID;
	/**
	 * 父ID	Not null
	 */
	@DatabaseField(columnName = "PID")
	private String PID;
	/**
	 * 地址中文名称	Not null，地址中文摘要名称
	 * 【AddessChinese】
	 */
	@DatabaseField(columnName = "AddessChinese")
	private String ASC;
	/**
	 * 排序号	Not null，地址显示顺序	OID【OrderID】	
	 */
	@DatabaseField(columnName = "OrderID")
	private String OID;
	/**
	 * 节点层级	Not null，地址所属层级（如城市、园区、建筑）	LID【LevelId】
	 */
	@DatabaseField(columnName = "LevelId")
	private String LID;
	/**
	 * 有效标识	1:有效；
	 * 0：无效；
	 * 标识地址的有效性，支持地址的软删除	IVID【IsValidId】
	 */
	@DatabaseField(columnName = "IsValidId")
	private String IVID;
	/**
	 * 地址完整中文名称		
	 * 【LongAddessChinese】	String
	 */
	@DatabaseField(columnName = "LongAddessChinese")
	private String LASC;
	
	/**
	 * ID Not null
	 */
	public String getID() {
		return ID;
	}	
	/**
	 * ID Not null
	 */
	public void setID(String iD) {
		ID = iD;
	}
	/**
	 * 父ID	Not null
	 */
	public String getPID() {
		return PID;
	}
	/**
	 * 父ID	Not null
	 */
	public void setPID(String pID) {
		PID = pID;
	}
	/**
	 * 地址中文名称	Not null，地址中文摘要名称
	 * 【AddessChinese】
	 */
	public String getASC() {
		return ASC;
	}
	/**
	 * 地址中文名称	Not null，地址中文摘要名称
	 * 【AddessChinese】
	 */
	public void setASC(String aSC) {
		ASC = aSC;
	}
	/**
	 * 排序号	Not null，地址显示顺序	OID【OrderID】	
	 */
	public String getOID() {
		return OID;
	}
	/**
	 * 排序号	Not null，地址显示顺序	OID【OrderID】	
	 */
	public void setOID(String oID) {
		OID = oID;
	}
	/**
	 * 节点层级	Not null，地址所属层级（如城市、园区、建筑）	LID【LevelId】
	 */
	public String getLID() {
		return LID;
	}
	/**
	 * 节点层级	Not null，地址所属层级（如城市、园区、建筑）	LID【LevelId】
	 */
	public void setLID(String lID) {
		LID = lID;
	}
	/**
	 * 有效标识	1:有效；
	 * 0：无效；
	 * 标识地址的有效性，支持地址的软删除	IVID【IsValidId】
	 */
	public String getIVID() {
		return IVID;
	}
	/**
	 * 有效标识	1:有效；
	 * 0：无效；
	 * 标识地址的有效性，支持地址的软删除	IVID【IsValidId】
	 */
	public void setIVID(String iVID) {
		IVID = iVID;
	}
	/**
	 * 地址完整中文名称		
	 * 【LongAddessChinese】	String
	 */
	public String getLASC() {
		return LASC;
	}
	/**
	 * 地址完整中文名称		
	 * 【LongAddessChinese】	String
	 */
	public void setLASC(String lASC) {
		LASC = lASC;
	} 

	
}
