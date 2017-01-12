package cn.com.zte.emeeting.app.activity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import roboguice.inject.InjectView;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.emeeting.app.adapter.FindMeetingRoomAdapter;
import cn.com.zte.emeeting.app.appservice.ShakeActivityService;
import cn.com.zte.emeeting.app.base.MyApplication;
import cn.com.zte.emeeting.app.base.activity.AppActivity;
import cn.com.zte.emeeting.app.dialog.NoFindMeetingRoomDialog;
import cn.com.zte.emeeting.app.response.entity.MeetingRoomInfo;
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
 * 找到会议室的界面
 * 
 * @author liu.huanbo
 * 
 */
public class FindMeetingRoomActivity extends AppActivity {
	/** 寻找会议室 */
	private static final int CODE_FIND_ROOM = 1;
	/** 摇动监听 */
	private ShakeListener mShakeListener;
	/** 是否允许摇一摇 */
	private boolean allowShake = false;
	/** 精度 */
	double latitude;
	/** 纬度 */
	double longitude;
	/** 分页对象 */
	private PageInput pageInput;
	/** lst */
	@InjectView(R.id.lst_view)
	private ListView lst;
	/** 上下文 */
	private Context mContext;
	/** tb */
	@InjectView(R.id.find_meeting_topbar)
	private TopBar find_meeting_topbar;
	/** 找到会议室数据源 */
	private List<MeetingRoomInfo> lists;
	/** 找到会议室布局 */
	@InjectView(R.id.linear_have_room)
	private LinearLayout linear_have_room;
	@InjectView(R.id.tv_meeting_room_num)
	private TextView tv_meeting_room_num;
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
	/** 未找到会议室dialog */
	NoFindMeetingRoomDialog roomDialog;
	/** 查询接口数据源 */
	private List<MeetingRoomInfo> meetingRoomInfos;
	/** 调用接口id */
	private String id;
	/** 调用接口时长 */
	private String time = "1";
	FindMeetingRoomAdapter adapter;
	/** 声音延迟 */
	private static final int CODE_TIME = 2;
	/**
	 * 界面关闭时是否发送广播
	 */
	private boolean isSendRecevice = true;
	/** 摇一摇的时间 */
	private String shakeTime;
	/** 当前页面再次摇一摇的时间 */
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case CODE_FIND_ROOM:
				// 判断是否有会议室
				if (mediaPlayer.isPlaying()) {
					mediaPlayer.stop();
				}
				// 判断是否有会议室
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
		setContentView(R.layout.dialog_have_meeting_room);
		initUtil();
		linear_have_room.setAnimation(AnimationUtils.loadAnimation(mContext,
				R.anim.anim_enter));
		initTopbar();
		try {
			lists = (List<MeetingRoomInfo>) getIntent().getSerializableExtra("data");
			id = getIntent().getStringExtra("ID");
			time = getIntent().getStringExtra("TIME");
			shakeTime = getIntent().getStringExtra("shakeTime");
		} catch (Exception e) {
			lists = new ArrayList<MeetingRoomInfo>();
			id = "";
			time = "1";
			shakeTime = "";
			e.printStackTrace();
		}
	}

	/** 初始化 */
	private void initUtil() {
		mContext = this;
		shakeActivityService = new ShakeActivityService(mContext);
		mediaPlayer = new MediaPlayer();
		assetMgr = this.getAssets();
	}

	/** 初始化tb */
	private void initTopbar() {
		find_meeting_topbar.setViewBackGround(TopBar.leftBtnLogo,
				R.drawable.ico_g_back);
	}

	@Override
	protected void initData() {
		latitude = MyApplication.GetApp().getMyLocation().getLatitude();
		longitude = MyApplication.GetApp().getMyLocation().getLongitude();
		regBroadcastReceiver();
		super.initData();

	}

	@Override
	protected void initViews() {
		super.initViews();
		if (lists != null&&lists.size()>0) {
			if (lists.size() == 1) {
				tv_meeting_room_num.setText(getString(R.string.tv_findroom_shake_one));
			} else if (lists.size() == 2) {
				tv_meeting_room_num.setText(getString(R.string.tv_findroom_shake_two));
			} else if (lists.size()>= 3) {
				tv_meeting_room_num.setText(getString(R.string.tv_findroom_shake_three));
			}
		}
		mShakeListener = new ShakeListener(mContext);
		roomDialog = new NoFindMeetingRoomDialog(mContext);
		adapter = new FindMeetingRoomAdapter(mContext, lists, time, shakeTime);
		lst.setAdapter(adapter);
	}

	@Override
	protected void initViewEvents() {
		super.initViewEvents();
		find_meeting_topbar.setViewOnClickListener(TopBar.leftBtnLogo,
				new ButtonOnClick() {
					@Override
					public void onClick(View view) {
						finish();
					}
				});
		mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
			@Override
			public void onShake() {
				if (!allowShake)
					return;
				if (isShake()) {
					if (OneOrTwoHours()) {
						Date date = DateFormatUtil.getServerTime(mContext);
						SimpleDateFormat format = new SimpleDateFormat("HH:mm");
						shakeTime = format.format(date);
						adapter.setShakeTime(shakeTime);
						allowShake();
					}
				}
			}
		});

	}

	/** 摇一摇是否摇出会议室 */
	private void setShake() {
		allowShake = true;
		prepareAndPlay(haveMusicName);
		// 刷新数据
		if (meetingRoomInfos.size() > 3) {
			adapter.setLists(meetingRoomInfos);
		} else {
			EmeetingToast.show(mContext, getString(R.string.tv_findroom_no_have_message), 1500);
		}

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

	private MyBroadcastReceiver mBroadcastReceiver;

	private void regBroadcastReceiver() {
		mBroadcastReceiver = new MyBroadcastReceiver();
		IntentFilter filter = new IntentFilter(DataConst.ACTION_HOMEMENU_SWITCH);
		filter.addAction(DataConst.ACTION_FIND_ROOM);
		filter.addAction(DataConst.ACTION_TIME_OUT);
		mContext.registerReceiver(mBroadcastReceiver, filter);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mShakeListener != null) {
			mShakeListener.setLastUpdateTime(System.currentTimeMillis());
			mShakeListener.start();
		}
		allowShake = true;
	}

	@Override
	protected void onPause() {
		allowShake = false;
		if (mShakeListener != null) {
			mShakeListener.stop();
		}
		super.onPause();
	}

	public boolean isAllowShake() {
		return allowShake;
	}

	public void setAllowShake(boolean allowShake) {
		this.allowShake = allowShake;
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

	/**
	 * 广播接收器
	 * */
	private class MyBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			try {
				if (DataConst.ACTION_HOMEMENU_SWITCH.equals(intent.getAction())) {
					isSendRecevice = false;
					finish();
				}
				if (intent.getAction().equals(DataConst.ACTION_FIND_ROOM)) {
					allowShake = false;

				}
				if (intent.getAction().equals(DataConst.ACTION_TIME_OUT)) {
					allowShake = true;
					finish();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/** 3.2.5.2【分页】查询定位或选择园区可预订会议室信息接口 */

	BaseAsyncHttpResponseHandler<GetNearParkMeetingRoomInfoResponse> responseHandlerP = new BaseAsyncHttpResponseHandler<GetNearParkMeetingRoomInfoResponse>(
			true) {

		public void onSuccessTrans(
				GetNearParkMeetingRoomInfoResponse responseModelVO) {
			if (adapter!=null) {
				adapter.setBookButIsClick(true);
			}
			if (responseModelVO.getD() != null
					&& responseModelVO.getD().size() > 0) {
				meetingRoomInfos = responseModelVO.getD();
				handler.sendEmptyMessage(CODE_FIND_ROOM);
			}

		};

		public void onFailureTrans(
				GetNearParkMeetingRoomInfoResponse responseModelVO) {
			if (adapter!=null) {
				adapter.setBookButIsClick(true);
			}
			allowShake = true;
			notShakeMeetingRoom();
			if (responseModelVO != null && responseModelVO.getM() != null
					&& !responseModelVO.getM().equals("")) {
				EmeetingToast.show(mContext, responseModelVO.getM());
			}else {
				EmeetingToast.show(mContext,  HttpUtil.SERVER_REQUEST_FAIL);
			}
		};

		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			if (adapter!=null) {
				adapter.setBookButIsClick(true);
			}
			notShakeMeetingRoom();
			allowShake = true;
			EmeetingToast.show(mContext,  HttpUtil.SERVER_REQUEST_FAIL);
		};

	};

	/** 判断是否在摇一摇的时间当中 */
	private boolean isShake() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateFormatUtil.getServerTime(mContext));
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int minuteOfDay = hour * 60 + minute;// 从0:00分开是到目前为止的分钟数

		final int start = 20 * 60;// 起始时间 20:00的分钟数
		final int end = 8 * 60 + 20;// 结束时间 08:20的分钟数
		if (minuteOfDay >= start || minuteOfDay <= end) {
			Toast.makeText(mContext, getString(R.string.tv_findroom_do_not_shake),
					Toast.LENGTH_SHORT).show();
			return false;

		}

		return true;
	}

	/** 允许摇动的逻辑处理 */
	private void allowShake() {
		if (adapter!=null) {
			adapter.setBookButIsClick(false);
		}
		prepareAndPlay(shakeMusicName); // 准备播放
		allowShake = false;
		// mShakeListener.stop();
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

	/**
	 * 没有摇到会议室逻辑处理
	 */
	private void notShakeMeetingRoom() {

		// allowShake = true;
		// mShakeListener.start();
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

	/** 临界判断 */
	private boolean OneOrTwoHours() {
		Date newTime = DateFormatUtil.getServerTime(mContext);
		SimpleDateFormat df = new SimpleDateFormat("HH");
		int newTimeMinute = Integer.parseInt(df.format(newTime));
		int t = Integer.parseInt(time);
		if (20 > newTimeMinute && newTimeMinute >= 18) {

			if (20 - newTimeMinute < t) {

				EmeetingToast.show(mContext,getString(R.string.tv_findroom_do_filt_one)
						+ (20 - newTimeMinute) + getString(R.string.tv_findroom_do_filt_two));
				return false;
			}
			return true;

		}
		return true;

	}

}
