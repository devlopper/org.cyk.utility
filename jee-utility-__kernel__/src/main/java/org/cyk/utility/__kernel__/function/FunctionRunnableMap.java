package org.cyk.utility.__kernel__.function;

public interface FunctionRunnableMap {

	FunctionRunnableMap set(Class<?> aClass,Class<? extends FunctionRunnable<?>> functionRunnableClass,Boolean isOverridable);
	FunctionRunnableMap set(Class<?> aClass,Class<? extends FunctionRunnable<?>> functionRunnableClass);
	Class<? extends FunctionRunnable<?>> get(Class<?> aClass);
}
