/******************************************************************************
 * Copyright (C) 2015 ZTE Co.,Ltd
 * All Rights Reserved.
 * 本软件为中兴通讯股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package cn.com.zte.android.widget.lockpattern.pattern;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zte.android.widget.lockpattern.listener.ActionListener;
import cn.com.zte.android.widget.lockpattern.utils.GesturesLock;
import cn.com.zte.android.widget.lockpattern.view.LockPatternUtils;
import cn.com.zte.android.widget.lockpattern.view.LockPatternView;
import cn.com.zte.android.widget.lockpattern.view.LockPatternView.Cell;
import cn.com.zte.android.widget.lockpattern.view.LockPatternView.DisplayMode;
import cn.com.zte.android.widget.pattern.R;

/**
 * Create. <br/>
 * 日期: 2015-2-9 下午4:37:15 <br/>
 * 
 * @author wangenzi
 * @version 1.0
 * @since JDK 1.6
 * @history 2015-2-9 wangenzi 新建
 */
public class CreateGesturePasswordActivity extends Activity {
	private static final int ID_EMPTY_MESSAGE = -1;
	private static final String KEY_UI_STAGE = "uiStage";
	private static final String KEY_PATTERN_CHOICE = "chosenPattern";
	private LockPatternView mLockPatternView;
	private Button mFooterButton;
	private String lockFileName = "";
	/**
	 * 顶部导航栏
	 */
	private RelativeLayout topbarLayout;
	/**
	 * 返回按钮
	 */
	private ImageView backView;
	protected TextView mHeaderText;
	protected List<LockPatternView.Cell> mChosenPattern = null;
	private Toast mToast;
	private Stage mUiStage = Stage.Introduction;
	private View mPreviewViews[][] = new View[3][3];
	/**
	 * The patten used during the help screen to show how to draw a pattern.
	 */
	private final List<LockPatternView.Cell> mAnimatePattern = new ArrayList<LockPatternView.Cell>();

	/**
	 * The states of the left footer button.
	 */
	enum LeftButtonMode {
		Cancel(android.R.string.cancel, true), CancelDisabled(
				android.R.string.cancel, false), Retry(
				R.string.lockpattern_retry_button_text, true), RetryDisabled(
				R.string.lockpattern_retry_button_text, false), Gone(
				ID_EMPTY_MESSAGE, false);

		/**
		 * @param text
		 *            The displayed text for this mode.
		 * @param enabled
		 *            Whether the button should be enabled.
		 */
		LeftButtonMode(int text, boolean enabled) {
			this.text = text;
			this.enabled = enabled;
		}

		final int text;
		final boolean enabled;
	}

	/**
	 * The states of the right button.
	 */
	enum RightButtonMode {
		Continue(R.string.lockpattern_continue_button_text, true), ContinueDisabled(
				R.string.lockpattern_continue_button_text, false), Confirm(
				R.string.lockpattern_confirm_button_text, true), ConfirmDisabled(
				R.string.lockpattern_confirm_button_text, false), Ok(
				android.R.string.ok, true);

		/**
		 * @param text
		 *            The displayed text for this mode.
		 * @param enabled
		 *            Whether the button should be enabled.
		 */
		RightButtonMode(int text, boolean enabled) {
			this.text = text;
			this.enabled = enabled;
		}

		final int text;
		final boolean enabled;
	}

	/**
	 * Keep track internally of where the user is in choosing a pattern.
	 */
	protected enum Stage {

		Introduction(R.string.lockpattern_recording_intro_header,
				LeftButtonMode.Cancel, RightButtonMode.ContinueDisabled,
				ID_EMPTY_MESSAGE, true), HelpScreen(
				R.string.lockpattern_settings_help_how_to_record,
				LeftButtonMode.Gone, RightButtonMode.Ok, ID_EMPTY_MESSAGE,
				false), ChoiceTooShort(
				R.string.lockpattern_recording_incorrect_too_short,
				LeftButtonMode.Retry, RightButtonMode.ContinueDisabled,
				ID_EMPTY_MESSAGE, true), FirstChoiceValid(
				R.string.lockpattern_pattern_entered_header,
				LeftButtonMode.Retry, RightButtonMode.Continue,
				ID_EMPTY_MESSAGE, false), NeedToConfirm(
				R.string.lockpattern_need_to_confirm, LeftButtonMode.Cancel,
				RightButtonMode.ConfirmDisabled, ID_EMPTY_MESSAGE, true), ConfirmWrong(
				R.string.lockpattern_need_to_unlock_wrong,
				LeftButtonMode.Cancel, RightButtonMode.ConfirmDisabled,
				ID_EMPTY_MESSAGE, true), ChoiceConfirmed(
				R.string.lockpattern_pattern_confirmed_header,
				LeftButtonMode.Cancel, RightButtonMode.Confirm,
				ID_EMPTY_MESSAGE, false);

		/**
		 * @param headerMessage
		 *            The message displayed at the top.
		 * @param leftMode
		 *            The mode of the left button.
		 * @param rightMode
		 *            The mode of the right button.
		 * @param footerMessage
		 *            The footer message.
		 * @param patternEnabled
		 *            Whether the pattern widget is enabled.
		 */
		Stage(int headerMessage, LeftButtonMode leftMode,
				RightButtonMode rightMode, int footerMessage,
				boolean patternEnabled) {
			this.headerMessage = headerMessage;
			this.leftMode = leftMode;
			this.rightMode = rightMode;
			this.footerMessage = footerMessage;
			this.patternEnabled = patternEnabled;
		}

		final int headerMessage;
		final LeftButtonMode leftMode;
		final RightButtonMode rightMode;
		final int footerMessage;
		final boolean patternEnabled;
	}

	private void showToast(CharSequence message) {
		if (null == mToast) {
			mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(message);
		}

		mToast.show();
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gesturepassword_create);
		SharedPreferences sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);
		boolean isFirstCreate = sp.getBoolean("isFirstCreate", true);
		lockFileName = getIntent().getStringExtra(GesturesLock.LOCK_FILE_NAME);
		// 初始化演示动画
		// mAnimatePattern.add(LockPatternView.Cell.of(0, 0));
		// mAnimatePattern.add(LockPatternView.Cell.of(0, 1));
		// mAnimatePattern.add(LockPatternView.Cell.of(1, 1));
		// mAnimatePattern.add(LockPatternView.Cell.of(2, 1));
		// mAnimatePattern.add(LockPatternView.Cell.of(2, 2));

		topbarLayout = (RelativeLayout) findViewById(R.id.create_gesture_topbar);
		backView = (ImageView) findViewById(R.id.create_gesture_back);
		mLockPatternView = (LockPatternView) this
				.findViewById(R.id.gesturepwd_create_lockview);
		mHeaderText = (TextView) findViewById(R.id.gesturepwd_create_text);
		mLockPatternView.setOnPatternListener(mChooseNewLockPatternListener);
		mLockPatternView.setTactileFeedbackEnabled(false);

		if (GesturesLock.isHideTop) {
			topbarLayout.setVisibility(View.INVISIBLE);
		} else {
			topbarLayout.setVisibility(View.VISIBLE);
		}
		backView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (GesturesLock.clistener != null) {
					GesturesLock.clistener.callback(ActionListener.BACK);
					GesturesLock.clistener = null;
				}
				finish();
			}
		});

		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		// if(GesturesLock.tv_title!=null){
		// tv_title.setText(GesturesLock.tv_title);
		// }

		mFooterButton = (Button) this
				.findViewById(R.id.gesturepwd_create_bot_but);
		mFooterButton.setVisibility(View.INVISIBLE);
		mFooterButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mChosenPattern = null;
				mFooterButton.setVisibility(View.INVISIBLE);
				mLockPatternView.clearPattern();
				updateStage(Stage.Introduction);
				initPreviewViews();
			}
		});
		// mFooterButton.setText(R.string.gesture_password_guide_creat_btn);
		initPreviewViews();
		if (savedInstanceState == null) {
			mLockPatternView.clearPattern();
			mLockPatternView.setDisplayMode(DisplayMode.Correct);
			updateStage(Stage.Introduction);
		} else {
			// restore from previous state
			final String patternString = savedInstanceState
					.getString(KEY_PATTERN_CHOICE);
			if (patternString != null) {
				mChosenPattern = LockPatternUtils
						.stringToPattern(patternString);
			}
			updateStage(Stage.values()[savedInstanceState.getInt(KEY_UI_STAGE)]);
		}

	}

	private void initPreviewViews() {
		mPreviewViews = new View[3][3];
		mPreviewViews[0][0] = findViewById(R.id.gesturepwd_setting_preview_0);
		mPreviewViews[0][1] = findViewById(R.id.gesturepwd_setting_preview_1);
		mPreviewViews[0][2] = findViewById(R.id.gesturepwd_setting_preview_2);
		mPreviewViews[1][0] = findViewById(R.id.gesturepwd_setting_preview_3);
		mPreviewViews[1][1] = findViewById(R.id.gesturepwd_setting_preview_4);
		mPreviewViews[1][2] = findViewById(R.id.gesturepwd_setting_preview_5);
		mPreviewViews[2][0] = findViewById(R.id.gesturepwd_setting_preview_6);
		mPreviewViews[2][1] = findViewById(R.id.gesturepwd_setting_preview_7);
		mPreviewViews[2][2] = findViewById(R.id.gesturepwd_setting_preview_8);

		mPreviewViews[0][0].setBackgroundResource(R.drawable.trans);
		mPreviewViews[0][1].setBackgroundResource(R.drawable.trans);
		mPreviewViews[0][2].setBackgroundResource(R.drawable.trans);
		mPreviewViews[1][0].setBackgroundResource(R.drawable.trans);
		mPreviewViews[1][1].setBackgroundResource(R.drawable.trans);
		mPreviewViews[1][2].setBackgroundResource(R.drawable.trans);
		mPreviewViews[2][0].setBackgroundResource(R.drawable.trans);
		mPreviewViews[2][1].setBackgroundResource(R.drawable.trans);
		mPreviewViews[2][2].setBackgroundResource(R.drawable.trans);
	}

	private void updatePreviewViews() {
		if (mChosenPattern == null)
			return;
		Log.i("way", "result = " + mChosenPattern.toString());
		for (LockPatternView.Cell cell : mChosenPattern) {
			Log.i("way", "cell.getRow() = " + cell.getRow()
					+ ", cell.getColumn() = " + cell.getColumn());
			mPreviewViews[cell.getRow()][cell.getColumn()]
					.setBackgroundResource(R.drawable.gesture_create_grid_selected);

		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(KEY_UI_STAGE, mUiStage.ordinal());
		if (mChosenPattern != null) {
			outState.putString(KEY_PATTERN_CHOICE,
					LockPatternUtils.patternToString(mChosenPattern));
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			Log.e("xzg", "执行KEYCODE_BACK");
			if (GesturesLock.clistener != null) {
				Log.e("xzg", "执行callback--ActionListener.BACK");
				GesturesLock.clistener.callback(ActionListener.BACK);
				GesturesLock.clistener = null;
			}
			finish();
			// if (mUiStage == Stage.HelpScreen) {
			// updateStage(Stage.Introduction);
			// return true;
			// }
			// System.exit(0);
			return true;
		}
		if (keyCode == KeyEvent.KEYCODE_MENU && mUiStage == Stage.Introduction) {
			Log.e("xzg", "执行KEYCODE_MENU");
			// updateStage(Stage.HelpScreen);
			return true;
		}
		return false;
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
			// Log.i("way", "result = " + pattern.toString());
			if (mUiStage == Stage.NeedToConfirm
					|| mUiStage == Stage.ConfirmWrong) {
				if (mChosenPattern == null)
					throw new IllegalStateException(
							"null chosen pattern in stage 'need to confirm");
				if (mChosenPattern.equals(pattern)) {
					updateStage(Stage.ChoiceConfirmed);
					saveChosenPatternAndFinish();
				} else {
					updateStage(Stage.ConfirmWrong);
				}
			} else if (mUiStage == Stage.Introduction
					|| mUiStage == Stage.ChoiceTooShort) {
				if (pattern.size() < LockPatternUtils.MIN_LOCK_PATTERN_SIZE) {
					updateStage(Stage.ChoiceTooShort);
				} else {
					mChosenPattern = new ArrayList<LockPatternView.Cell>(
							pattern);
					updateStage(Stage.FirstChoiceValid);
					updateStage(Stage.NeedToConfirm);
				}
			} else {
				throw new IllegalStateException("Unexpected stage " + mUiStage
						+ " when " + "entering the pattern.");
			}
		}

		public void onPatternCellAdded(List<Cell> pattern) {

		}

		private void patternInProgress() {
			mHeaderText.setText(R.string.lockpattern_recording_inprogress);
			mHeaderText.setTextColor(Color.WHITE);
			// mFooterLeftButton.setEnabled(false);
			// mFooterRightButton.setEnabled(false);

		}
	};

	private void updateStage(Stage stage) {
		mUiStage = stage;
		if (stage == Stage.ChoiceTooShort) {
			mHeaderText.setText(getResources().getString(stage.headerMessage,
					LockPatternUtils.MIN_LOCK_PATTERN_SIZE));
			mHeaderText.setTextColor(Color.RED);
		} else if (stage == Stage.ConfirmWrong) {
			mHeaderText.setText(stage.headerMessage);
			mHeaderText.setTextColor(Color.RED);
		} else {
			mHeaderText.setText(stage.headerMessage);
			mHeaderText.setTextColor(Color.WHITE);
		}

		// same for whether the patten is enabled
		if (stage.patternEnabled) {
			mLockPatternView.enableInput();
		} else {
			mLockPatternView.disableInput();
		}

		mLockPatternView.setDisplayMode(DisplayMode.Correct);

		switch (mUiStage) {
		case Introduction:
			mLockPatternView.clearPattern();
			Log.d("TAG", "Introduction");
			break;
		case HelpScreen:
			mLockPatternView.setPattern(DisplayMode.Animate, mAnimatePattern);
			Log.d("TAG", "HelpScreen");
			break;
		case ChoiceTooShort:
			mLockPatternView.setDisplayMode(DisplayMode.Wrong);
			postClearPatternRunnable();
			Log.d("TAG", "ChoiceTooShort");
			break;
		case FirstChoiceValid:
			Log.d("TAG", "FirstChoiceValid");
			mFooterButton.setVisibility(View.VISIBLE);
			break;
		case NeedToConfirm:
			mLockPatternView.clearPattern();
			updatePreviewViews();
			Log.d("TAG", "NeedToConfirm");
			break;
		case ConfirmWrong:
			mLockPatternView.setDisplayMode(DisplayMode.Wrong);
			postClearPatternRunnable();
			Log.d("TAG", "ConfirmWrong");
			break;
		case ChoiceConfirmed:
			Log.d("TAG", "ChoiceConfirmed");

			break;
		}

	}

	// clear the wrong pattern unless they have started a new one
	// already
	private void postClearPatternRunnable() {
		mLockPatternView.removeCallbacks(mClearPatternRunnable);
		mLockPatternView.postDelayed(mClearPatternRunnable, 2000);
	}

	// @Override
	// public void onClick(View v) {
	//
	// int i = v.getId();
	//
	// if(i == R.id.reset_btn){
	// if (mUiStage.leftMode == LeftButtonMode.Retry) {
	// mChosenPattern = null;
	// mLockPatternView.clearPattern();
	// updateStage(Stage.Introduction);
	// } else if (mUiStage.leftMode == LeftButtonMode.Cancel) {
	// if(GesturesLock.hasLock){
	// LockPatternUtils.getInstance(getApplicationContext()).setSavedPatternExists(true);
	// GesturesLock.hasLock = false;
	// }
	// finish();
	// } else {
	// throw new IllegalStateException(
	// "left footer button pressed, but stage of " + mUiStage
	// + " doesn't make sense");
	// }
	//
	// }else if(i == R.id.right_btn){
	// if (mUiStage.rightMode == RightButtonMode.Continue) {
	// if (mUiStage != Stage.FirstChoiceValid) {
	// throw new IllegalStateException("expected ui stage "
	// + Stage.FirstChoiceValid + " when button is "
	// + RightButtonMode.Continue);
	// }
	// updateStage(Stage.NeedToConfirm);
	// } else if (mUiStage.rightMode == RightButtonMode.Confirm) {
	// if (mUiStage != Stage.ChoiceConfirmed) {
	// throw new IllegalStateException("expected ui stage "
	// + Stage.ChoiceConfirmed + " when button is "
	// + RightButtonMode.Confirm);
	// }
	// saveChosenPatternAndFinish();
	// } else if (mUiStage.rightMode == RightButtonMode.Ok) {
	// if (mUiStage != Stage.HelpScreen) {
	// throw new IllegalStateException(
	// "Help screen is only mode with ok button, but "
	// + "stage is " + mUiStage);
	// }
	// mLockPatternView.clearPattern();
	// mLockPatternView.setDisplayMode(DisplayMode.Correct);
	// updateStage(Stage.Introduction);
	// if(true){
	// /* 设置手势密码演示完毕，隐藏多余按钮*/
	// mFooterRightButton.setVisibility(View.GONE);
	// mFooterLeftButton.setVisibility(View.GONE);
	// }
	// }
	// }
	// }

	private void saveChosenPatternAndFinish() {
		new LockPatternUtils(getApplicationContext(),lockFileName).saveLockPattern(
				mChosenPattern);
		// showToast("密码设置成功");
		if (GesturesLock.clistener != null) {
			GesturesLock.clistener.callback(ActionListener.SUCCESS);
			GesturesLock.clistener = null;
		}
		// startActivity(new Intent(this, UnlockGesturePasswordActivity.class));
		finish();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
