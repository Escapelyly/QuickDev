package com.escape.quickdevlibrary.delegate;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by 洋 on 2016/5/9.
 */
public class SpringRecyclerViewController extends ViewController {
    public SpringRecyclerViewController(Activity activity) {
        super(activity);
    }

    public SpringRecyclerViewController(Fragment fragment) {
        super(fragment);
    }

    @Override
    public void onCreate(Bundle bundle) {

    }

    @Override
    public void onViewCreated() {

    }

    @Override
    public int getLayoutId() {
        return 0;
    }
}
