package cn.com.zte.emeeting.app.activity;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.zte.android.app.activity.BaseActivity;
import cn.com.zte.emeeting.app.base.activity.AppActivity;
import cn.com.zte.emeeting.app.util.DateFormatUtil;
import cn.com.zte.emeeting.app.views.TopBar;
import cn.com.zte.emeeting.app.views.TopBar.ButtonOnClick;
import cn.com.zte.mobileemeeting.R;
import roboguice.inject.InjectView;

/**
 * Created by Administrator on 16-3-8.
 */
public class ScanSuccessActivity extends AppActivity {

    /**
     * 顶部导航栏
     */
    @InjectView(R.id.scan_success_topbar)
    private TopBar topBar;
    /**
     * 签到结果图片
     */
    @InjectView(R.id.iv_checkedResultImageView)
    private ImageView checkedResultView;
    /**
     * 签到时间
     */
    @InjectView(R.id.scan_success_time)
    private TextView timeView;

    /**
     * 签到结果展示
     */
    @InjectView(R.id.scan_success_result)
    private TextView resultView;

    /**
     * 会议未开始图片
     */
    @InjectView(R.id.iv_meetingNotStart)
    private ImageView meetingNotstart;
    
    /**
     * 签到成功布局
     */
    @InjectView(R.id.sign_rell)
    private RelativeLayout signResultRell;
    
    /**
     * 签到是否成功
     */
	private String isSuccess;

	/**
	 * 签到人数
	 */
	private String checkedNum = "";
	/**
	 * 签到时间
	 */
	private String checkedTime = "";
	
	/**
	 * 会议开始时间
	 */
	private String beginTime = null;
	/**
	 * 会议结束时间
	 */
	private String endTime = null;
	
	private String checkedType = null;
	
	private String resultInfo = null;

    @Override
    protected void initContentView() {
        super.initContentView();
        setContentView(R.layout.acitvity_scan_success);
        Intent intent = getIntent();
        isSuccess = intent.getStringExtra("isSuccess");
        checkedNum = intent.getStringExtra("checkedNum");
        checkedType = intent.getStringExtra("checkedType");
        resultInfo = intent.getStringExtra("ResultInfo");
	 	beginTime = intent.getStringExtra("BD");
	 	endTime = intent.getStringExtra("ED");
    }
    

    @Override
    protected void initViews() {
        super.initViews();
        if (isSuccess.equals("1")) {
			resultView.setText(checkedNum);
			checkedResultView.setImageResource(R.drawable.icon_signin);
			Date date = DateFormatUtil.getServerTime(this);
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			String time = sdf.format(date);
			timeView.setText(time);
		}else {
			checkedResultView.setImageResource(R.drawable.icon_checkinfailed);
			if (resultInfo != null) {
				resultView.setText(resultInfo);
			}else {
				resultView.setText("");
			}
			
			timeView.setVisibility(View.GONE);
		}
    }

    @Override
    protected void initData() {
        super.initData();
        initTopBar();
		if (beginTime != null && endTime != null) {
			
			if (!isInMeeting(beginTime, endTime)) {
				signResultRell.setVisibility(View.GONE);
				meetingNotstart.setVisibility(View.VISIBLE);
			}
		}
    }

    private void initTopBar() {
      
    	topBar.setViewBackGround(TopBar.leftBtnLogo, R.drawable.back);
    	topBar.setViewOnClickListener(TopBar.leftBtnLogo, new ButtonOnClick() {

			@Override
			public void onClick(View view) {
				finish();
			}
		});

    	if (checkedType.equals("1")) {
			
    	topBar.setViewText(TopBar.titleLogo,
				getResourceString(R.string.home_bot_scan));
    	}else {
    		topBar.setViewText(TopBar.titleLogo,
    				getResourceString(R.string.MeetingChecked));
		}
    	topBar.setViewTextColor(TopBar.titleLogo,
				getResourceColor(R.color.TEXTCOLOR_TOPBAR_TITLE_TEXT));
    }

    @Override
    protected void initViewEvents() {
        super.initViewEvents();
    }
	/**
	 * 会议是否已经开始
	 */
	private boolean isInMeeting(String beginTime,String endTime){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Log.d("zl", "beginTime:"+beginTime);
		Log.d("zl", "endTime："+endTime);
		try {
			long begin = sdf.parse(beginTime).getTime();
			long end = sdf.parse(endTime).getTime();
			Date serverTime = DateFormatUtil.getServerTime(this);
			String servertime = sdf.format(serverTime);
			long server = sdf.parse(servertime).getTime();
			Log.d("zl", "beginlong:"+begin);
			Log.d("zl", "endlong："+end);
			Log.d("zl", "serverlong:"+server);
			if (server >= begin && server <= end) {
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

}
