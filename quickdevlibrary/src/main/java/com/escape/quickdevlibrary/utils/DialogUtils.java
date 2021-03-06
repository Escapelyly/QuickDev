package com.escape.quickdevlibrary.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class DialogUtils {
    public static Dialog makeCommanYesOrNoDialog(Context context, String title, String message,
                                                 DialogInterface.OnClickListener no, DialogInterface.OnClickListener yes) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(message).setNegativeButton("取消", no).setPositiveButton
                ("确定", yes);
        return builder.create();
    }
}
