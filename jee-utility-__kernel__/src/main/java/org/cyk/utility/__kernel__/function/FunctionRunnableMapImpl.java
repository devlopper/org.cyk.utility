package org.cyk.utility.__kernel__.function;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

@Singleton
public class FunctionRunnableMapImpl implements FunctionRunnableMap,Serializable {
	private static final long serialVersionUID = 1L;

	private Map<Class<?>,Class<? extends FunctionRunnable<?>>> map;
	private Map<Class<? extends FunctionRunnable<?>>,Integer> levelMap;
	
	@Override
	public FunctionRunnableMap set(Class<?> aClass, Class<? extends FunctionRunnable<?>> functionRunnableClass,Integer level) {
		Class<? extends FunctionRunnable<?>> currentFunctionRunnableClass = get(aClass);
		Integer currentFunctionRunnableClassLevel = levelMap == null ? null : levelMap.get(currentFunctionRunnableClass);
		if(currentFunctionRunnableClass==null || currentFunctionRunnableClassLevel == null || (level!=null && level > currentFunctionRunnableClassLevel)) {
			if(map == null)
				map = new HashMap<>();
			map.put(aClass, functionRunnableClass);
			if(levelMap == null)
				levelMap = new HashMap<>();
			levelMap.put(functionRunnableClass,level);
		}
		return this;
	}
	
	@Override
	public FunctionRunnableMap set(Class<?> aClass, Class<? extends FunctionRunnable<?>> functionRunnableClass) {
		return set(aClass, functionRunnableClass, null);
	}

	@Override
	public Class<? extends FunctionRunnable<?>> get(Class<?> aClass) {
		Class<? extends FunctionRunnable<?>> functionRunnableClass = null;
		if(map != null)
			functionRunnableClass = map.get(aClass);
		return functionRunnableClass;
	}

}
