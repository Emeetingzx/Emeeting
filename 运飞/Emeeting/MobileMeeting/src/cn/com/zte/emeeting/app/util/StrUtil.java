package cn.com.zte.emeeting.app.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ab.util.AbDateUtil;
import com.ab.util.AbStrUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextPaint;
import android.util.Base64;
import android.widget.TextView;

/**
 * 
 * @ClassName: StrUtil
 * @Description: TODO 【字符串处理工具类】
 * @author Pan.Jianbo
 * @date 2015-8-14 上午10:40:03
 * 
 */
public class StrUtil {

	/**
	 * 
	 * @Title: getNumbers
	 * @Description: TODO【截取数字】
	 * @param @param content 需要截取的数字的字符串
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getNumbers(String content) {
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			return matcher.group(0);
		}
		return "";
	}

	/**
	 * 
	 * @Title: splitNotNumber
	 * @Description: TODO 【截取非数字】
	 * @param @param content 需要截取的字符串
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String splitNotNumber(String content) {
		Pattern pattern = Pattern.compile("\\D+");
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			return matcher.group(0);
		}
		return "";
	}

	/**
	 * 
	 * @Title: setTextViewByBold
	 * @Description: TODO 【设置TextView字体加粗】
	 * @param @param textView 需要设置加粗的TextView
	 * @return void 返回类型
	 * @throws
	 */
	public static void setTextViewByBold(TextView textView) {

		TextPaint paint = textView.getPaint();
		paint.setFakeBoldText(true);

	}

	/**
	 * 
	 * @Title: setChecked
	 * @Description: TODO 【设置单选框的初始状态】
	 * @param @param bool
	 * @param @param textView 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public static void setChecked(boolean bool, TextView textView) {
		textView.setSelected(bool);
	}


	/**
	 * List 转成String
	 * 
	 * @param SceneList
	 * @return
	 * @throws IOException
	 */
	public static String SceneList2String(List SceneList) throws IOException {
		// 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件。
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		// 然后将得到的字符数据装载到ObjectOutputStream
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				byteArrayOutputStream);
		// writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
		objectOutputStream.writeObject(SceneList);
		// 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
		String SceneListString = new String(Base64.encode(
				byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
		// 关闭objectOutputStream
		objectOutputStream.close();
		return SceneListString;

	}

	/**
	 * String 转成List
	 * 
	 * @param SceneListString
	 * @return
	 * @throws StreamCorruptedException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public static List String2SceneList(String SceneListString)
			throws StreamCorruptedException, IOException,
			ClassNotFoundException {
		byte[] mobileBytes = Base64.decode(SceneListString.getBytes(),
				Base64.DEFAULT);
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				mobileBytes);
		ObjectInputStream objectInputStream = new ObjectInputStream(
				byteArrayInputStream);
		List SceneList = (List) objectInputStream.readObject();
		objectInputStream.close();
		return SceneList;
	}

	/**
	 * 以最省内存的方式读取本地资源的图片
	 * 
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap readBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	/**
	 * 
	 * @Title: isZh
	 * @Description: TODO 【获取当前系统的语言】
	 * @param @param mContext
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws zh
	 *             ：汉语 ;en：英语
	 * 
	 */
	public static boolean getLanguag(Context mContext) {
		Locale locale = mContext.getResources().getConfiguration().locale;
		String language = locale.getLanguage();
		if (language.endsWith("zh"))
			return true;
		else
			return false;
	}

	/**
	 * 
	 * @Title:DataTimeTest
	 * @Description: TODO 【比较两个日期的大小】
	 * @param @param DATE1 【开始时间】
	 * @param @param DATE2 【结束时间】
	 * @param @return 设定文件
	 * @return $ 返回类型
	 * @date 2015年9月13日 下午12:46:32
	 * @throws 注意
	 *             ：返回数据作如下解释： 1、表示开始时间大于结束时间； 2、表示开始时间小于结束时间； 0、表示开始时间等于结束时间
	 */
	public static int compare_date(String DATE1, String DATE2) {

		DateFormat df = new SimpleDateFormat(AbDateUtil.dateFormatYMDHMS);
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				System.out.println("开始时间大于结束时间");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				System.out.println("开始时间小于结束时间");
				return 2;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * 
	 * @Title:TestList
	 * @Description: TODO 【获取文件名称去掉后缀】
	 * @param @param str
	 * @param @return 设定文件
	 * @return $ 返回类型
	 * @date 2015年9月12日 下午10:59:46
	 * @throws
	 */
	public static String getStrByFileName(String str) {

		if (!AbStrUtil.isEmpty(str)) {
			int start = str.indexOf(".");
			if (start != -1) {
				return str.substring(0, start);
			} else {
				return str;
			}
		} else {
			return null;
		}

	}

	/**
	 * 
	 * @Title: getStrByFileSuffix
	 * @Description: TODO 【获取文件后缀】
	 * @param @param str
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getStrByFileSuffix(String str) {

		if (!AbStrUtil.isEmpty(str)) {
			int start = str.indexOf(".");
			if (start != -1) {
				return str.substring(start + 1, str.length());
			} else {
				return str;
			}
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @Title: getNewStr
	 * @Description: TODO 【处理换行】
	 * @param @param s2
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getNewStr(String s2) {
		String newStr = "";
		if (!AbStrUtil.isEmpty(s2)) {
			String s1 = "\\r\\n";
			String s = "\r\n";
			if (s2.contains(s1) || s2.contains(s)) {
				System.out.println("s2包含了s1");
				// 删掉s1
				s2 = s2.replace(s1, "\n");
				s2 = s2.replace(s, "\n");
				System.out.println(s2);
				newStr = s2;
			} else {
				System.out.println("s2不包含s1");
			}
		}

		return newStr;
	}

	/**
	 * 
	 * @Title: getNewStr
	 * @Description: TODO 【处理换行】
	 * @param @param s2
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getNewStr2(String s2) {
		String newStr = "";
		if (!AbStrUtil.isEmpty(s2)) {
			String s1 = ";";
			if (s2.contains(s1)) {
				System.out.println("s2包含了s1");
				// 删掉s1
				newStr = s2.replace(s1, "\n");
			} else {
				newStr = s2;
			}
		}

		return newStr;
	}

	/**
	 * 
	 * @Title: getNewStr
	 * @Description: TODO 【处理</br>换行】
	 * @param @param s2
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getNewlineStr(String str) {
		if (!AbStrUtil.isEmpty(str)) {
			if (str.endsWith("<br/>") || str.endsWith("</br>")) {
				str = str.substring(0, str.length() - 5);
			}
			String s1 = "<br/>";
			String s2 = "</br>";
			String s3 = "<br />";
			if (str.contains(s1) || str.contains(s2) || str.contains(s3)) {
				str = str.replace(s1, "\n");
				str = str.replace(s2, "\n");
				str = str.replace(s3, "\n");
			}
		}

		return str;
	}

	/**
	 * 
	 * @Title: dayHourMinConvertMin
	 * @Description: TODO 【天、时、分转成分钟】
	 * @param @param day
	 * @param @param hour
	 * @param @param min
	 * @param @return 设定文件
	 * @return int 返回类型
	 * @throws
	 */
	public static int dayHourMinConvertMin(int day, int hour, int min) {
		int days = day * 24 * 60;
		int hours = hour * 60;
		return days + hours + min;
	}

	/**
	 * 
	 * @Title: minConvertDayHourMin
	 * @Description: TODO 【分钟转换为 天 时 分】
	 * @param @param min
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String minConvertDayHourMin(Double min) {
		String html = "";
		if (min != null) {
			Double m = min;
			String format;
			Object[] array;
			Integer days = (int) (m / (60 * 24));
			Integer hours = (int) (m / (60) - days * 24);
			Integer minutes = (int) (m - hours * 60 - days * 24 * 60);
			if (days > 0) {
				format = "%1$,d天%2$,d时%3$,d分";
				array = new Object[] { days, hours, minutes };
			} else if (hours > 0) {
				format = "%1$,d时%2$,d分";
				array = new Object[] { hours, minutes };
				;
			} else {
				format = "%1$,d分";
				array = new Object[] { minutes };
			}
			html = String.format(format, array);
		}

		return html;
	}

	/**
	 * 
	 * @Title: getExItemID
	 * @Description: TODO 【获取服务器返回的时间排除项ID】
	 * @param @param str
	 * @param @return 设定文件
	 * @return String[] 返回类型
	 * @throws
	 */
	public static String[] getExItemID(String str) {
		String[] strArray = null;
		if (!AbStrUtil.isEmpty(str)) {
			strArray = str.split(",");
		}
		return strArray;
	}

	/**
	 * 
	 * @Title: removeDuplicate
	 * @Description: TODO 【去除List中相同元素】
	 * @param @param list 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public static void removeDuplicate(List list) {
		HashSet h = new HashSet(list);
		list.clear();
		list.addAll(h);
		System.out.println(list);
	}

	/** 按照传入的标识将String截为String[] */
	public static String[] cutoffString(String allStr, String icon) {
		String a[] = new String[] {};
		if (AbStrUtil.isEmpty(allStr)) {
			return a;
		} else {
			a = allStr.split(icon);
		}
		return a;
	}

	/**
	 * 功能介绍:根据间隔字符将一字符串切成数组 输入参数:要切的字符串,间隔字符 输出参数:字符串数组
	 **************************************************************************/
	public static final String[] split(String str, String pre) {

		if (str == null || str.length() < 1)
			return null;

		if (!str.contains(pre)) {
			String[] arr = new String[1];
			arr[0] = str;
			return arr;
		}

		Vector<String> veTmp = new Vector<String>();
		while (str.indexOf(pre) != -1) {// 取得每个标志前面的字符内容
			veTmp.addElement(str.substring(0, str.indexOf(pre)));
			str = str.substring(str.indexOf(pre) + 1, str.length());
		}
		if (str.length() > 0) {// 最后一个标志后还有字符内容则保存
			veTmp.addElement(str);
		}
		if (veTmp.size() == 0) {
			return null;
		}
		String arrTmp[] = new String[veTmp.size()];
		for (int i = 0; i < arrTmp.length; i++) {
			arrTmp[i] = (String) veTmp.elementAt(i);
		}
		veTmp = null;
		return arrTmp;
	}

	/**
	 * 功能介绍:根据间隔字符将一字符串切成数组 输入参数:要切的字符串,间隔字符 输出参数:字符串数组
	 **************************************************************************/
	public static final ArrayList<String> splitToArr(String str, String pre) {

		if (str == null || str.length() < 1)
			return null;

		ArrayList arr = new ArrayList<String>();

		if (!str.contains(pre)) {
			arr.add(str);
			return arr;
		}

		Vector<String> veTmp = new Vector<String>();
		while (str.indexOf(pre) != -1) {// 取得每个标志前面的字符内容
			veTmp.addElement(str.substring(0, str.indexOf(pre)));
			str = str.substring(str.indexOf(pre) + 1, str.length());
		}
		if (str.length() > 0) {// 最后一个标志后还有字符内容则保存
			veTmp.addElement(str);
		}
		if (veTmp.size() == 0) {
			return null;
		}
		for (int i = 0; i < veTmp.size(); i++) {
			arr.add((String) veTmp.elementAt(i));
		}
		veTmp = null;
		return arr;
		
	}

	/**
	 * 
	 * @Title: getDateFormatYMDHMS
	 * @Description: TODO 【获取开始时间结束时间】
	 * @param @param date
	 * @param @param bool
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getDateFormatYMDHMS(String date, boolean bool) {
		String dateFormatYMDHMS = null;
		if (!AbStrUtil.isEmpty(date)) {
			if (bool) {
				String timeStart = date + " " + "00" + ":" + "00" + ":" + "00";
				dateFormatYMDHMS = AbDateUtil.getStringByFormat(timeStart,
						AbDateUtil.dateFormatYMDHMS);
			} else {
				String timeEnd = date + " " + "23" + ":" + "59" + ":" + "59";
				dateFormatYMDHMS = AbDateUtil.getStringByFormat(timeEnd,
						AbDateUtil.dateFormatYMDHMS);
			}
		}

		return dateFormatYMDHMS;
	}
	
}
