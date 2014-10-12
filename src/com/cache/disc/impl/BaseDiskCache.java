package com.cache.disc.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.graphics.Bitmap;

import com.cache.disc.DiscCache;
import com.util.IoUtil;
import com.util.IoUtil.CopyListener;
import com.util.MD5NameGenerator;
import com.util.NameGenerator;

public class BaseDiskCache implements DiscCache{
	public static final int DEFAULT_BUFFER_SIZE = 2 * 1024 * 1024; // 2 Mb
	/** {@value */
	public static final Bitmap.CompressFormat DEFAULT_COMPRESS_FORMAT = Bitmap.CompressFormat.PNG;
	/** {@value */
	public static final int DEFAULT_COMPRESS_QUALITY = 100;

	private static final String ERROR_ARG_NULL = " argument must be not null";
	private static final String TEMP_IMAGE_POSTFIX = ".tmp";
	protected final File cacheDir;
	protected final File reserveCacheDir;

	protected final NameGenerator nameGenerator;

	protected int bufferSize = DEFAULT_BUFFER_SIZE;

	protected Bitmap.CompressFormat compressFormat = DEFAULT_COMPRESS_FORMAT;
	protected int compressQuality = DEFAULT_COMPRESS_QUALITY;
	

	/** @param cacheDir Directory for file caching */
	public BaseDiskCache(File cacheDir) {
		this(cacheDir, null);
	}

	/**
	 * @param cacheDir        Directory for file caching
	 * @param reserveCacheDir null-ok; Reserve directory for file caching. It's used when the primary directory isn't available.
	 */
	public BaseDiskCache(File cacheDir, File reserveCacheDir) {
		this(cacheDir, reserveCacheDir, new MD5NameGenerator());
	}

	/**
	 * @param cacheDir          Directory for file caching
	 * @param reserveCacheDir   null-ok; Reserve directory for file caching. It's used when the primary directory isn't available.
	 * @param fileNameGenerator {@linkplain com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator
	 *                          Name generator} for cached files
	 */
	public BaseDiskCache(File cacheDir, File reserveCacheDir, NameGenerator NameGenerator) {
		if (cacheDir == null) {
			throw new IllegalArgumentException("cacheDir" + ERROR_ARG_NULL);
		}
		if (NameGenerator == null) {
			throw new IllegalArgumentException("NameGenerator" + ERROR_ARG_NULL);
		}

		this.cacheDir = cacheDir;
		this.reserveCacheDir = reserveCacheDir;
		this.nameGenerator = NameGenerator;
	}

	@Override
	public File getDirectory() {
		return cacheDir;
	}

	@Override
	public File get(String imageUri) {
		return getFile(imageUri);
	}

	@Override
	public boolean save(String imageUri, InputStream imageStream,
			CopyListener listener) throws IOException {
		File imageFile = getFile(imageUri);
		File tmpFile = new File(imageFile.getAbsolutePath() + TEMP_IMAGE_POSTFIX);
		boolean loaded = false;
		try {
			OutputStream os = new BufferedOutputStream(new FileOutputStream(tmpFile), bufferSize);
			try {
				loaded = IoUtil.copyStream(imageStream, os, listener, bufferSize);
			} finally {
				IoUtil.closeSilently(os);
			}
		} finally {
			if (loaded && !tmpFile.renameTo(imageFile)) {
				loaded = false;
			}
			if (!loaded) {
				tmpFile.delete();
			}
		}
		return loaded;
	}

	@Override
	public boolean save(String imageUri, Bitmap bitmap) throws IOException {
		File imageFile = getFile(imageUri);
		File tmpFile = new File(imageFile.getAbsolutePath() + TEMP_IMAGE_POSTFIX);
		OutputStream os = new BufferedOutputStream(new FileOutputStream(tmpFile), bufferSize);
		boolean savedSuccessfully = false;
		try {
			savedSuccessfully = bitmap.compress(compressFormat, compressQuality, os);
		} finally {
			IoUtil.closeSilently(os);
			if (savedSuccessfully && !tmpFile.renameTo(imageFile)) {
				savedSuccessfully = false;
			}
			if (!savedSuccessfully) {
				tmpFile.delete();
			}
		}
		bitmap.recycle();
		return savedSuccessfully;
	}

	@Override
	public boolean remove(String imageUri) {
		return getFile(imageUri).delete();
	}

	@Override
	public void close() {
		
	}

	@Override
	public void clear() {
		File[] files=cacheDir.listFiles();
		if(files!=null){
			for(File f:files){
				f.delete();
			}
		}
	}
	/** Returns file object (not null) for incoming image URI. File object can reference to non-existing file. */
	protected File getFile(String imageUri) {
		String fileName = nameGenerator.generate(imageUri);
		File dir = cacheDir;
		if (!cacheDir.exists() && !cacheDir.mkdirs()) {
			if (reserveCacheDir != null && (reserveCacheDir.exists() || reserveCacheDir.mkdirs())) {
				dir = reserveCacheDir;
			}
		}
		return new File(dir, fileName);
	}
}