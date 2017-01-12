package cn.com.zte.emeeting.app.views;

import cn.com.zte.android.common.log.Log;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;

public class MyWebView extends WebView {
	
    // 双击手势
    private static final int TAP_DOUBLE = 4;
    
	public static final int STATUS_INIT = 1;//常量初始化
	public static final int STATUS_ZOOM_OUT = 2;//放大状态常量
	public static final int STATUS_ZOOM_IN = 3;//缩小状态常量
	public static final int STATUS_MOVE = 4;//图片拖动状态常量
	private int currentStatus;//记录当前操作的状态，可选值为STATUS_INIT、STATUS_ZOOM_OUT、STATUS_ZOOM_IN和STATUS_MOVE
	private float lastXMove = -1;//记录上次手指移动时的横坐标
	private float lastYMove = -1;//记录上次手指移动时的纵坐标
	private float movedDistanceX;//记录手指在横坐标方向上的移动距离
	private float movedDistanceY;//记录手指在纵坐标方向上的移动距离
	private float totalTranslateX;//记录图片在矩阵上的横向偏移值
	private float totalTranslateY;//记录图片在矩阵上的纵向偏移值
	private float centerPointX;//记录两指同时放在屏幕上时，中心点的横坐标值
	private float centerPointY;//记录两指同时放在屏幕上时，中心点的纵坐标值
    private ZoomSize initRatio;//记录webview初始化时的缩放比例
	private double lastFingerDis;//记录上次两指之间的距离
	
	 public enum ZoomSize {
	        SMALL(10),      // 240dpi
	        SMALLNORMALL(12),      // 240dpi
	        NORMAL(14),      // 240dpi
	        BIGNORMALL(16),      // 240dpi
	        BIG(18);     // 120dpi
	        ZoomSize(int size) {
	            value = size;
	        }
	        int value;
	    }
    
	public MyWebView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public MyWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public MyWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MyWebView(Context context, AttributeSet attrs, int defStyle,
			boolean privateBrowsing) {
		super(context, attrs, defStyle, privateBrowsing);
		// TODO Auto-generated constructor stub
	}
	
	
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		if (initRatio == ZoomSize.BIG) {
//			getParent().requestDisallowInterceptTouchEvent(false);
//		} else {
//			getParent().requestDisallowInterceptTouchEvent(true);
//		}
//		switch (event.getActionMasked()) {
//		case MotionEvent.ACTION_POINTER_DOWN:
//			if (event.getPointerCount() == 2) {
//				// 当有两个手指按在屏幕上时，计算两指之间的距离
//				lastFingerDis = distanceBetweenFingers(event);
//			}
//			break;
//		case MotionEvent.ACTION_CANCEL:
//		case MotionEvent.ACTION_MOVE:
//			if (event.getPointerCount() == 1) {
//				// 只有单指按在屏幕上移动时，为拖动状态
//				float xMove = event.getX();
//				float yMove = event.getY();
//				if (lastXMove == -1 && lastYMove == -1) {
//					lastXMove = xMove;
//					lastYMove = yMove;
//				}
//				currentStatus = STATUS_MOVE;
//				movedDistanceX = xMove - lastXMove;
//				movedDistanceY = yMove - lastYMove;
//				// 进行边界检查，不允许将图片拖出边界
//				if (totalTranslateX + movedDistanceX > 0) {
//					movedDistanceX = 0;
//				} else if (width - (totalTranslateX + movedDistanceX) > currentBitmapWidth) {
//					movedDistanceX = 0;
//				}
//				if (totalTranslateY + movedDistanceY > 0) {
//					movedDistanceY = 0;
//				} else if (height - (totalTranslateY + movedDistanceY) > currentBitmapHeight) {
//					movedDistanceY = 0;
//				}
//				// 调用onDraw()方法绘制图片
//				invalidate();
//				lastXMove = xMove;
//				lastYMove = yMove;
//			} else if (event.getPointerCount() == 2) {
//				// 有两个手指按在屏幕上移动时，为缩放状态
//				centerPointBetweenFingers(event);
//				double fingerDis = distanceBetweenFingers(event);
//				if (fingerDis > lastFingerDis) {
//					currentStatus = STATUS_ZOOM_OUT;
//				} else {
//					currentStatus = STATUS_ZOOM_IN;
//				}
//				// 进行缩放倍数检查，最大只允许将图片放大4倍，最小可以缩小到初始化比例
//				if ((currentStatus == STATUS_ZOOM_OUT && totalRatio < 4 * initRatio)
//						|| (currentStatus == STATUS_ZOOM_IN && totalRatio > initRatio)) {
//					scaledRatio = (float) (fingerDis / lastFingerDis);
//					totalRatio = totalRatio * scaledRatio;
//					if (totalRatio > 4 * initRatio) {
//						totalRatio = 4 * initRatio;
//					} else if (totalRatio < initRatio) {
//						totalRatio = initRatio;
//					}
//					// 调用onDraw()方法绘制图片
//					invalidate();
//					lastFingerDis = fingerDis;
//				}
//			}
//			break;
//		case MotionEvent.ACTION_POINTER_UP:
//			if (event.getPointerCount() == 2) {
//				// 手指离开屏幕时将临时值还原
//				Log.i("双指离开", "POINTER_UP");
//				lastXMove = -1;
//				lastYMove = -1;
//				isR = true;
//			}
//			break;
//		case MotionEvent.ACTION_UP:
//			// 手指离开屏幕时将临时值还原
//			lastXMove = -1;
//			lastYMove = -1;
//			if(event.getPointerCount() == 1 && !isR){
//				Log.i("单指离开", "UP");
//				if (isTouchClose) {
//					if(dl!=null){
//						dl.dismiss();
//					}
//				}
//				
//			}else{
//				isR = false;
//			}
//			break;
//		default:
//			break;
//		}
//		return true;
//	}

	
	/**
	 * 计算两个手指之间的距离。
	 * @param event
	 * @return 两个手指之间的距离
	 */
	private double distanceBetweenFingers(MotionEvent event) {
		float disX = Math.abs(event.getX(0) - event.getX(1));
		float disY = Math.abs(event.getY(0) - event.getY(1));
		return Math.sqrt(disX * disX + disY * disY);
	}
}
