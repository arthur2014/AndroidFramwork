package com.cache.memory.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import android.graphics.Bitmap;

import com.cache.memory.MemoryCache;

/**
 * 
 * @author Administrator
 *
 */
public class LruMemoryCache implements MemoryCache{

	private final LinkedHashMap<String, Bitmap> map;
	private final int maxSize;
	/** Size of this cache in bytes */
	private int size;
	private static LruMemoryCache memoryCache;
	private static int default_memory_cache_size=10;
	private  LruMemoryCache(int maxSize){
		if(maxSize<0)
			throw new IllegalArgumentException("maxSize < 0");
		this.maxSize=maxSize;
		this.map=new LinkedHashMap<String, Bitmap>(0,0.75f,true);
	}
	public static LruMemoryCache getInstance(){
		if(memoryCache==null){
			memoryCache=new LruMemoryCache(default_memory_cache_size);
		}
		return memoryCache;
	}
	@Override
	public final boolean put(String key, Bitmap value) {
		if(key==null || value==null)
			throw new NullPointerException("key==null || value==null");
		synchronized (this) {
			size+=sizeOf(key, value);
			Bitmap previous=map.put(key, value);
			if(previous!=null){
				size-=sizeOf(key, value);
			}
		}
		trimToSize(maxSize);
		return true;
	}

	@Override
	public final Bitmap get(String key) {
		if(key==null)
			throw new NullPointerException("key==null");
		synchronized (this) {
			return map.get(key);
		}
	}

	@Override
	public Bitmap remove(String key) {
		if(key==null)
			throw new NullPointerException("key==null");
		synchronized(this){
			Bitmap pre=map.remove(key);
			if(pre!=null){
				size-=sizeOf(key, pre);
			}
			return pre;
		}
	}

	@Override
	public Collection<String> keys() {
		synchronized(this){
			return new HashSet<String>(map.keySet());
		}
	}

	@Override
	public void clear() {
		synchronized(this){
			trimToSize(-1);
		}
	}

	/**
	 * Returns the size {@code Bitmap} in bytes.
	 * <p/>
	 * An entry's size must not change while it is in the cache.
	 */
	private int sizeOf(String key, Bitmap value) {
		return value.getRowBytes() * value.getHeight();
	}
	
	/**
	 * Remove the eldest entries until the total of remaining entries is at or below the requested size.
	 *
	 * @param maxSize the maximum size of the cache before returning. May be -1 to evict even 0-sized elements.
	 */
	private void trimToSize(int maxSize) {
		while (true) {
			String key;
			Bitmap value;
			synchronized (this) {
				if (size < 0 || (map.isEmpty() && size != 0)) {
					throw new IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
				}

				if (size <= maxSize || map.isEmpty()) {
					break;
				}

				Map.Entry<String, Bitmap> toEvict = map.entrySet().iterator().next();
				if (toEvict == null) {
					break;
				}
				key = toEvict.getKey();
				value = toEvict.getValue();
				map.remove(key);
				size -= sizeOf(key, value);
			}
		}
	}

}