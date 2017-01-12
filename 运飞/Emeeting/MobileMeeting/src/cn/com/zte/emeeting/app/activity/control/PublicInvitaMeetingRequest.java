package cn.com.zte.emeeting.app.activity.control;

import com.ab.util.AbAppUtil;
import com.ab.util.AbStrUtil;
import com.lidroid.xutils.util.LogUtils;

import android.content.Context;
import cn.com.zte.android.http.constants.ResultCodeConstants;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.android.widget.dialog.DialogManager;
import cn.com.zte.emeeting.app.appservice.InvitaMeetingService;
import cn.com.zte.emeeting.app.response.instrument.GetDoInvitaMeetingResponse;
import cn.com.zte.emeeting.app.response.instrument.GetInvitaMeetingResponse;
import cn.com.zte.emeeting.app.util.DataConst;
import cn.com.zte.emeeting.app.views.dialog.AbAlertDialogFragment.AbDialogOnClickListener;
import cn.com.zte.emeeting.app.views.dialog.AbDialogUtil;
import cn.com.zte.mobilebasedata.request.HttpUtil;
import cn.com.zte.mobileemeeting.R;


/**
 * 
 * @ClassName: PublicInvitaMeetingRequest
 * @Description: TODO 【会议邀请公共逻辑处理类】
 * @author Pan.Jianbo
 * @date 2016年4月11日 下午4:13:50
 *
 */
public class PublicInvitaMeetingRequest {

	/** 上下文环境 */
	private Context mContext;

	/** 会议邀请逻辑处理类 */
	private InvitaMeetingService invitaMeetingService;
	
	/** 回调接口 */
	private OnRequestSuccess onRequestSuccess;
	
	/** 会议ID */
	private String ID = null;
	
	/** 终端编号 */
	private String Number = null;

	public PublicInvitaMeetingRequest(Context mContext) {
		super();
		this.mContext = mContext;
		invitaMeetingService = new InvitaMeetingService(mContext);
	}

	/**
	 * 获取接口请求数据
	 * @param Type
	 * @param Number
	 * @param Name
	 * @param onRequestSuccess
	 */
	public void getDataInfo(String ID,String Type, String Number, String Name, OnRequestSuccess onRequestSuccess) {
		this.onRequestSuccess = onRequestSuccess;
		this.ID = ID;
		this.Number = Number;
		invitaMeetingService.GetInvitaMeeting(GetInvitaMeetingResponseHand, Type, Number, Name, ID);
	}

	/**
	 * 3.2.9.4新增（14）-会议邀请接口
	 */
	BaseAsyncHttpResponseHandler<GetInvitaMeetingResponse> GetInvitaMeetingResponseHand = new BaseAsyncHttpResponseHandler<GetInvitaMeetingResponse>(
			true) {

		/**
		 * 请求成功
		 */
		public void onSuccessTrans(GetInvitaMeetingResponse responseModelVO) {
			LogUtils.e("【会议邀请】请求成功");
			if (!AbStrUtil.isEmpty(responseModelVO.getS()) && responseModelVO.getS().equals(DataConst.TRUE)) {
				if (onRequestSuccess != null) {
					onRequestSuccess.OnSuccessCallBack(DataConst.YES);
				}
				
			}
           
		};

		/**
		 * 请求失败
		 */
		public void onFailureTrans(GetInvitaMeetingResponse responseModelVO) {
			LogUtils.e("【会议邀请】请求失败");
			/**
			 * (1)如果S返回false,且M字段有值。则直接提示用户M字段的内容。即邀请失败
			 * (2)邀请会议室加入时，如果S返回false,且D字段有值。表示会议室可预订时间和当前会议有冲突。则需要弹出冲突提示，即D字段里面的内容。
			 */
			if (responseModelVO != null) {
				if (!AbStrUtil.isEmpty(responseModelVO.getM())) { // 邀请失败
					DialogManager.showToast(mContext, responseModelVO.getM());
				} else if (!AbStrUtil.isEmpty(responseModelVO.getD())) { // 会议冲突
					 showDiageleHint(responseModelVO.getD(), ID, Number);
				} else {
					DialogManager.showToast(mContext, HttpUtil.SERVER_REQUEST_FAIL);
				}
			} else {
				DialogManager.showToast(mContext, HttpUtil.SERVER_REQUEST_FAIL);
			}
			
			if (onRequestSuccess != null) {
				onRequestSuccess.OnSuccessCallBack(DataConst.NO);
			}
			
		};

		/**
		 * 网络异常
		 */
		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode, String strMsg) {
			LogUtils.e("【会议邀请】请求网络异常");
			if (strCode.equals(ResultCodeConstants.NETWORK_0001C)) {
				DialogManager.showToast(mContext, strMsg);
			} else if (strCode.equals(ResultCodeConstants.NETWORK_0002C)) {
				DialogManager.showToast(mContext, strMsg);
			} else if (strCode.equals(ResultCodeConstants.NETWORK_0003C)) {
				DialogManager.showToast(mContext, strMsg);
			} else {
				DialogManager.showToast(mContext, HttpUtil.SERVER_REQUEST_NORMAL_ERROR);
			}
			
			if (onRequestSuccess != null) {
				onRequestSuccess.OnSuccessCallBack(DataConst.NO);
			}
			
		};
	};

	/**
	 * 3.2.9.8新增（16）- 会议室邀请确认接口
	 */
	BaseAsyncHttpResponseHandler<GetDoInvitaMeetingResponse> GetDoInvitaMeetingResponseHand = new BaseAsyncHttpResponseHandler<GetDoInvitaMeetingResponse>(true){
		
		/**
		 * 请求成功
		 */
		public void onSuccessTrans(GetDoInvitaMeetingResponse responseModelVO) {
			LogUtils.e("【3.2.9.8新增（16）- 会议室邀请确认接口】请求成功");
			if (!AbStrUtil.isEmpty(responseModelVO.getS()) && responseModelVO.getS().equals(DataConst.TRUE)) {
				if (onRequestSuccess != null) {
					onRequestSuccess.OnConfirm(DataConst.YES);
				}
			}
		};
		
		/**
		 * 请求失败
		 */
		public void onFailureTrans(GetDoInvitaMeetingResponse responseModelVO) {
			LogUtils.e("【3.2.9.8新增（16）- 会议室邀请确认接口】请求失败");
			if (responseModelVO != null && !AbStrUtil.isEmpty(responseModelVO.getM())) {
				DialogManager.showToast(mContext, responseModelVO.getM());
			} else {
				DialogManager.showToast(mContext, HttpUtil.SERVER_REQUEST_FAIL);
			}
			
			if (onRequestSuccess != null) {
				onRequestSuccess.OnConfirm(DataConst.NO);
			}
			
		};
		
		/**
		 * 网络异常
		 */
		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode, String strMsg) {
			LogUtils.e("【3.2.9.8新增（16）- 会议室邀请确认接口】请求网络异常");
			if (strCode.equals(ResultCodeConstants.NETWORK_0001C)) {
				DialogManager.showToast(mContext, strMsg);
			} else if (strCode.equals(ResultCodeConstants.NETWORK_0002C)) {
				DialogManager.showToast(mContext, strMsg);
			} else if (strCode.equals(ResultCodeConstants.NETWORK_0003C)) {
				DialogManager.showToast(mContext, strMsg);
			} else {
				DialogManager.showToast(mContext, HttpUtil.SERVER_REQUEST_NORMAL_ERROR);
			}
			
			if (onRequestSuccess != null) {
				onRequestSuccess.OnConfirm(DataConst.NO);
			}
		};
		
	};
	
	
	
	/**
	 * 
	 * @ClassName: OnRequestSuccess
	 * @Description: TODO 【请求回调接口】
	 * @author Pan.Jianbo
	 * @date 2016年4月11日 下午4:24:08
	 *
	 */
	public interface OnRequestSuccess {

		/**
		 * 回调方法
		 * @param code
		 */
		public void OnSuccessCallBack(String code);
		
		public void OnConfirm(String code);
	}
	
	
	
	/**
	 * 会议确认信息对话框
	 * @param hint
	 */
	private void showDiageleHint(String hint,final String ID, final String Number){
		
		AbDialogUtil.showAlertDialog(mContext, mContext.getResources()
				.getString(R.string.AMHint), hint,
				new AbDialogOnClickListener() {

					@Override
					public void onPositiveClick() {

						AbDialogUtil.removeDialog(mContext);
						new AbAppUtil().closeSoftInput(mContext);
						invitaMeetingService.DoInvitaMeeting(GetDoInvitaMeetingResponseHand, ID, Number);
					}

					@Override
					public void onNegativeClick() {

					}
				});
	}
	
	
	
	
	
	

}
