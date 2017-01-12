/**
 * 
 */
package cn.com.zte.emeeting.app.views;

import cn.com.zte.emeeting.app.util.DensityUtil;
import cn.com.zte.mobileemeeting.R;
import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;


/**
 * 仿iphone带进度的进度条，线程安全的View，可直接在线程中更新进度
 * @author xiaanming
 */
public class CircleBar extends View {
	/**
	 * 画笔对象的引用
	 */
	private Paint paint;
	
	/**
	 * 圆环的颜色
	 */
	private int roundColor=Color.RED;
	
	/**
	 * 圆环进度的颜色
	 */
	private int roundProgressColor=Color.GREEN;
	
	/**
	 * 中间进度百分比的字符串的颜色
	 */
	private int textColorProgress=Color.BLUE;
	
	/**
	 * 中间状态字符串的颜色
	 */
	private int textColorState=Color.BLUE;
	
	/**
	 * 中间进度百分比的字符串的字体
	 */
	private float textSizeProgress=14;
	
	/**
	 * 中间状态字符串的字体
	 */
	private float textSizeState=12;
	
	/**
	 * 圆环的宽度
	 */
	private float roundWidth=5;
	
	/**
	 * 最大进度
	 */
	private int max=100;
	
	/**
	 * 当前进度
	 */
	private int progress=75;
	/**
	 * 是否显示中间的进度
	 */
	private boolean textIsDisplayable=true;
	
	/** 是否为相反进度*/
	private boolean isReverseProgress=false;
	
	/**
	 * 进度的风格，实心或者空心
	 */
	private int style=0;
	
	private String state ="空闲";
	
	public static final int STROKE = 0;
	public static final int FILL = 1;
	
	public CircleBar(Context context) {
		this(context, null);
		init(isReverseProgress);
	}

	public CircleBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		init(isReverseProgress);
	}
	
	public CircleBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(isReverseProgress);

		
//		TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
//				R.styleable.RoundProgressBar);
//		
//		//获取自定义属性和默认值
//		roundColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundColor, Color.RED);
//		roundProgressColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundProgressColor, Color.GREEN);
//		textColor = mTypedArray.getColor(R.styleable.RoundProgressBar_textColor, Color.GREEN);
//		textSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_textSize, 15);
//		roundWidth = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundWidth, 5);
//		max = mTypedArray.getInteger(R.styleable.RoundProgressBar_max, 100);
//		textIsDisplayable = mTypedArray.getBoolean(R.styleable.RoundProgressBar_textIsDisplayable, true);
//		style = mTypedArray.getInt(R.styleable.RoundProgressBar_style, 0);
//		
//		mTypedArray.recycle();
	}
	

	/**
	 * @param isReverseProgress 是否为相反的进度
	 * @return void
	 */
	private void init(boolean isReverseProgress) {
		roundWidth=DensityUtil.dip2px(getContext(), 5);
	
		textSizeProgress=getContext().getResources().getDimension(R.dimen.textsize_14);
//		textColorProgress=getContext().getResources().getColor(R.color.textcolor_filter_blue);
		textColorProgress=Color.parseColor("#01aeff");
		
		textSizeState =getContext().getResources().getDimension(R.dimen.textsize_12);
		textColorState = getContext().getResources().getColor(R.color.textcolor_gray_6);
		
		if(!isReverseProgress)
		{
			roundProgressColor=getContext().getResources().getColor(R.color.bgcolor_filter_yellow);
			roundColor=getContext().getResources().getColor(R.color.bgcolor_gray_c);
		}else
		{
			roundColor=getContext().getResources().getColor(R.color.bgcolor_filter_yellow);
			roundProgressColor=getContext().getResources().getColor(R.color.bgcolor_gray_c);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint = new Paint();
		/**
		 * 画最外层的大圆环
		 */
		int centre = getWidth()/2; //获取圆心的x坐标
		int radius = (int) (centre - roundWidth/2); //圆环的半径
		paint.setColor(roundColor); //设置圆环的颜色
		paint.setStyle(Paint.Style.STROKE); //设置空心
		paint.setStrokeWidth(roundWidth); //设置圆环的宽度
		paint.setAntiAlias(true);  //消除锯齿 
		canvas.drawCircle(centre, centre, radius, paint); //画出圆环
		
		Log.e("log", centre + "");
		
		/**
		 * 画进度百分比
		 */
		paint.setStrokeWidth(0); 
		paint.setColor(textColorProgress);
		paint.setTextSize(textSizeProgress);
//		paint.setTypeface(Typeface.DEFAULT_BOLD); //设置字体
		int percent = (int)(((float)progress / (float)max) * 100);  //中间的进度百分比，先转换成float在进行除法运算，不然都为0
		
		if(isReverseProgress)
		{
			percent = (int)(((float)(max-progress)/ (float)max) * 100);
		}
		
		float textWidth = paint.measureText(percent + "%");   //测量字体宽度，我们需要根据字体的宽度设置在圆环中间
		
		if(textIsDisplayable && percent >= 0 && style == STROKE){
//			canvas.drawText(percent + "%", centre - textWidth / 2, centre + textSizeProgress/2, paint); //画出进度百分比
			canvas.drawText(percent + "%", centre - textWidth / 2, centre, paint); //画出进度百分比
			paint.setColor(textColorState);
			paint.setTextSize(textSizeState);
			float stateCenter = paint.measureText(state);;
			canvas.drawText(state, centre - stateCenter / 2, centre+textSizeState, paint); //画出进度百分比
		}
		
		
		/**
		 * 画圆弧 ，画圆环的进度
		 */
		
		//设置进度是实心还是空心
		paint.setStrokeWidth(roundWidth); //设置圆环的宽度
		paint.setColor(roundProgressColor);  //设置进度的颜色
		RectF oval = new RectF(centre - radius, centre - radius, centre
				+ radius, centre + radius);  //用于定义的圆弧的形状和大小的界限
		float startAngle =-90;
		switch (style) {
		case STROKE:{
			paint.setStyle(Paint.Style.STROKE);
			canvas.drawArc(oval, startAngle, 360 * progress / max, false, paint);  //根据进度画圆弧
			break;
		}
		case FILL:{
			paint.setStyle(Paint.Style.FILL_AND_STROKE);
			if(progress !=0)
				canvas.drawArc(oval, startAngle, 360 * progress / max, true, paint);  //根据进度画圆弧
			break;
		}
		}
		
	}
	
	private void drawCircle(Paint paint)
	{
		
	}
	
	private void drawProgress(Paint paint)
	{
		
	}
	
	public synchronized int getMax() {
		return max;
	}

	/**
	 * 设置进度的最大值
	 * @param max
	 */
	public synchronized void setMax(int max) {
		if(max < 0){
			throw new IllegalArgumentException("max not less than 0");
		}
		this.max = max;
	}

	/**
	 * 获取进度.需要同步
	 * @return
	 */
	public synchronized int getProgress() {
		return progress;
	}

	/**
	 * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
	 * 刷新界面调用postInvalidate()能在非UI线程刷新
	 * @param progress
	 */
	public synchronized void setProgress(int progress) {
		if(progress < 0){
//			throw new IllegalArgumentException("progress not less than 0");
			progress=0;
		}
		if(progress > max){
			progress = max;
		}
		if(progress <= max){
			this.progress = progress;
			postInvalidate();
		}
		
	}
	
	
	public int getCricleColor() {
		return roundColor;
	}

	public void setCricleColor(int cricleColor) {
		this.roundColor = cricleColor;
	}

	public int getCricleProgressColor() {
		return roundProgressColor;
	}

	public void setCricleProgressColor(int cricleProgressColor) {
		this.roundProgressColor = cricleProgressColor;
	}

	public int getTextColor() {
		return textColorProgress;
	}

	public void setTextColor(int textColor) {
		this.textColorProgress = textColor;
	}

	public float getTextSize() {
		return textSizeProgress;
	}

	public void setTextSize(float textSize) {
		this.textSizeProgress = textSize;
	}

	public float getRoundWidth() {
		return roundWidth;
	}

	public void setRoundWidth(float roundWidth) {
		this.roundWidth = roundWidth;
	}



}
