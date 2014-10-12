package com.net;

import android.content.res.Resources;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable;
import android.os.Handler;


public final class DisplayImageOptions {

	 final int imageResOnLoading;
	 final int imageResForEmptyUri;
	 final int imageResOnFail;
	 final Drawable imageOnLoading;
	 final Drawable imageForEmptyUri;
	 final Drawable imageOnFail;
	 final boolean resetViewBeforeLoading;
	 final boolean cacheInMemory;
	 final boolean cacheOnDisk;
//	private final ImageScaleType imageScaleType;
	 final Options decodingOptions;
	 final int delayBeforeLoading;
	 final boolean considerExifParams;
	 final Object extraForDownloader;
//	private final BitmapProcessor preProcessor;
//	private final BitmapProcessor postProcessor;
//	private final BitmapDisplayer displayer;
	 final Handler handler;
	 final boolean isSyncLoading;
	
	private DisplayImageOptions(Builder builder){
		imageResOnLoading = builder.imageResOnLoading;
		imageResForEmptyUri = builder.imageResForEmptyUri;
		imageResOnFail = builder.imageResOnFail;
		imageOnLoading = builder.imageOnLoading;
		imageForEmptyUri = builder.imageForEmptyUri;
		imageOnFail = builder.imageOnFail;
		resetViewBeforeLoading = builder.resetViewBeforeLoading;
		cacheInMemory = builder.cacheInMemory;
		cacheOnDisk = builder.cacheOnDisk;
//		imageScaleType = builder.imageScaleType;
		decodingOptions = builder.decodingOptions;
		delayBeforeLoading = builder.delayBeforeLoading;
		considerExifParams = builder.considerExifParams;
		extraForDownloader = builder.extraForDownloader;
//		preProcessor = builder.preProcessor;
//		postProcessor = builder.postProcessor;
//		displayer = builder.displayer;
		handler = builder.handler;
		isSyncLoading = builder.isSyncLoading;
	}
	
	public boolean shouldShowImageOnLoading(){
		return imageResOnLoading!=0 || imageOnLoading!=null;
	}
	
	public boolean shouldShowImageForEmptyUri() {
		return imageForEmptyUri != null || imageResForEmptyUri != 0;
	}
	
	public boolean shouldShowImageOnFail() {
		return imageOnFail != null || imageResOnFail != 0;
	}
	
	public boolean shouldDelayBeforeLoading() {
		return delayBeforeLoading > 0;
	}
	
	public Drawable getImageOnLoading(Resources res) {
		return imageResOnLoading != 0 ? res.getDrawable(imageResOnLoading) : imageOnLoading;
	}
	
	public boolean isSyncLoading(){
		return isSyncLoading;
	}
	public static class Builder{
		private int imageResOnLoading = 0;
		private int imageResForEmptyUri = 0;
		private int imageResOnFail = 0;
		private Drawable imageOnLoading = null;
		private Drawable imageForEmptyUri = null;
		private Drawable imageOnFail = null;
		private boolean resetViewBeforeLoading = false;
		private boolean cacheInMemory = false;
		private boolean cacheOnDisk = false;
//		private ImageScaleType imageScaleType = ImageScaleType.IN_SAMPLE_POWER_OF_2;
		private Options decodingOptions = new Options();
		private int delayBeforeLoading = 0;
		private boolean considerExifParams = false;
		private Object extraForDownloader = null;
//		private BitmapProcessor preProcessor = null;
//		private BitmapProcessor postProcessor = null;
//		private BitmapDisplayer displayer = DefaultConfigurationFactory.createBitmapDisplayer();
		private Handler handler = null;
		private boolean isSyncLoading = false;
		public Builder(){
			
		}
		
		public DisplayImageOptions build(){
			return new DisplayImageOptions(this);
		}
	}
}
