package cn.com.zte.mobilebasedata.entity;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

/**
 * 分页提交参数实体类
 * 
 * @author sun.li
 */
public class PageInput implements Serializable{
	
	@Expose(serialize = false, deserialize = false)
	private static final long serialVersionUID = 5568623456780963138L;

	/** 开始时间，要大于该时间一般为本地最后一次更新时间 */
	private String S;

	/** 结束时间，一般为服务器第一次获取时取得的本地时间传回客户端 */
	private String E;

	/** 记录总数，第二页的时候传入 */
	private String T;

	/** 本地最后一次更新时间，当有这个参数的时候要额外计算一个更新总数，即L到E的记录总数 */
	private String L;

	/** 页数 */
	private String PNO;

	/** 每页记录数 */
	private String PSIZE;

	/**
	 * 获取开始时间，要大于该时间一般为本地最后一次更新时间
	 * 
	 * @return s s
	 */
	public String getS() {
		return S;
	}

	/**
	 * 设置开始时间，要大于该时间一般为本地最后一次更新时间
	 * 
	 * @param s
	 *            s
	 */
	public void setS(String s) {
		S = s;
	}

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
	 * 获取记录总数，第二页的时候传入
	 * 
	 * @return t t
	 */
	public String getT() {
		return T;
	}

	/**
	 * 设置记录总数，第二页的时候传入
	 * 
	 * @param t
	 *            t
	 */
	public void setT(String t) {
		T = t;
	}

	/**
	 * 获取本地最后一次更新时间，当有这个参数的时候要额外计算一个更新总数，即L到E的记录总数
	 * 
	 * @return l l
	 */
	public String getL() {
		return L;
	}

	/**
	 * 设置本地最后一次更新时间，当有这个参数的时候要额外计算一个更新总数，即L到E的记录总数
	 * 
	 * @param l
	 *            l
	 */
	public void setL(String l) {
		L = l;
	}

	/**
	 * 获取页数
	 * 
	 * @return pNO pNO
	 */
	public String getPNO() {
		return PNO;
	}

	/**
	 * 设置页数
	 * 
	 * @param pNO
	 *            pNO
	 */
	public void sourceID(String pNO) {
		PNO = pNO;
	}

	/**
	 * 获取每页记录数
	 * 
	 * @return pSIZE pSIZE
	 */
	public String getPSIZE() {
		return PSIZE;
	}

	/**
	 * 设置每页记录数
	 * 
	 * @param pSIZE
	 *            pSIZE
	 */
	public void setPSIZE(String pSIZE) {
		PSIZE = pSIZE;
	}

	public void setPNO(String pNO)
	{
		PNO = pNO;
	}
	
	/**
	 * 页码+1
	 */
	public void pageNoAdd(){
		if (PNO==null) {
			PNO = "0";
		}
		int pageNo = 0;
		try {
			pageNo = Integer.parseInt(PNO);
		} catch (Exception e) {
			e.printStackTrace();
			pageNo = 0;
		}
		pageNo++;
		PNO = pageNo+"";
	}

}
