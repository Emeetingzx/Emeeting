package cn.com.zte.emeeting.app.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.com.zte.android.validation.annotation.Regex;

import android.annotation.SuppressLint;

/** 数据操作公共类
 * @author sun.li
 * */
public class DataUtil {

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 * @param v2
	 * @return 两个参数的积
	 */
	@SuppressLint("SimpleDateFormat")
	public static double multiply(double v1, double v2) {
		double data = v1*v2;
		DecimalFormat df = new DecimalFormat("0.00");
		return Double.valueOf(df.format(data));
	}
	
	/**
	 * 提供精确的除法运算。
	 * 
	 * @param v1
	 * @param v2
	 * @return 两个相除的结果
	 */
	@SuppressLint("SimpleDateFormat")
	public static double division(double v1, double v2) {
		double data = v1/v2;
		DecimalFormat df = new DecimalFormat("0.00");
		return Double.valueOf(df.format(data));
	}
	
	 /** 
     * 提供精确的加法运算。 
     *  
     * @param v1 
     *            被加数 
     * @param v2 
     *            加数 
     * @return 两个参数的和 
     */  
  
    public static double add(double v1, double v2) {  
        BigDecimal b1 = new BigDecimal(Double.toString(v1));  
        BigDecimal b2 = new BigDecimal(Double.toString(v2));  
        return b1.add(b2).doubleValue();  
    }  
    
    /**
     * 获取字符串中数字
     * @param s
     * @return
     */
    public static String getNumber(String s){
    	String ss = "";
    	try {
            for(String sss:s.replaceAll("[^0-9]", ",").split(",")){  
                if (sss.length()>0)  
                    ss = ss+sss;  
            }  
		} catch (Exception e) {
			// TODO: handle exception
			ss = "";
		}
    	
         return ss;
    }
    /** 
     * 提供精确的减法运算。 
     *  
     * @param v1 
     *            被减数 
     * @param v2 
     *            减数 
     * @return 两个参数的差 
     */  
  
    public static double sub(double v1, double v2) {  
        BigDecimal b1 = new BigDecimal(Double.toString(v1));  
        BigDecimal b2 = new BigDecimal(Double.toString(v2));  
        return b1.subtract(b2).doubleValue();  
    }  
	
	/** 乘法计算商品价格结果
	 * @return double
	 * */
	public static double getGoodsPrice(double goodsNumber,double price){
		double goodsPrice = 0;
		if(price >= 0){
			goodsPrice = DataUtil.multiply(goodsNumber, price);
		}
		return goodsPrice;
	}
	
	/** 读取TXT文本内容*/
	public static String getString(InputStream inputStream) { 
	    InputStreamReader inputStreamReader = null;  
	    try {  
	        inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
	    } catch (UnsupportedEncodingException e1) {  
	        e1.printStackTrace();  
	    }  
	    BufferedReader reader = new BufferedReader(inputStreamReader);  
	    StringBuffer sb = new StringBuffer("");  
	    String line;  
	    try {  
	        while ((line = reader.readLine()) != null) {  
	            sb.append(line);  
	            sb.append("\n");  
	        }  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	    return sb.toString();  
	}

	/**获取系统时间*/
	public static String getSystemDate(){
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
		return format.format(new java.util.Date());
}
	
	/**
	 * 计算时期与当前的时间差
	 * */
	public static String getDateString(String date){
		String str="";
		  SimpleDateFormat D = new SimpleDateFormat("yyyy年MM月dd日");
		   Date begin=String2Date(date);//发布时间

		   Date end = new Date();//当前时间

		   long between=(end.getTime()-begin.getTime())/1000;//除以1000是为了转换成秒
		   	
		   if (between<60) {
			str=between+"秒前";
		}else if (between>=60&&between<3600) {
			str=between/60+"分钟前";
		}else if (between>=3600&&between<86400) {
			str=between/3600+"小时前";
		}else if (between>=86400&&between<604800) {
			str=between/86400+"天前";
		}else if(between>=604800&&between<2592000){
			str=between/604800+"个月前";
		}else if(between>=2592000&&between<31536000){
			str=between/2592000+"年前";
		}
		else{
			str=D.format(String2Date(date));
		}
		   
		 
		
		return str;
	}
	
	/**
	 * method 将字符串类型的日期转换为一个Date（java.sql.Date）
	 * 
	 * @param dateString
	 *            需要转换为Date的字符串
	 * @return dataTime Date
	 */
	public final static Date String2Date(String dateString) {
		DateFormat dateFormat;
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// Locale.ENGLISH

		dateFormat.setLenient(false);
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// java.sql.Date dateTime = new java.sql.Date(date.getTime());// sql类型
		return date;// new java.sql.Date(date.getTime());
	}
	
	/** 判断输入的字符串是否只包含英文字母 */ 
	public static boolean IsNumAndEnCh(String input)  
	{  
		String pattern = "[a-zA-Z]+";  
	    return input.matches(pattern);  
	} 
}
