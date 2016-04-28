package com.escape.quickdevlibrary.delegate;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by 洋 on 2016/4/26.
 */
public abstract class ViewController {
    protected Activity mActivity;
    protected View mView;
    protected Fragment mFragment;

    public ViewController(Activity activity) {
        mActivity = activity;
        mView = mActivity.getWindow().getDecorView();
    }

    public ViewController(Fragment fragment) {
        mFragment = fragment;
        mActivity = mFragment.getActivity();
        mView = mFragment.getView();
    }

    public abstract void onCreate(Bundle bundle);

    public abstract void onViewCreated();

    public abstract void onPause();

    public abstract void onStart();

    public abstract void onStop();

    public abstract void onResume();

    public abstract void onDestroy();

    public abstract int getLayoutId();


}
