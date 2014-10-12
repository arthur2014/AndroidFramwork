package com.data;

import android.graphics.Bitmap;

public interface Loader {

	public Bitmap load(String imageUrl,ImageLoadingListener listener);
	
	public Bitmap syncLoad(String imageUrl);
}
