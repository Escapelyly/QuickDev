package com.escape.quickdevlibrary.activity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.escape.quickdevlibrary.R;
import com.escape.quickdevlibrary.bean.Constants;
import com.escape.quickdevlibrary.delegate.ViewController;
import com.escape.quickdevlibrary.imageutils.ImageFloder;
import com.escape.quickdevlibrary.imageutils.ListImageDirPopupWindow;
import com.escape.quickdevlibrary.imageutils.MyAdapter;
import com.escape.quickdevlibrary.manager.SelectPhotoManager;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


/**
 * 选择照片，包括多选和单选
 * 
 * @author 洋
 * 
 */
public class ChoosePhotoActivity extends DevBaseActivity implements
		ListImageDirPopupWindow.OnImageDirSelected, MyAdapter.TextCallback {

	private ProgressDialog mProgressDialog;

	private int mPicsSize;
	private File mImgDir;
	private List<String> mImgs;

	private GridView mGirdView;
	private MyAdapter mAdapter;
	private HashSet<String> mDirPaths = new HashSet<String>();

	private List<ImageFloder> mImageFloders = new ArrayList<ImageFloder>();

	private View mBottomLy;
	private ImageView back, sure;
	private TextView mChooseDir;
	private TextView mImageCount;
	int totalCount = 0;

	private int mScreenHeight;
	private int mMode;
	private ListImageDirPopupWindow mListImageDirPopupWindow;
	private String mAction;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			mProgressDialog.dismiss();
			// 为View绑定数据
			data2View();
			// 初始化展示文件夹的popupWindw
			initListDirPopupWindw();
		}
	};

	@Override
	public ViewController onCreateViewController() {
		return null;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_photo);
		setTitle("选择要打印的照片");
		// setRightIcon(R.drawable.sure);
		mAction = getIntent().getAction();
		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		mScreenHeight = outMetrics.heightPixels;
		initView();
		getImages();
		initEvent();
	}

	public void done(View v) {
		List<String> path = SelectPhotoManager.getInstance().getPhotos();
		setResult(RESULT_OK, new Intent().putStringArrayListExtra(
				Constants.KEY_DATA, (ArrayList<String>) path));
		finish();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mAdapter != null) {
			mAdapter.notifyDataSetChanged();
		}
	}

	private void data2View() {
		if (mImgDir == null) {
			// Toast.makeText(getApplicationContext(), "擦，一张图片没扫描到",
			// Toast.LENGTH_SHORT).show();
			return;
		}

		mImgs = Arrays.asList(mImgDir.list());
		mAdapter = new MyAdapter(getActivity(), mImgs, R.layout.grid_item,
				mImgDir.getAbsolutePath(), mAction);
		mGirdView.setAdapter(mAdapter);
		mAdapter.setTextCallback(this);
	};

	private void initListDirPopupWindw() {
		mListImageDirPopupWindow = new ListImageDirPopupWindow(
				LayoutParams.MATCH_PARENT, (int) (mScreenHeight * 0.7),
				mImageFloders, LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.list_dir, null));

		mListImageDirPopupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				// 设置背景颜色变暗
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1.0f;
				getWindow().setAttributes(lp);
			}
		});
		// 设置选择文件夹的回调
		mListImageDirPopupWindow.setOnImageDirSelected(this);
	}

	private void getImages() {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
			return;
		}
		// 显示进度条
		mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

		new Thread(new Runnable() {
			@Override
			public void run() {
				String firstImage = null;

				Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				ContentResolver mContentResolver = ChoosePhotoActivity.this
						.getContentResolver();

				// 只查询jpeg和png的图片
				Cursor mCursor = mContentResolver.query(mImageUri, null,
						MediaStore.Images.Media.MIME_TYPE + "=? or "
								+ MediaStore.Images.Media.MIME_TYPE + "=?",
						new String[] { "image/jpeg", "image/png" },
						MediaStore.Images.Media.DATE_MODIFIED);

				while (mCursor.moveToNext()) {
					// 获取图片的路径
					String path = mCursor.getString(mCursor
							.getColumnIndex(MediaStore.Images.Media.DATA));

					// 拿到第一张图片的路径
					if (firstImage == null)
						firstImage = path;
					// 获取该图片的父路径名
					File parentFile = new File(path).getParentFile();
					if (parentFile == null)
						continue;
					String dirPath = parentFile.getAbsolutePath();
					ImageFloder imageFloder = null;
					// 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
					if (mDirPaths.contains(dirPath)) {
						continue;
					} else {
						mDirPaths.add(dirPath);
						// 初始化imageFloder
						imageFloder = new ImageFloder();
						imageFloder.setDir(dirPath);
						imageFloder.setFirstImagePath(path);
					}
					String[] pics = parentFile.list(new FilenameFilter() {
						@Override
						public boolean accept(File dir, String filename) {
							if (filename.endsWith(".jpg")
									|| filename.endsWith(".png")
									|| filename.endsWith(".jpeg"))
								return true;
							return false;
						}
					});
					int picSize = 0;
					if (pics != null) {
						picSize = pics.length;
					}

					totalCount += picSize;

					imageFloder.setCount(picSize);
					if (pics != null) {
						mImageFloders.add(imageFloder);
					}

					if (picSize > mPicsSize) {
						mPicsSize = picSize;
						mImgDir = parentFile;
					}
				}
				mCursor.close();

				// 扫描完成，辅助的HashSet也就可以释放内存了
				mDirPaths = null;

				// 通知Handler扫描图片完成
				mHandler.sendEmptyMessage(0x110);
			}
		}).start();

	}

	private void initView() {
		mGirdView = (GridView) findViewById(R.id.id_gridView);
		mChooseDir = (TextView) findViewById(R.id.id_choose_dir);
		mImageCount = (TextView) findViewById(R.id.id_total_count);

		mBottomLy = findViewById(R.id.id_bottom_ly);
		mImageCount.setText("已选"
				+ SelectPhotoManager.getInstance().getPhotos().size() + "张");
	}

	private void initEvent() {
		mBottomLy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mListImageDirPopupWindow
						.setAnimationStyle(R.style.anim_popup_dir);
				mListImageDirPopupWindow.showAsDropDown(mBottomLy, 0, 0);

				// 设置背景颜色变暗
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = .3f;
				getWindow().setAttributes(lp);
			}
		});
	}

	@Override
	public void selected(ImageFloder floder) {

		mImgDir = new File(floder.getDir());
		mImgs = Arrays.asList(mImgDir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String filename) {
				if (filename.endsWith(".jpg") || filename.endsWith(".JPG")
						|| filename.endsWith(".PNG")
						|| filename.endsWith(".JPEG")
						|| filename.endsWith(".png")
						|| filename.endsWith(".jpeg"))
					return true;
				return false;
			}
		}));
		mAdapter = new MyAdapter(getActivity(), mImgs, R.layout.grid_item,
				mImgDir.getAbsolutePath(), mAction);
		mGirdView.setAdapter(mAdapter);
		mAdapter.setTextCallback(this);
		// mAdapter.notifyDataSetChanged();
		mChooseDir.setText(floder.getName());
		mListImageDirPopupWindow.dismiss();

	}

	@Override
	public void onListen(int count) {
		// TODO Auto-generated method stub
		mImageCount.setText("已选" + count + "张");
	}
}
