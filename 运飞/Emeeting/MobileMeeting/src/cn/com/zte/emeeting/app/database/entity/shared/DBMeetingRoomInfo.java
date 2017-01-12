package cn.com.zte.emeeting.app.database.entity.shared;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import cn.com.zte.android.app.common.vo.BaseVO;
/**
 * 会议室基本信息对象
 * @author sun.li
 * */
@DatabaseTable(tableName = "MeetingRoomInfo")
public class DBMeetingRoomInfo extends BaseVO {

	/** 序列化ID*/
	@Expose(serialize = false, deserialize = false)
	private static final long serialVersionUID = 5480999215195225132L;
	
	/**
	 * ID Not null
	 */
	@DatabaseField(columnName = "ID", id = true)
	private String  ID;
	/**
	 *会议室编号	Not null	MRN 【MeetingRoomNo】	
	 */
	@DatabaseField(columnName = "MeetingRoomNo")
	private String  MRN;
	/**
	 * 会议室中文名称（摘要名称）	Not null，会议室中文名称	MRC 【MeetingRoomChinese】	
	 */
	@DatabaseField(columnName = "MeetingRoomChinese")
	private String  MRC;
	/**
	 * 所属组织	Not null,
	 * 对应Emeeting_Organization主键	ON【OrganisationNo】
	 */
	@DatabaseField(columnName = "OrganisationNo")
	private String ON;
	/**
	 * 收费标准	Not null，单位：元/小时 	CSD【ChargeStandard】	
	 */
	@DatabaseField(columnName = "ChargeStandard")
	private String CSD;
	/**
	 * 有效标识	Not null，
	 *0：无效；
	 *1:有效；
	 *标识地址的有效性，支持地址的软删除	IVID【IsValidId】	
	 */
	@DatabaseField(columnName = "IsValidId")
	private String IVID;
	/**
	 * 会议室地址ID串	Not null，
	 * 对应会议室地址对象【MeetingRoomAddress】ID串(用,号隔开)，如：
	 * 深圳-南山区-研发大楼-4层；
	 * 9857（4层），5875（研发大楼）,5871（南山区）,3677（深圳）	MRAIDS【MeetingRoomAddressIds】	
	 */
	@DatabaseField(columnName = "MeetingRoomAddressIds")
	private String MRAIDS;
	/**
	 * 会议室规模	Not null，
	 * 座位数	MRS【MeetingRoomScale】	
	 */
	@DatabaseField(columnName = "MeetingRoomScale")
	private String MRS;
	/**
	 * 	投影仪状态	Not null，
	 * 	0->没有
	 * 	1->不支持双流
	 * 	2->支持双流接收
	 * 	3->支持双流接收发送	PJS【ProjectorState】	
	 */
	@DatabaseField(columnName = "ProjectorState")
	private String PJS;
	/**
	 * 	电视状态	Not null，
	 * 	0->没有
	 * 	1->有	TVS【TelevisionState】	
	 */
	@DatabaseField(columnName = "TelevisionState")
	private String TVS;
	/**
	 * 电话状态	Not null，
	 * 0->没有
	 * 1-> POLYCOM
	 * 2-> USB-Phone 	PS【PhoneState】	
	 */
	@DatabaseField(columnName = "PhoneState")
	private String PS;
	/**
	 * 巡检人中文名称	Not null	CCN【CheckingChineseName】	
	 */
	@DatabaseField(columnName = "CheckingChineseName")
	private String CCN;
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
	 *会议室编号	Not null	MRN 【MeetingRoomNo】	
	 */
	public String getMRN() {
		return MRN;
	}
	/**
	 *会议室编号	Not null	MRN 【MeetingRoomNo】	
	 */
	public void setMRN(String mRN) {
		MRN = mRN;
	}
	/**
	 * 会议室中文名称（摘要名称）	Not null，会议室中文名称	MRC 【MeetingRoomChinese】	
	 */
	public String getMRC() {
		return MRC;
	}
	/**
	 * 会议室中文名称（摘要名称）	Not null，会议室中文名称	MRC 【MeetingRoomChinese】	
	 */
	public void setMRC(String mRC) {
		MRC = mRC;
	}
	/**
	 * 所属组织	Not null,
	 * 对应Emeeting_Organization主键	ON【OrganisationNo】
	 */
	public String getON() {
		return ON;
	}
	/**
	 * 所属组织	Not null,
	 * 对应Emeeting_Organization主键	ON【OrganisationNo】
	 */
	public void setON(String oN) {
		ON = oN;
	}
	/**
	 * 收费标准	Not null，单位：元/小时 	CSD【ChargeStandard】	
	 */
	public String getCSD() {
		return CSD;
	}
	/**
	 * 收费标准	Not null，单位：元/小时 	CSD【ChargeStandard】	
	 */
	public void setCSD(String cSD) {
		CSD = cSD;
	}
	/**
	 * 有效标识	Not null，
	 *0：无效；
	 *1:有效；
	 *标识地址的有效性，支持地址的软删除	IVID【IsValidId】	
	 */
	public String getIVID() {
		return IVID;
	}
	/**
	 * 有效标识	Not null，
	 *0：无效；
	 *1:有效；
	 *标识地址的有效性，支持地址的软删除	IVID【IsValidId】	
	 */
	public void setIVID(String iVID) {
		IVID = iVID;
	}
	/**
	 * 会议室地址ID串	Not null，
	 * 对应会议室地址对象【MeetingRoomAddress】ID串(用,号隔开)，如：
	 * 深圳-南山区-研发大楼-4层；
	 * 9857（4层），5875（研发大楼）,5871（南山区）,3677（深圳）	MRAIDS【MeetingRoomAddressIds】	
	 */
	public String getMRAIDS() {
		return MRAIDS;
	}
	/**
	 * 会议室地址ID串	Not null，
	 * 对应会议室地址对象【MeetingRoomAddress】ID串(用,号隔开)，如：
	 * 深圳-南山区-研发大楼-4层；
	 * 9857（4层），5875（研发大楼）,5871（南山区）,3677（深圳）	MRAIDS【MeetingRoomAddressIds】	
	 */
	public void setMRAIDS(String mRAIDS) {
		MRAIDS = mRAIDS;
	}
	/**
	 * 会议室规模	Not null，
	 * 座位数	MRS【MeetingRoomScale】	
	 */
	public String getMRS() {
		return MRS;
	}
	/**
	 * 会议室规模	Not null，
	 * 座位数	MRS【MeetingRoomScale】	
	 */
	public void setMRS(String mRS) {
		MRS = mRS;
	}
	/**
	 * 	投影仪状态	Not null，
	 * 	0->没有
	 * 	1->不支持双流
	 * 	2->支持双流接收
	 * 	3->支持双流接收发送	PJS【ProjectorState】	
	 */
	public String getPJS() {
		return PJS;
	}
	/**
	 * 	投影仪状态	Not null，
	 * 	0->没有
	 * 	1->不支持双流
	 * 	2->支持双流接收
	 * 	3->支持双流接收发送	PJS【ProjectorState】	
	 */
	public void setPJS(String pJS) {
		PJS = pJS;
	}
	/**
	 * 	电视状态	Not null，
	 * 	0->没有
	 * 	1->有	TVS【TelevisionState】	
	 */
	public String getTVS() {
		return TVS;
	}
	/**
	 * 	电视状态	Not null，
	 * 	0->没有
	 * 	1->有	TVS【TelevisionState】	
	 */
	public void setTVS(String tVS) {
		TVS = tVS;
	}
	/**
	 * 电话状态	Not null，
	 * 0->没有
	 * 1-> POLYCOM
	 * 2-> USB-Phone 	PS【PhoneState】	
	 */
	public String getPS() {
		return PS;
	}
	/**
	 * 电话状态	Not null，
	 * 0->没有
	 * 1-> POLYCOM
	 * 2-> USB-Phone 	PS【PhoneState】	
	 */
	public void setPS(String pS) {
		PS = pS;
	}
	/**
	 * 巡检人中文名称	Not null	CCN【CheckingChineseName】	
	 */
	public String getCCN() {
		return CCN;
	}
	/**
	 * 巡检人中文名称	Not null	CCN【CheckingChineseName】	
	 */
	public void setCCN(String cCN) {
		CCN = cCN;
	}
	
	
	
}
