package org.cyk.utility.thread;

import java.io.Serializable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.function.Function;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

@Dependent @Deprecated
public class ExecutorServiceBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<ExecutorService> implements ExecutorServiceBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private Integer corePoolSize,maximumPoolSize,queueSize;
	private Long keepAliveTime;
	private TimeUnit keepAliveTimeUnit;
	private BlockingQueue<Runnable> queue;
	private ThreadFactory threadFactory;
	private RejectedExecutionHandler rejectedExecutionHandler;
	
	@Override
	public Function<Properties, ExecutorService> execute() {
		Integer corePoolSize = getCorePoolSize();
		if(corePoolSize == null)
			corePoolSize = 2;
		Integer maximumPoolSize = getMaximumPoolSize();
		if(maximumPoolSize == null)
			maximumPoolSize = corePoolSize * 3;
		Long keepAliveTime = getKeepAliveTime();
		if(keepAliveTime == null)
			keepAliveTime = 1l;
		TimeUnit keepAliveTimeUnit = getKeepAliveTimeUnit();
		if(keepAliveTimeUnit == null)
			keepAliveTimeUnit = TimeUnit.SECONDS;
		BlockingQueue<Runnable> queue = getQueue();
		if(queue == null) {
			if(queueSize == null)
				queueSize = maximumPoolSize * 5;
			queue = new ArrayBlockingQueue<Runnable>(queueSize);
		}
		ThreadFactory threadFactory = getThreadFactory();
		if(threadFactory == null)
			threadFactory = Executors.defaultThreadFactory();
		RejectedExecutionHandler rejectedExecutionHandler = getRejectedExecutionHandler();
		if(rejectedExecutionHandler == null)
			rejectedExecutionHandler = new ThreadPoolExecutor.AbortPolicy();
		ExecutorService executorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, keepAliveTimeUnit, queue, threadFactory, rejectedExecutionHandler);
		setProperty(Properties.OUTPUT, executorService);
		return this;
	}

	@Override
	public Integer getCorePoolSize() {
		return corePoolSize;
	}

	@Override
	public ExecutorServiceBuilder setCorePoolSize(Integer corePoolSize) {
		this.corePoolSize = corePoolSize;
		return this;
	}

	@Override
	public Integer getMaximumPoolSize() {
		return maximumPoolSize;
	}

	@Override
	public ExecutorServiceBuilder setMaximumPoolSize(Integer maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
		return this;
	}

	@Override
	public Long getKeepAliveTime() {
		return keepAliveTime;
	}

	@Override
	public ExecutorServiceBuilder setKeepAliveTime(Long keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
		return this;
	}

	@Override
	public TimeUnit getKeepAliveTimeUnit() {
		return keepAliveTimeUnit;
	}

	@Override
	public ExecutorServiceBuilder setKeepAliveTimeUnit(TimeUnit keepAliveTimeUnit) {
		this.keepAliveTimeUnit = keepAliveTimeUnit;
		return this;
	}

	@Override
	public BlockingQueue<Runnable> getQueue() {
		return queue;
	}

	@Override
	public ExecutorServiceBuilder setQueue(BlockingQueue<Runnable> queue) {
		this.queue = queue;
		return this;
	}
	
	@Override
	public Integer getQueueSize() {
		return queueSize;
	}

	@Override
	public ExecutorServiceBuilder setQueueSize(Integer queueSize) {
		this.queueSize = queueSize;
		return this;
	}

	@Override
	public ThreadFactory getThreadFactory() {
		return threadFactory;
	}

	@Override
	public ExecutorServiceBuilder setThreadFactory(ThreadFactory threadFactory) {
		this.threadFactory = threadFactory;
		return this;
	}

	@Override
	public RejectedExecutionHandler getRejectedExecutionHandler() {
		return rejectedExecutionHandler;
	}

	@Override
	public ExecutorServiceBuilder setRejectedExecutionHandler(RejectedExecutionHandler rejectedExecutionHandler) {
		this.rejectedExecutionHandler = rejectedExecutionHandler;
		return this;
	}
	
}
