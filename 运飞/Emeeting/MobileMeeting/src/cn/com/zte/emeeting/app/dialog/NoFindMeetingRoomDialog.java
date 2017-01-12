package cn.com.zte.emeeting.app.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cn.com.zte.mobileemeeting.R;
/**
 * 未找到会议室的dialog
 * @author liu.huanbo
 *
 */
public class NoFindMeetingRoomDialog extends Dialog {
	/** 当前view */
	private View view;
	/** 未找到会议室所显示的图片 */
	private ImageView iv_choose;

	private LinearLayout linearLayout;
	
	public NoFindMeetingRoomDialog(Context context) {
		super(context, R.style.shake_dialog);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		view = LayoutInflater.from(context).inflate(
				R.layout.dialog_no_find_meeeting_room, null);
		setContentView(view, params);
		initViews();
		setDialogposition(context);

	}

	/** 初始化view */
	private void initViews() {
		// TODO Auto-generated method stub
		iv_choose = (ImageView) view.findViewById(R.id.iv_choose);
		linearLayout=(LinearLayout) view.findViewById(R.id.l);
	}

	/** 设置dialog位置 */
	private void setDialogposition(Context context) {

		WindowManager manager = ((Activity) context).getWindowManager();
		Display display = manager.getDefaultDisplay();
		WindowManager.LayoutParams lp = this.getWindow().getAttributes();
		lp.width = (int) display.getWidth();
		this.getWindow().setAttributes(lp);
		this.getWindow().setGravity(Gravity.TOP);
	}

	/** 图标点击事件 */
	public void IvClick(final OnClick onClick) {
		linearLayout.setOnClickListener(new View.OnClickListener() {

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
}
