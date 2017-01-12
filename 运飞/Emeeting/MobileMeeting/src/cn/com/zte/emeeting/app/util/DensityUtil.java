package cn.com.zte.emeeting.app.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/** 单位转换工具类
 * @author sun.li
 * */
public class DensityUtil {
    /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {  
        float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    } 
    
    public static void toUpperCase(byte[] data, int start, int len)
	{
		int end = start + len;
		int dist = 'A' - 'a';
		for (int i = start; i < end; i++)
		{
			if (data[i] >= 'a' && data[i] <= 'z')
			{
				data[i] += dist;
			}
		}
	}
    /**
     * 获取屏幕宽高
     * @param context
     */
    public static int[] getDensityWidthHeight(Context context){
    	DisplayMetrics dm = new DisplayMetrics();
    	WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    	windowManager.getDefaultDisplay().getMetrics(dm);
    	int width = dm.widthPixels;
    	int height = dm.heightPixels;
//    	int width= windowManager.getDefaultDisplay().getWidth();
//    	int height = windowManager.getDefaultDisplay().getHeight();
    	int[] ints = new int[]{width,height};
    	return ints;
    }
	
	public static void toLowerCase(byte[] data, int start, int len)
	{
		int end = start + len;
		int dist = 'a' - 'A';
		for (int i = start; i < end; i++)
		{
			if (data[i] >= 'A' && data[i] <= 'Z')
			{
				data[i] += dist;
			}
		}
	}
}
