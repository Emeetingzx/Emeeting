package cn.com.zte.emeeting.app.response.entity;

import cn.com.zte.android.app.common.vo.BaseVO;

/**
 * 食品茶点对象实体
 * 
 * @author LangK
 * 
 */
public class FoodAndRefreshmentsInfo extends BaseVO {

	private static final long serialVersionUID = 1927707772398104791L;

	public FoodAndRefreshmentsInfo(String id) {
		this.ID = id;
	}
	public FoodAndRefreshmentsInfo() {
	}
	/**
	 * 增值服务ID ID
	 */
	private String ID;
	/**
	 * 增值服务名称 【AddValueServiceName】
	 */
	private String AVSN;
	/**
	 * 增值服务图片路径 URL路径 【AddValueServicePath】
	 */
	private String AVSP;

	/**
	 * 增值服务ID
	 */
	public String getID() {
		return ID;
	}

	/**
	 * 增值服务ID ID
	 */
	public void setID(String iD) {
		ID = iD;
	}

	/**
	 * 增值服务名称 【AddValueServiceName】
	 */
	public String getAVSN() {
		return AVSN;
	}

	/**
	 * 增值服务名称 【AddValueServiceName】
	 */
	public void setAVSN(String aVSN) {
		AVSN = aVSN;
	}

	/**
	 * 增值服务图片路径 URL路径 【AddValueServicePath】
	 */
	public String getAVSP() {
		return AVSP;
	}

	/**
	 * 增值服务图片路径 URL路径 【AddValueServicePath】
	 */
	public void setAVSP(String aVSP) {
		AVSP = aVSP;
	}

}
