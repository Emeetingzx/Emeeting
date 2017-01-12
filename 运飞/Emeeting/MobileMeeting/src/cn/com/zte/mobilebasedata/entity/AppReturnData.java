package cn.com.zte.mobilebasedata.entity;

import cn.com.zte.android.app.application.BaseApplication;
import cn.com.zte.android.http.model.BaseHttpResponse;
import cn.com.zte.emeeting.app.util.DataConst;

import com.google.gson.annotations.Expose;

public class AppReturnData<T> extends BaseHttpResponse {

	@Expose(serialize = false, deserialize = false)
	private static final long serialVersionUID = -1045154964212383098L;

	/** 是否成功 */
	private String S;

	/** 错误提示 */
	private String M;

	private String C;

	/** 分页返回对象 */
	private PageOutput P;

	private T D;
	/**
	 * 服务端当前时间
	 */
	private String DT;

	public void setD(T t) {
		D = t;
	}

	public T getD() {
		return D;
	}

	@Override
	public String getResultCode() {
		return S;
	}

	@Override
	public String getResultDesc() {
		return M;
	}

	/**
	 * 获取c
	 * 
	 * @return c c
	 */
	public String getC() {
		return C;
	}

	/**
	 * 获取p
	 * 
	 * @return p p
	 */
	public PageOutput getP() {
		return P;
	}

	/**
	 * HTTP请求成功返回的参数值
	 */
	@Override
	public String getDefaultSucessCode() {
		return "true";
	}

	/**
	 * 对比HTTP请求返回参数，判断是否成功
	 */
	@Override
	public boolean getSuccessful() {
		return getDefaultSucessCode().equals(getResultCode());
	}

	/**
	 * 获取s
	 * 
	 * @return s s
	 */
	public String getS() {
		return S;
	}

	/**
	 * 设置s
	 * 
	 * @param s
	 *            s
	 */
	public void setS(String s) {
		S = s;
	}

	/**
	 * 获取m
	 * 
	 * @return m m
	 */
	public String getM() {
		return M;
	}

	/**
	 * 设置m
	 * 
	 * @param m
	 *            m
	 */
	public void setM(String m) {
		M = m;
	}

	/**
	 * 设置c
	 * 
	 * @param c
	 *            c
	 */
	public void setC(String c) {
		C = c;
	}

	/**
	 * 设置p
	 * 
	 * @param p
	 *            p
	 */
	public void setP(PageOutput p) {
		P = p;
	}

	/**
	 * 服务端当前时间
	 */
	public String getDT() {
		return DT;
	}

	/**
	 * 服务端当前时间
	 */
	public void setDT(String dT) {
		DT = dT;
	}

}
