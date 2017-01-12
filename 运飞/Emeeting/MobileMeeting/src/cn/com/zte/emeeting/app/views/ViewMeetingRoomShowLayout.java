/**
 * 
 */
package cn.com.zte.emeeting.app.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

/**
 * 该类用于:会议室情况使用展示
 * @author wf
 */
public class ViewMeetingRoomShowLayout extends LinearLayout {

	
//	int[] ids = {R.id.v_1,R.id.v_2,R.id.v_3,R.id.v_4,R.id.v_5};
	int num = 7;
	ViewShowUseTimeDraw[] views=new ViewShowUseTimeDraw[num];
	
	
	public ViewMeetingRoomShowLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public ViewMeetingRoomShowLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}

	public ViewMeetingRoomShowLayout(Context context) {
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
		this.setWeightSum(num);
		for(int i = 0;i<num;i++)
		{
			ViewShowUseTimeDraw c=new ViewShowUseTimeDraw(getContext());
			c.setMinimumHeight(100);
			c.setMinimumWidth(100);
			LinearLayout.LayoutParams p = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
				p.leftMargin=2;
			p.gravity=Gravity.CENTER;
			c.setTimeUsed((i+8)+":00", null);
//			if(a%2==0)
//			{
//				c.setUsedState(ViewMultiState.STATE_USED);
//			}
			p.weight=1;
			views[i]=c;
			this.addView(c,p);
		}
	}
}
