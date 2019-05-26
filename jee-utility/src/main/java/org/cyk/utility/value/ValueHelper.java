package org.cyk.utility.value;

import org.cyk.utility.helper.Helper;

public interface ValueHelper extends Helper {

	<T> T defaultToIfNull(Class<T> aClass, T value, T defaultValue);
	<T> T defaultToIfNull(T value, T defaultValue);

	<FROM,CLASS> CLASS cast(Object object,CLASS aClass);
	
	<T> T returnOrThrowIfBlank(String name,T value);
	
	Boolean isEmpty(Object value);
	Boolean isNotEmpty(Object value);
	
	Boolean isBlank(Object value);
	Boolean isNotBlank(Object value);
}
