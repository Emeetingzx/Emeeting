/**
 * 
 */
package cn.com.zte.emeeting.app.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.zte.android.common.util.JsonUtil;
import cn.com.zte.emeeting.app.base.MyApplication;
import cn.com.zte.emeeting.app.dao.shared.SysDBMeetingRoomInfoDBDao;
import cn.com.zte.emeeting.app.database.entity.shared.DBMeetingRoomAddress;
import cn.com.zte.emeeting.app.database.entity.shared.DBMeetingRoomInfo;
import cn.com.zte.mobileemeeting.R;

import android.content.Context;
import android.text.TextUtils;

/**
 * 该类为项目工具类,需要在App对象不为空时使用
 * @author wf
 */
public final class EmeetingUtil {
	private EmeetingUtil(){}
	
	private static Context mContext=MyApplication.GetApp();
	
	
	private static  List<String> listPersonNumName=new ArrayList<String>();
	private static  List<String> listPersonNumCode=new ArrayList<String>();
	
	private static Map<String,String> mapLeaderLevel=new HashMap<String, String>();
	private static Map<String,String> mapMeetingLevel=new HashMap<String, String>();
	
	private static Map<String,String> mapMeetingPhone=new HashMap<String, String>();
	private static Map<String,String> mapMeetingTV=new HashMap<String, String>();
	private static Map<String,String> mapMeetingProjector=new HashMap<String, String>();
	private static Map<String,String> mapMeetingScale=new HashMap<String, String>();
	
	private static Map<String,String> mapMeetingPersonNum = new HashMap<String, String>();
//	/**
//	 * 投影仪状态 0->没有 1->不支持双流 2->支持双流接收 3->支持双流接收发送 会议室详情接口返回 PJS【ProjectorState】
//	 */
//	private String PJS;
//
//	/**
//	 * 电视状态 0->没有 1->有 会议室详情接口返回 TVS【TelevisionState】
//	 */
//	private String TVS;
//
//	/**
//	 * 电话状态 0->没有 1-> POLYCOM 2-> USB-Phone 会议室详情接口返回 PS【PhoneState】
//	 */
//	private String PS;
	
	static{
		
		
		listPersonNumCode.add("0");
		listPersonNumCode.add("10");
		listPersonNumCode.add("20");
		listPersonNumCode.add("100");
		listPersonNumCode.add("200");

		
		if(mContext!=null)
		{
			listPersonNumName = Arrays.asList(mContext.getResources().getStringArray(R.array.arr_PersonNumName));
			
			mapMeetingPhone.put("0", mContext.getString(R.string.meeting_phonestate_0));
			mapMeetingPhone.put("1", mContext.getString(R.string.meeting_phonestate_1));
			mapMeetingPhone.put("2", mContext.getString(R.string.meeting_phonestate_2));
			
			mapMeetingProjector.put("0", mContext.getString(R.string.meeting_projectorstate_0));
			mapMeetingProjector.put("1", mContext.getString(R.string.meeting_projectorstate_1));
			mapMeetingProjector.put("2", mContext.getString(R.string.meeting_projectorstate_2));
			mapMeetingProjector.put("3", mContext.getString(R.string.meeting_projectorstate_3));
			
			mapMeetingTV.put("0", mContext.getString(R.string.meeting_tvstate_0));
			mapMeetingTV.put("1", mContext.getString(R.string.meeting_tvstate_1));
			
			mapLeaderLevel.put("1", mContext.getString(R.string.meeting_leaderlevel_1));
			mapLeaderLevel.put("2", mContext.getString(R.string.meeting_leaderlevel_2));
			mapLeaderLevel.put("3",	mContext.getString(R.string.meeting_leaderlevel_3));
			mapLeaderLevel.put("4", mContext.getString(R.string.meeting_leaderlevel_4));
			
			mapMeetingLevel.put("1", mContext.getString(R.string.meeting_level_1));
			mapMeetingLevel.put("2", mContext.getString(R.string.meeting_level_2));
			mapMeetingLevel.put("3", mContext.getString(R.string.meeting_level_3));
		}else
		{
			
			listPersonNumName.clear();
			listPersonNumName.add("不限");
			listPersonNumName.add("10人以下");
			listPersonNumName.add("10-30人");
			listPersonNumName.add("30-60人");
			listPersonNumName.add("60人以上");
			
			mapMeetingPhone.put("0", "无");
			mapMeetingPhone.put("1", "POLYCOM");
			mapMeetingPhone.put("2", "USB-Phone");
			
			mapMeetingProjector.put("0", "无");
			mapMeetingProjector.put("1", "不支持双流");
			mapMeetingProjector.put("2", "支持双流接收");
			mapMeetingProjector.put("3", "支持接收双流");
			
			mapMeetingTV.put("0", "无");
			mapMeetingTV.put("1", "有");
			
			mapLeaderLevel.put("1", "二层领导");
			mapLeaderLevel.put("2", "三层领导");
			mapLeaderLevel.put("3", "四层领导");
			mapLeaderLevel.put("4", "其他");
			
			mapMeetingLevel.put("1", "A级");
			mapMeetingLevel.put("2", "B级");
			mapMeetingLevel.put("3", "C级");
		}
		
		for (int i = 0; i < listPersonNumCode.size(); i++) {
			mapMeetingPersonNum.put(listPersonNumCode.get(i), listPersonNumName.get(i));
		}
	}
	
	
	/** 人数列表,显示的字符串*/
	public static List<String> getListPersonNumName()
	{
		return listPersonNumName;
	}
	
	
	/** 人数列表,提交给服务器的标识码*/
	public static List<String> getListPersonNumCode()
	{
		return listPersonNumCode;
	}
	
	/** 获取会议室人数*/
	private static String getMeetingPersonNum(String code)
	{
		if(TextUtils.isEmpty(code))
		{
			return "未知";
		}
		if(mapMeetingPersonNum.get(code)!=null)
		{
			return mapMeetingPersonNum.get(code);
		}else
		{
			return mapMeetingPersonNum.get(listPersonNumCode.get(0));
		}
	}
	
	/**获取会议室电视状态*/
	public static String getMeetingRoomTvState(String code)
	{
		if(TextUtils.isEmpty(code))
		{
			return "未知";
		}
		
		if(mapMeetingTV.get(code)!=null)
		{
			return mapMeetingTV.get(code);
		}else
		{
			return "未知";
		}
	}
	
	/**获取会议室电话状态*/
	public static String getMeetingRoomPhoneState(String code)
	{
		if(TextUtils.isEmpty(code))
		{
			return "未知";
		}
		
		if(mapMeetingPhone.get(code)!=null)
		{
			return mapMeetingPhone.get(code);
		}else
		{
			return "未知";
		}
	}
	
	/**获取会议室投影仪状态*/
	public static String getMeetingRoomProjectorState(String code)
	{
		if(TextUtils.isEmpty(code))
		{
			return "未知";
		}
		if(mapMeetingProjector.get(code)!=null)
		{
			return mapMeetingProjector.get(code);
		}else
		{
			return "未知";
		}
	}
	
	
	/**获取会议室规模*/
	public static String getMeetingRoomScale(String code)
	{
		return getMeetingPersonNum(code);
	}
	
	
	/** 获取领导等级名称*/
	public static String getLeaderLevelName(String id)
	{
		if(mapLeaderLevel.get(id)!=null)
		{
			return mapLeaderLevel.get(id);
		}else
		{
			return "其他";
		}
	}
	
	/** 获取会议等级名称*/
	public static String getMeetingLevelName(String id)
	{
		if(mapMeetingLevel.get(id)!=null)
		{
			return mapMeetingLevel.get(id);
		}else
		{
			return "C级";
		}
	}
	
	
	/** 获得会议时间*/
	public static String getMeetingTime(String beginTime,String endTime)
	{
		if(TextUtils.isEmpty(beginTime)||TextUtils.isEmpty(endTime)) return "";
		
		SimpleDateFormat tmpDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date beginDate = tmpDf.parse(beginTime);
			Date endDate = tmpDf.parse(endTime);
			
//			long meetingDuration=endDate.getTime()-beginDate.getTime();
//			String strMeetingDuration = meetingDuration/1000/60/60+"小时";
//			tv_meetingdetail_duration.setText(strMeetingDuration);
			
			SimpleDateFormat df_date = new SimpleDateFormat("yyyy-MM-dd");
			String meetingDate_1 = df_date.format(beginDate);
			String meetingDate_2 = df_date.format(endDate);
			if(!meetingDate_1.equals(meetingDate_2))
			{
				SimpleDateFormat df_time = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				return df_time.format(beginDate)+"-"+df_time.format(endDate);
			}else
			{
				SimpleDateFormat df_time = new SimpleDateFormat("HH:mm");
				return meetingDate_1+"　"+df_time.format(beginDate)+"-"+df_time.format(endDate);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	/**存储用户选择的地址或定位的地址*/
	public static void setUserLocation(DBMeetingRoomAddress addressEntity)
	{
		SharedPreferenceUtil util=new SharedPreferenceUtil("location", MyApplication.getInstance());
		util.setNameAndValue("locationentity",JsonUtil.toJson(addressEntity));
	}
	
	/**获取用户选择的地址或定位的地址*/
	public static DBMeetingRoomAddress getUserLocation()
	{
		SharedPreferenceUtil util=new SharedPreferenceUtil("location", MyApplication.getInstance());
		String address = util.getString("locationentity");
		if(TextUtils.isEmpty(address))
		{
			return null;
		}else
		{
			return JsonUtil.fromJson(address, DBMeetingRoomAddress.class);
		}
	}

	/** 通过id串获取会议室基础信息列表*/
	public static List<DBMeetingRoomInfo> getMeetingRoomList(String meetingRoomIds)
	{
		List<DBMeetingRoomInfo> list = new ArrayList<DBMeetingRoomInfo>();
		if(TextUtils.isEmpty(meetingRoomIds))
		{
			return list;
		}
		
		String ids[] = meetingRoomIds.split(",");
		SysDBMeetingRoomInfoDBDao dao = new SysDBMeetingRoomInfoDBDao();
		
		for(String id:ids)
		{
			if(!TextUtils.isEmpty(id)){
				DBMeetingRoomInfo mr = dao.getDataByID(id);
				if(mr!=null){
					list.add(mr);
				}
			}
		}
		return list;
	}
	
	/** 通过id串获取会议室名称串*/
	public static String getMeetingRoomNames(String meetingRoomIds)
	{
		List<DBMeetingRoomInfo> list =getMeetingRoomList(meetingRoomIds);
		if(list==null||list.isEmpty())return "";
		
		List<String> listNames= new ArrayList<String>();
		for(DBMeetingRoomInfo mr:list)
		{
			if(!TextUtils.isEmpty(mr.getMRC())){
				listNames.add(mr.getMRC());
			}
		}
		
		LogTools.i("sql","查到的会议室名:"+listNames.toString());
		return listNames.toString().replace("[", "").replace("]", "").replace(" ", "");
		
	}
}
