package org.cyk.utility.throwable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.throwable.SystemException;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.helper.AbstractHelper;

@ApplicationScoped @Deprecated
public class ThrowableHelperImpl extends AbstractHelper implements ThrowableHelper,Serializable {
	private static final long serialVersionUID = 1L;

	private static ThrowableHelper INSTANCE;
	public static ThrowableHelper getInstance(Boolean isNew) {
		//if(INSTANCE == null || Boolean.TRUE.equals(isNew))
			INSTANCE =  DependencyInjection.inject(ThrowableHelper.class);
		return INSTANCE;
	}
	public static ThrowableHelper getInstance() {
		return getInstance(null);
	}
	
	@Override
	public Throwable getInstanceOf(Throwable throwable,Class<?> aClass){
		Throwable index = throwable;
		while(index!=null){
			if(aClass.isAssignableFrom(index.getClass())){
				return index;
			}else
				index = index.getCause();
		}
		return null;
	}
	
	@Override
	public Throwable getInstanceOf(Throwable throwable,Class<?> aClass,Class<?>...classes){
		List<Class<?>> list = new ArrayList<>();
		list.add(aClass);
		if(__inject__(ArrayHelper.class).isNotEmpty(classes))
			list.addAll(Arrays.asList(classes));
		for(Class<?> index : list){
			Throwable instance = getInstanceOf(throwable, index);
			if(instance!=null)
				return instance;
		}
		return null;
	}
	
	@Override
	public Throwable getFirstCause(Throwable throwable){
		Throwable cause=throwable,index = throwable;
		while(index!=null){
			cause = index;
			index = index.getCause();
		}
		return cause;
	}
	
	/*@Override
	public Throwable getLastCause(Throwable throwable) {
		Throwable cause=throwable,index = throwable;
		while(index!=null){
			cause = index;
			index = index.getCause();
		}
		return cause;
	}*/
	
	@Override
	public void throw_(RuntimeException runtimeException) {
		throw runtimeException;
	}
	
	@Override
	public void throw_(SystemException systemException) {
		throw_((RuntimeException)systemException);
	}
	
	@Override
	public void throwRuntimeException(String message) {
		throw_(new RuntimeException(message));
	}

	@Override
	public void throwRuntimeExceptionNotYetImplemented(String name) {
		throwRuntimeException(name+" : Not yet implemented");
	}
	
	@Override
	public void throwRuntimeExceptionNotYetImplemented() {
		throwRuntimeException("Not yet implemented");
	}
	
	@Override
	public void throwRuntimeExceptionImplementationOrRunnableRequired(Class<?> aClass) {
		throwRuntimeException(aClass+" : Implementation or runnable required");
	}

	@Override
	public void throwRuntimeExceptionIfNull(Object value, String message) {
		if(value == null)
			throwRuntimeException(message);
	}

	@Override
	public void throwRuntimeExceptionIfEmpty(Object value, String message) {
		if(ValueHelper.isEmpty(value))
			throwRuntimeException(message);
	}

	public void throwRuntimeExceptionIfBlank(Object value, String message) {
		if(ValueHelper.isBlank(value))
			throwRuntimeException(message);
	}
	
	/**/
	
	public static Throwable __getInstanceOf__(Throwable throwable,Class<?> aClass){
		Throwable index = throwable;
		while(index!=null){
			if(aClass.isAssignableFrom(index.getClass())){
				return index;
			}else
				index = index.getCause();
		}
		return null;
	}
	
	public static Throwable __getInstanceOf__(Throwable throwable,Class<?> aClass,Class<?>...classes){
		List<Class<?>> list = new ArrayList<>();
		list.add(aClass);
		if(__inject__(ArrayHelper.class).isNotEmpty(classes))
			list.addAll(Arrays.asList(classes));
		for(Class<?> index : list){
			Throwable instance = __getInstanceOf__(throwable, index);
			if(instance!=null)
				return instance;
		}
		return null;
	}
	
	public static Throwable __getFirstCause__(Throwable throwable){
		Throwable cause=throwable,index = throwable;
		while(index!=null){
			cause = index;
			index = index.getCause();
		}
		return cause;
	}
	
	public static void __throw__(Exception exception) {
		throw new RuntimeException(exception);
	}
	
	public static void __throw__(RuntimeException runtimeException) {
		throw runtimeException;
	}
	
	public static void __throw__(SystemException systemException) {
		__throw__((RuntimeException)systemException);
	}
	
	public static void __throwRuntimeException__(String message) {
		__throw__(new RuntimeException(message));
	}

	public static void __throwRuntimeExceptionNotYetImplemented__(String name) {
		__throwRuntimeException__(name+" : Not yet implemented");
	}
	
	public static void __throwRuntimeExceptionNotYetImplemented__() {
		__throwRuntimeException__("Not yet implemented");
	}
	
	public static void __throwRuntimeExceptionImplementationOrRunnableRequired__(Class<?> aClass) {
		__throwRuntimeException__(aClass+" : Implementation or runnable required");
	}

	public static void __throwRuntimeExceptionIfNull__(Object value, String message) {
		if(value == null)
			__throwRuntimeException__(message);
	}

	public static void __throwRuntimeExceptionIfEmpty__(Object value, String message) {
		if(ValueHelper.isEmpty(value))
			__throwRuntimeException__(message);
	}

	public static void __throwRuntimeExceptionIfBlank__(Object value, String message) {
		if(ValueHelper.isBlank(value))
			__throwRuntimeException__(message);
	}
}
