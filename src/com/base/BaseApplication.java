package com.base;

import android.app.Application;
import android.os.Handler;
import android.os.Message;

public class BaseApplication  extends Application{

	private Handler mHandler;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		mHandler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
			}
			
		};
		
		
	}

	public Handler getHandler(){
		return this.mHandler;
	}

	
}
