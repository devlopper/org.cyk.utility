package org.cyk.utility.value;

import org.cyk.utility.helper.Helper;

public interface ValueHelper extends Helper {

	<T> T defaultToIfNull(Class<T> aClass, T value, T defaultValue);
	<T> T defaultToIfNull(T value, T defaultValue);

	
	
}
