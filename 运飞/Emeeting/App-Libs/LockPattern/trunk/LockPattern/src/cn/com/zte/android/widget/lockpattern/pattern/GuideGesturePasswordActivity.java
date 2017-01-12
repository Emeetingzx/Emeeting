/******************************************************************************
 * Copyright (C) 2015 ZTE Co.,Ltd
 * All Rights Reserved.
 * 本软件为中兴通讯股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package cn.com.zte.android.widget.lockpattern.pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import cn.com.zte.android.widget.lockpattern.view.LockPatternUtils;
import cn.com.zte.android.widget.pattern.R;

/**
 * Guide. <br/>
 * 日期: 2015-2-9 下午4:37:04 <br/>
 * 
 * @author wangenzi
 * @version 1.0
 * @since JDK 1.6
 * @history 2015-2-9 wangenzi 新建
 */
public class GuideGesturePasswordActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gesturepassword_guide);
		findViewById(R.id.gesturepwd_guide_btn).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						LockPatternUtils.getInstance(getApplicationContext())
								.clearLock();
						Intent intent = new Intent(
								GuideGesturePasswordActivity.this,
								CreateGesturePasswordActivity.class);
						// 打开新的Activity
						startActivity(intent);
						finish();
					}
				});
	}

}
