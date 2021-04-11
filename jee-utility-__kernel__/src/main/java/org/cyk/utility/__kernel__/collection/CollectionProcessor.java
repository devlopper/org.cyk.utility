package org.cyk.utility.__kernel__.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
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
			LogHelper.logInfo(String.format("processing will be done in %s batches", batches.size()), getClass());
			for(List<Object> batch : batches) {
				arguments.processing.process((List<T>) batch);
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
		
		public List<Object> getList(Boolean injectIfNull) {
			if(list == null && Boolean.TRUE.equals(injectIfNull))
				list = new ArrayList<>();
			return list;
		}
		
		/**/
		
		public static interface Processing<T> {
			void process(List<T> list);
			
			public static abstract class AbstractImpl<T> extends AbstractObject implements Processing<T>,Serializable {
				
				@Override
				public void process(List<T> list) {
					if(CollectionHelper.isEmpty(list))
						return;
					for(T object : list)
						__process__(object);
				}
				
				protected abstract void __process__(T object);
			}
		}
	}
}