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
import android.widget.TextView;
import cn.com.zte.mobileemeeting.R;

/**
 * 预订dialog
 * 
 * @author liu.huanbo
 * 
 */

public class ReserveDialog extends Dialog {
	/** 确定按钮 */
	private Button sureBtn;
	/** 取消按钮 */
	private Button cancleBtn;
	/** 内容 */
	private TextView contentTv;
	/**当前view*/
	private View view;

	public ReserveDialog(Context context) {
		super(context, R.style.menu_dialog);
		view = LayoutInflater.from(context).inflate(R.layout.dialog_reserve,
				null);
		setContentView(view);
		setDialogposition(context);
		initViews();
		this.show();
	}

	/** 设置dialog位置 */
	private void setDialogposition(Context context) {

		WindowManager manager = ((Activity) context).getWindowManager();
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
		contentTv = (TextView) view.findViewById(R.id.tv_content);
	}

	/**确定(继续预定) */
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

	/** 取消按钮 (会议室详情)*/
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

	/** 设置内容 */
	public void setContentText(String tv) {
		contentTv.setText(tv);
	}

	/** 设置内容 */
	public void setSureBtnText(String tv) {
		sureBtn.setText(tv);
	}

	/** 设置内容 */
	public void setCancelBtnText(String tv) {
		cancleBtn.setText(tv);
	}

	public interface OnClick {

		void onclick();
	}
}
