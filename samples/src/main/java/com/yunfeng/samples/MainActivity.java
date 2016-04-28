package com.yunfeng.samples;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.escape.quickdevlibrary.activity.DevBaseActivity;
import com.escape.quickdevlibrary.adapter.ListBaseRecyclerAdapter;
import com.escape.quickdevlibrary.delegate.RecyclerViewController;
import com.escape.quickdevlibrary.delegate.ViewController;

public class MainActivity extends DevBaseActivity {
    @Override
    public ViewController onCreateViewController() {
        return new MainRecycler(this);
    }

    class MainRecycler extends RecyclerViewController<Object> {

        public MainRecycler(Activity activity) {
            super(activity);
        }

        @Override
        public void loadData() {

        }

        @Override
        public void onItemClick(ListBaseRecyclerAdapter.YFViewHolder holder, Object o, int position, long id) {

        }

        @Override
        public View getView(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void convertObject2View(ListBaseRecyclerAdapter.YFViewHolder holder, int position, Object item) {

        }

    }
}
