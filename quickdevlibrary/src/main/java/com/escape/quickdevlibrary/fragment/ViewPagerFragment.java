package com.escape.quickdevlibrary.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.escape.quickdevlibrary.R;

public class ViewPagerFragment extends Fragment {

	private ViewPager mViewPager;

	private PagerAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mViewPager = (ViewPager) inflater.inflate(R.layout.fragment_viewpager, null);
		return mViewPager;
	}

	protected void setViewPagerAdapter(PagerAdapter adapter) {
		mAdapter = adapter;
		mViewPager.setAdapter(mAdapter);
	}

	public ViewPager getViewPager() {
		return mViewPager;
	}

	public PagerAdapter getAdapter() {
		return mAdapter;
	}
}
