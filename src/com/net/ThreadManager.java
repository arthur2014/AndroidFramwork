package com.net;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.net.task.Task;

import android.os.Handler;
import android.os.Message;

/**
 * 
 * @author Administrator
 * 
 */
public class ThreadManager {
	private ThreadPoolExecutor threadPool;

	private final int corePoolSize = 5;
	private final int maximumPoolSize = corePoolSize + 2;
	private final int keepAliveTime = 5;
	private final TimeUnit unit = TimeUnit.SECONDS;
	private final int workQueueCapacity = 5;
	private ArrayBlockingQueue<Runnable> workQueue;
	
	
	private static ThreadManager threadManager;
	private ThreadManager(){
		initThreadPool();
	}

	public static ThreadManager getInstance(){
		if(threadManager==null){
			threadManager=new ThreadManager();
		}
		return threadManager;
	}
	
	
	private void initThreadPool() {
		workQueue = new ArrayBlockingQueue<Runnable>(workQueueCapacity);

		threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
				keepAliveTime, unit, workQueue,
				new ThreadPoolExecutor.DiscardOldestPolicy());
		
	}

	public void execute(Task work) {
		threadPool.execute(work);
	}
	
}