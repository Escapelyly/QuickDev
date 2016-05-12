package com.escape.quickdevlibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class HalfHeightRelativeLayout extends RelativeLayout {

	public HalfHeightRelativeLayout(final Context context) {
		super(context);
	}

	public HalfHeightRelativeLayout(final Context context,
			final AttributeSet attrs) {
		super(context, attrs);
	}

	public HalfHeightRelativeLayout(final Context context,
			final AttributeSet attrs, final int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(final int widthMeasureSpec,
			final int heightMeasureSpec) {
		final int width = MeasureSpec.getSize(widthMeasureSpec);
		final int height = width / 2;
		final int w_mode = MeasureSpec.getMode(heightMeasureSpec);
		super.onMeasure(widthMeasureSpec,
				MeasureSpec.makeMeasureSpec(height, w_mode));
		setMeasuredDimension(width, height);
	}
}
