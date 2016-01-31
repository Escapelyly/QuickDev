package com.escape.quickdevlibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 高度为宽度一半的FrameLayout
 * 
 * @author 李洋
 * 
 */
public class SquareFrameLayout extends FrameLayout {

	public SquareFrameLayout(final Context context) {
		super(context);
	}

	public SquareFrameLayout(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}

	public SquareFrameLayout(final Context context, final AttributeSet attrs,
			final int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(final int widthMeasureSpec,
			final int heightMeasureSpec) {
		final int width = MeasureSpec.getSize(widthMeasureSpec);
		final int height = width;
		final int w_mode = MeasureSpec.getMode(heightMeasureSpec);
		super.onMeasure(widthMeasureSpec,
				MeasureSpec.makeMeasureSpec(height, w_mode));
		setMeasuredDimension(width, height);
	}
}
