package cn.com.zte.emeeting.app.response.entity;

import cn.com.zte.android.app.common.vo.BaseVO;

/**
 * Created by LangK on 16-3-10. 3.3.2.7 会议室时间延长冲突信息对象实体
 */
public class MeetingProLong extends BaseVO {

	private static final long serialVersionUID = 2773598233253322764L;

	/**
	 * 会议室ID MRID【MeetingRoomID】 String
	 */
	private String MRID;
	/**
	 * 是否可以延长 Y：是 N：否 IPL【isProlong】 String
	 */
	private String IPL;
	/**
	 * 最长可延长时间 PLT【ProlongTime】 String
	 */
	private String PLT;

	/**
	 * 会议室ID MRID【MeetingRoomID】 String
	 */
	public String getMRID() {
		return MRID;
	}

	/**
	 * 会议室ID MRID【MeetingRoomID】 String
	 */
	public void setMRID(String mRID) {
		MRID = mRID;
	}

	/**
	 * 是否可以延长 Y：是 N：否 IPL【isProlong】 String
	 */
	public String getIPL() {
		return IPL;
	}

	/**
	 * 是否可以延长 Y：是 N：否 IPL【isProlong】 String
	 */
	public void setIPL(String iPL) {
		IPL = iPL;
	}

	/**
	 * 最长可延长时间 PLT【ProlongTime】 String
	 */
	public String getPLT() {
		return PLT;
	}

	/**
	 * 最长可延长时间 PLT【ProlongTime】 String
	 */
	public void setPLT(String pLT) {
		PLT = pLT;
	}

}
