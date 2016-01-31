package com.escape.quickdevlibrary.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.escape.quickdevlibrary.R;
import com.escape.quickdevlibrary.adapter.ListBaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class DevBaseRecyclerViewActivity<T> extends DevBaseActivity implements
        ListBaseRecyclerAdapter.OnItemClickListener, ListBaseRecyclerAdapter.ViewProvider,
        SwipeRefreshLayout.OnRefreshListener {
    protected List<T> mList = new ArrayList<>();
    protected RecyclerView mRecyclerView;
    protected ListBaseRecyclerAdapter<T> mAdapter;
    protected View mEmptyView, mLoadingView;
    protected int mDefaultPageNo = 0;
    protected int mPageNo = mDefaultPageNo;
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_base_recycler_view);
        mRecyclerView = findView(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        RecyclerView.LayoutManager layoutManager = onCreateLayoutManager(mRecyclerView);
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        }
        mEmptyView = findView(R.id.empty);
        mLoadingView = findView(R.id.loading);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = onCreateAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        beforeLoadData();
        loadData();
    }

    /**
     * 是否启用刷新
     *
     * @param enable
     */
    public void enableSwipeRefresh(boolean enable) {
        mSwipeRefreshLayout.setEnabled(enable);
    }

    public void enableLoadMore(boolean enable) {
        mAdapter.setShouldLoadMore(enable);
    }

    public void onLoadMore() {

    }

    /**
     * 列表长度
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mList.size();
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

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    /**
     *
     */
    public void setListAdapter() {
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

    /**
     * @param list
     */
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

    public void addItem(int location, T t) {
        mList.add(location, t);
        notifyItemInserted(location);
    }

    public void deleteItem(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
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
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.setOnItemClickListener(null);
        mAdapter.setViewProvider(null);
    }

    @Override
    public void onRefresh() {
        mPageNo = mDefaultPageNo;
        loadData();
    }

    @Override
    public void onCreateViewHolder(ListBaseRecyclerAdapter.YFViewHolder holder, int viewType) {

    }

    public abstract RecyclerView.LayoutManager onCreateLayoutManager(RecyclerView view);

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
    }

    public int getDefaultPageNo() {
        return mDefaultPageNo;
    }

    public void setDefaultPageNo(int defaultPageNo) {
        mDefaultPageNo = defaultPageNo;
    }
}