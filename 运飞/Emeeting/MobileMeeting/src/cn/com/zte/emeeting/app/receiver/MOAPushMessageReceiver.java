package cn.com.zte.emeeting.app.receiver;

import com.ab.util.AbStrUtil;
import com.google.gson.Gson;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import cn.com.zte.android.common.util.JsonUtil;
import cn.com.zte.android.pushclient.model.MessageNotify;
import cn.com.zte.android.pushclient.model.PushMessage;
import cn.com.zte.android.pushclient.receiver.AbstractPushMessageReceiver;
import cn.com.zte.emeeting.app.activity.MainActivity;
import cn.com.zte.emeeting.app.activity.MeetingDetailActivity;
import cn.com.zte.emeeting.app.activity.MeetingDetailMessageActivity;
import cn.com.zte.emeeting.app.activity.PushMessageActivity;
import cn.com.zte.emeeting.app.activity.ServiceAdminActivity;
import cn.com.zte.emeeting.app.base.ConfigrationList;
import cn.com.zte.emeeting.app.response.entity.MeetingInfo;
import cn.com.zte.emeeting.app.response.entity.NotifyInfo;
import cn.com.zte.emeeting.app.util.SharedPreferenceUtil;
import cn.com.zte.emeeting.app.views.TopBar;
import cn.com.zte.mobileemeeting.R;

public class MOAPushMessageReceiver extends AbstractPushMessageReceiver {
	private static final String TAG = MOAPushMessageReceiver.class.getSimpleName();

	/**
	 * 非通知类型的消息内容，在此处进行消息逻辑处理(后台处理).
	 * 
	 * @see cn.com.zte.android.pushclient.receiver.AbstractPushMessageReceiver#onMessageContent(android.content.Context,
	 *      cn.com.zte.android.pushclient.model.PushMessage, java.lang.String)
	 */
	@Override
	protected void onMessageContentInBackground(Context context, PushMessage message, String messageContent) {
		// TODO  自己的业务逻辑
	}

	/**
	 * 通知类型的内容，需要在通知栏提示，点击通知栏的通知，执行的目标Intent，需要指定目标Intent组件.
	 * 
	 * @see cn.com.zte.android.pushclient.receiver.AbstractPushMessageReceiver#onNotifyCreateTargetIntent(android.content.Context,
	 *      cn.com.zte.android.pushclient.model.PushMessage,
	 *      cn.com.zte.android.pushclient.model.MessageNotify)
	 */
	@Override
	protected Intent onNotifyCreateTargetIntent(Context context, PushMessage message, MessageNotify mn) {
		
		Log.d(TAG, "你到底能否接收消息呢？");
		
		if (message != null) {
			// 获取到推送接收的JSON字符串 
			String josn = message.getMsgContent();
			if (!AbStrUtil.isEmpty(josn)) {
				Log.d(TAG, josn);
                // 获取到通知实体类
                NotifyInfo notifyInfo = JsonUtil.fromJson(josn, NotifyInfo.class);
                // 获取会议类型
                String nt = notifyInfo.getNT();
                int code = 0;
				try {
					code = Integer.valueOf(nt);
				} catch (Exception e) {
					e.printStackTrace();
				}
                Intent intent = new Intent();
                switch (code) {
				case 0: //会议相关通知
                    String meetingnotifytype = notifyInfo.getMNT();
                    if (meetingnotifytype.equals("0")) {
                    	if (notifyInfo.getMT()!=null&&notifyInfo.getMT().equals("0")) {
							//普通会议
                    		MeetingInfo info = new MeetingInfo();
                    		info.setMID(notifyInfo.getMID());
                    		intent.setClass(context, MeetingDetailActivity.class);
                    		intent.putExtra("data", info);
						}else if (notifyInfo.getMT()!=null&&notifyInfo.getMT().equals("1")) {
							//视频会议
							intent.setClass(context, MeetingDetailMessageActivity.class);
							intent.putExtra("MID", notifyInfo.getMID());
						}else {
							//否则跳转到首页我的会议
							intent.setClass(context, MainActivity.class);
							intent.putExtra(MainActivity.FLAG,MainActivity.MELOGO);
						}
					}else {
						//会议取消或者结束跳转到首页预订会议
						intent.setClass(context, MainActivity.class);
						intent.putExtra(MainActivity.FLAG,MainActivity.GMLOGO);
					}
					break;
                case 1: // 增值服务通知
                	
                	if (notifyInfo.getVNT()!=null&&notifyInfo.getVNT().equals("0")&&
                			new SharedPreferenceUtil(ConfigrationList.USERINFO, context).getString(ConfigrationList.ISADMIN).equals("Y")) {
                		//当前用户是管理员，且是新订单
                		intent.setClass(context, ServiceAdminActivity.class);
                	}else {
                		//订单状态改变，跳转到首页增值服务
                		intent.setClass(context, MainActivity.class);
    					intent.putExtra(MainActivity.FLAG,MainActivity.VALOGO);
					}
                	break;
				default:
					break;
				}
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                return intent;
                
			}
		}

		return null;
	}

	/**
	 * 通知栏的图标资源ID.
	 * 
	 * @see cn.com.zte.android.pushclient.receiver.AbstractPushMessageReceiver#onNotifyCreateIconResId(android.content.Context,
	 *      cn.com.zte.android.pushclient.model.PushMessage,
	 *      cn.com.zte.android.pushclient.model.MessageNotify)
	 */
	@Override
	protected int onNotifyCreateIconResId(Context context, PushMessage message,
			MessageNotify mn) {
		return R.drawable.ico_logo;
	}

	/**
	 * 通知提示的TickerText.
	 * 
	 * @see cn.com.zte.android.pushclient.receiver.AbstractPushMessageReceiver#onNotifyCreateTickerText(android.content.Context,
	 *      cn.com.zte.android.pushclient.model.PushMessage,
	 *      cn.com.zte.android.pushclient.model.MessageNotify)
	 */
	@Override
	protected String onNotifyCreateTickerText(Context context, PushMessage message, MessageNotify mn) {
		String TickerText = "";
		if (message!=null){
			String json = message.getMsgContent();
			if (!AbStrUtil.isEmpty(json)){
				NotifyInfo notifyInfo =JsonUtil.fromJson(json,  NotifyInfo.class);
				String mnt = notifyInfo.getNT();
				int code = 0;
				try {
					code = Integer.valueOf(mnt);
				} catch (Exception e) {
					e.printStackTrace();
				}
				switch (code) {
				case 0: // 预定通知、提前30分钟通知、变更通知
					TickerText = context.getResources().getString(R.string.AMReservations);
					break;
                case 1: // 增值服务
                	TickerText = context.getResources().getString(R.string.AMUnsubscribe);
                	break;
//                case 2: // 签到通知
//                	TickerText = context.getResources().getString(R.string.AMRegistration);
//                	break;
				default:
					TickerText = context.getResources().getString(R.string.AMRegistration);
					break;
				}
				
			}
		}

		return TickerText;
	}

	/**
	 * 设置最新的通知内容，并和PendingIntent完成绑定. <br/>
	 */
	protected void onNotifyCreateLatestEventInfo(Context con, PushMessage pushMessage, MessageNotify mn, Notification notice,
			PendingIntent resultPendingIntent) {
		if (notice!=null){
			String strTickerText = onNotifyCreateTickerText(con, pushMessage, mn);
			notice.setLatestEventInfo(con, strTickerText, pushMessage.getMsgNotifyAlert()==null?"":pushMessage.getMsgNotifyAlert(), resultPendingIntent);
		}
	}


}
