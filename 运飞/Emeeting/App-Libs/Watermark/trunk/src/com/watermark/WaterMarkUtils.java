
/******************************************************************************
 * Copyright (C) 2014 ZTE Co.,Ltd
 * All Rights Reserved.
 * 本软件为中兴通讯股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package com.watermark;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * WaterMarkUtils. <br/>
 * 日期: 2015-3-5 上午9:35:27 <br/>
 * @author Administrator
 * @version 1.0
 * @since JDK 1.6
 * @history 2015-3-5 Administrator 新建
 */
public class WaterMarkUtils {
	
	private Context context;

	private  float textSize = 16;
	private  int textColor = Color.rgb(94, 182, 195);
	private  Drawable back;
	private int direction = -45;

	private Bitmap waterMarkBitmap;

	private String text;
	
	public WaterMarkUtils(Context context) {
		this.context=  context;
	}
	

	public void setTextDirection(int direction) {
		this.direction = direction;
	}

	
	public  void setTextSize(float size){
		this.textSize = size;
	}
	
	public  void setTextColor(int color){
		this.textColor = color;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public  Bitmap createWaterMarkBitmap(String s){
		Bitmap src = Bitmap.createBitmap(120, 120, Config.ARGB_8888);
		if(text==null||"".equals(text)){
			text = s;
		}
		waterMarkBitmap = createBitmap(src,text,textColor,textSize);
		return waterMarkBitmap;
	}
	
	private  Bitmap createBitmap(Bitmap src, String str,int color,float textSize) {
		int w = src.getWidth();
		int h = src.getHeight();
		Bitmap bmpTemp = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		Canvas canvas = new Canvas(bmpTemp);
		Paint p = new Paint();
		
		p.setColor(color);
		p.setTextSize(textSize);
		canvas.translate(10, 0);
		canvas.rotate(direction, 60, 60);
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.drawBitmap(src, 0, 0, p);
		canvas.drawText(str, 0, 60, p);
		canvas.restore();
		return bmpTemp;
	}

	

}
