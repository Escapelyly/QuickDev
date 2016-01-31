package com.escape.quickdevlibrary.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore.MediaColumns;

public class UriUtils {
	/**
	 * Gets the corresponding path to a file from the given content:// URI
	 * 
	 * @param selectedVideoUri
	 *            The content:// URI to find the file path from
	 * @param contentResolver
	 *            The content resolver to use to perform the query.
	 * @return the file path as a string
	 */
	@SuppressLint("NewApi")
	public static String getFilePathFromContentUri(Uri selectedVideoUri,
			ContentResolver contentResolver, Context context) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			if (DocumentsContract.isDocumentUri(context, selectedVideoUri)) {

			}
		}

		String filePath;
		String[] filePathColumn = { MediaColumns.DATA };

		Cursor cursor = contentResolver.query(selectedVideoUri, filePathColumn,
				null, null, null);
		// 也可用下面的方法拿到cursor
		// Cursor cursor = this.context.managedQuery(selectedVideoUri,
		// filePathColumn, null, null, null);

		cursor.moveToFirst();

		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		filePath = cursor.getString(columnIndex);
		cursor.close();
		return filePath;
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri
				.getAuthority());
	}

}
