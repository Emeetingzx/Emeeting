package cn.com.zte.emeeting.app.response.entity;

import com.google.gson.annotations.Expose;

import cn.com.zte.android.app.common.vo.BaseVO;

/**
 * 常见问题对象实体类
 * 
 * @author sun.li
 * */
public class CommonProblemsInfo extends BaseVO {

	@Expose(serialize = false, deserialize = false)
	private static final long serialVersionUID = 8320492097556425034L;

	/** 常见问题内容 */
	private String commonProblemsContent;

	/** 常见问题标识 */
	private int commonProblemsLogo;

	/**
	 *取得常见问题内容
	 * @return the commonProblemsContent
	 */
	public String getCommonProblemsContent() {
		return commonProblemsContent;
	}

	/**
	 *设置常见问题内容
	 * @param commonProblemsContent the commonProblemsContent to set
	 */
	public void setCommonProblemsContent(String commonProblemsContent) {
		this.commonProblemsContent = commonProblemsContent;
	}

	/**
	 *取得常见问题标识
	 * @return the commonProblemsLogo
	 */
	public int getCommonProblemsLogo() {
		return commonProblemsLogo;
	}

	/**
	 *设置常见问题标识
	 * @param commonProblemsLogo the commonProblemsLogo to set
	 */
	public void setCommonProblemsLogo(int commonProblemsLogo) {
		this.commonProblemsLogo = commonProblemsLogo;
	}

	

}
