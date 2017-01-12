package cn.com.zte.emeeting.app.response.entity;

import cn.com.zte.android.app.common.vo.BaseVO;

/**
 * Created by LangK on 16-3-10. 3.3.2.7 参会人员签到对象实体
 */
public class MeetingAttendanceInfo extends BaseVO {

	private static final long serialVersionUID = 2773598233252333764L;

	/**
	 * 参会人员姓名 【MeetingAttendanceName】
	 */
	private String MANA;
	/**
	 * 参会人员工号 【MeetingAttendanceNumber】
	 */
	private String MANU;
	/**
	 * 状态 0：未签到，1已签到 ST【State】
	 */
	private String ST;
	/**
	 * 签到时间 状态为已签到时非空 【AttendanceTime】
	 */
	private String AT;

	/**
	 * 参会人员姓名 【MeetingAttendanceName】
	 */
	public String getMANA() {
		return MANA;
	}

	/**
	 * 参会人员姓名 【MeetingAttendanceName】
	 */
	public void setMANA(String mANA) {
		MANA = mANA;
	}

	/**
	 * 参会人员工号 【MeetingAttendanceNumber】
	 */
	public String getMANU() {
		return MANU;
	}

	/**
	 * 参会人员工号 【MeetingAttendanceNumber】
	 */
	public void setMANU(String mANU) {
		MANU = mANU;
	}

	/**
	 * 状态 0：未签到，1已签到 ST【State】
	 */
	public String getST() {
		return ST;
	}

	/**
	 * 状态 0：未签到，1已签到 ST【State】
	 */
	public void setST(String sT) {
		ST = sT;
	}

	/**
	 * 签到时间 状态为已签到时非空 【AttendanceTime】
	 */
	public String getAT() {
		return AT;
	}

	/**
	 * 签到时间 状态为已签到时非空 【AttendanceTime】
	 */
	public void setAT(String aT) {
		AT = aT;
	}

}
