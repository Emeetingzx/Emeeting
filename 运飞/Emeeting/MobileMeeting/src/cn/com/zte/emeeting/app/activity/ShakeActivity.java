package cn.com.zte.emeeting.app.activity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.entity.mime.MinimalField;

import roboguice.inject.InjectView;
import android.R.integer;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.emeeting.app.appservice.ShakeActivityService;
import cn.com.zte.emeeting.app.base.MyApplication;
import cn.com.zte.emeeting.app.base.activity.AppActivity;
import cn.com.zte.emeeting.app.database.entity.shared.DBMeetingRoomAddress;
import cn.com.zte.emeeting.app.dialog.NoFindMeetingRoomDialog;
import cn.com.zte.emeeting.app.dialog.NoFindMeetingRoomDialog.OnClick;
import cn.com.zte.emeeting.app.dialog.ShakeDialog;
import cn.com.zte.emeeting.app.dialog.ShakeDialog.BtnClick;
import cn.com.zte.emeeting.app.response.entity.MeetingRoomInfo;
import cn.com.zte.emeeting.app.response.instrument.GetNearParkAddressInfoResponse;
import cn.com.zte.emeeting.app.response.instrument.GetNearParkMeetingRoomInfoResponse;
import cn.com.zte.emeeting.app.util.DataConst;
import cn.com.zte.emeeting.app.util.DateFormatUtil;
import cn.com.zte.emeeting.app.util.EmeetingToast;
import cn.com.zte.emeeting.app.util.ShakeListener;
import cn.com.zte.emeeting.app.views.TopBar;
import cn.com.zte.emeeting.app.views.TopBar.ButtonOnClick;
import cn.com.zte.mobilebasedata.entity.PageInput;
import cn.com.zte.mobilebasedata.request.HttpUtil;
import cn.com.zte.mobileemeeting.R;

/**
 * 摇一摇界面
 * 
 * @author liu.huanbo
 * */
public class ShakeActivity extends AppActivity {
	/** 纬度 */
	double latitude;
	/** 精度 */
	double longitude;
	/** 摇动监听 */
	private ShakeListener mShakeListener;
	/** 上下文 */
	private Context mContext;
	/** 分页对象 */
	private PageInput pageInput;
	/** topbar */
	@InjectView(R.id.shakefragment_topbar)
	private TopBar shakefragment_topbar;
	/** 摇一摇图片 */
	@InjectView(R.id.tv_shake)
	private ImageView tv_shake;
	/** 摇一摇文字 */
	@InjectView(R.id.tv_shake_txt)
	private TextView tv_shake_txt;
	/** 寻找会议室 */
	private static final int CODE_FIND_ROOM = 1;
	/** 声音延迟 */
	private static final int CODE_TIME = 2;
	/** 时长数据源 */
	private List<String> listDuration;
	/** 播放器 */
	private MediaPlayer mediaPlayer = null;
	/** 资源管理器 */
	private AssetManager assetMgr = null;
	/** 摇晃声音 */
	private final String shakeMusicName = "shake_sound.mp3";
	/** 摇到声音 */
	private final String haveMusicName = "shake_have.mp3";
	/** 没摇到声音 */
	private final String noMusicName = "shake_no_have.mp3";
	/** 摇一摇逻辑处理 */
	private ShakeActivityService shakeActivityService;
	/** 分页查询接口id */
	private String id;
	/** 筛选数据源 */
	private List<DBMeetingRoomAddress> addresses;
	/** 分页查询接口时长 */
	private String time = "1";
	/** 查询接口数据源 */
	private List<MeetingRoomInfo> meetingRoomInfos;
	/** 摇一摇动画动画 */
	private Animation animation;
	/** 是否允许摇一摇 */
	private boolean allowShake = false;
	/** 未找到会议室dialog */
	NoFindMeetingRoomDialog roomDialog;
	/** 选中地点标记 */
	private int hasSelectPositionPlace = -1;
	/** 选中时长标记 */
	private int hasSelectPositionTime = -1;

	/**
	 * 是否可以筛选
	 */
	private boolean isFilter = false;
	/**
	 * 是否第一次
	 */
	private boolean isFirst = true;

	/**
	 * 开始摇一摇时间分钟 8:20
	 */
	private static int startMin = 15;

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case CODE_FIND_ROOM:
				// 判断是否有会议室
				if (mediaPlayer.isPlaying()) {
					mediaPlayer.stop();
				}
				setShake();
				break;
			case CODE_TIME:
				pageInput = new PageInput();
				shakeActivityService.GetNearParkMeetingRoomInfo(
						responseHandlerP, longitude + "," + latitude, id, time,
						pageInput);
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void initContentView() {
		super.initContentView();
		setContentView(R.layout.activity_shake);
		mContext = ShakeActivity.this;
		initUtil();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mShakeListener != null) {
			mShakeListener.setLastUpdateTime(System.currentTimeMillis());
			mShakeListener.start();
		}
		if (!isFirst) {
			ifNetWorkConnect(mContext);
		} else {
			isFirst = false;
		}
	}

	@Override
	protected void initData() {
		super.initData();
		regBroadcastReceiver();
		listDuration = new ArrayList<String>();
		listDuration.add(getString(R.string.tv_shake_time_one));
		listDuration.add(getString(R.string.tv_shake_time_two));
		listDuration.add(getString(R.string.tv_shake_time_three));

	}

	@Override
	protected void initViews() {
		super.initViews();
		initTopBar();
		ifNetWorkConnect(mContext);
		if (!allowShake) {
			tv_shake_txt.setText(getString(R.string.tv_shake_no_net));
		}
		roomDialog = new NoFindMeetingRoomDialog(mContext);
	}

	/** 摇动手机的时间 */
	String shakeTime;

	@Override
	protected void initViewEvents() {
		super.initViewEvents();
		// 摇一摇监听
		mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
			@Override
			public void onShake() {
				if (!allowShake)
					return;
				if (isShake()) {
					// 执行摇一摇操作
					if (OneOrTwoHours()) {

						Date date = DateFormatUtil.getServerTime(mContext);
						SimpleDateFormat format = new SimpleDateFormat("HH:mm");

						shakeTime = format.format(date);

						allowShake();
					}

				}

			}
		});
		// 筛选条件摇一摇
		shakefragment_topbar.setViewOnClickListener(TopBar.rightBtnLogo,
				new ButtonOnClick() {
					@Override
					public void onClick(View view) {
						if (!isFilter) {
							return;
						}
						// 输入条件再摇一摇
						filterShake();
					}
				});
		// 返回菜单键
		shakefragment_topbar.setViewOnClickListener(TopBar.leftBtnLogo,
				new ButtonOnClick() {

					@Override
					public void onClick(View view) {
						ShakeActivity.this.finish();
					}
				});
	}

	@Override
	protected void onPause() {
		allowShake = false;
		if (mShakeListener != null) {
			mShakeListener.stop();
		}
		super.onPause();
	}

	@Override
	public void onDestroy() {
		if (mBroadcastReceiver != null) {
			mContext.unregisterReceiver(mBroadcastReceiver);
		}
		allowShake = false;
		if (mShakeListener != null) {
			mShakeListener = null;
		}
		super.onDestroy();
	}

	/** 初始化 */
	private void initUtil() {
		mShakeListener = new ShakeListener(mContext);
		shakeActivityService = new ShakeActivityService(mContext);
		mediaPlayer = new MediaPlayer();
		assetMgr = this.getAssets();
	}

	/** 初始化TopBar */
	private void initTopBar() {
		shakefragment_topbar.setViewText(TopBar.titleLogo,
				getString(R.string.tv_shake_shake_one));
		shakefragment_topbar.setViewBackGround(TopBar.rightBtnLogo,
				R.drawable.ico_g_filtrate);
		shakefragment_topbar.setViewBackGround(TopBar.leftBtnLogo,
				R.drawable.ico_g_back);

	}

	/**
	 * 摇一摇动画动画
	 */

	private void startAnim() {
		animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_shake);
		tv_shake.startAnimation(animation);

	}

	/**
	 * 筛选条件摇一摇
	 */
	private void filterShake() {

		final ShakeDialog shakeDialog = new ShakeDialog(mContext, addresses,
				listDuration, hasSelectPositionPlace, hasSelectPositionTime);
		shakeDialog.setBtnCancelClick(new BtnClick() {

			@Override
			public void onBtnClick() {
				shakeDialog.dismiss();
				allowShake = true;
			}
		});
		shakeDialog.setBtnSureClick(new BtnClick() {// 确定逻辑（传时长和id）

					@Override
					public void onBtnClick() {
						allowShake = true;
						// mShakeListener.start();
						shakeDialog.dismiss();
						int times = shakeDialog.setGdvDurationClick();
						if (times == 0) {
							time = "1";
							tv_shake_txt
									.setText(getString(R.string.tv_shake_shake1));
						} else if (times == 1) {
							time = "2";
							tv_shake_txt
									.setText(getString(R.string.tv_shake_shake2));
						} else if (times == 2) {
							time = "3";
							tv_shake_txt
									.setText(getString(R.string.tv_shake_shake3));
						} else {
							time = "1";
							tv_shake_txt
									.setText(getString(R.string.tv_shake_shake1));
						}
						hasSelectPositionTime = times;
						int click = shakeDialog.setGdvPlaceClick();
						if (addresses != null && addresses.size() > 0) {

							hasSelectPositionPlace = click;
							if (click < 0) {
								return;
							}
							id = addresses.get(click).getID();

						}

					}
				});
		shakeDialog.setGdvPlaceClick();
		shakeDialog.setGdvDurationClick();
		shakeDialog.show();
		allowShake = false;
	}

	/**
	 * 准备播放音乐
	 * 
	 * @param music
	 */
	private void prepareAndPlay(String name) {
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
		}
		try {
			// 打开指定音乐文件
			AssetFileDescriptor afd = assetMgr.openFd(name);
			mediaPlayer.reset();
			// 使用MediaPlayer加载指定的声音文件。
			mediaPlayer.setDataSource(afd.getFileDescriptor(),
					afd.getStartOffset(), afd.getLength());
			// 准备声音
			mediaPlayer.prepare();
			// 播放
			mediaPlayer.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 执行摇动的逻辑处理 */
	private void allowShake() {
		allowShake = false;
		// mShakeListener.stop();
		prepareAndPlay(shakeMusicName); // 准备播放
		startAnim();
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				try {
					sleep(1000);
					handler.sendEmptyMessage(CODE_TIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}.start();

	}

	/** 3.2.5.1定位查询附近园区地址信息接口 */
	BaseAsyncHttpResponseHandler<GetNearParkAddressInfoResponse> responseHandler = new BaseAsyncHttpResponseHandler<GetNearParkAddressInfoResponse>(
			true) {

		public void onSuccessTrans(
				GetNearParkAddressInfoResponse responseModelVO) {
			if (responseModelVO.getD() != null
					&& responseModelVO.getD().size() > 0) {
				isFilter = true;
				addresses = responseModelVO.getD();
				allowShake = true;
				tv_shake_txt.setText(getString(R.string.tv_shake_shake1));
			} else {
				allowShake = false;
				EmeetingToast.show(mContext,
						getString(R.string.tv_shake_no_have_room));
				tv_shake_txt.setText(getString(R.string.tv_shake_no_have_room));
			}

		};

		public void onFailureTrans(
				GetNearParkAddressInfoResponse responseModelVO) {
			allowShake = false;
			if (responseModelVO != null && responseModelVO.getM() != null
					&& !responseModelVO.getM().equals("")) {
				EmeetingToast.show(mContext, responseModelVO.getM());
			} else {
				EmeetingToast.show(mContext, HttpUtil.SERVER_REQUEST_FAIL);
			}
			tv_shake_txt.setText(getString(R.string.tv_service_error));
		};

		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			allowShake = false;
			Toast.makeText(mContext, HttpUtil.SERVER_REQUEST_FAIL,
					Toast.LENGTH_LONG).show();
			tv_shake_txt.setText(getString(R.string.tv_netword_error));

		};

	};
	/** 3.2.5.2【分页】查询定位或选择园区可预订会议室信息接口 */

	BaseAsyncHttpResponseHandler<GetNearParkMeetingRoomInfoResponse> responseHandlerP = new BaseAsyncHttpResponseHandler<GetNearParkMeetingRoomInfoResponse>(
			true) {

		public void onSuccessTrans(
				GetNearParkMeetingRoomInfoResponse responseModelVO) {
			if (animation != null) {
				animation.cancel();
			}
			if (responseModelVO.getD() != null
					&& responseModelVO.getD().size() > 0) {
				meetingRoomInfos = responseModelVO.getD();
				handler.sendEmptyMessage(CODE_FIND_ROOM);
			} else {
				notShakeMeetingRoom();
			}

		};

		public void onFailureTrans(
				GetNearParkMeetingRoomInfoResponse responseModelVO) {
			if (animation != null) {
				animation.cancel();
			}
			if (responseModelVO != null && responseModelVO.getM() != null
					&& !responseModelVO.getM().equals("")) {
				notShakeMeetingRoom();
			}
		};

		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			if (animation != null) {
				animation.cancel();
			}
			noShakeRoomAndPlay();
			allowShake = true;
			EmeetingToast.show(mContext, HttpUtil.SERVER_REQUEST_FAIL);

		};

	};

	/**
	 * 网络异常时，提示没有摇到会议室的声音
	 */
	private void noShakeRoomAndPlay() {
		prepareAndPlay(noMusicName);
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.stop();
				if (mediaPlayer.isPlaying()) {
					mediaPlayer.stop();

				}

			}
		});
	}

	/**
	 * 没有摇到会议室逻辑处理
	 */
	private void notShakeMeetingRoom() {
		prepareAndPlay(noMusicName);
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.stop();
				if (mediaPlayer.isPlaying()) {
					mediaPlayer.stop();

				}

			}
		});
		if (roomDialog != null && !roomDialog.isShowing()) {
			roomDialog.show();
		}

		allowShake = false;
		// mShakeListener.stop();
		roomDialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				allowShake = true;
				// mShakeListener.start();
				shakefragment_topbar.setViewBackGround(TopBar.rightBtnLogo,
						R.drawable.ico_g_filtrate);
			}
		});
		roomDialog.IvClick(new OnClick() {

			@Override
			public void onclick() {
				roomDialog.dismiss();
				allowShake = true;
				// mShakeListener.start();
				shakefragment_topbar.setViewBackGround(TopBar.rightBtnLogo,
						R.drawable.ico_g_filtrate);

			}
		});

	}

	private MyBroadcastReceiver mBroadcastReceiver;

	private void regBroadcastReceiver() {
		mBroadcastReceiver = new MyBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(DataConst.ACTION_MYLOCATION_CHANGED);
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		mContext.registerReceiver(mBroadcastReceiver, filter);
	}

	/**
	 * 广播接收器
	 * */
	private class MyBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			try {
				if (DataConst.ACTION_HOMEMENU_SWITCH.equals(intent.getAction())) {
					if (intent.getIntExtra("data", -1) == MainActivity.MELOGO) {
						finish();
					}
				}
				if (intent.getAction().equals(
						DataConst.ACTION_MYLOCATION_CHANGED)) {
					if (addresses == null || addresses.size() == 0) {
						tv_shake_txt
								.setText(getString(R.string.tv_shake_do_not_location));
						latitude = MyApplication.GetApp().getMyLocation()
								.getLatitude();
						longitude = MyApplication.GetApp().getMyLocation()
								.getLongitude();
						// latitude = 22.543629;
						// longitude = 113.9558;
						shakeActivityService.GetNearParkAddressInfo(
								responseHandler, +longitude + "," + latitude);

					}
				}
				if (intent.getAction().equals(
						ConnectivityManager.CONNECTIVITY_ACTION)) {

					ifNetWorkConnect(context);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/** 判断网络是否连接 */
	private void ifNetWorkConnect(Context context) {
		State wifiState = null;
		State mobileState = null;
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState();
		if (wifiState != null && mobileState != null
				&& State.CONNECTED == mobileState) {
			// 手机网络连接成功
			latitude = MyApplication.GetApp().getMyLocation().getLatitude();
			longitude = MyApplication.GetApp().getMyLocation().getLongitude();
			if (latitude == 0 || longitude == 0) {
				tv_shake_txt
						.setText(getString(R.string.tv_shake_do_not_location));
				allowShake = false;
				if (animation != null) {
					animation.cancel();
				}
			} else {
				if (addresses != null && addresses.size() > 0) {
					tv_shake_txt.setText(getString(R.string.tv_shake_shake1));
					allowShake = true;
				} else {
					tv_shake_txt.setText(getString(R.string.tv_shake_shake1));
					allowShake = false;
					shakeActivityService.GetNearParkAddressInfo(
							responseHandler, +longitude + "," + latitude);
				}
			}
			// 手机没有任何的网络
		} else if (wifiState != null && mobileState != null
				&& State.CONNECTED != wifiState
				&& State.CONNECTED != mobileState) {
			tv_shake_txt.setText(getString(R.string.tv_shake_no_net));

			allowShake = false;
			// mShakeListener.stop();
			if (animation != null) {
				animation.cancel();
			}

		} else if (wifiState != null && State.CONNECTED == wifiState) {
			// 无线网络连接成功
			latitude = MyApplication.GetApp().getMyLocation().getLatitude();
			longitude = MyApplication.GetApp().getMyLocation().getLongitude();
			if (latitude == 0 || longitude == 0) {
				tv_shake_txt
						.setText(getString(R.string.tv_shake_do_not_location));
				allowShake = false;
				if (animation != null) {
					animation.cancel();
				}
			} else {
				if (addresses != null && addresses.size() > 0) {
					tv_shake_txt.setText(getString(R.string.tv_shake_shake1));
					allowShake = true;
				} else {
					tv_shake_txt.setText(getString(R.string.tv_shake_shake1));
					allowShake = false;
					shakeActivityService.GetNearParkAddressInfo(
							responseHandler, +longitude + "," + latitude);
				}
			}
		}
	}

	/** 判断是否在摇一摇的时间当中 */
	private boolean isShake() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateFormatUtil.getServerTime(mContext));
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int minuteOfDay = hour * 60 + minute;// 从0:00分开是到目前为止的分钟数

		final int start = 20 * 60;// 起始时间 20:00的分钟数
		final int end = 8 * 60 + startMin;// 结束时间 08:20的分钟数
		if (minuteOfDay >= start || minuteOfDay <= end) {
			Toast.makeText(mContext,
					getString(R.string.tv_findroom_do_not_shake),
					Toast.LENGTH_SHORT).show();
			return false;

		}

		return true;
	}

	/** 临界判断 */
	private boolean OneOrTwoHours() {
		Date newTime = DateFormatUtil.getServerTime(mContext);
		SimpleDateFormat df = new SimpleDateFormat("HH");
		int newTimeMinute = Integer.parseInt(df.format(newTime));
		int t = Integer.parseInt(time);
		if (20 > newTimeMinute && newTimeMinute >= 18) {

			if (20 - newTimeMinute < t) {

				EmeetingToast.show(mContext,
						getString(R.string.tv_findroom_do_filt_one)
								+ (20 - newTimeMinute)
								+ getString(R.string.tv_findroom_do_filt_two));
				return false;
			}
			return true;

		}
		return true;

	}

	/** 摇一摇是否摇出会议室 */
	private void setShake() {
		if (animation != null) {
			animation.cancel();
		}
		prepareAndPlay(haveMusicName);
		Intent intent = new Intent(mContext, FindMeetingRoomActivity.class);
		intent.putExtra("data", (ArrayList<MeetingRoomInfo>) meetingRoomInfos);
		intent.putExtra("shakeTime", shakeTime);
		intent.putExtra("ID", id);
		intent.putExtra("TIME", time);
		startActivity(intent);
		overridePendingTransition(R.anim.anim_activity, R.anim.anim_exit);
	}
}
