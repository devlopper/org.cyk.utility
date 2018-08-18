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
	public FunctionRunnableMap set(Class<?> aClass, Class<? extends FunctionRunnable<?>> functionRunnableClass) {
		if(map == null)
			map = new HashMap<>();
		map.put(aClass, functionRunnableClass);
		return this;
	}

	@Override
	public Class<? extends FunctionRunnable<?>> get(Class<?> aClass) {
		Class<? extends FunctionRunnable<?>> functionRunnableClass = null;
		if(map != null)
			functionRunnableClass = map.get(aClass);
		return functionRunnableClass;
	}

}
