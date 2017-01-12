package cn.com.zte.emeeting.app.response.entity;

import com.google.gson.annotations.Expose;
import cn.com.zte.android.app.common.vo.BaseVO;

/**
 * 会议操作信息对象实体
 * 
 * @author sun.li
 * */
public class MeetingOperationInfo extends BaseVO {

	/** 序列化ID */
	@Expose(serialize = false, deserialize = false)
	private static final long serialVersionUID = 5580999225195225132L;

	/**
	 * 会议ID Not null MID【MeetingID】
	 */
	private String MID;

	/**
	 * OperatingState 操作状态： 0不可操作；1 可退订；2可结束；
	 */
	private String OS;

	/**
	 * OperatingState 操作状态： 0不可操作；1 可退订；2可结束；
	 */
	public String getOS() {
		return OS;
	}

	/**
	 * OperatingState 操作状态： 0不可操作；1 可退订；2可结束；
	 */
	public void setOS(String oS) {
		OS = oS;
	}

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

}
