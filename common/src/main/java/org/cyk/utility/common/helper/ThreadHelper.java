package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.cyk.utility.common.Action;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
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
		
		Long getStartedMillisecond();
		Executable setStartedMillisecond(Long startedMillisecond);
		
		Long getEndedMillisecond();
		Executable setEndedMillisecond(Long endedMillisecond);
		
		Long getDurationMillisecond();
		Executable setDurationMillisecond(Long durationMillisecond);
		
		void ____execute____();
		
		@Getter
		public static class Adapter extends AbstractBean implements Executable,Serializable {
			private static final long serialVersionUID = 1L;
			
			protected Throwable throwable;
			protected String name;
			protected Long startedMillisecond,endedMillisecond,durationMillisecond;
			
			@Override
			public Executable setDurationMillisecond(Long durationMillisecond) {
				return null;
			}
			
			@Override
			public Executable setStartedMillisecond(Long startedMillisecond) {
				return null;
			}
			
			@Override
			public Executable setEndedMillisecond(Long endedMillisecond) {
				return null;
			}
			
			@Override
			public Executable setThrowable(Throwable throwable) {
				return null;
			}
			
			@Override
			public Executable setName(String name) {
				return null;
			}
			
			@Override
			public void ____execute____() {}
			
			@Override
			public void run() {}
			
			public static class Default extends Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				public void run() {
					final String name = getName();
					new LoggingHelper.Run.Adapter.Default(StackTraceHelper.getInstance().getAt(2),getClass()){
						private static final long serialVersionUID = 1L;
						
						public Object __execute__() {
							try {
								____execute____();
							} catch (Exception e) {
								setThrowable(e);
							}
							return null;
						}
						
						@Override
						public String getName() {
							return StringHelper.getInstance().isBlank(name) ? super.getName() : name;
						}
						
					}.execute();
				}
				
				@Override
				public void ____execute____() {
					ThrowableHelper.getInstance().throwNotYetImplemented();
				}
				
				@Override
				public Executable setDurationMillisecond(Long durationMillisecond) {
					this.durationMillisecond = durationMillisecond;
					return this;
				}
				
				@Override
				public Executable setStartedMillisecond(Long startedMillisecond) {
					this.startedMillisecond = startedMillisecond;
					return this;
				}
				
				@Override
				public Executable setEndedMillisecond(Long endedMillisecond) {
					this.endedMillisecond = endedMillisecond;
					return this;
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
				
				@Override
				public String toString() {
					return getName();
				}
			}
		}
	}
	
	public static interface Executor extends Action<Collection<Executable>, Void> {
		
		ThreadPoolExecutor getPool();
		ThreadPoolExecutor getPool(Boolean createIfNull);
		Executor setPool(ThreadPoolExecutor pool);
		ThreadPoolExecutor createPool();
		
		Collection<Executable> getWhereThrowableInstanceOf();
		Collection<Executable> getWhereThrowableIsNull();
		Collection<Executable> getWhereThrowableIsNotNull();
		
		Long getNumberOfMillisecondBetweenExecutableScheduling();
		Executor setNumberOfMillisecondBetweenExecutableScheduling(Long numberOfMillisecondBetweenExecutableScheduling);
		
		Long getMaximumNumberOfExecution();
		Executor setMaximumNumberOfExecution(Long maximumNumberOfExecution);
		
		Long getNumberOfExecution();
		Executor setNumberOfExecution(Long numberOfExecution);
		
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
			protected Long maximumNumberOfExecution,numberOfExecution,numberOfMillisecondBeforeReexecuting,numberOfMillisecondBetweenExecutableScheduling;
			protected ThreadPoolExecutor pool;
			
			@SuppressWarnings("unchecked")
			public Adapter(Collection<Executable> input) {
				super("execute", (Class<Collection<Executable>>) ClassHelper.getInstance().getByName(Collection.class), input, Void.class);
			}
			
			@Override
			public Executor setMaximumNumberOfExecution(Long maximumNumberOfExecution) {
				return null;
			}
			
			@Override
			public Executor setNumberOfExecution(Long numberOfExecution) {
				return null;
			}
			
			@Override
			public Executor setNumberOfMillisecondBetweenExecutableScheduling(Long numberOfMillisecondBetweenExecutableScheduling) {
				return null;
			}
			
			@Override
			public ThreadPoolExecutor getPool(Boolean createIfNull) {
				return null;
			}
			
			@Override
			public Executor setPool(ThreadPoolExecutor pool) {
				return null;
			}
			
			@Override
			public ThreadPoolExecutor createPool() {
				return null;
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
				
				public Default(Collection<Executable> input) {
					super(input);
				}
				
				public Default() {
					this(null);
				}
				
				@Override
				protected Void __execute__() {
					Long maximumNumberOfExecution = InstanceHelper.getInstance().getIfNotNullElseDefault(getMaximumNumberOfExecution(),1l);
					Long numberOfExecution = InstanceHelper.getInstance().getIfNotNullElseDefault(getNumberOfExecution(),0l);
					Long numberOfMillisecondBeforeReexecuting = InstanceHelper.getInstance().getIfNotNullElseDefault(getNumberOfMillisecondBeforeReexecuting(),3000l);
					Long numberOfMillisecondBetweenExecutableScheduling = InstanceHelper.getInstance().getIfNotNullElseDefault(getNumberOfMillisecondBetweenExecutableScheduling(), 1000l);
					logDebug("{} , #max execution={} , #millisecond before reexecuting={} , #millisecond between scheduling={}",getName()
							,maximumNumberOfExecution,numberOfMillisecondBeforeReexecuting,numberOfMillisecondBetweenExecutableScheduling);
					Collection<Executable> executables;
					while(CollectionHelper.getInstance().isNotEmpty(executables = (numberOfExecution == 0l ? getInput() : getWhereThrowableIsNotNull())) && numberOfExecution<maximumNumberOfExecution){
						if(numberOfExecution>0){
							logTrace("Waiting {} ms before re-executing",numberOfMillisecondBeforeReexecuting);
							TimeHelper.getInstance().pause(numberOfMillisecondBeforeReexecuting);
						}
						logTrace("execution No {} , #executables={}",numberOfExecution+1,CollectionHelper.getInstance().getSize(executables));
						ThreadPoolExecutor pool = getPool(Boolean.TRUE);
						for(Executable executable : executables){
							executable.setStartedMillisecond(null);
							executable.setEndedMillisecond(null);
							executable.setDurationMillisecond(null);
							executable.setThrowable(null);
							pool.execute(executable);
							TimeHelper.getInstance().pause(numberOfMillisecondBetweenExecutableScheduling);
						}
						Duration maximumDuration = getMaximumDuration();
						try {
							logDebug("waiting for termination. timeout in {} {} ...",maximumDuration.getValue(),maximumDuration.getUnit());
							pool.awaitTermination(maximumDuration.getValue(), maximumDuration.getUnit());
						} catch (InterruptedException e) {
							logThrowable(e);
						}
						setNumberOfExecution(numberOfExecution++);
						setLoggingMessageBuilder(null);
						setPool(null);
					}
					return Constant.VOID;
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
				public ThreadPoolExecutor getPool(Boolean createIfNull) {
					if(pool==null && Boolean.TRUE.equals(createIfNull))
						pool = createPool();
					return pool;
				}
				
				@Override
				public Executor setPool(ThreadPoolExecutor pool) {
					this.pool = pool;
					return this;
				}
				
				@Override
				public ThreadPoolExecutor createPool() {
					Duration keepAliveDuration = getKeepAliveDuration();
					ThreadPoolExecutor threadPoolExecutor = new java.util.concurrent.ThreadPoolExecutor(getCorePoolSize(), getMaximumPoolSize(), keepAliveDuration.getValue(), keepAliveDuration.getUnit()
							, new ArrayBlockingQueue<Runnable>(getWorkQueueInitialSize())) {
						
						@Override
						protected void beforeExecute(Thread thread, Runnable runnable) {
							super.beforeExecute(thread, runnable);
							((Executable)runnable).setStartedMillisecond(System.currentTimeMillis());
						}
						
						@Override
						protected void afterExecute(Runnable runnable, Throwable throwable) {
							super.afterExecute(runnable, throwable);
							((Executable)runnable).setEndedMillisecond(System.currentTimeMillis());
							Long executableCount = getTaskCount();
							Integer executableFailCount = CollectionHelper.getInstance().getSize(getWhereThrowableIsNotNull());
							if(getCompletedTaskCount() == executableCount - 1){
								logDebug("All {} executable(s) completed. #success={} , #fail={} shutdown signal sent."
										, executableCount,executableCount - executableFailCount,executableFailCount);
								shutdown();
							}
						}
						
					};
					return threadPoolExecutor;
				}
				
				@Override
				public Executor setMaximumNumberOfExecution(Long maximumNumberOfExecution) {
					this.maximumNumberOfExecution = maximumNumberOfExecution;
					return this;
				}
				
				@Override
				public Executor setNumberOfExecution(Long numberOfExecution) {
					this.numberOfExecution = numberOfExecution;
					return this;
				}
				
				@Override
				public Executor setNumberOfMillisecondBetweenExecutableScheduling(Long numberOfMillisecondBetweenExecutableScheduling) {
					this.numberOfMillisecondBetweenExecutableScheduling = numberOfMillisecondBetweenExecutableScheduling;
					return this;
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
