package cn.com.zte.emeeting.app.views.wheel;

import android.graphics.Color;

public class TextInfo {
	public TextInfo(int index, String text, boolean isSelected,int textSize) {
		mIndex = index;
		mText = text;
		mIsSelected = isSelected;
		mTextSize = textSize;
		if (isSelected) {
			mColor = Color.BLUE;
		}
	}

	public int mIndex;
	public String mText;
	public boolean mIsSelected = false;
	public int mColor = Color.BLACK;
	public int mTextSize;
	
}
