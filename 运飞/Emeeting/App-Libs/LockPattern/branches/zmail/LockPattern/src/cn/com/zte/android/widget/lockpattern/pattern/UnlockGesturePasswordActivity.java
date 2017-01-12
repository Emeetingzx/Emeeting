/******************************************************************************
 * Copyright (C) 2015 ZTE Co.,Ltd
 * All Rights Reserved.
 * 本软件为中兴通讯股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package cn.com.zte.android.widget.lockpattern.pattern;

import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zte.android.widget.lockpattern.listener.ActionListener;
import cn.com.zte.android.widget.lockpattern.utils.GesturesLock;
import cn.com.zte.android.widget.lockpattern.view.ExitDialog;
import cn.com.zte.android.widget.lockpattern.view.ExitDialog.BtnOnclick;
import cn.com.zte.android.widget.lockpattern.view.LockPatternUtils;
import cn.com.zte.android.widget.lockpattern.view.LockPatternView;
import cn.com.zte.android.widget.lockpattern.view.LockPatternView.Cell;
import cn.com.zte.android.widget.pattern.R;

/**
 * Unlock. <br/>
 * 日期: 2015-2-9 下午4:37:23 <br/>
 * 
 * @author wangenzi
 * @version 1.0
 * @since JDK 1.6
 * @history 2015-2-9 wangenzi 新建
 */
public class UnlockGesturePasswordActivity extends Activity {
	/**
	 * 没有头像时显示拼音的字体大小
	 */
	private static final int TEXTSIZE = 25;
	
	private LockPatternView mLockPatternView;
	private int mFailedPatternAttemptsSinceLastTimeout = 0;
	private CountDownTimer mCountdownTimer = null;
	private Handler mHandler = new Handler();
	private TextView mHeadTextView;
	private Animation mShakeAnim;
	private TextView forgetText;
	private TextView otherView;

	/**
	 * 顶部导航栏
	 */
	private RelativeLayout topbarLayout;
	/**
	 * 返回按钮
	 */
	private ImageView backView;
	private Toast mToast;

	private Context mContext;
	
	private String lockFileName = "" ;
	
	private Receiver receiver;

	private void showToast(CharSequence message) {
		if (null == mToast) {
			mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
			mToast.setGravity(Gravity.CENTER, 0, 0);
		} else {
			mToast.setText(message);
		}

		mToast.show();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gesturepassword_unlock);
		mContext = this;
		lockFileName = getIntent().getStringExtra(GesturesLock.LOCK_FILE_NAME);
		otherView = (TextView) findViewById(R.id.gesturepwd_unlock_other);
		if (GesturesLock.isHideOther) {
			otherView.setVisibility(View.GONE);
		}
		
		topbarLayout = (RelativeLayout) findViewById(R.id.unlock_gesture_topbar);
		backView = (ImageView) findViewById(R.id.unlock_gesture_back);
		mLockPatternView = (LockPatternView) this
				.findViewById(R.id.gesturepwd_unlock_lockview);
//		RoundImageLayoutLock iv_titlePicture_unload = (cn.com.zte.android.widget.lockpattern.view.RoundImageLayoutLock) findViewById(R.id.gesturepwd_unlock_face);
//		if (GesturesLock.userEntity != null) {
//			iv_titlePicture_unload
//					.setContact(GesturesLock.userEntity, TEXTSIZE);
//		}
		// TextView tv_title = (TextView) findViewById(R.id.tv_title);
		mLockPatternView.setOnPatternListener(mChooseNewLockPatternListener);
		mLockPatternView.setTactileFeedbackEnabled(false);
		mLockPatternView.setInStealthMode(GesturesLock.getInstance()
				.isHidePath(mContext));

		mHeadTextView = (TextView) findViewById(R.id.gesturepwd_unlock_text);
		if (GesturesLock.tv_title != null) {
			mHeadTextView.setText(GesturesLock.tv_title);
		}
		mShakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake_x);
		forgetText = (TextView) findViewById(R.id.gesturepwd_unlock_forget);
		forgetText.setClickable(true);
		otherView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (GesturesLock.otherListener != null) {
					final ExitDialog dialog = new ExitDialog(mContext, 2);
					dialog.setContentText(getString(R.string.confirm_log_off_the_current_account));
					dialog.setSureBtnText(getString(R.string.dialog_comfirm));
					dialog.setCancelBtnText(getString(R.string.dialog_cancel));
					dialog.setCancelable(true);
					dialog.setSureBtnClicklistener(new BtnOnclick() {

						@Override
						public void btnClick(View view) {
							GesturesLock.otherListener.callback(ActionListener.FAIL);
							GesturesLock.deleteListener = null;
							finish();
						}
					});
					dialog.setCancelBtnClicklistener(new BtnOnclick() {

						@Override
						public void btnClick(View view) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				if (GesturesLock.otherListener != null) 
				{
					GesturesLock.otherListener.callback(ActionListener.FAIL);
//					finish();
//					final ExitDialog dialog = new ExitDialog(mContext, 2);
//					dialog.setContentText("确认注销当前账号？");
//					dialog.setSureBtnText("确定");
//					dialog.setCancelBtnText("取消");
//					dialog.setCancelable(true);
//					dialog.setSureBtnClicklistener(new BtnOnclick() {
//
//						@Override
//						public void btnClick(View view) {
//							//清除手势密码
//							LockPatternUtils lockPatternUtils = new LockPatternUtils(mContext);
//							lockPatternUtils.clearLock();
//							Log.e("temp", "清除手势密码");
//							GesturesLock.otherListener.callback(ActionListener.FAIL);
//							GesturesLock.deleteListener = null;
//							finish();
//						}
//					});
//					dialog.setCancelBtnClicklistener(new BtnOnclick() {
//
//						@Override
//						public void btnClick(View view) {
//							// TODO Auto-generated method stub
//							dialog.dismiss();
//						}
//					});
//					dialog.show();

				}
			}
		  }
		});
		forgetText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (GesturesLock.forgetListener != null) {

					final ExitDialog dialog = new ExitDialog(mContext, 2);
					dialog.setContentText(getString(R.string.forght_gesture_password_need_to_re_sign));
					dialog.setSureBtnText(getString(R.string.log_in_again));
					dialog.setCancelBtnText(getString(R.string.dialog_cancel));
					dialog.setCancelable(true);
					dialog.setSureBtnClicklistener(new BtnOnclick() {

						@Override
						public void btnClick(View view) {
							//清除手势密码
							LockPatternUtils lockPatternUtils = new LockPatternUtils(mContext, lockFileName);
							lockPatternUtils.clearLock();
							GesturesLock.forgetListener.callback(ActionListener.SUCCESS);
							GesturesLock.deleteListener = null;
							finish();
						}
					});
					dialog.setCancelBtnClicklistener(new BtnOnclick() {

						@Override
						public void btnClick(View view) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
		});

		if (GesturesLock.isHideTop) {
			topbarLayout.setVisibility(View.INVISIBLE);
		} else {
			topbarLayout.setVisibility(View.VISIBLE);
		}
		backView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (GesturesLock.listener != null) {
					GesturesLock.listener.callback(ActionListener.BACK);
				}
				finish();
			}
		});
		
		receiver = new Receiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("cn.com.zte.mobilemail.loginsuccess");
		filter.addCategory("loginsuccess");
		registerReceiver(receiver, filter);
	}

	// @Override
	// protected void onResume() {
	// super.onResume();
	//
	// if (!LockPatternUtils.getInstance(getApplicationContext())
	// .savedPatternExists()) {
	// startActivity(new Intent(this, CreateGesturePasswordActivity.class));
	// finish();
	// }
	// }

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (!GesturesLock.isBack) {
			return false;
		}
		if (keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN) {
			if (GesturesLock.listener != null) {
				Log.d("TAG", "调用返回事件"+ActionListener.BACK);
				GesturesLock.listener.callback(ActionListener.BACK);
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mCountdownTimer != null){
			mCountdownTimer.cancel();
		}
		try {
			if(receiver != null){
				unregisterReceiver(receiver);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Runnable mClearPatternRunnable = new Runnable() {
		public void run() {
			mLockPatternView.clearPattern();
		}
	};

	protected LockPatternView.OnPatternListener mChooseNewLockPatternListener = new LockPatternView.OnPatternListener() {

		public void onPatternStart() {
			mLockPatternView.removeCallbacks(mClearPatternRunnable);
			patternInProgress();
		}

		public void onPatternCleared() {
			mLockPatternView.removeCallbacks(mClearPatternRunnable);
		}

		public void onPatternDetected(List<LockPatternView.Cell> pattern) {
			if (pattern == null)
				return;
			if (new LockPatternUtils(getApplicationContext(),lockFileName)
					.checkPattern(pattern)) {
				mLockPatternView
						.setDisplayMode(LockPatternView.DisplayMode.Correct);
				GesturesLock.validate = true;
				if (GesturesLock.listener != null) {
					GesturesLock.listener.callback(ActionListener.SUCCESS);
					GesturesLock.listener = null;
				}
				if (GesturesLock.deleteListener != null) {
					GesturesLock.deleteListener.callback(ActionListener.SUCCESS);
					GesturesLock.deleteListener = null;
				}

				// if(GesturesLock.c!=null){
				// if(UnlockGesturePasswordActivity.class.equals(GesturesLock.c)){
				// if(!LockPatternUtils.getInstance(getApplicationContext()).savedPatternExists()){
				// Toast.makeText(getApplicationContext(), "手势密码删成功", 0).show();
				// }
				// finish();
				// return;
				// }
				// startActivity(new
				// Intent(UnlockGesturePasswordActivity.this,GesturesLock.c));
				// GesturesLock.c = null;
				// }
				finish();
			} else {
				mLockPatternView
						.setDisplayMode(LockPatternView.DisplayMode.Wrong);
				if (pattern.size() >= LockPatternUtils.MIN_LOCK_PATTERN_SIZE) {
					mFailedPatternAttemptsSinceLastTimeout++;
					int retry = LockPatternUtils.FAILED_ATTEMPTS_BEFORE_TIMEOUT
							- mFailedPatternAttemptsSinceLastTimeout;
					if (retry >= 0) {
						if (retry == 0) {
							Log.d("TAG",
									"您已"
											+ LockPatternUtils.FAILED_ATTEMPTS_BEFORE_TIMEOUT
											+ "次输错密码");
							forgetText.setClickable(false);
							GesturesLock.listener.callback(ActionListener.FAIL);
							finish();
							return;
						}
						mHeadTextView.setText(getString(R.string.wrong_password_you_can_enter_again)+" " + retry + " "+getString(R.string.secondary));
						mHeadTextView.setTextColor(Color.RED);
						if (GesturesLock.isShake) {
							mHeadTextView.startAnimation(mShakeAnim);
						}
					}

				} else {
					mHeadTextView.setText(getString(R.string.lockpattern_recording_incorrect_too_short));
					mHeadTextView.setTextColor(Color.RED);
				}

				if (mFailedPatternAttemptsSinceLastTimeout >= LockPatternUtils.FAILED_ATTEMPTS_BEFORE_TIMEOUT) {
					mHandler.postDelayed(attemptLockout, 2000);
				} else {
					mLockPatternView.postDelayed(mClearPatternRunnable, 2000);
				}
			}
		}

		public void onPatternCellAdded(List<Cell> pattern) {

		}

		private void patternInProgress() {
		}
	};
	Runnable attemptLockout = new Runnable() {

		@Override
		public void run() {
			mLockPatternView.clearPattern();
			mLockPatternView.setEnabled(false);
			mCountdownTimer = new CountDownTimer(
					LockPatternUtils.FAILED_ATTEMPT_TIMEOUT_MS + 1, 1000) {

				@Override
				public void onTick(long millisUntilFinished) {
					int secondsRemaining = (int) (millisUntilFinished / 1000) - 1;
					if (secondsRemaining > 0) {
						mHeadTextView.setText(secondsRemaining + getString(R.string.seconds_retry));
					} else {
						mHeadTextView.setText(getString(R.string.please_lockscreen_access_pattern));
						forgetText.setClickable(true);
						mHeadTextView.setTextColor(Color.WHITE);
					}

				}

				@Override
				public void onFinish() {
					mLockPatternView.setEnabled(true);
					mFailedPatternAttemptsSinceLastTimeout = 0;
				}
			}.start();
		}
	};

	
	class Receiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			UnlockGesturePasswordActivity.this.finish();
		}
	}
	
}
