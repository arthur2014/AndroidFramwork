package com.net;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;


public interface ImageAware {

	int getWidth();
	
	int getHeight();
	
	ViewScaleType getScaleType();
	
	View getWrappedView();
	
	boolean isCollected();
	
	int getId();
	
	boolean setImageDrawable(Drawable drawable);
	
	boolean setImageBitmap(Bitmap bitmap);
	
	public enum ViewScaleType{
		FIT_INSIDE,
		
		CROP;
		
		public static ViewScaleType fromImageView(ImageView imageView){
			switch (imageView.getScaleType()) {
			case FIT_XY:
			case FIT_CENTER:
			case FIT_START:
			case FIT_END:
			case CENTER_INSIDE:
				return FIT_INSIDE;
			case CENTER:
			case MATRIX:
			case CENTER_CROP:
			default:
				return CROP;
			}
		}
	}
}
