package cn.com.zte.emeeting.app.response.entity;

import java.util.List;

import com.google.gson.annotations.Expose;
import cn.com.zte.android.app.common.vo.BaseVO;
import cn.com.zte.android.http.model.BaseHttpResponse;

/**
 * 会议室信息对象实体
 * 
 * @author sun.li
 * */
public class MeetingRoomInfo extends BaseVO {

	/** 序列化ID */
	@Expose(serialize = false, deserialize = false)
	private static final long serialVersionUID = 5480999225195225132L;

	/**
	 * 会议室ID Not null MRID【MeetingRoomId】
	 */
	private String MRID;

	/**
	 * 会议室名称 Not null MRN【MeetingRoomName】
	 */
	private String MRN;

	/**
	 * 会议室地址ID串 会议室详情接口返回 MRAIDS【MeetingRoomAddressIds】
	 */
	private String MRAIDS;

	/**
	 * 会议室规模 会议室详情接口返回 MRS【MeetingRoomScale】
	 */
	private String MRS;

	/**
	 * 投影仪状态 0->没有 1->不支持双流 2->支持双流接收 3->支持双流接收发送 会议室详情接口返回 PJS【ProjectorState】
	 */
	private String PJS;

	/**
	 * 电视状态 0->没有 1->有 会议室详情接口返回 TVS【TelevisionState】
	 */
	private String TVS;

	/**
	 * 电话状态 0->没有 1-> POLYCOM 2-> USB-Phone 会议室详情接口返回 PS【PhoneState】
	 */
	private String PS;
	/**
	 * 【会议信息对象】集合 只返回会议ID、开始时间、结束时间、时长 HBMIS【HaveBookingMeetingInfos】
	 */
	private List<MeetingInfo> HBMIS;
	
	/**
	 * 会议剩余比例	会议预定当天不限时间段查询返回	
	 * MROR【MeetingRoomOccupancyRation】	
	 */
	private String MROR;
	
	/**
	 * 会议室ID Not null MRID【MeetingRoomId】
	 */
	public String getMRID() {
		return MRID;
	}
	/**
	 * 会议室ID Not null MRID【MeetingRoomId】
	 */
	public void setMRID(String mRID) {
		MRID = mRID;
	}
	/**
	 * 会议室名称 Not null MRN【MeetingRoomName】
	 */
	public String getMRN() {
		return MRN;
	}
	/**
	 * 会议室名称 Not null MRN【MeetingRoomName】
	 */
	public void setMRN(String mRN) {
		MRN = mRN;
	}
	/**
	 * 会议室地址ID串 会议室详情接口返回 MRAIDS【MeetingRoomAddressIds】
	 */
	public String getMRAIDS() {
		return MRAIDS;
	}
	/**
	 * 会议室地址ID串 会议室详情接口返回 MRAIDS【MeetingRoomAddressIds】
	 */
	public void setMRAIDS(String mRAIDS) {
		MRAIDS = mRAIDS;
	}
	/**
	 * 会议室规模 会议室详情接口返回 MRS【MeetingRoomScale】
	 */
	public String getMRS() {
		return MRS;
	}
	/**
	 * 会议室规模 会议室详情接口返回 MRS【MeetingRoomScale】
	 */
	public void setMRS(String mRS) {
		MRS = mRS;
	}
	/**
	 * 投影仪状态 0->没有 1->不支持双流 2->支持双流接收 3->支持双流接收发送 会议室详情接口返回 PJS【ProjectorState】
	 */
	public String getPJS() {
		return PJS;
	}
	/**
	 * 投影仪状态 0->没有 1->不支持双流 2->支持双流接收 3->支持双流接收发送 会议室详情接口返回 PJS【ProjectorState】
	 */
	public void setPJS(String pJS) {
		PJS = pJS;
	}
	/**
	 * 电视状态 0->没有 1->有 会议室详情接口返回 TVS【TelevisionState】
	 */
	public String getTVS() {
		return TVS;
	}
	/**
	 * 电视状态 0->没有 1->有 会议室详情接口返回 TVS【TelevisionState】
	 */
	public void setTVS(String tVS) {
		TVS = tVS;
	}
	/**
	 * 电话状态 0->没有 1-> POLYCOM 2-> USB-Phone 会议室详情接口返回 PS【PhoneState】
	 */
	public String getPS() {
		return PS;
	}
	/**
	 * 电话状态 0->没有 1-> POLYCOM 2-> USB-Phone 会议室详情接口返回 PS【PhoneState】
	 */
	public void setPS(String pS) {
		PS = pS;
	}
	/**
	 * 【会议信息对象】集合 只返回会议ID、开始时间、结束时间、时长 HBMIS【HaveBookingMeetingInfos】
	 */
	public List<MeetingInfo> getHBMIS() {
		return HBMIS;
	}
	/**
	 * 【会议信息对象】集合 只返回会议ID、开始时间、结束时间、时长 HBMIS【HaveBookingMeetingInfos】
	 */
	public void setHBMIS(List<MeetingInfo> hBMIS) {
		HBMIS = hBMIS;
	}
	/**
	 * 会议剩余比例	会议预定当天不限时间段查询返回	
	 * MROR【MeetingRoomOccupancyRation】	
	 */
	public String getMROR() {
		return MROR;
	}
	/**
	 * 会议剩余比例	会议预定当天不限时间段查询返回	
	 * MROR【MeetingRoomOccupancyRation】	
	 */
	public void setMROR(String mROR) {
		MROR = mROR;
	}

	
}
