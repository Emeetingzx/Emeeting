package cn.com.zte.emeeting.app.adapter;

import java.util.List;

import com.ab.util.AbImageUtil;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.zte.emeeting.app.response.entity.MeetingAttendanceInfo;
import cn.com.zte.emeeting.app.util.LogTools;
import cn.com.zte.mobilebasedata.request.HttpUtil;
import cn.com.zte.mobileemeeting.R;

/**
 *  会议签到表适配器
 * @author zhangli
 *
 */

public class CheckInAdapter extends BaseAdapter{
	private List<MeetingAttendanceInfo> dataList;
	private Context context;
	private LayoutInflater inflater;
	
	private ViewHolder viewholder;
	private HttpUtils httpUtils = new HttpUtils(HttpUtil.TimeOut);
	private BitmapUtils bitmapUtils;
	public CheckInAdapter(Context context, List<MeetingAttendanceInfo> dataList){
		this.dataList = dataList;
		this.context = context;
		this.inflater = LayoutInflater.from(context);

		bitmapUtils = new BitmapUtils(context);
		bitmapUtils.configDiskCacheEnabled(true).configMemoryCacheEnabled(true).configThreadPoolSize(5).configDefaultLoadFailedImage(R.drawable.icon_default_user).configDefaultLoadingImage(R.drawable.icon_default_user);
	}
	
	public List<MeetingAttendanceInfo> getDataList() {
		return dataList;
	}

	public void setDataList(List<MeetingAttendanceInfo> dataList) {
		this.dataList = dataList;
	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			viewholder = new ViewHolder();
			convertView = inflater.inflate(R.layout.layout_checkin_item, null); 
			viewholder.ivCheckinHeader = (ImageView) convertView.findViewById(R.id.iv_checkin_head);
			viewholder.tvCheckinName = (TextView) convertView.findViewById(R.id.tv_checkin_name);
			viewholder.tvCheckinNum = (TextView) convertView.findViewById(R.id.tv_checkin_num);
			viewholder.btnCheckinTime = (Button) convertView.findViewById(R.id.btn_checkin_time);
			
			convertView.setTag(viewholder);
		}else{
			viewholder = (ViewHolder) convertView.getTag();
		}
		
		viewholder.tvCheckinName.setText(dataList.get(position).getMANA());
		viewholder.tvCheckinNum.setText(dataList.get(position).getMANU()); 
		
		 getUserHeadImage(dataList.get(position).getMANU(),viewholder.ivCheckinHeader);
		
		if(dataList.get(position).getST().equals("1")){
			viewholder.btnCheckinTime.setVisibility(View.VISIBLE);
			viewholder.btnCheckinTime.setText(dataList.get(position).getAT());
		}else{
			viewholder.btnCheckinTime.setVisibility(View.GONE);
		}
		
		return convertView;
	}
	
	public class ViewHolder{
		ImageView ivCheckinHeader;
		/**
		 * 签到人姓名
		 */
		TextView tvCheckinName;
		/**
		 * 签到人工号
		 */
		TextView tvCheckinNum;
		/**
		 * 签到时间
		 */
		Button btnCheckinTime;
	}
	
	private void getUserHeadImage(String uid,final ImageView imageView) {
		
		httpUtils.send(HttpMethod.GET, HttpUtil.url_moa_facephoto + "?userId=" + uid,
                new RequestCallBack<String>() {



					@Override
                    public void onFailure(HttpException arg0, String arg1) {
						LogTools.d("zl", "获取头像地址失败");
						imageView.setImageResource(R.drawable.icon_default_user);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                    	if (arg0.result != null) {
							
                    		bitmapUtils.display(imageView, arg0.result, new BitmapLoadCallBack<View>() {

								@Override
								public void onLoadCompleted(View arg0,
										String arg1, Bitmap arg2,
										BitmapDisplayConfig arg3,
										BitmapLoadFrom arg4) {
									Bitmap roundBitmap = AbImageUtil.toRoundBitmap(arg2);
									imageView.setImageBitmap(roundBitmap);
								}

								@Override
								public void onLoadFailed(View arg0,
										String arg1, Drawable arg2) {
									Log.d("zl","头像加载失败"+arg1);
									imageView.setImageResource(R.drawable.icon_default_user);
								}
							});
						}else {
							Log.d("zl", "图片地址不存在");
						}
                   }
		});
	}
}
