/**
 * 
 */
package cn.com.zte.emeeting.app.views;

import cn.com.zte.emeeting.app.util.LogTools;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * 该类用于:触摸滑动选择会议室时间段
 * @author wf
 */
public class ViewChooseMeetingLayout extends LinearLayout {

	
//	int[] ids = {R.id.v_1,R.id.v_2,R.id.v_3,R.id.v_4,R.id.v_5};
	int num = 10;
	ViewMultiState[] views=new ViewMultiState[num];
	
//	int[] stateChanged=new int[ids.length];
	
	int currentIndex=0;
	
	/** 点击的item的状态*/
	int firstOldState=ViewMultiState.STATE_NOT_USE;
	
	/** 点击时的x*/
	float firstX=0;
	
	/** 第一次移动时的x增量,用来标识第一次移动的方向*/
	float firstMoveX=0;
	
	/** 上次移动到的X*/
	float preX=0;
	
	/** 状态改变了*/
	private final int STATE_CHANGED=1;
	
	/** 状态未改变*/
	private final int STATE_NOT_CHANGED=0;
	
	public ViewChooseMeetingLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public ViewChooseMeetingLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}

	public ViewChooseMeetingLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}

	/**
	 * 该方法用于:初始化
	 * @param 
	 * @return void
	 */
	private void initView() {
//		inflate(getContext(), R.layout.view_choose_meetingroom, this);
		this.setOrientation(LinearLayout.HORIZONTAL);
		this.setWeightSum(10);
		for(int i = 0;i<num;i++)
		{
			ViewMultiState c=new ViewMultiState(getContext());
			LinearLayout.LayoutParams p = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
			p.leftMargin=2;
			if(i%2==0)
			{
				p.leftMargin=4;
			}
			int a = (int) (Math.random()*10);
			if(a%2==0)
			{
				c.setUsedState(ViewMultiState.STATE_USED);
			}
			p.weight=1;
			views[i]=c;
			this.addView(c,p);
		}
	}
	
	
	/**
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction()==MotionEvent.ACTION_DOWN)
		{
			LogTools.i("ViewC", "X:"+event.getX()+"");
			currentIndex=(int) (event.getX()/views[0].getWidth());
			if(currentIndex<0){
				currentIndex=0;
			}
			
			LogTools.i("ViewC", "Index:"+currentIndex);
			if(currentIndex<num&&currentIndex>=0)
			{
				firstOldState=views[currentIndex].getUsedState();
				if(firstOldState==ViewMultiState.STATE_USED)
				{
					firstOldState=ViewMultiState.STATE_NOT_USE;
				}
				
				
				firstX=event.getX();
				preX=firstX;
				
				if(views[currentIndex].getUsedState()!=ViewMultiState.STATE_USED
						//&&stateChanged[currentIndex]!=STATE_CHANGED
						)
				{
					if(views[currentIndex].getUsedState()==ViewMultiState.STATE_NOT_USE)
					{
						views[currentIndex].setUsedState(ViewMultiState.STATE_READY_USE);
					}
					else if(views[currentIndex].getUsedState()==ViewMultiState.STATE_READY_USE)
					{
						views[currentIndex].setUsedState(ViewMultiState.STATE_NOT_USE);
					}
//					stateChanged[currentIndex]=STATE_CHANGED;
				}
			}
		}else if(event.getAction()==MotionEvent.ACTION_MOVE)
		{
			LogTools.i("ViewC", "X:"+event.getX()+"");
			currentIndex=(int) (event.getX()/views[0].getWidth());
			if(currentIndex<0){
				currentIndex=0;
			}
			LogTools.i("ViewC", "Index:"+currentIndex);
			if(currentIndex<num&&currentIndex>=0)
			{
				if(firstMoveX==0)//
				{
					firstMoveX=event.getX()-firstX;
				}
				
				if(firstMoveX*(event.getX()-preX)>=0)//第一次移动增量与本次移动增量之积非负时,表示本次移动方向和第一次移动方向一致,故执行与第一次移动相同的操作
				{
					if(views[currentIndex].getUsedState()!=ViewMultiState.STATE_USED
//							&&stateChanged[currentIndex]!=STATE_CHANGED
							)
					{
						if(getFirstNewState()!=views[currentIndex].getUsedState())
						{
							views[currentIndex].setUsedState(getFirstNewState());
						}
					}
//					stateChanged[currentIndex]=STATE_CHANGED;
				}else//负责执行和第一次移动相反的操作,即进行取消操作
				{
					
					if(views[currentIndex].getUsedState()!=ViewMultiState.STATE_USED)
					{
						if(firstOldState!=views[currentIndex].getUsedState())
						{
							views[currentIndex].setUsedState(firstOldState);
						}
					}
				}
			}
			preX=event.getX();
		}else if(event.getAction()==MotionEvent.ACTION_UP||event.getAction()==MotionEvent.ACTION_CANCEL)
		{
			firstMoveX=0;
		}
			  
		return true;
	}

	/** 获取此次操作正常应变为何种状态*/
	private int getFirstNewState()
	{
		if(firstOldState==ViewMultiState.STATE_NOT_USE)
		{
			return ViewMultiState.STATE_READY_USE;
		}else if(firstOldState==ViewMultiState.STATE_READY_USE)
		{
			return ViewMultiState.STATE_NOT_USE;
		}else{
			return ViewMultiState.STATE_USED;
		}
	}
}
