package com.escape.quickdevlibrary.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseAppContext extends Application {
    public static String IMG_TOP = "";
    public static String EMAIL = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
    public static String WULIU_URL = "";

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    public static void closeIME(Context context) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            inputMethodManager.hideSoftInputFromWindow(((Activity) context)
                            .getCurrentFocus().getWindowToken(),

                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void closeIME(Context context, View v) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),

                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void openIME(Context context, View v) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(v,
                    InputMethodManager.SHOW_IMPLICIT);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static boolean isEmail(String email) {
        Pattern regex = Pattern.compile(EMAIL);
        Matcher matcher = regex.matcher(email);
        return matcher.matches();
    }

    public static final String SPECIAL_CHARS = " `~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？";

    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(17[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean isInvalidPassWord(String password) {
        // if (TextUtils.isEmpty(password)) {
        // return false;
        // }
        // Pattern p = Pattern.compile("(^[A-Za-z0-9]$)");
        // Matcher m = p.matcher(password);
        // return m.matches();
        char[] chars = SPECIAL_CHARS.toCharArray();
        for (char c : chars) {
            if (password.contains(String.valueOf(c))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 隐藏手机中间几位数
     */
    public static String hideRealPhone(String realPhone) {
        if (TextUtils.isEmpty(realPhone)) {
            return realPhone;
        }
        String temp = realPhone.substring(3, 7);
        return realPhone.replace(temp, "****");
    }

    /**
     * 将身份证号中间几位隐藏
     */
    public static String hideRealAccount(String realAccount) {
        if (TextUtils.isEmpty(realAccount)) {
            return realAccount;
        }
        String temp = realAccount.substring(6, 14);
        return realAccount.replace(temp, "********");

    }

    /**
     * 隐藏邮箱中间几位数
     */
    public static String hideRealEmail(String realEmail) {
        if (TextUtils.isEmpty(realEmail)) {
            return realEmail;
        }
        String temp = realEmail.substring(1, 3);
        return realEmail.replace(temp, "**");
    }

    public static boolean checkNet(Context context) {
        // 获得手机所有连接管理对象（包括对wi-fi等连接的管理）
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                // 获得网络连接管理的对象
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    // 判断当前网络是否已连接
                    if (info.getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
