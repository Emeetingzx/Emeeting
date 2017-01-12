package cn.com.zte.emeeting.app.views.scan;

import java.io.IOException;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.android.resource.inflater.BaseLayoutInflater;
import cn.com.zte.android.widget.dialog.CustomDialog;
import cn.com.zte.emeeting.app.activity.MainActivity;
import cn.com.zte.emeeting.app.activity.ScanSuccessActivity;
import cn.com.zte.emeeting.app.appservice.CheckInService;
import cn.com.zte.emeeting.app.base.MyApplication;
import cn.com.zte.emeeting.app.base.activity.AppActivity;
import cn.com.zte.emeeting.app.base.fragment.AppFragment;
import cn.com.zte.emeeting.app.dialog.DialogLoadBar;
import cn.com.zte.emeeting.app.response.instrument.GetAttendanceOperationResponse;
import cn.com.zte.emeeting.app.util.EmeetingToast;
import cn.com.zte.emeeting.app.views.CustomToast;
import cn.com.zte.emeeting.app.views.TopBar;
import cn.com.zte.emeeting.app.views.scan.util.CameraManager;
import cn.com.zte.emeeting.app.views.scan.util.CaptureActivityHandler;
import cn.com.zte.emeeting.app.views.scan.util.InactivityTimer;
import cn.com.zte.emeeting.app.views.scan.util.ViewfinderView;
import cn.com.zte.mobileemeeting.R;
import roboguice.inject.InjectView;

/**
 * 扫一扫界面
 * 
 * @author 6396000419
 */
public class CaptureFragment extends AppFragment implements Callback {

	@InjectView(R.id.capture_topbar)
	private TopBar topBar;
	private CaptureActivityHandler handler;
	@InjectView(R.id.viewfinder_view)
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	// 界面逻辑操作
	private Context mContext;

	private boolean isProcess = false;
	@InjectView(R.id.preview_view)
	private SurfaceView surfaceView;

	private CheckInService checkInService = new CheckInService(mContext);

	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API. See
	 * https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	@Override
	protected View onCreateView(BaseLayoutInflater arg0, ViewGroup arg1,
			Bundle arg2) {
		super.onCreateView(arg0, arg1, arg2);
		mContext = getActivity();
		return arg0.inflate(R.layout.activity_capture, null);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		super.initData();
		// 初始化 CameraManager
		CameraManager.init(MyApplication.GetApp());
		hasSurface = false;
		inactivityTimer = new InactivityTimer((Activity) mContext);

		initTopbar();

	}

	/**
	 * 初始化顶部
	 */
	private void initTopbar() {
		topBar.setViewBackGround(TopBar.leftBtnLogo, R.drawable.icon_home_menu);
		topBar.setViewText(TopBar.titleLogo, getString(R.string.home_bot_scan));
		topBar.setViewOnClickListener(TopBar.leftBtnLogo,
				new TopBar.ButtonOnClick() {
					@Override
					public void onClick(View view) {
						// 返回菜单栏
						MainActivity mainActivity = (MainActivity) getActivity();
						mainActivity.showLeftView();
					}
				});
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if (hidden) {
			onHide();
		} else {
			onShow();
		}
	}

	/**
	 * 界面显示需要处理的逻辑
	 */
	private void onShow() {
		Log.d("TAG", "Scan show method");
		// SurfaceView surfaceView = (SurfaceView)
		// view.findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			// surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) mContext
				.getSystemService(Context.AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
	}
	
	private Handler restartHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			isProcess = false;
			if (handler!=null) {
				handler.restartPreviewAndDecode();
			}
		};
	};

	@Override
	public void onResume() {
		super.onResume();
		onShow();
	}

	/**
	 * 界面隐藏需要处理的逻辑
	 */
	private void onHide() {
		// TODO Auto-generated method stub
		Log.d("TAG", "Scan hide method");
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	public void onPause() {
		onHide();
		super.onPause();
	}

	@Override
	public void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	public void handleDecode(final Result obj, Bitmap barcode) {
		inactivityTimer.onActivity();
		if (isProcess) {
			return;
		}
		playBeepSoundAndVibrate();
        
        checkInService.CheckIn(mContext, null, obj.getText(), checkedHandler);
       
//        CustomToast.show(mContext, obj.getText());
        
//        Log.d("zl", obj.getText());
//        isProcess = true;
//		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//		if (barcode == null)
//		{
//			dialog.setIcon(null);
//		}
//		else
//		{
//
//			Drawable drawable = new BitmapDrawable(barcode);
//			dialog.setIcon(drawable);
//		}
//		dialog.setTitle("扫描结果");
//		dialog.setMessage(obj.getText());
//		dialog.setNegativeButton("确定", new DialogInterface.OnClickListener()
//		{
//			@Override
//			public void onClick(DialogInterface dialog, int which)
//			{
//				//用默认浏览器打开扫描得到的地址
//				Intent intent = new Intent();
//				intent.setAction("android.intent.action.VIEW");
//				Uri content_url = Uri.parse(obj.getText());
//				intent.setData(content_url);
//				startActivity(intent);
//				finish();
//			}
//		});
//		dialog.setPositiveButton("取消", new DialogInterface.OnClickListener()
//		{
//			@Override
//			public void onClick(DialogInterface dialog, int which)
//			{
//				finish();
//			}
//		});
//		dialog.create().show();
    }

	/**
	 * 扫码签到结果处理
	 */
	BaseAsyncHttpResponseHandler<GetAttendanceOperationResponse> checkedHandler = new BaseAsyncHttpResponseHandler<GetAttendanceOperationResponse>() {


		@Override
		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			super.onPopUpHttpErrorDialogPre(strTitle, strCode, strMsg);
			EmeetingToast.show(mContext, strMsg);
			restartHandler.sendEmptyMessageDelayed(0, 2000);
		}

		@Override
		public void onFailureTrans(
				GetAttendanceOperationResponse responseModelVO) {
			super.onFailureTrans(responseModelVO);
			Log.d("zl", "扫码失败信息,s=" + responseModelVO.getS() + "m="
					+ responseModelVO.getM());
			
			Intent intent = new Intent(getActivity(),ScanSuccessActivity.class);
			intent.putExtra("isSuccess", "0");
			intent.putExtra("checkedType", "1");
			intent.putExtra("ResultInfo", responseModelVO.getM());
			startActivity(intent);
			isProcess = false;
		}

		@Override
		public void onSuccessTrans(
				GetAttendanceOperationResponse responseModelVO) {
			super.onSuccessTrans(responseModelVO);
			Log.d("zl", "扫码签到成功");
			Intent intent = new Intent(getActivity(), ScanSuccessActivity.class);
			intent.putExtra("isSuccess", "1");
			intent.putExtra("checkedNum", responseModelVO.getM());
			intent.putExtra("checkedType", "1");
			startActivity(intent);
			isProcess = false;
		}

	};

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			((Activity) mContext)
					.setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) mContext
					.getSystemService(Context.VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

}