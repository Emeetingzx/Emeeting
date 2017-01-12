package cn.com.zte.emeeting.app.response.entity;

import java.util.List;

import cn.com.zte.android.app.common.vo.BaseVO;

/**
 * 管理员相关增值服务信息对象实体
 * 
 * @author LangK
 * 
 */
public class AdminAddValueInfo extends BaseVO {

	private static final long serialVersionUID = -2693037791253317433L;
	/**
	 * 订单服务ID
	 */
	private String ID;
	/**
	 * 订单编号 【OrderNumber】
	 */
	private String ON;
	/**
	 * 订单状态 【OrderState】
	 */
	private String OS;
	/**
	 * 服务地点 【ServiceAddress】
	 */
	private String SA;
	/**
	 * 预定时间 【ServiceDate】
	 */
	private String SD;
	/**
	 * 实物茶点对象集合 【食品茶点对象】集合
	 */
	List<FoodAndRefreshmentsInfo> FARIS;
	/**
	 * 提单人 人员名称加工号,使用逗号隔开 【BillOfLadingUser】
	 */
	private String BOLU;
	/**
	 * 联系电话 【Phone】
	 */
	private String PE;
	/**
	 * 操作项串 多个操作项使用逗号隔开 接单 服务 退单 【ActionItems】
	 */
	private String AIS;

	/**
	 * 订单服务ID
	 */
	public String getID() {
		return ID;
	}

	/**
	 * 订单服务ID
	 */
	public void setID(String iD) {
		ID = iD;
	}

	/**
	 * 订单编号 【OrderNumber】
	 */
	public String getON() {
		return ON;
	}

	/**
	 * 订单编号 【OrderNumber】
	 */
	public void setON(String oN) {
		ON = oN;
	}

	/**
	 * 订单状态 【OrderState】
	 */
	public String getOS() {
		return OS;
	}

	/**
	 * 订单状态 【OrderState】
	 */
	public void setOS(String oS) {
		OS = oS;
	}

	/**
	 * 服务地点 【ServiceAddress】
	 */
	public String getSA() {
		return SA;
	}

	/**
	 * 服务地点 【ServiceAddress】
	 */
	public void setSA(String sA) {
		SA = sA;
	}

	/**
	 * 预定时间 【ServiceDate】
	 */
	public String getSD() {
		return SD;
	}

	/**
	 * 预定时间 【ServiceDate】
	 */
	public void setSD(String sD) {
		SD = sD;
	}

	/**
	 * 实物茶点对象集合 【食品茶点对象】集合
	 */
	public List<FoodAndRefreshmentsInfo> getFARIS() {
		return FARIS;
	}

	/**
	 * 实物茶点对象集合 【食品茶点对象】集合
	 */
	public void setFARIS(List<FoodAndRefreshmentsInfo> fARIS) {
		FARIS = fARIS;
	}

	/**
	 * 提单人 人员名称加工号,使用逗号隔开 【BillOfLadingUser】
	 */
	public String getBOLU() {
		return BOLU;
	}

	/**
	 * 提单人 人员名称加工号,使用逗号隔开 【BillOfLadingUser】
	 */
	public void setBOLU(String bOLU) {
		BOLU = bOLU;
	}

	/**
	 * 联系电话 【Phone】
	 */
	public String getPE() {
		return PE;
	}

	/**
	 * 联系电话 【Phone】
	 */
	public void setPE(String pE) {
		PE = pE;
	}

	/**
	 * 操作项串 多个操作项使用逗号隔开 接单 服务 退单 【ActionItems】
	 */
	public String getAIS() {
		return AIS;
	}

	/**
	 * 操作项串 多个操作项使用逗号隔开 接单 服务 退单 【ActionItems】
	 */
	public void setAIS(String aIS) {
		AIS = aIS;
	}

}
