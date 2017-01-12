package cn.com.zte.emeeting.app.fragment;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;
import cn.com.zte.android.app.fragment.BaseFragment;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.android.resource.inflater.BaseLayoutInflater;
import cn.com.zte.android.widget.dialog.DialogManager;
import cn.com.zte.emeeting.app.adapter.AdminServiceAdapter;
import cn.com.zte.emeeting.app.adapter.MyServiceAdapter;
import cn.com.zte.emeeting.app.appservice.AdminAddValueService;
import cn.com.zte.emeeting.app.response.entity.AdminAddValueInfo;
import cn.com.zte.emeeting.app.response.entity.MyAddValueInfo;
import cn.com.zte.emeeting.app.response.instrument.GetAdminAddValueServiceInfosResponse;
import cn.com.zte.emeeting.app.util.BroadcastUtil;
import cn.com.zte.emeeting.app.util.DensityUtil;
import cn.com.zte.emeeting.app.views.BaseListView;
import cn.com.zte.emeeting.app.views.BaseListViewLayout;
import cn.com.zte.emeeting.app.views.BaseRefreshDataMeans;
import cn.com.zte.emeeting.app.views.ViewListEmpty;
import cn.com.zte.emeeting.app.views.pulllistview.PullToRefreshLayout;
import cn.com.zte.emeeting.app.views.pulllistview.PullToRefreshLayout.OnRefreshListener;
import cn.com.zte.emeeting.app.views.pulllistview.PullableListView;
import cn.com.zte.mobilebasedata.entity.PageInput;
import cn.com.zte.mobileemeeting.R;
/**
 * 我的增值服务
 * @author LangK
 *
 */
public class ServiceDoneFragment extends BaseFragment{

//	private BaseListViewLayout baseListViewLayout;
	
	@InjectView(R.id.refresh_listview)
	private PullToRefreshLayout pullToRefreshLayout;
//	@InjectView(R.id.content_view)
//	private PullableListView pullableListView;
	
	private AdminServiceAdapter adapter;
	
	private List<AdminAddValueInfo> list = new ArrayList<AdminAddValueInfo>();;
	
	private Context mContext;
	
	private AdminAddValueService addValueService;
	
	private PageInput pageInput;
	
	private static final String PAGESIZE = "20";
	
	/**
	 *  广播接收器
	 */
	private SelfBroadcastReceiver selfReceiver;
	/**
	 * 页面标志
	 */
	public static final int FLAG = 0x333;
	
	/**
	 * 列表为空时显示该布局
	 */
	private ViewListEmpty listEmpty;
	
	@Override
	protected View onCreateView(BaseLayoutInflater arg0, ViewGroup arg1,
			Bundle arg2) {
		mContext = getActivity();
		View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_service_myself,null);
//		baseListViewLayout = (BaseListViewLayout) view.findViewById(R.id.service_myself_baselistview);
		listEmpty = (ViewListEmpty) view.findViewById(R.id.service_myself_empty);

		selfReceiver = new SelfBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(BroadcastUtil.AVServiceDoneNotifi);
		mContext.registerReceiver(selfReceiver, filter);
		return view;
	}
	
	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		super.initData();
		listEmpty.setEmptyText(getString(R.string.service_order_empty));
		
		addValueService = new AdminAddValueService(mContext);
		pageInput = new PageInput();
		pageInput.setPSIZE(PAGESIZE);
		pageInput.setPNO("1");
		
		addValueService.getDoneAddValueService(handler, pageInput);
		pullToRefreshLayout.isPullDown(true);
		pullToRefreshLayout.isPullUp(true);
//		baseListViewLayout.setAllowedPullRefresh(true);
//		baseListViewLayout.setAllowedSlideRefresh(true);
		
	}
	/**
	 * 初始化设置
	 */
	private void initSet(){
		pageInput.setPNO("1");
		addValueService.getDoneAddValueService(handler, pageInput);
		pullToRefreshLayout.isPullDown(true);
		pullToRefreshLayout.isPullUp(true);
//		baseListViewLayout.setAllowedPullRefresh(true);
//		baseListViewLayout.setAllowedSlideRefresh(true);
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		if (selfReceiver != null) {
			mContext.unregisterReceiver(selfReceiver);
		}
		super.onDestroyView();
	}
	
	@Override
	protected void initViewEvents() {
		// TODO Auto-generated method stub
		super.initViewEvents();
		pullToRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
				// TODO Auto-generated method stub
				pullToRefreshLayout.isPullUp(true);
				pageInput.setPNO("1");
				addValueService.getDoneAddValueService(handler, pageInput);
			}
			
			@Override
			public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
				// TODO Auto-generated method stub
				pageInput.pageNoAdd();
				addValueService.getDoneAddValueService(handler, pageInput);
			}
		});
		
//		baseListViewLayout.setLVRefreshDataMeans(new BaseRefreshDataMeans() {
//			
//			@Override
//			public void SlideRefreshData() {
//				// TODO Auto-generated method stub
//				pageInput.pageNoAdd();
//				addValueService.getDoneAddValueService(handler, pageInput);
//			}
//			
//			@Override
//			public void PullRefreshData() {
//				baseListViewLayout.setAllowedSlideRefresh(true);
//				pageInput.setPNO("1");
//				addValueService.getDoneAddValueService(handler, pageInput);
//			}
//		});
		
		listEmpty.setOnRefreshClick(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				listEmpty.setRefreshEnable(false);
				initSet();
			}
		});
	}
	
	BaseAsyncHttpResponseHandler<GetAdminAddValueServiceInfosResponse> handler = new BaseAsyncHttpResponseHandler<GetAdminAddValueServiceInfosResponse>(){
		public void onSuccessTrans(GetAdminAddValueServiceInfosResponse responseModelVO) {
			super.onSuccessTrans(responseModelVO);
			listEmpty.setRefreshEnable(true);
			pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
			pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
//			baseListViewLayout.hideRefreshDataFootView();
//			baseListViewLayout.hideRefreshDataHeaderView();
			if (responseModelVO.getD()!=null) {
				listEmpty.setVisibility(View.GONE);
				if (pageInput.getPNO().equals("1")) {
					list = responseModelVO.getD();
					adapter = new AdminServiceAdapter(mContext, list,FLAG,listEmpty);
					pullToRefreshLayout.setAdapter(adapter);
				}else {
					list.addAll(responseModelVO.getD());
					adapter.notifyDataSetChanged();
				}
			}else {
				if (list.isEmpty()) {
					listEmpty.setVisibility(View.VISIBLE);
				}
//				baseListViewLayout.setAllowedSlideRefresh(false);
				pullToRefreshLayout.isPullUp(false);
			}
		};
		
		public void onFailureTrans(GetAdminAddValueServiceInfosResponse responseModelVO) {
			super.onFailureTrans(responseModelVO);
			listEmpty.setRefreshEnable(true);
			if (responseModelVO!=null&&responseModelVO.getM()!=null) {
				DialogManager.showToast(mContext, responseModelVO.getM());
			}
			if (list.isEmpty()) {
				listEmpty.setVisibility(View.VISIBLE);
			}
			pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
			pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
//			baseListViewLayout.hideRefreshDataFootView();
//			baseListViewLayout.hideRefreshDataHeaderView();
		};
		
		/**
		 * 网络异常
		 */
		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			DialogManager.showToast(mContext, R.string.respones_error);
			listEmpty.setRefreshEnable(true);
			if (list.isEmpty()) {
				listEmpty.setVisibility(View.VISIBLE);
			}
//			baseListViewLayout.hideRefreshDataFootView();
//			baseListViewLayout.hideRefreshDataHeaderView();
			pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
			pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
		};
	};
	
	/**
	 * 广播接收器
	 * @author LangK
	 *
	 */
	class SelfBroadcastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			initSet();
		}
	}

}
