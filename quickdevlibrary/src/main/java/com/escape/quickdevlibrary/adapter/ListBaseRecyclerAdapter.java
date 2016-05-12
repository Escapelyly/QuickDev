package com.escape.quickdevlibrary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.escape.quickdevlibrary.R;

import java.util.List;

public class ListBaseRecyclerAdapter<T> extends
        RecyclerView.Adapter<ListBaseRecyclerAdapter.YFViewHolder> {

    public boolean isLoadingMore() {
        return mLoadingMore;
    }

    public void setLoadingMore(boolean loadingMore) {
        mLoadingMore = loadingMore;
    }

    public enum RefreshMode {
        PULL_FROM_START, PULL_FROM_END, BOTH, DISABLE;
    }

    public static final int TYPE_LOAD_MORE = 20;

    private boolean mShouldLoadMore;//标记是否需要自动刷新
    private boolean mLoadingMore;//表示是否已经在加载更多了
    private List<T> mList;
    private Context mContext;

    public ListBaseRecyclerAdapter(Context context, List<T> list) {
        mContext = context;
        mList = list;
    }

    public boolean isShouldLoadMore() {
        return mShouldLoadMore;
    }

    public void setShouldLoadMore(boolean shouldLoadMore) {
        mShouldLoadMore = shouldLoadMore;
        notifyDataSetChanged();
    }

    private OnItemClickListener mOnItemClickListener;
    private ViewProvider mViewProvider;

    @Override
    public YFViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_LOAD_MORE) {
            View view = LayoutInflater.from(mContext).inflate(R.layout
                    .list_item_footer_load_more, parent, false);
            return new YFViewHolder(view);
        }
        YFViewHolder yfViewHolder = new YFViewHolder(getView(parent, viewType));
        mViewProvider.onCreateViewHolder(yfViewHolder, viewType );
        return yfViewHolder;
    }


    @Override
    public int getItemViewType(int position) {

        if (mShouldLoadMore) {
            if (position == getItemCount() - 1) {
                return 20;
            }
        }
        if (mViewProvider != null) {
            return mViewProvider.getItemViewType(position,mList.get(position) );
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(final YFViewHolder holder, final int position) {
        if (mShouldLoadMore) {
            if (holder.getItemViewType() == TYPE_LOAD_MORE) {
                if (mLoadingMore) {
                    return;
                }
                mViewProvider.onLoadMore();
                mLoadingMore = true;
                return;
            }
        }
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder, mList.get(position), position, getItemId
                            (position));
                }
            });
        }
        if (mViewProvider != null) {
            mViewProvider.convertObject2View(holder, position,mList.get(position) );
        }
    }

    public View getView(ViewGroup parent, int viewType) {
        if (mViewProvider == null) {
            return new View(mContext);
        }
        return mViewProvider.getView(parent, viewType);
    }

    @Override
    public int getItemCount() {
        if (mViewProvider == null) {
            return 0;
        }
        if (mShouldLoadMore) {
            return mViewProvider.getItemCount() + 1;
        }
        return mViewProvider.getItemCount();
    }

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public ViewProvider getViewProvider() {
        return mViewProvider;
    }

    public void setViewProvider(ViewProvider viewProvider) {
        mViewProvider = viewProvider;
    }

    public interface ViewProvider<T> {
        View getView(ViewGroup parent, int viewType);

        void convertObject2View(YFViewHolder holder, int position, T item);

        int getItemViewType(int position, T item);

        int getItemCount();

        void onCreateViewHolder(YFViewHolder holder, int viewType);

        void onLoadMore();
    }

    public interface OnItemClickListener<T> {
        void onItemClick(YFViewHolder holder, T t, int position, long id);
    }

    public static class YFViewHolder extends RecyclerView.ViewHolder {

        public static final int TYPE_HEADER = 0;
        public static final int TYPE_FOOTER = 1;
        public static final int TYPE_NORMAL = 2;
        private SparseArray<View> mViews = new SparseArray<>();

        public YFViewHolder(View itemView) {
            super(itemView);
        }

        public <T extends View> T getView(int id) {
            T t = (T) mViews.get(id);
            if (t == null) {
                t = (T) itemView.findViewById(id);
                if (t != null) {
                    mViews.put(id, t);
                }
            }
            return t;
        }

        public void setText(int textViewId, String text) {
            TextView textView = getView(textViewId);
            if (textView != null) {
                textView.setText(text);
            }
        }
    }


}
