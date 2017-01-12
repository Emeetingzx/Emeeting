package cn.com.zte.emeeting.app.request.entity;

import cn.com.zte.android.app.common.vo.BaseVO;
/**
 * 筛选条件对象实体
 * @author LangK
 *
 */
public class ScreeningCondition extends BaseVO {

	private static final long serialVersionUID = -6045148470407976935L;

	/**
	 * 会议室地址ID 需要传递最小节点ID（最小节点为楼层，选择到哪个节点就传哪个节点） MRAIDS【MeetingRoomAddressIds】
	 */
	private String MRAIDS;
	/**
	 * 投影仪状态 0->没有 1->不支持双流 2->支持双流接收 3->支持双流接收发送 PJS【ProjectorState】
	 */
	private String PJS;
	/**
	 * 电视状态 0->没有 1->有 TVS【TelevisionState】
	 */
	private String TVS;
	/**
	 * 电话状态 0->没有 1-> POLYCOM 2-> USB-Phone PS【PhoneState】
	 */
	private String PS;
	/**
	 * 参会人数 0->不限 1->10人以下 2->10-30人 3->30-60人 4->60人以上 PN【ParticipantsNumber】
	 */
	private String PN;

	/**
	 * 会议室地址ID 需要传递最小节点ID（最小节点为楼层，选择到哪个节点就传哪个节点） MRAIDS【MeetingRoomAddressIds】
	 */
	public String getMRAIDS() {
		return MRAIDS;
	}

	/**
	 * 会议室地址ID 需要传递最小节点ID（最小节点为楼层，选择到哪个节点就传哪个节点） MRAIDS【MeetingRoomAddressIds】
	 */
	public void setMRAIDS(String MRAIDS) {
		this.MRAIDS = MRAIDS;
	}

	/**
	 * 投影仪状态 0->没有 1->不支持双流 2->支持双流接收 3->支持双流接收发送 PJS【ProjectorState】
	 */
	public String getPJS() {
		return PJS;
	}

	/**
	 * 投影仪状态 0->没有 1->不支持双流 2->支持双流接收 3->支持双流接收发送 PJS【ProjectorState】
	 */
	public void setPJS(String PJS) {
		this.PJS = PJS;
	}

	/**
	 * 电视状态 0->没有 1->有 TVS【TelevisionState】
	 */
	public String getTVS() {
		return TVS;
	}

	/**
	 * 电视状态 0->没有 1->有 TVS【TelevisionState】
	 */
	public void setTVS(String TVS) {
		this.TVS = TVS;
	}

	/**
	 * 电话状态 0->没有 1-> POLYCOM 2-> USB-Phone PS【PhoneState】
	 */
	public String getPS() {
		return PS;
	}

	/**
	 * 电话状态 0->没有 1-> POLYCOM 2-> USB-Phone PS【PhoneState】
	 */
	public void setPS(String PS) {
		this.PS = PS;
	}

	/**
	 * 参会人数 0->不限 1->10人以下 2->10-30人 3->30-60人 4->60人以上 PN【ParticipantsNumber】
	 */
	public String getPN() {
		return PN;
	}

	/**
	 * 参会人数 0->不限 1->10人以下 2->10-30人 3->30-60人 4->60人以上 PN【ParticipantsNumber】
	 */
	public void setPN(String PN) {
		this.PN = PN;
	}

}
