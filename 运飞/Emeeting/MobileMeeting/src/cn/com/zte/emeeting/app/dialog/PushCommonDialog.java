package cn.com.zte.emeeting.app.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import cn.com.zte.mobileemeeting.R;

/**
 * 公共退出dialog
 * 
 * @author liu.huanbo
 * 
 */
public class PushCommonDialog extends Dialog {
	private View view;
	/** 取消按钮 */
	private Button btn_cancel;
	/** 确定按钮 */
	private Button btn_sure;
	private Context context;

	public PushCommonDialog(Context context) {
		super(context, R.style.menu_dialog);
		this.context = context;
		view = LayoutInflater.from(context).inflate(R.layout.dialog_push_num,
				null);

		setContentView(view);

		initViews();
		setDialogposition();
		this.show();
	}

	/** 设置dialog位置 */
	private void setDialogposition() {

		WindowManager manager = ((Activity) context).getWindowManager();
		Display display = manager.getDefaultDisplay();
		WindowManager.LayoutParams lp = this.getWindow().getAttributes();
		lp.width = (int) (display.getWidth() * 0.9);
		this.getWindow().setAttributes(lp);
		this.getWindow().setGravity(Gravity.CENTER);
		this.show();
	}

	/** 初始化view */
	private void initViews() {
		btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
		btn_sure = (Button) view.findViewById(R.id.btn_sure);
	}

	/** 确定按钮点击 */
	public void sureBtnClick(final OnClick click) {
		btn_sure.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (click != null) {

					click.btnClick();
				}
			}
		});

	}

	/** 取消按钮点击 */
	public void cancelBtnClick(final OnClick click) {
		btn_cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (click != null) {

					click.btnClick();
				}
			}
		});
	}

	public interface OnClick {

		void btnClick();
	}
}
