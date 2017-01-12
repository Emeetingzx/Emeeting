package cn.com.zte.emeeting.app.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import roboguice.inject.InjectView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import cn.com.zte.android.app.fragment.BaseFragment;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.android.resource.inflater.BaseLayoutInflater;
import cn.com.zte.android.widget.dialog.DialogManager;
import cn.com.zte.emeeting.app.appservice.ValueAddBookService;
import cn.com.zte.emeeting.app.base.ConfigrationList;
import cn.com.zte.emeeting.app.dialog.AddValueServicePlaceDialog;
import cn.com.zte.emeeting.app.dialog.AddValueServicePlaceDialog.OnClick;
import cn.com.zte.emeeting.app.dialog.DialogBotTimeChoose;
import cn.com.zte.emeeting.app.dialog.DialogServiceBookSuccess;
import cn.com.zte.emeeting.app.dialog.DialogTopTimeChoose;
import cn.com.zte.emeeting.app.dialog.DlgBookSuccess;
import cn.com.zte.emeeting.app.dialog.OthersDialog;
import cn.com.zte.emeeting.app.dialog.DialogServiceBookSuccess.OnBookSuccessDlgListener;
import cn.com.zte.emeeting.app.response.entity.AddValueRegionInfo;
import cn.com.zte.emeeting.app.response.entity.FoodAndRefreshmentsInfo;
import cn.com.zte.emeeting.app.response.instrument.GetAddValueServiceRegionInfosResponse;
import cn.com.zte.emeeting.app.response.instrument.GetFoodAndRefreshmentsInfosResponse;
import cn.com.zte.emeeting.app.response.instrument.GetReserveAddValueServiceResponse;
import cn.com.zte.emeeting.app.util.BroadcastUtil;
import cn.com.zte.emeeting.app.util.DateFormatUtil;
import cn.com.zte.emeeting.app.util.DensityUtil;
import cn.com.zte.emeeting.app.util.EmeetingToast;
import cn.com.zte.emeeting.app.util.SharedPreferenceUtil;
import cn.com.zte.emeeting.app.views.TimeChooseBotView.onClickEventListener;
import cn.com.zte.emeeting.app.views.ViewServiceFoodLayout;
import cn.com.zte.mobilebasedata.entity.PageInput;
import cn.com.zte.mobileemeeting.R;

/**
 * 增值服务预定视图
 * 
 * @author LangK
 */
public class ValueAddBookFragment extends BaseFragment {

	private Context mContext;

	/**
	 * 服务内容布局
	 */
	@InjectView(R.id.service_book_foodlayout)
	private LinearLayout foodLayout;
	/**
	 * 联系电话
	 */
	@InjectView(R.id.service_book_phone_text)
	private EditText phoneEditText;
	/**
	 * 详细地址信息
	 */
	@InjectView(R.id.service_book_addr_text)
	private EditText addrEditText;
	/**
	 * 时间选择
	 */
	@InjectView(R.id.service_book_time_text)
	private TextView timeView;
	/**
	 * 区域选择
	 */
	@InjectView(R.id.service_book_place_text)
	private TextView placeView;

	/**
	 * 提交按钮
	 */
	@InjectView(R.id.service_book_submit)
	private Button submitButton;

	/**
	 * 数据源
	 */
	private List<FoodAndRefreshmentsInfo> list = new ArrayList<FoodAndRefreshmentsInfo>();
	/**
	 * 选中的食品
	 */
	private List<String> selectIdList = new ArrayList<String>();

	/**
	 * 区域选择的数据集合
	 */
	private List<AddValueRegionInfo> placeList = new ArrayList<AddValueRegionInfo>();

	/**
	 * 分页对象
	 */
	private PageInput pageInput;
	/**
	 * 页大小
	 */
	private static final int PSIZE = 50;

	private ValueAddBookService bookService;

	@Override
	protected View onCreateView(BaseLayoutInflater arg0, ViewGroup arg1,
			Bundle arg2) {
		// TODO Auto-generated method stub
		mContext = getActivity();
		bookService = new ValueAddBookService(mContext);
		return arg0.inflate(R.layout.fragment_service_book);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		super.initData();
		submitButton.setEnabled(false);
		reCheck();
	}

	/**
	 * 日期格式化
	 */
	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private Calendar calendar = Calendar.getInstance();

	/**
	 * 刷新界面 重新赋值服务时间 检查服务项和服务地址非空
	 */
	public void reCheck() {
		// TODO Auto-generated method stub
		Date date = DateFormatUtil.getServerTime(mContext);
		calendar.setTime(date);
		if (calendar.get(Calendar.HOUR_OF_DAY) > 20
				|| calendar.get(Calendar.HOUR_OF_DAY) < 8) {
			// EmeetingToast.show(mContext, "抱歉，当前时间不再服务时间内");
		} else {
			int minute = calendar.get(Calendar.MINUTE);
			if (minute == 0) {
				calendar.set(Calendar.MINUTE, minute);
			} else {
				calendar.add(Calendar.HOUR_OF_DAY, 1);
				calendar.set(Calendar.MINUTE, 0);
			}
			date = calendar.getTime();
			String dateString = format.format(date);
			timeView.setText(dateString);
		}
		initList();
	}

	@Override
	protected void initViewEvents() {
		// TODO Auto-generated method stub
		super.initViewEvents();
		placeView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				chooseDialog(placeList, placeView, placeView.getText()
						.toString());
			}
		});
		timeView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final DialogBotTimeChoose dialog = new DialogBotTimeChoose(
						mContext);
				dialog.setCurrentDateAndTime(timeView.getText().toString());
				dialog.setClickEventListener(new onClickEventListener() {

					@Override
					public void onSure(String time) {
						// TODO Auto-generated method stub
						if (time.equals("0") || time.equals("-1")) {
							Log.d(this.getClass().getSimpleName(), time);
							EmeetingToast.show(mContext,
									R.string.service_time_error);
							dialog.dismiss();
						} else {
							try {
								SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
								Date date= format.parse(time);
								Calendar calendar = Calendar.getInstance();
								calendar.setTime(date);
								Calendar currentCalendar = Calendar.getInstance();
								currentCalendar.setTime(DateFormatUtil.getServerTime(mContext));
								if (calendar.before(currentCalendar)) {
									EmeetingToast.show(mContext, R.string.service_time_before);
									return;
								}
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}

							timeView.setText(time);
							dialog.dismiss();
						}
					}

					@Override
					public void noTime() {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				dialog.show();
			}
		});
		submitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (timeView.getText().equals("")) {
					// DialogManager.showToast(mContext, "时间选择不能为空");
					EmeetingToast.show(mContext, R.string.service_time_empty);
					return;
				}
				if (addrEditText.getText().toString().equals("")) {
					// DialogManager.showToast(mContext, "会议地点不能为空");
					EmeetingToast
							.show(mContext, R.string.service_address_empty);
					return;
				}

				if (placeView.getText().equals("")) {
					// DialogManager.showToast(mContext, "会议地点不能为空");
					EmeetingToast.show(mContext, R.string.service_place_empty);
					return;
				}

				if (phoneEditText.getText().length() != 11) {
					// DialogManager.showToast(mContext, "请输入手机号");
					EmeetingToast.show(mContext,
							R.string.service_phone_length_error);
					return;
				}
				Pattern p = Pattern.compile("^(1)\\d{10}$");

				Matcher m = p.matcher(phoneEditText.getText().toString());
				if (!m.matches()) {
					// DialogManager.showToast(mContext, "手机格式有误");
					EmeetingToast.show(mContext, R.string.service_phone_error);
					return;
				}

				if (selectIdList == null || selectIdList.size() == 0) {
					EmeetingToast
							.show(mContext, R.string.service_service_empty);
					return;
				}

				StringBuilder sBuilder = new StringBuilder();
				for (int i = 0; i < selectIdList.size(); i++) {
					if (i + 1 == selectIdList.size()) {
						sBuilder.append(selectIdList.get(i));
					} else {
						sBuilder.append(selectIdList.get(i)).append(",");
					}
				}
				String placeId = null;
				for (int j = 0; j < placeList.size(); j++) {
					if (placeList.get(j).getAVSRN()
							.equals(placeView.getText().toString())) {
						placeId = placeList.get(j).getID();
						break;
					}
				}

				String timeString = timeView.getText().toString();
				timeString = timeString + ":00";
				bookService.bookValueAddService(handler, timeString, placeId,
						phoneEditText.getText().toString(), addrEditText
								.getText().toString(), sBuilder.toString());
			}
		});
	}

	/**
	 * 初始化数据源
	 */
	private void initList() {
		if (placeList == null || placeList.size() == 0) {
			bookService.getAddValueRegion(placeHandler);
		}
		if (list == null || list.size() == 0) {
			pageInput = new PageInput();
			pageInput.setPNO(1 + "");
			pageInput.setPSIZE("" + PSIZE);
			bookService.getFoodAndRefreshments(foodHandler, pageInput);
		}
	}

	/**
	 * 初始化预定值
	 */
	private void initBookValue() {
		// TODO Auto-generated method stub
		reCheck();
		addrEditText.setText("");
		phoneEditText.setText("");
		selectIdList.clear();
		foodLayout.removeAllViews();
		initFoodLayout();
		if (placeList != null && placeList.size() > 0) {
			String idString = new SharedPreferenceUtil(
					ConfigrationList.PLACEINFO, mContext)
					.getString(ConfigrationList.PLACEID);
			if (idString == null || idString.equals("")) {
				placeView.setText(placeList.get(0).getAVSRN());
			} else {
				for (int i = 0; i < placeList.size(); i++) {
					if (idString.equals(placeList.get(i).getID())) {
						placeView.setText(placeList.get(i).getAVSRN());
					}
				}
				if (placeView.getText().toString().equals("")) {
					placeView.setText(placeList.get(0).getAVSRN());
				}
			}
		}
	}

	/**
	 * 初始化食品内容布局
	 */
	private void initFoodLayout() {
		ViewServiceFoodLayout layout = null;
		for (int i = 0; i < list.size(); i++) {
			if (i % 3 == 0) {
				layout = new ViewServiceFoodLayout(mContext, selectIdList);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				params.topMargin = DensityUtil.dip2px(mContext, 15);
				foodLayout.addView(layout, params);
			}
			layout.addFoodEntity(list.get(i));
		}
	}

	/** 选择的dialog */
	private void chooseDialog(List<AddValueRegionInfo> str, TextView tv,
			String select) {
		final AddValueServicePlaceDialog othersDialogLeader = new AddValueServicePlaceDialog(
				mContext, str, select);
		othersDialogLeader.onCancelButtonClick(new OnClick() {
			@Override
			public void onClick() {
				othersDialogLeader.dismiss();
			}
		});

		othersDialogLeader.onLeaderClick(tv);
		othersDialogLeader.show();
	}

	BaseAsyncHttpResponseHandler<GetReserveAddValueServiceResponse> handler = new BaseAsyncHttpResponseHandler<GetReserveAddValueServiceResponse>(
			true) {
		public void onSuccessTrans(
				GetReserveAddValueServiceResponse responseModelVO) {
			super.onSuccessTrans(responseModelVO);
			DialogServiceBookSuccess dlg = new DialogServiceBookSuccess(
					mContext, null, new OnBookSuccessDlgListener() {

						@Override
						public void onRightClicked() {
							initBookValue();
							BroadcastUtil.sendBroadcase(mContext,
									BroadcastUtil.AVServiceMySelfNotifi);
							((ValueAddServiceFragment) getParentFragment())
									.setViewPagerItem(1);
						}

						@Override
						public void onLeftClicked() {
							initBookValue();
							BroadcastUtil.sendBroadcase(mContext,
									BroadcastUtil.AVServiceMySelfNotifi);
						}
					});
			dlg.show();
		};

		public void onFailureTrans(
				GetReserveAddValueServiceResponse responseModelVO) {
			super.onFailureTrans(responseModelVO);
			if (responseModelVO != null && responseModelVO.getM() != null) {
				EmeetingToast.show(mContext, responseModelVO.getM());
			}
		};

		/**
		 * 网络异常
		 */
		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			DialogManager.showToast(mContext, R.string.respones_error);
		};
	};
	BaseAsyncHttpResponseHandler<GetFoodAndRefreshmentsInfosResponse> foodHandler = new BaseAsyncHttpResponseHandler<GetFoodAndRefreshmentsInfosResponse>() {
		public void onSuccessTrans(
				GetFoodAndRefreshmentsInfosResponse responseModelVO) {
			super.onSuccessTrans(responseModelVO);
			if (responseModelVO != null && responseModelVO.getD() != null) {
				submitButton.setEnabled(true);
				list = responseModelVO.getD();
				initFoodLayout();
			} else {
				DialogManager.showToast(mContext,
						R.string.service_book_food_fail);
			}
		};

		public void onFailureTrans(
				GetFoodAndRefreshmentsInfosResponse responseModelVO) {
			super.onFailureTrans(responseModelVO);
			if (responseModelVO != null && responseModelVO.getM() != null) {
				DialogManager.showToast(mContext, responseModelVO.getM());
			}
		};

		/**
		 * 网络异常
		 */
		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			DialogManager.showToast(mContext, R.string.service_book_food_fail);
		};
	};

	BaseAsyncHttpResponseHandler<GetAddValueServiceRegionInfosResponse> placeHandler = new BaseAsyncHttpResponseHandler<GetAddValueServiceRegionInfosResponse>() {
		public void onSuccessTrans(
				GetAddValueServiceRegionInfosResponse responseModelVO) {
			super.onSuccessTrans(responseModelVO);
			if (responseModelVO != null && responseModelVO.getD() != null) {
				placeList = responseModelVO.getD();
				if (placeList != null && placeList.size() > 0) {
					String idString = new SharedPreferenceUtil(
							ConfigrationList.PLACEINFO, mContext)
							.getString(ConfigrationList.PLACEID);
					if (idString == null || idString.equals("")) {
						placeView.setText(placeList.get(0).getAVSRN());
					} else {
						for (int i = 0; i < placeList.size(); i++) {
							if (idString.equals(placeList.get(i).getID())) {
								placeView.setText(placeList.get(i).getAVSRN());
							}
						}
						if (placeView.getText().toString().equals("")) {
							placeView.setText(placeList.get(0).getAVSRN());
						}
					}
				}
			} else {
				// DialogManager.showToast(mContext,
				// R.string.service_book_place_fail);
				EmeetingToast.show(mContext, R.string.service_book_place_fail);
			}
		};

		public void onFailureTrans(
				GetAddValueServiceRegionInfosResponse responseModelVO) {
			super.onFailureTrans(responseModelVO);
			if (responseModelVO != null && responseModelVO.getM() != null) {
				DialogManager.showToast(mContext, responseModelVO.getM());
			}
		};

		/**
		 * 网络异常
		 */
		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			DialogManager.showToast(mContext, R.string.service_book_place_fail);

		};
	};

}
