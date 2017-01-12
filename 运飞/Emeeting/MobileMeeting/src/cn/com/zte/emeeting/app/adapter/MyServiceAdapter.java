package cn.com.zte.emeeting.app.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.android.widget.dialog.DialogManager;
import cn.com.zte.emeeting.app.appservice.ValueAddBookService;
import cn.com.zte.emeeting.app.dialog.DlgServiceConfirmBook;
import cn.com.zte.emeeting.app.dialog.DlgServiceConfirmBook.OnConfirmDlgListener;
import cn.com.zte.emeeting.app.response.entity.MyAddValueInfo;
import cn.com.zte.emeeting.app.response.instrument.GetAddValueServiceOperateResponse;
import cn.com.zte.emeeting.app.util.EmeetingToast;
import cn.com.zte.mobileemeeting.R;
/**
 * 我的增值服务适配器
 * @author LangK
 */
public class MyServiceAdapter extends BaseAdapter {
	/** 上下文 */
	private Context mContext;
	/** 集合列表 */
	private List<MyAddValueInfo> lists;
	/** 解析器 */
	LayoutInflater layoutInflater;

	public MyServiceAdapter(Context mContext, List<MyAddValueInfo> lists) {
		this.mContext = mContext;
		this.lists = lists;
		layoutInflater = LayoutInflater.from(mContext);
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
			view = layoutInflater.inflate(R.layout.item_myservice, null);
			holder.nameView = (TextView) view.findViewById(R.id.item_service_number);
			holder.stateView = (TextView) view.findViewById(R.id.item_service_state);
			holder.addressView = (TextView) view.findViewById(R.id.item_service_address);
			holder.timeView = (TextView) view.findViewById(R.id.item_service_time);
			holder.foodView = (TextView) view.findViewById(R.id.item_service_food);
			holder.operationButton01 = (Button) view.findViewById(R.id.item_service_operation1);
			
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		
		MyAddValueInfo info = lists.get(position);
		if (info.getON()!=null) {
			holder.nameView.setText(info.getON());
		}
		if (info.getOS()!=null) {
			holder.stateView.setText(info.getOS());
		}
		if (info.getSA()!=null) {
			holder.addressView.setText(info.getSA());
		}
		if (info.getSD()!=null) {
			holder.timeView.setText(info.getSD());
		}
		if (info.getAIS()!=null&&info.getAIS().equals("2")) {
			holder.operationButton01.setVisibility(View.VISIBLE);
		}else {
			holder.operationButton01.setVisibility(View.GONE);
		}
		
		if (info.getFARIS()!=null) {
			StringBuilder sbBuilder = new StringBuilder();
			for (int i = 0; i < info.getFARIS().size(); i++) {
				sbBuilder.append(info.getFARIS().get(i).getAVSN()).append(" ");
			}
			holder.foodView.setText(sbBuilder.toString());
		}
		
		holder.operationButton01.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cancelBook(lists.get(position));
			}
		});
		
		return view;
	}
	
	BaseAsyncHttpResponseHandler<GetAddValueServiceOperateResponse> handler = new BaseAsyncHttpResponseHandler<GetAddValueServiceOperateResponse>(){
		public void onSuccessTrans(GetAddValueServiceOperateResponse responseModelVO) {
			super.onSuccessTrans(responseModelVO);
//			DialogManager.showToast(mContext, "退订成功");
			EmeetingToast.show(mContext, "退订成功");
			Map<String, String> map = responseModelVO.getD();
			if (map!=null) {
				String id = map.get("AVSID");
				for (int i = 0; i < lists.size(); i++) {
					if (lists.get(i).getID().equals(id)) {
						lists.get(i).setOS("已退订");
						lists.get(i).setAIS("1");
						notifyDataSetChanged();
						return;
					}
				}
			}
		};
		
		public void onFailureTrans(GetAddValueServiceOperateResponse responseModelVO) {
			super.onFailureTrans(responseModelVO);
			if (responseModelVO!=null&&responseModelVO.getM()!=null) {
				DialogManager.showToast(mContext, responseModelVO.getM());
			}
		};
		
		/**
		 * 网络异常
		 */
		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
		};
	};

	class ViewHolder {
		
		TextView nameView;
		
		TextView stateView;
		
		Button operationButton01;
		
		Button operationButton02;
		
		TextView addressView;
		
		TextView timeView;
		
		TextView foodView;
		
	}
	
	/**
	 * 该方法用于:取消退订
	 * @param meeting 要退订的服务
	 * @param 
	 * @return void
	 */
	private void cancelBook(final MyAddValueInfo meeting) {
		DlgServiceConfirmBook dlg = new DlgServiceConfirmBook(mContext, "", new OnConfirmDlgListener() {
			
			@Override
			public void onConfirmed() {
				ValueAddBookService service = new ValueAddBookService(mContext);
				service.operateAddValue(handler, meeting.getID());
			}
			
			@Override
			public void onCanceled() {
				
			}
		});
		String leftTitle = "确定退订该服务";
		dlg.setLeftTitleNoTitle(leftTitle);
		dlg.show();
	}
}
