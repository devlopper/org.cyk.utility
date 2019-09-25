package org.cyk.utility.__kernel__.throwable;

import java.util.Collection;
import java.util.List;

import org.cyk.utility.__kernel__.value.ValueHelper;

public interface ThrowableHelper {

	static Throwable getInstanceOf(Throwable throwable,Collection<Class<?>> classes){
		if(throwable == null || classes == null || classes.isEmpty())
			return null;
		Throwable index;
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
	
	static Throwable getInstanceOf(Throwable throwable,Class<?>...classes){
		if(throwable == null || classes == null || classes.length == 0)
			return null;
		return getInstanceOf(throwable,List.of(classes));
	}
	
	static Throwable getFirstCause(Throwable throwable){
		if(throwable == null)
			return null;
		Throwable cause=throwable,index = throwable;
		while(index!=null){
			cause = index;
			index = index.getCause();
		}
		return cause;
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
	
	/**/
	
	RuntimeException NOT_YET_IMPLEMENTED = new RuntimeException("not yet implemented");
}
