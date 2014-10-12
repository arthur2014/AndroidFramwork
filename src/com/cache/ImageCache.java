package com.cache;

import java.io.File;
import java.io.IOException;

import android.graphics.Bitmap;

import com.cache.disc.DiscCache;
import com.cache.memory.MemoryCache;
import com.data.ImageLoadingListener;
import com.decode.ImageDecodeInfo;
import com.decode.ImageDecoder;
import com.util.MD5NameGenerator;
import com.util.NameGenerator;

/**
 * 
 * @author Administrator
 * 
 */
public class ImageCache {

	private MemoryCache memoryCache;
	private DiscCache diskCache;

	private NameGenerator nameGenerator;
	private ImageDecoder imageDecoder;

	public ImageCache() {

	}

	public Bitmap getBitmap(String imageUrl, ImageLoadingListener listener) {
		String key=getNameGenerator().generate(imageUrl);
		Bitmap tempBitmap=memoryCache.get(key);
		if(tempBitmap==null){
			File file=diskCache.get(imageUrl);
			try {
				tempBitmap=getImageDecoder().decode(new ImageDecodeInfo(file));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(tempBitmap==null){
			//load on network
		}
		return tempBitmap;
	}

	public Bitmap syncgetBitmap(String imageUrl) {
		String key=getNameGenerator().generate(imageUrl);
		Bitmap tempBitmap=memoryCache.get(key);
		if(tempBitmap==null){
			File file=diskCache.get(imageUrl);
			try {
				tempBitmap=getImageDecoder().decode(new ImageDecodeInfo(file));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return tempBitmap;
	}

	public ImageDecoder getImageDecoder() {
		if(imageDecoder==null){
			imageDecoder=new ImageDecoder();
		}
		return imageDecoder;
	}

	public void setImageDecoder(ImageDecoder imageDecoder) {
		this.imageDecoder = imageDecoder;
	}

	// default generator is MD5
	private NameGenerator getNameGenerator() {
		if (nameGenerator == null) {
			nameGenerator = new MD5NameGenerator();
		}
		return nameGenerator;
	}

	public void setNameGenerator(NameGenerator nameGenerator) {
		this.nameGenerator = nameGenerator;
	}

	public void setMemoryCache(MemoryCache memoryCache) {
		this.memoryCache = memoryCache;
	}

	public void setDiskCache(DiscCache diskCache) {
		this.diskCache = diskCache;
	}
}