package cn.com.zte.emeeting.app.util; 

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

import android.util.Log;

/** 
 * @author 作者 10165251 
 * @version 创建时间：2015-5-11 下午4:57:43 
 * 类说明  删除该目录下的所有文件
 */
public class FileUtil {
	private String TAG = FileUtil.class.getSimpleName();
	/**删除该目录下的所有文件*/
	public void deleteFile(File file) {
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				Log.d(TAG, "deleteFile  file.getName()=="+file.getName());
				try {
					file.delete();
				} catch (Throwable e) {
					e.printStackTrace();
				} // delete()方法 你应该知道 是删除的意思;
			} else if (file.isDirectory()) { // 否则如果它是一个目录
				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				if(files!=null&&files.length>0){
					for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
						this.deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
					}
				}
			}
		} else {
			Log.d("FileUtil", "文件不存在！");
		}
	}
	
	public void deleteFileDirectory(File file) {
		isDeleteComplete.compareAndSet(true, false);
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				Log.d(TAG, "deleteFile  file.getName()=="+file.getName());
				try {
					file.delete();
				} catch (Throwable e) {
					e.printStackTrace();
				} // delete()方法 你应该知道 是删除的意思;
			} else if (file.isDirectory()) { // 否则如果它是一个目录
				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				if(files!=null&&files.length>0){
					for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
						this.deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
					}
				}
				if(files!=null&&files.length>0){
					for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
						files[i].delete(); // 删除子文件夹
					}
				}
			}
		} else {
			Log.d("FileUtil", "文件不存在！");
		}
		isDeleteComplete.compareAndSet(false, true);
	}

	public static AtomicBoolean isDeleteComplete=new AtomicBoolean(true);
}
 