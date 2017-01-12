package cn.com.zte.emeeting.app.dialog;

import cn.com.zte.mobileemeeting.R;
import android.app.Dialog;
import android.content.Context;

public class DialogLoadBar extends Dialog{

	public DialogLoadBar(Context context) {
		super(context,cn.com.zte.android.resource.R.style.ThemeDialog);
		// TODO Auto-generated constructor stub
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.dialog_loading_image);
		setCancelable(false);
	}
	
	

}
