package cn.com.zte.emeeting.app.views;

import cn.com.zte.emeeting.app.util.DensityUtil;
import cn.com.zte.mobileemeeting.R;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TopBar extends RelativeLayout {

	/**
	 * 上下文
	 */
	private Context mContext;
	/**
	 * 整体视图
	 */
	private View view;
	/**
	 * 视图解析器
	 */
	private LayoutInflater mInflater;

	/**
	 * 左侧布局
	 * */
	private RelativeLayout topBar_leftLayout;

	/**
	 * 左侧图片TextView
	 * */
	private ImageView topBar_leftBtn;

	/**
	 * 左侧文字TextView
	 * */
	private TextView topBar_leftTextBtn;

	/**
	 * 标题
	 * */
	private TextView topBar_title;

	/**
	 * 右侧右边布局
	 * */
	private RelativeLayout topBar_rightLayout;

	/**
	 * 右侧右边TextView
	 * */
	private ImageView topBar_rightBtn;

	/**
	 * 右侧右边文字TextView
	 * */
	private TextView topBar_rightTextBtn;

	/**
	 * 右侧左边布局
	 * */
	private RelativeLayout topBar_rightLayout_left;

	/**
	 * 右侧左边TextView
	 * */
	private ImageView topBar_rightBtn_left;

	/**
	 * 右侧左边上标文字TextView
	 * */
	private TextView topBar_rightBtn_tag_left;

	/**
	 * TopBar右侧左边Button标识
	 * */
	public static final int rightBtnLeftLogo = 0303;
	/**
	 * TopBar左侧Button标识
	 * */
	public static final int leftBtnLogo = 0101;

	/**
	 * TopBar Title标识
	 * */
	public static final int titleLogo = 0000;

	/**
	 * TopBar右侧右边Button标识
	 * */
	public static final int rightBtnLogo = 0202;

	/**
	 * TopBar全部View标识
	 * */
	public static final int allViewLogo = 1111;

	public TopBar(Context context) {
		super(context);
		this.mContext = context;
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		initView();
	}

	public TopBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		initView();
	}

	public TopBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		initView();
	}

	/**
	 * 初始化界面控件
	 * */
	private void initView() {
		view = mInflater.inflate(R.layout.topbar_layout, null);
		topBar_leftLayout = (RelativeLayout) view
				.findViewById(R.id.topBar_leftLayout);
		topBar_leftBtn = (ImageView) view.findViewById(R.id.topBar_leftBtn);
		topBar_leftTextBtn = (TextView) view
				.findViewById(R.id.topBar_leftTextBtn);
		topBar_title = (TextView) view.findViewById(R.id.topBar_title);
		topBar_rightLayout = (RelativeLayout) view
				.findViewById(R.id.topBar_rightLayout);
		topBar_rightBtn = (ImageView) view.findViewById(R.id.topBar_rightBtn);
		topBar_rightTextBtn = (TextView) view
				.findViewById(R.id.topBar_rightTextBtn);
		topBar_rightLayout_left = (RelativeLayout) view
				.findViewById(R.id.topBar_rightLayout_left);
		topBar_rightBtn_left = (ImageView) view
				.findViewById(R.id.topBar_rightBtn_left);
		topBar_rightBtn_tag_left = (TextView) view
				.findViewById(R.id.topBar_rightBtn_tag_left);
		addView(view);
		setViewWidthHeight(leftBtnLogo, 26, 26);
	}

	/** 设置左侧按钮gone掉*/
	public void setLeftButtonGone()
	{
		topBar_leftLayout.setVisibility(View.GONE);
	}
	
	/**
	 * 控制TopBar内控件是否隐藏
	 * */
	public void HiddenView(int viewLogo) {
		switch (viewLogo) {
		case leftBtnLogo:
			topBar_leftLayout.setVisibility(View.INVISIBLE);
			break;
		case titleLogo:
			topBar_title.setVisibility(View.INVISIBLE);
			break;
		case rightBtnLogo:
			topBar_rightLayout.setVisibility(View.INVISIBLE);
			break;
		case rightBtnLeftLogo:
			topBar_rightLayout_left.setVisibility(View.INVISIBLE);
			break;
		case allViewLogo:
			topBar_leftLayout.setVisibility(View.INVISIBLE);
			topBar_title.setVisibility(View.INVISIBLE);
			topBar_rightLayout.setVisibility(View.INVISIBLE);
			break;
		default:
			break;
		}
	}
	
	/**
	 * 控制TopBar内控件是否隐藏
	 * */
	public void showView(int viewLogo) {
		switch (viewLogo) {
		case leftBtnLogo:
			topBar_leftLayout.setVisibility(View.VISIBLE);
			break;
		case titleLogo:
			topBar_title.setVisibility(View.VISIBLE);
			break;
		case rightBtnLogo:
			topBar_rightLayout.setVisibility(View.VISIBLE);
			break;
		case rightBtnLeftLogo:
			topBar_rightLayout_left.setVisibility(View.VISIBLE);
			break;
		case allViewLogo:
			topBar_leftLayout.setVisibility(View.VISIBLE);
			topBar_title.setVisibility(View.VISIBLE);
			topBar_rightLayout.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
	}

	/**
	 * 设置控件Padding
	 * 
	 * @param viewLogo
	 *            标识
	 * 
	 */
	public void setViewPadding(int viewLogo, int left, int top, int right,
			int bottom) {
		switch (viewLogo) {
		case leftBtnLogo:
			topBar_leftLayout.setPadding(left, top, right, bottom);
			break;
		case titleLogo:
			topBar_title.setPadding(left, top, right, bottom);
			break;
		case rightBtnLogo:
			topBar_rightLayout.setPadding(left, top, right, bottom);

		case rightBtnLeftLogo:
			topBar_rightLayout_left.setPadding(left, top, right, bottom);
			break;

		case allViewLogo:

			break;
		}
	}

	/**
	 * 设置控件文本内容
	 * 
	 * @param viewLogo
	 *            标识
	 * @param textStr
	 *            文本内容
	 * 
	 */
	public void setViewText(int viewLogo, String textStr) {
		switch (viewLogo) {
		case leftBtnLogo:
			topBar_leftTextBtn.setText(textStr);
			topBar_leftTextBtn.setVisibility(View.VISIBLE);
			break;
		case titleLogo:
			// 显示场景名称
			if (textStr.indexOf("/n") >= 0) {
				textStr.replace("/n", "");
			}
			if (textStr.length() <12) {
				topBar_title.setText(textStr);
			} else {
				topBar_title.setText(textStr.substring(0, 12) + "...");
			}
			break;
		case rightBtnLogo:
			topBar_rightTextBtn.setText(textStr);
			topBar_rightTextBtn.setVisibility(View.VISIBLE);

		case rightBtnLeftLogo:
			if (textStr.equals("0") || textStr.equals("")) {
				topBar_rightBtn_tag_left.setVisibility(View.GONE);
			} else {
				topBar_rightBtn_tag_left.setText(textStr);
				topBar_rightBtn_tag_left.setVisibility(View.VISIBLE);
			}
			break;
		}
	}

	/**
	 * 设置控件图文并存内容
	 * 
	 * @param viewLogo
	 *            标识
	 * @param textStr
	 *            文本内容
	 * @param image
	 *            文本图标(项目内图片资源)
	 * @param imageLocation
	 *            文本图标位置 0图居于文字左边，1图居于文字上方，2图居于文字右边，3图居于文字下方。
	 * @param padding
	 *            文字与图片间距
	 */
	public void setViewTextAndImage(int viewLogo, String textStr, int imageId,
			int imageLocation, int padding) {
		Drawable image = null;
		try {
			image = getResources().getDrawable(imageId);
		} catch (NotFoundException e) {
			e.printStackTrace();
			image = null;
		}
		if (image != null) {
			image.setBounds(0, 0, image.getMinimumWidth(),
					image.getMinimumHeight());
		}
		switch (viewLogo) {
		case leftBtnLogo:
			topBar_leftTextBtn.setText(textStr);
			topBar_leftTextBtn.setVisibility(View.VISIBLE);
			if (image != null) {
				topBar_leftBtn.setVisibility(View.VISIBLE);
				topBar_leftBtn.setImageDrawable(image);
			}
			break;
		case titleLogo:
			// 显示场景名称
			topBar_title.setText(textStr);
			break;
		case rightBtnLogo:
			topBar_rightTextBtn.setText(textStr);
			topBar_rightTextBtn.setVisibility(View.VISIBLE);
			if (image != null) {
				topBar_rightBtn.setVisibility(View.VISIBLE);
				topBar_rightBtn.setImageDrawable(image);
			}
		case rightBtnLeftLogo:
			topBar_rightBtn_tag_left.setText(textStr);
			topBar_rightBtn_tag_left.setVisibility(View.VISIBLE);
			if (image != null) {
				topBar_rightBtn_left.setVisibility(View.VISIBLE);
				topBar_rightBtn_left.setImageDrawable(image);
			}
			break;
		}
	}

	/**
	 * 设置字体大小
	 * 
	 * @param viewLogo
	 *            标识
	 * @param size
	 *            大小
	 */
	public void setViewTextSize(int viewLogo, int sizeStyle) {
		switch (viewLogo) {
		case leftBtnLogo:
			topBar_leftTextBtn.setTextAppearance(mContext, sizeStyle);
			break;
		case titleLogo:
			topBar_title.setTextAppearance(mContext, sizeStyle);
			break;
		case rightBtnLogo:
			topBar_rightTextBtn.setTextAppearance(mContext, sizeStyle);
			break;
		case rightBtnLeftLogo:
			topBar_rightBtn_tag_left.setTextAppearance(mContext, sizeStyle);
			break;
		case allViewLogo:
			topBar_leftTextBtn.setTextAppearance(mContext, sizeStyle);
			topBar_title.setTextAppearance(mContext, sizeStyle);
			topBar_rightTextBtn.setTextAppearance(mContext, sizeStyle);
			topBar_rightBtn_tag_left.setTextAppearance(mContext, sizeStyle);
			break;
		}
	}

	/**
	 * 设置字体颜色
	 * 
	 * @param viewLogo
	 *            标识
	 * @param color
	 *            颜色
	 */
	public void setViewTextColor(int viewLogo, int color) {
		switch (viewLogo) {
		case leftBtnLogo:
			topBar_leftTextBtn.setTextColor(color);
			break;
		case titleLogo:
			topBar_title.setTextColor(color);
			break;
		case rightBtnLogo:
			topBar_rightTextBtn.setTextColor(color);
			break;
		case rightBtnLeftLogo:
			topBar_rightBtn_tag_left.setTextColor(color);
			break;
		case allViewLogo:
			topBar_leftTextBtn.setTextColor(color);
			topBar_title.setTextColor(color);
			topBar_rightTextBtn.setTextColor(color);
			topBar_rightBtn_tag_left.setTextColor(color);
			break;
		}
	}

	/**
	 * 设置view宽高
	 * @param viewLogo
	 * @param width	直接设置dp
	 * @param height 直接设置dp
	 */
	public void setViewWidthHeight(int viewLogo, int width, int height) {
		int pw = DensityUtil.dip2px(mContext, width);
		int ph = DensityUtil.dip2px(mContext, height);
		switch (viewLogo) {
		case leftBtnLogo:
			topBar_leftTextBtn.setWidth(pw);
			topBar_leftTextBtn.setHeight(ph);
			break;
		case titleLogo:
			topBar_title.setWidth(pw);
			topBar_title.setHeight(ph);
			break;
		case rightBtnLogo:
			topBar_rightTextBtn.setWidth(pw);
			topBar_rightTextBtn.setHeight(ph);
			break;
		case rightBtnLeftLogo:
			topBar_rightBtn_tag_left.setWidth(pw);
			topBar_rightBtn_tag_left.setHeight(ph);
			break;
		}
	}

	/**
	 * 设置点击事件
	 * 
	 * @param i
	 *            标识
	 * @param listener
	 *            监听事件
	 */
	public void setViewOnClickListener(int viewLogo,
			final ButtonOnClick btnOnClick) {
		switch (viewLogo) {
		case leftBtnLogo:
			topBar_leftLayout.setOnClickListener(new OnClickListener() {
				public void onClick(View view) {
					if (btnOnClick != null) {
						btnOnClick.onClick(view);
					}
				}
			});
			break;
		case titleLogo:
			topBar_title.setOnClickListener(new OnClickListener() {
				public void onClick(View view) {
					if (btnOnClick != null) {
						btnOnClick.onClick(view);
					}
				}
			});
			break;
		case rightBtnLogo:
			topBar_rightLayout.setOnClickListener(new OnClickListener() {
				public void onClick(View view) {
					if (btnOnClick != null) {
						btnOnClick.onClick(view);
					}
				}
			});
			break;
		case rightBtnLeftLogo:
			topBar_rightLayout_left.setOnClickListener(new OnClickListener() {
				public void onClick(View view) {
					if (btnOnClick != null) {
						btnOnClick.onClick(view);
					}
				}
			});
			break;
		}
	}

	/**
	 * 设置背景图片
	 * 
	 * @param viewLogo
	 *            标识
	 * @param draw
	 *            背景图片(项目内图片资源)
	 */
	public void setViewBackGround(int viewLogo, int draw) {
		switch (viewLogo) {
		case leftBtnLogo:
			topBar_leftBtn.setImageResource(draw);
			topBar_leftBtn.setVisibility(View.VISIBLE);
			if (!topBar_leftTextBtn.getText().equals("")) {
				topBar_leftTextBtn.setVisibility(View.VISIBLE);
				topBar_leftBtn.setVisibility(View.GONE);
			} else {
				topBar_leftTextBtn.setVisibility(View.GONE);
				topBar_leftBtn.setVisibility(View.VISIBLE);
			}
			break;
		case titleLogo:
			topBar_title
					.setBackgroundResource(draw);
			break;
		case rightBtnLeftLogo:
			topBar_rightBtn_left.setImageResource(
					draw);
			topBar_rightBtn_left.setVisibility(View.VISIBLE);
			if (!topBar_rightBtn_tag_left.getText().equals("")) {
				topBar_rightBtn_tag_left.setVisibility(View.VISIBLE);
			} else {
				topBar_rightBtn_tag_left.setVisibility(View.GONE);
			}
			break;
		case rightBtnLogo:
			topBar_rightBtn.setImageResource(draw);
			topBar_rightBtn.setVisibility(View.VISIBLE);
			if (!topBar_rightTextBtn.getText().equals("")) {
				topBar_rightTextBtn.setVisibility(View.VISIBLE);
			} else {
				topBar_rightTextBtn.setVisibility(View.GONE);
			}
			break;
		}
	}

	/**
	 * 获取控件
	 * 
	 * @param viewLogo
	 *            标识
	 */
	public View getView(int viewLogo) {
		View view = null;
		switch (viewLogo) {
		case leftBtnLogo:
			view = topBar_leftLayout;
			break;
		case titleLogo:
			view = topBar_title;
			break;
		case rightBtnLogo:
			view = topBar_rightLayout;
			break;

		case rightBtnLeftLogo:
			view = topBar_rightLayout_left;

			break;
		}
		return view;
	}

	/**
	 * ButtonOnClick接口
	 * 
	 * @author Administrator
	 * 
	 */
	public static interface ButtonOnClick {
		public void onClick(View view);
	}

}
