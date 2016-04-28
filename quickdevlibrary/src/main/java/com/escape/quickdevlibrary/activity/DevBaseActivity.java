package com.escape.quickdevlibrary.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.WindowCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.escape.quickdevlibrary.R;
import com.escape.quickdevlibrary.delegate.ViewController;

import java.lang.reflect.Field;
import java.util.List;

public abstract class DevBaseActivity extends AppCompatActivity implements
        OnClickListener {
    private ViewController mViewController;


    public abstract ViewController onCreateViewController();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_MODE_OVERLAY);
        super.onCreate(savedInstanceState);
        mViewController = onCreateViewController();
        mViewController.onCreate(savedInstanceState);
        int layoutId = mViewController.getLayoutId();
        if (layoutId != 0) {
            setContentView(layoutId);
        }
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mViewController.onViewCreated();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mViewController.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewController.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewController.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewController.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewController.onDestroy();
    }

    public String getTextViewText(int id) {
        TextView tv = (TextView) findViewById(id);
        return tv == null ? "" : tv.getText().toString();
    }

    public void setTextViewText(int id, CharSequence text) {
        TextView tv = (TextView) findViewById(id);
        if (tv != null) {
            tv.setText(text);
        }
    }

    public void setTextViewHint(int id, String hint) {
        TextView tv = (TextView) findViewById(id);
        if (tv != null) {
            tv.setHint(hint);
        }
    }

    public String getVersionName() {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            String version = packInfo.versionName;
            return version;
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";

    }

    public static String getVersionName(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(),
                    0);
            String version = packInfo.versionName;
            return version;
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";

    }

    @SuppressWarnings("unchecked")
    public <T extends View> T findView(int id) {
        // TODO Auto-generated method stub
        return (T) super.findViewById(id);
    }

    public <T> boolean isEmpty(List<T> list) {
        return list == null || list.isEmpty();

    }

    @SuppressLint("InlinedApi")
    public void initTranslucentActionBar(int colorResId) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 创建TextView
            TextView textView = new TextView(this);
            LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, getStatusBarHeight());
            textView.setBackgroundColor(getResources().getColor(colorResId));
            textView.setLayoutParams(lParams);
//            TextView textView1 = new TextView(this);
//            LinearLayout.LayoutParams lParams1 = new LinearLayout.LayoutParams(
//                    LayoutParams.MATCH_PARENT, getNavigationBarHeight(), Gravity.BOTTOM);
//            textView1.setBackgroundColor(getResources().getColor(colorResId));
//            textView1.setLayoutParams(lParams1);
            // 获得根视图并把TextView加进去。
            ViewGroup view = (ViewGroup) getWindow().getDecorView();
            view.addView(textView);
//            view.addView(textView1);
        }
    }

    public int getNavigationBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("navigation_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    // 获取手机状态栏高度
    public int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    @Override
    public void onSupportContentChanged() {
        super.onSupportContentChanged();
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(showHomeAsUp());
        }
    }

    protected boolean showHomeAsUp() {
        return true;
    }

    public void setActionBarTitle(CharSequence title) {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    @Override
    public void onClick(View v) {

    }

    private ProgressDialog mProgressDialog;

    public void showProgressDialog(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
        }
        if (TextUtils.isEmpty(msg)) {
            msg = "正在加载数据...";
        }
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    public void cancelProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.cancel();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showToast(int resId) {
        if (getActivity() != null)
            Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT).show();
    }

    public void showToast(CharSequence text) {
        if (getActivity() != null)
            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    public Activity getActivity() {
        return this;
    }

    public Dialog showNormalDialog(String title, String msg, String btnString,
                                   android.content.DialogInterface.OnClickListener l) {
        Builder mBuilder = new Builder(this);
        mBuilder.setTitle(title);
        mBuilder.setMessage(msg);
        mBuilder.setPositiveButton(btnString, l);
        Dialog d = mBuilder.create();
        d.show();
        return d;
    }

    public static Dialog getShowFromBottomDialog(Context context) {
        AlertDialog dialog = new Builder(context).create();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.mystyle); // 添加动画
        return dialog;
    }


    public static Dialog getDialog(Context context, View contentView, Dialog d) {
        // 防止dialog重复弹出
        if (d != null && d.isShowing()) {
            return d;
        }
        Dialog dialog = new Dialog(context, R.style.myDialog);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();
        int[] wh = getDeviceWH(context);
        windowParams.x = 0;
        windowParams.y = wh[1];
        // 控制dialog停放位置
        window.setAttributes(windowParams);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(contentView);
        // 最终决定dialog的大小,实际由contentView确定了
        dialog.getWindow().setLayout(LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT);
        dialog.show();
        return dialog;
    }

    // 终端设备的WH
    public static int[] getDeviceWH(Context context) {
        int[] wh = new int[2];
        int w = 0;
        int h = 0;
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        w = dm.widthPixels;
        h = dm.heightPixels;
        wh[0] = w;
        wh[1] = h;
        return wh;
    }

}
