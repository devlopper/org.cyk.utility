package org.cyk.utility.runnable;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.cyk.utility.collection.CollectionHelperImpl;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.throwable.ThrowableHelperImpl;
import org.cyk.utility.value.ValueHelperImpl;

public class RunnableHelperImpl extends AbstractHelper implements RunnableHelper,Serializable {
	private static final long serialVersionUID = 1L;

	public static ExecutorService __instanciateExecutorService__(Integer corePoolSize,Integer maximumPoolSize,Long keepAliveTime,TimeUnit keepAliveTimeUnit
			,BlockingQueue<Runnable> queue,Integer queueSize,ThreadFactory threadFactory,RejectedExecutionHandler rejectedExecutionHandler) {
		corePoolSize = ValueHelperImpl.__defaultToIfBlank__(corePoolSize, 2);
		maximumPoolSize = ValueHelperImpl.__defaultToIfBlank__(maximumPoolSize, corePoolSize * 3);
		keepAliveTime = ValueHelperImpl.__defaultToIfBlank__(keepAliveTime, 1l);
		keepAliveTimeUnit = ValueHelperImpl.__defaultToIfBlank__(keepAliveTimeUnit, TimeUnit.SECONDS);
		queueSize = ValueHelperImpl.__defaultToIfBlank__(queueSize, maximumPoolSize * 5);
		threadFactory = ValueHelperImpl.__defaultToIfBlank__(threadFactory, Executors.defaultThreadFactory());
		corePoolSize = ValueHelperImpl.__defaultToIfBlank__(corePoolSize, 2);
		//for optimisation we create object only when needed
		if(queue == null)
			queue = new ArrayBlockingQueue<Runnable>(queueSize);
		if(rejectedExecutionHandler == null)
			rejectedExecutionHandler = new ThreadPoolExecutor.AbortPolicy();
		return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, keepAliveTimeUnit, queue, threadFactory, rejectedExecutionHandler);
	}
	
	public static ExecutorService __instanciateExecutorService__(Integer corePoolSize) {
		return __instanciateExecutorService__(corePoolSize, null, null, null, null, null, null, null);
	}
	
	public static ExecutorService __instanciateExecutorService__() {
		return __instanciateExecutorService__(3, null, null, null, null, null, null, null);
	}
	
	public static void __run__(Collection<Runnable> runnables,String name,ExecutorService executorService,Long timeOut,TimeUnit timeOutUnit) {
		if(CollectionHelperImpl.__isEmpty__(runnables))
			return;
		ValueHelperImpl.__throwIfBlank__("runnables name", name);
		if(executorService == null)
			executorService = __instanciateExecutorService__(runnables.size() / 4 + 1);
		for(Runnable index : runnables)
			executorService.submit(index);
		executorService.shutdown();
		timeOut = ValueHelperImpl.__defaultToIfNull__(timeOut, 60l * 2);
		timeOutUnit = ValueHelperImpl.__defaultToIfNull__(timeOutUnit, TimeUnit.SECONDS);
		try {
			if(executorService.awaitTermination(timeOut, timeOutUnit))
				;
			else
				ThrowableHelperImpl.__throwRuntimeException__(name+" : runnables executor service time out!!!");
		} catch (InterruptedException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public static void __run__(Collection<Runnable> runnables,String name,ExecutorService executorService) {
		__run__(runnables, name, executorService, null, null);
	}
	
	public static void __run__(Collection<Runnable> runnables,String name) {
		__run__(runnables, name, null, null, null);
	}
	
}
