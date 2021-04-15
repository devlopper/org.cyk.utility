package org.cyk.utility.__kernel__.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.runnable.RunnableHelper;
import org.cyk.utility.__kernel__.runnable.Runner;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.ValueHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface CollectionProcessor {

	<T> void process(Class<T> klass,Arguments<T> arguments);
	
	public static abstract class AbstractImpl extends AbstractObject implements CollectionProcessor,Serializable {
		
		@Override
		public <T> void process(Class<T> klass,Arguments<T> arguments) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("arguments", arguments);
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("processing logic", arguments.processing);		
			LogHelper.logInfo(String.format("%s element(s) to be processed",CollectionHelper.getSize(arguments.list)), getClass());
			if(CollectionHelper.isEmpty(arguments.list))
				return;
			Integer batchSize = ValueHelper.defaultToIfNull(arguments.batchSize,1000);
			List<List<Object>> batches = CollectionHelper.getBatches(arguments.list, batchSize);
			LogHelper.logInfo(String.format("%s processing will be done in %s batches (batch size = %s).",arguments.runnerArguments == null ? "sequential" : "parallel"
				, batches.size(),batchSize), getClass());
			if(arguments.runnerArguments == null) {
				for(List<Object> batch : batches) {
					arguments.processing.process((List<T>) batch);
				}
			}else {
				for(List<Object> batch : batches) {
					arguments.runnerArguments.addRunnables(new Runnable() {						
						@Override
						public void run() {
							arguments.processing.process((List<T>) batch);
						}
					});
				}				
				Runner.getInstance().run(arguments.runnerArguments);
			}
			LogHelper.logInfo("processing done.", getClass());
		}
	}
	
	/**/
	
	static CollectionProcessor getInstance() {
		return Helper.getInstance(CollectionProcessor.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments<T> {
		private List<Object> list;
		private Integer batchSize;
		private Processing<T> processing;
		private Runner.Arguments runnerArguments;
		
		public List<Object> getList(Boolean injectIfNull) {
			if(list == null && Boolean.TRUE.equals(injectIfNull))
				list = new ArrayList<>();
			return list;
		}
		
		/**/
		
		public static interface Processing<T> {
			void process(List<T> list);
			Boolean getParallelizable();
			Processing<T> setParallelizable(Boolean parallelizable);
			
			@Getter @Setter @Accessors(chain=true)
			public static abstract class AbstractImpl<T> extends AbstractObject implements Processing<T>,Serializable {
				
				protected Boolean parallelizable;
				
				@Override
				public void process(List<T> list) {
					if(CollectionHelper.isEmpty(list))
						return;
					__process__(list);
				}
				
				protected void __process__(List<T> list) {
					Boolean parallelizable = getParallelizable();
					if(Boolean.TRUE.equals(parallelizable)) {
						__processParallely__(list);
					}else {
						__processSequentially__(list);
					}					
				}
				
				protected void __processSequentially__(List<T> list) {
					for(T object : list)
						__process__(object);
				}
				
				protected void __processParallely__(List<T> list) {
					Collection<Runnable> runnables = new ArrayList<>();
					for(T object : list)
						runnables.add(new Runnable() {							
							@Override
							public void run() {
								__process__(object);
							}
						});
					LogHelper.logInfo(String.format("parallel processing of %s runnable(s)", runnables.size()), getClass());
					__processParallely__(list, runnables);
				}
				
				protected void __processParallely__(List<T> list,Collection<Runnable> runnables) {
					RunnableHelper.run(runnables, "parallel processing");
				}
				
				protected abstract void __process__(T object);
			}
		}
	}
}