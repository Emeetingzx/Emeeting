package cn.com.zte.emeeting.app.util;

import java.lang.reflect.Field;

import cn.com.zte.mobileemeeting.R;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/** 
 * 控件公共类
 * 
 * @author s.l
 * 
 * */
public class ViewsUtil {

	/** 获取控件高度 */
	public static int getHeight(View view) {
		int w = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		view.measure(w, h);
		return (view.getMeasuredHeight());
	}
	
	/** 获取控件宽度 */
	public static int getWidth(View view) {
		int w = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		view.measure(w, h);
		return (view.getMeasuredWidth());
	}
	
	/** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    } 
    
    /** 
     * 将px值转换为sp值，保证文字大小不变 
     *  
     * @param pxValue 
     * @param fontScale 
     *            （DisplayMetrics类中属性scaledDensity） 
     * @return 
     */  
    public static int px2sp(Context context, float pxValue) {  
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
        return (int) (pxValue / fontScale + 0.5f);  
    }  
  
    /** 
     * 将sp值转换为px值，保证文字大小不变 
     *  
     * @param spValue 
     * @param fontScale 
     *            （DisplayMetrics类中属性scaledDensity） 
     * @return 
     */  
    public static int sp2px(Context context, float spValue) {  
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
        return (int) (spValue * fontScale + 0.5f);  
    }  
	
    /** 根据资源ID的变量名获取Field对象 */
	public static int getResourceId(String name) {
		try {
			/* 根据资源ID的变量名获取Field对象 */
			Field field = R.drawable.class.getField(name);
			/* 获取并返回资源ID的字段（静态变量）的值 */
			return Integer.parseInt(field.get(null).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		/* 如果加载图片失败，则返回默认图片 */
		return 0;
	}
	
	
	/**
	 * @Title: setAdapterForRightLayout
	 * @Description: TODO 【线性布局适配器】
	 * @param @param ll
	 * @param @param rightAdapter 适配器
	 * @return void 返回类型
	 * @throws
	 */
	public static  void setAdapterForLayout(LinearLayout ll,
			BaseAdapter adapter) {

		BaseAdapter adapterRight = adapter;
		ll.removeAllViews();
		if (adapterRight == null)
			return;
		int count = adapterRight.getCount();
		for (int i = 0; i < count; i++) {
			View convertView = adapterRight.getView(i, null, null);
			ll.addView(convertView);
		}

	}
	
}
