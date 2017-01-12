package cn.com.zte.mobilebasedata.entity;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

/** 
 * 请求实体类的基类
 * @author sun.li
 * */
public class BaseRequestEntity implements Serializable{
	
	@Expose(serialize=false,deserialize=false)
	private static final long serialVersionUID = -8642892907141062597L;

}
