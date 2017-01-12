package cn.com.zte.emeeting.app.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.com.zte.mobilebasedata.request.HttpUtil;

import android.os.Environment;

/**
 * 日志记录工具类
 * 
 * @author sun.li
 * */
public class LoggingUtil {
	
	private LoggingUtil() {
		super();
	};

	public static LoggingUtil getInstance() {
		return new LoggingUtil();
	}

	/** Log日志文件存储路径 */
	private static final String PHOTO_DIR = Environment
			.getExternalStorageDirectory() + "/Mobileemeeting";

	/** 
	 * 记录接口请求时间信息日志
	 * @param userId 用户工号
	 * @param timeLogString，如接口名+开始或结束
	 *  */
	public static void recordLog(String userId, String timeLogString) {
		if (getSDCardExist()) {

			// 获取当前时间
			long time = System.currentTimeMillis();// long now =
													// android.os.SystemClock.uptimeMillis();
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			
			Date d1 = new Date(time);
			String t1 = format.format(d1);
			String fileName = "";
			timeLogString += ":"+ HttpUtil.getInstance().getSERVEREIP()+"  "+ t1 + "\r\n";
			/* 检验创建项目文件夹*/
			isFolderExist(PHOTO_DIR);
			/* 检验创建项目请求日志存贮文件夹*/
			isFolderExist(PHOTO_DIR + "/Log");
			/* 检验创建某用户项目请求日志存贮文件夹*/
			isFolderExist(PHOTO_DIR + "/Log" + "/" + userId);
			String filePath = PHOTO_DIR + "/Log" + "/" + userId + "/";
			SimpleDateFormat format1 = new SimpleDateFormat(
					"yyyy年MM月dd日");
			try {
				fileName = format1.format(d1)+"_Log.txt";
			} catch (Exception e1) {
				e1.printStackTrace();
				fileName = "TimeLog.txt"; 
			}
			
			try {
				File timeFile = new File(filePath+fileName);
				if(!isFileExist(filePath)){
					timeFile.createNewFile();
				}
				String logStr = readSDFile(timeFile) + timeLogString;
				writeSDFile(timeFile,logStr);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	};
	
	/** 写文件  */
	public static void writeSDFile(File file, String write_str) throws IOException{    
	  
	        FileOutputStream fos = new FileOutputStream(file);    
	  
	        byte [] bytes = write_str.getBytes();   
	  
	        fos.write(bytes);   
	  
	        fos.close();   
	}   
	
	/** 读文件  */
	public static String readSDFile(File file) throws IOException {    
			StringBuffer res = new StringBuffer();
	        try {
				FileInputStream fis = new FileInputStream(file);    
  
				int length = fis.available();   
  
				 byte [] buffer = new byte[length];   
				 fis.read(buffer);
				 res.append(buffer);
				 fis.close();
			} catch (Exception e) {
				e.printStackTrace();
				res.append("");
			}
	         return res.toString();
	}    
	  

	/** 判断sd卡是否存在 */
	public static boolean getSDCardExist() {
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		return sdCardExist;
	}

	/**
	 * 判断文件夹是否存在,如果不存在则创建文件夹
	 */
	public static void isFolderExist(String FolderPath) {
		File folder = new File(FolderPath);
		if(!folder.exists()){
			folder.mkdir();
		}
	}

	/**
	 * 判断文件是否存在
	 */
	public static boolean isFileExist(String filePath) {
		boolean isR = false;
		File file = null;
		try {
			file = new File(filePath);
			isR = file.exists();
		} catch (Exception e) {
			e.printStackTrace();
			isR = false;
		}
		return isR;
	}
}
