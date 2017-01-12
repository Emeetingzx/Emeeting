/******************************************************************************
 * Copyright (C) 2014 ZTE Co.,Ltd
 * All Rights Reserved.
 * 本软件为中兴通讯股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package cn.zte.com.office.reader;

import android.graphics.Color;

/**
 * TODO 简单地描述功能. <br/>
 * 日期: 2015-6-5 下午4:18:51 <br/>
 * 
 * @author wangenzi
 * @version 1.0
 * @since JDK 1.6
 * @history 2015-6-5 wangenzi 新建
 */
public class WaterMask {
	private String waterMaskText;

	private int argbAlpha; // argb 透明度
	private int argbRed;
	private int argbGreen;
	private int argbBlue;

	public WaterMask(String waterMaskText) {
		super();
		this.waterMaskText = waterMaskText;
		// 设置默认透明度和颜色
		this.argbAlpha = 128;
		this.argbRed = 204;
		this.argbGreen = 204;
		this.argbBlue = 204;
	}

	
	/**
	 * 获取argb Color. <br/>
	 * 日期: 2015-6-5 下午4:30:04 <br/>
	 * @author wangenzi
	 * @return
	 * @since JDK 1.6
	 */
	public int argb() {
		return Color.argb(argbAlpha, argbRed, argbGreen, argbBlue);
	}

	public String getWaterMaskText() {
		return waterMaskText;
	}

	public void setWaterMaskText(String waterMaskText) {
		this.waterMaskText = waterMaskText;
	}

	public int getArgbAlpha() {
		return argbAlpha;
	}

	public void setArgbAlpha(int argbAlpha) {
		this.argbAlpha = argbAlpha;
	}

	public int getArgbRed() {
		return argbRed;
	}

	public void setArgbRed(int argbRed) {
		this.argbRed = argbRed;
	}

	public int getArgbGreen() {
		return argbGreen;
	}

	public void setArgbGreen(int argbGreen) {
		this.argbGreen = argbGreen;
	}

	public int getArgbBlue() {
		return argbBlue;
	}

	public void setArgbBlue(int argbBlue) {
		this.argbBlue = argbBlue;
	}

}
