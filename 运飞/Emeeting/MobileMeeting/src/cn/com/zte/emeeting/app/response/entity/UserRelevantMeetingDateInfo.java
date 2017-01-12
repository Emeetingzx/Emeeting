package cn.com.zte.emeeting.app.response.entity;

import java.util.List;

import cn.com.zte.android.app.common.vo.BaseVO;

/**
 * 与我有关所有会议所在日期信息对象
 * 
 * @author LangK
 * 
 */
public class UserRelevantMeetingDateInfo extends BaseVO {

	private static final long serialVersionUID = -2693037791253317433L;
	
	/**
	 * 会议所在日期【MeetingsDate】
	 */
	private String MD;
	
	/**
	 * 当天会议数量 【TheDayMettingNumber】
	 */
	private String TDMN;
	
	/**  
	 * 获取会议所在日期【MeetingsDate】
	 */
	public String getMD() {
		return MD;
	}
	
	/**  
	 * 设置会议所在日期【MeetingsDate】
	 */
	public void setMD(String mD) {
		MD = mD;
	}
	
	/**  
	 * 获取当天会议数量 【TheDayMettingNumber】
	 */
	public String getTDMN() {
		return TDMN;
	}
	
	/**  
	 * 设置当天会议数量 【TheDayMettingNumber】
	 */
	public void setTDMN(String tDMN) {
		TDMN = tDMN;
	}

}
