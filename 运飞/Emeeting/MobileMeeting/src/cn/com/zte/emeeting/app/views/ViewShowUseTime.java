/**
 * 
 */
package cn.com.zte.emeeting.app.views;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import cn.com.zte.mobileemeeting.R;

/**
 * 该类用于:时间使用情况显示的View
 * @author wf
 */
public class ViewShowUseTime extends RelativeLayout {

	private View v_used_time;
	/**
	 * 该类构造方法:
	 * @param @param context
	 */
	public ViewShowUseTime(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}

	public ViewShowUseTime(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initView();
	}
	

	public ViewShowUseTime(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}

	/**
	 * 该方法用于:初始化控件
	 * @param 
	 * @return void
	 */
	private void initView() {
		// TODO Auto-generated method stub
		inflate(getContext(), R.layout.view_show_usetime, this);
		v_used_time=findViewById(R.id.v_used_time);
	}
	
	/** 
	 * 设置已使用时间的百分比
	 * usedTimePersent+left<=1
	 * */
	public void setTimeUsed(float usedTimePersent,float left)
	{
		int totalWidth=this.getWidth();
		if(totalWidth==0){
			try {
				this.measure(0, 0);
				totalWidth=this.getMeasuredWidth();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				totalWidth=60;
			}
			
		}
		LayoutParams p=(LayoutParams) v_used_time.getLayoutParams();
		if(usedTimePersent>1){usedTimePersent=1;}
		
//		if(usedTimePersent<=1)
//		{
		
			p.width=(int)(usedTimePersent*totalWidth);
			if(1-usedTimePersent>=left)
			{
				p.leftMargin=(int) (totalWidth*left);
			}else
			{
				p.leftMargin=(int)(totalWidth*(1-usedTimePersent));
			}
//		}
			v_used_time.setLayoutParams(p);
	}

}
