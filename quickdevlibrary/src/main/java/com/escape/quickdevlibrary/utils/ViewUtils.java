package com.escape.quickdevlibrary.utils;

import android.graphics.Paint;
import android.widget.TextView;

public class ViewUtils {

	/**
	 * 给textview添加一个删除线
	 * 
	 * @param tv
	 */
	public static final void addDeleteLine(TextView tv) {
		tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
	}
}
