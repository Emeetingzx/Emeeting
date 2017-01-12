package cn.com.zte.emeeting.app.util;

import android.annotation.SuppressLint;
import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.com.zte.emeeting.app.base.ConfigrationList;

/**
 * 日期格式转换工具类
 * 
 * @author LiuZhiYun
 * 
 */
public class DateFormatUtil {

	/** 将传入的日期格式转换为yyyy-MM-dd(例如传入"Jan 1,2015 12:00:00 AM",传出2015-01-01) */
	@SuppressLint("SimpleDateFormat")
	public static String changeDateFormat(String date) {
		String newDate = "";
		DateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date time = null;
		try {
			time = df.parse(date);
			newDate = sdf.format(time);
		} catch (Exception e) {
			e.printStackTrace();
			newDate = "";
		}
		if (newDate.equals("")) {
			try {
				time = df1.parse(date);
				newDate = sdf.format(time);
			} catch (Exception e) {
				e.printStackTrace();
				newDate = "";
			}
		}
		return newDate;
	}

	/**
	 * 比较日期大小 如果date大于date2返回true
	 * */
	@SuppressLint("SimpleDateFormat")
	public static boolean compareDate(String date, String date2) {
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date time = null;
		Date time2 = null;
		try {
			time = df1.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
		try {
			time2 = df1.parse(date2);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if (time.after(time2)) {
			return true;
		}
		return false;
	}
	

	/** 将传入的日期格式转换为MM-dd(例如传入"Jan 1,2015 12:00:00 AM",传出2015-01-01) */
	@SuppressLint("SimpleDateFormat")
	public static String changeDateFormatMMDD(String date) {
//		String newDate = "";
//		if(EmailDataOperationUtil.objectValueIsNotEmpty(date)){
//			DateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
//			DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
//			Date time = null;
//			try {
//				time = df.parse(date);
//				newDate = sdf.format(time);
//			} catch (Exception e) {
//				e.printStackTrace();
//				newDate = "";
//			}
//			if (newDate.equals("")) {
//				try {
//					time = df1.parse(date);
//					newDate = sdf.format(time);
//				} catch (Exception e) {
//					e.printStackTrace();
//					newDate = "";
//				}
//			}
//		}
		return formatDateTime(date);
	}
	
	/**
	 * 格式化时间
	 * @param time
	 * @return
	 */
	private static String formatDateTime(String time) {
		SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		if(time==null ||"".equals(time)){
			return "";
		}
		Date date = null;
		try {
			date = format.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Calendar current = Calendar.getInstance();
		
		Calendar today = Calendar.getInstance();	//今天
		
		today.set(Calendar.YEAR, current.get(Calendar.YEAR));
		today.set(Calendar.MONTH, current.get(Calendar.MONTH));
		today.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH));
		//  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
		today.set( Calendar.HOUR_OF_DAY, 0);
		today.set( Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		
		Calendar yesterday = Calendar.getInstance();	//昨天
		
		yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
		yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
		yesterday.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH)-1);
		yesterday.set( Calendar.HOUR_OF_DAY, 0);
		yesterday.set( Calendar.MINUTE, 0);
		yesterday.set(Calendar.SECOND, 0);
		
		current.setTime(date);
		
		if(current.after(today)){
//			return "今天 "+time.split(" ")[1];
			return "今天 ";
		}else if(current.before(today) && current.after(yesterday)){
//			return "昨天 "+time.split(" ")[1];
			return "昨天 ";
		}else{
			int index = time.indexOf("-")+1;
			return time.substring(index, time.length()).replace(time.split(" ")[1], "");
		}
	}

	/**
	 * 将传入的日期格式转换为yyyy-MM-dd
	 * 
	 * @param date
	 *            传入的日期
	 * @param fromFormat
	 *            传入的日期格式
	 * @param toFormat
	 *            传出的日期格式
	 * @return 传出的日期
	 */
	@SuppressLint("SimpleDateFormat")
	public static String formatDate(String date, String fromFormat,
			String toFormat) {
		String newDate = "";
		SimpleDateFormat fromsdf = new SimpleDateFormat(fromFormat);
		SimpleDateFormat tosdf = new SimpleDateFormat(toFormat);
		Date time;
		try {
			time = fromsdf.parse(date);
			newDate = tosdf.format(time);
		} catch (ParseException e) {
			e.printStackTrace();
			newDate = "";
		}
		return newDate;
	}
	
	/**
	 * 将传入的日期格式转换为yyyy-MM-dd
	 * 
	 * @param date
	 *            传入的日期
	 * @param fromFormat
	 *            传入的日期格式
	 * @param toFormat
	 *            传出的日期格式
	 * @return 传出的日期
	 */
	@SuppressLint("SimpleDateFormat")
	public static String formatDate(Date date, String fromFormat){
		SimpleDateFormat fromsdf = new SimpleDateFormat(fromFormat);
		String result = fromsdf.format(date);
		return result;
	}

	/** 获取系统当前时间 */
	@SuppressLint("SimpleDateFormat")
	public static String getSystemDate() {
		String newDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date time = new Date(System.currentTimeMillis());
		try {
			newDate = sdf.format(time);
		} catch (Exception e) {
			e.printStackTrace();
			newDate = "";
		}
		return newDate;
	}

	/** 获取系统当前时间 */
	@SuppressLint("SimpleDateFormat")
	public static String getSystemDate(String format) {
		String newDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date time = new Date(System.currentTimeMillis());
		try {
			newDate = sdf.format(time);
		} catch (Exception e) {
			e.printStackTrace();
			newDate = "";
		}
		return newDate;
	}

	/** 获取系统当前前一天时间 */
	@SuppressLint("SimpleDateFormat")
	public static String getSystemBeforeDate() {
		String newDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date time = new Date(System.currentTimeMillis());// 当前时间
		Date dBefore = new Date();// 当前前一天时间
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(time);// 把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1); // 设置为前一天
		dBefore = calendar.getTime(); // 得到前一天的时间
		try {
			newDate = sdf.format(dBefore);
		} catch (Exception e) {
			e.printStackTrace();
			newDate = "";
		}
		return newDate;
	}
	
	/** 获取系统当前时间 */
	@SuppressLint("SimpleDateFormat")
	public static Date getServerTime(Context context) {
		long timeDifference = new SharedPreferenceUtil(ConfigrationList.USERINFO, context).getLong(ConfigrationList.TimeDifference);
		final Date currDate = new Date(System.currentTimeMillis()+timeDifference);
		return currDate;
	}

}
