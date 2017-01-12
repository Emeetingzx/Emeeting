package cn.com.zte.emeeting.app.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import cn.com.zte.mobileemeeting.R;

/**
 * 版本更新的dialog
 * 
 * @author liu.huanbo
 * 
 */
public class VersionUpdateDialog extends Dialog {

	/** 确定按钮 */
	private Button sureBtn;
	/** 取消按钮 */
	private Button cancleBtn;
	private Context mContext;
	// /** 内容 */
	// private TextView contentTv;
	/** 当前view */
	private View view;

	public VersionUpdateDialog(Context context) {
		super(context, R.style.menu_dialog);
		mContext = context;
		view = LayoutInflater.from(context).inflate(
				R.layout.dialog_version_update, null);
		setContentView(view);
		setDialogposition();
		initViews();
		this.show();
	}

	/** 设置dialog位置 */
	private void setDialogposition() {

		WindowManager manager = ((Activity) mContext).getWindowManager();
		Display display = manager.getDefaultDisplay();
		WindowManager.LayoutParams lp = this.getWindow().getAttributes();
		lp.width = (int) (display.getWidth() * 0.9);
		this.getWindow().setAttributes(lp);
		this.getWindow().setGravity(Gravity.CENTER);
		this.show();
	}

	private void initViews() {
		sureBtn = (Button) view.findViewById(R.id.btn_sure);
		cancleBtn = (Button) view.findViewById(R.id.btn_cancel);
		// contentTv = (TextView) view.findViewById(R.id.tv_content);
	}

	/** 确定按钮 */
	public void sureBtnClick(final OnClick onClick) {
		sureBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (onClick != null) {
					onClick.onclick();
				}
			}
		});

	}

	/** 去取消按钮 */
	public void cancelBtnClick(final OnClick onClick) {
		cancleBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (onClick != null) {
					onClick.onclick();
				}
			}
		});
	}

	public interface OnClick {

		void onclick();
	}

	/***
	 * 获取当前应用的版本号：
	 * 
	 * @throws NameNotFoundException
	 */
	private String getVersionName() {
		String version;
		try {
			// 获取packagemanager的实例
			PackageManager packageManager = mContext.getPackageManager();
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			PackageInfo packInfo = packageManager.getPackageInfo(
					mContext.getPackageName(), 0);
			version = packInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			version = "";
		}
		return version;
	}
}
