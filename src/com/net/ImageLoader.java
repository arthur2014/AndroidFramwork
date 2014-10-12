package com.net;

import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import com.net.listener.ImageLoadingListener;
import com.net.listener.ImageLoadingProgressListener;
import com.net.task.ImageTask;
import com.net.task.ImageTaskFactory;

public class ImageLoader {

	private static ImageLoader instance;

	private final ImageLoadingListener emptyListener =new SimpleImageLoadingListener();
	private ImageLoaderConfiguration configuration;
	
	private volatile  ImageLoaderEngine loaderEngine;
	private ImageLoader() {

	}

	public static ImageLoader getInstance() {
		synchronized (ImageLoader.class) {
			if (instance == null) {
				instance = new ImageLoader();
			}
			return instance;
		}
	}

	public synchronized void init(ImageLoaderConfiguration configuration){
		if(configuration==null){
			throw new IllegalArgumentException();
		}
		if(this.configuration==null){
			this.configuration=configuration;
			loaderEngine=new ImageLoaderEngine(configuration);
		}else{
			
		}
	}
	public void display(String imageUrl, ImageAware viewAware,
			DisplayImageOptions options) {
		display(imageUrl,viewAware,options,null);
	}

	
	public void display(String imageUrl, ImageAware viewAware,
			DisplayImageOptions options, ImageLoadingListener listener) {
		
		if(viewAware==null){
			throw new IllegalArgumentException();
		}
		
		if(listener==null){
			listener=emptyListener;
		}
		
		if(options==null){
			options=configuration.defaultDisplayImageOptions;
		}
		
		if(TextUtils.isEmpty(imageUrl)){
			
			return;
		}
		if(options.shouldShowImageOnLoading()){
			viewAware.setImageDrawable(options.imageOnLoading);
		}
		Bitmap bitmap=configuration.memoryCache.get(imageUrl);
		if(bitmap!=null){
			listener.onLoadingStarted(imageUrl, viewAware);
			viewAware.setImageBitmap(bitmap);
			listener.onLoadingComplete(imageUri, view, loadedImage);
			return;
		}
		ImageTask task=ImageTaskFactory.get(imageUrl,viewAware,configuration);
		if(options.isSyncLoading()){
			task.run();
			return;
		}else if(options.handler!=null){
			options.handler.post(task);
			return;
		}
		loaderEngine.submit(task);
		
	}
	
	private class SimpleImageLoadingListener implements ImageLoadingListener {
		@Override
		public void onLoadingStarted(String imageUri, View view) {
			// Empty implementation
		}

		@Override
		public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
			// Empty implementation
		}

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			// Empty implementation
			
		}

		@Override
		public void onLoadingCancelled(String imageUri, View view) {
			// Empty implementation
		}

		@Override
		public void onProgressUpdate(String imageUri, View view, int current,
				int total) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
