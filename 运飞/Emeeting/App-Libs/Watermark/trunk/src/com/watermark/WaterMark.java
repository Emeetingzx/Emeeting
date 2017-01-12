/******************************************************************************
 * Copyright (C) 2014 ZTE Co.,Ltd
 * All Rights Reserved.
 * 本软件为中兴通讯股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package com.watermark;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * TODO 简单地描述功能. <br/>
 * 日期: 2015-3-5 下午3:53:07 <br/>
 * 
 * @author Administrator
 * @version 1.0
 * @since JDK 1.6
 * @history 2015-3-5 Administrator 新建
 */
public class WaterMark extends FrameLayout {

	private WaterMarkUtils waterMarkUtils;
	private String mark;
	private Bitmap createWaterMarkBitmap;
	private ImageView iv;
	private Context context;


	public WaterMark(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
		this.context = context;
		initWaterMarkUtils(context);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WaterMark);
		mark = (String) a.getText(R.styleable.WaterMark_text);
		float textSize = a.getDimension(R.styleable.WaterMark_textSize, 16);
		int color = a.getColor(R.styleable.WaterMark_textColor, Color.rgb(94, 182, 195));
		int direction = a.getInteger(R.styleable.WaterMark_direction, -45);
		waterMarkUtils.setTextDirection(direction);
		waterMarkUtils.setTextSize(textSize);
		waterMarkUtils.setTextColor(color);
		
		setImageViewBackGroud(context);
		
		a.recycle();
	}

	private void setImageViewBackGroud(Context context) {
		removeView(iv);
		createWaterMarkBitmap = waterMarkUtils.createWaterMarkBitmap(mark);
		BitmapDrawable drawable = new BitmapDrawable(createWaterMarkBitmap);
		drawable.setTileModeXY(TileMode.REPEAT , TileMode.REPEAT );
		drawable.setDither(true);
		
		iv = new ImageView(context);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		iv.setLayoutParams(lp);
		iv.setBackgroundDrawable(drawable);
		addView(iv);
	}
	

	private void initWaterMarkUtils(Context context) {
		waterMarkUtils = new WaterMarkUtils(context);
		
	}
	
	public void setWaterMarkText(String text){
		waterMarkUtils.setText(text);
		setImageViewBackGroud(context);
	}

	public void setWaterMarkTextSize(float size) {
		waterMarkUtils.setTextSize(size);
		setImageViewBackGroud(context);
	}
	
	public void setWaterMarkTextColor(int textColor) {
		waterMarkUtils.setTextColor(textColor);
		setImageViewBackGroud(context);
	}
	
	public void setWaterMarkTextDirection(int direction) {
		waterMarkUtils.setTextDirection(direction);
		setImageViewBackGroud(context);
	}

}
