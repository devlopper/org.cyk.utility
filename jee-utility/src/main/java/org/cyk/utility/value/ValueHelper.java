package org.cyk.utility.value;

import java.util.Collection;

import org.cyk.utility.collection.CollectionInstance;
import org.cyk.utility.helper.Helper;

public interface ValueHelper extends Helper {

	<T> T defaultToIfNull(Class<T> aClass, T value, T defaultValue);
	<T> T defaultToIfNull(T value, T defaultValue);

	<FROM,CLASS> CLASS cast(Object object,CLASS aClass);
	
	<T> T returnOrThrowIfBlank(String name,T value);
	
	static Boolean isEmpty(Object value) {
		if(value == null)
			return Boolean.TRUE;
		if(value instanceof String)
			return ((String)value).isEmpty();
		if(value instanceof Collection<?>)
			return ((Collection<?>)value).isEmpty();
		if(value instanceof CollectionInstance<?>)
			return ((CollectionInstance<?>)value).isEmpty();
		throw new RuntimeException("check empty of value type "+value.getClass()+" not yet handled");
	}
	
	static Boolean isNotEmpty(Object value) {
		return !isEmpty(value);
	}
	
	static Boolean isBlank(Object value) {
		if(isEmpty(value))
			return Boolean.TRUE;
		if(value instanceof String)
			return ((String)value).isBlank();
		throw new RuntimeException("check blank of value type "+value.getClass()+" not yet handled");
	}

	static Boolean isNotBlank(Object value) {
		return !isBlank(value);
	}
}
