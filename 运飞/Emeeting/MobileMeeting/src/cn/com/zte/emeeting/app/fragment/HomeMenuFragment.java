package cn.com.zte.emeeting.app.fragment;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.exception.AfinalException;

import com.androidquery.AQuery;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import roboguice.inject.InjectView;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.zte.android.app.fragment.BaseFragment;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.android.resource.inflater.BaseLayoutInflater;
import cn.com.zte.android.widget.dialog.DialogManager;
import cn.com.zte.emeeting.app.activity.MainActivity;
import cn.com.zte.emeeting.app.appservice.HomeMenuFragmentService;
import cn.com.zte.emeeting.app.appservice.WelComeService;
import cn.com.zte.emeeting.app.base.fragment.AppFragment;
import cn.com.zte.emeeting.app.util.DataConst;
import cn.com.zte.emeeting.app.util.DensityUtil;
import cn.com.zte.emeeting.app.util.LogTools;
import cn.com.zte.emeeting.app.views.RoundImageView;
import cn.com.zte.mobilebasedata.request.HttpUtil;
import cn.com.zte.mobileemeeting.R;

/**
 * 首页菜单容器
 *
 * @author sun.li
 */
public class HomeMenuFragment extends AppFragment implements OnClickListener {

    /**
     * Tag
     */
    private static final String TAG = HomeMenuFragment.class.getSimpleName();

    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 主页菜单逻辑处理类
     */
    private HomeMenuFragmentService mService;

    /**
     * 用户头像
     */
    @InjectView(R.id.home_menu_userinfo)
    private LinearLayout userinfoLayout;

    /**
     * 用户头像控件
     */
    @InjectView(R.id.home_menu_img)
    private RoundImageView home_menu_img;
    /**
     * 用户头像控件
     */
    @InjectView(R.id.home_menu_img_Tmp)
    private ImageView home_menu_img_Tmp;
    /**
     * 姓名
     */
    @InjectView(R.id.home_menu_name)
    private TextView emptyName;
    /**
     * 员工工号
     */
    @InjectView(R.id.home_menu_num)
    private TextView emptyNum;
    /**
     * 我的会议室
     */
    @InjectView(R.id.home_menu_melayout)
    private LinearLayout meLayout;
    /**
     * 会议室预定
     */
    @InjectView(R.id.home_menu_getmeetlayout)
    private LinearLayout getMeetLayout;
    /**
     * 扫码签到
     */
    @InjectView(R.id.home_menu_scanlayout)
    private LinearLayout scanLayout;
    /**
     * 设置
     */
    @InjectView(R.id.home_menu_setlayout)
    private LinearLayout setLayout;
    /**
     * 摇一摇
     */
    @InjectView(R.id.home_menu_shakelayout)
    private LinearLayout shakeLayout;

    /**
     * 增值服务
     */
    @InjectView(R.id.home_menu_valueaddlayout)
    private LinearLayout valueAddLayout;
    /**
     * 电话会议桥
     */
    @InjectView(R.id.home_menu_videolayout)
    private LinearLayout phoneVideoLayout;

    /**
     * 根布局
     */
    @InjectView(R.id.home_menu_root_layout)
    private RelativeLayout rootLayout;

    @Override
    protected View onCreateView(BaseLayoutInflater arg0, ViewGroup arg1,
                                Bundle arg2) {
        mService = new HomeMenuFragmentService(getActivity());
        return arg0.inflate(R.layout.fragment_home_menu, null);
    }

    @Override
    protected void initData() {
        super.initData();
        mContext = getActivity();

        int[] densitys = DensityUtil.getDensityWidthHeight(mContext);
        int rootWidth = (densitys[0] * 13) / 18;
        rootLayout.getLayoutParams().width = rootWidth;
        WelComeService service = new WelComeService(mContext);
        if (service.getUserInfo() != null) {
            if (service.getUserInfo().getUID() != null) {
                emptyNum.setText(service.getUserInfo().getUID());
            } else {
                emptyNum.setText("");
            }
            if (service.getUserInfo().getCNM() != null) {
                emptyName.setText(service.getUserInfo().getCNM());
            } else {
                emptyName.setText("");
            }
        } else {
            emptyNum.setText("");
            emptyName.setText("");
        }
    }

    @Override
    protected void initViews() {
        super.initViews();
        getFacePhoto();
        processSelectState(R.id.home_menu_getmeetlayout);
    }


    @Override
    protected void initViewEvents() {
        super.initViewEvents();
        meLayout.setOnClickListener(this);
        getMeetLayout.setOnClickListener(this);
        setLayout.setOnClickListener(this);
        valueAddLayout.setOnClickListener(this);
        phoneVideoLayout.setOnClickListener(this);
        shakeLayout.setOnClickListener(this);
        scanLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MainActivity mainActivity = (MainActivity) getActivity();
        switch (v.getId()) {
            case R.id.home_menu_scanlayout:
                mainActivity.hiddenLeft();
                mainActivity.displayFragment(MainActivity.SCLOGO);
                break;
            case R.id.home_menu_getmeetlayout:
                mainActivity.hiddenLeft();
                mainActivity.displayFragment(MainActivity.GMLOGO);
                break;
            case R.id.home_menu_valueaddlayout:
                mainActivity.hiddenLeft();
                mainActivity.displayFragment(MainActivity.VALOGO);
                break;
            case R.id.home_menu_melayout:
                mainActivity.hiddenLeft();
                mainActivity.displayFragment(MainActivity.MELOGO);
                break;
            case R.id.home_menu_setlayout:
                mainActivity.hiddenLeft();
                mainActivity.displayFragment(MainActivity.SETLOGO);
                break;
            case R.id.home_menu_videolayout:
                mainActivity.hiddenLeft();
                mainActivity.displayFragment(MainActivity.PHONELOGO);
                break;
            case R.id.home_menu_shakelayout:
                mainActivity.hiddenLeft();
                mainActivity.displayFragment(MainActivity.SKLOGO);
                break;
        }
        processSelectState(v.getId());
    }

    /**
     * 处理选中状态
     */
    public void processSelectState(int flag) {
        // TODO Auto-generated method stub
        meLayout.setSelected(false);
        getMeetLayout.setSelected(false);
        setLayout.setSelected(false);
        valueAddLayout.setSelected(false);
        phoneVideoLayout.setSelected(false);
        shakeLayout.setSelected(false);
        scanLayout.setSelected(false);
        switch (flag) {
            case R.id.home_menu_getmeetlayout:
                getMeetLayout.setSelected(true);
                break;
            case R.id.home_menu_scanlayout:
                scanLayout.setSelected(true);
                break;
            case R.id.home_menu_valueaddlayout:
                valueAddLayout.setSelected(true);
                break;
            case R.id.home_menu_melayout:
                meLayout.setSelected(true);
                break;
            case R.id.home_menu_setlayout:
                setLayout.setSelected(true);
                break;
            case R.id.home_menu_videolayout:
                phoneVideoLayout.setSelected(true);
                break;
            case R.id.home_menu_shakelayout:
                shakeLayout.setSelected(true);
                break;
        }
    }

    /**
     * 供Activity调用
     */
    public void processSelectForMain(int flag) {
        switch (flag) {
            case MainActivity.VALOGO:
                processSelectState(R.id.home_menu_valueaddlayout);
                break;
            case MainActivity.GMLOGO:
                processSelectState(R.id.home_menu_getmeetlayout);
                break;
            case MainActivity.SETLOGO:
                processSelectState(R.id.home_menu_setlayout);
                break;
            case MainActivity.MELOGO:
                processSelectState(R.id.home_menu_melayout);
                break;
            case MainActivity.PHONELOGO:
                processSelectState(R.id.home_menu_videolayout);
                break;
            case MainActivity.SKLOGO:
                processSelectState(R.id.home_menu_shakelayout);
                break;
        }
    }

//	/** 获取moa头像*/
    //http://share.zte.com.cn/tech/rest/auth/userinfo_image?userId=10114660

    /**
     * 获取moa头像
     */
    private void getFacePhoto() {
        String uid = "";
        WelComeService service = new WelComeService(mContext);
        if (service.getUserInfo() != null) {
            if (service.getUserInfo().getUID() != null) {
                uid = service.getUserInfo().getUID();
//				uid = "10114660";
            }
        }

        if (TextUtils.isEmpty(uid)) {
            return;
        }

        String url = HttpUtil.url_moa_facephoto + "?U=" + uid;

        try {

            LogTools.d(TAG, "头像地址:" + url);
//							FinalBitmap bitmapUtil = FinalBitmap.create(mContext);
//							bitmapUtil.configLoadingImage(R.drawable.icon_default_user);
//							bitmapUtil.configLoadfailImage(R.drawable.icon_default_user);
//							bitmapUtil.display(home_menu_img, arg0.result);

            BitmapUtils bitmapUtils = new BitmapUtils(mContext);
            bitmapUtils.clearMemoryCache();
            bitmapUtils.clearDiskCache();
            bitmapUtils.configDefaultLoadFailedImage(R.drawable.icon_default_user);
            bitmapUtils.configDefaultLoadingImage(R.drawable.icon_default_user);
            bitmapUtils.configDiskCacheEnabled(true);
            bitmapUtils.configMemoryCacheEnabled(false);
//							bitmapUtils.display(home_menu_img, arg0.result);
            bitmapUtils.display(home_menu_img_Tmp, url, new BitmapLoadCallBack<ImageView>() {

                @Override
                public void onLoadCompleted(ImageView arg0,
                                            String arg1, Bitmap arg2,
                                            BitmapDisplayConfig arg3,
                                            BitmapLoadFrom arg4) {
                    // TODO Auto-generated method stub
//									arg0.setImageBitmap(arg2);
                    home_menu_img.setImageBitmap(arg2);
                }


                @Override
                public void onLoadFailed(ImageView arg0,
                                         String arg1, Drawable arg2) {
                    // TODO Auto-generated method stub
                    home_menu_img.setImageResource(R.drawable.icon_default_user);
                }
            });

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

//        HttpUtils utils = new HttpUtils(HttpUtil.TimeOut);
//        utils.configSoTimeout(HttpUtil.SocketTimeOut);
//		RequestParams params = new RequestParams("utf-8");
//		params.addBodyParameter("userId", uid);
//		
//        utils.send(HttpMethod.GET, HttpUtil.url_moa_facephoto + "?U=" + uid,
//                new RequestCallBack<String>() {
//
//
//                    @Override
//                    public void onFailure(HttpException arg0, String arg1) {
//                        LogTools.d(TAG, "获取moa头像失败");
//
//                    }
//
//                    @Override
//                    public void onSuccess(ResponseInfo<String> arg0) {
//
//
//                    }
//                }
//        );

    }


    /**
     * 刷新用户信息
     */
    public void refreshView() {
        // TODO Auto-generated method stub
        WelComeService service = new WelComeService(mContext);
        if (service.getUserInfo() != null) {
            if (service.getUserInfo().getUID() != null) {
                emptyNum.setText(service.getUserInfo().getUID());
            } else {
                emptyNum.setText("");
            }
            if (service.getUserInfo().getCNM() != null) {
                emptyName.setText(service.getUserInfo().getCNM());
            } else {
                emptyName.setText("");
            }
        } else {
            emptyNum.setText("");
            emptyName.setText("");
        }
    }

}


