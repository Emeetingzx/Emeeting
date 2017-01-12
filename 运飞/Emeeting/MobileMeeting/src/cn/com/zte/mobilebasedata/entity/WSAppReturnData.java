package cn.com.zte.mobilebasedata.entity;

import com.google.gson.annotations.Expose;

import cn.com.zte.android.http.model.BaseWebServiceResponse;

public class WSAppReturnData <T> extends BaseWebServiceResponse {

	@Expose(serialize = false, deserialize = false)
	private static final long serialVersionUID = 1355084305523167645L;

	/** 是否成功 */
	private String S;

	/** 错误提示 */
	private String M;

	private String C;

	/** 分页返回对象 */
	private PageOutput P;
	
	private T D;
	
	public void setD(T t){
		D = t;
	}
	
	public T getD(){
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

}

