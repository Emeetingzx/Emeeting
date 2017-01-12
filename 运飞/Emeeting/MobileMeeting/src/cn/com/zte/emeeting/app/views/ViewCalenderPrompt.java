package cn.com.zte.emeeting.app.views;

import cn.com.zte.mobileemeeting.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 日历总共会议提醒
 * 
 * @author LangK
 */
public class ViewCalenderPrompt extends LinearLayout {

	/**
	 * 月份
	 */
	private TextView monthView;

	/**
	 * 次数
	 */
	private TextView numberView;

	/**
	 * 关闭
	 */
	private ImageView closeView;

	/**
	 * 该类构造方法:
	 */
	public ViewCalenderPrompt(Context context) {
		super(context);
		initWin(context);
	}

	public ViewCalenderPrompt(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initWin(context);
	}

	public ViewCalenderPrompt(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initWin(context);
	}

	/**
	 * 该方法用于:初始化
	 * 
	 * @param @param context
	 * @return void
	 */
	private void initWin(Context context) {
		View contentView = LayoutInflater.from(context).inflate(
				R.layout.view_pupo_calenderprompt, null);
		monthView = (TextView) contentView
				.findViewById(R.id.view_pupo_prompt_text_month);
		numberView = (TextView) contentView
				.findViewById(R.id.view_pupo_prompt_text_number);
		closeView = (ImageView) contentView
				.findViewById(R.id.view_pupo_prompt_image_close);
		addView(contentView);

		closeView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setVisibility(View.GONE);
			}
		});
	}

	/**
	 * 设置月份
	 * 
	 * @param textString
	 *            比如 8月-9月
	 */
	public void setMonthText(String textString) {
		monthView.setText(textString);
	}

	/**
	 * 设置月份
	 * 
	 * @param textString
	 *            比如 5
	 */
	public void setNumberText(String textString) {
		numberView.setText(textString);
	}

}
