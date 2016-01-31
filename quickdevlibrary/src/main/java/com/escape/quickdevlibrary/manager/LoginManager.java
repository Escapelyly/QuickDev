package com.escape.quickdevlibrary.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.ly.quickdev.library.bean.BaseUserIface;
import com.ly.quickdev.library.utils.JsonUtils;

public class LoginManager<T extends BaseUserIface> {

	public boolean isLogined() {
		return mLogined;
	}

	private T mT;

	protected boolean mLogined;

	public LoginManager(Context context) {
		mPreferences = context.getSharedPreferences("user",
				Context.MODE_PRIVATE);
	}

	private SharedPreferences mPreferences;

	private LoginManager() {

	}

	public void saveUser(T t) {
		mPreferences.edit().putString("user", new Gson().toJson(t)).commit();
		mT = t;
		mLogined = true;
	}

	public void saveUser(T t, boolean saveToDisk) {
		clearLoginState();
		if (saveToDisk) {
			saveUser(t);
		} else {
			mT = t;
			mLogined = true;
		}
	}

	public T getCurrentUser(Class<T> class1) {
		if (mT != null) {
			return mT;
		}
		mT = JsonUtils.parse(mPreferences.getString("user", ""), class1);
		return mT;
	}

	public void clearLoginState() {
		mPreferences.edit().clear().commit();
		mT = null;
		mLogined = false;
	}

	public SharedPreferences getSharedPreferences() {
		return mPreferences;
	}
}
