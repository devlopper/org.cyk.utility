package org.cyk.utility.__kernel__.runnable;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;

public interface RunnableHelper {
	
	static ExecutorService instantiateExecutorService(Integer corePoolSize,Integer maximumPoolSize,Long keepAliveTime,TimeUnit keepAliveTimeUnit
			,BlockingQueue<Runnable> queue,Integer queueSize,ThreadFactory threadFactory,RejectedExecutionHandler rejectedExecutionHandler) {
		corePoolSize = ValueHelper.defaultToIfBlank(corePoolSize, 2);
		maximumPoolSize = ValueHelper.defaultToIfBlank(maximumPoolSize, corePoolSize * 3);
		keepAliveTime = ValueHelper.defaultToIfBlank(keepAliveTime, 1l);
		keepAliveTimeUnit = ValueHelper.defaultToIfBlank(keepAliveTimeUnit, TimeUnit.SECONDS);
		queueSize = ValueHelper.defaultToIfBlank(queueSize, maximumPoolSize * 5);
		threadFactory = ValueHelper.defaultToIfBlank(threadFactory, Executors.defaultThreadFactory());
		corePoolSize = ValueHelper.defaultToIfBlank(corePoolSize, 2);
		//for optimisation we create object only when needed
		if(queue == null)
			queue = new ArrayBlockingQueue<Runnable>(queueSize);
		if(rejectedExecutionHandler == null)
			rejectedExecutionHandler = new ThreadPoolExecutor.AbortPolicy();
		return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, keepAliveTimeUnit, queue, threadFactory, rejectedExecutionHandler);
	}
	
	static ExecutorService instantiateExecutorService(Integer corePoolSize) {
		return instantiateExecutorService(corePoolSize, null, null, null, null, null, null, null);
	}
	
	static ExecutorService instantiateExecutorService() {
		return instantiateExecutorService(3, null, null, null, null, null, null, null);
	}
	
	static void run(Collection<Runnable> runnables,String name,ExecutorService executorService,Long timeOut,TimeUnit timeOutUnit) {
		if(CollectionHelper.isEmpty(runnables))
			return;
		ValueHelper.throwIfBlank("runnables name", name);
		if(executorService == null)
			executorService = instantiateExecutorService(runnables.size() / 4 + 1);
		for(Runnable index : runnables)
			executorService.submit(index);
		executorService.shutdown();
		timeOut = ValueHelper.defaultToIfNull(timeOut, 60l * 2);
		timeOutUnit = ValueHelper.defaultToIfNull(timeOutUnit, TimeUnit.SECONDS);
		try {
			if(executorService.awaitTermination(timeOut, timeOutUnit))
				;
			else
				throw new RuntimeException(name+" : runnables executor service time out!!!");
		} catch (InterruptedException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	static void run(Collection<Runnable> runnables,String name,ExecutorService executorService) {
		run(runnables, name, executorService, null, null);
	}
	
	static void run(Collection<Runnable> runnables,String name) {
		run(runnables, name, null, null, null);
	}
	
}
