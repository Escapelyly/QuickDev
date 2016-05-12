package com.escape.quickdevlibrary.delegate;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.escape.quickdevlibrary.R;

/**
 * Created by 洋 on 2016/5/6.
 */
public abstract class FragmentTabsViewController extends ViewController implements TabHost.OnTabChangeListener {
    protected FragmentTabHost mFragmentTabHost;
    private String[] mTabTitles;
    private Class<? extends Fragment>[] mFragments;
    private int[] mTabIcons;

    public FragmentTabsViewController(Activity activity) {
        super(activity);
    }

    public FragmentTabsViewController(Fragment fragment) {
        super(fragment);
    }

    @Override
    public void onCreate(Bundle bundle) {

    }

    @Override
    public void onViewCreated() {
        setTabHost();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_fragment_tabs;
    }

    public void setTabHost() {
        mTabTitles = getTabTitles();
        mFragments = getFragments();
        mTabIcons = getTabIcons();
        mFragmentTabHost = findViewById(android.R.id.tabhost);
        mFragmentTabHost.setup(getActivity(), getActivity().getSupportFragmentManager(),
                android.R.id.tabcontent);
        int size = mTabTitles.length;
        for (int i = 0; i < size; i++) {
            TabHost.TabSpec tab = mFragmentTabHost.newTabSpec(mTabTitles[i]);
            tab.setIndicator(getIndicatorView(i));
            mFragmentTabHost.addTab(tab, mFragments[i], getFragmentArguments(i));
        }
        mFragmentTabHost.setOnTabChangedListener(this);
    }


    /**
     * 返回底部的tabView
     *
     * @param position
     * @return
     */
    public View getIndicatorView(int position) {
        final View v = mActivity.getLayoutInflater()
                .inflate(R.layout.tab_indicator, null);
        final TextView tv = (TextView) v.findViewById(R.id.title);
        final ImageView iv = (ImageView) v.findViewById(R.id.icon);
        tv.setText(mTabTitles[position]);
        iv.setImageResource(mTabIcons[position]);
        return v;
    }


    /**
     * 返回tab的标题
     */
    public abstract String[] getTabTitles();

    /**
     * 返回tab对应的fragment
     */
    public abstract Class<? extends Fragment>[] getFragments();

    /**
     * 返回每个fragment对应的参数
     */
    public abstract Bundle getFragmentArguments(int position);

    /**
     * 返回对应的icon
     */
    public abstract int[] getTabIcons();

    @Override
    public void onTabChanged(String tabId) {

    }
}
