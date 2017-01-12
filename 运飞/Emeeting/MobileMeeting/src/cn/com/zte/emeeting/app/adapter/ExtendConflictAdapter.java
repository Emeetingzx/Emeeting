package cn.com.zte.emeeting.app.adapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.com.zte.emeeting.app.dao.shared.SysDBMeetingRoomInfoDBDao;
import cn.com.zte.emeeting.app.database.entity.shared.DBMeetingRoomInfo;
import cn.com.zte.emeeting.app.response.entity.MeetingProLong;
import cn.com.zte.emeeting.app.util.DateFormatUtil;
import cn.com.zte.mobileemeeting.R;

/**
 * 会议室冲突信息列表adapter
 */

public class ExtendConflictAdapter extends BaseAdapter {
	/** 上下文 */
	private Context mContext;
	/** 数据源 */
	private List<MeetingProLong> list;
	/** 布局解析器 */
	private LayoutInflater inflater;

	private SysDBMeetingRoomInfoDBDao roomInfoDBDao = new SysDBMeetingRoomInfoDBDao();

	Date date;
	Calendar calendar = Calendar.getInstance();
	private String endtime;

	public ExtendConflictAdapter(Context mContext,String endtime,
			List<MeetingProLong> listMeetings) {
		this.mContext = mContext;
		this.list = listMeetings;
		this.endtime = endtime;
		inflater = LayoutInflater.from(mContext);
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endtime);
		} catch (Exception e) {
			date = DateFormatUtil.getServerTime(mContext);
		}
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public MeetingProLong getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		final MeetingProLong item = getItem(position);
		if(convertView==null)
		{
			convertView = inflater.inflate(R.layout.item_dialog_extendconflict, null);
		}
		if (convertView.getTag()==null) {
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.tv_dialog_meeting_name);
			holder.time = (TextView) convertView.findViewById(R.id.tv_dialog_meeting_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if (item.getIPL()!=null&&item.getIPL().equals("Y")) {
			calendar.setTime(date);
			int addInt = 0;
			try {
				if (item.getPLT()!=null) {
					addInt = Integer.parseInt(item.getPLT());
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				addInt = 0;
			}
			
			calendar.add(Calendar.MINUTE, addInt);
			String time = new SimpleDateFormat("HH:mm").format(calendar.getTime());
			holder.time.setText(time+ mContext.getString(R.string.meeting_book_lock));
		}else {
			holder.time.setText(mContext.getString(R.string.meeting_book_locked));
		}
		if (item.getMRID()!=null) {
			DBMeetingRoomInfo info = roomInfoDBDao.getDataByID(item.getMRID());
			if (info!=null) {
				holder.name.setText(info.getMRC());
			}else {
				holder.name.setText("会议室信息未同步");
			}
		}
		
		
		return convertView;

	}

	class ViewHolder {
		TextView name;
		TextView time;
	}

}
