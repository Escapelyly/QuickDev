package com.escape.quickdevlibrary.activity;

import android.os.Bundle;

import com.escape.quickdevlibrary.R;
import com.escape.quickdevlibrary.delegate.ViewController;

public abstract class DevBaseRecyclerViewActivity<T> extends DevBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_base_recycler_view);
    }

    @Override
    public ViewController onCreateViewController() {
        return null;
    }
}
