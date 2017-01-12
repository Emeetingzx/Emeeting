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
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.android.widget.dialog.DialogManager;
import cn.com.zte.emeeting.app.appservice.AdminAddValueService;
import cn.com.zte.emeeting.app.appservice.ValueAddBookService;
import cn.com.zte.emeeting.app.dialog.DlgServiceConfirmBook;
import cn.com.zte.emeeting.app.dialog.DlgServiceConfirmBook.OnConfirmDlgListener;
import cn.com.zte.emeeting.app.fragment.ServiceReceiveFragment;
import cn.com.zte.emeeting.app.fragment.ServiceUnProcessFragment;
import cn.com.zte.emeeting.app.response.entity.AdminAddValueInfo;
import cn.com.zte.emeeting.app.response.entity.MeetingInfo;
import cn.com.zte.emeeting.app.response.entity.MyAddValueInfo;
import cn.com.zte.emeeting.app.response.instrument.GetAddValueServiceOperateResponse;
import cn.com.zte.emeeting.app.util.BroadcastUtil;
import cn.com.zte.emeeting.app.util.EmeetingToast;
import cn.com.zte.emeeting.app.views.ViewListEmpty;
import cn.com.zte.mobileemeeting.R;
/**
 * 管理员增值服务适配器
 * @author liu.huanbo
 */
public class AdminServiceAdapter extends BaseAdapter {
	/** 上下文 */
	private Context mContext;
	/** 集合列表 */
	private List<AdminAddValueInfo> lists;
	/** 解析器 */
	LayoutInflater layoutInflater;
	
	/**
	 * 标示页签
	 */
	private int fragmentFlag;
	
	private AdminAddValueService addValueService;
	/**
	 * 管理员退单
	 */
	private static final String unDo = "4";
	/**
	 * 管理员提交完成（服务）
	 */
	private static final String finsh = "3";
	/**
	 * 管理员受理（接单）
	 */
	private static final String receive = "2";
	/**
	 * 空列表时显示
	 */
	private ViewListEmpty listEmpty;

	public AdminServiceAdapter(Context mContext, List<AdminAddValueInfo> lists,int flag,ViewListEmpty listEmpty) {
		this.mContext = mContext;
		this.lists = lists;
		this.listEmpty = listEmpty;
		this.fragmentFlag = flag;
		layoutInflater = LayoutInflater.from(mContext);
		addValueService = new AdminAddValueService(mContext);
		
	}

	@Override
	public int getCount() {
		if (lists==null||lists.size()==0) {
			listEmpty.setVisibility(View.VISIBLE);
		}else {
			listEmpty.setVisibility(View.GONE);
		}
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
			holder.personLayout = (LinearLayout) view.findViewById(R.id.item_service_personlayout);
			holder.personView = (TextView) view.findViewById(R.id.item_service_person);
			holder.operationButton01 = (Button) view.findViewById(R.id.item_service_operation1);
			holder.operationButton02 = (Button) view.findViewById(R.id.item_service_operation2);
			holder.personLayout.setVisibility(View.VISIBLE);
			if (fragmentFlag==ServiceUnProcessFragment.FLAG) {
				holder.stateView.setVisibility(View.GONE);
				holder.operationButton02.setVisibility(View.VISIBLE);
				holder.operationButton01.setText(R.string.service_operation_doit);
			}else if (fragmentFlag==ServiceReceiveFragment.FLAG) {
				holder.stateView.setVisibility(View.GONE);
				holder.operationButton02.setVisibility(View.VISIBLE);
				holder.operationButton01.setText(R.string.service_operation_servive);
			}else {
				holder.operationButton01.setVisibility(View.GONE);
				holder.operationButton02.setVisibility(View.GONE);
			}
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		
		AdminAddValueInfo info = lists.get(position);
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
		if (info.getBOLU()!=null) {
			holder.personView.setText(info.getBOLU());
		}

		if (info.getAIS()==null) {
			holder.operationButton01.setVisibility(View.GONE);
			holder.operationButton02.setVisibility(View.GONE);
		}
		
		if (info.getFARIS()!=null) {
			StringBuilder sbBuilder = new StringBuilder();
			for (int i = 0; i < info.getFARIS().size(); i++) {
				sbBuilder.append(info.getFARIS().get(i).getAVSN()).append(" ");
			}
			holder.foodView.setText(sbBuilder.toString());
		}
		
		holder.operationButton02.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				addValueService.operateAddValue(handler, lists.get(position).getID(), unDo);
				cancelBook(lists.get(position),unDo);
			}
		});
		holder.operationButton01.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (fragmentFlag==ServiceUnProcessFragment.FLAG) {
					cancelBook(lists.get(position), receive);
//					addValueService.operateAddValue(handler, lists.get(position).getID(), receive);
				}else if (fragmentFlag==ServiceReceiveFragment.FLAG) {
					cancelBook(lists.get(position), finsh);
//					addValueService.operateAddValue(handler, lists.get(position).getID(), finsh);
				}
			}
		});
		return view;
	}

	BaseAsyncHttpResponseHandler<GetAddValueServiceOperateResponse> handler = new BaseAsyncHttpResponseHandler<GetAddValueServiceOperateResponse>(){
		public void onSuccessTrans(GetAddValueServiceOperateResponse responseModelVO) {
			super.onSuccessTrans(responseModelVO);
//			DialogManager.showToast(mContext, "操作成功");
			EmeetingToast.show(mContext, "操作成功");
			Map<String, String> map = responseModelVO.getD();
			if (map!=null) {
				String id = map.get("AVSID");
				for (int i = 0; i < lists.size(); i++) {
					if (lists.get(i).getID().equals(id)) {
						lists.remove(i);
						if (fragmentFlag==ServiceUnProcessFragment.FLAG) {
							BroadcastUtil.sendBroadcase(mContext, BroadcastUtil.AVServiceReceiveNotifi);
						}else if(fragmentFlag==ServiceReceiveFragment.FLAG) {
							BroadcastUtil.sendBroadcase(mContext, BroadcastUtil.AVServiceDoneNotifi);
						}
						notifyDataSetChanged();
						return;
					}
				}
			}else {
				if (fragmentFlag==ServiceUnProcessFragment.FLAG) {
					BroadcastUtil.sendBroadcase(mContext, BroadcastUtil.AVServiceUndoNotifi);
				}else if(fragmentFlag==ServiceReceiveFragment.FLAG) {
					BroadcastUtil.sendBroadcase(mContext, BroadcastUtil.AVServiceReceiveNotifi);
				}else {
					BroadcastUtil.sendBroadcase(mContext, BroadcastUtil.AVServiceDoneNotifi);
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
		
		LinearLayout personLayout;
		
		TextView personView;
		
	}
	
	
	/**
	 * 该方法用于:取消退订
	 * @param meeting 要退订的服务
	 * @param 
	 * @return void
	 */
	private void cancelBook(final AdminAddValueInfo meeting,final String falg) {
		String leftTitle = "确定操作该服务";
		if (falg.equals(unDo)) {
			leftTitle = "确定退单";
		}else if(falg.equals(receive)){
			leftTitle = "确定接单";
		}else if(falg.equals(finsh)){
			leftTitle = "确定已提供服务";
		}
		DlgServiceConfirmBook dlg = new DlgServiceConfirmBook(mContext, "", new OnConfirmDlgListener() {
			
			@Override
			public void onConfirmed() {
				addValueService.operateAddValue(handler, meeting.getID(),falg);
			}
			
			@Override
			public void onCanceled() {
				
			}
		});
		
		dlg.setLeftTitleNoTitle(leftTitle);
		dlg.show();
	}
}
