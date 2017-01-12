package cn.com.zte.emeeting.app.response.entity;

import com.google.gson.annotations.Expose;
import cn.com.zte.android.app.common.vo.BaseVO;

/**
 * 会议信息对象实体
 * 
 * @author sun.li
 * */
public class MeetingInfo extends BaseVO {

	/** 序列化ID */
	@Expose(serialize = false, deserialize = false)
	private static final long serialVersionUID = 5480999225195225132L;
	
	/**
	 * 可以会控，展示会控
	 */
	public static final String IECY = "Y";
	
	/**
	 * 不可以会控，不展示会控
	 */
	public static final String IECN = "N";

	/**
	 * 会议ID Not null MID【MeetingID】
	 */
	private String MID;
	/**
	 * 会议名称 MN 【MeetingName】
	 */
	private String MN;

	/**
	 * 预定会议室ID串 用逗号隔开，如： 5871,2586,2547 BMRIDS【BookingsMeetingRoomIds】
	 */
	private String BMRIDS;
	/**
	 * 开始时间 Not null BD【BeginDate】
	 */
	private String BD;

	/**
	 * 格式为： YYYY-MM-DD hh:mm:ss 结束时间 Not null ED【EndDate】
	 */
	private String ED;

	/**
	 * 格式为： YYYY-MM-DD hh:mm:ss 会议时长 Not null MT【MeetingTime】
	 */
	private String MT;

	/**
	 * 会议级别 会议级别： 1->A级 2-B级 3->C级 对应参会人级别,其中3对应参会人级别的3和4 ML【MeetingLevel】
	 */
	private String ML;
	/**
	 * 组织人工号 OPN【OrganizePeopleNo】
	 * 
	 */
	private String OPN;
	/**
	 * 组织人中文姓名 OPCN【OrganizePeopleChineseName】
	 * 
	 */
	private String OPCN;

	/**
	 * 参会领导级别 领导级别： 1->二层领导 2->三层领导 3->四层领导 4->其他
	 * PMLL【ParticipateMeetingLeaderLevel】
	 */
	private String PMLL;
	/**
	 * 申请时间 AT【ApplyTime】
	 */
	private String AT;

	/**
	 * 格式为： YYYY-MM-DD hh:mm:ss 会议状态 会议状态: 1->草稿 2->待分配 3->已预订 4->已服务 5->已结束
	 * 6->已回访 7->已退订 8->已取消 9->已删除 10-已关闭 MS【MeetingState】
	 */
	private String MS;

	/**
	 * 操作状态 0 不可操作；1 可退订； 2 可结束； OS【OperatingState】
	 */
	private String OS;

	/**
	 * 
	 * 参会方数 预定电话/视频会议使用 PN【ParticipantsNumber】
	 */
	private String PN;

	/**
	 * 会议类型 常规会议：1， 电话会议桥：2， 培训会议：3， 网真会议：4， 云招标会议：5， 视频会议桥：6 MTP【MeetingType】
	 */
	private String MTP;
	/**
	 * 接入号码 电话会议类型返回 AP【AccessPhone】
	 */
	private String AP;
	/**
	 * 会议编号 电话会议、视频会议类型返回 MNB【MeetingNumber】
	 */
	private String MNB;

	/**
	 * 会议密码 电话会议、视频会议类型返回 MPWD【MeetingPassword】
	 */
	private String MPWD;

	/**
	 * 是否可以延长 Y:是 N:否 【IsProlong】
	 */
	private String IPRL;

	
	/**
	 * 是否可以会控	Y:是 N:否	IEC	
	 */
	private String IEC;

	/**
	 * 是否已经签到	Y:是 N:否	ISI	
	 */
	private String ISI;
	
	
	/**
	 * 会议ID Not null MID【MeetingID】
	 */
	public String getMID() {
		return MID;
	}

	/**
	 * 会议ID Not null MID【MeetingID】
	 */
	public void setMID(String mID) {
		MID = mID;
	}

	/**
	 * 会议名称 MN 【MeetingName】
	 */
	public String getMN() {
		return MN;
	}

	/**
	 * 会议名称 MN 【MeetingName】
	 */
	public void setMN(String mN) {
		MN = mN;
	}

	/**
	 * 预定会议室ID串 用逗号隔开，如： 5871,2586,2547 BMRIDS【BookingsMeetingRoomIds】
	 */
	public String getBMRIDS() {
		return BMRIDS;
	}

	/**
	 * 预定会议室ID串 用逗号隔开，如： 5871,2586,2547 BMRIDS【BookingsMeetingRoomIds】
	 */
	public void setBMRIDS(String bMRIDS) {
		BMRIDS = bMRIDS;
	}

	/**
	 * 开始时间 Not null BD【BeginDate】
	 */
	public String getBD() {
		return BD;
	}

	/**
	 * 开始时间 Not null BD【BeginDate】
	 */
	public void setBD(String bD) {
		BD = bD;
	}

	/**
	 * 格式为： YYYY-MM-DD hh:mm:ss 结束时间 Not null ED【EndDate】
	 */
	public String getED() {
		return ED;
	}

	/**
	 * 格式为： YYYY-MM-DD hh:mm:ss 结束时间 Not null ED【EndDate】
	 */
	public void setED(String eD) {
		ED = eD;
	}

	/**
	 * 格式为： YYYY-MM-DD hh:mm:ss 会议时长 Not null MT【MeetingTime】
	 */
	public String getMT() {
		return MT;
	}

	/**
	 * 格式为： YYYY-MM-DD hh:mm:ss 会议时长 Not null MT【MeetingTime】
	 */
	public void setMT(String mT) {
		MT = mT;
	}

	/**
	 * 会议级别 会议级别： 1->A级 2-B级 3->C级 对应参会人级别,其中3对应参会人级别的3和4 ML【MeetingLevel】
	 */
	public String getML() {
		return ML;
	}

	/**
	 * 会议级别 会议级别： 1->A级 2-B级 3->C级 对应参会人级别,其中3对应参会人级别的3和4 ML【MeetingLevel】
	 */
	public void setML(String mL) {
		ML = mL;
	}

	/**
	 * 组织人工号 OPN【OrganizePeopleNo】
	 * 
	 */
	public String getOPN() {
		return OPN;
	}

	/**
	 * 组织人工号 OPN【OrganizePeopleNo】
	 * 
	 */
	public void setOPN(String oPN) {
		OPN = oPN;
	}

	/**
	 * 组织人中文姓名 OPCN【OrganizePeopleChineseName】
	 * 
	 */
	public String getOPCN() {
		return OPCN;
	}

	/**
	 * 组织人中文姓名 OPCN【OrganizePeopleChineseName】
	 * 
	 */
	public void setOPCN(String oPCN) {
		OPCN = oPCN;
	}

	/**
	 * 参会领导级别 领导级别： 1->二层领导 2->三层领导 3->四层领导 4->其他
	 * PMLL【ParticipateMeetingLeaderLevel】
	 */
	public String getPMLL() {
		return PMLL;
	}

	/**
	 * 参会领导级别 领导级别： 1->二层领导 2->三层领导 3->四层领导 4->其他
	 * PMLL【ParticipateMeetingLeaderLevel】
	 */
	public void setPMLL(String pMLL) {
		PMLL = pMLL;
	}

	/**
	 * 申请时间 AT【ApplyTime】
	 */
	public String getAT() {
		return AT;
	}

	/**
	 * 申请时间 AT【ApplyTime】
	 */
	public void setAT(String aT) {
		AT = aT;
	}

	/**
	 * 格式为： YYYY-MM-DD hh:mm:ss 会议状态 会议状态: 1->草稿 2->待分配 3->已预订 4->已服务 5->已结束
	 * 6->已回访 7->已退订 8->已取消 9->已删除 10-已关闭 MS【MeetingState】
	 */
	public String getMS() {
		return MS;
	}

	/**
	 * 格式为： YYYY-MM-DD hh:mm:ss 会议状态 会议状态: 1->草稿 2->待分配 3->已预订 4->已服务 5->已结束
	 * 6->已回访 7->已退订 8->已取消 9->已删除 10-已关闭 MS【MeetingState】
	 */
	public void setMS(String mS) {
		MS = mS;
	}

	/**
	 * 操作状态 0 不可操作；1 可退订； 2 可结束； OS【OperatingState】
	 */
	public String getOS() {
		return OS;
	}

	/**
	 * 操作状态 0 不可操作；1 可退订； 2 可结束； OS【OperatingState】
	 */
	public void setOS(String oS) {
		OS = oS;
	}

	/**
	 * 参会方数 预定电话/视频会议使用 PN【ParticipantsNumber】
	 */
	public String getPN() {
		return PN;
	}

	/**
	 * 参会方数 预定电话/视频会议使用 PN【ParticipantsNumber】
	 */
	public void setPN(String pN) {
		PN = pN;
	}

	/**
	 * 会议类型 常规会议：1， 电话会议桥：2， 培训会议：3， 网真会议：4， 云招标会议：5， 视频会议桥：6 MTP【MeetingType】
	 */
	public String getMTP() {
		return MTP;
	}

	/**
	 * 会议类型 常规会议：1， 电话会议桥：2， 培训会议：3， 网真会议：4， 云招标会议：5， 视频会议桥：6 MTP【MeetingType】
	 */
	public void setMTP(String mTP) {
		MTP = mTP;
	}

	/**
	 * 接入号码 电话会议类型返回 AP【AccessPhone】
	 */
	public String getAP() {
		return AP;
	}

	/**
	 * 接入号码 电话会议类型返回 AP【AccessPhone】
	 */
	public void setAP(String aP) {
		AP = aP;
	}

	/**
	 * 会议编号 电话会议、视频会议类型返回 MNB【MeetingNumber】
	 */
	public String getMNB() {
		return MNB;
	}

	/**
	 * 会议编号 电话会议、视频会议类型返回 MNB【MeetingNumber】
	 */
	public void setMNB(String mNB) {
		MNB = mNB;
	}

	/**
	 * 会议密码 电话会议、视频会议类型返回 MPWD【MeetingPassword】
	 */
	public String getMPWD() {
		return MPWD;
	}

	/**
	 * 会议密码 电话会议、视频会议类型返回 MPWD【MeetingPassword】
	 */
	public void setMPWD(String mPWD) {
		MPWD = mPWD;
	}

	/**
	 * 是否可以延长 Y:是 N:否 【IsProlong】
	 */
	public String getIPRL() {
		return IPRL;
	}

	/**
	 * 是否可以延长 Y:是 N:否 【IsProlong】
	 */
	public void setIPRL(String iPRL) {
		IPRL = iPRL;
	}
	/**
	 * 是否可以会控	Y:是 N:否	IEC	
	 */
	public String getIEC() {
		return IEC;
	}
	/**
	 * 是否可以会控	Y:是 N:否	IEC	
	 */
	public void setIEC(String iEC) {
		IEC = iEC;
	}
	/**
	 * 是否已经签到	Y:是 N:否	ISI	
	 */
	public String getISI() {
		return ISI;
	}
	/**
	 * 是否已经签到	Y:是 N:否	ISI	
	 */
	public void setISI(String iSI) {
		ISI = iSI;
	}

}
