package cn.com.zte.emeeting.app.dialog;

import cn.com.zte.mobileemeeting.R;
import android.app.Dialog;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * 正在寻找会议室的dialog
 * 
 * @author liu.huanbo
 * 
 */
public class NowFindMeetingRoomDialog extends Dialog {

	public NowFindMeetingRoomDialog(Context context) {
		super(context, R.style.shake_dialog);
		setContentView(R.layout.dialog_now_reserve);
		ImageView iv_rotate = (ImageView) findViewById(R.id.iv_rotate);
		Animation animation = AnimationUtils.loadAnimation(context,
				R.anim.dialog_find_room);
		iv_rotate.setAnimation(animation);

		this.show();

	}

}
