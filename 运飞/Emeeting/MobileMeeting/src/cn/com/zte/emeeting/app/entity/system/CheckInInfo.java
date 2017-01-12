package cn.com.zte.emeeting.app.entity.system;

import android.graphics.Bitmap;
	/**
	 *  会议签到适配器
	 * @author wtb
	 *
	 */
public class CheckInInfo {
	private Bitmap mBitmap; // 头像
	private String checkinName; // 姓名
	private String checkinNum;
	private String checkinTime;
	
	public Bitmap getmBitmap() {
		return mBitmap;
	}
	public void setmBitmap(Bitmap mBitmap) {
		this.mBitmap = mBitmap;
	}
	public String getCheckinName() {
		return checkinName;
	}
	public void setCheckinName(String checkinName) {
		this.checkinName = checkinName;
	}
	public String getCheckinNum() {
		return checkinNum;
	}
	public void setCheckinNum(String checkinNum) {
		this.checkinNum = checkinNum;
	}
	public String getCheckinTime() {
		return checkinTime;
	}
	public void setCheckinTime(String checkinTime) {
		this.checkinTime = checkinTime;
	}
}
