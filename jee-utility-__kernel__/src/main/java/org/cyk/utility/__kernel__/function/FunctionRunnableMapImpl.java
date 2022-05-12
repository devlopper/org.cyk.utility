package org.cyk.utility.__kernel__.function;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FunctionRunnableMapImpl implements FunctionRunnableMap,Serializable {
	private static final long serialVersionUID = 1L;

	private static final Map<Class<?>,Class<? extends FunctionRunnable<?>>> MAP = new HashMap<>();
	private static final Map<Class<? extends FunctionRunnable<?>>,Integer> LEVEL_MAP = new HashMap<>();
	
	public static void __set__(Class<?> aClass, Class<? extends FunctionRunnable<?>> functionRunnableClass,Integer level,Boolean overwrite) {
		Class<? extends FunctionRunnable<?>> currentFunctionRunnableClass = MAP.get(aClass);
		Integer currentFunctionRunnableClassLevel = LEVEL_MAP.get(currentFunctionRunnableClass);
		if(currentFunctionRunnableClass==null || currentFunctionRunnableClassLevel == null || (level!=null && level > currentFunctionRunnableClassLevel) || Boolean.TRUE.equals(overwrite)) {
			MAP.put(aClass, functionRunnableClass);
			LEVEL_MAP.put(functionRunnableClass,level);
		}
	}
	
	public static void __set__(Class<?> aClass, Class<? extends FunctionRunnable<?>> functionRunnableClass,Integer level) {
		__set__(aClass, functionRunnableClass, level, null);
	}
	
	@Override
	public FunctionRunnableMap set(Class<?> aClass, Class<? extends FunctionRunnable<?>> functionRunnableClass,Integer level,Boolean overwrite) {
		__set__(aClass, functionRunnableClass, level, overwrite);
		return this;
	}
	
	@Override
	public FunctionRunnableMap set(Class<?> aClass, Class<? extends FunctionRunnable<?>> functionRunnableClass,Integer level) {
		return set(aClass, functionRunnableClass,level, null);
	}
	
	@Override
	public FunctionRunnableMap set(Class<?> aClass, Class<? extends FunctionRunnable<?>> functionRunnableClass) {
		return set(aClass, functionRunnableClass, null,null);
	}

	@Override
	public Class<? extends FunctionRunnable<?>> get(Class<?> aClass) {
		return MAP.get(aClass);
	}

}
