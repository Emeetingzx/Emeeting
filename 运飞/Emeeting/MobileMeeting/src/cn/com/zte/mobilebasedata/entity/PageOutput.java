package cn.com.zte.mobilebasedata.entity;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

/**
 * 分页返回参数实体类
 * 
 * @author sun.li
 */
public class PageOutput implements Serializable{
	
	@Expose(serialize = false, deserialize = false)
	private static final long serialVersionUID = -6846410909905696459L;

	/** 结束时间，一般为服务器第一次获取时取得的本地时间传回客户端 */
	private String E;

	/** 数据总数 */
	private String T;

	/** 更新总数，当传入L的时候要计算返回 */
	private String U;

	/**
	 * 获取结束时间，一般为服务器第一次获取时取得的本地时间传回客户端 
	 * 
	 * @return e e
	 */
	public String getE() {
		return E;
	}

	/**
	 * 设置结束时间，一般为服务器第一次获取时取得的本地时间传回客户端 
	 * 
	 * @param e
	 *            e
	 */
	public void setE(String e) {
		E = e;
	}

	/**
	 * 获取数据总数
	 * 
	 * @return t t
	 */
	public String getT() {
		return T;
	}

	/**
	 * 设置数据总数
	 * 
	 * @param t
	 *            t
	 */
	public void setT(String t) {
		T = t;
	}

	/**
	 * 获取更新总数，当传入L的时候要计算返回
	 * 
	 * @return u u
	 */
	public String getU() {
		return U;
	}

	/**
	 * 设置更新总数，当传入L的时候要计算返回
	 * 
	 * @param u
	 *            u
	 */
	public void setU(String u) {
		U = u;
	}

}
