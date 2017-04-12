package org.cyk.utility.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.cyk.utility.common.cdi.BeanAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadPoolExecutor extends java.util.concurrent.ThreadPoolExecutor implements Serializable {

	private static final long serialVersionUID = -3776387708622454944L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolExecutor.class);
	
	@Getter @Setter private Boolean autoShutdown = Boolean.TRUE;
	@Getter private Long numberOfPlannedTask = 0l,numberOfExecutedTask = 0l,timeout=0l;
	@Getter private Collection<ExecutedTask> executedTasks;
	@Getter private Collection<Class<?>> throwableClasses;
	private Collection<Listener> listeners;
	
	private Long keepAliveTime;
	private TimeUnit unit,timeoutUnit;
	private Integer workQueueInitialSize;
	
	public ThreadPoolExecutor(Integer corePoolSize, Integer maximumPoolSize,Long keepAliveTime, TimeUnit unit, BlockingQueue<java.lang.Runnable> workQueue,Long timeout
			,TimeUnit timeoutUnit,Collection<Class<?>> throwableClasses) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		this.keepAliveTime = keepAliveTime;
		this.unit = unit;
		workQueueInitialSize = workQueue.size() + workQueue.remainingCapacity();
		this.timeout = timeout;
		this.timeoutUnit = timeoutUnit;
		this.throwableClasses = throwableClasses;
	}
	
	public ThreadPoolExecutor(Integer corePoolSize, Integer maximumPoolSize,Long keepAliveTime, TimeUnit unit,Integer workQueueSize,Long timeOut,TimeUnit timeOutUnit
			,Collection<Class<?>> throwableClasses) {
		this(corePoolSize, maximumPoolSize, keepAliveTime, unit, new ArrayBlockingQueue<Runnable>(workQueueSize),timeOut,timeOutUnit,throwableClasses);
	}
	
	public void addListener(Listener listener){
		if(listener==null)
			return;
		if(this.listeners==null)
			this.listeners = new ArrayList<>();
		this.listeners.add(listener);
	}
	
	public Long getNumberOfExecutedWhereThrowableIsNotNull(){
		return executedTasks==null ? 0l : executedTasks.size();
	}
	
	private String getName(Runnable runnable){
		return runnable instanceof Thread ? ((Thread)runnable).getName() : runnable.toString();
	}

	@Override
	public void execute(Runnable runnable) {
		super.execute(runnable);
		LogMessage.Builder logMessageBuilder = new LogMessage.Builder("Execute", getName(runnable));
		synchronized (this) {
			numberOfPlannedTask++;
		}
		logMessageBuilder.addParameters("#task planned",numberOfPlannedTask,"#task executed",numberOfExecutedTask);
		LogMessage logMessage = logMessageBuilder.build();
		LOGGER.trace(logMessage.getTemplate(),logMessage.getArgumentsArray());
	}
	
	public void execute(Collection<? extends Runnable> runnables) {
		for(Runnable runnable : runnables)
			execute(runnable);
	}
	
	protected void afterExecute(Runnable runnable, final Throwable throwable) {
		super.afterExecute(runnable, throwable);
		LogMessage.Builder logMessageBuilder = new LogMessage.Builder("Executed", getName(runnable));
		synchronized (this) {
			numberOfExecutedTask++;
			if(throwable==null){
				
			}else{
				if(executedTasks==null)
					executedTasks = new ArrayList<>();
				executedTasks.add(new ExecutedTask(runnable, ListenerUtils.getInstance().getValue(Throwable.class, listeners
						, new ListenerUtils.ResultMethod<Listener, Throwable>(){

							@Override
							public Throwable execute(Listener listener) {
								return listener.getThrowable(throwable);
							}

							@Override
							public Throwable getNullValue() {
								return null;
							}})));
			}
		}
		
		Long remaining = numberOfPlannedTask - numberOfExecutedTask;
		logMessageBuilder.addParameters("#task executed",numberOfExecutedTask,"remaining",remaining);
		Boolean shutdown = remaining == 0 && !isShutdown();
		logMessageBuilder.addParameters("shutdown",shutdown);
		if(throwable!=null)
			logMessageBuilder.addParameters("throwable",throwable.getMessage());
		LogMessage logMessage = logMessageBuilder.build();
		LOGGER.trace(logMessage.getTemplate(),logMessage.getArgumentsArray());
		if(Boolean.TRUE.equals(shutdown)){
			shutdownNow();
		}
	}
	
	public void waitTermination(long timeout,TimeUnit unit){
		LogMessage.Builder logMessageBuilder = new LogMessage.Builder("Wait for", "termination");
		logMessageBuilder.addParameters("#task planned",numberOfPlannedTask,"#task executed",numberOfExecutedTask,"throwables",throwableClasses);
		try {
			if(numberOfPlannedTask-numberOfExecutedTask>0)
				awaitTermination(timeout, unit);
		} catch (InterruptedException e) {
			LOGGER.error("Thread pool executor fail while awaiting termination",e);
		}
		logMessageBuilder.addParameters("has throwable",hasExecutedWhereThrowableInstanceOfOne(throwableClasses),"executed tasks",executedTasks);
		LogMessage logMessage = logMessageBuilder.build();
		LOGGER.trace(logMessage.getTemplate(),logMessage.getArgumentsArray());
	}
	
	public void waitTermination(){
		waitTermination(timeout, timeoutUnit);
	}
	
	public Collection<Runnable> getExecutedWhereThrowableInstanceOfOne(Collection<Class<?>> classes){
		Collection<Runnable> runnables = new ArrayList<>();
		for(ExecutedTask executedTask : executedTasks)
			if(executedTask.isThrowableInstanceOfOneAtLeast(classes))
				runnables.add(executedTask.getRunnable());
		return runnables;
	}
	
	public Boolean hasExecutedWhereThrowableInstanceOfOne(Collection<Class<?>> classes){
		if(classes==null)
			;
		else if(executedTasks!=null)
			for(ExecutedTask executedTask : executedTasks)
				if(executedTask.isThrowableInstanceOfOneAtLeast(classes))
					return Boolean.TRUE;
		return Boolean.FALSE;
	}
	/*
	public void executeWhereThrowableInstanceOfOne(Collection<Class<?>> classes){
		execute(getExecutedWhereThrowableInstanceOfOne(classes));
	}
	
	public void executeWhereThrowableInstanceOfOne(Class<?>...classes){
		executeWhereThrowableInstanceOfOne(Arrays.asList(classes));
	}
	*/
	public static ThreadPoolExecutor createExecutorWhereThrowableInstanceOfOne(ThreadPoolExecutor threadPoolExecutor,Collection<Class<?>> classes){
		ThreadPoolExecutor executor = new ThreadPoolExecutor(threadPoolExecutor.getCorePoolSize(), threadPoolExecutor.getMaximumPoolSize()
				, threadPoolExecutor.keepAliveTime, threadPoolExecutor.unit, threadPoolExecutor.workQueueInitialSize,threadPoolExecutor.timeout
				,threadPoolExecutor.timeoutUnit,threadPoolExecutor.throwableClasses);
		executor.listeners = threadPoolExecutor.listeners;
		executor.execute(threadPoolExecutor.getExecutedWhereThrowableInstanceOfOne(classes));
		return executor;
	}
	
	public static ThreadPoolExecutor createExecutorWhereThrowableInstanceOfOne(ThreadPoolExecutor threadPoolExecutor,Class<?>...classes){
		return createExecutorWhereThrowableInstanceOfOne(threadPoolExecutor,Arrays.asList(classes));
	}
	
	public static void execute(ThreadPoolExecutor executor,Long numberOfReexecution,Long numberOfMillisecondBeforeReexecuting){
		LOGGER.trace("run executor. #reexecution={} , #millisecond before reexecuting={}",numberOfReexecution
				,numberOfMillisecondBeforeReexecuting);
		Long executionCount = 0l;
		executor.waitTermination();
		
		while(++executionCount<numberOfReexecution && executor.hasExecutedWhereThrowableInstanceOfOne(executor.getThrowableClasses())){
			LOGGER.trace("Waiting "+numberOfMillisecondBeforeReexecuting+" ms before re-executing where throwable is not null");
			CommonUtils.getInstance().pause(numberOfMillisecondBeforeReexecuting);
			LOGGER.trace("#execution={},Re-executing where throwable is not null",executionCount);
			executor = ThreadPoolExecutor.createExecutorWhereThrowableInstanceOfOne(executor,executor.getThrowableClasses());
			executor.waitTermination();
		}
	}

	/**/
	
	@Getter @Setter
	private static class ExecutedTask implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private Runnable runnable;
		private Throwable throwable;
		
		public ExecutedTask(Runnable runnable, Throwable throwable) {
			super();
			this.runnable = runnable;
			this.throwable = throwable;
		}
		
		public Boolean isThrowableInstanceOfOneAtLeast(Collection<Class<?>> classes){
			for(Class<?> aClass : classes)
				if(throwable!=null && (aClass.equals(throwable.getClass()) || aClass.isAssignableFrom(throwable.getClass())))
					return Boolean.TRUE;
			return Boolean.FALSE;
		}
		
		@Override
		public String toString() {
			return runnable+"/"+throwable;
		}
		
	}
	
	/**/
	
	public static interface Listener {
		
		/**/
		
		void setAutoShutdown(Boolean autoShutdown);
		Boolean getAutoShutdown();
		
		void setNumberOfPlannedTask(Long numberOfPlannedTask);
		Long getNumberOfPlannedTask();
		
		void setNumberOfExecutedTask(Long numberOfExecutedTask);
		Long getNumberOfExecutedTask();
		
		void setTimeout(Long timeout);
		Long getTimeout();
		
		Collection<ExecutedTask> getExecutedTasks();
		
		void setThrowableClasses(Collection<Class<?>> collection);
		Collection<Class<?>> getThrowableClasses();
		
		void setListeners(Collection<Listener> listeners);
		Collection<Listener> getListeners();
		
		void setKeepAliveTime(Long keepAliveTime);
		Long getKeepAliveTime();
		
		void setKeepAliveTimeUnit(TimeUnit keepAliveTimeUnit);
		TimeUnit getKeepAliveTimeUnit();
		
		void setTimeoutUnit(TimeUnit timeoutUnit);
		TimeUnit getTimeoutUnit();
		
		Throwable getThrowable(Throwable throwable);
		
		@Getter @Setter
		public static class Adapter extends BeanAdapter implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			private Boolean autoShutdown = Boolean.TRUE;
			private Long numberOfPlannedTask = 0l,numberOfExecutedTask = 0l,timeout=0l;
			private Collection<ExecutedTask> executedTasks;
			private Collection<Class<?>> throwableClasses;
			private Collection<Listener> listeners;
			
			private Long keepAliveTime;
			private TimeUnit keepAliveTimeUnit,timeoutUnit;
			private Integer workQueueInitialSize;
			
			@Override
			public Throwable getThrowable(Throwable throwable) {
				return null;
			}
			
			/**/
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				public Throwable getThrowable(Throwable throwable) {
					return throwable;
				}
			}
		}
	}

	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Builder extends AbstractBuilder<ThreadPoolExecutor> implements Serializable {
		private static final long serialVersionUID = 1L;

		private Integer corePoolSize,maximumPoolSize,workQueueSize;
		private Long keepAliveTime,timeOut;
		private TimeUnit keepAliveTimeUnit,timeOutUnit;
		private Collection<Class<?>> throwableClasses;
		
		@Override
		public ThreadPoolExecutor build() {
			corePoolSize = getValueIfNotNullElseDefault(Integer.class, corePoolSize, 1);
			maximumPoolSize = getValueIfNotNullElseDefault(Integer.class, maximumPoolSize, corePoolSize);
			workQueueSize = getValueIfNotNullElseDefault(Integer.class, workQueueSize, maximumPoolSize * 2);
			keepAliveTime = getValueIfNotNullElseDefault(Long.class, keepAliveTime, 1l);
			keepAliveTimeUnit = getValueIfNotNullElseDefault(TimeUnit.class, keepAliveTimeUnit, TimeUnit.SECONDS);
			timeOut = getValueIfNotNullElseDefault(Long.class, timeOut, 1l);
			timeOutUnit = getValueIfNotNullElseDefault(TimeUnit.class, timeOutUnit, TimeUnit.SECONDS);
			
			ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, keepAliveTimeUnit, workQueueSize, timeOut
					, timeOutUnit, throwableClasses);
			return threadPoolExecutor;
		}
		
	}
}
