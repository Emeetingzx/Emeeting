/**
 * 
 */
package cn.com.zte.emeeting.app.views;


import java.util.List;

import cn.com.zte.emeeting.app.util.EmeetingTimeUtil.TimeScale;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;


/**
 * 该类用于:时间使用情况显示的View
 * @author wf
 */
public class ViewShowUseTimeDraw extends View {
	
	private int marginLeft =5;
	private int marginRight =5;
	private int marginTop = 10;
	private int marginBottom = 10;
	
	private int width = 100;
	private int height = 100;
	
	private float left=0;
	
	private float center=0;
	
	private float right=0;
	
	private String text="";
	
	private List<TimeScale> listTimeScale=null;
	
	/**
	 * 该类构造方法:
	 * @param @param context
	 */
	public ViewShowUseTimeDraw(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}

	public ViewShowUseTimeDraw(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initView();
	}
	

	public ViewShowUseTimeDraw(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}
//	
//	/**
//	 * @see android.view.View#onDraw(android.graphics.Canvas)
//	 */
//	@Override
//	protected void onDraw(Canvas canvas) {
//		
//		super.onDraw(canvas);
//		if(left+center+right<=0)return;
//		
//		float realWidth=width-marginLeft-marginRight;
//		float realLeft = realWidth*left+marginLeft;
//		float realCenter = realWidth*center+realLeft;
//		
//		Paint p = new Paint();
//		p.setColor(Color.GREEN);
//		canvas.drawRect(marginLeft,marginTop,width-marginRight, height-marginBottom, p);
//		p.setColor(Color.RED);
//		canvas.drawRect(realLeft, marginTop, realCenter, height-marginBottom, p);
//		
//		
//		/** 以下为居中画文本*/
//		p.setTextSize(40);
//		p.setColor(Color.BLACK);
//		
//		
//		p.setTextAlign(Align.LEFT);  
//	    Rect bounds = new Rect();  
//	    p.getTextBounds(text, 0, text.length(), bounds); 
//	    float textLeft = getMeasuredWidth()/2 - bounds.width()/2;//居中文本x坐标
//	    FontMetricsInt fontMetrics = p.getFontMetricsInt();  
//	    int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
//		
//		canvas.drawText(text,textLeft,baseline, p);
//	}
	
	/**
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		
		super.onDraw(canvas);
		
		width = getMeasuredWidth();
		height = getMeasuredHeight();
		
		
		Paint p = new Paint();
		p.setColor(Color.GREEN);
		canvas.drawRect(marginLeft,marginTop,width-marginRight, height-marginBottom, p);
		
		if(listTimeScale!=null&&!listTimeScale.isEmpty())
		{
			p.setColor(Color.RED);
			for(TimeScale t:listTimeScale)
			{
				float realWidth=width-marginLeft-marginRight;
				float realLeft = realWidth*t.getStart()+marginLeft;
				float realCenter = realWidth*t.getEnd()+marginLeft;
				canvas.drawRect(realLeft, marginTop, realCenter, height-marginBottom, p);
			}
		}
		
		
		/** 以下为居中画文本*/
		p.setTextSize(28);
		p.setColor(Color.BLACK);
		
		
		p.setTextAlign(Align.LEFT);
	    Rect bounds = new Rect();  
	    p.getTextBounds(text, 0, text.length(), bounds); 
	    float textLeft = getMeasuredWidth()/2 - bounds.width()/2;//居中文本x坐标
	    FontMetricsInt fontMetrics = p.getFontMetricsInt();  
	    int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
		
		canvas.drawText(text,textLeft,baseline, p);
	}
	
	
	/** 设置时间使用情况*/
	public void setTimeUsed(String text,List<TimeScale> listTimeScale)
	{
		this.text=text;
		this.listTimeScale=listTimeScale;
		invalidate();
	}


	/**
	 * 该方法用于:初始化控件
	 * @param 
	 * @return void
	 */
	private void initView() {

	}
	
	public void setTimeUsed(String text,float left,float center)
	{
		this.text=text;
		this.left=left;
		if(this.left>1){
			this.left=1;
		}
		
		this.center=center;
		if(this.center>1)
		{
			this.center=1;
			this.left=0;
		}
		
		this.right=1-this.left-this.center;
		invalidate();
	}
	
	/** 此方法设置左半边时间和右半边时间的使用情况*/
	public void setTimeUsed(String text,boolean leftIsUsed ,boolean rightIsUsed)
	{
		this.text=text;
		if(leftIsUsed)
		{
			this.left=0;
			this.center=0.5f;
			this.right=1-this.left-this.center;
			if(rightIsUsed)
			{
				this.left=0;
				this.center=1;
				this.right=0;
			}
		}else 
		{
			if(rightIsUsed)
			{
				this.left=0.5f;
				this.center=0.5f;
				this.right=0;
			}else
			{
				this.left=1;
				this.center=0;
				this.right=0;
			}
		}
		invalidate();
	}
	
//	/** 
//	 * 设置已使用时间的百分比
//	 * usedTimePersent+left<=1
//	 * */
//	public void setTimeUsed(float usedTimePersent,float left)
//	{
//
//	}

}
