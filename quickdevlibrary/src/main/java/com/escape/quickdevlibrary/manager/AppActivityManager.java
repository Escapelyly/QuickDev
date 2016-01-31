package com.escape.quickdevlibrary.manager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;

public class AppActivityManager {

	private AppActivityManager() {

	}

	private static AppActivityManager sActivityManager;
	private Context mContext;

	private List<Activity> mActivities;

	public static void init(Context context) {
		sActivityManager = new AppActivityManager();
		sActivityManager.mContext = context;
		sActivityManager.mActivities = new ArrayList<Activity>();
	}

	public static void addActivity(Activity activity) {
		sActivityManager.mActivities.add(activity);
	}

	public static void removeActivity(Activity activity) {
		sActivityManager.mActivities.remove(activity);
	}

	public static void closeAllActivity() {

		for (Activity a : sActivityManager.mActivities) {
			a.finish();
		}
		sActivityManager.mActivities.clear();
	}

	public static <T extends Activity> void closeActivity(Class<T> class1) {

		for (Activity a : sActivityManager.mActivities) {
			if (a.getClass().equals(class1)) {
				a.finish();
				sActivityManager.mActivities.remove(a);
			}
		}
	}
}
