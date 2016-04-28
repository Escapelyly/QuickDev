package com.escape.quickdevlibrary.fragment;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.escape.quickdevlibrary.R;
import com.escape.quickdevlibrary.delegate.ViewController;


public abstract class DevBaseFragment extends Fragment implements OnClickListener {
    private ViewController mViewController;


    public abstract ViewController onCreateViewController();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewController = onCreateViewController();
    }

    @Override
    public void onPause() {
        super.onPause();
        mViewController.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewController.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewController.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mViewController.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewController.onDestroy();
    }

    public String getTextViewText(int id) {
        TextView tv = findViewById(id);
        return tv == null ? "" : tv.getText().toString();
    }

    private View mView;

    public void setTextViewText(int id, CharSequence text) {
        TextView tv = findViewById(id);
        if (tv != null) {
            tv.setText(text);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int layout = getLayoutId();
        if (layout <= 0) {
            return null;
        }

        if (isViewReusable()) {
            if (mView != null) {
                if (mView.getParent() != null) {
                    ((ViewGroup) mView.getParent()).removeView(mView);
                }
                return mView;
            }
            mView = inflater.inflate(getLayoutId(), container, false);
        }
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewController.onViewCreated();
    }

    public int getLayoutId() {
        return mViewController.getLayoutId();
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T findViewById(int id) {
        if (getView() == null) {
            return null;
        }
        return (T) getView().findViewById(id);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
    }

    public String getTitle() {
        return getTag();
    }

    public void showToast(int resId) {
        if (getActivity() != null)
            Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT).show();
    }

    public void showToast(CharSequence text) {
        if (getActivity() != null)
            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    public void setTitle(CharSequence title) {
        final ActionBar actionBar = ((AppCompatActivity) getActivity())
                .getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    public ProgressDialog mProgressDialog;

    public void showProgressDialog(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
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
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

    public Dialog showNormalDialog(String title, String msg, String btnString,
                                   android.content.DialogInterface.OnClickListener l) {
        Builder mBuilder = new Builder(getActivity());
        mBuilder.setTitle(title);
        mBuilder.setMessage(msg);
        mBuilder.setPositiveButton(btnString, l);
        Dialog d = mBuilder.create();
        d.show();
        return d;
    }

    public void switchFragment(Fragment target, int id, boolean addToBack) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.push_in_right_to_left, R.anim
                .push_out_right_to_left, R.anim.push_in_left_to_right, R.anim.push_out_left_to_right);
        fragmentTransaction.replace(id, target);
        if (addToBack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    public boolean isViewReusable() {
        return true;
    }
}
