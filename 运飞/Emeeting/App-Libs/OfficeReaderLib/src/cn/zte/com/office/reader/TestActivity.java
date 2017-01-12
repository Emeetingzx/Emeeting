/******************************************************************************
 * Copyright (C) 2014 ZTE Co.,Ltd
 * All Rights Reserved.
 * 本软件为中兴通讯股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package cn.zte.com.office.reader;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import cn.zom.zte.office.reader.R;

/**
 * TestActivity. <br/>
 * 日期: 2015-6-5 下午4:11:43 <br/>
 * 
 * @author wangenzi
 * @version 1.0
 * @since JDK 1.6
 * @history 2015-6-5 wangenzi 新建
 */
public class TestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		findViewById(R.id.btn_open).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				WaterMask waterMask = new WaterMask("李明10188888");
				OfficeUtil.openFileWithWps(TestActivity.this,
						"/sdcard/download/1.docx", waterMask);
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

}
