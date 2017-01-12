package cn.com.zte.mobilebasedata.entity;

import java.util.List;

import cn.com.zte.mobilebasedata.request.HttpUtil;
import android.content.Context;

/**
 * 【WS】WebService请求参数实体类
 * 
 * @author sun.li
 */
public class WebServiceRequestEntity {
	
	public WebServiceRequestEntity(Context context){
		
	}

	/** 命令 */
	private String C;

	/** 用户登陆后的授权码 */
	private String T;

	/** 语言参数2052中文1033英文 */
	private String L = HttpUtil.CHINESELANG;

	/** 分页参数对象 */
	private PageInput P = new PageInput();

	/** 其他参数，采用jsonString*/
	private Object D;

	/** 用户信息对象 */
	private String U;
	
	/** 过滤对象数组*/
	private List<FilterInfo> F;

	/**
	 * 获取命令
	 * 
	 * @return c c
	 */
	public String getC() {
		return C;
	}

	/**
	 * 设置命令
	 * 
	 * @param c
	 *            c
	 */
	public void setC(String c) {
		C = c;
	}

	/**
	 * 获取用户登陆后的授权码
	 * 
	 * @return t
	 */
	public String getT() {
		return T;
	}

	/**
	 * 设置用户登陆后的授权码
	 * 
	 * @param t
	 *            t
	 */
	public void setT(String t) {
		T = t;
	}

	/**
	 * 获取语言参数2052中文1033英文
	 * 
	 * @return l l
	 */
	public String getL() {
		return L;
	}

	/**
	 * 设置语言参数2052中文1033英文
	 * 
	 * @param l
	 *            l
	 */
	public void setL(String l) {
		L = l;
	}

	/**
	 * 获取分页参数对象
	 * 
	 * @return p p
	 */
	public PageInput getP() {
		return P;
	}

	/**
	 * 设置分页参数对象
	 * 
	 * @param p
	 *            p
	 */
	public void setP(PageInput p) {
		P = p;
	}

	/**
	 * 获取其他参数，采用jsonString
	 * 
	 * @return d d
	 */
	public Object getD() {
		return D;
	}

	/**
	 * 设置其他参数，采用jsonString
	 * 
	 * @param d
	 *            d
	 */
	public void setD(Object d) {
		D = d;
	}

	/**
	 * 获取用户信息对象
	 * 
	 * @return u u
	 */
	public String getU() {
		return U;
	}

	/**
	 * 设置用户信息对象
	 * 
	 * @param u
	 *            u
	 */
	public void setU(String u) {
		U = u;
	}

	/**
	 *取得过滤对象数组
	 * @return the f
	 */
	public List<FilterInfo> getF() {
		return F;
	}

	/**
	 *设置过滤对象数组
	 * @param f the f to set
	 */
	public void setF(List<FilterInfo> f) {
		F = f;
	}

}
