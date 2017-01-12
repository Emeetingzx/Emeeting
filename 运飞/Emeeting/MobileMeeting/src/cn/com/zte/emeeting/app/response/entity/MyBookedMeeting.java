package cn.com.zte.emeeting.app.response.entity;

import cn.com.zte.android.app.common.vo.BaseVO;
/***
 *  我的预定会议实体类
 * @author wtb
 *
 */
public class MyBookedMeeting extends BaseVO{

	private static final long serialVersionUID = 6831740655884784571L;
	
	/**会议名称*/
	private String meetingName;
	
	/**会议状态*/
	private String meetingStatus;
	
	/**会议地点*/
	private String meetingLocation;
	
	/**会议时间*/
	private String meetingTime;
	
	/**会议预订人*/
	private String meetingBookedPerson;
	
	public MyBookedMeeting(String meetingName,String meetingStatus,String meetingLocation,String meetingTime,String meetingBookedPerson){
		this.meetingName = meetingName;
		this.meetingStatus = meetingStatus;
		this.meetingLocation = meetingLocation;
		this.meetingTime = meetingTime;
		this.meetingBookedPerson = meetingBookedPerson;
	}
	
	
	public String getMeetingName() {
		return meetingName;
	}

	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}

	public String getMeetingStatus() {
		return meetingStatus;
	}

	public void setMeetingStatus(String meetingStatus) {
		this.meetingStatus = meetingStatus;
	}

	public String getMeetingLocation() {
		return meetingLocation;
	}

	public void setMeetingLocation(String meetingLocation) {
		this.meetingLocation = meetingLocation;
	}

	public String getMeetingTime() {
		return meetingTime;
	}

	public void setMeetingTime(String meetingTime) {
		this.meetingTime = meetingTime;
	}

	public String getMeetingBookedPerson() {
		return meetingBookedPerson;
	}

	public void setMeetingBookedPerson(String meetingBookedPerson) {
		this.meetingBookedPerson = meetingBookedPerson;
	}
}
