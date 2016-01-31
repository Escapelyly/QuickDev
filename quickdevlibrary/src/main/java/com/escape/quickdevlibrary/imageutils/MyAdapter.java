package com.escape.quickdevlibrary.imageutils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.escape.quickdevlibrary.R;
import com.escape.quickdevlibrary.bean.Constants;
import com.escape.quickdevlibrary.manager.SelectPhotoManager;

import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends CommonAdapter<String> {

	/**
	 * 用户选择的图片，存储为图片的完整路径
	 */
	public List<String> mSelectedImage;
	private TextCallback textcallback = null;
	private int mark = 0;
	private int position;
	Context context;
	private String mAction;
	/**
	 * 文件夹路径
	 */
	private String mDirPath;

	private List<String> mDatas;
	private int mLimit;

	public MyAdapter(Context context, List<String> mDatas, int itemLayoutId,
			String dirPath, String action) {
		super(context, mDatas, itemLayoutId);
		this.mDirPath = dirPath;
		this.context = context;
		this.mDatas = new ArrayList<String>();
		for (String s : mDatas) {
			this.mDatas.add(mDirPath + "/" + s);
		}
		mAction = action;
		mSelectedImage = SelectPhotoManager.getInstance().getPhotos();
		mLimit = SelectPhotoManager.getInstance().getLimited();
	}

	public static interface TextCallback {
		public void onListen(int count);
	}

	public void setTextCallback(TextCallback textCallback2) {
		textcallback = textCallback2;
	}

	@SuppressLint("NewApi")
	@Override
	public void convert(final ViewHolder helper, final String item,
			final int position) {
		// 设置no_pic
		helper.setImageResource(R.id.id_item_image, R.drawable.pictures_no);
		// 设置no_selected
		helper.setImageResource(R.id.id_item_select,
				R.drawable.picture_unselected);
		// 设置图片
		helper.setImageByUrl(R.id.id_item_image, mDirPath + "/" + item);

		final ImageView mImageView = helper.getView(R.id.id_item_image);
		final ImageView mSelect = helper.getView(R.id.id_item_select);

		mImageView.setColorFilter(null);
		// 设置ImageView的点击事件
		mSelect.setOnClickListener(new OnClickListener() {
			// 选择，则将图片变暗，反之则反之
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				String path = mDirPath + "/" + item;

				if (mAction.equals(Constants.ACTION_CHOOSE_PHOTO_MULTI)) {
					// 已经选择过该图片
					if (mSelectedImage.contains(path)) {
						mSelectedImage.remove(path);
						if (textcallback != null)
							textcallback.onListen(mSelectedImage.size());
						mSelect.setImageResource(R.drawable.picture_unselected);
						mImageView.setColorFilter(null);
					} else
					// 未选择该图片
					{
						if (mSelectedImage.size() >= mLimit) {
							Toast.makeText(context, "本次可以选择的照片已经达到上限",
									Toast.LENGTH_SHORT).show();
							return;
						}
						mSelectedImage.add(path);
						if (textcallback != null)
							textcallback.onListen(mSelectedImage.size());
						mSelect.setImageResource(R.drawable.pictures_selected);
						mImageView.setColorFilter(Color.parseColor("#77000000"));
					}
				} else {
					Activity activity = (Activity) context;
					Intent i = new Intent();
					i.putExtra(Constants.KEY_DATA, path);
					activity.setResult(Activity.RESULT_OK, i);
					activity.finish();
				}

			}
		});

		/**
		 * 已经选择过的图片，显示出选择过的效果
		 */
		if (mSelectedImage.contains(mDirPath + "/" + item)) {
			mSelect.setImageResource(R.drawable.pictures_selected);
			mImageView.setColorFilter(Color.parseColor("#77000000"));
		}

	}
}
