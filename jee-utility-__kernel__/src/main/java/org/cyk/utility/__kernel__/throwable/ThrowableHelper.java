package org.cyk.utility.__kernel__.throwable;

import java.util.Collection;
import java.util.List;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;

public interface ThrowableHelper {

	String NOT_YET_IMPLEMENTED_MESSAGE = "not yet implemented";
	
	static java.lang.Throwable getInstanceOf(java.lang.Throwable throwable,Collection<Class<?>> classes){
		if(throwable == null || CollectionHelper.isEmpty(classes))
			return null;
		java.lang.Throwable index;
		for(Class<?> klass : classes) {
			index = throwable;
			while(index!=null){
				if(klass.isAssignableFrom(index.getClass()))
					return index;
				else
					index = index.getCause();
			}	
		}
		return null;
	}
	
	static java.lang.Throwable getInstanceOf(java.lang.Throwable throwable,Class<?>...classes){
		if(throwable == null || ArrayHelper.isEmpty(classes))
			return null;
		return getInstanceOf(throwable,List.of(classes));
	}
	
	static java.lang.Throwable getInstanceOfSystemClient(java.lang.Throwable throwable){
		if(throwable == null)
			return null;
		return getInstanceOf(throwable, SystemClientException.class);
	}
	
	static java.lang.Throwable getFirstCause(java.lang.Throwable throwable){
		if(throwable == null)
			return null;
		java.lang.Throwable cause=throwable,index = throwable;
		while(index!=null){
			cause = index;
			index = index.getCause();
		}
		return cause;
	}

	static void throwRuntimeExceptionIfTrue(Boolean condition, String message) {
		if(Boolean.TRUE.equals(condition))
			throw new java.lang.RuntimeException(message);
	}
	
	static void throwIllegalArgumentException(String name, Object value) {
		throw new IllegalArgumentException(name+" has an illegal value <<"+value+">>");
	}
	
	static void throwIllegalArgumentExceptionIfNull(String name, Object value) {
		if(value == null)
			throwIllegalArgumentException(name, value);
	}
	
	static void throwIllegalArgumentExceptionIfBlank(String name, Object value) {
		if(ValueHelper.isBlank(value))
			throwIllegalArgumentException(name, value);
	}
	
	static void throwIllegalArgumentExceptionIfEmpty(String name, Object value) {
		if(ValueHelper.isEmpty(value))
			throwIllegalArgumentException(name, value);
	}
	
	static void throwNotYetImplemented(String name) {
		throw new java.lang.RuntimeException(name+" "+NOT_YET_IMPLEMENTED_MESSAGE);
	}
	
	static void throwNotYetImplemented() {
		throw NOT_YET_IMPLEMENTED;
	}
	
	/**/
	
	java.lang.RuntimeException NOT_YET_IMPLEMENTED = new java.lang.RuntimeException(NOT_YET_IMPLEMENTED_MESSAGE);
	
	
}
