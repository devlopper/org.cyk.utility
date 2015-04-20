package org.cyk.utility.common;

import java.io.Serializable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Getter @Setter @Log
public class ThreadPoolExecutor extends java.util.concurrent.ThreadPoolExecutor implements Serializable {

	private static final long serialVersionUID = -3776387708622454944L;

	private Boolean autoShutdown = Boolean.TRUE;
	
	public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize,long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}
	
	protected void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t);
		if(getActiveCount()==1 && Boolean.TRUE.equals(autoShutdown))
			shutdownNow();
	}
	
	public void waitTermination(long timeout,TimeUnit unit){
		try {
			awaitTermination(timeout, unit);
		} catch (InterruptedException e) {
			log.log(Level.SEVERE,e.toString(),e);
		}
	}

}
