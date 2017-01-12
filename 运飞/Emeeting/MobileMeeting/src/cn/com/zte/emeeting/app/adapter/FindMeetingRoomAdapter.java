package cn.com.zte.emeeting.app.adapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.baidu.location.f;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import cn.com.zte.android.app.activity.BaseActivity;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.emeeting.app.activity.FindMeetingRoomActivity;
import cn.com.zte.emeeting.app.activity.MeetingBookChooseOtherActivity;
import cn.com.zte.emeeting.app.appservice.MeetingBookService;
import cn.com.zte.emeeting.app.base.ConfigrationList;
import cn.com.zte.emeeting.app.dialog.DlgMeetingBook;
import cn.com.zte.emeeting.app.dialog.DlgMeetingBook.DlgMeetingListener;
import cn.com.zte.emeeting.app.dialog.DlgMeetingBook.TimeOutListener;
import cn.com.zte.emeeting.app.response.entity.MeetingRoomInfo;
import cn.com.zte.emeeting.app.response.instrument.GetLockBookingMeetingRoomResponse;
import cn.com.zte.emeeting.app.util.DataConst;
import cn.com.zte.emeeting.app.util.DateFormatUtil;
import cn.com.zte.emeeting.app.util.EmeetingToast;
import cn.com.zte.emeeting.app.util.SharedPreferenceUtil;
import cn.com.zte.emeeting.app.views.ClockChooseView;
import cn.com.zte.mobileemeeting.R;

/**
 * 找到会议室的adapter
 * 
 * @author liu.huanbo
 * 
 */
public class FindMeetingRoomAdapter extends BaseAdapter {
	/** 上下文 */
	private Context mContext;
	/** 集合列表 */
	private List<MeetingRoomInfo> lists;
	/** 解析器 */
	LayoutInflater layoutInflater;
	private DlgMeetingBook dlg;
	/** 会议退订逻辑 */
	private MeetingBookService mService;
	/** 时长 */
	private String time;
	/** 起止时间字符串 */
	private String str;
	/** 摇一摇时间 */
	private String shakeTime;
	/**
	 * 预订按钮是否可以点击
	 */
	private boolean bookButIsClick = true;

	public String getShakeTime() {
		return shakeTime;
	}

	public void setShakeTime(String shakeTime) {
		this.shakeTime = shakeTime;
		shakeDate = DateFormatUtil.getServerTime(mContext);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(shakeDate);
		try {
			int hour = Integer.parseInt(this.shakeTime.split(":")[0]);
			int minute = Integer.parseInt(this.shakeTime.split(":")[1]);
			calendar.set(Calendar.HOUR_OF_DAY, hour);
			calendar.set(Calendar.MINUTE, minute);
			shakeDate = calendar.getTime();
		} catch (Exception e) {
			// TODO: handle exception
		}
		calendar.add(Calendar.HOUR_OF_DAY, Integer.parseInt(this.time));
		Date newTime = calendar.getTime();
		str = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(shakeDate) + " "
				+ new SimpleDateFormat("HH:mm").format(newTime);
	}

	/**
	 * 摇一摇的时间
	 */
	private Date shakeDate;

	public FindMeetingRoomAdapter(Context mContext,
			List<MeetingRoomInfo> lists, String time, String shakeTime) {
		this.mContext = mContext;
		this.lists = lists;
		layoutInflater = LayoutInflater.from(mContext);
		mService = new MeetingBookService(mContext);
		this.time = time;
		this.shakeTime = shakeTime;

		shakeDate = DateFormatUtil.getServerTime(mContext);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(shakeDate);
		try {
			int hour = Integer.parseInt(this.shakeTime.split(":")[0]);
			int minute = Integer.parseInt(this.shakeTime.split(":")[1]);
			calendar.set(Calendar.HOUR_OF_DAY, hour);
			calendar.set(Calendar.MINUTE, minute);
			shakeDate = calendar.getTime();
		} catch (Exception e) {
			// TODO: handle exception
		}
		calendar.add(Calendar.HOUR_OF_DAY, Integer.parseInt(this.time));
		Date newTime = calendar.getTime();
		str = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(shakeDate) + " "
				+ new SimpleDateFormat("HH:mm").format(newTime);

	}

	public void setLists(List<MeetingRoomInfo> meetingRoomInfos) {
		// TODO Auto-generated method stub
		lists = meetingRoomInfos;
		if (lists.size() <= 3) {
		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		if (lists.size() <= 3) {

			return lists.size();
		}

		return 3;

	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	/** 锁定会议室handler */
	private BaseAsyncHttpResponseHandler<GetLockBookingMeetingRoomResponse> handlerLockMeetingroom = new BaseAsyncHttpResponseHandler<GetLockBookingMeetingRoomResponse>() {

		@Override
		public void onSuccessTrans(GetLockBookingMeetingRoomResponse res) {
			if (res.getD() != null) {
				String meetingId = res.getD().get("EID");
				Intent it = new Intent(mContext,
						MeetingBookChooseOtherActivity.class);
				it.putExtra("isShake", true);
				it.putExtra("data", meetingId);
				mContext.startActivity(it);
				dlg.dismiss();
			}
		};

		@Override
		public void onFailureTrans(
				GetLockBookingMeetingRoomResponse responseModelVO) {
			if (responseModelVO != null && responseModelVO.getM() != null
					&& !responseModelVO.getM().equals("")) {
				EmeetingToast.show(mContext, responseModelVO.getM());
			} else {
				EmeetingToast.show(mContext, R.string.respones_false);
			}
		};

		@Override
		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			if (strMsg != null && !strMsg.equals("")) {
				EmeetingToast.showHttp(mContext, strMsg);
			} else {
				EmeetingToast.showHttp(mContext, mContext.getResources()
						.getString(R.string.respones_false));
			}
		};
	};

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder holder;

		if (convertView == null) {

			holder = new ViewHolder();
			convertView = layoutInflater.inflate(
					R.layout.item_find_meeting_room_activity, null);

			holder.iv_projector = (ImageView) convertView
					.findViewById(R.id.iv_projector);
			holder.iv_phone = (ImageView) convertView
					.findViewById(R.id.iv_phone);
			holder.iv_tv = (ImageView) convertView.findViewById(R.id.iv_tv);
			holder.btn_reserve = (Button) convertView.findViewById(R.id.btn);

			holder.tv_person_num = (TextView) convertView
					.findViewById(R.id.tv_person_num);
			holder.tv_building_num = (TextView) convertView
					.findViewById(R.id.tv_building_num);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();

		}

		if (lists.get(position).getPJS().equals("0")
				&& lists.get(position).getPJS() != null) {
			holder.iv_projector.setImageDrawable(mContext.getResources()
					.getDrawable(R.drawable.ico_meeting_projector2));
		} else {
			holder.iv_projector.setImageDrawable(mContext.getResources()
					.getDrawable(R.drawable.ico_meeting_projector1));
		}
		if (lists.get(position).getPS().equals("0")
				&& lists.get(position).getPS() != null) {
			holder.iv_phone.setImageDrawable(mContext.getResources()
					.getDrawable(R.drawable.ico_meeting_phone2));
		} else {
			holder.iv_phone.setImageDrawable(mContext.getResources()
					.getDrawable(R.drawable.ico_meeting_phone1));
		}
		if (lists.get(position).getTVS().equals("0")
				&& lists.get(position).getTVS() != null) {

			holder.iv_tv.setImageDrawable(mContext.getResources().getDrawable(
					R.drawable.ico_meeting_tv2));
		} else {
			holder.iv_tv.setImageDrawable(mContext.getResources().getDrawable(
					R.drawable.ico_meeting_tv1));
		}

		holder.btn_reserve.setText(mContext.getResources().getString(
				R.string.tv_findroom_do_reserve));
		holder.tv_person_num.setText(lists.get(position).getMRS());
		holder.tv_building_num.setText(lists.get(position).getMRN());

		holder.btn_reserve.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!bookButIsClick) {
					return;
				}

				Date date = DateFormatUtil.getServerTime(mContext);
				SimpleDateFormat format = new SimpleDateFormat("HH:mm");
				String reserveTime = format.format(date);// 预定时间
				String[] shakeTimes = shakeTime.split(":");
				String[] reserveTimes = reserveTime.split(":");
				int shake = Integer.parseInt(shakeTimes[1].substring(0, 1));
				int reserve = Integer.parseInt(reserveTimes[1].substring(0, 1));

				if (shakeTimes[0].equals(reserveTimes[0])) {// 小时相同
					// 判断分钟十位 个位不需要判断
					if (reserve < 3 && shake < 3 || reserve >= 3 && shake >= 3) {
						dlg = new DlgMeetingBook(mContext, true, shakeDate,
								lists.get(position), str,
								new DlgMeetingListener() {
									@Override
									public void onOkButtonClicked(Object o,
											ClockChooseView timeView) {

										if (TextUtils.isEmpty(timeView
												.getChooseTime())) {
											EmeetingToast
													.show(mContext,
															mContext.getResources()
																	.getString(
																			R.string.tv_findroom_do_choose));
										} else if ("-1".equals(timeView
												.getChooseTime())) {
											EmeetingToast
													.show(mContext,
															mContext.getResources()
																	.getString(
																			R.string.tv_findroom_do_please_choose));
										} else if ("0".equals(timeView
												.getChooseTime())) {
											EmeetingToast
													.show(mContext,
															mContext.getResources()
																	.getString(
																			R.string.tv_findroom_do_choose));
										} else {

											String chooseHours[] = timeView
													.getChooseTime().split("-");
											String currentDate = new SimpleDateFormat(
													"yyyy-MM-dd")
													.format(shakeDate);

											String beginDate = currentDate
													+ " " + chooseHours[0]
													+ ":00";
											String endDate = currentDate + " "
													+ chooseHours[1] + ":00";

											try {
												Date beginTime = new SimpleDateFormat(
														"yyyy-MM-dd HH:mm")
														.parse(beginDate);
												long currentTime = DateFormatUtil
														.getServerTime(mContext)
														.getTime();
												if (currentTime > beginTime
														.getTime()) {
													EmeetingToast
															.show(mContext,
																	mContext.getResources()
																			.getString(
																					R.string.tv_findroom_do_start_end_time));
													return;
												}
											} catch (Exception e) {
												// TODO Auto-generated catch
												// block
												e.printStackTrace();
											}

											mService.lockMeetingRoom(
													lists.get(position)
															.getMRID(),
													beginDate, endDate,
													handlerLockMeetingroom);

										}
									}

									@Override
									public void onCancelClicked() {
										// TODO Auto-generated method stub
										((FindMeetingRoomActivity) mContext)
												.setAllowShake(true);
									}
								}, new TimeOutListener() {

									@Override
									public void onTimeOut() {
										// TODO Auto-generated method stub
										EmeetingToast
												.show(mContext,
														mContext.getResources()
																.getString(
																		R.string.tv_findroom_do_out_time));

									}
								});
						Intent intent = new Intent(DataConst.ACTION_FIND_ROOM);
						mContext.sendBroadcast(intent);
						dlg.show();
					} else {
						// sendTimeOutBroadcast();

						EmeetingToast.show(mContext, mContext.getResources()
								.getString(R.string.tv_findroom_do_out_time),
								1500);

						new Thread() {
							public void run() {
								try {
									sleep(1500);
									handler.sendEmptyMessage(0);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							};
						}.start();

					}

				} else {
					// sendTimeOutBroadcast();
					EmeetingToast.show(mContext, mContext.getResources()
							.getString(R.string.tv_findroom_do_out_time), 1500);
					new Thread() {
						public void run() {
							try {
								sleep(1500);
								handler.sendEmptyMessage(0);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						};
					}.start();

				}

			}

		});
		holder.btn_reserve.setBackgroundResource(R.color.SABgColor);
		return convertView;

	}

	private void sendTimeOutBroadcast() {
		Intent intent = new Intent(DataConst.ACTION_TIME_OUT);
		mContext.sendBroadcast(intent);
	}

	public boolean isBookButIsClick() {
		return bookButIsClick;
	}

	public void setBookButIsClick(boolean bookButIsClick) {
		this.bookButIsClick = bookButIsClick;
	}

	class ViewHolder {
		/** 投影仪 */
		private ImageView iv_projector;
		/** 电话 */
		private ImageView iv_phone;
		/** 电视 */
		private ImageView iv_tv;
		/** 预订按钮 */
		private Button btn_reserve;
		/** 会议室规模数量 */
		private TextView tv_person_num;
		/** 会议室地址大楼 */
		private TextView tv_building_num;

	}

	Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case 0:
				((BaseActivity) mContext).finish();
				break;

			default:
				break;
			}
		};

	};

}
