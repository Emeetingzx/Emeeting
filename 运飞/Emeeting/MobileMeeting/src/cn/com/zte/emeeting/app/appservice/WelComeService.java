package cn.com.zte.emeeting.app.appservice;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import cn.com.zte.android.app.activity.BaseActivity;
import cn.com.zte.android.app.application.BaseApplication;
import cn.com.zte.android.app.appservice.BaseAppService;
import cn.com.zte.android.appupdate.AppUpdateManager;
import cn.com.zte.android.appupdate.http.LatestVersionHttpRequest;
import cn.com.zte.android.appupdate.http.LatestVersionHttpResponse;
import cn.com.zte.android.appupdate.http.LatestVersionHttpResponseHandler;
import cn.com.zte.android.http.HttpManager;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.android.securityauth.config.SSOAuthConfig;
import cn.com.zte.android.securityauth.http.SSOTokenHttpRequest;
import cn.com.zte.android.securityauth.http.SSOTokenHttpResponse;
import cn.com.zte.android.securityauth.manager.SSOAuthManager;
import cn.com.zte.android.securityauth.model.UserInfo;
import cn.com.zte.emeeting.app.base.MyApplication;
import cn.com.zte.emeeting.app.dao.shared.SysDBMeetingRoomAddressDBDao;
import cn.com.zte.emeeting.app.dao.shared.SysDBMeetingRoomInfoDBDao;
import cn.com.zte.emeeting.app.dao.shared.SysLastUpdatetimeInfoDBDao;
import cn.com.zte.emeeting.app.database.entity.shared.DBMeetingRoomAddress;
import cn.com.zte.emeeting.app.database.entity.shared.DBMeetingRoomInfo;
import cn.com.zte.emeeting.app.request.instrument.GetHelpFeedbackRequest;
import cn.com.zte.emeeting.app.request.instrument.GetRecentBuildingAddressInfoRequest;
import cn.com.zte.emeeting.app.request.instrument.GetServerTimeRequest;
import cn.com.zte.emeeting.app.request.instrument.GetSubmitBookingMeetingRoomRequest;
import cn.com.zte.emeeting.app.request.instrument.GetUserIfAddValueServiceAdminRequest;
import cn.com.zte.emeeting.app.response.instrument.GetHelpFeedbackResponse;
import cn.com.zte.emeeting.app.response.instrument.GetRecentBuildingAddressInfoResponse;
import cn.com.zte.emeeting.app.response.instrument.GetServerTimeResponse;
import cn.com.zte.emeeting.app.response.instrument.GetSubmitBookingMeetingRoomResponse;
import cn.com.zte.emeeting.app.response.instrument.GetUserIfAddValueServiceAdminResponse;
import cn.com.zte.emeeting.app.util.NetWorkUtil;
import cn.com.zte.mobilebasedata.entity.PageInput;
import cn.com.zte.mobilebasedata.request.CheckNetAsyncHttpResponseHandler;
import cn.com.zte.mobilebasedata.request.CheckNetCallBack;
import cn.com.zte.mobilebasedata.request.HttpUtil;
import cn.com.zte.mobilebasedata.system.entity.SysLastUpdatetimeInfo;
import cn.com.zte.mobilebasedata.system.request.GetLastUpdatetimeRequest;
import cn.com.zte.mobilebasedata.system.request.GetSysMeetingRoomAddressRequest;
import cn.com.zte.mobilebasedata.system.request.GetSysMeetingRoomInfoRequest;
import cn.com.zte.mobilebasedata.system.response.GetLastUpdateTimeResponse;
import cn.com.zte.mobilebasedata.system.response.GetSysMeetingRoomAddressResponse;
import cn.com.zte.mobilebasedata.system.response.GetSysMeetingRoomInfoResponse;
import cn.com.zte.mobileemeeting.R;

/** 欢迎界面逻辑处理类 */
public class WelComeService extends BaseAppService {

	private static final String TAG = WelComeService.class.getSimpleName();
	//测试环境
//	public static final String PackageName = "com.zte.softdatest";
	//正式环境
	public static final String PackageName = "com.zte.softda";

	public static final int outTime = 20 * 1000;

	/** 上下文 */
	private Context mContext;

	/** 没有新版本不需要升级标识 */
	public static final int NotHasNewVersionLogo = 1001;

	/** 有可选的新版本标识 */
	public static final int CancelNewOptionVersionLogo = 1002;

	/** 出现异常时标识 */
	public static final int PopUpErrorLogo = 1003;

	/** 外网请求失败标识 */
	public static final int NetworkUseErrorLogo = 1004;

	/** 数据更新状态表操作类对象*/
	private SysLastUpdatetimeInfoDBDao sysLastUpdatetimeInfoDBDao;
	
	/** 会议室地址表操作类对象*/
	private SysDBMeetingRoomAddressDBDao mraDao;
	
	/** 会议室信息表操作类对象*/
	private SysDBMeetingRoomInfoDBDao mriDao;
	
	public WelComeService(Context context) {
		super(context);
		this.mContext = context;
	}

	/**
	 * 检查新版本信息
	 * 
	 * @param networkUse
	 *            表示是否使用外网,true为外网，false为内网。
	 * @author sunli
	 * @since JDK 1.6
	 */
	public void VersionUpdateDetection(Handler handler, boolean networkUse) {
		final Handler h = handler;
		final boolean NetworkUse = networkUse;

		HttpManager.setTimeout(HttpUtil.VersionUpdateTimeOut);
		HttpManager.setSoTimeout(HttpUtil.SocketTimeOut);

		/* 获取版本更新管理器实例 */
		AppUpdateManager appUpdateManager = new AppUpdateManager(
				(BaseActivity) mContext);
		appUpdateManager.setAppUpdateTestEnvironment(true);

		/* IP端口相关的配置 */
		appUpdateManager.config(R.xml.map_update_server_config);

		if (NetworkUse) {
			/* 调用外网 */
			appUpdateManager.configToOuterNet();
		} else {
			/* 调用内网 */
			appUpdateManager.configToInnerNet();
		}

		/* 组建请求数据 */
		LatestVersionHttpRequest objRequest = new LatestVersionHttpRequest(
				MyApplication.getAppid(),mContext);

		/* 组建回调 */
		LatestVersionHttpResponseHandler<LatestVersionHttpResponse> objHander = new LatestVersionHttpResponseHandler<LatestVersionHttpResponse>() {
			@Override
			public void progressStart() {
				super.progressStart();
			}

			/**
			 * 没有新版本不需要升级.
			 * 
			 * 执行此方法
			 */
			@Override
			protected void onNotHasNewVersion(
					LatestVersionHttpResponse responseModelVO) {
				super.onNotHasNewVersion(responseModelVO);
				Log.i(TAG, "没有新版本");
				h.sendEmptyMessage(NotHasNewVersionLogo);
			}

			/**
			 * 有可选的新版本,但是用户选择取消升级.
			 * 
			 * 执行此方法
			 * 
			 */
			@Override
			protected void onCancelNewOptionVersion(
					LatestVersionHttpResponse responseModelVO) {
				super.onCancelNewOptionVersion(responseModelVO);
				Log.i(TAG, "有可选的新版本");
				h.sendEmptyMessage(CancelNewOptionVersionLogo);
			}

			@Override
			public void onFailureTrans(LatestVersionHttpResponse responseModelVO) {
				// TODO Auto-generated method stub
				try {
					super.onFailureTrans(responseModelVO);
				} catch (Exception e) {
					e.printStackTrace();
					Log.i(TAG, "版本更新出现异常");
				}
			}

			/**
			 * 出现异常时，子类决定是否弹出对话框或者继续其他逻辑.
			 * 
			 * 执行此方法
			 * 
			 */
			@Override
			public void onPopUpErrorDialog(String strTitle, String strCode,
					String strMsg) {
				super.onPopUpErrorDialog(strTitle, strCode, strMsg);
				Log.i(TAG, "版本更新出现异常");
				if (NetworkUse) {
					h.sendEmptyMessage(NetworkUseErrorLogo);
				} else {
					h.sendEmptyMessage(PopUpErrorLogo);
				}
			}

		};

		/**
		 * 检查版本
		 * */
		try {
			appUpdateManager.checkNewVersion(objRequest, objHander);
			HttpManager.setTimeout(HttpUtil.TimeOut);
		} catch (Exception e) {
			Log.d(TAG, "版本更新异常");
			HttpManager.setTimeout(HttpUtil.TimeOut);
			e.printStackTrace();
		}
	}

	/**
	 * 检测外网和内网. <br/>
	 * 日期: 2014-7-1 上午11:11:30 <br/>
	 * 
	 * @author wangenzi
	 * @since JDK 1.6
	 */
	public static void checkOuterInnerNet(final Context context,
			final CheckNetCallBack checkNetCallBack) {
		// 先检测外网，再检测内网
		CheckNetAsyncHttpResponseHandler<SSOTokenHttpResponse> outHandler = new CheckNetAsyncHttpResponseHandler<SSOTokenHttpResponse>(
				context, true) {

			/**
			 * 外网可用.
			 * 
			 * @see cn.com.zte.app.redenvelope.base.http.CheckNetAsyncHttpResponseHandler#onNetEnable(android.content.Context,
			 *      boolean)
			 */
			@Override
			public void onNetEnable(Context objContext, boolean isCheckOuter) {
				checkNetCallBack.onNetCheckFinish(isCheckOuter, true);
			}

			/**
			 * 外网不可用.
			 * 
			 * @see cn.com.zte.app.redenvelope.base.http.CheckNetAsyncHttpResponseHandler#onNetUnValible(android.content.Context,
			 *      boolean)
			 */
			@Override
			public void onNetUnValible(Context objContext, boolean isCheckOuter) {
				Log.i(TAG, "checkNetOuter  fail , try to check innerNet");
				// 外网检测失败再检测内网
				CheckNetAsyncHttpResponseHandler<SSOTokenHttpResponse> innerHandler = new CheckNetAsyncHttpResponseHandler<SSOTokenHttpResponse>(
						context, false) {
					/**
					 * 内网可用.
					 * 
					 * @see cn.com.zte.app.redenvelope.base.http.CheckNetAsyncHttpResponseHandler#onNetEnable(android.content.Context,
					 *      boolean)
					 */
					@Override
					public void onNetEnable(Context objContext,
							boolean isCheckOuter2) {
						Log.i(TAG, "checkNetOuter success");
						checkNetCallBack.onIntranetCheckFinish(isCheckOuter2,
								true);
					}

					/**
					 * 内网不可用.
					 * 
					 * @see cn.com.zte.app.redenvelope.base.http.CheckNetAsyncHttpResponseHandler#onNetUnValible(android.content.Context,
					 *      boolean)
					 */
					@Override
					public void onNetUnValible(Context objContext,
							boolean isCheckOuter2) {
						Log.i(TAG, "checkNetInner  fail");
						checkNetCallBack.onIntranetCheckFinish(isCheckOuter2,
								false);
					}
				};

				Log.i(TAG, "checkNetInner");
				checkNetInner(context, innerHandler);
			}

		};

		Log.i(TAG, "checkNetOuter");
		checkNetOuter(context, outHandler);
	}

	/**
	 * 检测外网. <br/>
	 * 日期: 2014-7-1 上午11:22:23 <br/>
	 * 
	 * @author wangenzi
	 * @param context
	 * @since JDK 1.6
	 */
	private static void checkNetOuter(Context context,
			CheckNetAsyncHttpResponseHandler checkNetAsyncHttpResponseHandler) {
		checkNet(context, checkNetAsyncHttpResponseHandler);
	}

	/**
	 * 检测内网. <br/>
	 * 日期: 2014-7-1 上午11:22:23 <br/>
	 * 
	 * @author wangenzi
	 * @param context
	 * @since JDK 1.6
	 */
	private static void checkNetInner(Context context,
			CheckNetAsyncHttpResponseHandler checkNetAsyncHttpResponseHandler) {
		checkNet(context, checkNetAsyncHttpResponseHandler);
	}

	/**
	 * 检测网络. <br/>
	 * 日期: 2014-7-1 上午11:21:43 <br/>
	 * 
	 * @author wangenzi
	 * @param context
	 * @param isCheckOuter
	 * @param checkNetAsyncHttpResponseHandler
	 * @since JDK 1.6
	 */
	private static void checkNet(final Context context,
			CheckNetAsyncHttpResponseHandler checkNetAsyncHttpResponseHandler) {
		try {
			boolean serverHttpsFlag = SSOAuthConfig.getAuthServerHttpsFlag();
			// 优先使用外网IP和端口
			String authServerIp = null;
			String authServerPort = null;

			if (SSOAuthConfig.getAuthServerIPPort() == null) {
				SSOAuthConfig.config(context, MyApplication.getAppid(),
						R.xml.map_sso_config);
				if (SSOAuthConfig.getAuthServerIPPort() == null) {
					Log.w(TAG, "checkNet fail, skip...");
					return;
				}
			}

			if (checkNetAsyncHttpResponseHandler.isCheckOuter()) {
				authServerIp = SSOAuthConfig.getAuthServerIPPort().getOuterIp();
				authServerPort = SSOAuthConfig.getAuthServerIPPort()
						.getOuterPort();
			} else {
				authServerIp = SSOAuthConfig.getAuthServerIPPort().getInnerIp();
				authServerPort = SSOAuthConfig.getAuthServerIPPort()
						.getInnerPort();
			}

			SSOTokenHttpRequest objRequst = new SSOTokenHttpRequest(
					serverHttpsFlag, authServerIp, authServerPort);

			HttpManager.post(context, objRequst,
					checkNetAsyncHttpResponseHandler);

		} catch (Exception e) {
			Log.d(TAG, "checkNet error", e);
		}
	}

	/** 获取MOA用户登陆信息 */
	public UserInfo getUserInfo() {
		SSOAuthManager ssoAuthManager = new SSOAuthManager(
				BaseApplication.getInstance(), MyApplication.getAppid());
		SSOAuthConfig.setMoaPackageName(PackageName);
		return ssoAuthManager.getUserInfo();
	}

	/** 获取MOA用户安全令牌加密串 */
	public String getToken() {
		SSOAuthManager ssoAuthManager = new SSOAuthManager(
				BaseApplication.getInstance(), MyApplication.getAppid());
		SSOAuthConfig.setMoaPackageName(PackageName);
		return ssoAuthManager.getToken();
	}

	/** 注销MOA用户登陆 */
	public boolean ssoLogout() {
		SSOAuthManager ssoAuthManager = new SSOAuthManager(
				BaseApplication.getInstance(), MyApplication.getAppid());
		SSOAuthConfig.setMoaPackageName(PackageName);
		return ssoAuthManager.logout();
	}

	/** 帮助反馈接口 */

	public void HelpFeedback(
			BaseAsyncHttpResponseHandler<GetHelpFeedbackResponse> handler,
			String info,String contactInfo) {
		if (!NetWorkUtil.noNetworkPromptProcessing(mContext)) {
			return;
		}
		GetHelpFeedbackRequest helpFeedbackRequest = new GetHelpFeedbackRequest(
				mContext, info,contactInfo);
		HttpManager.post(mContext, helpFeedbackRequest, handler);

	}

	/** 提交预定会议室接口 */

	public void SubmitBookingMeetingRoom(
			BaseAsyncHttpResponseHandler<GetSubmitBookingMeetingRoomResponse> handler,
			String EmeetingId, String AttendLeaderLevel, String MemberNos,
			String MeetingName) {
		if (!NetWorkUtil.noNetworkPromptProcessing(mContext)) {
			return;
		}
		GetSubmitBookingMeetingRoomRequest helpFeedbackRequest = new GetSubmitBookingMeetingRoomRequest(
				mContext, EmeetingId, AttendLeaderLevel, MemberNos, MeetingName);
		HttpManager.post(mContext, helpFeedbackRequest, handler);

	}

	/** 获取离用户最近公司建筑地址信息接口 */
	public void GetRecentBuildingAddressInfo(
			BaseAsyncHttpResponseHandler<GetRecentBuildingAddressInfoResponse> handler,
			String LatitudeAndLongitude) {

		if (!NetWorkUtil.noNetworkPromptProcessing(mContext)) {
			return;
		}
		GetRecentBuildingAddressInfoRequest request = new GetRecentBuildingAddressInfoRequest(
				mContext, LatitudeAndLongitude);

		HttpManager.post(mContext, request, handler);

	}
	
	/** 从服务器获取数据更新时间信息方法 */
	public void getLastUpdateTimeInfo(
			BaseAsyncHttpResponseHandler<GetLastUpdateTimeResponse> responseHandler) {
		GetLastUpdatetimeRequest glutRequest = new GetLastUpdatetimeRequest(
				getContext());
		HttpManager.post(getContext(), glutRequest, responseHandler);
	}
	
	
	/** 获取该用户是否为管理员 */
	public void getUserIsAdmin(
			BaseAsyncHttpResponseHandler<GetUserIfAddValueServiceAdminResponse> responseHandler) {
		GetUserIfAddValueServiceAdminRequest glutRequest = new GetUserIfAddValueServiceAdminRequest(
				getContext());
		HttpManager.post(getContext(), glutRequest, responseHandler);
	}
	
	/** 获取该用户是否为管理员 */
	public void getServerTime(
			BaseAsyncHttpResponseHandler<GetServerTimeResponse> responseHandler) {
		GetServerTimeRequest glutRequest = new GetServerTimeRequest(
				getContext());
		HttpManager.post(getContext(), glutRequest, responseHandler);
	}
	
	/** 从服务器获取会议室地址信息方法 */
	public void getSysMeetingRoomAddress(
			BaseAsyncHttpResponseHandler<GetSysMeetingRoomAddressResponse> responseHandler,String lastUpdateTime, PageInput pageInput) {
		GetSysMeetingRoomAddressRequest glutRequest = new GetSysMeetingRoomAddressRequest(
				getContext(),lastUpdateTime,pageInput);
		HttpManager.post(getContext(), glutRequest, responseHandler);
	}
	
	/** 从服务器获取会议室基础信息数据方法 */
	public void getSysMeetingRoomInfo(
			BaseAsyncHttpResponseHandler<GetSysMeetingRoomInfoResponse> responseHandler,String lastUpdateTime, PageInput pageInput) {
		GetSysMeetingRoomInfoRequest glutRequest = new GetSysMeetingRoomInfoRequest(
				getContext(),lastUpdateTime,pageInput);
		HttpManager.post(getContext(), glutRequest, responseHandler);
	}
	
	/** 本地数据库添加数据更新时间信息*/
	public void insertLastUpdatetimeInfos(List<SysLastUpdatetimeInfo> luis){
		sysLastUpdatetimeInfoDBDao = new SysLastUpdatetimeInfoDBDao();
		if(luis!=null && luis.size()>0){
			sysLastUpdatetimeInfoDBDao.batchInsertOrUpdatData(luis);
		}
	}
	
	/** 获取本地数据库数据更新时间信息对象
	 * @param name 数据对应的Name
	 * */
	public SysLastUpdatetimeInfo getLastUpdatetimeInfo(String tableName){
		sysLastUpdatetimeInfoDBDao = new SysLastUpdatetimeInfoDBDao();
		return sysLastUpdatetimeInfoDBDao.selectNameData(tableName);
	}
	
	/** 获取本地数据库表最后更新时间
	 * @param name 数据对应的Name
	 * */
	public String getLDT(String name){
		sysLastUpdatetimeInfoDBDao = new SysLastUpdatetimeInfoDBDao();
		return sysLastUpdatetimeInfoDBDao.getLastDate(name);
	}
	
	/** 本地数据库添加会议室地址表对象信息集合*/
	public void insertDBMeetingRoomAddress(List<DBMeetingRoomAddress> dmras){
		mraDao = new SysDBMeetingRoomAddressDBDao();
		if(dmras!=null && dmras.size()>0){
			mraDao.batchInsertOrUpdatData(dmras);
		}
	}
	
	/** 本地数据库添加会议室信息表对象信息集合*/
	public void insertDBMeetingRoomInfos(List<DBMeetingRoomInfo> dmris){
		mriDao = new SysDBMeetingRoomInfoDBDao();
		if(dmris!=null && dmris.size()>0){
			mriDao.batchInsertOrUpdatData(dmris);
		}
	}
}
