package com.data;

import android.graphics.Bitmap;

import com.cache.ImageCache;

public class ImageManager implements Loader,ReleaseAble{
	private static ImageManager instance;

	private ImageCache cache;

	private ImageManager(){
		
	}
	
	public static ImageManager getInstance(){
		if(instance==null){
			instance=new ImageManager();
		}
		return instance;
	}
	
	@Override
	public Bitmap load(String imageUrl, ImageLoadingListener listener) {
		return null;
	}

	@Override
	public Bitmap syncLoad(String imageUrl) {
		return null;
	}

	@Override
	public void relese() {
		
	}
	
	
}
