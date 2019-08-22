package org.cyk.utility.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface ExecutorServiceBuilder extends FunctionWithPropertiesAsInput<ExecutorService> {

	Integer getCorePoolSize();
	ExecutorServiceBuilder setCorePoolSize(Integer corePoolSize);
	
	Integer getMaximumPoolSize();
	ExecutorServiceBuilder setMaximumPoolSize(Integer maximumPoolSize);
	
	Long getKeepAliveTime();
	ExecutorServiceBuilder setKeepAliveTime(Long keepAliveTime);
	
	TimeUnit getKeepAliveTimeUnit();
	ExecutorServiceBuilder setKeepAliveTimeUnit(TimeUnit keepAliveTimeUnit);
	
	BlockingQueue<Runnable> getQueue();
	ExecutorServiceBuilder setQueue(BlockingQueue<Runnable> queue);
	
	Integer getQueueSize();
	ExecutorServiceBuilder setQueueSize(Integer queueSize);
	
	ThreadFactory getThreadFactory();
	ExecutorServiceBuilder setThreadFactory(ThreadFactory threadFactory);
	
	RejectedExecutionHandler getRejectedExecutionHandler();
	ExecutorServiceBuilder setRejectedExecutionHandler(RejectedExecutionHandler rejectedExecutionHandler);
	
	
}
