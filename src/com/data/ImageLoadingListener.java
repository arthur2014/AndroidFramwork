package com.data;

public interface ImageLoadingListener {
	public void onLoadingStart();
	public void onLoadingFailed();
	public void onProcessUpdate(int current,int total);
	public void onLoadingCompleted();
}
