package com.escape.quickdevlibrary.manager;

import java.util.ArrayList;
import java.util.List;

public class SelectPhotoManager {

	private static SelectPhotoManager selectPhotoManager;

	private List<String> mSelectedPath;

	private boolean mEditable = true;

	private SelectPhotoManager() {
		mSelectedPath = new ArrayList<String>();
	}

	public static SelectPhotoManager getInstance() {
		if (selectPhotoManager == null) {
			selectPhotoManager = new SelectPhotoManager();
		}

		return selectPhotoManager;
	}

	public void setSelectedPhotos(List<String> paths) {
		mSelectedPath = paths;
	}

	public List<String> getPhotos() {
		return mSelectedPath;
	}

	private int mLimite;// 限制最多上传多多少

	public int getLimited() {
		if (mLimite == 0) {
			mLimite = 1000;
		}
		return mLimite;
	}

	public void setLimite(int limit) {
		mLimite = limit;
	}

	public boolean isEditable() {
		return mEditable;
	}

	public void setEditable(boolean editable) {
		mEditable = editable;
	}
}
