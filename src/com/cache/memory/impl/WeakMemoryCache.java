package com.cache.memory.impl;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Collection;

import com.cache.memory.BaseMemoryCache;
import com.cache.memory.MemoryCache;

import android.graphics.Bitmap;

/**
 * 
 * @author Administrator
 *
 */
public class WeakMemoryCache extends BaseMemoryCache{

	@Override
	protected Reference<Bitmap> createReference(Bitmap value) {
		return new WeakReference<Bitmap>(value);
	}


}