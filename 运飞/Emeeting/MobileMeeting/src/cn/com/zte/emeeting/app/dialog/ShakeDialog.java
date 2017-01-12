package cn.com.zte.emeeting.app.dialog;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.zte.android.common.util.SharedPreferencesUtil;
import cn.com.zte.emeeting.app.activity.ShakeActivity;
import cn.com.zte.emeeting.app.adapter.DialogGdvPlaceAdapter;
import cn.com.zte.emeeting.app.adapter.GdvDurationAdapter;
import cn.com.zte.emeeting.app.database.entity.shared.DBMeetingRoomAddress;
import cn.com.zte.mobileemeeting.R;

/**
 * 摇一摇公共dialog
 * 
 * @author liu.huanbo
 * 
 */
public class ShakeDialog extends Dialog {
	/** 上下文 */
	private Context mContext;
	/** 取消按钮 */
	private Button btnCancel;
	/** 取消按钮 */
	private Button btnSure;
	/** 会议地点 */
	private GridView gdvPlace;
	/** 会议时长 */
	private GridView gdvDuration;
	/** 时长数据源 */
	private List<String> listDuration;
	/** 地点数据源 */
	private List<DBMeetingRoomAddress> listPlace;
	/** 会议地点adapter */
	DialogGdvPlaceAdapter gdvPlaceAdapter;
	/** 会议时长adapter */
	GdvDurationAdapter durationAdapter;
	private TextView shake_tv_place;
	private ImageView iv;
	private int hasSelectTime = -1;
	private int hasSelectAdressId = -1;

	public ShakeDialog(Context context, List<DBMeetingRoomAddress> listPlace,
			List<String> listDuration, int hasSelectPositionPlace,
			int hasSelectPositionTime) {
		super(context, R.style.menu_dialog);
		mContext = context;
		hasSelectTime = hasSelectPositionTime;
		hasSelectAdressId = hasSelectPositionPlace;
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.dialog_shake, null);
		shake_tv_place = (TextView) view.findViewById(R.id.shake_tv_place);
		iv = (ImageView) view.findViewById(R.id.iv);

		this.listPlace = listPlace;
		this.listDuration = listDuration;

		if (this.listPlace == null || this.listPlace.size() == 0) {

			this.listPlace = new ArrayList<DBMeetingRoomAddress>();
		}

		shake_tv_place.setVisibility(View.GONE);
		iv.setVisibility(View.GONE);


		setContentView(view, params);
		setDialogposition(mContext);
		initDatas();
		initViews();
	}

	/** 设置dialog位置 */
	private void setDialogposition(Context context) {

		WindowManager manager = ((Activity) context).getWindowManager();
		Display display = manager.getDefaultDisplay();
		WindowManager.LayoutParams lp = this.getWindow().getAttributes();
		lp.width = (int) display.getWidth();
		lp.height = (int) display.getHeight();
		this.getWindow().setAttributes(lp);
		this.getWindow().setGravity(Gravity.FILL);
		this.getWindow().setWindowAnimations(R.style.toptodownAnimation);
	}

	/** 初始化views */
	private void initViews() {

		btnCancel = (Button) findViewById(R.id.btn_cancels);
		btnSure = (Button) findViewById(R.id.btn_sure);
		gdvPlace = (GridView) findViewById(R.id.gdvPlace);
		gdvDuration = (GridView) findViewById(R.id.gdvDuration);
		durationAdapter = new GdvDurationAdapter(mContext, listDuration);
		durationAdapter.setSelection(hasSelectTime);
		gdvPlaceAdapter = new DialogGdvPlaceAdapter(mContext, listPlace);
		gdvPlaceAdapter.setSelection(hasSelectAdressId);
		gdvDuration.setAdapter(durationAdapter);
		gdvPlace.setAdapter(gdvPlaceAdapter);
		// gdvPlaceAdapter = new GdvPlaceAdapter(mContext, listPlace,
		// new ItemTmpListener() {
		//
		// @Override
		// public void onItemClicked(int position, String item) {
		// // TODO Auto-generated method stub
		// gdvPlaceAdapter.setSelection(position);
		// gdvPlaceAdapter.getClickTemp();
		// }
		// });

	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN) {
			btnCancel.performClick();
		}
		return super.onKeyDown(keyCode, event);
	}

	/** 初始化数据 */
	private void initDatas() {
	}

	/** 按钮被点击接口 */
	public interface BtnClick {

		void onBtnClick();
	}

	/** 确定按钮点击 */
	public void setBtnSureClick(final BtnClick btnClick) {

		btnSure.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (btnClick != null) {
					btnClick.onBtnClick();
				}
			}
		});

	}

	/** 取消按钮点击 */
	public void setBtnCancelClick(final BtnClick btnClick) {

		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (btnClick != null) {
					btnClick.onBtnClick();
				}
			}
		});

	}

	/** 会议地点 */
	public int setGdvPlaceClick() {

		gdvPlace.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				gdvPlaceAdapter.setSelection(arg2);

			}
		});
		return gdvPlaceAdapter.getClickTemp();

	}

	/** 会议时长 */
	public int setGdvDurationClick() {

		gdvDuration.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				durationAdapter.setSelection(arg2);
			}
		});
		return durationAdapter.getClickTemp();
	}

}
