package org.cyk.utility.common;

import java.io.Serializable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import lombok.Getter;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter @Setter
public class ThreadPoolExecutor extends java.util.concurrent.ThreadPoolExecutor implements Serializable {

	private static final long serialVersionUID = -3776387708622454944L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolExecutor.class);
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
			LOGGER.error("Thread pool executor fail while awaiting termination",e);
		}
	}

}
