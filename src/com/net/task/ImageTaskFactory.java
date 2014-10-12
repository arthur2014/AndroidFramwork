package com.net.task;

import com.net.ImageAware;
import com.net.ImageLoaderConfiguration;


public class ImageTaskFactory  {

public static ImageTask get(String imageUrl, ImageAware imageView,ImageLoaderConfiguration configure){
	
	return new ImageTask(imageUrl,imageView,configure);
}


}
