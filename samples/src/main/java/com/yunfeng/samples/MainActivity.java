package com.yunfeng.samples;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        public void onCreate(Bundle bundle) {

        }

        @Override
        public void loadData() {
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
            setListAdapter();
        }

        @Override
        public void onItemClick(ListBaseRecyclerAdapter.YFViewHolder holder, Object o, int position, long id) {

        }

        @Override
        public View getView(ViewGroup parent, int viewType) {
            return new TextView(getActivity());
        }

        @Override
        public void convertObject2View(ListBaseRecyclerAdapter.YFViewHolder holder, int position, Object item) {
            TextView textView = (TextView) holder.itemView;
            textView.setText(position + "");
        }

    }
}
