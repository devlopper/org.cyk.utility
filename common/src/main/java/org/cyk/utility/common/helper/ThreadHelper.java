package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.cyk.utility.common.Action;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.BeanAdapter;

public class ThreadHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private static ThreadHelper INSTANCE;
	
	public static ThreadHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new ThreadHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	/**/
	
	public static interface Executable extends java.lang.Runnable {
		
		String getName();
		Executable setName(String name);
		
		Throwable getThrowable();
		Executable setThrowable(Throwable throwable);
		
		void __execute__();
		
		@Getter
		public static class Adapter implements Executable,Serializable {
			private static final long serialVersionUID = 1L;
			
			protected Throwable throwable;
			protected String name;
			
			@Override
			public Executable setThrowable(Throwable throwable) {
				return null;
			}
			
			@Override
			public Executable setName(String name) {
				return null;
			}
			
			@Override
			public void __execute__() {}
			
			@Override
			public void run() {
				__execute__();
			}
			
			public static class Default extends Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				public void __execute__() {
					ThrowableHelper.getInstance().throwNotYetImplemented();
				}
				
				@Override
				public Executable setThrowable(Throwable throwable) {
					this.throwable = throwable;
					return this;
				}
				
				@Override
				public Executable setName(String name) {
					this.name = name;
					return this;
				}
			}
		}
	}
	
	public static interface Executor extends Action<Collection<Executable>, Void> {
		
		Collection<Executable> getWhereThrowableInstanceOf();
		Collection<Executable> getWhereThrowableIsNull();
		Collection<Executable> getWhereThrowableIsNotNull();
		
		Long getNumberOfReexecution();
		Executor setNumberOfReexecution(Long numberOfReexecution);
		
		Long getNumberOfMillisecondBeforeReexecuting();
		Executor setNumberOfMillisecondBeforeReexecuting(Long numberOfMillisecondBeforeReexecuting);
		
		Boolean getIsAutomaticallyShutdown();
		Executor setIsAutomaticallyShutdown(Boolean isAutomaticallyShutdown);
		
		Collection<Class<?>> getThrowableClasses();
		Executor setThrowableClasses(Collection<Class<?>> throwableClasses);
		Executor addThrowableClasses(Class<?>...throwableClasses);
		
		Executor addExecutables(Executable...executables);
		
		Duration getMaximumDuration();
		Executor setMaximumDuration(Duration maximumDuration);
		
		Duration getKeepAliveDuration();
		Executor setKeepAliveDuration(Duration keepAliveDuration);
		
		Integer getCorePoolSize();
		Executor setCorePoolSize(Integer corePoolSize);
		
		Integer getMaximumPoolSize();
		Executor setMaximumPoolSize(Integer maximumPoolSize);
		
		Integer getWorkQueueInitialSize();
		Executor setWorkQueueInitialSize(Integer workQueueInitialSize);
		
		@Getter
		public static class Adapter extends Action.Adapter.Default<Collection<Executable>, Void> implements Executor,Serializable {
			private static final long serialVersionUID = 1L;
			
			protected Boolean isAutomaticallyShutdown = Boolean.TRUE;
			protected Collection<Class<?>> throwableClasses;
			protected Duration keepAliveDuration,maximumDuration;
			protected Integer workQueueInitialSize,corePoolSize,maximumPoolSize;
			protected Long numberOfReexecution,numberOfMillisecondBeforeReexecuting;
			
			protected Long numberOfPlannedTask = 0l,numberOfExecutedTask = 0l;
			
			@SuppressWarnings("unchecked")
			public Adapter(Collection<Executable> input) {
				super("execute", (Class<Collection<Executable>>) ClassHelper.getInstance().getByName(Collection.class), input, Void.class);
			}
			
			@Override
			public Collection<Executable> getWhereThrowableIsNull() {
				return null;
			}
			
			@Override
			public Collection<Executable> getWhereThrowableIsNotNull() {
				return null;
			}
			
			@Override
			public Executor addExecutables(Executable...executables) {
				return null;
			}
			
			@Override
			public Executor setCorePoolSize(Integer corePoolSize) {
				return null;
			}
			
			@Override
			public Executor setMaximumPoolSize(Integer maximumPoolSize) {
				return null;
			}
			
			@Override
			public Collection<Executable> getWhereThrowableInstanceOf() {
				return null;
			}
			
			@Override
			public Executor setNumberOfMillisecondBeforeReexecuting(Long numberOfMillisecondBeforeReexecuting) {
				return null;
			}
			
			@Override
			public Executor setNumberOfReexecution(Long numberOfReexecution) {
				return null;
			}
			
			@Override
			public Executor setWorkQueueInitialSize(Integer workQueueInitialSize) {
				return null;
			}
			
			@Override
			public Executor setIsAutomaticallyShutdown(Boolean isAutomaticallyShutdown){
				return null;
			}
			
			@Override
			public Executor addThrowableClasses(Class<?>...throwableClasses) {
				return null;
			}
			
			@Override
			public Executor setThrowableClasses(Collection<Class<?>> throwableClasses) {
				return null;
			}
			
			@Override
			public Executor setMaximumDuration(Duration maximumDuration){
				return null;
			}
			
			@Override
			public Executor setKeepAliveDuration(Duration keepAliveDuration){
				return null;
			}
			
			public static class Default extends Executor.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				protected java.util.concurrent.ThreadPoolExecutor threadPoolExecutor;
				
				public Default(Collection<Executable> input) {
					super(input);
				}
				
				public Default() {
					this(null);
				}
				
				@Override
				protected Void __execute__() {
					Duration keepAliveDuration = getKeepAliveDuration();
					threadPoolExecutor = new java.util.concurrent.ThreadPoolExecutor(getCorePoolSize(), getMaximumPoolSize(), keepAliveDuration.getValue(), keepAliveDuration.getUnit()
							, new ArrayBlockingQueue<Runnable>(getWorkQueueInitialSize()));
					//logTrace("run executor. #reexecution={} , #millisecond before reexecuting={}",numberOfReexecution,numberOfMillisecondBeforeReexecuting);
					//Long executionCount = 0l;
					//executor.waitTermination();
					
					for(Executable executable : getInput()){
						threadPoolExecutor.execute(executable);
						//LogMessage.Builder logMessageBuilder = new LogMessage.Builder("Execute", getName(runnable));
						synchronized (this) {
							numberOfPlannedTask++;
						}
						//logMessageBuilder.addParameters("#task planned",numberOfPlannedTask,"#task executed",numberOfExecutedTask);
						//LogMessage logMessage = logMessageBuilder.build();
						//LOGGER.trace(logMessage.getTemplate(),logMessage.getArgumentsArray());
					}
					
					waitTermination(threadPoolExecutor);
					
					/*
					while(++executionCount<numberOfReexecution && CollectionHelper.getInstance().isNotEmpty(getWhereThrowableInstanceOf())){
						//LOGGER.trace("Waiting "+numberOfMillisecondBeforeReexecuting+" ms before re-executing where throwable is not null");
						CommonUtils.getInstance().pause(numberOfMillisecondBeforeReexecuting);
						//LOGGER.trace("#execution={},Re-executing where throwable is not null",executionCount);
						//executor = ThreadPoolExecutor.createExecutorWhereThrowableInstanceOfOne(executor,executor.getThrowableClasses());
						//executor.waitTermination();
					}
					*/
					return Constant.VOID;
				}
				
				private void waitTermination(java.util.concurrent.ThreadPoolExecutor threadPoolExecutor){
					Duration maximumDuration = getMaximumDuration();
					//LogMessage.Builder logMessageBuilder = new LogMessage.Builder("Wait for", "termination");
					//logMessageBuilder.addParameters("#task planned",numberOfPlannedTask,"#task executed",numberOfExecutedTask,"throwables",throwableClasses);
					try {
						if(numberOfPlannedTask-numberOfExecutedTask>0)
							threadPoolExecutor.awaitTermination(maximumDuration.getValue(), maximumDuration.getUnit());
					} catch (InterruptedException e) {
						//LOGGER.error("Thread pool executor fail while awaiting termination",e);
					}
					//logMessageBuilder.addParameters("has throwable",hasExecutedWhereThrowableInstanceOfOne(throwableClasses),"executed tasks",executedTasks);
					//LogMessage logMessage = logMessageBuilder.build();
					//LOGGER.trace(logMessage.getTemplate(),logMessage.getArgumentsArray());
				}
				
				@Override
				public Executor addExecutables(Executable... executables) {
					if(ArrayHelper.getInstance().isNotEmpty(executables)){
						if(input == null)
							input = new ArrayList<>();
						input.addAll(Arrays.asList(executables));
					}
					return this;
				}
				
				@Override
				public Collection<Executable> getWhereThrowableInstanceOf() {
					Collection<Executable> executables = new ArrayList<>();
					Collection<Class<?>> throwableClasses = getThrowableClasses();
					for(Executable executable : getInput())
						if(executable.getThrowable()!=null && ClassHelper.getInstance().isInstanceOfOneAtLeast(executable.getThrowable().getClass(), throwableClasses))
							executables.add(executable);
					return executables;
				}
				
				@Override
				public Collection<Executable> getWhereThrowableIsNull() {
					Collection<Executable> executables = new ArrayList<>();
					for(Executable executable : getInput())
						if(executable.getThrowable() == null)
							executables.add(executable);
					return executables;
				}
				
				@Override
				public Collection<Executable> getWhereThrowableIsNotNull() {
					Collection<Executable> executables = new ArrayList<>();
					for(Executable executable : getInput())
						if(executable.getThrowable() != null)
							executables.add(executable);
					return executables;
				}
				
				@Override
				public Executor setCorePoolSize(Integer corePoolSize) {
					this.corePoolSize = corePoolSize;
					return this;
				}
				
				@Override
				public Executor setMaximumPoolSize(Integer maximumPoolSize) {
					this.maximumPoolSize = maximumPoolSize;
					return this;
				}
				
				@Override
				public Executor setNumberOfMillisecondBeforeReexecuting(Long numberOfMillisecondBeforeReexecuting) {
					this.numberOfMillisecondBeforeReexecuting = numberOfMillisecondBeforeReexecuting;
					return this;
				}
				
				@Override
				public Executor setNumberOfReexecution(Long numberOfReexecution) {
					this.numberOfReexecution = numberOfReexecution;
					return this;
				}
				
				@Override
				public Executor setWorkQueueInitialSize(Integer workQueueInitialSize) {
					this.workQueueInitialSize = workQueueInitialSize;
					return this;
				}
				
				@Override
				public Executor setIsAutomaticallyShutdown(Boolean isAutomaticallyShutdown){
					this.isAutomaticallyShutdown = isAutomaticallyShutdown;
					return this;
				}
				
				@Override
				public Executor addThrowableClasses(Class<?>...throwableClasses) {
					if(ArrayHelper.getInstance().isNotEmpty(throwableClasses)){
						if(this.throwableClasses == null)
							this.throwableClasses = new ArrayList<>();
						this.throwableClasses.addAll(Arrays.asList(throwableClasses));
					}
					return this;
				}
				
				@Override
				public Executor setThrowableClasses(Collection<Class<?>> throwableClasses) {
					this.throwableClasses = throwableClasses;
					return this;
				}
				
				@Override
				public Executor setMaximumDuration(Duration maximumDuration){
					this.maximumDuration = maximumDuration;
					return this;
				}
				
				@Override
				public Executor setKeepAliveDuration(Duration keepAliveDuration){
					this.keepAliveDuration = keepAliveDuration;
					return this;
				}
				
			}	
		}
	}
	
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
	
	@Getter @Setter @Accessors(chain=true) @AllArgsConstructor
	public static class Duration implements Serializable{
		private static final long serialVersionUID = 1L;
		
		private Long value;
		private TimeUnit unit;
		
	}
}
