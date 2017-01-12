package cn.com.zte.emeeting.app.response.entity;

import com.google.gson.annotations.Expose;

import cn.com.zte.android.app.common.vo.BaseVO;

/**
 * Created by LangK on 16-3-10. 3.3.2.7 参会的会议室/人员对象实体
 */
public class MeetingJoinInfo extends BaseVO {

	/**
	 * 会议类型
	 */
	@Expose(deserialize = false, serialize = false)
	public static final String MEETINGTYPE = "0";
	/**
	 * 手机联系人类型
	 */
	@Expose(deserialize = false, serialize = false)
	public static final String PHONETYPE = "1";
	/**
	 * 固话类型
	 */
	@Expose(deserialize = false, serialize = false)
	public static final String TELTYPE = "2";

	/**
	 * 拨号操作
	 */
	@Expose(deserialize = false, serialize = false)
	public static final String CALLOPARETION = "1";

	/**
	 * 挂断操作
	 */
	@Expose(deserialize = false, serialize = false)
	public static final String HANGUPOPARETION = "0";

	/**
	 * 静音操作
	 */
	@Expose(deserialize = false, serialize = false)
	public static final String QUEITOPARETION = "2";

	/**
	 * 取消静音操作
	 */
	@Expose(deserialize = false, serialize = false)
	public static final String RESTORECALLOPARETION = "3";

	/**
	 * 删除操作
	 */
	@Expose(deserialize = false, serialize = false)
	public static final String DELETEOPARETION = "4";

	/**
	 * 通话非静音状态
	 */
	@Expose(deserialize = false, serialize = false)
	public static final String CALLSTATE = "2";

	/**
	 * 挂断状态
	 */
	@Expose(deserialize = false, serialize = false)
	public static final String HANGUPSTATE = "0";

	/**
	 * 拨号中状态
	 */
	@Expose(deserialize = false, serialize = false)
	public static final String CALLINGSTATE = "1";

	/**
	 * 通话静音状态
	 */
	@Expose(deserialize = false, serialize = false)
	public static final String QUEITSTATE = "3";

	private static final long serialVersionUID = 2773598233213333764L;

	/**
	 * 终端ID
	 */
	private String ID;
	/**
	 * 终端名称
	 */
	private String TN;
	/**
	 * 状态 状态：0挂断，2，拨通中非静音状态，3拨通中静音状态 ST【State】
	 */
	private String ST;
	/**
	 * 终端号码
	 */
	private String NO;
	/**
	 * 结束时间
	 * 如果该会议终端与下场会议有冲突，需要返回该会议的结束时间
	 */
	private String ET;
	
	/**
	 * 终端类型
	 *  0 GK号   1 内线   2 外线
	 */
	private String TT;
	
	
	/**
	 * 终端ID
	 */
	public String getID() {
		return ID;
	}

	/**
	 * 终端ID
	 */
	public void setID(String iD) {
		ID = iD;
	}

	/**
	 * 终端名称
	 */
	public String getTN() {
		return TN;
	}

	/**
	 * 终端名称
	 */
	public void setTN(String tN) {
		TN = tN;
	}

	/**
	 * 状态 状态：0挂断，2，拨通中非静音状态，3拨通中静音状态 ST【State】
	 */
	public String getST() {
		return ST;
	}

	/**
	 * 状态 状态：0挂断，2，拨通中非静音状态，3拨通中静音状态 ST【State】
	 */
	public void setST(String sT) {
		ST = sT;
	}

	/**
	 * 终端号码
	 */
	public String getNO() {
		return NO;
	}

	/**
	 * 终端号码
	 */
	public void setNO(String nO) {
		NO = nO;
	}
	/**
	 * 结束时间
	 * 如果该会议终端与下场会议有冲突，需要返回该会议的结束时间
	 */
	public String getET() {
		return ET;
	}
	/**
	 * 结束时间
	 * 如果该会议终端与下场会议有冲突，需要返回该会议的结束时间
	 */
	public void setET(String eT) {
		ET = eT;
	}
	/**
	 * 终端类型
	 *  0 GK号   1 内线   2 外线
	 */
	public String getTT() {
		return TT;
	}
	/**
	 * 终端类型
	 *  0 GK号   1 内线   2 外线
	 */
	public void setTT(String tT) {
		TT = tT;
	}

}
