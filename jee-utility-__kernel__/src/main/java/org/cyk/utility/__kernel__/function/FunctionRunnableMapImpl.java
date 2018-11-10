package org.cyk.utility.__kernel__.function;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

@Singleton
public class FunctionRunnableMapImpl implements FunctionRunnableMap,Serializable {
	private static final long serialVersionUID = 1L;

	private Map<Class<?>,Class<? extends FunctionRunnable<?>>> map;
	
	@Override
	public FunctionRunnableMap set(Class<?> aClass, Class<? extends FunctionRunnable<?>> functionRunnableClass,Boolean isOverridable) {
		Class<? extends FunctionRunnable<?>> currentFunctionRunnableClass = get(aClass);
		if(currentFunctionRunnableClass==null || Boolean.TRUE.equals(isOverridable)) {
			if(map == null)
				map = new HashMap<>();
			
			map.put(aClass, functionRunnableClass);	
		}
		return this;
	}
	
	@Override
	public FunctionRunnableMap set(Class<?> aClass, Class<? extends FunctionRunnable<?>> functionRunnableClass) {
		return set(aClass, functionRunnableClass, Boolean.FALSE);
	}

	@Override
	public Class<? extends FunctionRunnable<?>> get(Class<?> aClass) {
		Class<? extends FunctionRunnable<?>> functionRunnableClass = null;
		if(map != null)
			functionRunnableClass = map.get(aClass);
		return functionRunnableClass;
	}

}
