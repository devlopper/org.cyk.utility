package org.cyk.utility.__kernel__.value;

import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionInstance;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;

public interface ValueHelper {

	static Boolean isEmpty(Object value) {
		if(value == null)
			return Boolean.TRUE;
		if(value instanceof String)
			return ((String) value).isEmpty();
		if(value instanceof Collection)
			return((Collection<?>)value).isEmpty();
		if(value instanceof CollectionInstance<?>)
			return ((CollectionInstance<?>)value).isEmpty();
		return Boolean.FALSE;
	}
	
	static Boolean isNotEmpty(Object value) {
		if(value == null)
			return Boolean.FALSE;
		return !isEmpty(value);
	}
	
	static Boolean isBlank(Object value) {
		if(value == null)
			return Boolean.TRUE;
		if(value instanceof String)
			return ((String) value).isBlank();
		return isEmpty(value);
	}

	static Boolean isNotBlank(Object value) {
		if(value == null)
			return Boolean.FALSE;
		return !isBlank(value);
	}
	
	static <T> T defaultToIfNull(Class<T> klass,T value,T defaultValue){
		return value == null ? defaultValue : value;
	}
	
	static <T> T defaultToIfNull(T value,T defaultValue){
		return value == null ? defaultValue : value;
	}
	
	static <T> T defaultToIfBlank(T value,T defaultValue){
		return isBlank(value) ? defaultValue : value;
	}
	
	@SuppressWarnings("unchecked")
	static <FROM, CLASS> CLASS cast(Object object, CLASS aClass) {
		return (CLASS) object;
	}
	
	static <T> T returnOrThrowIfBlank(String name,T value){
		ThrowableHelper.throwIllegalArgumentExceptionIfBlank(name, value);
		return value;
	}
	
	static <T> T throwIfBlank(String name,T value){
		ThrowableHelper.throwIllegalArgumentExceptionIfBlank(name, value);
		return value;
	}
	
}
