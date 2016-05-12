package com.escape.quickdevlibrary.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class ListBaseAdapter<T> extends BaseAdapter {

	private Context mContext;
	private List<T> mList;
	private LayoutInflater mInflater;

	public ListBaseAdapter(Context context, List<T> list) {
		this.mContext = context;
		this.mList = list;
		mInflater = LayoutInflater.from(context);
	}

	public List<T> getList() {
		return mList;
	}

	public LayoutInflater getInflater() {
		return mInflater;
	}

	public Context getContext() {
		return mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public T getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return initView(position, convertView, parent);
	}

	public abstract View initView(int position, View convertView,
			ViewGroup parent);

	public void startActivity(Intent intent) {
		getContext().startActivity(intent);
	}

	public void addItem(T item) {
		mList.add(item);
		notifyDataSetChanged();
	}

	public void addItem(T item, int postion) {
		mList.add(postion, item);
		notifyDataSetChanged();
	}

	public void removeItem(T item) {
		mList.remove(item);
		notifyDataSetChanged();
	}

	public void removeItem(int position) {
		if (position > getCount()) {
			throw new IndexOutOfBoundsException("postion大于列表的size 了");
		}
		mList.remove(position);
		notifyDataSetChanged();
	}

	public static class ViewHolder {
		@SuppressWarnings("unchecked")
		public static <T extends View> T get(View view, int id) {

			SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();

			if (viewHolder == null) {
				viewHolder = new SparseArray<View>();
				view.setTag(viewHolder);
			}

			View childView = viewHolder.get(id);

			if (childView == null) {
				childView = view.findViewById(id);
				viewHolder.put(id, childView);
			}

			return (T) childView;
		}
	}
}
