package com.escape.quickdevlibrary.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

import com.escape.quickdevlibrary.R;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

/**
 * 基础的底部tab栏activity
 * 
 * @author 李洋
 * 
 */
public abstract class BaseFragmentTabActivity extends DevBaseActivity implements
		OnTabChangeListener {
	protected FragmentTabHost mFragmentTabHost;
	private String[] mTabTitles;
	private Class<? extends Fragment>[] mFragments;
	private int mLastIndex;
	private int[] mTabIcons;
	private int mScreenWidth;
	private TabWidget mTabWidget;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment_tabs);
		mScreenWidth = getWindowManager().getDefaultDisplay().getWidth();
		setTabHost();
	}

	ImageView iv;

	public void setTabHost() {
		mTabWidget = (TabWidget) findViewById(android.R.id.tabs);
		mTabTitles = getTabTitles();
		mFragments = getFragments();
		mTabIcons = getTabIcons();
		mFragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);

		mFragmentTabHost.setup(this, getSupportFragmentManager(),
				android.R.id.tabcontent);
		int size = mTabTitles.length;
		for (int i = 0; i < size; i++) {
			TabSpec tab = mFragmentTabHost.newTabSpec(mTabTitles[i]);
			tab.setIndicator(getIndicatorView(i));
			mFragmentTabHost
					.addTab(tab, mFragments[i], getFragmentArguments(i));
		}
//		iv = (ImageView) findViewById(R.id.anim_image);
		mFragmentTabHost.setOnTabChangedListener(this);
//		if (iv != null) {
//			setTabAnimatorImage(iv);
//		}
//		LinearLayout layout = (LinearLayout) findViewById(R.id.anim_container);
//		if (layout != null) {
//			setTabAnimatorContainer(layout);
//		}
	}

	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub
		if (enalbleTabAnimator()) {
			int index = mFragmentTabHost.getCurrentTab();
			AnimatorSet animatorSet = new AnimatorSet();

			ObjectAnimator mAnimator = new ObjectAnimator();
			mAnimator.setPropertyName("x");
			mAnimator
					.setFloatValues(new float[] { ViewHelper.getX(iv)
							+ (float) (mScreenWidth / mFragments.length * (index - mLastIndex)) });
			mAnimator.setTarget(iv);
			animatorSet.playTogether(mAnimator);
			ObjectAnimator animator = ObjectAnimator.ofFloat(iv, "scaleX",
					1.0f, 0.6f, 1f);
			animatorSet.playTogether(animator);
			animatorSet.addListener(new AnimatorListener() {

				@Override
				public void onAnimationStart(Animator arg0) {
					// TODO Auto-generated method stub\
					mTabWidget.setEnabled(false);
				}

				@Override
				public void onAnimationRepeat(Animator arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationEnd(Animator arg0) {
					// TODO Auto-generated method stub
					// mTabImage.setVisibility(View.GONE);
					mTabWidget.setEnabled(true);
				}

				@Override
				public void onAnimationCancel(Animator arg0) {
					// TODO Auto-generated method stub
					// mTabImage.setVisibility(View.GONE);
					mTabWidget.setEnabled(true);
				}
			});
			animatorSet.start();
			mLastIndex = index;
		}
	}

	public boolean enalbleTabAnimator() {
		return false;
	}

	public void setTabAnimatorImage(ImageView iv) {

	}

	public void setTabAnimatorContainer(LinearLayout layout) {
		layout.setWeightSum(mFragments.length);
	}

	/**
	 * 返回底部的tabView
	 * 
	 * @param position
	 * @return
	 */
	public View getIndicatorView(int position) {
		final View v = getLayoutInflater()
				.inflate(R.layout.tab_indicator, null);
		final TextView tv = (TextView) v.findViewById(R.id.title);
		final ImageView iv = (ImageView) v.findViewById(R.id.icon);
		tv.setText(mTabTitles[position]);
		iv.setImageResource(mTabIcons[position]);
		return v;
	}

	/**
	 * 是否使用默认的底部tab栏 此方法未使用
	 * 
	 * @return
	 */
	public boolean useDefaultIndicatorView() {
		return true;
	}

	/**
	 * 返回tab的标题
	 * 
	 * @return
	 */
	public abstract String[] getTabTitles();

	/**
	 * 返回tab对应的fragment
	 * 
	 * @return
	 */
	public abstract Class[] getFragments();

	/**
	 * 返回每个fragment对应的参数
	 * 
	 * @param position
	 * @return
	 */
	public abstract Bundle getFragmentArguments(int position);

	/**
	 * 返回对应的icon
	 * 
	 * @return
	 */
	public abstract int[] getTabIcons();
}
