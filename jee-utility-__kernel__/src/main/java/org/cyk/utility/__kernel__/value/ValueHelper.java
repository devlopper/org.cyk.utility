package org.cyk.utility.__kernel__.value;

import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.collection.CollectionInstance;
import org.cyk.utility.__kernel__.string.StringHelper;

public interface ValueHelper {

	static <T> T defaultToIfNull(Class<T> aClass,T value,T defaultValue){
		return value == null ? defaultValue : value;
	}
	
	static <T> T defaultToIfNull(T value,T defaultValue){
		return value == null ? defaultValue : value;
	}
	
	static <FROM, CLASS> CLASS cast(Object object, CLASS aClass) {
		return (CLASS) object;
	}
	
	static <T> T returnOrThrowIfBlank(String name, T value) {
		//TODO use isBlank method
		Boolean isThrow = value == null;
		if(!Boolean.TRUE.equals(isThrow))
			isThrow = (value instanceof String) &&StringHelper.isBlank((String) value);
		if(!Boolean.TRUE.equals(isThrow))
			isThrow = (value instanceof Collection) && CollectionHelper.isEmpty((Collection<?>)value);
		if(!Boolean.TRUE.equals(isThrow))
			isThrow = (value instanceof CollectionInstance<?>) && CollectionHelper.isEmpty((CollectionInstance<?>)value);		
		if(Boolean.TRUE.equals(isThrow))
			throw new RuntimeException(name+" is required.");
		return value;
	}
	
	static <T> T defaultToIfBlank(T value,T defaultValue){
		return isBlank(value) ? defaultValue : value;
	}
	
	/**/
	
	static Boolean isEmpty(Object value) {
		Boolean isEmpty = value == null;
		if(!Boolean.TRUE.equals(isEmpty))
			isEmpty = (value instanceof String) && StringHelper.isEmpty((String) value);
		if(!Boolean.TRUE.equals(isEmpty))
			isEmpty = (value instanceof Collection) && CollectionHelper.isEmpty((Collection<?>)value);
		if(!Boolean.TRUE.equals(isEmpty))
			isEmpty = (value instanceof CollectionInstance<?>) && CollectionHelper.isEmpty((CollectionInstance<?>)value);
		
		return Boolean.TRUE.equals(isEmpty);
	}
	
	static Boolean isNotEmpty(Object value) {
		return !Boolean.TRUE.equals(isEmpty(value));
	}
	
	static Boolean isBlank(Object value) {
		Boolean isBlank = isEmpty(value);
		if(!Boolean.TRUE.equals(isBlank))
			isBlank = (value instanceof String) && StringHelper.isBlank((String) value);
		return Boolean.TRUE.equals(isBlank);
	}

	static Boolean isNotBlank(Object value) {
		return !Boolean.TRUE.equals(isBlank(value));
	}
	
	static void throwIfBlank(String name, Object value) {
		if(Boolean.TRUE.equals(isBlank(value)))
			throw new RuntimeException(name+" is required.");
	}
	
}
