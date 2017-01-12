package cn.com.zte.emeeting.app.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.TextView;
import cn.com.zte.android.common.util.JsonUtil;
import cn.com.zte.android.http.HttpManager;
import cn.com.zte.android.http.constants.ResultCodeConstants;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.android.pushclient.PushManager;
import cn.com.zte.android.pushclient.constants.PushConstants;
import cn.com.zte.android.pushclient.model.PushMessage;
import cn.com.zte.android.securityauth.config.SSOAuthConfig;
import cn.com.zte.android.securityauth.interfaces.SSOAuthCheckCallBack;
import cn.com.zte.android.securityauth.manager.SSOAuthCheckManager;
import cn.com.zte.android.widget.dialog.DialogManager;
import cn.com.zte.emeeting.app.appservice.WelComeService;
import cn.com.zte.emeeting.app.base.ConfigrationList;
import cn.com.zte.emeeting.app.base.MyApplication;
import cn.com.zte.emeeting.app.base.activity.AppActivity;
import cn.com.zte.emeeting.app.dialog.DlgProgress;
import cn.com.zte.emeeting.app.response.instrument.GetServerTimeResponse;
import cn.com.zte.emeeting.app.response.instrument.GetUserIfAddValueServiceAdminResponse;
import cn.com.zte.emeeting.app.util.DataConst;
import cn.com.zte.emeeting.app.util.DataUtil;
import cn.com.zte.emeeting.app.util.MyToast;
import cn.com.zte.emeeting.app.util.NetWorkUtil;
import cn.com.zte.emeeting.app.util.SharedPreferenceUtil;
import cn.com.zte.emeeting.app.util.SystemTablesUtil;
import cn.com.zte.mobilebasedata.entity.PageInput;
import cn.com.zte.mobilebasedata.entity.PageOutput;
import cn.com.zte.mobilebasedata.request.CheckNetCallBack;
import cn.com.zte.mobilebasedata.request.HttpUtil;
import cn.com.zte.mobilebasedata.system.entity.SysLastUpdatetimeInfo;
import cn.com.zte.mobilebasedata.system.response.GetLastUpdateTimeResponse;
import cn.com.zte.mobilebasedata.system.response.GetSysMeetingRoomAddressResponse;
import cn.com.zte.mobilebasedata.system.response.GetSysMeetingRoomInfoResponse;
import cn.com.zte.mobileemeeting.R;

/**
 * 欢迎界面
 * 
 * @author sun.li
 * */
public class WelComeActivity extends AppActivity {

	/** 是否为体验包模式 */
	private boolean isR = false;

	private final boolean isShowHttpDlg=false;
	
	/**
	 * 上下文
	 */
	private Context mContext;

	/**
	 * 当前页码
	 */
	private static final int PNO = 1;

	/**
	 * 每页加载数量
	 */
	private static final int PSIZE = HttpUtil.RequestMAXSize;

	/** 基础数据接口请求每页数据量 */
	private int sbdPageSize = 500;

	/** 本地系统表更新时间对象集合 */
	private List<SysLastUpdatetimeInfo> luis;

	private boolean versionUpdateDetectionLogo = true;

	/** 欢迎界面逻辑处理类对象 */
	private WelComeService wcService;

	/** 会议室地址表对象接口请求页码 */
	private int smraPageNo = 1;

	/** 会议室地址表对象数据的更新时间对象 */
	private PageOutput smraPageOutput = new PageOutput();

	/** 请求会议室地址表对象数据接口结束标志 */
	private boolean smraSuccessful = false;

	/** 会议室信息表对象接口请求页码 */
	private int smriPageNo = 1;

	/** 会议室信息表对象数据的更新时间对象 */
	private PageOutput smriPageOutput = new PageOutput();

	/** 请求会议室信息表对象数据接口结束标志 */
	private boolean smriSuccessful = false;
	
	
	private DlgProgress dlgProgress;
	
	/** 展示进度对话框*/
	private void dlgProgressShow(Context context){
		if(dlgProgress==null)
		{
			dlgProgress = new DlgProgress(context);
		}
		if(!dlgProgress.isShowing()){
			dlgProgress.show();
		}
	}
	
	/**关闭进度对话框*/
	private void dlgProgressDissmiss(){
		if(dlgProgress!=null&&dlgProgress.isShowing())
		{
			dlgProgress.dismiss();
		}
	}

	@Override
	protected void initContentView() {
		super.initContentView();
		Log.d("TAG", System.currentTimeMillis()+"");
		setContentView(R.layout.activity_welcome);
		mContext = this;
		wcService = new WelComeService(mContext);
		
		//首次启动时也需要判断是否有新消息
		setIntent(getIntent());
		onNewPushMessage(getIntent());
	}

	/**
	 * @see android.app.Activity#onPostCreate(android.os.Bundle)
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}

	@Override
	protected void initData() {
		super.initData();
	}

	@Override
	protected void initMenu() {
		super.initMenu();
	}

	@Override
	protected void initMenuEvents() {
		super.initMenuEvents();
	}

	@Override
	protected void initViews() {
		super.initViews();
		if (!NetWorkUtil.netIsConnect(mContext)) {
			showNoNetDialog();
		}
		
		// 设置超时
		HttpManager.setTimeout(DataConst.INTERNET_INTERIOR_TIMEOUT);
		// 网络检测回调
		CheckNetCallBack callBack = new CheckNetCallBack() {
			@Override
			public void onNetCheckFinish(boolean isCheckOuter, boolean isEnable) {
				if (isEnable) {
					versionUpdateDetectionLogo = true;
					HttpUtil.ISOUTSIDENET = true;
					Log.i(TAG, "您已切换到外网网络环境");
					HttpUtil.getInstance().setSERVEREIP(HttpUtil.NetworkIP);
					SSOAuthConfig.configToOuterNet();
					VersionUpdateDetection();
				}
			}

			@Override
			public void onIntranetCheckFinish(boolean isCheckOuter,
					boolean isEnable) {
				super.onIntranetCheckFinish(isCheckOuter, isEnable);
				if (isEnable) {
					versionUpdateDetectionLogo = false;
					HttpUtil.ISOUTSIDENET = false;
					Log.i(TAG, "您已切换到内网网络环境");
					HttpUtil.getInstance().setSERVEREIP(HttpUtil.IntranetIP);
					SSOAuthConfig.configToInnerNet();
					VersionUpdateDetection();
				} else {
					showNoNetDialog();
				}
			}

		};
		// 检测外网内网
		WelComeService.checkOuterInnerNet(this, callBack);
	}
	
	/**
	 * 显示没有网络提示的对话框
	 */
	private void showNoNetDialog() {
		Dialog dialog = new AlertDialog.Builder(
				WelComeActivity.this)
				.setMessage("无法连接到服务器,请检查你的网络或者稍后重试")
				.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(
									DialogInterface dialog,
									int which) {
								Intent intent = new Intent(
										Intent.ACTION_MAIN);
								intent.addCategory(Intent.CATEGORY_HOME);
								intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(intent);
								System.exit(0);
							}

						}).create();
		dialog.show();
	}

	/** 版本检测 */
	private void VersionUpdateDetection() {
		wcService.VersionUpdateDetection(versionUpdateDetectionHandler,
				versionUpdateDetectionLogo);
	}

	/** 更新版本返回结果接收 */
	@SuppressLint("HandlerLeak")
	Handler versionUpdateDetectionHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WelComeService.NotHasNewVersionLogo:
				Log.i(TAG, "没有新版本");
				verifyUserIsLogoin();
//				loginSuccess();
//				getLastUpdatetimeInfos();
				break;
			case WelComeService.CancelNewOptionVersionLogo:
				Log.i(TAG, "有可选的新版本");
				verifyUserIsLogoin();
//				loginSuccess();
//				getLastUpdatetimeInfos();
				break;
			case WelComeService.PopUpErrorLogo:
				Log.i(TAG, "版本更新出现异常");
				verifyUserIsLogoin();
//				loginSuccess();
//				getLastUpdatetimeInfos();
				break;
			case WelComeService.NetworkUseErrorLogo:
				Log.i(TAG, "外网版本更新出现异常");
				verifyUserIsLogoin();
//				loginSuccess();
//				getLastUpdatetimeInfos();
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 校验用户登录状态
	 */
	private void verifyUserIsLogoin() {
//		dlgProgressDissmiss();
//		if (isR) {
//			skipHome();
//		} else {
//			getLastUpdatetimeInfos();
//		}

//		if (wcService.getUserInfo() == null) {
//			MOALogin();
//		} else {
//			getLastUpdatetimeInfos();
		MOALogin();
//			skipHome();
//		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d("TAG", System.currentTimeMillis()+"");
	}
	
	//登录成功
	private void loginSuccess(){
		// 注册推送功能
		try{
			if (wcService.getUserInfo().getUID()!=null&&!wcService.getUserInfo().getUID().equals("")){
				PushManager.getInstance(mContext).register(wcService.getUserInfo().getUID());
			}else{
				Log.d(TAG,"用户工号为空");
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		getLastUpdatetimeInfos();
	}

	/**
	 * MOA登录
	 */
	private void MOALogin() {
//		checkSSOLogin();
		Intent intent = new Intent();
		intent.setClass(WelComeActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
		
	}

	/**
	 * SSL登录检查. <br/>
	 * 日期: 2014-6-3 上午12:10:13 <br/>
	 * 
	 * @author wangenzi
	 * @since JDK 1.6
	 */
	private void checkSSOLogin() {
		final SSOAuthCheckCallBack authCheckCallBack = new SSOAuthCheckCallBack() {

			/**
			 * 应用关闭前回调，可以做一些现场保护数据操作.
			 * 
			 * @see cn.com.zte.android.securityauth.interfaces.SSOAuthCheckCallBack#onAppClosePre()
			 */
			@Override
			public void onAppClosePre() {
				// 应用关闭前回调，可以做一些现场保护数据操作.
			}


			/**
			 * MOA未安装的逻辑. <br/>
			 * 默认回调authCheckCallBack.onMOANotInstalled<br/>
			 * 然后finishApp<br/>
			 * 日期: 2014-6-30 上午1:14:48 <br/>
			 * 
			 * @author wangenzi
			 * @since JDK 1.6
			 */
			@Override
			public void onMOANotInstalled() {
				AlertDialog dialog = new AlertDialog.Builder(
						WelComeActivity.this)
						.setMessage(
								Html.fromHtml("<br>没有检测到MOA，请先安装MOA。<br>请点击<a href=\"http://moa.zte.com.cn/m\">http://moa.zte.com.cn/m</a>进行下载安装！<br>"))
						.setPositiveButton("关闭",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										System.exit(0);
										
									}

								}).create();// 创建
				dialog.setCanceledOnTouchOutside(false);
				// 显示对话框
				dialog.show();
				// 一定在show()方法之后
				((TextView) dialog.findViewById(android.R.id.message))
						.setMovementMethod(LinkMovementMethod.getInstance());
			}

			/*
			 * @Override public void onMOANotInstalled() { // 弹出安装MOA的提醒
			 * MyToast.show(WelComeActivity.this, "请先安装MOA");
			 * 
			 * 
			 * }
			 */

			@Override
			public void onAuthSuccess() {
				Log.i(TAG, "onAuthSuccess...");
				// MyToast.show(getApplicationContext(), "单点登陆成功");

				// 应用原有业务逻辑
				// getLastUpdatetimeInfos();
				
				loginSuccess();
			}

			/**
			 * Http通讯错误回调.
			 * 
			 * @see cn.com.zte.android.securityauth.interfaces.SSOAuthCallBack#onHttpError(String, String)
			 */
			@Override
			public void onHttpError(String strCode, String strMsg) {
				// 弹出错误信息
				MyToast.show(getApplicationContext(), strMsg);
				// 阻止进入下一界面
				finish();
			}

			public void onAuthFailureTrans() {
				// TODO Auto-generated method stub
				
			}

			public void onFailure(String arg0) {
				// TODO Auto-generated method stub
				
			}
		};

		// 构造SSOAuthCheckManager
		final SSOAuthCheckManager acm = new SSOAuthCheckManager(this,
				MyApplication.getAppid(), authCheckCallBack, false) {

			@Override
			public void onMOANotInstalled() {
				// 执行MOA未安装的回调
				authCheckCallBack.onMOANotInstalled();

			}

			// /**
			// * 覆盖广播行为，直接跳转到登陆功能.
			// */
			// @Override
			// public void appToMOALogin() {
			// // 跳转到登陆Activity TODO
			// jumpLoginActivity();
			// }

		};

		// 配置IP和端口
		acm.config(R.xml.map_sso_config);

		// 执行SSO检查
		acm.check();
	}
	
	/***
	 * 获得推送消息 -- 首次启动时也需要判断是否有新消息  
	 * @param intent
	 */
	
	private void onNewPushMessage(Intent intent) {
		// 判断是否从通知跳转而来
		PushMessage objPushMessage = getIntent().getParcelableExtra(PushConstants.PUSH_MESSAGE_DATA_KEY);
		
		if (objPushMessage != null) {
			Log.i(TAG, "objPushMessage is not null:" + JsonUtil.toJson(objPushMessage));

			// 判断是否是轻应用的消息
			Log.i(TAG, "onNewPushMessage of Web APP");

			String appId = objPushMessage.getITPWebAppId();
			String msgId = objPushMessage.getMsgId();

			Log.i(TAG, "msgId :" + msgId);
			Log.i(TAG, "appId :" + appId);
			
			dumpIntent(intent);
		
		} else {
			Log.i(TAG, "objPushMessage is null 传过来的信息为空");
		}
	}
	
	
	/**
	 * dumpIntent. <br/>
	 * 日期: 2014-12-26 上午11:44:50 <br/>
	 * 
	 * @author wangenzi
	 * @param i
	 * @since JDK 1.6
	 */
	public void dumpIntent(Intent i) {
		StringBuffer sbf = new StringBuffer();
		Bundle bundle = i.getExtras();
		
		if (bundle != null) {
			Set<String> keys = bundle.keySet();
			Iterator<String> it = keys.iterator();
			Log.e(TAG, "Dumping Intent start");
			while (it.hasNext()) {
				String key = it.next();
				String strLog = "[" + key + "=" + bundle.get(key) + "]";
				Log.e(TAG, strLog);
				sbf.append(strLog + "\n");
			}
			Log.e(TAG, "Dumping Intent end");
		}

	}

	/** 获取系统数据更新时间数据信息集合 */
	private void getLastUpdatetimeInfos() {
		wcService.getLastUpdateTimeInfo(glutResponseHandler);
		wcService.getUserIsAdmin(isAdminHandler);
		wcService.getServerTime(serverTimeHandler);
	}

	/** 获取系统数据更新时间接口返回结果接收器 */
	BaseAsyncHttpResponseHandler<GetLastUpdateTimeResponse> glutResponseHandler = new BaseAsyncHttpResponseHandler<GetLastUpdateTimeResponse>(
			isShowHttpDlg) {
		/** Token验证成功. */
		public void onSuccessTrans(GetLastUpdateTimeResponse responseModelVO) {

			Log.i(TAG, "获取系统数据更新时间数据成功");
			luis = responseModelVO.getD();
			if (luis != null && luis.size() > 0) {
				for (int i = 0; i < luis.size(); i++) {
					String newLDT = luis.get(i).getLDT();
					String oldLDT = "";
					String tableName = luis.get(i).getName();
					SysLastUpdatetimeInfo lui = wcService
							.getLastUpdatetimeInfo(tableName);
					if (lui != null) {
						oldLDT = lui.getLDT();
					}
					Log.i(TAG, "NewLDT" + newLDT);
					Log.i(TAG, "OldLDT" + oldLDT);
					if (oldLDT != null && !oldLDT.equals("")) {
						// 通过字符串创建两个日期对象
						Date newDate = null;
						try {
							newDate = DataUtil.String2Date(newLDT);
						} catch (Exception e) {
							newDate = null;
						}
						Date oldDate = null;
						try {
							oldDate = DataUtil.String2Date(oldLDT);
						} catch (Exception e) {
							oldDate = null;
						}

						// 得到两个日期对象的总毫秒数
						/** 服务器返回的数据更新最新时间 */
						long firstDateMilliSeconds = 0;
						if (newDate != null) {
							firstDateMilliSeconds = newDate.getTime();
						}
						/** 本地存储数据更新时间 */
						long secondDateMilliSeconds = 0;
						if (oldDate != null) {
							secondDateMilliSeconds = oldDate.getTime();
						}

						// 得到两者之差
						long firstMinusSecond = firstDateMilliSeconds
								- secondDateMilliSeconds;
						Log.i(TAG, "NewLDT - OldLDT" + firstMinusSecond);
						/* 数据需要更新,更新对应表数据 */
						if (firstMinusSecond > 0) {
							dlgProgressShow(mContext);
							getSystemBaseDataInfo(tableName, newLDT);
						} else {
							getSystemBaseTableUpdataState(tableName);
						}
					} else {
						dlgProgressShow(mContext);
						/* 数据需要更新,更新对应表数据 */
						getSystemBaseDataInfo(tableName, newLDT);
					}
				}
			} else {
				for (int i = 1; i < 16; i++) {
					getSystemBaseDataInfo(i);
				}
			}
			skipHome();
		};

		/**
		 * 返回结果失败时触发.
		 * 
		 */
		public void onFailureTrans(GetLastUpdateTimeResponse responseModelVO) {
			if (responseModelVO != null && responseModelVO.getM() != null
					&& !responseModelVO.getM().equals("")) {
				DialogManager.showToast(mContext, responseModelVO.getM());
			} else {
				DialogManager.showToast(mContext, HttpUtil.SERVER_REQUEST_FAIL);
			}
			for (int i = 1; i < 16; i++) {
				getSystemBaseDataInfo(i);
			}
		};

		/**
		 * Http通讯出现异常时，子类决定是否拦截. <br/>
		 * 
		 */
		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			if (strCode.equals(ResultCodeConstants.NETWORK_0001C)) {
				DialogManager.showToast(mContext, strMsg);
			} else if (strCode.equals(ResultCodeConstants.NETWORK_0002C)) {
				DialogManager.showToast(mContext, strMsg);
			} else if (strCode.equals(ResultCodeConstants.NETWORK_0003C)) {
				DialogManager.showToast(mContext, strMsg);
			} else {
				DialogManager.showToast(mContext,
						HttpUtil.SERVER_REQUEST_NORMAL_ERROR);
			}
			for (int i = 1; i < 16; i++) {
				getSystemBaseDataInfo(i);
			}
		};
	};

	/**
	 * 根据系统表名表名更新某表
	 * 
	 * @param data 表更新最新时间
	 * @param systemTableName 当前请求基础数据类表名
	 * */
	public void getSystemBaseDataInfo(String systemTableName, String data) {
		int tableLogo = 0;
		if (systemTableName
				.equals(SystemTablesUtil.SysMeetingRoomAddressTableName)) {
			tableLogo = 1;
			smraSuccessful = false;
			smraPageOutput.setE(data);
		} else if (systemTableName
				.equals(SystemTablesUtil.SysMeetingRoomInfoTableName)) {
			tableLogo = 2;
			smriSuccessful = false;
			smriPageOutput.setE(data);
		}
		getSystemBaseDataInfo(tableLogo);
	}

	/**
	 * 根据系统表名获取表格数据是否更新状态
	 * 
	 * @param systemTableName 当前请求基础数据类表名
	 * */
	public void getSystemBaseTableUpdataState(String systemTableName) {
		if (systemTableName
				.equals(SystemTablesUtil.SysMeetingRoomAddressTableName)) {
			smraSuccessful = true;
		} else if (systemTableName
				.equals(SystemTablesUtil.SysMeetingRoomInfoTableName)) {
			smriSuccessful = true;
		}
	}

	/**
	 * 获取系统基础数据信息
	 * 
	 * @param baseDataLogo 当前请求基础数据类型标志
	 * */
	public void getSystemBaseDataInfo(int baseDataLogo) {
		PageInput pageInput = new PageInput();
		pageInput.setPSIZE(sbdPageSize + "");
		switch (baseDataLogo) {
		case 1:
			pageInput.setPNO(smraPageNo + "");
			if (smraPageOutput != null) {
				if (smraPageOutput.getT() != null
						&& !smraPageOutput.getT().equals("")
						&& !smraPageOutput.getT().equals("null")) {
					pageInput.setT(smraPageOutput.getT());
				}
				if (smraPageOutput.getE() != null
						&& !smraPageOutput.getE().equals("")
						&& !smraPageOutput.getE().equals("null")) {
					pageInput.setE(smraPageOutput.getE());
				}
			}
			String smraLastUpdateTime = wcService
					.getLDT(SystemTablesUtil.SysMeetingRoomAddressTableName);
			wcService.getSysMeetingRoomAddress(smraResponseHandler,
					smraLastUpdateTime, pageInput);
			break;
		case 2:
			pageInput.setPNO(smriPageNo + "");
			if (smriPageOutput != null) {
				if (smriPageOutput.getT() != null
						&& !smriPageOutput.getT().equals("")
						&& !smriPageOutput.getT().equals("null")) {
					pageInput.setT(smriPageOutput.getT());
				}
				if (smriPageOutput.getE() != null
						&& !smriPageOutput.getE().equals("")
						&& !smriPageOutput.getE().equals("null")) {
					pageInput.setE(smriPageOutput.getE());
				}
			}
			String smriLastUpdateTime = wcService
					.getLDT(SystemTablesUtil.SysMeetingRoomInfoTableName);
			wcService.getSysMeetingRoomInfo(smriResponseHandler,
					smriLastUpdateTime, pageInput);
			break;
		}
	}

	/**
	 * 请求失败时，恢复对应表的最后更新时间
	 * 
	 * @param systemTableName 当前请求基础数据类表名
	 * */
	public void setSystemBaseTableLDT(String systemTableName) {
		String lastUpdateTime = wcService.getLDT(systemTableName);
		if (lastUpdateTime != null && !lastUpdateTime.equals("")) {
			if (luis != null && luis.size() > 0) {
				for (int i = 0; i < luis.size(); i++) {
					if (luis.get(i) != null && luis.get(i).getName() != null
							&& luis.get(i).getName().equals(systemTableName)) {
						luis.get(i).setLDT(lastUpdateTime);
					}
				}
			}
		}
	}

	/** 1、获取系统会议室地址信息接口返回结果接收器 */
	BaseAsyncHttpResponseHandler<GetSysMeetingRoomAddressResponse> smraResponseHandler = new BaseAsyncHttpResponseHandler<GetSysMeetingRoomAddressResponse>(
			isShowHttpDlg) {
		/** Token验证成功. */
		public void onSuccessTrans(
				GetSysMeetingRoomAddressResponse responseModelVO) {
			Log.i(TAG, "获取会议室地址数据成功");
			if (smraPageNo == 1) {
				smraPageOutput = responseModelVO.getP();
			}
			/** 当前数据请求是否完毕标志 */
			boolean isR = false;
			/* 判断会议室地址数据是否获取成功 */
			if (responseModelVO != null && responseModelVO.getD() != null) {
				/* 将获取的会议室地址数据插入本地数据库 */
				wcService.insertDBMeetingRoomAddress(responseModelVO.getD());
				if (responseModelVO.getP() != null) {
					PageOutput pop = responseModelVO.getP();
					int t = 0;
					try {
						t = Integer.parseInt(pop.getT());
					} catch (NumberFormatException e) {
						e.printStackTrace();
						t = 0;
					}
					/* 判断会议室地址数据是否获取完，未获取完再次获取 */
					if (t > 0 && t > sbdPageSize * smraPageNo) {
						/* 当前类型会议室地址数据请求未完毕，继续请求当前类型数据 */
						smraPageNo = smraPageNo + 1;
						getSystemBaseDataInfo(1);
					} else {
						/* 当前类型会议室地址数据请求完毕，开始请求其它类型数据 */
						isR = true;
					}
				} else {
					/* 当前类型会议室地址数据请求完毕，开始请求其它类型数据 */
					isR = true;
				}
			} else {
				/* 当前类型会议室地址数据请求完毕，开始请求其它类型数据 */
				isR = true;
			}

			if (isR) {
				smraSuccessful = true;
				skipHome();
			}
		};

		/**
		 * 返回结果失败时触发.
		 * 
		 */
		public void onFailureTrans(
				GetSysMeetingRoomAddressResponse responseModelVO) {
			if (responseModelVO != null && responseModelVO.getM() != null
					&& !responseModelVO.getM().equals("")) {
				DialogManager.showToast(mContext, responseModelVO.getM());
			} else {
				DialogManager.showToast(mContext, HttpUtil.SERVER_REQUEST_FAIL);
			}
			smraSuccessful = true;
			setSystemBaseTableLDT(SystemTablesUtil.SysMeetingRoomAddressTableName);
			skipHome();
		};

		/**
		 * Http通讯出现异常时，子类决定是否拦截. <br/>
		 * 
		 */
		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			if (strCode.equals(ResultCodeConstants.NETWORK_0001C)) {
				DialogManager.showToast(mContext, strMsg);
			} else if (strCode.equals(ResultCodeConstants.NETWORK_0002C)) {
				DialogManager.showToast(mContext, strMsg);
			} else if (strCode.equals(ResultCodeConstants.NETWORK_0003C)) {
				DialogManager.showToast(mContext, strMsg);
			} else {
				DialogManager.showToast(mContext,
						HttpUtil.SERVER_REQUEST_NORMAL_ERROR);
			}
			smraSuccessful = true;
			setSystemBaseTableLDT(SystemTablesUtil.SysMeetingRoomAddressTableName);
			skipHome();
		};
	};
	
	
	/** 2、获取服务器时间接口返回结果接收器 */
	BaseAsyncHttpResponseHandler<GetServerTimeResponse> serverTimeHandler = new BaseAsyncHttpResponseHandler<GetServerTimeResponse>() {
		/** Token验证成功. */
		public void onSuccessTrans(
				GetServerTimeResponse responseModelVO) {
			String serverTime =  responseModelVO.getD();
			if (serverTime != null && !serverTime.equals("")) {
				try {
					Date serDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(serverTime);
					long timeDifference = serDate.getTime()-System.currentTimeMillis();
					new SharedPreferenceUtil(ConfigrationList.USERINFO,
							MyApplication.GetApp()).setNameAndValueForLong(
							ConfigrationList.TimeDifference, timeDifference);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		};

		/**
		 * 返回结果失败时触发.
		 * 
		 */
		public void onFailureTrans(
				GetServerTimeResponse responseModelVO) {
			Log.d(TAG, "获取服务器时间失败");
		};

		/**
		 * Http通讯出现异常时，子类决定是否拦截. <br/>
		 * 
		 */
		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			Log.d(TAG, "获取服务器时间接口网络异常");
		};
	};

	/** 2、获取当前用户是否为管理员接口返回结果接收器 */
	BaseAsyncHttpResponseHandler<GetUserIfAddValueServiceAdminResponse> isAdminHandler = new BaseAsyncHttpResponseHandler<GetUserIfAddValueServiceAdminResponse>() {
		/** Token验证成功. */
		public void onSuccessTrans(
				GetUserIfAddValueServiceAdminResponse responseModelVO) {
			Map<String, String> map = responseModelVO.getD();
			String isA = map.get("AdminLogo");
			if (isA != null && isA.equals("Y")) {
				new SharedPreferenceUtil(ConfigrationList.USERINFO,
						MyApplication.GetApp()).setNameAndValue(
						ConfigrationList.ISADMIN, "Y");
			} else {
				new SharedPreferenceUtil(ConfigrationList.USERINFO,
						MyApplication.GetApp()).setNameAndValue(
						ConfigrationList.ISADMIN, "N");
			}
		};

		/**
		 * 返回结果失败时触发.
		 * 
		 */
		public void onFailureTrans(
				GetUserIfAddValueServiceAdminResponse responseModelVO) {
			Log.d(TAG, "获取管理员接口返回失败");
//			new SharedPreferenceUtil(ConfigrationList.USERINFO,
//					MyApplication.GetApp()).setNameAndValue(
//					ConfigrationList.ISADMIN, "N");
		};

		/**
		 * Http通讯出现异常时，子类决定是否拦截. <br/>
		 * 
		 */
		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			Log.d(TAG, "获取管理员接口网络异常");
//			new SharedPreferenceUtil(ConfigrationList.USERINFO,
//					MyApplication.GetApp()).setNameAndValue(
//					ConfigrationList.ISADMIN, "N");
		};
	};

	/** 2、获取系统会议室基础信息接口返回结果接收器 */
	BaseAsyncHttpResponseHandler<GetSysMeetingRoomInfoResponse> smriResponseHandler = new BaseAsyncHttpResponseHandler<GetSysMeetingRoomInfoResponse>(isShowHttpDlg) {
		/** Token验证成功. */
		public void onSuccessTrans(GetSysMeetingRoomInfoResponse responseModelVO) {
			Log.i(TAG, "获取会议室信息数据成功");
			if (smriPageNo == 1) {
				smriPageOutput = responseModelVO.getP();
			}
			/** 当前数据请求是否完毕标志 */
			boolean isR = false;
			/* 判断会议室基础信息数据是否获取成功 */
			if (responseModelVO != null && responseModelVO.getD() != null) {
				/* 将获取的会议室基础信息数据插入本地数据库 */
				wcService.insertDBMeetingRoomInfos(responseModelVO.getD());
				if (responseModelVO.getP() != null) {
					PageOutput pop = responseModelVO.getP();
					int t = 0;
					try {
						t = Integer.parseInt(pop.getT());
					} catch (NumberFormatException e) {
						e.printStackTrace();
						t = 0;
					}
					/* 判断会议室基础信息数据是否获取完，未获取完再次获取 */
					if (t > 0 && t > sbdPageSize * smriPageNo) {
						/* 当前类型会议室基础信息数据请求未完毕，继续请求当前类型数据 */
						smriPageNo = smriPageNo + 1;
						getSystemBaseDataInfo(2);
					} else {
						/* 当前类型会议室基础信息数据请求完毕，开始请求其它类型数据 */
						isR = true;
					}
				} else {
					/* 当前类型会议室基础信息数据请求完毕，开始请求其它类型数据 */
					isR = true;
				}
			} else {
				/* 当前类型会议室基础信息数据请求完毕，开始请求其它类型数据 */
				isR = true;
			}

			if (isR) {
				smriSuccessful = true;
				skipHome();
			}
		};

		/**
		 * 返回结果失败时触发.
		 * 
		 */
		public void onFailureTrans(GetSysMeetingRoomInfoResponse responseModelVO) {
			if (responseModelVO != null && responseModelVO.getM() != null
					&& !responseModelVO.getM().equals("")) {
				DialogManager.showToast(mContext, responseModelVO.getM());
			} else {
				DialogManager.showToast(mContext, HttpUtil.SERVER_REQUEST_FAIL);
			}
			smriSuccessful = true;
			setSystemBaseTableLDT(SystemTablesUtil.SysMeetingRoomInfoTableName);
			skipHome();
		};

		/**
		 * Http通讯出现异常时，子类决定是否拦截. <br/>
		 * 
		 */
		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			if (strCode.equals(ResultCodeConstants.NETWORK_0001C)) {
				DialogManager.showToast(mContext, strMsg);
			} else if (strCode.equals(ResultCodeConstants.NETWORK_0002C)) {
				DialogManager.showToast(mContext, strMsg);
			} else if (strCode.equals(ResultCodeConstants.NETWORK_0003C)) {
				DialogManager.showToast(mContext, strMsg);
			} else {
				DialogManager.showToast(mContext,
						HttpUtil.SERVER_REQUEST_NORMAL_ERROR);
			}
			smriSuccessful = true;
			setSystemBaseTableLDT(SystemTablesUtil.SysMeetingRoomInfoTableName);
			skipHome();
		};
	};

	/**
	 * 跳转到主页
	 */
	private void skipHome() {
		if (isR) {
			Intent intent = new Intent();
			intent.setClass(WelComeActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		} else {
			if (smraSuccessful && smriSuccessful) {
				wcService.insertLastUpdatetimeInfos(luis);
				Intent intent = new Intent();
				intent.setClass(WelComeActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		}
	}

	/**
	 * @see cn.com.zte.emeeting.app.base.activity.AppActivity#finish()
	 */
	@Override
	public void finish() {
		dlgProgressDissmiss();
		super.finish();
	}
}
