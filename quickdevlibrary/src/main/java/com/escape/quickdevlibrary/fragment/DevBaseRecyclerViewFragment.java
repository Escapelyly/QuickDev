package com.escape.quickdevlibrary.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.escape.quickdevlibrary.R;
import com.escape.quickdevlibrary.adapter.ListBaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 洋 on 2015/12/10.
 */
public abstract class DevBaseRecyclerViewFragment<T> extends DevBaseFragment implements
        ListBaseRecyclerAdapter.OnItemClickListener<T>, ListBaseRecyclerAdapter.ViewProvider<T>,
        SwipeRefreshLayout.OnRefreshListener {
    protected List<T> mList = new ArrayList<>();
    protected RecyclerView mRecyclerView;
    protected ListBaseRecyclerAdapter<T> mAdapter;
    private View mEmptyView, mLoadingView;
    protected int mDefaultPageNo = 0;
    protected int mPageNo = mDefaultPageNo;
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    public void enableLoadMore(boolean enable) {
        mAdapter.setShouldLoadMore(enable);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_dev_base_recycler_view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh);
        RecyclerView.LayoutManager layoutManager = onCreateLayoutManager(mRecyclerView);
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        }
        mEmptyView = findViewById(R.id.empty);
        mLoadingView = findViewById(R.id.loading);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = onCreateAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        beforeLoadData();
        loadData();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position, Object item) {
        return 0;
    }

    public void enableSwipeRefresh(boolean enable) {
        mSwipeRefreshLayout.setEnabled(enable);
    }

    public void showList() {
        if (mAdapter.getItemCount() == 0) {
            mEmptyView.setVisibility(View.VISIBLE);
            mLoadingView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mEmptyView.setVisibility(View.GONE);
            mLoadingView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }


    public void setListAdapter() {
        if (getActivity() == null) {
            return;
        }
        if (mAdapter == null) {
            mAdapter = onCreateAdapter();
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
        mSwipeRefreshLayout.setRefreshing(false);
        showList();
        mAdapter.setLoadingMore(false);
    }

    public void addItems(List<T> list) {
        int oldSize = mList.size();
        mList.addAll(list);
        notifyItemRangeInserted(oldSize, list.size());
    }

    public void addItem(T t) {
        mList.add(t);
        notifyItemInserted(mList.size() - 1);
    }

    public void addItems(int location, List<T> list) {
        mList.addAll(location, list);
        notifyItemRangeInserted(location, list.size());
    }

    @Override
    public void onCreateViewHolder(ListBaseRecyclerAdapter.YFViewHolder holder, int viewType) {

    }

    @Override
    public void onLoadMore() {

    }

    public void addItem(int location, T t) {
        mList.add(location, t);
        notifyItemInserted(location);
    }

    public void deleteItem(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public void deleteItems(int from, int to) {
        List<T> temp = new ArrayList<>();
        for (; from < to; from++) {
            temp.add(mList.get(from));
        }
        mList.removeAll(temp);
        notifyItemRangeRemoved(from, to);
    }

    public void deleteItems(List<T> list) {
        mList.removeAll(list);
        notifyDataSetChanged();
    }

    public void swap(int from, int to) {
        Collections.swap(mList, from, to);
        notifyItemMoved(from, to);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.setOnItemClickListener(null);
            mAdapter.setViewProvider(null);
        }
    }

    @Override
    public void onRefresh() {
        mPageNo = mDefaultPageNo;
        loadData();
    }

    public RecyclerView.LayoutManager onCreateLayoutManager(RecyclerView view) {
        return null;
    }

    public void beforeLoadData() {

    }

    public abstract void loadData();

    public ListBaseRecyclerAdapter<T> onCreateAdapter() {
        mAdapter = new ListBaseRecyclerAdapter<T>(getActivity(), mList);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setViewProvider(this);
        return mAdapter;
    }

    public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }

    public void notifyItemChanged(int position) {
        mAdapter.notifyItemChanged(position);
    }

    public void notifyItemChanged(int position, Object payload) {
        mAdapter.notifyItemChanged(position, payload);
    }

    public void notifyItemInserted(int position) {
        mAdapter.notifyItemInserted(position);
    }

    public void notifyItemMoved(int fromPosition, int toPosition) {
        mAdapter.notifyItemMoved(fromPosition, toPosition);
    }

    public void notifyItemRangeChanged(int positionStart, int itemCount) {
        mAdapter.notifyItemRangeChanged(positionStart, itemCount);
    }

    public void notifyItemRangeChanged(int positionStart, int itemCount, Object payload) {
        mAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
    }

    public void notifyItemRangeInserted(int positionStart, int itemCount) {
        mAdapter.notifyItemRangeInserted(positionStart, itemCount);
    }

    public void notifyItemRangeRemoved(int positionStart, int itemCount) {
        mAdapter.notifyItemRangeRemoved(positionStart, itemCount);
    }

    public void notifyItemRemoved(int position) {
        mAdapter.notifyItemRemoved(position);
        mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
        showList();
    }

    public int getDefaultPageNo() {
        return mDefaultPageNo;
    }

    public void setDefaultPageNo(int defaultPageNo) {
        mDefaultPageNo = defaultPageNo;
    }

    public void setHolderTextViewText(ListBaseRecyclerAdapter.YFViewHolder holder, int textId,
                                      String text) {
        TextView textView = holder.getView(textId);
        if (textView != null) {
            textView.setText(text);
        }
    }
}
