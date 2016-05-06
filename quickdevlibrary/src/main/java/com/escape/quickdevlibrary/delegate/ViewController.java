package com.escape.quickdevlibrary.delegate;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
    protected AppCompatActivity mActivity;
    protected View mView;
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

    public abstract int getLayoutId();


}
