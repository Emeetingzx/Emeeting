/**
 * 
 */
package cn.com.zte.emeeting.app.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 该类用于:触摸滑动选择会议室时间段
 * @author wf
 */
public class ViewChooseMeetingLayoutText extends LinearLayout {

	
//	int[] ids = {R.id.v_1,R.id.v_2,R.id.v_3,R.id.v_4,R.id.v_5};
	int num = 10;
	TextView[] views=new TextView[num/2];
	
//	int[] stateChanged=new int[ids.length];
	
	
	public ViewChooseMeetingLayoutText(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public ViewChooseMeetingLayoutText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}

	public ViewChooseMeetingLayoutText(Context context) {
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
		for(int i = 0;i<num/2;i++)
		{
			TextView c=new TextView(getContext());
			c.setGravity(Gravity.CENTER);
			LinearLayout.LayoutParams p = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
			p.leftMargin=2;
			p.weight=2;
			p.gravity=Gravity.CENTER;
			c.setText(i+"");
			views[i]=c;
			this.addView(c,p);
		}
	}
}
