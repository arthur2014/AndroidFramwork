package com.net;

import java.util.concurrent.Executor;

import com.net.task.Task;

public class ImageLoaderEngine {

	private ImageLoaderConfiguration configuration;
	
	private Executor taskExecutor;
	
	private ThreadManager threadManager;
	
	public ImageLoaderEngine(ImageLoaderConfiguration config){
		this.configuration=config;
		taskExecutor=config.taskExecutor;
		threadManager=ThreadManager.getInstance();
		
	}
	
	
	void submit(Task task){
		threadManager.execute(task);
	}
}
