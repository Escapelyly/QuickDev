package com.escape.quickdevlibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 宽高一样的imageview
 * @author 李洋
 *
 */
public class HalfHeightImageView extends ImageView {

	public HalfHeightImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public HalfHeightImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public HalfHeightImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, widthMeasureSpec/2);
	}
}
