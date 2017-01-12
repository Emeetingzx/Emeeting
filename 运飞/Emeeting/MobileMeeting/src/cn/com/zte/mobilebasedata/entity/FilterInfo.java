package cn.com.zte.mobilebasedata.entity;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

/**
 * 简单参数封装对象实体类
 * 
 * @author sun.li
 */
public class FilterInfo  implements Serializable{
	
	@Expose(serialize = false, deserialize = false)
	private static final long serialVersionUID = -902808929542376131L;

	private String K;

	private String V;

	public FilterInfo(String k, String v) {
		K = k;
		V = v;
	}

	public String getK() {
		return K;
	}

	public void setK(String s) {
		K = s;
	}

	public String getV() {
		return V;
	}

	public void setV(String s) {
		V = s;
	}
}
