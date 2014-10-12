package com.cache.memory;

import java.lang.ref.Reference;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Service;
import android.graphics.Bitmap;


public abstract class BaseMemoryCache implements MemoryCache{

	/** Stores not strong references to objects */
	private final Map<String, Reference<Bitmap>> softMap = Collections.synchronizedMap(new HashMap<String, Reference<Bitmap>>());

	
	@Override
	public boolean put(String key, Bitmap value) {
		Reference<Bitmap> reference=createReference(value);
		softMap.put(key, reference);
		return true;
	}

	@Override
	public Bitmap get(String key) {
		Bitmap result=null;
		Reference<Bitmap> reference=softMap.get(key); 
		if(reference!=null){
			result=reference.get();
		}
		return result;
	}

	@Override
	public Bitmap remove(String key) {
		Reference<Bitmap> bmpRef=softMap.remove(key);
		return (bmpRef==null)?null:bmpRef.get();
	}

	@Override
	public Collection<String> keys() {
		synchronized (softMap) {
			return softMap.keySet();
		}
	}

	@Override
	public void clear() {
		softMap.clear();
	}

	protected abstract Reference<Bitmap> createReference(Bitmap value);
}
