package com.escape.quickdevlibrary.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.escape.quickdevlibrary.R;


public abstract class BaseTabFragment extends DevBaseFragment implements TabHost.OnTabChangeListener {
    protected FragmentTabHost mFragmentTabHost;
    private String[] mTabTitles;
    private Class<? extends Fragment>[] mFragments;

    private int[] mTabIcons;
    private View mView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mTabTitles = getTabTitles();
        mFragments = getFragments();
        mTabIcons = getTabIcons();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView != null) {
            if (mView.getParent() != null) {
                ((ViewGroup) mView.getParent()).removeView(mView);
            }
            return mView;
        }
        mView = inflater.inflate(R.layout.activity_fragment_tabs, container, false);
        mFragmentTabHost = (FragmentTabHost) mView.findViewById(android.R.id.tabhost);

        mFragmentTabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);
        int size = mTabTitles.length;
        for (int i = 0; i < size; i++) {
            TabSpec tab = mFragmentTabHost.newTabSpec(mTabTitles[i]);
            tab.setIndicator(getIndicatorView(i));
            mFragmentTabHost
                    .addTab(tab, mFragments[i], getFragmentArguments(i));
        }
        mFragmentTabHost.setOnTabChangedListener(this);
        return mView;
    }

    public boolean useDefaultIndicatorView() {
        return true;
    }

    public abstract String[] getTabTitles();

    public abstract Class[] getFragments();

    public abstract Bundle getFragmentArguments(int position);

    public View getIndicatorView(int position) {
        final View v = getActivity().getLayoutInflater().inflate(
                R.layout.tab_indicator, null);
        final TextView tv = (TextView) v.findViewById(R.id.title);
        final ImageView iv = (ImageView) v.findViewById(R.id.icon);
        tv.setText(mTabTitles[position]);
        iv.setImageResource(mTabIcons[position]);
        return v;
    }

    public abstract int[] getTabIcons();

    @Override
    public void onTabChanged(String tabId) {

    }
}
