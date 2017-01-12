package cn.com.zte.emeeting.app.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import roboguice.inject.InjectView;
import cn.com.zte.emeeting.app.util.DateFormatUtil;
import cn.com.zte.emeeting.app.util.EmeetingToast;
import cn.com.zte.mobileemeeting.R;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 表盘控件
 * 
 * @author LangK
 * 
 */
public class ClockChooseView extends LinearLayout {

	/**
	 * 上下文
	 */
	private Context mContext;
	
	/** 当前时间显示文本框*/
	@InjectView(R.id.current_date_text)
	private TextView current_date_text;
	
	/** 当前选择的时间段文本框*/
	@InjectView(R.id.current_selected_time_period_text)
	private TextView current_selected_time_period_text;

	/**
	 * 表盘上所有可点击的视图
	 */
	private List<ImageView> btns;

	/**
	 * 不可点击的位置
	 */
	private List<Integer> disEnable;

	/**
	 * 点击选中的位置
	 */
	private List<Integer> chooseEnable;
	
	/**
	 * 所有的时间表
	 */
	private String[] timeStrings = new String[]{"08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30"
			,"12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30"
			,"18:00","18:30","19:00","19:30","20:00","20:30","21:00"};

	public String[] getTimeStrings() {
		return timeStrings;
	}

	public ClockChooseView(Context context) {
		super(context);
		this.mContext = context;
		initView();
	}

	public ClockChooseView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		initView();
	}

	public ClockChooseView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
	}

	private void initView() {
		chooseEnable = new ArrayList<Integer>();

		disEnable = new ArrayList<Integer>();

		View view = LayoutInflater.from(mContext).inflate(
				R.layout.view_clock_choose, null);
		if(current_date_text == null){
			current_date_text = (TextView)view.findViewById(R.id.current_date_text);
			String dateStr = DateFormatUtil.formatDate(DateFormatUtil.getSystemDate(), "yyyy-MM-dd", "MM月dd日");
			current_date_text.setText(dateStr);
		}
		if(current_selected_time_period_text == null){
			current_selected_time_period_text = (TextView)view.findViewById(R.id.current_selected_time_period_text);
		}
		
		btns = new ArrayList<ImageView>();
		ImageView image1 = (ImageView) view.findViewById(R.id.btn1);
		image1.setTag(1);
		btns.add(image1);
		ImageView image2 = (ImageView) view.findViewById(R.id.btn2);
		image2.setTag(2);
		btns.add(image2);
		ImageView image3 = (ImageView) view.findViewById(R.id.btn3);
		image3.setTag(3);
		btns.add(image3);
		ImageView image4 = (ImageView) view.findViewById(R.id.btn4);
		image4.setTag(4);
		btns.add(image4);
		ImageView image5 = (ImageView) view.findViewById(R.id.btn5);
		image5.setTag(5);
		btns.add(image5);
		ImageView image6 = (ImageView) view.findViewById(R.id.btn6);
		image6.setTag(6);
		btns.add(image6);
		ImageView image7 = (ImageView) view.findViewById(R.id.btn7);
		image7.setTag(7);
		btns.add(image7);
		ImageView image8 = (ImageView) view.findViewById(R.id.btn8);
		image8.setTag(8);
		btns.add(image8);
		ImageView image9 = (ImageView) view.findViewById(R.id.btn9);
		image9.setTag(9);
		btns.add(image9);
		ImageView image10 = (ImageView) view.findViewById(R.id.btn10);
		image10.setTag(10);
		btns.add(image10);
		ImageView image11 = (ImageView) view.findViewById(R.id.btn11);
		image11.setTag(11);
		btns.add(image11);
		ImageView image12 = (ImageView) view.findViewById(R.id.btn12);
		image12.setTag(12);
		btns.add(image12);
		ImageView image13 = (ImageView) view.findViewById(R.id.btn13);
		image13.setTag(13);
		btns.add(image13);
		ImageView image14 = (ImageView) view.findViewById(R.id.btn14);
		image14.setTag(14);
		btns.add(image14);
		ImageView image15 = (ImageView) view.findViewById(R.id.btn15);
		image15.setTag(15);
		btns.add(image15);
		ImageView image16 = (ImageView) view.findViewById(R.id.btn16);
		image16.setTag(16);
		btns.add(image16);

		ImageView image17 = (ImageView) view.findViewById(R.id.btn17);
		image17.setTag(17);
		btns.add(image17);
		ImageView image18 = (ImageView) view.findViewById(R.id.btn18);
		image18.setTag(18);
		btns.add(image18);
		ImageView image19 = (ImageView) view.findViewById(R.id.btn19);
		image19.setTag(19);
		btns.add(image19);
		ImageView image20 = (ImageView) view.findViewById(R.id.btn20);
		image20.setTag(20);
		btns.add(image20);
		ImageView image21 = (ImageView) view.findViewById(R.id.btn21);
		image21.setTag(21);
		btns.add(image21);
		ImageView image22 = (ImageView) view.findViewById(R.id.btn22);
		image22.setTag(22);
		btns.add(image22);
		ImageView image23 = (ImageView) view.findViewById(R.id.btn23);
		image23.setTag(23);
		btns.add(image23);
		ImageView image24 = (ImageView) view.findViewById(R.id.btn24);
		image24.setTag(24);
		btns.add(image24);
		ImageView image25 = (ImageView) view.findViewById(R.id.btn25);
		image25.setTag(25);
		btns.add(image25);
		ImageView image26 = (ImageView) view.findViewById(R.id.btn26);
		image26.setTag(26);
		btns.add(image26);
		addView(view);

		initClick();
		
	}

	private void initClick() {
		for (int i = 0; i < btns.size(); i++) {
			btns.get(i).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						ImageView imageView = (ImageView) v;
						if (disEnable.contains(((Integer) imageView.getTag()))) {
							imageView.setImageResource(R.drawable.icon_clock_disenable);
							return;
						} else {
							if (chooseEnable.contains(((Integer) imageView
									.getTag()))) {
								imageView
										.setImageResource(R.drawable.icon_clock_enable);
								chooseEnable.remove(((Integer) imageView
										.getTag()));
							} else {
								imageView
										.setImageResource(R.drawable.icon_clock_selected);
								chooseEnable.add(((Integer) imageView.getTag()));
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (getChooseTime().equals("0")||getChooseTime().equals("-1")) {
						current_selected_time_period_text.setText("");
					}else {
						current_selected_time_period_text.setText(getChooseTime());
					}
				}
			});
		}
	}
	/**
	 * 获取选中状态的位置
	 * @return 如果返回-1，表示没有选中任何时间、返回0，表示选择时间不连续
	 */
	public String getChooseTime(){
		if (chooseEnable.size()<1) {
			return "-1";
		}
		Collections.sort(chooseEnable);
		for (int i = 1; i < chooseEnable.size(); i++) {
			if (chooseEnable.get(i)-chooseEnable.get(i-1)!=1) {
				return "0";
			}
		}
		StringBuilder builder = new StringBuilder();
		builder.append(timeStrings[chooseEnable.get(0)-1]);
		builder.append("-");
		if(chooseEnable.size()-1<timeStrings.length){
			builder.append(timeStrings[chooseEnable.get(chooseEnable.size()-1)]);
		}else{
			builder.append(timeStrings[chooseEnable.get(timeStrings.length-1)]);
		}
		return builder.toString();
	}
	
	/**
	 * 设置选中时间
	 * @return
	 */
	public void resetChooseTime(){
		if (chooseEnable.size()<1) {
			current_selected_time_period_text.setText("");
			return;
		}
		Collections.sort(chooseEnable);
		for (int i = 1; i < chooseEnable.size(); i++) {
			if (chooseEnable.get(i)-chooseEnable.get(i-1)!=1) {
				current_selected_time_period_text.setText("");
				return;
			}
		}
		StringBuilder builder = new StringBuilder();
		builder.append(timeStrings[chooseEnable.get(0)-1]);
		builder.append("-");
		if(chooseEnable.size()-1<timeStrings.length){
			builder.append(timeStrings[chooseEnable.get(chooseEnable.size()-1)]);
		}else{
			builder.append(timeStrings[chooseEnable.get(timeStrings.length-1)]);
		}
		current_selected_time_period_text.setText(builder.toString());
	}

	/**
	 * 初始化按钮样式
	 */
	private void initButStyle() {
		if (disEnable != null&&!disEnable.isEmpty()) {
			for (int i = 0; i < disEnable.size(); i++) {
				btns.get(disEnable.get(i) - 1).setImageResource(
						R.drawable.icon_clock_disenable);
			}
		}
		
		if (chooseEnable != null&&!chooseEnable.isEmpty()) {
			for (int i = 0; i < chooseEnable.size(); i++) {
				btns.get(chooseEnable.get(i) - 1).setImageResource(
						R.drawable.icon_clock_selected);
			}
		}
	}
	

	/**
	 * 设置不可点击的位置
	 * 
	 * @param list
	 */
	public void setDisClick(List<Integer> list) {
		this.disEnable = list;
		initButStyle();
	}
	
	/**
	 * 设置选中的位置
	 * 
	 * @param list
	 */
	public void setSelectlick(List<Integer> list) {
		if (disEnable!=null&&list!=null) {
			for (int i = 0; i < disEnable.size(); i++) {
				if (list.contains(disEnable.get(i))) {
					list.remove(disEnable.get(i));
				}
			}
		}
		this.chooseEnable = list;
		initButStyle();
	}
	
	
	/**
	 * 设置显示时间
	 * @param date
	 */
	public void setCurrentDate(Date date){
		String dateStr;
		if (date==null) {
			dateStr = DateFormatUtil.getSystemDate("MM月dd日");
		}else {
			dateStr = DateFormatUtil.formatDate(date,"MM月dd日");
		}
		current_date_text.setText(dateStr);
	};

}
