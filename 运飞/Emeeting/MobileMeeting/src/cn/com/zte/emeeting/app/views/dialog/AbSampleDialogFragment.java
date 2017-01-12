package cn.com.zte.emeeting.app.views.dialog;

import android.animation.Animator;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
/**
 * 
* @ClassName: AbSampleDialogFragment
* @Description: TODO 描述：弹出框
* @author Pan.Jianbo
* @date 2015-8-5 下午2:50:47
*
 */
public class AbSampleDialogFragment extends DialogFragment {
	
	/** The m theme. */
	private int mTheme;
	
	/** The m style. */
	private int mStyle;
	
	/** The m content view. */
	private View mContentView;
	
	/** The m on cancel listener. */
	private DialogInterface.OnCancelListener mOnCancelListener = null;
	
	/** The m on dismiss listener. */
	private DialogInterface.OnDismissListener mOnDismissListener = null;
	
	/** The m gravity. */
	private int mGravity;

	/**
	 * New instance.
	 *
	 * @param style the style
	 * @param theme the theme
	 * @return the ab sample dialog fragment
	 */
	public static AbSampleDialogFragment newInstance(int style, int theme) {
		return newInstance(style,theme,Gravity.CENTER);
	}
	
	
	/**
	 * New instance.
	 *
	 * @param style the style
	 * @param theme the theme
	 * @param gravity the gravity
	 * @return the ab sample dialog fragment
	 */
	public static AbSampleDialogFragment newInstance(int style, int theme,int gravity) {
		AbSampleDialogFragment f = new AbSampleDialogFragment();

		// Supply style input as an argument.
		Bundle args = new Bundle();
		args.putInt("style", style);
		args.putInt("theme", theme);
		args.putInt("gravity", gravity);
		f.setArguments(args);

		return f;
	}

	/* (non-Javadoc)
	 * @see android.app.DialogFragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mStyle = getArguments().getInt("style");
		mTheme = getArguments().getInt("theme");
		mGravity = getArguments().getInt("gravity");
		setStyle(mStyle, mTheme);
	}
	
	/* (non-Javadoc)
	 * @see android.app.DialogFragment#onCancel(android.content.DialogInterface)
	 */
	@Override
	public void onCancel(DialogInterface dialog) {
		// 用户中断
		if(mOnCancelListener != null){
			mOnCancelListener.onCancel(dialog);
		}
		
		super.onCancel(dialog);
	}

	/* (non-Javadoc)
	 * @see android.app.DialogFragment#onDismiss(android.content.DialogInterface)
	 */
	@Override
	public void onDismiss(DialogInterface dialog) {
		// 用户隐藏
		if(mOnDismissListener != null){
		    mOnDismissListener.onDismiss(dialog);
		}
		super.onDismiss(dialog);
	}

	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return mContentView;
	}
	
	/* (non-Javadoc)
	 * @see android.app.DialogFragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Window window = getDialog().getWindow();
		WindowManager.LayoutParams attributes = window.getAttributes();
		attributes.gravity = mGravity;
		window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#88838B8B")));
		super.onActivityCreated(savedInstanceState);
	}

	/**
	 * Gets the content view.
	 *
	 * @return the content view
	 */
	public View getContentView() {
		return mContentView;
	}

	/**
	 * Sets the content view.
	 *
	 * @param mContentView the new content view
	 */
	public void setContentView(View mContentView) {
		this.mContentView = mContentView;
	}

	/**
	 * Gets the on cancel listener.
	 *
	 * @return the on cancel listener
	 */
	public DialogInterface.OnCancelListener getOnCancelListener() {
		return mOnCancelListener;
	}

	/**
	 * Sets the on cancel listener.
	 *
	 * @param onCancelListener the new on cancel listener
	 */
	public void setOnCancelListener(
			DialogInterface.OnCancelListener onCancelListener) {
		this.mOnCancelListener = onCancelListener;
	}

	/**
	 * Gets the on dismiss listener.
	 *
	 * @return the on dismiss listener
	 */
	public DialogInterface.OnDismissListener getOnDismissListener() {
		return mOnDismissListener;
	}

	/**
	 * Sets the on dismiss listener.
	 *
	 * @param onDismissListener the new on dismiss listener
	 */
	public void setOnDismissListener(
			DialogInterface.OnDismissListener onDismissListener) {
		this.mOnDismissListener = onDismissListener;
	}
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateAnimator(int, boolean, int)
	 */
	@Override
	public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
		return super.onCreateAnimator(transit, enter, nextAnim);
	}
	
}
