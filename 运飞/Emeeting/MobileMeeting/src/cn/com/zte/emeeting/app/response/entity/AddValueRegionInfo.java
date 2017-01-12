package cn.com.zte.emeeting.app.response.entity;

import java.util.List;

import cn.com.zte.android.app.common.vo.BaseVO;

/**
 * 管理员相关增值服务信息对象实体
 * 
 * @author LangK
 * 
 */
public class AddValueRegionInfo extends BaseVO {

	private static final long serialVersionUID = -2693037791253317433L;
	/**
	 * 增值服务地区ID
	 */
	private String ID;
	/**
	 * 增值服务地区名称 【AddValueServiceRegionName】
	 */
	private String AVSRN;
	/**  
	 * 获取增值服务地区ID
	 */
	public String getID() {
		return ID;
	}
	/**  
	 * 设置增值服务地区ID
	 */
	public void setID(String iD) {
		ID = iD;
	}
	/**  
	 * 获取增值服务地区名称 【AddValueServiceRegionName】
	 */
	public String getAVSRN() {
		return AVSRN;
	}
	/**  
	 * 设置增值服务地区名称 【AddValueServiceRegionName】
	 */
	public void setAVSRN(String aVSRN) {
		AVSRN = aVSRN;
	}

}
