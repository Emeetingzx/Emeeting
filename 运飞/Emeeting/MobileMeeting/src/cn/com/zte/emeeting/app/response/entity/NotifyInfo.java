package cn.com.zte.emeeting.app.response.entity;

import cn.com.zte.android.app.common.vo.BaseVO;

/**
 * Created by LangK on 16-3-10. 3.3.2.7 通知类实体对象
 */
public class NotifyInfo extends BaseVO {

	private static final long serialVersionUID = 2773598233253322764L;

	/**
	 * 通知类型 0：会议相关通知 1：增值服务通知 NT【NotifyType】 String
	 */
	private String NT;

	/**
	 * 会议ID 会议相关通知时非空 MID【MeetingID】 String
	 */
	private String MID;
	
	/**
	 * 会议类型 0：普通会议，1：视频会议
	 */
	private String MT;

	/**
	 * 通知内容 NC【NotifyContent】 String
	 */
	private String NC;

	/**
	 * 会议通知类型 0：预定通知、提前30分钟通知、变更通知 1：会议退订、结束通知 2：签到通知 MNT【MeetingNotifyType】
	 * String
	 */
	private String MNT;
	
	/**
	 * 增值服务类型 0，新订单，管理员使用，通知普通用户。1：订单状态改变，通知普通用户
	 */
	private String VNT;

	/**
	 * 通知类型 0：会议相关通知 1：增值服务通知 NT【NotifyType】 String
	 */
	public String getNT() {
		return NT;
	}

	/**
	 * 通知类型 0：会议相关通知 1：增值服务通知 NT【NotifyType】 String
	 */
	public void setNT(String nT) {
		NT = nT;
	}

	/**
	 * 会议ID 会议相关通知时非空 MID【MeetingID】 String
	 */
	public String getMID() {
		return MID;
	}

	/**
	 * 会议ID 会议相关通知时非空 MID【MeetingID】 String
	 */
	public void setMID(String mID) {
		MID = mID;
	}

	/**
	 * 通知内容 NC【NotifyContent】 String
	 */
	public String getNC() {
		return NC;
	}

	/**
	 * 通知内容 NC【NotifyContent】 String
	 */
	public void setNC(String nC) {
		NC = nC;
	}

	/**
	 * 会议通知类型 0：预定通知、提前30分钟通知、变更通知 1：会议退订、结束通知 2：签到通知 MNT【MeetingNotifyType】
	 * String
	 */
	public String getMNT() {
		return MNT;
	}

	/**
	 * 会议通知类型 0：预定通知、提前30分钟通知、变更通知 1：会议退订、结束通知 2：签到通知 MNT【MeetingNotifyType】
	 * String
	 */
	public void setMNT(String mNT) {
		MNT = mNT;
	}
	/**
	 * 增值服务类型 0，新订单，管理员使用，通知普通用户。1：订单状态改变，通知普通用户
	 */
	public String getVNT() {
		return VNT;
	}
	/**
	 * 增值服务类型 0，新订单，管理员使用，通知普通用户。1：订单状态改变，通知普通用户
	 */
	public void setVNT(String VNT) {
		this.VNT = VNT;
	}
	/**
	 * 会议类型 0：普通会议，1：视频会议
	 */
	public String getMT() {
		return MT;
	}
	/**
	 * 会议类型 0：普通会议，1：视频会议
	 */
	public void setMT(String mT) {
		MT = mT;
	}

}
