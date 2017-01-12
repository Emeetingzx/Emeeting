package cn.com.zte.emeeting.app.views;

/**
 * ListView滑动刷新触发事件父类
 * 
 * @author sunli
 * */
public abstract class BaseRefreshDataMeans {

	/** 下拉滑动刷新触发事件*/
	public abstract void PullRefreshData();
	
	/** 上滑滑动刷新触发事件*/
	public abstract void SlideRefreshData();
}
