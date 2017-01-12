package cn.com.zte.android.widget.lockpattern.view;

import cn.com.zte.android.widget.pattern.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * 退出当前账号的dialog
 * 
 * @author liu.huanbo
 * 
 */
public class ExitDialog extends Dialog {

	/** 标题 */
	private TextView dialog_tv_title;
	/** 内容 */
	private TextView dialog_tv_content;
	/** 确定按钮 */
	private Button dialog_btn_sure;
	/** 取消按钮 */
	private Button dialog_btn_cancel;
	/** dialog按钮的数目 */
	private int theme;
	/** 按钮之间的割线 */
	private TextView dialog_line;

	public ExitDialog(Context context, int i) {
		super(context, R.style.menu_dialog);
		this.theme = i;
		setContentView(R.layout.dialog_exit);
		getWindow().setBackgroundDrawable(new BitmapDrawable());
		initViews();
		WindowManager manager = ((Activity) context).getWindowManager();
		Display display = manager.getDefaultDisplay();
		WindowManager.LayoutParams lp = this.getWindow().getAttributes();
		lp.height = (int) (display.getHeight() * 0.6); // 高度设置为屏幕的0.6
		lp.width = (int) (display.getWidth() * 0.95); // 宽度设置为屏幕的0.95

		lp.y = (int) (display.getHeight() * 0.15);
		this.getWindow().setAttributes(lp);
	}

	/** 初始化控件 */
	private void initViews() {
		dialog_tv_title = (TextView) findViewById(R.id.dialog_tv_title);
		dialog_tv_content = (TextView) findViewById(R.id.dialog_tv_content);
		dialog_btn_sure = (Button) findViewById(R.id.dialog_btn_sure);
		dialog_btn_cancel = (Button) findViewById(R.id.dialog_btn_cancel);
		dialog_line = (TextView) findViewById(R.id.dialog_line);
		if (theme == 1) {
			dialog_btn_cancel.setVisibility(View.GONE);
			dialog_line.setVisibility(View.GONE);
		}

	}

	public interface BtnOnclick {

		void btnClick(View view);
	}

	/** 设置确定按钮的点击监听 */
	public void setSureBtnClicklistener(final BtnOnclick btnOnclick) {
		dialog_btn_sure.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (btnOnclick != null) {
					btnOnclick.btnClick(v);
				}
			}
		});
	}

	/** 设置取消按钮的点击监听 */
	public void setCancelBtnClicklistener(final BtnOnclick onclick) {

		dialog_btn_cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (onclick != null) {
					onclick.btnClick(v);

				}

			}
		});

	}

	/** 设置标题的内容 */
	public void setTitleText(String str) {
		dialog_tv_title.setText(str);

	}

	/** 设置标题的字体大小 */
	public void setTitleTextSize(float size) {
		dialog_tv_title.setTextSize(size);

	}

	/** 设置内容的内容 */
	public void setContentText(String str) {
		dialog_tv_content.setText(str);
	}

	/** 设置内容的字体大小 */
	public void setContentTextSize(float size) {
		dialog_tv_content.setTextSize(size);
	}

	/** 设置确定按钮的字体内容 */
	public void setSureBtnText(String str) {

		dialog_btn_sure.setText(str);
	}

	/** 设置确定按钮的字体颜色 */
	public void setSureBtnTextColor(int color) {

		dialog_btn_sure.setTextColor(color);

	}

	/** 设置确定按钮的字体大小 */
	public void setSureBtnTextSize(float size) {
		dialog_btn_sure.setTextSize(size);

	}

	/** 设置取消按钮的字体内容 */
	public void setCancelBtnText(String str) {

		dialog_btn_cancel.setText(str);
	}

	/** 设置取消按钮的字体颜色 */
	public void setCancelBtnTextColor(int color) {

		dialog_btn_cancel.setTextColor(color);

	}

	/** 设置取消按钮的字体大小 */
	public void setCancelBtnTextSize(float size) {
		dialog_btn_cancel.setTextSize(size);
	}

}
