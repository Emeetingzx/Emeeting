package cn.com.zte.emeeting.app.base;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import cn.com.zte.android.app.application.BaseApplication;
import cn.com.zte.android.http.HttpCryptoManager;
import cn.com.zte.android.logmgr.LogManager;
import cn.com.zte.android.orm.DBManager;
import cn.com.zte.android.orm.config.DBConfig;
import cn.com.zte.android.securityauth.interfaces.SSOAuthCheckCallBack;
import cn.com.zte.android.securityauth.manager.SSOAuthCheckManager;
import cn.com.zte.mobileemeeting.R;
import cn.com.zte.emeeting.app.entity.system.MyLocation;
import cn.com.zte.emeeting.app.util.DataConst;
import cn.com.zte.emeeting.app.util.LogTools;
import cn.com.zte.emeeting.app.util.MyToast;
import cn.com.zte.emeeting.app.util.PriorityThreadFactory;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.BitmapAjaxCallback;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.BDNotifyListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;

/******************************************************************************
 * Copyright (C) 2014 ZTE Co.,Ltd All Rights Reserved.
 * 本软件为中兴通讯股份有限公司�?��研制。未经本公司正式书面同意，其他任何个人�?团体 不得使用、复制�?修改或发布本软件.
 * cn.com.zte.android.teamshare.base.client.android.appdemo.base;
 * 
 * import cn.com.zte.android.app.application.BaseApplication; import
 * cn.com.zte.android.app.demo.R; import cn.com.zte.android.orm.DBManager;
 * import cn.com.zte.android.orm.config.DBConfig;
 * 
 * /** MyApplication. <br/>
 * 日期: 2014-5-20 上午10:51:32 <br/>
 * 
 * @author wangenzi
 * @version 1.0
 * @since JDK 1.6
 * @history 2014-5-20 wangenzi 新建
 */

public class MyApplication extends BaseApplication {

	/** 线程池的大小最好设置成为CUP核数的2N */
	private final static int POOL_SIZE = 4;
	/** 设置线程池的最大线程数 */
	private final static int MAX_POOL_SIZE = 4;
	/** 设置线程的存活时间 */
	private final static int KEEP_ALIVE_TIME = 4;

	private Executor mExecutor;
	/**
	 * 应用测试ID
	 */
//	private static final String APPID = "A00258";
	/**
	 * 应用正式ID
	 */
	private static final String APPID = "A00218";

	private boolean isScreenLandscape = false;
	
	private static boolean isFirst = true;
	
	private MyLocation myLocation=new MyLocation();

	/**
	 * @return the isScreenLandscape
	 */
	public boolean isScreenLandscape() {
		return isScreenLandscape;
	}

	/**
	 * @param isScreenLandscape
	 *            the isScreenLandscape to set
	 */
	public void setScreenLandscape(boolean isScreenLandscape) {
		this.isScreenLandscape = isScreenLandscape;
	}

	/** 定义Activity栈集合 */
	private List<Activity> activityList = new LinkedList<Activity>();

	private static MyApplication instance;

	// public static final String myMailAddress="郎健康6396000419";

	@Override
	public void onCreate() {
		Log.d("TAG", System.currentTimeMillis()+"");
		super.onCreate();
		HttpCryptoManager.LOGCAT_PLAIN_JSON_FLAG = true;
		HttpCryptoManager.IS_PRODUCTION = false;
		HttpCryptoManager.DEBUG_FLAG = true;
		System.out.println("app创建");
		getLocation();
		Log.d("TAG", System.currentTimeMillis()+"");
	}

	public static MyApplication GetApp() {
		if (null == instance) {
			instance = ((MyApplication) MyApplication.getInstance());
		}
		return instance;
	}

	@Override
	public void initCacheManager() {
		super.initCacheManager();
		
		// set the max number of concurrent network connections, default is 4
		AjaxCallback.setNetworkLimit(30);
		
		// set the max number of icons (image width <= 50) to be cached in
		BitmapAjaxCallback.setIconCacheLimit(200);
		
		// set the max number of images (image width > 50) to be cached in
		BitmapAjaxCallback.setCacheLimit(200);

		// set the max size of an image to be cached in memory, default is 1600
		BitmapAjaxCallback.setPixelLimit(400 * 400);
		
		//set the max size of the memory cache, default is 1M pixels (4MB)
		BitmapAjaxCallback.setMaxPixelLimit(4000000);
	}

	/**
	 * 初始化数据库管理�? <br/>
	 * 日期: 2014-5-19 下午4:32:11 <br/>
	 * 
	 * @author wangenzi
	 * @since JDK 1.6
	 */
	@Override
	public void initDBManager() {
		DBConfig sharedDBConfig = new DBConfig(
				R.string.database_shared_db_name_suffix,
				R.integer.database_shared_db_version,
				R.array.database_shared_tables);

		DBConfig userScopeDBConfig = new DBConfig(
				R.string.database_user_scope_db_name_suffix,
				R.integer.database_user_scope_db_version,
				R.array.database_user_scope_tables);

		BaseApplication.getInstance().setDBManager(
				new DBManager(this, sharedDBConfig, userScopeDBConfig));

		// 初始化公共数据库操作组件
		BaseApplication.getInstance().getDbManager().initSharedDBHelper();
	}

	/**
	 * SSL登录检查. <br/>
	 * 日期: 2014-6-3 上午12:10:13 <br/>
	 * 
	 * @author wangenzi
	 * @since JDK 1.6
	 */
	public void checkSSOLogin(final Context context) {

		SSOAuthCheckCallBack authCheckCallBack = new SSOAuthCheckCallBack() {

			/**
			 * 应用关闭前回调，可以做一些现场保护数据操作.
			 * 
			 * @see cn.com.zte.android.securityauth.interfaces.SSOAuthCheckCallBack#onAppClosePre()
			 */
			@Override
			public void onAppClosePre() {
				// 应用关闭前回调，可以做一些现场保护数据操作.
			}

			@Override
			public void onMOANotInstalled() {
				// 弹出安装MOA的提醒
				MyToast.show(context, "请先安装MOA");
			}

			@Override
			public void onAuthSuccess() {
				Log.i("单点登陆成功", "onAuthSuccess...");
				// MyToast.show(getApplicationContext(), "单点登陆成功");

			}

			/**
			 * Http通讯错误回调.
			 * 
			 */
			@Override
			public void onHttpError(String strCode, String strMsg) {
				// 弹出错误信息
				MyToast.show(getApplicationContext(), strMsg);
			}

			public void onAuthFailureTrans() {
				// TODO Auto-generated method stub
				
			}

			public void onFailure(String arg0) {
				// TODO Auto-generated method stub
				
			}
		};

		// 构造SSOAuthCheckManager
		SSOAuthCheckManager acm = new SSOAuthCheckManager(this, getAppid(),
				authCheckCallBack, false) {

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

	/**
	 * 获取appid
	 * 
	 * @return appid appid
	 */
	public static String getAppid() {
		return APPID;
	}

	/** 记录error级别错误信息 */
	public static void recordLogError(String errorString) {
		LogManager logManager = BaseApplication.getInstance().getLogManager();
		// logManager.logError(errorString);
	}

	/** 添加Activity 到容器栈中 */
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	/** 遍历所有Activity 并finish */
	public void exit() {
		if (activityList != null && activityList.size() > 0) {
			for (Activity activity : activityList) {
				activity.finish();
			}
		}
		System.exit(0);
	}

	/** 在线程池中执行线程 */
	public void executeRunnable(Runnable command) {
		if (mExecutor == null) {
			// 创建线程池工厂
			ThreadFactory factory = new PriorityThreadFactory("thread-pool",
					android.os.Process.THREAD_PRIORITY_BACKGROUND);
			// 创建工作队列
			BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<Runnable>();
			mExecutor = new ThreadPoolExecutor(POOL_SIZE, MAX_POOL_SIZE,
					KEEP_ALIVE_TIME, TimeUnit.SECONDS, workQueue, factory);
		}
		mExecutor.execute(command);
	}

	public Context getContext(){
		return getApplicationContext();
	}

	/**
	 * @return the isFirst
	 */
	public static boolean isFirst() {
		return isFirst;
	}

	/**
	 * @param isFirst the isFirst to set
	 */
	public static void setFirst(boolean isFirst) {
		MyApplication.isFirst = isFirst;
	}
	
	
	
	private LocationClient locationClient;
	/** 定位 */
	private void getLocation()
	{
		SDKInitializer.initialize(getApplicationContext());
		locationClient = new LocationClient(this);
		locationClient.registerLocationListener(new MyLocationListenner());
		LocationClientOption option = new LocationClientOption();
		option.setScanSpan(3000);
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
//		option.setIsNeedAddress(true);
//		option.setAddrType("all");
		locationClient.setLocOption(option);
//		locationClient.registerNotify(new BDNotifyListener() {
//			/**
//			 * @see com.baidu.location.BDNotifyListener#onNotify(com.baidu.location.BDLocation, float)
//			 */
//			@Override
//			public void onNotify(BDLocation arg0, float arg1) {
//				// TODO Auto-generated method stub
//				super.onNotify(arg0, arg1);
//			}
//		});
		locationClient.start();
//		locationClient.requestOfflineLocation();
//		locationClient.requestLocation();
	}
	
	/**
	 * 定位监听器
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			LogTools.i("myapp", "location");
			if (location == null)
				// || mapView == null
				return;
			// MyLocationData locData = new MyLocationData.Builder()
			// .accuracy(location.getRadius())
			// 此处设置开发者获取到的方向信息，顺时针0-360
			// .direction(100).latitude(location.getLatitude())
			// .longitude(location.getLongitude()).build();

			// mBaiduMap.setMyLocationData(locData);

//			纬度：34.099587经度：108.837093
			
			LogTools.d("经纬度：",
					"纬度：" + location.getLatitude() + "经度："
							+ location.getLongitude()
							+"\n"
					+location.getCity()
					+"定位方式:"+location.getNetworkLocationType()
					+"精确半径:"+location.getRadius()
					);
			
			if(location.getLatitude()!=4.9E-324)
			{
				if(location.getLatitude()!=myLocation.getLatitude()||location.getLongitude()!=myLocation.getLongitude())
				{
					Intent it = new Intent(DataConst.ACTION_MYLOCATION_CHANGED);
					sendBroadcast(it);
					LogTools.i("location", "change广播");
				}
				
				myLocation.setBaiduLocation(location);
				myLocation.setLatitude(location.getLatitude());
				myLocation.setLongitude(location.getLongitude());
				
			}else
			{
				myLocation.setLatitude(0);
				myLocation.setLongitude(0);
			}
			
			
			
			// MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
			// mBaiduMap.setMapStatus(u);
		}
	}
	
	/**
	 * @see cn.com.zte.android.app.application.BaseApplication#onExitApplication()
	 */
	@Override
	public void onExitApplication() {
		super.onExitApplication();
	}

	/**
	 * @return the myLocation
	 */
	public MyLocation getMyLocation() {
		return myLocation;
	}

	/**
	 * @param myLocation the myLocation to set
	 */
	public void setMyLocation(MyLocation myLocation) {
		this.myLocation = myLocation;
	}

	/**
	 * @return 获取当前存活的activity列表
	 */
	public List<Activity> getActivityList() {
		return activityList;
	}

	
}
