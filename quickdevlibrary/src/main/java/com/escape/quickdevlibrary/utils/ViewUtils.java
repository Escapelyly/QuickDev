package com.escape.quickdevlibrary.utils;

import android.graphics.Paint;
import android.widget.TextView;

public class ViewUtils {

	public static final void addDeleteLine(TextView tv) {
		tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
	}
}
