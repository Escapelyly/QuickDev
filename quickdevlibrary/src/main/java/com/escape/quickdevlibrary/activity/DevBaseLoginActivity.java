package com.escape.quickdevlibrary.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.escape.quickdevlibrary.bean.BaseUserIface;


public abstract class DevBaseLoginActivity<T extends BaseUserIface> extends
		DevBaseActivity {

	private T mT;

	private EditText mEditTextUserCode, mEditTextUserPass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(getLayoutId());
		mT = getSavedUser();
		if (mT != null) {
			mEditTextUserCode.setText(mT.getUserCode());
			mEditTextUserPass.setText(mT.getUserPassWord());
		}
	}

	public abstract T getSavedUser();

	@Override
	public void onSupportContentChanged() {
		// TODO Auto-generated method stub
		super.onSupportContentChanged();
		mEditTextUserCode = (EditText) findViewById(getUserCodeEdittextId());
		mEditTextUserPass = (EditText) findViewById(getUserPassEdittextId());
	}

	public void saveUser() {

	}

	public abstract void login();

	public abstract int getLayoutId();

	public abstract int getUserCodeEdittextId();

	public abstract int getUserPassEdittextId();
}
