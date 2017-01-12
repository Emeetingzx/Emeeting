package cn.com.zte.emeeting.app.fragment;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectView;

import com.google.inject.Inject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;
import cn.com.zte.android.app.fragment.BaseFragment;
import cn.com.zte.android.http.handler.BaseAsyncHttpResponseHandler;
import cn.com.zte.android.resource.inflater.BaseLayoutInflater;
import cn.com.zte.android.widget.dialog.DialogManager;
import cn.com.zte.emeeting.app.adapter.MyServiceAdapter;
import cn.com.zte.emeeting.app.appservice.ValueAddBookService;
import cn.com.zte.emeeting.app.dialog.DialogServiceBookSuccess;
import cn.com.zte.emeeting.app.dialog.DialogServiceBookSuccess.OnBookSuccessDlgListener;
import cn.com.zte.emeeting.app.fragment.ServiceReceiveFragment.SelfBroadcastReceiver;
import cn.com.zte.emeeting.app.request.instrument.GetMyAddValueServiceInfosRequest;
import cn.com.zte.emeeting.app.response.entity.MyAddValueInfo;
import cn.com.zte.emeeting.app.response.instrument.GetMyAddValueServiceInfosResponse;
import cn.com.zte.emeeting.app.response.instrument.GetReserveAddValueServiceResponse;
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
 * 
 * @author LangK
 * 
 */
public class ValueAddMyselfFragment extends BaseFragment {

	// @InjectView(R.id.service_myself_baselistview)
	// private BaseListViewLayout baseListViewLayout;

	private PullToRefreshLayout pullToRefreshLayout;
	// @InjectView(R.id.content_view)
	// private PullableListView pullableListView;

	private MyServiceAdapter adapter;

	private List<MyAddValueInfo> list = new ArrayList<MyAddValueInfo>();

	private Context mContext;

	private ValueAddBookService addBookService;

	private PageInput pageInput;

	/**
	 * 广播接收器
	 */
	private SelfBroadcastReceiver selfReceiver;

	private static final String PAGESIZE = "20";

	/**
	 * 列表为空时显示该布局
	 */
	@InjectView(R.id.service_myself_empty)
	private ViewListEmpty listEmpty;

	@Override
	protected View onCreateView(BaseLayoutInflater arg0, ViewGroup arg1,
			Bundle arg2) {
		mContext = getActivity();
		View view = arg0.inflate(R.layout.fragment_service_myself, null);
		pullToRefreshLayout = (PullToRefreshLayout) view
				.findViewById(R.id.refresh_listview);
		selfReceiver = new SelfBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(BroadcastUtil.AVServiceMySelfNotifi);
		mContext.registerReceiver(selfReceiver, filter);
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d("TAG", "onresume");
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		Log.d("TAG", "onPause");
		super.onPause();
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		Log.d("TAG", "onHiddenChanged" + hidden);
		super.onHiddenChanged(hidden);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		super.initData();
		addBookService = new ValueAddBookService(mContext);
		pageInput = new PageInput();
		pageInput.setPSIZE(PAGESIZE);
		pageInput.setPNO("1");
		pullToRefreshLayout.isPullUp(true);
		pullToRefreshLayout.isPullDown(true);
		// baseListViewLayout.setAllowedPullRefresh(true);
		// baseListViewLayout.setAllowedSlideRefresh(true);

		addBookService.getMyselfAddValueService(handler, pageInput);

		pullToRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
				// TODO Auto-generated method stub
				pageInput.setPNO("1");
				addBookService.getMyselfAddValueService(handler, pageInput);
			}

			@Override
			public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
				// TODO Auto-generated method stub
				pageInput.pageNoAdd();
				addBookService.getMyselfAddValueService(handler, pageInput);
			}
		});

	}

	@Override
	protected void initViewEvents() {
		// TODO Auto-generated method stub
		super.initViewEvents();
		// baseListViewLayout.setLVRefreshDataMeans(new BaseRefreshDataMeans() {
		//
		// @Override
		// public void SlideRefreshData() {
		// // TODO Auto-generated method stub
		// pageInput.pageNoAdd();
		// addBookService.getMyselfAddValueService(handler, pageInput);
		// }
		//
		// @Override
		// public void PullRefreshData() {
		// baseListViewLayout.setAllowedSlideRefresh(true);
		// pageInput.setPNO("1");
		// addBookService.getMyselfAddValueService(handler, pageInput);
		// }
		// });
		listEmpty.setOnRefreshClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				listEmpty.setRefreshEnable(false);
				initSet();
			}
		});
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		if (selfReceiver != null) {
			mContext.unregisterReceiver(selfReceiver);
		}
		super.onDestroyView();
	}

	BaseAsyncHttpResponseHandler<GetMyAddValueServiceInfosResponse> handler = new BaseAsyncHttpResponseHandler<GetMyAddValueServiceInfosResponse>() {
		public void onSuccessTrans(
				GetMyAddValueServiceInfosResponse responseModelVO) {
			super.onSuccessTrans(responseModelVO);
			listEmpty.setRefreshEnable(true);
			pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
			pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
			// baseListViewLayout.hideRefreshDataFootView();
			// baseListViewLayout.hideRefreshDataHeaderView();
			if (responseModelVO.getD() != null) {
				listEmpty.setVisibility(View.GONE);
				if (pageInput.getPNO().equals("1")) {
					list = responseModelVO.getD();
					adapter = new MyServiceAdapter(mContext, list);
					pullToRefreshLayout.setAdapter(adapter);
				} else {
					list.addAll(responseModelVO.getD());
					adapter.notifyDataSetChanged();
				}
			} else {
				if (list == null || list.isEmpty()) {
					listEmpty.setVisibility(View.VISIBLE);
				}
				pullToRefreshLayout.isPullUp(false);
				// baseListViewLayout.setAllowedSlideRefresh(false);
			}
		};

		public void onFailureTrans(
				GetMyAddValueServiceInfosResponse responseModelVO) {
			super.onFailureTrans(responseModelVO);
			listEmpty.setRefreshEnable(true);
			if (responseModelVO != null && responseModelVO.getM() != null) {
				DialogManager.showToast(mContext, responseModelVO.getM());
			}
			if (list == null || list.isEmpty()) {
				listEmpty.setVisibility(View.VISIBLE);
			}
			// baseListViewLayout.hideRefreshDataFootView();
			// baseListViewLayout.hideRefreshDataHeaderView();
			pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
			pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
		};

		/**
		 * 网络异常
		 */
		public void onPopUpHttpErrorDialogPre(String strTitle, String strCode,
				String strMsg) {
			DialogManager.showToast(mContext, R.string.respones_error);
			if (list == null || list.isEmpty()) {
				listEmpty.setVisibility(View.VISIBLE);
			}
			listEmpty.setRefreshEnable(true);
			pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
			pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
			// baseListViewLayout.hideRefreshDataFootView();
			// baseListViewLayout.hideRefreshDataHeaderView();
		};
	};

	/**
	 * 初始化设置
	 */
	private void initSet() {
		pageInput.setPNO("1");
		addBookService.getMyselfAddValueService(handler, pageInput);
		// baseListViewLayout.setAllowedPullRefresh(true);
		// baseListViewLayout.setAllowedSlideRefresh(true);
	}

	/**
	 * 广播接收器
	 * 
	 * @author LangK
	 * 
	 */
	class SelfBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			initSet();
		}
	}
}
