package com.net;

import java.util.concurrent.Executor;

import android.content.Context;
import android.content.res.Resources;

import com.cache.disc.DiscCache;
import com.cache.memory.MemoryCache;
import com.decode.ImageDecoder;

public class ImageLoaderConfiguration {

	final Resources resources;

	final int maxImageWidthForMemoryCache;
	final int maxImageHeightForMemoryCache;
	final int maxImageWidthForDiskCache;
	final int maxImageHeightForDiskCache;
//	final BitmapProcessor processorForDiskCache;

	final Executor taskExecutor;
	final Executor taskExecutorForCachedImages;
	final boolean customExecutor;
	final boolean customExecutorForCachedImages;

	final int threadPoolSize;
	final int threadPriority;
//	final QueueProcessingType tasksProcessingType;

	final MemoryCache memoryCache;
	final DiscCache diskCache;
//	final ImageDownloader downloader;
	final ImageDecoder decoder;
	final DisplayImageOptions defaultDisplayImageOptions;

//	final ImageDownloader networkDeniedDownloader;
//	final ImageDownloader slowNetworkDownloader;
	
	public ImageLoaderConfiguration(final Builder builder){
		resources = builder.context.getResources();
		maxImageWidthForMemoryCache = builder.maxImageWidthForMemoryCache;
		maxImageHeightForMemoryCache = builder.maxImageHeightForMemoryCache;
		maxImageWidthForDiskCache = builder.maxImageWidthForDiskCache;
		maxImageHeightForDiskCache = builder.maxImageHeightForDiskCache;
//		processorForDiskCache = builder.processorForDiskCache;
		taskExecutor = builder.taskExecutor;
		taskExecutorForCachedImages = builder.taskExecutorForCachedImages;
		threadPoolSize = builder.threadPoolSize;
		threadPriority = builder.threadPriority;
//		tasksProcessingType = builder.tasksProcessingType;
		diskCache = builder.diskCache;
		memoryCache = builder.memoryCache;
		defaultDisplayImageOptions = builder.defaultDisplayImageOptions;
//		downloader = builder.downloader;
		decoder = builder.decoder;

		customExecutor = builder.customExecutor;
		customExecutorForCachedImages = builder.customExecutorForCachedImages;

//		networkDeniedDownloader = new NetworkDeniedImageDownloader(downloader);
//		slowNetworkDownloader = new SlowNetworkImageDownloader(downloader);
	}

	public MemoryCache getMemoryCache() {
		return memoryCache;
	}

	public DiscCache getDiskCache() {
		return diskCache;
	}

	public class Builder{
		/** {@value} */
		public static final int DEFAULT_THREAD_POOL_SIZE = 3;
		/** {@value} */
		public static final int DEFAULT_THREAD_PRIORITY = Thread.NORM_PRIORITY - 2;
		/** {@value} */
//		public static final QueueProcessingType DEFAULT_TASK_PROCESSING_TYPE = QueueProcessingType.FIFO;

		private Context context;

		private int maxImageWidthForMemoryCache = 0;
		private int maxImageHeightForMemoryCache = 0;
		private int maxImageWidthForDiskCache = 0;
		private int maxImageHeightForDiskCache = 0;
//		private BitmapProcessor processorForDiskCache = null;

		private Executor taskExecutor = null;
		private Executor taskExecutorForCachedImages = null;
		private boolean customExecutor = false;
		private boolean customExecutorForCachedImages = false;

		private int threadPoolSize = DEFAULT_THREAD_POOL_SIZE;
		private int threadPriority = DEFAULT_THREAD_PRIORITY;
		private boolean denyCacheImageMultipleSizesInMemory = false;
//		private QueueProcessingType tasksProcessingType = DEFAULT_TASK_PROCESSING_TYPE;

		private int memoryCacheSize = 0;
		private long diskCacheSize = 0;
		private int diskCacheFileCount = 0;

		private MemoryCache memoryCache = null;
		private DiscCache diskCache = null;
//		private FileNameGenerator diskCacheFileNameGenerator = null;
//		private ImageDownloader downloader = null;
		private ImageDecoder decoder;
		private DisplayImageOptions defaultDisplayImageOptions = null;

		private boolean writeLogs = false;
	}
}
