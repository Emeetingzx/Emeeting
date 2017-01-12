package cn.com.zte.emeeting.app.response.entity;

import com.google.gson.annotations.Expose;

import cn.com.zte.android.app.common.vo.BaseVO;

/**
 * Created by LangK on 16-3-10. 3.3.2.7 参会的会议室/人员对象实体
 */
public class ValidBookDate extends BaseVO {

	private static final long serialVersionUID = 8046336743521931785L;

	private String minDate;
	
	private String maxDate;

	public String getMinDate() {
		return minDate;
	}

	public void setMinDate(String minDate) {
		this.minDate = minDate;
	}

	public String getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}
	
	

}
