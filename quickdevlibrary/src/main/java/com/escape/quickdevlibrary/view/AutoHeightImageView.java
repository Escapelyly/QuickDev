package com.escape.quickdevlibrary.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * ImageView 根据布局的宽度自适应填充
 * 
 * @author 李洋
 */
public class AutoHeightImageView extends ImageView {

	public AutoHeightImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public AutoHeightImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AutoHeightImageView(Context context) {
		super(context);
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		// TODO Auto-generated method stub
		super.setImageBitmap(bm);
	}

	@Override
	public void setImageDrawable(Drawable drawable) {
		// TODO Auto-generated method stub
		if (drawable instanceof LayerDrawable) {
			Drawable drawable2 = ((LayerDrawable) drawable)
					.getDrawable(((LayerDrawable) drawable).getNumberOfLayers() - 1);
			setImageDrawable(drawable2);
			return;
		}
		super.setImageDrawable(drawable);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		Drawable drawable = getDrawable();
		if (drawable != null && drawable instanceof BitmapDrawable) {
			Bitmap bit = ((BitmapDrawable) drawable).getBitmap();

			if (bit == null) {
				super.onMeasure(widthMeasureSpec, heightMeasureSpec);
				return;
			}
			int width = MeasureSpec.getSize(widthMeasureSpec);
			int height = MeasureSpec.getSize(heightMeasureSpec);
			height = width / bit.getWidth() * bit.getHeight();
			heightMeasureSpec = widthMeasureSpec / bit.getWidth()
					* bit.getHeight();
			setMeasuredDimension(width, height);
			return;
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}
