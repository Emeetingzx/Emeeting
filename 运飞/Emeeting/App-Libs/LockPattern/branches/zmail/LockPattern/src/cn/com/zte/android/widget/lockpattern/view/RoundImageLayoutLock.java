package cn.com.zte.android.widget.lockpattern.view;

import java.io.File;
import java.io.FileNotFoundException;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import cn.com.zte.android.widget.lockpattern.utils.UserEntity;
import cn.com.zte.android.widget.pattern.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 圆形ImageView，可设置联系人对象
 * 
 * @author Alan
 */
public class RoundImageLayoutLock extends RelativeLayout {
	private String TAG = "RelativeLayout";
	private Context mContext;

	private UserEntity contactInfo;

	private ProgressBar progressBar;

//	private TextView textView;

	private RoundImageViewLock imageView;

	private boolean isGroup = false;

	public RoundImageLayoutLock(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public RoundImageLayoutLock(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}

	public RoundImageLayoutLock(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		init();
	}

	private void init() {
		View view = ((LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.view_round_layout, null);
//		textView = (TextView) view.findViewById(R.id.view_roundlayout_text);
		imageView = (RoundImageViewLock) view
				.findViewById(R.id.view_roundlayout_image);
		progressBar = (ProgressBar) view
				.findViewById(R.id.pg_view_roundlayout_image);
		// imageView.setEmpty(true);
//		textView.setText(getResources().getString(
//				R.string.md_head_image_contact_name_default_value));
//		textView.setVisibility(View.VISIBLE);
//		textView.bringToFront();
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		addView(view, params);
	}

	/**
	 * 设置为正在加载中. <br/>
	 * 日期: 2015-5-28 下午6:50:16 <br/>
	 * 
	 * @author wangenzi
	 * @since JDK 1.6
	 */
	public void setIsLoading() {
		if (progressBar != null) {
			progressBar.setVisibility(View.GONE);
		}

//		if (textView != null) {
//			textView.setVisibility(View.VISIBLE);
//		}

		if (imageView != null) {
			imageView.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * 设置为加载完成. <br/>
	 * 日期: 2015-5-28 下午6:50:16 <br/>
	 * 
	 * @author wangenzi
	 * @since JDK 1.6
	 */
	public void setLoadFinish() {
		if (progressBar != null) {
			progressBar.setVisibility(View.GONE);
		}

		if (imageView != null) {
			imageView.setVisibility(View.VISIBLE);
		}

//		if (textView != null) {
//			textView.setVisibility(View.VISIBLE);
//		}
	}
	
	

	/**
	 * 设置联系人对象
	 * 
	 * @param contactInfo
	 *            联系人对象
	 * @param isGroup
	 *            是否是聚合
	 */
	public void setContact(final UserEntity contactInfo, final int textsize) {
		this.contactInfo = contactInfo;
		
		imageView.setImageResource(R.drawable.lv_user_head_default);
		imageView.setVisibility(View.VISIBLE);
		if (contactInfo == null) {
			return;
		}
		int defaultMissingImgResId = R.drawable.lv_user_head_default;
		if (contactInfo.getUrl() != null && !contactInfo.getUrl().equals("")) {
			ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(
					mContext).threadPoolSize(3)
					.threadPriority(Thread.NORM_PRIORITY - 2)
					.tasksProcessingOrder(QueueProcessingType.LIFO)
					.memoryCache(new WeakMemoryCache())
					.memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(10)
					.build();
			ImageLoader imageLoader = ImageLoader.getInstance();
			imageLoader.init(configuration);
			
			DisplayImageOptions options = new DisplayImageOptions.Builder()
					.showImageForEmptyUri(defaultMissingImgResId)
					.showImageOnFail(defaultMissingImgResId)
					.showImageOnLoading(defaultMissingImgResId)
					.resetViewBeforeLoading(false)
					.cacheInMemory(false)
					.cacheOnDisk(true)
//					.displayer(new RoundedBitmapDisplayer(ViewsUtil.getHeight(view)+10))
					.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
					.bitmapConfig(Bitmap.Config.RGB_565).build();
			imageLoader.displayImage(contactInfo.getUrl(), imageView, options);
			
			
//			imageView.setVisibility(View.VISIBLE);
//			imageView.bringToFront();
//			ImageLoader.loadWebImage(mContext, imageView,
//					Uri.parse(contactInfo.getUrl()),
//					R.drawable.lv_user_head_default);
		}else{
			imageView.setImageResource(R.drawable.lv_user_head_default);
		}
		

//		if (contactInfo.getName() != null && !contactInfo.getName().equals("")) {
//			setName(contactInfo.getName(), textsize);
//		} else {
//			if (contactInfo.getName()!=null&&!contactInfo.getName().equals("")) {
//				setName(contactInfo.getName(), textsize);
//			} else {
//				setName(getResources().getString(
//						R.string.md_head_image_contact_name_default_value),
//						textsize);
//			}
			
//		}
	}

//	/**
//	 * 设置姓名
//	 * 
//	 * @param name
//	 *            中文名
//	 * @param textSize
//	 *            需要修改字体大小
//	 */
//	public void setName(String name, int textSize) {
//		textView.setTextSize(textSize);
//		if (name != null) {
//			textView.setText(name);
//			textView.setVisibility(View.VISIBLE);
//		}
//	}

	public void setImageResource(final int resourse) {
		// imageView.setEmpty(false);
		// imageView.setFrame(false);
//		textView.setVisibility(View.VISIBLE);
		imageView.setVisibility(View.VISIBLE);
		imageView.setImageResource(resourse);
		imageView.bringToFront();
		// imageView.setImageResource(resourse);
	}

	public void setImageDrawable(final Drawable resourse) {
		// imageView.setFrame(false);
		// imageView.setEmpty(false);
//		textView.setVisibility(View.GONE);
		imageView.setVisibility(View.VISIBLE);
		imageView.setImageDrawable(resourse);
		imageView.bringToFront();
		// imageView.setImageDrawable(resourse);
	}

	public void setImageBitmap(final Bitmap resourse) {
		// imageView.setFrame(false);
		// imageView.setEmpty(false);
//		textView.setVisibility(View.GONE);
		imageView.setVisibility(View.VISIBLE);
		imageView.setImageBitmap(resourse);
		imageView.bringToFront();
		// imageView.setImageBitmapPre(resourse);

	}

	/**
	 * 设置已经处理为圆形的bitmap. <br/>
	 * 日期: 2015-5-28 下午2:25:25 <br/>
	 * 
	 * @author wangenzi
	 * @param resourse
	 * @since JDK 1.6
	 */
	public void setImageBitmapRound(final Bitmap resourse) {
		// imageView.setFrame(false);
		// imageView.setEmpty(false);
//		textView.setVisibility(View.GONE);
		imageView.setVisibility(View.VISIBLE);
		imageView.setImageBitmap(resourse);
		imageView.bringToFront();
		// imageView.setImageBitmapRound(resourse);
	}

}
