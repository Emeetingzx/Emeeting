package cn.com.zte.emeeting.app.views;

import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import cn.com.zte.mobileemeeting.R;


public class AutoLinearLayout4Emeeting extends ViewGroup {

	private static final String TAG = AutoLinearLayout4Emeeting.class.getSimpleName();

	private static final int PADDING_HOR = 6;// 水平方向padding
	private static final int PADDING_VERTICAL = 3;// 垂直方向padding
	private static final int PARENTPADDING_VERTICAL = 0;// 父边距垂直方向padding
	private static final int SIDE_MARGIN = 0;// 左右间距
	private static final int MARGIN_HOR = 6;
	private static final int MARGIN_VERTICAL = 0;

	// private int childMaxraw = 1;
	// private int childMaxHeight = 0;

	public AutoLinearLayout4Emeeting(Context context) {
		super(context);
	}

	public AutoLinearLayout4Emeeting(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AutoLinearLayout4Emeeting(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int childCount = getChildCount();
		int autualWidth = r - l;
		int x = SIDE_MARGIN;// 横坐标开始
		int y = 0;// 纵坐标开始
		int rows = 1;
		for (int i = 0; i < childCount; i++) {
			View view = getChildAt(i);
			int width = view.getMeasuredWidth();
			int height = view.getMeasuredHeight();

			// view.setBackgroundColor(Color.GREEN);
			if (i == 0) {
				x += width;
			} else {
				x += width + MARGIN_HOR;
			}
			if (x > autualWidth && i > 0) {
				x = width + SIDE_MARGIN;
				rows++;
			}
			y = rows * (height + MARGIN_VERTICAL) + PARENTPADDING_VERTICAL;
			if (x > autualWidth) {
//				try {
//					((TextView) view
//							.findViewById(R.id.select_friend_and_group_name_view))
//							.setMaxWidth(autualWidth - ViewsUtil.dip2px(getContext(), 37));
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
				view.layout(SIDE_MARGIN, y - height, SIDE_MARGIN + autualWidth,
						y);
			} else {
				view.layout(x - width, y - height, x, y);
			}

		}
	};

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int x = 0;// 横坐标
		int y = 0;// 纵坐标
		int rows = 1;// 总行数
		int specWidth = MeasureSpec.getSize(widthMeasureSpec);
		int actualWidth = specWidth - SIDE_MARGIN * 2;// 实际宽度
		int childCount = getChildCount();
		int childHeight = 0;
		for (int index = 0; index < childCount; index++) {
			View child = getChildAt(index);
			child.setPadding(PADDING_HOR, PADDING_VERTICAL, PADDING_HOR,
					PADDING_VERTICAL);
			child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
			int width = child.getMeasuredWidth();
			int height = child.getMeasuredHeight();
			childHeight = Math.max(height, childHeight);
			x += width + MARGIN_HOR;
			if (x > actualWidth && index > 0) {// 换行
				x = width;
				rows++;
			}
			y = rows * (childHeight + MARGIN_VERTICAL);
			if (x > actualWidth) {
				child.measure(actualWidth, child.getMeasuredHeight());
			}
		}
		setMeasuredDimension(actualWidth, y + PARENTPADDING_VERTICAL * 2);
	}

	@Override
	protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
		return new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
	}

	@Override
	public android.view.ViewGroup.LayoutParams generateLayoutParams(
			AttributeSet attrs) {
		return new LayoutParams(getContext(), attrs);
	}

	@Override
	protected android.view.ViewGroup.LayoutParams generateLayoutParams(
			android.view.ViewGroup.LayoutParams p) {
		return new LayoutParams(p);
	}

	public static class LayoutParams extends MarginLayoutParams {
		public int gravity = -1;

		public LayoutParams(Context c, AttributeSet attrs) {
			super(c, attrs);

			TypedArray ta = c.obtainStyledAttributes(attrs,
					R.styleable.SlideGroup);

			gravity = ta.getInt(R.styleable.SlideGroup_layout_gravity, -1);

			ta.recycle();
		}

		public LayoutParams(int width, int height) {
			this(width, height, -1);
		}

		public LayoutParams(int width, int height, int gravity) {
			super(width, height);
			this.gravity = gravity;
		}

		public LayoutParams(android.view.ViewGroup.LayoutParams source) {
			super(source);
		}

		public LayoutParams(MarginLayoutParams source) {
			super(source);
		}
	}
	
	/** 设置右侧线性布局的适配器*/
	public void setAdapter(BaseAdapter adapter)
	{
//		this.adapterRight=rightAdapter;
		this.removeAllViews();
		if(adapter==null)return;
		int count=adapter.getCount();
		for(int i=0;i<count;i++)
		{
			View convertView=adapter.getView(i, null, null);
			this.addView(convertView);
		}
	}
	
	/** 设置右侧线性布局的适配器*/
	public void setChildViewList(List<View> childrenList)
	{
//		this.adapterRight=rightAdapter;
		this.removeAllViews();
		if(childrenList==null)return;
		int count=childrenList.size();
		for(int i=0;i<count;i++)
		{
			View convertView=childrenList.get(i);
			this.addView(convertView);
		}
	}

}
