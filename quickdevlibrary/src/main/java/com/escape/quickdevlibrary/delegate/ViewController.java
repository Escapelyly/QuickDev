package com.escape.quickdevlibrary.delegate;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 洋 on 2016/4/26.
 */
public abstract class ViewController {

    public static final int NETWORK_3G_4G = 0;//数据网络连接
    public static final int NETWORK_WIFI = 1;//wifi连接
    public static final int NETWORK_NONE = 2;//无网络连接
    protected AppCompatActivity mActivity;
    protected View mView;//根视图
    protected Fragment mFragment;

    public ViewController(Activity activity) {
        mActivity = (AppCompatActivity) activity;
        mView = mActivity.getWindow().getDecorView();
    }

    public ViewController(Fragment fragment) {
        mFragment = fragment;
        mActivity = (AppCompatActivity) mFragment.getActivity();
        mView = mFragment.getView();
    }

    public View getView() {
        return mView;
    }


    public AppCompatActivity getActivity() {
        return mActivity;
    }

    public abstract void onCreate(Bundle bundle);

    public abstract void onViewCreated();

    public void setTextViewText(int textViewId, CharSequence text) {
        TextView textView = findViewById(textViewId);
        if (textView != null) {
            textView.setText(text);
        }
    }

    public void setTextViewText(int textViewId, int resId) {
        TextView textView = findViewById(textViewId);
        if (textView != null) {
            textView.setText(resId);
        }
    }

    public void setImageViewImage(int imageViewId, Bitmap bitmap) {
        ImageView imageView = findViewById(imageViewId);
        if (imageView != null) {
            imageView.setImageBitmap(bitmap);
        }
    }

    public void setImageViewImage(int imageViewId, Drawable drawable) {
        ImageView imageView = findViewById(imageViewId);
        if (imageView != null) {
            imageView.setImageDrawable(drawable);
        }
    }

    public void setImageViewImage(int imageViewId, int resId) {
        ImageView imageView = findViewById(imageViewId);
        if (imageView != null) {
            imageView.setImageResource(resId);
        }
    }

    public String getTextViewText(int textViewId) {
        TextView textView = findViewById(textViewId);
        if (textView == null) {
            return null;
        }
        return textView.getText().toString();
    }

    public <T extends View> T findViewById(int id) {
        return (T) mView.findViewById(id);
    }

    public void onPause() {

    }

    public void onStart() {

    }

    public void onStop() {

    }

    public void onResume() {

    }

    public void onDestroy() {

    }

    public void onNetWorkChange(int currentNetwork) {

    }

    /**
     * @return 是否监听网络变化
     */
    public boolean shouldListenNetworkChange() {
        return false;
    }

    public abstract int getLayoutId();

    public boolean isNetworkAvailable() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) mActivity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable();
        }
        return false;
    }

    public boolean isWifiConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) mActivity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWiFiNetworkInfo != null) {
            return mWiFiNetworkInfo.isAvailable();
        }
        return false;
    }

    public boolean isMobileConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) mActivity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mMobileNetworkInfo = mConnectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mMobileNetworkInfo != null) {
            return mMobileNetworkInfo.isAvailable();
        }
        return false;
    }

    public int getConnectedType() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) mActivity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
            switch (mNetworkInfo.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    return NETWORK_WIFI;
                case ConnectivityManager.TYPE_MOBILE:
                    return NETWORK_3G_4G;
            }
            return mNetworkInfo.getType();
        }
        return NETWORK_NONE;
    }
}
