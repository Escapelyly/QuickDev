package com.escape.quickdevlibrary.fragment;

import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class NetworkStateFragment extends Fragment {
	public static final String ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
	private NetWorkStateFragmentReceiver mNetWorkStateFragmentReceiver;
	State wifiState = null;
	State mobileState = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mNetWorkStateFragmentReceiver = new NetWorkStateFragmentReceiver();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		IntentFilter mFilter = new IntentFilter();
		mFilter.addAction(ACTION);
		getActivity().registerReceiver(mNetWorkStateFragmentReceiver, mFilter);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		getActivity().unregisterReceiver(mNetWorkStateFragmentReceiver);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}

	/**
	 * 
	 */
	private void showNoNetWorkDialog() {
		Builder mBuilder = new Builder(getActivity());
		mBuilder.setMessage("您尚未连接任何网络");
		mBuilder.setTitle("网络出现问题");
		mBuilder.setPositiveButton("去设置", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent intent = null;
				// 判断手机系统的版本 即API大于10 就是3.0或以上版本
				if (android.os.Build.VERSION.SDK_INT > 10) {
					intent = new Intent(
							android.provider.Settings.ACTION_WIRELESS_SETTINGS);
				} else {
					intent = new Intent();
					ComponentName component = new ComponentName(
							"com.android.settings",
							"com.android.settings.WirelessSettings");
					intent.setComponent(component);
					intent.setAction("android.intent.action.VIEW");
				}
				startActivity(intent);
			}
		});
		mBuilder.setNegativeButton("忽略", null);
		mBuilder.show();
	}

	public class NetWorkStateFragmentReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			try {
				if (ACTION.equals(intent.getAction())) {
					// 获取手机的连接服务管理器，这里是连接管理器类
					ConnectivityManager cm = (ConnectivityManager) context
							.getSystemService(Context.CONNECTIVITY_SERVICE);
					NetworkInfo wifiInfo = cm
							.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
					if (wifiInfo != null) {
						wifiState = wifiInfo.getState();
					}
					NetworkInfo mobileInfo = cm
							.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
					if (mobileInfo != null) {
						mobileState = mobileInfo.getState();
					}

					if (wifiState != null && mobileState != null
							&& State.CONNECTED != wifiState
							&& State.CONNECTED == mobileState) {
					} else if (wifiState != null && mobileState != null
							&& State.CONNECTED == wifiState
							&& State.CONNECTED != mobileState) {
					} else if (wifiState != null && mobileState != null
							&& State.CONNECTED != wifiState
							&& State.CONNECTED != mobileState) {
						showNoNetWorkDialog();
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
