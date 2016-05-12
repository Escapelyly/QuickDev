package com.escape.quickdevlibrary.view;

import android.annotation.SuppressLint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

public class CornerMarkView extends TextView {

	private int mSize;
	private int mNumber;
	private int mColor;

	@SuppressLint("NewApi")
	public CornerMarkView(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		// TODO Auto-generated constructor stub
	}

	public CornerMarkView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public CornerMarkView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CornerMarkView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		RectF rectF = new RectF(0, 0, mSize, mSize);
		// canvas.drawCircle(mSize, cy, radius, paint)
	}
}
