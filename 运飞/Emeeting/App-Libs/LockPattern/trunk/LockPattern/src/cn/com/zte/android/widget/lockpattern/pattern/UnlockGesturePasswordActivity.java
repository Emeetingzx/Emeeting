/******************************************************************************
 * Copyright (C) 2015 ZTE Co.,Ltd
 * All Rights Reserved.
 * 本软件为中兴通讯股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package cn.com.zte.android.widget.lockpattern.pattern;

import java.io.Serializable;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zte.android.widget.lockpattern.listener.ActionListener;
import cn.com.zte.android.widget.lockpattern.utils.GesturesLock;
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
	private LockPatternView mLockPatternView;
	private int mFailedPatternAttemptsSinceLastTimeout = 0;
	private CountDownTimer mCountdownTimer = null;
	private Handler mHandler = new Handler();
	private TextView mHeadTextView;
	private Animation mShakeAnim;
	private TextView forgetText;

	private Toast mToast;
	
	private Context mContext;

	private void showToast(CharSequence message) {
		if (null == mToast) {
			mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
			mToast.setGravity(Gravity.CENTER, 0, 0);
		} else {
			mToast.setText(message);
		}

		mToast.show();
	}

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gesturepassword_unlock);
		mContext = this;
		mLockPatternView = (LockPatternView) this
				.findViewById(R.id.gesturepwd_unlock_lockview);
		ImageView iv_titlePicture_unload = (ImageView) findViewById(R.id.gesturepwd_unlock_face);
		if(GesturesLock.titlePicture_unload!=null){
			iv_titlePicture_unload.setBackground(GesturesLock.titlePicture_unload);
		}
		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		if(GesturesLock.tv_title!=null){
			tv_title.setText(GesturesLock.tv_title);
		}
		mLockPatternView.setOnPatternListener(mChooseNewLockPatternListener);
		mLockPatternView.setTactileFeedbackEnabled(true);
		mHeadTextView = (TextView) findViewById(R.id.gesturepwd_unlock_text);
		mShakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake_x);
		forgetText = (TextView) findViewById(R.id.gesturepwd_unlock_forget);
		forgetText.setClickable(true);
		forgetText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(GesturesLock.forgetListener!=null){
					Dialog dialog = new AlertDialog.Builder(mContext)
					.setMessage("确认退出当前账号？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									GesturesLock.forgetListener.callback();
									GesturesLock.deleteListener = null;
									finish();
								}

							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									// 点击"取消"按钮
								}
							}).create();// 创建
			// 显示对话框
			dialog.show();
				}
			}
		});
	}

//	@Override
//	protected void onResume() {
//		super.onResume();
//
//		if (!LockPatternUtils.getInstance(getApplicationContext())
//				.savedPatternExists()) {
//			startActivity(new Intent(this, CreateGesturePasswordActivity.class));
//			finish();
//		}
//	}

	@Override
	public void onBackPressed() {
		// 不允许回退
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mCountdownTimer != null)
			mCountdownTimer.cancel();
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
			if (LockPatternUtils.getInstance(getApplicationContext())
					.checkPattern(pattern)) {
				mLockPatternView
						.setDisplayMode(LockPatternView.DisplayMode.Correct);
				// Intent intent = new
				// Intent(UnlockGesturePasswordActivity.this,
				// GuideGesturePasswordActivity.class);
				// // 打开新的Activity
				// startActivity(intent); 
				//showToast("解锁成功");
				GesturesLock.validate = true;
				if(GesturesLock.listener!=null){
					GesturesLock.listener.callback();
					GesturesLock.listener = null;
				}
				if(GesturesLock.deleteListener!=null){
					GesturesLock.deleteListener.callback();
					GesturesLock.deleteListener=null;
				}
				
//				if(GesturesLock.c!=null){
//					if(UnlockGesturePasswordActivity.class.equals(GesturesLock.c)){
//						if(!LockPatternUtils.getInstance(getApplicationContext()).savedPatternExists()){
//							Toast.makeText(getApplicationContext(), "手势密码删成功", 0).show();
//						}
//						finish();
//						return;
//					}
//				startActivity(new Intent(UnlockGesturePasswordActivity.this,GesturesLock.c));
//				GesturesLock.c = null;
//				}
				finish();
			} else {
				mLockPatternView
						.setDisplayMode(LockPatternView.DisplayMode.Wrong);
				if (pattern.size() >= LockPatternUtils.MIN_LOCK_PATTERN_SIZE) {
					mFailedPatternAttemptsSinceLastTimeout++;
					int retry = LockPatternUtils.FAILED_ATTEMPTS_BEFORE_TIMEOUT
							- mFailedPatternAttemptsSinceLastTimeout;
					if (retry >= 0) {
						if (retry == 0){
							showToast("您已"+LockPatternUtils.FAILED_ATTEMPTS_BEFORE_TIMEOUT+"次输错密码，请30秒后再试");
							forgetText.setClickable(false);
						}
						mHeadTextView.setText("密码错误，还可以再输入" + retry + "次");
						mHeadTextView.setTextColor(Color.RED);
						if(GesturesLock.isShake){
							mHeadTextView.startAnimation(mShakeAnim);
						}
					}

				} else {
					showToast("输入长度不够，请重试");
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
						mHeadTextView.setText(secondsRemaining + " 秒后重试");
					} else {
						mHeadTextView.setText("请绘制手势密码");
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
	

}
