package cn.com.zte.emeeting.app.activity;

import android.content.Context;
import cn.com.zte.emeeting.app.base.activity.AppActivity;
import cn.com.zte.mobileemeeting.R;

public class PushMessageActivity extends AppActivity{
	private static final String TAG = PushMessageActivity.class.getSimpleName();
	private Context context;
	
	@Override
	protected void initContentView() {
		// TODO Auto-generated method stub
		super.initContentView();
		setContentView(R.layout.activity_push_message);
	}
	

}
