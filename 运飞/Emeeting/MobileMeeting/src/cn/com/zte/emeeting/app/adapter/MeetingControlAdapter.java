package cn.com.zte.emeeting.app.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.android.widget.dialog.DialogManager;
import cn.com.zte.emeeting.app.appservice.ValueAddBookService;
import cn.com.zte.emeeting.app.dao.shared.SysDBMeetingRoomInfoDBDao;
import cn.com.zte.emeeting.app.database.entity.shared.DBMeetingRoomInfo;
import cn.com.zte.emeeting.app.dialog.DlgServiceConfirmBook;
import cn.com.zte.emeeting.app.dialog.DlgServiceConfirmBook.OnConfirmDlgListener;
import cn.com.zte.emeeting.app.response.entity.MeetingJoinInfo;
import cn.com.zte.emeeting.app.response.entity.MeetingRoomInfo;
import cn.com.zte.emeeting.app.response.entity.MyAddValueInfo;
import cn.com.zte.emeeting.app.response.instrument.GetAddValueServiceOperateResponse;
import cn.com.zte.emeeting.app.util.EmeetingToast;
import cn.com.zte.emeeting.app.views.SlidingLayout;
import cn.com.zte.emeeting.app.views.SlideCutListView.RemoveDirection;
import cn.com.zte.mobileemeeting.R;

/**
 * 会议控制适配器
 * 
 * @author LangK
 */
public class MeetingControlAdapter extends BaseAdapter {
	/** 上下文 */
	private Context mContext;
	/** 集合列表 */
	private List<MeetingJoinInfo> lists;

	public List<MeetingJoinInfo> getLists() {
		return lists;
	}

	public void setLists(List<MeetingJoinInfo> lists) {
		this.lists = lists;
	}

	/** 解析器 */
	LayoutInflater layoutInflater;
	/**
	 * 屏幕宽度
	 */
	private int screenWidth;

	private EventListener eventListener;

	private SysDBMeetingRoomInfoDBDao roomInfoDBDao = new SysDBMeetingRoomInfoDBDao();

	public MeetingControlAdapter(Context mContext, List<MeetingJoinInfo> lists) {
		this.mContext = mContext;
		this.lists = lists;
		layoutInflater = LayoutInflater.from(mContext);
		DisplayMetrics dMetrics = new DisplayMetrics();
		((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay().getMetrics(dMetrics);
		screenWidth = dMetrics.widthPixels;
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	@Override
	public Object getItem(int arg0) {
		return lists.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder holder;

		if (view == null) {
			holder = new ViewHolder();
			view = layoutInflater.inflate(R.layout.item_meeting_control, null);
			holder.cSlidingLayout = (SlidingLayout) view.findViewById(R.id.item_slidinglayout);
			holder.disConHintLayout = (LinearLayout) view.findViewById(R.id.item_control_disconnecthintlayout);
			holder.disConHintView = (TextView) view.findViewById(R.id.item_control_disconnecthint);
			holder.nameView = (TextView) view
					.findViewById(R.id.item_control_name);
			holder.stateView = (TextView) view
					.findViewById(R.id.item_control_state_text);
			holder.operationLayout01 = (FrameLayout) view
					.findViewById(R.id.item_control_operation1);
			holder.operationLayout02 = (FrameLayout) view
					.findViewById(R.id.item_control_operation2);
			holder.callImg = (ImageView) view
					.findViewById(R.id.item_call_state_img);
			holder.stateImg = (ImageView) view
					.findViewById(R.id.item_control_state_img);

			holder.operationImg01 = (ImageView) view
					.findViewById(R.id.item_control_operation1_img);
			holder.operationImg02 = (ImageView) view
					.findViewById(R.id.item_control_operation2_img);

			holder.operationText01 = (TextView) view
					.findViewById(R.id.item_control_operation1_text);
			holder.operationText02 = (TextView) view
					.findViewById(R.id.item_control_operation2_text);

			holder.callstateLayout = (LinearLayout) view
					.findViewById(R.id.item_control_calllayout);
			holder.deleteLayout = (LinearLayout) view
					.findViewById(R.id.item_control_deletelayout);
			holder.contentLayout = (RelativeLayout) view
					.findViewById(R.id.item_control_content);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		MeetingJoinInfo info = lists.get(position);

		initView(holder,position,info);

		initViewEvent(view,holder, position, info);

		return view;
	}

	
	
	
	/**
	 * 初始化视图
	 * 
	 * @param holder
	 * @param position
	 */
	private void initView(ViewHolder holder, int position, MeetingJoinInfo info) {
		// TODO Auto-generated method stub
		AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
		alphaAnimation.setDuration(1000);
		alphaAnimation.setRepeatCount(Integer.MAX_VALUE);
		alphaAnimation.setRepeatMode(Animation.REVERSE);
		holder.operationImg01.setAnimation(alphaAnimation);
		holder.operationText01.setAnimation(alphaAnimation);
		
		if (info.getNO()!=null&&info.getNO().length()>7) {
			try {
				if (info.getNO().substring(0, 2).equals("90")) {
					String no = info.getNO().substring(2);
					if (no.equals(info.getTN())) {
						holder.nameView.setText(info.getTN());
					}else {
						holder.nameView.setText(info.getTN()+" "+no);
					}
				}else {
					holder.nameView.setText(info.getTN());
				}
			} catch (Exception e) {
				// TODO: handle exception
				holder.nameView.setText(info.getTN());
			}
		}else {
			holder.nameView.setText(info.getTN());
		}
		
		if (info.getET()!=null&&!info.getET().equals("")) {
			holder.disConHintLayout.setVisibility(View.VISIBLE);
			holder.disConHintView.setText(mContext.getString(R.string.string_will_at)+info.getET()+mContext.getString(R.string.string_disconnect));
		}else {
			holder.disConHintLayout.setVisibility(View.GONE);
		}

		if (info.getST() == null
				|| info.getST().equals(MeetingJoinInfo.HANGUPSTATE)) {
			// 挂断状态
			holder.stateImg.setImageResource(R.drawable.icon_state_disconnect);
			holder.stateView.setText(R.string.disconnect);
			holder.callstateLayout.setVisibility(View.GONE);

			holder.operationText01.setText(R.string.call);
			holder.operationImg01.setImageResource(R.drawable.icon_call);
			holder.operationImg02.setImageResource(R.drawable.icon_quiet);
			Drawable image;
			image = holder.operationImg02.getDrawable();
			image.mutate().setAlpha(180);
			holder.operationImg02.setImageDrawable(image);
			holder.operationText02.setText(R.string.quiet);
			holder.operationText02.setEnabled(false);
			holder.operationLayout02.setEnabled(false);
			
			alphaAnimation.cancel();
			
		} else if (info.getST().equals(MeetingJoinInfo.CALLINGSTATE)) {
			// 拨号中状态
			
			alphaAnimation.startNow();
			
			holder.stateImg.setImageResource(R.drawable.icon_state_connection);
			holder.stateView.setText(R.string.calling);
			holder.callstateLayout.setVisibility(View.GONE);
			holder.callImg.setImageResource(R.drawable.icon_state_call);

			holder.operationText01.setText(R.string.call);
			holder.operationImg01.setImageResource(R.drawable.icon_call);
			holder.operationImg02.setImageResource(R.drawable.icon_hangup);
			holder.operationText02.setText(R.string.hangup);
			holder.operationText02.setEnabled(true);
			holder.operationLayout02.setEnabled(true);
			

		} else if (info.getST().equals(MeetingJoinInfo.CALLSTATE)) {
			// 拨通中非静音状态
			alphaAnimation.cancel();
			holder.stateImg.setImageResource(R.drawable.icon_state_connection);
			holder.stateView.setText(R.string.connection);
			holder.callstateLayout.setVisibility(View.VISIBLE);
			holder.callImg.setImageResource(R.drawable.icon_state_call);

			holder.operationText01.setText(R.string.hangup);
			holder.operationImg01.setImageResource(R.drawable.icon_hangup);
			holder.operationImg02.setImageResource(R.drawable.icon_quiet);
			holder.operationText02.setText(R.string.quiet);
			holder.operationText02.setEnabled(true);
			holder.operationLayout02.setEnabled(true);

		} else if (info.getST().equals(MeetingJoinInfo.QUEITSTATE)) {
			// 拨通中静音状态
			alphaAnimation.cancel();
			holder.stateImg.setImageResource(R.drawable.icon_state_connection);
			holder.stateView.setText(R.string.connection);
			holder.callstateLayout.setVisibility(View.VISIBLE);
			holder.callImg.setImageResource(R.drawable.icon_state_quiet);

			holder.operationText01.setText(R.string.hangup);
			holder.operationImg01.setImageResource(R.drawable.icon_hangup);
			holder.operationImg02.setImageResource(R.drawable.icon_talk);
			holder.operationText02.setText(R.string.restore_call);
			holder.operationText02.setEnabled(true);
			holder.operationLayout02.setEnabled(true);
		}

	}

	/**
	 * 初始化事件
	 */
	private void initViewEvent(final View view,final ViewHolder holder, final int position,
			final MeetingJoinInfo info) {
		// TODO Auto-generated method stub
		holder.operationLayout01.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (info.getST() == null
						|| info.getST().equals(MeetingJoinInfo.HANGUPSTATE)) {
					// 挂断状态,呼叫操作
					if (eventListener != null) {
						eventListener.callEvent(position, info);
					}
					info.setST(MeetingJoinInfo.CALLINGSTATE);
					notifyDataSetChanged();
				} else if (!info.getST().equals(MeetingJoinInfo.CALLINGSTATE)) {
					// 挂断操作
					if (eventListener != null) {
						eventListener.hangupEvent(position, info);
					}
				}
			}
		});

		holder.operationLayout02.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (info.getST().equals(MeetingJoinInfo.CALLINGSTATE)) {
					// 挂断操作
					if (eventListener != null) {
						eventListener.hangupEvent(position, info);
					}
				}else if (info.getST() == null
						|| info.getST().equals(MeetingJoinInfo.CALLSTATE)) {
					// 非静音状态，静音操作
					if (eventListener != null) {
						eventListener.queitEvent(position, info);
					}
				} else if (info.getST() == null
						|| info.getST().equals(MeetingJoinInfo.QUEITSTATE)) {
					// 静音状态，恢复通话操作
					if (eventListener != null) {
						eventListener.restoreCallEvent(position, info);
					}
				}
			}
		});

		holder.deleteLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (eventListener != null) {
					eventListener.removeItem(position, info);
				}
			}
		});

	}

	public EventListener getEventListener() {
		return eventListener;
	}

	public void setEventListener(EventListener eventListener) {
		this.eventListener = eventListener;
	}

	class ViewHolder {

		SlidingLayout cSlidingLayout;
		
		TextView nameView;

		TextView stateView;

		FrameLayout operationLayout01;

		FrameLayout operationLayout02;

		TextView operationText01;

		TextView operationText02;

		ImageView operationImg01;

		ImageView operationImg02;

		ImageView callImg;

		ImageView stateImg;

		LinearLayout callstateLayout;

		LinearLayout deleteLayout;

		RelativeLayout contentLayout;
		
		LinearLayout disConHintLayout;
		
		TextView disConHintView;

	}

	/**
	 * 列表事件监听
	 * 
	 * @author 6396000419
	 * 
	 */
	public interface EventListener {
		/**
		 * 删除操作
		 * 
		 * @param position
		 *            列表中的位置
		 * @param info
		 *            实体对象
		 */
		public void removeItem(int position, MeetingJoinInfo info);

		/**
		 * 拨号操作
		 * 
		 * @param position
		 *            列表中的位置
		 * @param info
		 *            实体对象
		 */
		public void callEvent(int position, MeetingJoinInfo info);

		/**
		 * 挂断操作
		 * 
		 * @param position
		 *            列表中的位置
		 * @param info
		 *            实体对象
		 */
		public void hangupEvent(int position, MeetingJoinInfo info);

		/**
		 * 静音操作
		 * 
		 * @param position
		 *            列表中的位置
		 * @param info
		 *            实体对象
		 */
		public void queitEvent(int position, MeetingJoinInfo info);

		/**
		 * 恢复通话操作
		 * 
		 * @param position
		 *            列表中的位置
		 * @param info
		 *            实体对象
		 */
		public void restoreCallEvent(int position, MeetingJoinInfo info);
	}

}
