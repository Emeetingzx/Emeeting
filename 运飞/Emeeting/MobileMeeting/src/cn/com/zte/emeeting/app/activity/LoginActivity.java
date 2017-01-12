package cn.com.zte.emeeting.app.activity;

import roboguice.inject.InjectView;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.zte.emeeting.app.base.activity.AppActivity;
import cn.com.zte.mobileemeeting.R;

	/**
	 * 登陆界面
	 * 
	 * @author wtb
	 * */

public class LoginActivity extends AppActivity implements OnClickListener{
	private static final String TAG = LoginActivity.class.getSimpleName();
	
	private Context mContext = LoginActivity.this;
	
	/**是否显示密码*/
	private boolean isShowPassword = false;
	
	/** 账号用户名输入框 */
	@InjectView(R.id.et_login_username)
	private EditText et_login_username;
	
	/**账号用户名*/
	private String userName;
	
	/** 密码 */
	private String userPassword;
	
	/** 密码输入框 */
	@InjectView(R.id.et_login_password)
	private EditText et_login_password;
	
	/**密码框layout*/
	@InjectView(R.id.show_psd_layout)
	private RelativeLayout show_psd_layout;
	
	/**隐藏密码*/
	@InjectView(R.id.tv_hide_psd)
	private TextView tv_hide_psd;
	
	/**显示密码*/
	@InjectView(R.id.tv_show_psd)
	private TextView tv_show_psd;
	
	/**登陆*/
	@InjectView(R.id.btn_login_button)
	private Button btn_login_button;
	
	@Override
	protected void initContentView() {
		super.initContentView();
		setContentView(R.layout.acitvity_login);
	}
	
	
	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		super.initViews();
		userName = et_login_username.getText().toString().trim();
		userPassword = et_login_password.getText().toString().trim();
	}
	
	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		super.initData();
	}
	
	@Override
	protected void initViewEvents() {
		// TODO Auto-generated method stub
		super.initViewEvents();
		show_psd_layout.setOnClickListener(this);
		btn_login_button.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.show_psd_layout:
			if(isShowPassword == false){
				show_psd_layout.setBackgroundDrawable(getResourceDrawable(R.drawable.login_show_password_image_left));
				et_login_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				tv_show_psd.setVisibility(View.VISIBLE);
				tv_hide_psd.setVisibility(View.INVISIBLE);
				isShowPassword = true;
				Log.d(TAG, "isShowPassword = true --> 显示密码");
			}else{
				show_psd_layout.setBackgroundDrawable(getResourceDrawable(R.drawable.login_show_password_image_right));
				et_login_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				tv_show_psd.setVisibility(View.INVISIBLE);
				tv_hide_psd.setVisibility(View.VISIBLE);
				isShowPassword = false;
				Log.d(TAG, "isShowPassword = false --> 隐藏密码");
			}
			break;
		case R.id.btn_login_button:
			// 上传用户信息 -- 显示进度圈 -- 完毕后跳转到主页
			Intent intent = new Intent();
			intent.setClass(mContext, MainActivity.class);
			startActivity(intent);
			this.finish();
			break;
		default:
			break;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {

		if (keyCode == event.KEYCODE_BACK) {
			Dialog dialog = new AlertDialog.Builder(LoginActivity.this)
					.setMessage("确认退出？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
						
								@Override
								public void onClick(DialogInterface dialog, int which) { 
									Intent intent = new Intent(Intent.ACTION_MAIN);
									intent.addCategory(Intent.CATEGORY_HOME);
									intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									startActivity(intent);
									System.exit(0);
								}

							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
								}
							}).create();
			dialog.show();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
}
