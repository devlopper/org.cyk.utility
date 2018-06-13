package org.cyk.utility.value;

public interface ValueHelper {

	<T> T defaultToIfNull(Class<T> aClass, T value, T defaultValue);
	<T> T defaultToIfNull(T value, T defaultValue);

	
	
}
