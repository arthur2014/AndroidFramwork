package com.net.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import com.data.ImageLoadingListener;
import com.net.DisplayImageOptions;
import com.net.ImageAware;
import com.net.ImageLoaderConfiguration;
import com.net.stream.ImageInputStream;
import com.thread.ThreadManager;

public class ImageTask implements Task {

	private String imageUrl;
	private ImageAware imageAware;
	private ImageLoadingListener listener;
	private DisplayImageOptions options;
	private ImageLoaderConfiguration configuration;

	private int BufferSize = 32 * 1024;// 32Kb

	public ImageTask() {

	}

	public ImageTask(String imageUrl, ImageAware imageView,
			ImageLoaderConfiguration loaderConfig, DisplayImageOptions options,
			ImageLoadingListener listener) {
		this.imageUrl = imageUrl;
		this.imageAware = imageView;
		this.configuration = loaderConfig;
		this.listener = listener;
		this.options = options;
	}

	@Override
	public void run() {
		if (listener != null) {
			listener.onLoadingStart();
		}
		ImageInputStream imageInputStream = new ImageInputStream();
		File imageFile = configuration.getDiskCache().get(imageUrl);
		try {
			if (imageFile != null) {
				InputStream inputStream = new FileInputStream(imageFile);
				Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
				imageAware.setImageBitmap(bitmap);
				configuration.getMemoryCache().put(imageUrl, bitmap);
				listener.onLoadingCompleted();
				inputStream.close();
				return;
			}

			InputStream inputStream = imageInputStream.getStream(imageUrl);
			Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
			configuration.getDiskCache().save(imageUrl, bitmap);
			configuration.getMemoryCache().put(imageUrl, bitmap);
			imageAware.setImageBitmap(bitmap);
			listener.onLoadingCompleted();
			inputStream.close();
		} catch (IOException e) {
			listener.onLoadingFailed();
		}
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public ImageLoadingListener getListener() {
		return listener;
	}

	public void setListener(ImageLoadingListener listener) {
		this.listener = listener;
	}

	public void excute(Task r, boolean sync, Handler handler) {
		if (sync) {
			r.run();
		} else if (handler != null) {
			handler.post(r);
		} else {
			ThreadManager.getInstance().execute(r);
		}
	}

}
