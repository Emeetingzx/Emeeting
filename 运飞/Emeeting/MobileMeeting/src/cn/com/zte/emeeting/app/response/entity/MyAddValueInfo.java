package cn.com.zte.emeeting.app.response.entity;

import java.util.List;

import com.google.gson.annotations.Expose;
import cn.com.zte.android.app.common.vo.BaseVO;

/**
 * 我的增值服务信息对象实体
 * 
 * @author sun.li
 * */
public class MyAddValueInfo extends BaseVO {

	/** 序列化ID */
	@Expose(serialize = false, deserialize = false)
	private static final long serialVersionUID = 5480999225295225132L;

	/**
	 * 订单服务ID ID
	 */
	private String ID;
	/**
	 * 订单编号【OrderNumber】
	 */
	private String ON;
	/**
	 * 订单状态【OrderState】
	 */
	private String OS;
	/**
	 * 服务地点 【ServiceAddress】
	 */
	private String SA;
	/**
	 * 预定时间【ServiceDate】
	 */
	private String SD;
	/**
	 * 食品茶点对象集合 【食品茶点对象】集合 【FoodAndRefreshmentsInfos】 Json数组
	 */
	private List<FoodAndRefreshmentsInfo> FARIS;
	/**
	 * 操作项 不可操作 可退订 【ActionItems】
	 */
	private String AIS;

	/**
	 * 最后更新时间
	 */
	private String LUD;
	/**
	 * 订单服务ID ID
	 */
	public String getID() {
		return ID;
	}

	/**
	 * 订单服务ID ID
	 */
	public void setID(String iD) {
		ID = iD;
	}

	/**
	 * 订单编号【OrderNumber】
	 */
	public String getON() {
		return ON;
	}

	/**
	 * 订单编号【OrderNumber】
	 */
	public void setON(String oN) {
		ON = oN;
	}

	/**
	 * 订单状态【OrderState】
	 */
	public String getOS() {
		return OS;
	}

	/**
	 * 订单状态【OrderState】
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
	 * 预定时间【ServiceDate】
	 */
	public String getSD() {
		return SD;
	}

	/**
	 * 预定时间【ServiceDate】
	 */
	public void setSD(String sD) {
		SD = sD;
	}

	/**
	 * 食品茶点对象集合 【食品茶点对象】集合 【FoodAndRefreshmentsInfos】 Json数组
	 */
	public List<FoodAndRefreshmentsInfo> getFARIS() {
		return FARIS;
	}

	/**
	 * 食品茶点对象集合 【食品茶点对象】集合 【FoodAndRefreshmentsInfos】 Json数组
	 */
	public void setFARIS(List<FoodAndRefreshmentsInfo> fARIS) {
		FARIS = fARIS;
	}

	/**
	 * 操作项 不可操作 可退订 【ActionItems】
	 */
	public String getAIS() {
		return AIS;
	}

	/**
	 * 操作项 不可操作 可退订 【ActionItems】
	 */
	public void setAIS(String aIS) {
		AIS = aIS;
	}
	/**
	 * 最后更新时间
	 */
	public String getLUD() {
		return LUD;
	}
	/**
	 * 最后更新时间
	 */
	public void setLUD(String lUD) {
		LUD = lUD;
	}

}
