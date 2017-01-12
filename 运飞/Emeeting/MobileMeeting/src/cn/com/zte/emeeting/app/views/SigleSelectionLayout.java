/**
 * 
 */
package cn.com.zte.emeeting.app.views;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.zte.emeeting.app.util.DensityUtil;
import cn.com.zte.mobileemeeting.R;

/**
 * 该类用于
 * @author wf
 */
public class SigleSelectionLayout extends LinearLayout {
	
	int selectionState=0;
	private TextView[] items=new TextView[3];;
	
	public SigleSelectionLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		initViews();
	}

	
	public SigleSelectionLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initViews();
	}


	public SigleSelectionLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initViews();
	}


	/**
	 * 该方法用于:
	 * @param 
	 * @return void
	 */
	@SuppressLint("NewApi")
	private void initViews() {
		// TODO Auto-generated method stub
		this.setWeightSum(3);
		this.setOrientation(LinearLayout.HORIZONTAL);
		
		GradientDrawable shape = new GradientDrawable();
		int borderColor=Color.parseColor("#cccccc");
		int fillColor =Color.WHITE;
		shape.setColor(fillColor);
		
		shape.setStroke(DensityUtil.dip2px(getContext(), 1), borderColor);
		this.setBackground(shape);
//		
//		GradientDrawable bg_tv_normal=new GradientDrawable();
//		bg_tv_normal.setColor(fillColor);
//		
//		int chooseColor = Color.parseColor("#0000ff");
//		GradientDrawable bg_tv_pressed=new GradientDrawable();
//		bg_tv_normal.setColor(chooseColor);
//		
//		StateListDrawable selector = new StateListDrawable();
//		selector.addState(View.SELECTED_STATE_SET, bg_tv_pressed);
//		selector.addState(View.ENABLED_STATE_SET, bg_tv_normal);
//		selector.addState(View.EMPTY_STATE_SET, bg_tv_normal);
		
		
		
		for(int i=0;i<3;i++)
		{
			items[i]=new TextView(getContext());
			items[i].setTextSize(16);
			items[i].setBackgroundDrawable(getResources().getDrawable(R.drawable.bgselector_single_selection_layout));
			items[i].setGravity(Gravity.CENTER);
//			ColorStateList textcolor = getResources().getColorStateList(R.color.white);
			ColorStateList textcolor = getResources().getColorStateList(R.color.textcolor_item_filter_selectlayout);
			items[i].setTextColor(textcolor);
			final int position = i;
			items[i].setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					for(int j = 0;j<3;j++)
					{
						items[j].setSelected(false);
					}
					
					v.setSelected(true);
					selectionState = position;
				}
			});
		}
		items[0].setText(getContext().getString(R.string.dlg_filter_choosestate_no_limit));
		items[1].setText(getContext().getString(R.string.dlg_filter_choosestate_have));
		items[2].setText(getContext().getString(R.string.dlg_filter_choosestate_have_nothing));
		
		items[0].setSelected(true);
		
		
		LinearLayout.LayoutParams lp = new LayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		lp.gravity=Gravity.CENTER;
		lp.weight=1;
		View[] dividers = new View[2];
		for(int i =0;i<2;i++)
		{
			dividers[i]=new View(getContext());
			dividers[i].setBackgroundColor(borderColor);
		}
		LinearLayout.LayoutParams lp_divider = new LayoutParams(new LayoutParams(DensityUtil.dip2px(getContext(), 1), LayoutParams.FILL_PARENT));
		
		this.addView(items[0],lp);
		this.addView(dividers[0],lp_divider);
		this.addView(items[1],lp);
		this.addView(dividers[1],lp_divider);
		this.addView(items[2],lp);
	}


	/**
	 * @return the selectionState
	 */
	public int getSelectionState() {
		return selectionState;
	}


	/**
	 * @param selectionState the selectionState to set
	 */
	public void setSelectionState(int selectionState) {
		if(selectionState>2)return;
		items[selectionState].performClick();
		this.selectionState = selectionState;
	}
	
	

}
