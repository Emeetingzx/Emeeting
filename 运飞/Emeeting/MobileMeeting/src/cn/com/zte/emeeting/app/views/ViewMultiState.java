/**
 * 
 */
package cn.com.zte.emeeting.app.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

/**
 * 该类用于:显示会议室指定时间段内的被使用状态
 * @author wf
 */
public class ViewMultiState extends View {
	
	private int currentState=STATE_NOT_USE;
	
	/** 已使用,不可再用*/
	public static final int STATE_USED=2;
	
	/** 准备用,处理正在选中状态*/
	public static final int STATE_READY_USE=1;
	
	/** 未使用,可被选中*/
	public static final int STATE_NOT_USE=0;
	
	
	private static final int COLOR_USED=Color.parseColor("#aaaaaa");
	
	private static final int COLOR_READY_USE=Color.parseColor("#0000bb");
	
	private static final int COLOR_NOT_USE=Color.parseColor("#00bb00");
	
	
	

	public ViewMultiState(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		setUsedState(currentState);
	}

	public ViewMultiState(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		setUsedState(currentState);
	}

	public ViewMultiState(Context context) {
		super(context);
		setUsedState(currentState);
	}
	
	/** 设置当前使用状态*/
	public void setUsedState(int state)
	{
		if(state==STATE_NOT_USE)
		{
			setBackgroundColor(COLOR_NOT_USE);
			currentState=STATE_NOT_USE;
		}else if(state==STATE_READY_USE)
		{
			setBackgroundColor(COLOR_READY_USE);
			currentState=STATE_READY_USE;
		}else if(state==STATE_USED)
		{
			setBackgroundColor(COLOR_USED);
			currentState=STATE_USED;
		}else
		{
			setBackgroundColor(COLOR_NOT_USE);
			currentState=STATE_NOT_USE;
		}
	}
	
	/** 获取当前使用状态*/
	public int getUsedState()
	{
		return currentState;
	}
}
