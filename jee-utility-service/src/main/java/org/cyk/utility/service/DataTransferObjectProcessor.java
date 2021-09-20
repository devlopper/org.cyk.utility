package org.cyk.utility.service;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.enumeration.Action;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;

public interface DataTransferObjectProcessor {

	<T> void process(Class<T> klass,Arguments arguments,Action action,Collection<T> collection);
	<T> void process(Class<T> klass,Arguments arguments,Action action,@SuppressWarnings("unchecked") T...array);
	
	<T> void processRead(Class<T> klass,Arguments arguments,Collection<T> collection);
	<T> void processRead(Class<T> klass,Arguments arguments,@SuppressWarnings("unchecked") T...array);
	
	public abstract static class AbstractImpl extends AbstractObject implements DataTransferObjectProcessor,Serializable {
		
		@Override
		public <T> void process(Class<T> klass,Arguments arguments,Action action, Collection<T> collection) {
			if(CollectionHelper.isEmpty(collection))
				return;
			collection.forEach(dto -> {
				__process__(klass, arguments,action, dto);
			});
		}
		
		protected <T> void __process__(Class<T> klass,Arguments arguments,Action action,T dto) {
			if(Action.READ.equals(action))
				__processRead__(klass, arguments,dto);
		}
		
		protected <T> void __processRead__(Class<T> klass,Arguments arguments,T dto) {
			
		}
		
		@Override
		public <T> void process(Class<T> klass,Arguments arguments,Action action, @SuppressWarnings("unchecked") T... array) {
			if(ArrayHelper.isEmpty(array))
				return;
			process(klass,arguments,action, CollectionHelper.listOf(array));
		}
		
		@Override
		public <T> void processRead(Class<T> klass,Arguments arguments, Collection<T> collection) {
			process(klass, arguments,Action.READ, collection);
		}
		
		@Override
		public <T> void processRead(Class<T> klass,Arguments arguments, @SuppressWarnings("unchecked") T... array) {
			process(klass, arguments,Action.READ, array);
		}
	}
	
	static DataTransferObjectProcessor getInstance() {
		return Helper.getInstance(DataTransferObjectProcessor.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}