package cn.com.zte.mobilebasedata.request;

import cn.com.zte.android.http.HttpManager;

/**
 * HTTP请求公共类
 * 
 * @author sun.li
 * 
 * */
public class HttpUtil {

	private static HttpUtil httpUtil;

	private HttpUtil() {
	}

	public static HttpUtil getInstance() {
		if (httpUtil == null)
			httpUtil = new HttpUtil();
		HttpManager.setTimeout(TimeOut);
		HttpManager.setSoTimeout(SocketTimeOut);
		return httpUtil;
	}

	/** 请求超时时间 */
	public static final int SocketTimeOut = 30 * 1000;
	/** 请求超时时间 */
	public static final int TimeOut = 30 * 1000;

	public static final int VersionUpdateTimeOut = 3 * 1000;
	
	public static final int CallSockctTimeOut = 8 * 1000;

	/** 选择HTTPS请求标识 true 启用 false 不启用 */
	public static final boolean HTTPSFLAG = false;

	/**
	 * 测试用户
	 */
//	public static final String UID = "10103749";

	/**
	 * 是否是外网环境
	 */
	public static boolean ISOUTSIDENET = true;

	/**
	 * 服务器IP
	 */
	private static String SERVEREIP = HttpUtil.IntranetIP;
	/**
	 * 服务器端口
	 */
	private static String SERVEREPORT = HttpUtil.JSONSERVERPORT;

	/** 【内网】生产服务器IP */
	public static final String IntranetIP = "mdm.zte.com.cn";

	/** 【外网】生产服务器IP */
	public static final String NetworkIP = "mdm.zte.com.cn";
	
//	/** 【内网】生产服务器IP */
//	public static final String IntranetIP = "http://120.77.72.167";
//
//	/** 【外网】生产服务器IP */
//	public static final String NetworkIP = "http://120.77.72.167";

	/** 【内网】生产服务器HTTP请求服务器Json端口号 */
	public static final String JSONSERVERPORT = "80";

	/** 【外网】生产服务器HTTP请求服务器Json端口号 */
	public static final String NETJSONSERVERPORT = "80";

	/** 【内网】测试服务器IP */
//	public static final String IntranetIP = "mdmtest.zte.com.cn";

	/** 【外网】测试服务器IP */
//	public static final String NetworkIP = "mdmtest.zte.com.cn";
	/**
	 * 程伟服务器
	 */
//	public static final String NetworkIP = "210.21.223.22";

	/** 【内网】测试服务器HTTP请求服务器Json端口号 */
//	public static final String JSONSERVERPORT = "8888";

	/** 【外网】测试服务器HTTP请求服务器Json端口号 */
//	public static final String NETJSONSERVERPORT = "8888";
	/**
	 * 程伟服务器
	 */
//	public static final String NETJSONSERVERPORT = "8088";
	
	/** HTTPJson请求服务器公共路径 */
	public static final String HTTPJSONREQUESTPATH = "emeeting";
	
	/** HTTPWebService请求服务器公共路径 */
	public static final String HTTPWSREQUESTPATH = "emeeting";

	/** WebService请求命名空间 */
	public static final String WSNAMESPACE = "";

	/** WebService请求方法名 */
	public static final String WSMETHODNAME = "";

	/** HTTP请求服务器中文语言标识 */
	public static final String CHINESELANG = "2052";

	/** HTTP请求服务器英文语言标识 */
	public static final String ENGLISHLANG = "1033";

	/** 数据请求默认请求条数10条 */
	public static final int PageSize = 15;
	
	/** 请求数据最大数据量*/
	public static final int RequestMAXSize = 10;

	/**
	 * 接口版本号
	 */
	public static String INTERFACEVER = "1";

	/** 服务器请求失败 */
	public static final String SERVER_REQUEST_FAIL = "无法连接到服务器,请检查你的网络或者稍后重试";

	/** 服务器异常 */
	public static final String SERVER_REQUEST_NORMAL_ERROR = "无法连接到服务器,请检查你的网络或者稍后重试";

	
//	/** HTTPJson请求‘基础’接口地址 */
//	public static final String BasisInterface = "baseData/services.jssm";
//	/** HTTPJson请求‘公共’接口地址 */
//	public static final String PublicInterface = "public/services.jssm";
//	/** HTTPJson请求‘会议查询’接口地址 */
//	public static final String EmeetingQueryInterface = "emeetingQuery/services.jssm";
//	/** HTTPJson请求‘摇一摇’接口地址 */
//	public static final String SharkOffInterface = "sharkOff/services.jssm";
//	/** HTTPJson请求‘我的会议’接口地址 */
//	public static final String MyEmeetingInterface = "myMeeting/services.jssm";
	
	/** HTTPJson请求‘基础’接口地址 */
	public static final String BasisInterface = "App/AppHandler.ashx";
	/** HTTPJson请求‘公共’接口地址 */
	public static final String PublicInterface = "App/AppHandler.ashx";
	/** HTTPJson请求‘会议查询’接口地址 */
	public static final String EmeetingQueryInterface = "App/AppHandler.ashx";
	/** HTTPJson请求‘摇一摇’接口地址 */
	public static final String SharkOffInterface = "App/AppHandler.ashx";
	/** HTTPJson请求‘我的会议’接口地址 */
	public static final String MyEmeetingInterface = "App/AppHandler.ashx";
	/** HTTPJson请求‘会议控制’接口地址 */
	public static final String MeetingControl = "App/MeetingControlHandler.ashx";
	
	/**
	 * 获取数据更新时间接口
	 */
	public static final String GetLastUpdatetime = "GetLastUpdatetime";
	
	/**
	 * 获取会议室地址信息数据接口
	 */
	public static final String GetSysMeetingRoomAddress = "SysMeetingRoomAddress";
	/**
	 * 获取会议室信息数据接口
	 */
	public static final String SysMeetingRoomInfo = "SysMeetingRoomInfo";
	
	/**
	 * 获取服务器时间接口
	 */
	public static final String GetServerData = "GetServerData";
	/**
	 * 结束会议接口
	 */
	public static final String EndMeetingRoom = "EndMeetingRoom";
	/**
	 * 退订会议室接口
	 */
	public static final String CancelMeetingRoom = "CancelMeetingRoom";
	/**
	 * 锁定会议室接口
	 */
	public static final String LockBookingMeetingRoom = "LockBookingMeetingRoom";
	/**
	 * 预定电话/视频会议桥会议室接口
	 */
	public static final String ReservePhoneOrVideoMeetingRoom = "ReservePhoneOrVideoMeetingRoom";
	
	/**
	 * 提交预定会议室接口
	 */
	public static final String SubmitBookingMeetingRoom = "SubmitBookingMeetingRoom";
	/**
	 * 帮助反馈接口
	 */
	public static final String HelpFeedback = "HelpFeedback";
	
	/**
	 *获取指定条件会议室预定情况列表接口
	 */
	public static final String GetDayMeetingRoomBookingInfo = "GetDayMeetingRoomBookingInfo";
	/**
	 *获取会议室预定情况详细信息接口
	 */
	public static final String GetMeetingRoomBookingInfo = "GetMeetingRoomBookingInfo";
	
	/**
	 *定位查询附近园区地址信息接口
	 */
	public static final String GetNearParkAddressInfo = "GetNearParkAddressInfo";
	/**
	 *【分页】查询定位或选择园区可预订会议室信息接口
	 */
	public static final String GetNearParkMeetingRoomInfo = "GetNearParkMeetingRoomInfo";
	
	
	/**
	 *获取与我相关的会议接口
	 */
	public static final String GetUserRelevantMeetingInfo = "GetUserRelevantMeetingInfo";
	/**
	 *获取会议详细信息
	 */
	public static final String GetMeetingInfo = "GetMeetingInfo";
	/**
	 *获取与我有关的会议所在日期信息
	 */
	public static final String GetUserRelevantMeetingDates = "GetUserRelevantMeetingDates";
	
	/**
	 *获取用户是否为增值服务管理员接口
	 */
	public static final String GetUserIfAddValueServiceAdmin = "GetUserIfAddValueServiceAdmin";
	
	/**
	 *获取食品茶点信息接口
	 */
	public static final String GetFoodAndRefreshmentsInfos = "GetFoodAndRefreshmentsInfos";
	
	/**
	 *预定增值服务接口
	 */
	public static final String ReserveAddValueService = "ReserveAddValueService";

	/**
	 *获取我的增值服务信息接口
	 */
	public static final String GetMyAddValueServiceInfos = "GetMyAddValueServiceInfos";

	/**
	 *增值服务操作接口
	 */
	public static final String AddValueServiceOperate = "AddValueServiceOperate";

	/**
	 *获取管理员相关增值服务信息接口
	 */
	public static final String GetAdminAddValueServiceInfos = "GetAdminAddValueServiceInfos";
	
	/**
	 *获取增值服务地区信息接口
	 */
	public static final String GetAddValueServiceRegionInfos = "GetAddValueServiceRegionInfos";
	
	/**
	 *获取离用户最近公司建筑地址信息接口
	 */
	public static final String GetRecentBuildingAddressInfo = "GetRecentBuildingAddressInfo";
	
	/**
	 *获取会议的参会会议室或者人员接口
	 */
	public static final String GetMeetingJoinInfo = "GetMeetingJoinInfo";

	/**
	 *获取签到人员信息接口
	 */
	public static final String GetMeetingAttendanceInfo = "GetMeetingAttendanceInfo";

	/**
	 *人员签到接口
	 */
	public static final String AttendanceOperation = "AttendanceOperation";

	/**
	 *会议邀请接口
	 */
	public static final String InvitaMeeting = "InvitaMeeting";
	
	/**
	 *会议操作接口
	 */
	public static final String MeetingOperation = "MeetingOperation";
	
	/**
	 *获取会议时间延长信息接口
	 */
	public static final String GetMeetingProlongInfo = "GetMeetingProlongInfo";
	
	/**
	 *会议时间延长接口
	 */
	public static final String MeetingProlong = "MeetingProlong";
	
	/**
	 *获取最大预订会议时间接口
	 */
	public static final String GetValidBookDate = "GetValidBookDate";
	
	/**
	 *会议室邀请确认接口
	 */
	public static final String DoInvitaMeeting = "DoInvitaMeeting";
	
	
	//public static final String url_moa_facephoto="http://share.zte.com.cn/tech/rest/auth/userinfo_image";
	public static final String url_moa_facephoto="http://mdm.zte.com.cn:80/redpacketnew/moa/services.dssm";

	/**
	 * 获取服务器IP
	 * 
	 * @return sERVEREIP sERVEREIP
	 */
	public String getSERVEREIP() {
		return SERVEREIP;
	}

	/**
	 * 获取服务器IP
	 * 
	 * @return sERVEREIP sERVEREIP
	 */
	public String getSERVEREPORT() {
		return SERVEREPORT;
	}

	/**
	 * 设置服务器IP
	 * 
	 * @param sERVEREIP
	 *            sERVEREIP
	 */
	public void setSERVEREIP(String sERVEREIP) {
		SERVEREIP = sERVEREIP;
		if (sERVEREIP.equals(NetworkIP)) {
			setSERVEREPORT(NETJSONSERVERPORT);
		} else {
			setSERVEREPORT(JSONSERVERPORT);
		}
	}
	
	/**
	 * 图片地址主机
	 * @return
	 */
	public static String getImageHost(){
//		if (ISOUTSIDENET) {
//			http://10.88.144.120/
//		}
		return "http://"+SERVEREIP+":"+SERVEREPORT;
	}

	/**  
	 * 设置服务器端口
	 */
	public static void setSERVEREPORT(String sERVEREPORT) {
		SERVEREPORT = sERVEREPORT;
	}

	
	
}
