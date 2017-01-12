package cn.com.zte.emeeting.app.dialog;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zte.emeeting.app.adapter.DialogChooseAdapter;
import cn.com.zte.emeeting.app.fragment.PhoneVideoFragment;
import cn.com.zte.emeeting.app.response.entity.MeetingInfo;
import cn.com.zte.mobileemeeting.R;

/**
 * 参会人员
 * 
 * @author liu.huanbo
 * 
 */
public class OthersDialog extends Dialog {
	/** list */
	private ListView lst_others;
	/** view */
	View view;
	/** 上下文 */
	private Context mContext;

	/** 取消按钮 */
	private TextView tvCancel;

	/** 选择类型adapter */
	DialogChooseAdapter chooseAdapter;

	private List<String> strs;

	/**
	 * 选中的选项
	 */
	private String selectString;

	public OthersDialog(Context context, List<String> listTypes) {
		super(context, R.style.dlgStyle_Transparent);
		strs = listTypes;
		mContext = context;
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		view = LayoutInflater.from(context).inflate(R.layout.dialog_others,
				null);

		setContentView(view, params);
		setDialogposition(mContext);
		// setContentView(view);
		initViews();
	}

	public OthersDialog(Context context, List<String> listTypes, String select) {
		super(context, R.style.dlgStyle_Transparent);
		this.selectString = select;
		strs = listTypes;
		mContext = context;
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		view = LayoutInflater.from(context).inflate(R.layout.dialog_others,
				null);

		setContentView(view, params);
		setDialogposition(mContext);
		// setContentView(view);
		initViews();
	}

	/** 设置dialog位置 */
	private void setDialogposition(Context context) {

		WindowManager manager = ((Activity) context).getWindowManager();
		Display display = manager.getDefaultDisplay();
		WindowManager.LayoutParams lp = this.getWindow().getAttributes();
		lp.width = (int) display.getWidth();
		this.getWindow().setAttributes(lp);
		this.getWindow().setGravity(Gravity.BOTTOM);
		this.show();
	}

	/** 初始化views */
	private void initViews() {
		lst_others = (ListView) view.findViewById(R.id.lst_others);
		tvCancel = (TextView) view.findViewById(R.id.tv_cancel);

		chooseAdapter = new DialogChooseAdapter(mContext, strs, selectString);
		lst_others.setAdapter(chooseAdapter);

	}

	/** 取消按钮点击 */
	public void onCancelButtonClick(final OnClick onClick) {
		tvCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (onClick != null) {
					onClick.onClick();
				}
			}
		});

	}

	/** 选择方法选项 */
	public void onLeaderClick(final TextView t) {

		lst_others.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				chooseAdapter.selection(arg2);
				t.setText(strs.get(arg2));
				cancelDialog();
			}

		});

	}

	/** 选择方法选项 */
	public void onLeaderClick(final TextView t, final TextView level,
			final View num, final View pwd, final View line) {

		lst_others.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				chooseAdapter.selection(arg2);
				t.setText(strs.get(arg2));

				if (strs.get(arg2).equals(mContext.getResources().getString(R.string.tv_message_detail_two_leader))) {
					level.setText(mContext.getResources().getString(R.string.tv_message_detail_a_level));
				} else if (strs.get(arg2).equals(mContext.getResources().getString(R.string.tv_message_detail_three_leader))) {
					level.setText(mContext.getResources().getString(R.string.tv_message_detail_b_level));
				} else {
					level.setText(mContext.getResources().getString(R.string.tv_message_detail_c_level));
				}
				if (strs.get(arg2).equals(mContext.getResources().getString(R.string.tv_message_detail_video_meeting))) {
					PhoneVideoFragment.isShow = false;
				} else {
					PhoneVideoFragment.isShow = true;
				}
				cancelDialog();
			}

		});

	}
	
	/** 选择方法选项 */
	public void onLeaderClick(final EditText editText,final TextView t, final TextView level,
			final View num, final View pwd, final View line) {

		lst_others.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				chooseAdapter.selection(arg2);
				t.setText(strs.get(arg2));

				if (strs.get(arg2).equals(mContext.getResources().getString(R.string.tv_message_detail_two_leader))) {
					level.setText(mContext.getResources().getString(R.string.tv_message_detail_a_level));
				} else if (strs.get(arg2).equals(mContext.getResources().getString(R.string.tv_message_detail_three_leader))) {
					level.setText(mContext.getResources().getString(R.string.tv_message_detail_b_level));
				} else {
					level.setText(mContext.getResources().getString(R.string.tv_message_detail_c_level));
				}
				if (strs.get(arg2).equals(mContext.getResources().getString(R.string.tv_message_detail_video_meeting))) {
					PhoneVideoFragment.isShow = false;
					editText.setHint(mContext.getString(R.string.tv_choose_edit));
				} else {
					editText.setHint(mContext.getString(R.string.tv_only_six_number));
					PhoneVideoFragment.isShow = true;
				}
				cancelDialog();
			}

		});

	}

	public void cancelDialog() {
		this.dismiss();
	}

	public interface OnClick {

		void onClick();
	}
}
