/**
 * 
 */
package cn.com.zte.emeeting.app.entity.system;

import com.baidu.location.BDLocation;

/**
 * 该类为:存储我的位置信息的实体
 * @author wf
 */
public class MyLocation {
//	getLongitude() 
	/** 经度*/
	private double longitude;
	/** 纬度*/
	private double latitude;
	/** 详细百度位置信息*/
	private BDLocation baiduLocation;
	
	/**
	 * @return 获取百度定位到的详细信息
	 */
	public BDLocation getBaiduLocation() {
		return baiduLocation;
	}
	/**
	 * @param 设置百度定位到的详细信息
	 */
	public void setBaiduLocation(BDLocation baiduLocation) {
		this.baiduLocation = baiduLocation;
	}
	/**
	 * @return 获取经度
	 */
	public double getLongitude() {
		return longitude;
	}
	/**
	 * @param 设置经度
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return 获取纬度
	 */
	public double getLatitude() {
		return latitude;
	}
	/**
	 * @param 设置纬度
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
}
