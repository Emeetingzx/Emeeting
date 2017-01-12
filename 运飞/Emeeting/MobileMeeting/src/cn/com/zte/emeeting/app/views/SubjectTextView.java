package cn.com.zte.emeeting.app.views;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.widget.TextView;

public class SubjectTextView extends TextView {

	public SubjectTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public SubjectTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public SubjectTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public int getLineNum() {
		Layout layout = getLayout();
		int topOfLastLine = getHeight() - getPaddingTop() - getPaddingBottom()
				- getLineHeight();
		return layout.getLineForVertical(topOfLastLine);
	}

	public int getCharNum() {
		return getLayout().getLineEnd(getLineNum());
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
//		resize();
	}
	
	  /**
     * 去除当前页无法显示的字
     * @return 去掉的字数
     */
    public int resize() {
        CharSequence oldContent = getText();
        CharSequence newContent = oldContent.subSequence(0, getCharNum());
        return oldContent.length() - newContent.length();
    }

}
