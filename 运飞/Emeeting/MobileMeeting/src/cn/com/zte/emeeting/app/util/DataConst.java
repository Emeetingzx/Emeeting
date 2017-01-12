/**
 * 
 */
package cn.com.zte.emeeting.app.util;

/**
 * 该类为常量类
 * 
 */
public class DataConst {

	/* 默认超时时间 */
	public static final int TIMEOUT = 8000;

	/* 默认超时时间 */
	public static final int INTERNET_INTERIOR_TIMEOUT = 5 * 1000;

	public static final String ACTION_HOMEMENU_SWITCH = "cn.com.zte.emeeting.homemenufragment";

	/** 刷新我的会议广播 */
	public static final String ACTION_MYMEETING_REFRESH = "cn.com.zte.emeeting.mymeetingfragment.refresh";
	/** 摇一摇广播 */
	// public static final String
	// ACTION_SHAKE="cn.com.zte.emeeting.mymeetingfragment.shake";

	/** 刷新会议详情广播 */
	public static final String ACTION_MEETING_DETAIL_REFRESH = "cn.com.zte.emeeting.medetail.refresh";

	/** 本地(移除)刷新我的会议广播 */
	public static final String ACTION_MYMEETING_REFRESH_REMOVE = "cn.com.zte.emeeting.mymeetingfragment.refresh.remove";
	/** 意见反馈关闭广播 */
	public static final String ACTION_CLOSE = "cn.com.zte.emeeting.suggestionclose";
	/** 找到会议室广播 */
	public static final String ACTION_FIND_ROOM = "cn.com.zte.emeeting.findroom";
	/** 会议时间过期广播 */
	public static final String ACTION_TIME_OUT = "cn.com.zte.emeeting.timeout";

	/** 经纬度改变广播 */
	public static final String ACTION_MYLOCATION_CHANGED = "cn.com.zte.emeeting.myapplication.location.changed";

	/** 我预定的 */
	public static final String MEETINGBTYPE_MYBOOKED = "1";
	/** 我组织的 */
	public static final String MEETINGBTYPE_MYORGANIZED = "2";
	/** 我参加的 */
	public static final String MEETINGBTYPE_ATTENDED = "3";
	/** 所有会议 */
	public static final String MEETINGBTYPE_ALL = "0";

	/** 不可操作 */
	public static final String OP_STATE_DISENABLE = "0";
	/** 可退订 */
	public static final String OP_STATE_CAN_CANCELBOOK = "1";
	/** 可结束 */
	public static final String OP_STATE_CAN_STOP = "2";

	/** 会议类型 */
	/** 常规会议：1 */
	public static final String MT_NORMAL = "1";
	/** 电话会议桥：2， */
	public static final String MT_PHONE = "2";
	/** 培训会议：3， */
	public static final String MT_TRAIN = "3";
	/** 网真会议：4， */
	public static final String MT_WEB = "4";
	/** 云招标会议：5， */
	public static final String MT_CLOUD = "5";
	/** 视频会议桥：6 */
	public static final String MT_VIDEO = "6";

	/**
	 * 二期添加字段
	 * 
	 * @author Pan.Jianbo
	 */

	/** 视频终端号标识 */
	public static final int VIDEO_CODE = 0X10;
	/** 电话端号标识 */
	public static final int PHONE_CODE = 0X11;
	
	/** 联系人页面跳转标识 */
	public static final int REQUESTCODE_CONTACTS = 0X12;
	
	/** 添加会议类型选择页面广播接收 */
	public static final String CONTROL_FILTER = "CONTROL_FILTER";
	
	/** 成功标识 */
	public static final String YES = "Y";
	
	/** 失败标识 */
	public static final String NO = "N";

	/** 请求成功标识 */
	public static final String TRUE = "true";
	
	/** 标志1 */
	public static final String ONE = "1";
	/** 标志2 */
	public static final String TWO = "2";
	/** 标志0 */
	public static final String ZERO = "0";
	/** 空选项 */
	public static final String NULL = "";
	
	/**
	 * 应用配置信息
	 */
	public static final String APPCONFIG = "appconfig";
	/**
	 * 应用配置信息,最大可预订天数
	 */
	public static final String APPCONFIG_MAXDAY = "maxdays";

}
