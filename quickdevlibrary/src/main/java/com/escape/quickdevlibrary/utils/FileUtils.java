package com.escape.quickdevlibrary.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.os.Environment;

public class FileUtils {

	public static String bitmapToFile(Bitmap bitmap, String folderPath,
			String name) {

		File f1 = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/" + folderPath);
		File f = new File(f1.getAbsolutePath(), name);
		try {
			if (!f1.exists()) {
				f1.mkdirs();
			}
			f.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
		try {
			if (fOut != null) {
				fOut.flush();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if (fOut != null) {
				fOut.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return f.getAbsolutePath();
	}

	public static void makePathNoMeida(String path) {
		File file = new File(path, ".nomedia");
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
