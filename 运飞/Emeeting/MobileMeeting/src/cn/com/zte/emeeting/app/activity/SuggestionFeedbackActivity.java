package cn.com.zte.emeeting.app.activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.zte.emeeting.app.base.activity.AppActivity;
import roboguice.inject.InjectView;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zte.android.app.activity.BaseActivity;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.emeeting.app.appservice.WelComeService;
import cn.com.zte.emeeting.app.response.instrument.GetHelpFeedbackResponse;
import cn.com.zte.emeeting.app.util.DataConst;
import cn.com.zte.emeeting.app.util.EmeetingToast;
import cn.com.zte.emeeting.app.views.TopBar;
import cn.com.zte.emeeting.app.views.TopBar.ButtonOnClick;
import cn.com.zte.mobilebasedata.request.HttpUtil;
import cn.com.zte.mobileemeeting.R;

/**
 * 意见反馈界面
 * 
 * @author liu.huanbo
 * 
 */

public class SuggestionFeedbackActivity extends AppActivity implements
		OnClickListener {
	/** 上下文 */
	private Context mContext;
	/** 意见反馈Topbar */
	@InjectView(R.id.topbar_suggestion)
	private TopBar topbar_suggestion;
	/** 意见输入框 */
	@InjectView(R.id.edt_suggess)
	private EditText edt_suggess;
	/** 联系方式输入框 */
	@InjectView(R.id.edt_contact_way)
	private EditText edt_contact_way;
	/** 取消按钮 */
	@InjectView(R.id.tv_cancel)
	private TextView tv_cancel;
	/** 确定按钮 */
	@InjectView(R.id.tv_sure)
	private TextView tv_sure;

	@Override
	protected void initContentView() {
		super.initContentView();
		mContext = this;
		setContentView(R.layout.activity_suggestion_feedback);

	}

	@Override
	protected void initData() {
		super.initData();
	}

	@Override
	protected void initViewEvents() {
		super.initViewEvents();
		tv_cancel.setOnClickListener(this);
		tv_sure.setOnClickListener(this);
	}

	@Override
	protected void initViews() {
		super.initViews();
		topbar_suggestion
				.setViewBackGround(TopBar.leftBtnLogo, R.drawable.back);
		topbar_suggestion.HiddenView(TopBar.rightBtnLogo);
		topbar_suggestion.setViewText(TopBar.titleLogo,
				getString(R.string.tv_suggestion));
		topbar_suggestion.setViewTextColor(TopBar.titleLogo,
				getResourceColor(R.color.TEXTCOLOR_TOPBAR_TITLE_TEXT));
		topbar_suggestion.setViewOnClickListener(TopBar.leftBtnLogo,
				new ButtonOnClick() {

					@Override
					public void onClick(View view) {
						InputMethodManager inputManager = (InputMethodManager) mContext
								.getSystemService(Context.INPUT_METHOD_SERVICE);
						if (inputManager.isActive()) {
							inputManager.hideSoftInputFromWindow(
									tv_sure.getWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS);
						}
						finish();
					}
				});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_cancel:
			finish();
			break;
		case R.id.tv_sure:
			if (edt_suggess.getText().toString().isEmpty()) {
				EmeetingToast.show(mContext, getString(R.string.tv_suggesion_no));
				return;
			}
			if (!edt_contact_way.getText().toString().isEmpty()) {
				if (!isMobileNO(edt_contact_way.getText().toString())) {

					EmeetingToast.show(mContext, getString(R.string.tv_suggesion_phone_number));
					return;
				}
			}

			/** 调用接口数据传送到服务器 */
			new WelComeService(this)
					.HelpFeedback(handler, edt_suggess.getText().toString(),
							edt_contact_way.getText().toString());
			break;

		default:
			break;
		}

	}

	/**
	 * 是否是合法的手机号
	 * 
	 * @param context
	 * @return
	 */
	private boolean isMobileNO(String mobiles) {
		// Pattern p = Pattern
		// .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

		Pattern p = Pattern
				.compile("^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	BaseAsyncHttpResponseHandler<GetHelpFeedbackResponse> handler = new BaseAsyncHttpResponseHandler<GetHelpFeedbackResponse>(
			true) {

		public void onSuccessTrans(GetHelpFeedbackResponse responseModelVO) {

			if (responseModelVO != null) {

				Intent intent = new Intent(DataConst.ACTION_CLOSE);
				sendBroadcast(intent);
				finish();
			}

		};

		public void onFailureTrans(GetHelpFeedbackResponse responseModelVO) {

			if (responseModelVO != null && responseModelVO.getM() != null
					&& !responseModelVO.getM().equals("")) {
				Toast.makeText(mContext, responseModelVO.getM(),
						Toast.LENGTH_LONG).show();

			}
		};

		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {

			Toast.makeText(mContext, HttpUtil.SERVER_REQUEST_FAIL,
					Toast.LENGTH_LONG).show();
		};

	};
}
