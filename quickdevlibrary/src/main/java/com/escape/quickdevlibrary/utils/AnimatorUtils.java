package com.escape.quickdevlibrary.utils;

import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.OvershootInterpolator;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

public class AnimatorUtils {

	/**
	 * 使view抖动的动画
	 * 
	 * @param v
	 * @param maxX
	 * @param shakeCount
	 * @param duration
	 */
	public static void shakeView(View v, float maxX, int shakeCount,
			long duration) {
		ObjectAnimator mAnimator = ObjectAnimator.ofFloat(v, "translationX",
				0f, 15f, 0f, -15f, 0f);
		mAnimator.setInterpolator(new CycleInterpolator(shakeCount));
		mAnimator.setDuration(duration);
		// mAnimator.setRepeatCount(5);
		mAnimator.start();
	}

	/**
	 * 缩放动画
	 * 
	 * @param v
	 */
	public static void scaleAnim(View v) {
		AnimatorSet mAnimatorSet = new AnimatorSet();
		ObjectAnimator mAnimator = new ObjectAnimator();
		mAnimator.setFloatValues(new float[] { 0.3f, 1.5f, 1.0f });
		mAnimator.setTarget(v);
		mAnimator.setDuration(600);
		mAnimator.setPropertyName("scaleX");
		ObjectAnimator mAnimator1 = new ObjectAnimator();
		mAnimator1.setFloatValues(new float[] { 0.3f, 1.5f, 1.0f });
		mAnimator1.setTarget(v);
		mAnimator1.setDuration(600);
		mAnimator1.setPropertyName("scaleY");
		mAnimatorSet.playTogether(mAnimator, mAnimator1);
		mAnimatorSet.setInterpolator(new OvershootInterpolator());
		mAnimatorSet.start();
	}
}
