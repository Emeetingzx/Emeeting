package cn.com.zte.emeeting.app.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import roboguice.inject.InjectView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.android.resource.inflater.BaseLayoutInflater;
import cn.com.zte.emeeting.app.activity.MainActivity;
import cn.com.zte.emeeting.app.appservice.PhoneFragmentService;
import cn.com.zte.emeeting.app.base.fragment.AppFragment;
import cn.com.zte.emeeting.app.dialog.DialogBotDoubleTimeChoose;
import cn.com.zte.emeeting.app.dialog.OthersDialog;
import cn.com.zte.emeeting.app.dialog.OthersDialog.OnClick;
import cn.com.zte.emeeting.app.dialog.ReserveDialog;
import cn.com.zte.emeeting.app.response.entity.MeetingInfo;
import cn.com.zte.emeeting.app.response.instrument.GetReservePhoneOrVideoMeetingRoomResponse;
import cn.com.zte.emeeting.app.util.DataConst;
import cn.com.zte.emeeting.app.util.DateFormatUtil;
import cn.com.zte.emeeting.app.util.EmeetingToast;
import cn.com.zte.emeeting.app.views.TimeChooseBotDoubleView.onClickEventListener;
import cn.com.zte.emeeting.app.views.TopBar;
import cn.com.zte.emeeting.app.views.TopBar.ButtonOnClick;
import cn.com.zte.mobilebasedata.request.HttpUtil;
import cn.com.zte.mobileemeeting.R;

/**
 * 电话视频会议界面
 * 
 * @author liu.huanbo
 * 
 */
public class PhoneVideoFragment extends AppFragment implements OnClickListener {
	/** 上下文 */
	private Context mContext;
	/** topbar_phone */
	@InjectView(R.id.topbar_phone)
	private TopBar topbar_phone;
	/** 会议名称 */
	@InjectView(R.id.et_meeting_name)
	private EditText et_meeting_name;
	/** 会议类型 */
	@InjectView(R.id.tv_meeting_type)
	private TextView tv_meeting_type;
	/** 起止时间 */
	@InjectView(R.id.rel_time)
	private RelativeLayout rel_time;
	/** 起止时间 */
	@InjectView(R.id.tv_meeting_data)
	private TextView tv_meeting_data;
	/** 参会领导 */
	@InjectView(R.id.join_leader_other)
	private TextView join_leader_other;
	/** 会议级别 */
	@InjectView(R.id.tv_meeting_level)
	private TextView tv_meeting_level;
	/** 预定 */
	@InjectView(R.id.btn_reserve)
	private Button btn_reserve;
	/** 参会领导数据源 */
	private ArrayList<String> listLeaders;
	/** 会议类型数据源 */
	private ArrayList<String> listTypes;
	// /** 参会方数 */
	// @InjectView(R.id.et_join_meeting_num)
	// private EditText et_join_meeting_num;
	/** 会议密码 */
	@InjectView(R.id.et_join_meeting_pwd)
	private EditText et_join_meeting_pwd;
	/** 会议编号布局 */
	@InjectView(R.id.rel_join_meeting_num)
	private RelativeLayout rel_join_meeting_num;
	/** 会议密码布局 */
	@InjectView(R.id.view_line)
	private View view_line;
	/** 会议密码布局 */
	@InjectView(R.id.rel_join_meeting_pwd)
	private RelativeLayout rel_join_meeting_pwd;
	/** 判断是视频会议还是电话会议 (默认是电话会议) */
	public static boolean isShow = true;
	/** 日期格式化 */
	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	/** 日历对象 */
	private Calendar calendar = Calendar.getInstance();
	/** 接口所需要的MID */
	private String id;

	@Override
	protected View onCreateView(BaseLayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {

		mContext = getActivity();

		return LayoutInflater.from(mContext).inflate(
				R.layout.fragment_phone_video, null);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (isHidden()) {
			return;
		}
		cleanRecord();
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (hidden) {
			cleanRecord();
		}
	}

	@Override
	protected void initData() {
		super.initData();
		listLeaders = new ArrayList<String>();
		listLeaders.add(getString(R.string.tv_message_detail_two_leader));
		listLeaders.add(getString(R.string.tv_message_detail_three_leader));
		listLeaders.add(getString(R.string.tv_message_detail_four_leader));
		listLeaders.add(getString(R.string.tv_message_detail_other_leader));
		listTypes = new ArrayList<String>();
		listTypes.add(getString(R.string.tv_message_detail_phone_meeting));
		listTypes.add(getString(R.string.tv_message_detail_video_meeting));
	}

	@Override
	protected void initViews() {
		super.initViews();
		initTopbar();
	}

	@Override
	protected void initViewEvents() {
		super.initViewEvents();
		tv_meeting_type.setOnClickListener(this);
		rel_time.setOnClickListener(this);
		join_leader_other.setOnClickListener(this);
		btn_reserve.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_meeting_type:// 会议类型

			chooseDialog(listTypes, tv_meeting_type, tv_meeting_level,
					tv_meeting_type.getText().toString(), rel_join_meeting_num,
					rel_join_meeting_pwd, view_line);

			break;
		case R.id.rel_time:// 起止时间

			chooseTime();
			break;
		case R.id.join_leader_other:// 参会领导

			chooseDialog(listLeaders, join_leader_other, tv_meeting_level,
					join_leader_other.getText().toString(),
					rel_join_meeting_num, rel_join_meeting_pwd, view_line);

			break;
		case R.id.btn_reserve:// 预定按钮

			// 预定之前逻辑处理
			reservePrepare();

			break;

		default:
			break;
		}
	}

	/** 初始化tb */
	private void initTopbar() {
		
		topbar_phone.setViewBackGround(TopBar.leftBtnLogo,
				R.drawable.icon_home_menu);
		topbar_phone.HiddenView(TopBar.rightBtnLogo);
		topbar_phone.setViewText(TopBar.titleLogo,
				getString(R.string.tv_message_detail_phone_or_video_meeting));
		topbar_phone.setViewOnClickListener(TopBar.leftBtnLogo,
				new ButtonOnClick() {

					@Override
					public void onClick(View view) {
						// 隐藏软键盘
						InputMethodManager inputManager = (InputMethodManager) mContext
								.getSystemService(Context.INPUT_METHOD_SERVICE);
						if (inputManager.isActive()) {
							inputManager.hideSoftInputFromWindow(
									et_join_meeting_pwd.getWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS);
						}
						// 切换菜单
						MainActivity activity = (MainActivity) getActivity();
						activity.showLeftView();
					}
				});
	}

	/** 选择时间 */
	private void chooseTime() {
		final DialogBotDoubleTimeChoose dialog = new DialogBotDoubleTimeChoose(
				mContext,true);
		String time = tv_meeting_data.getText().toString();
		String str[] = time.split("-");
		String newTime = str[0] + "-" + str[1] + "-" + str[2] + " " + str[3];

		dialog.setCurrentDateAndTime(newTime);
		dialog.show();
		dialog.setClickEventListener(new onClickEventListener() {

			@Override
			public void onSure(String time) {

				if (time.equals("0") || time.equals("-1")) {
					EmeetingToast.show(mContext,
							getString(R.string.tv_message_detail_end_time));
				} else {

					String str[] = time.split(" ");
					String times = str[0] + " " + str[1] + "-" + str[2];
//					String cutime = str[0]+" "+str[1];
//					try {
//						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//						Date date= format.parse(cutime);
//						Calendar calendar = Calendar.getInstance();
//						calendar.setTime(date);
//						if (calendar.before(currentCalendar)) {
//							EmeetingToast.show(mContext, R.string.service_time_before);
//							return;
//						}
//					} catch (Exception e) {
//						// TODO: handle exception
//						e.printStackTrace();
//					}
					
					Log.d("XXX", time);
					tv_meeting_data.setText(times);
					dialog.dismiss();
				}
			}

			@Override
			public void noTime() {
				dialog.dismiss();
			}
		});
	}

	/** 公共选择类型的dialog */
	private void chooseDialog(List<String> str, TextView tv, TextView click,
			String select, View num, View pwd, View line) {
		final OthersDialog othersDialogLeader = new OthersDialog(mContext, str,
				select);
		othersDialogLeader.onCancelButtonClick(new OnClick() {

			@Override
			public void onClick() {
				othersDialogLeader.dismiss();
			}
		});
		othersDialogLeader.onLeaderClick(et_join_meeting_pwd,tv, click, num, pwd, line);
	}

	/** 预定情况 */
	private void dialogDetail(String content) {

		final ReserveDialog reserveDialog = new ReserveDialog(mContext);
		reserveDialog.setContentText(content);
		reserveDialog.sureBtnClick(new ReserveDialog.OnClick() {

			@Override
			public void onclick() {
				reserveDialog.dismiss();
				et_meeting_name.setText("");
				tv_meeting_type
						.setText(getString(R.string.tv_message_detail_phone_meeting));
				join_leader_other
						.setText(getString(R.string.tv_message_detail_other_leader));
				tv_meeting_level
						.setText(getString(R.string.tv_message_detail_c_level));
				// et_join_meeting_num.setText("");
				et_join_meeting_pwd.setText("");
				et_join_meeting_pwd.setHint(getString(R.string.tv_only_six_number));

			}
		});
		reserveDialog.cancelBtnClick(new ReserveDialog.OnClick() {

			@Override
			public void onclick() {
				if (id != "" && id != null) {
					Intent it = new Intent(DataConst.ACTION_HOMEMENU_SWITCH);
					it.putExtra("data", MainActivity.MELOGO);
					getActivity().sendBroadcast(it);
					reserveDialog.dismiss();
				} else {
					EmeetingToast.show(mContext,
							getString(R.string.tv_message_detail_serve_no_id));

				}

			}
		});
	}

	/** 视频会议接口 */
	BaseAsyncHttpResponseHandler<GetReservePhoneOrVideoMeetingRoomResponse> handler = new BaseAsyncHttpResponseHandler<GetReservePhoneOrVideoMeetingRoomResponse>(
			true) {

		public void onSuccessTrans(
				GetReservePhoneOrVideoMeetingRoomResponse responseModelVO) {

			if (responseModelVO.getD() != null
					&& responseModelVO.getD().size() > 0) {
				id = responseModelVO.getD().get("MID");
				String pn = tv_meeting_type.getText().toString();
				if (pn.equals(getString(R.string.tv_message_detail_phone_meeting))) {
					pn = getString(R.string.tv_message_detail_phone_meeting_success);
				} else {
					pn = getString(R.string.tv_message_detail_video_meeting_success);
				}
				dialogDetail(pn);
				Intent it_refresh = new Intent(
						DataConst.ACTION_MYMEETING_REFRESH);
				mContext.sendBroadcast(it_refresh);

			}

		};

		public void onFailureTrans(
				GetReservePhoneOrVideoMeetingRoomResponse responseModelVO) {
			if (responseModelVO != null && responseModelVO.getM() != null
					&& !responseModelVO.getM().equals("")) {
				EmeetingToast.show(mContext, responseModelVO.getM(),8000);
			} else {
				EmeetingToast.show(mContext, HttpUtil.SERVER_REQUEST_FAIL);
			}
		};

		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			Toast.makeText(mContext, HttpUtil.SERVER_REQUEST_FAIL,
					Toast.LENGTH_LONG).show();

		};

	};

	/** 清空之前预定信息 */
	private void cleanRecord() {
		et_meeting_name.setText("");
		tv_meeting_type
				.setText(getString(R.string.tv_message_detail_phone_meeting));
		et_join_meeting_pwd.setHint(getString(R.string.tv_only_six_number));
		join_leader_other
				.setText(getString(R.string.tv_message_detail_other_leader));
		tv_meeting_level.setText(getString(R.string.tv_message_detail_c_level));
		// et_join_meeting_num.setText("");
		et_join_meeting_pwd.setText("");
		Date date = DateFormatUtil.getServerTime(mContext);
		calendar.setTime(date);
		int minute = calendar.get(Calendar.MINUTE);
		if (minute > 0) {
			calendar.add(Calendar.HOUR_OF_DAY, 1);
			calendar.set(Calendar.MINUTE, 0);
		} else {
			calendar.set(Calendar.MINUTE, minute);
		}
		date = calendar.getTime();
		calendar.add(Calendar.HOUR_OF_DAY, 2);
		Date nextHour = calendar.getTime();
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		String ed = df.format(nextHour);
		String dateString = format.format(date);
		tv_meeting_data.setText(dateString + "-" + ed);
	}

	/** 预定之前的逻辑判断 */
	private void reservePrepare() {
		MeetingInfo info = new MeetingInfo();
		info.setMN(et_meeting_name.getText().toString());
		// info.setPN(et_join_meeting_num.getText().toString());
		info.setMPWD(et_join_meeting_pwd.getText().toString());
		String type = tv_meeting_type.getText().toString();
		if (type.equals(getString(R.string.tv_message_detail_routine_meeting))) {
			type = "1";
		} else if (type
				.equals(getString(R.string.tv_message_detail_phone_meeting))) {
			type = "2";

		} else if (type
				.equals(getString(R.string.tv_message_detail_train_meeting))) {
			type = "3";

		} else if (type
				.equals(getString(R.string.tv_message_detail_net_meeting))) {
			type = "4";

		} else if (type
				.equals(getString(R.string.tv_message_detail_cloud_meeting))) {
			type = "5";

		} else if (type
				.equals(getString(R.string.tv_message_detail_video_meeting))) {
			type = "6";
		}

		info.setMTP(type);
		String leader = join_leader_other.getText().toString();
		String level = tv_meeting_level.getText().toString();
		if (leader.equals(getString(R.string.tv_message_detail_two_leader))) {
			leader = "1";
			level = "1";
		} else if (leader
				.equals(getString(R.string.tv_message_detail_three_leader))) {

			leader = "2";
			level = "2";
		} else if (leader
				.equals(getString(R.string.tv_message_detail_four_leader))) {

			leader = "3";
			level = "3";
		} else if (leader
				.equals(getString(R.string.tv_message_detail_other_leader))) {

			leader = "4";
			level = "3";
		}

		info.setPMLL(leader);
		info.setML(level);

		// Log.d("XXXXX", tv_meeting_data.getText().toString());
		String[] str = tv_meeting_data.getText().toString().split(" ");
		String[] str2 = str[1].split("-");
		info.setBD(str[0] + " " + str2[0] + ":00");
		info.setED(str[0] + " " + str2[1] + ":00");

		if (!et_meeting_name.getText().toString().isEmpty()) {

			if (type.equals("2")) {
				if (!et_join_meeting_pwd.getText().toString().isEmpty()) {

					new PhoneFragmentService(mContext)
							.ReservePhoneOrVideoMeetingRoom(handler, info);
				} else {

					EmeetingToast.show(mContext,
							getString(R.string.tv_message_please_input_pwd));
				}
			} else if (type.equals("6")) {
				new PhoneFragmentService(mContext)
						.ReservePhoneOrVideoMeetingRoom(handler, info);
			}

		} else {
			EmeetingToast.show(mContext,
					getString(R.string.tv_message_please_input_name));
		}
	}

}
