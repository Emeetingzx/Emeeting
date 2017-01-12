package cn.com.zte.emeeting.app.dialog;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zte.emeeting.app.adapter.AddValueServicePlaceAdapter;
import cn.com.zte.emeeting.app.adapter.DialogChooseAdapter;
import cn.com.zte.emeeting.app.base.ConfigrationList;
import cn.com.zte.emeeting.app.fragment.PhoneVideoFragment;
import cn.com.zte.emeeting.app.response.entity.AddValueRegionInfo;
import cn.com.zte.emeeting.app.util.SharedPreferenceUtil;
import cn.com.zte.mobileemeeting.R;

/**
 * 参会人员
 * 
 * @author liu.huanbo
 * 
 */
public class AddValueServicePlaceDialog extends Dialog {
	/** list */
	private ListView lst_others;
	/** view */
	View view;
	/** 上下文 */
	private Context mContext;

	/** 取消按钮 */
	private TextView tvCancel;

	/** 选择类型adapter */
	AddValueServicePlaceAdapter chooseAdapter;

	private List<AddValueRegionInfo> strs;

	/**
	 * 选中的选项
	 */
	private String selectString;

	public AddValueServicePlaceDialog(Context context,
			List<AddValueRegionInfo> listTypes) {
		super(context, R.style.dlgStyle_Transparent);
		strs = listTypes;
		mContext = context;
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		view = LayoutInflater.from(context).inflate(R.layout.dialog_others,
				null);
		setDialogposition(mContext);
		setContentView(view, params);
		initViews();
	}

	public AddValueServicePlaceDialog(Context context,
			List<AddValueRegionInfo> listTypes, String select) {
		super(context, R.style.dlgStyle_Transparent);
		this.selectString = select;
		strs = listTypes;
		mContext = context;
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		view = LayoutInflater.from(context).inflate(R.layout.dialog_others,
				null);

		setDialogposition(mContext);
		setContentView(view, params);
		initViews();
	}

	/** 设置dialog位置 */
	private void setDialogposition(Context context) {
		WindowManager manager = ((Activity) context).getWindowManager();
		Display display = manager.getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();
		display.getMetrics(dm);
		WindowManager.LayoutParams lp = this.getWindow().getAttributes();
		lp.width = dm.widthPixels;
		lp.height = dm.heightPixels;
		this.getWindow().setAttributes(lp);
		this.getWindow().setGravity(Gravity.BOTTOM);
	}

	/** 初始化views */
	private void initViews() {
		lst_others = (ListView) view.findViewById(R.id.lst_others);
		tvCancel = (TextView) view.findViewById(R.id.tv_cancel);

		chooseAdapter = new AddValueServicePlaceAdapter(mContext, strs,
				selectString);
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
				new SharedPreferenceUtil(ConfigrationList.PLACEINFO, mContext).setNameAndValue(ConfigrationList.PLACEID, strs.get(arg2).getID());
				chooseAdapter.selection(arg2);
				t.setText(strs.get(arg2).getAVSRN());
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
				t.setText(strs.get(arg2).getAVSRN());

				if (strs.get(arg2).equals(mContext.getResources().getString(R.string.tv_message_detail_two_leader))) {
					level.setText(mContext.getResources().getString(R.string.tv_message_detail_a_level));
				} else if (strs.get(arg2).equals(mContext.getResources().getString(R.string.tv_message_detail_three_leader))) {
					level.setText(mContext.getResources().getString(R.string.tv_message_detail_b_level));
				} else {
					level.setText(mContext.getResources().getString(R.string.tv_message_detail_c_level));
				}
				if (strs.get(arg2).equals(mContext.getResources().getString(R.string.tv_message_detail_video_meeting))) {
					// num.setVisibility(View.GONE);
					// pwd.setVisibility(View.GONE);
					// line.setVisibility(View.GONE);
					PhoneVideoFragment.isShow = false;
				} else {
					// num.setVisibility(View.VISIBLE);
					// pwd.setVisibility(View.VISIBLE);
					// line.setVisibility(View.VISIBLE);
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
