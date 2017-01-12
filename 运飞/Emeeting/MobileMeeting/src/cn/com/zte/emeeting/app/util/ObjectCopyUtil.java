package cn.com.zte.emeeting.app.util;

import cn.com.zte.emeeting.app.response.entity.MeetingInfo;

/**
 * 对象copy类
 * @author 6396000419
 *
 */
public class ObjectCopyUtil {
	
	/**
	 * 
	 * @param info  from entity
	 * @param meetingInfo to entity
	 * @return
	 */
	public static MeetingInfo copy(MeetingInfo src,MeetingInfo to){
		if (src==null) {
			return null;
		}
		to = new MeetingInfo();
//		String json = JsonUtil.toJson(info);
//		meetingInfo = JsonUtil.fromJson(json, MeetingInfo.class);
		to.setAP(src.getAP());
		to.setAT(src.getAT());
		to.setBD(src.getBD());
		to.setBMRIDS(src.getBMRIDS());
		to.setED(src.getED());
		to.setIEC(src.getIEC());
		to.setIPRL(src.getIPRL());
		to.setISI(src.getISI());
		to.setMID(src.getMID());
		to.setML(src.getML());
		to.setMN(src.getMN());
		to.setMNB(src.getMNB());
		to.setMPWD(src.getMPWD());
		to.setMS(src.getMS());
		to.setMT(src.getMT());
		to.setMTP(src.getMTP());
		to.setOPCN(src.getOPCN());
		to.setOPN(src.getOPN());
		to.setOS(src.getOS());
		to.setPMLL(src.getPMLL());
		to.setPN(src.getPN());
		return to;
	}

}
