package org.cyk.utility.value;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

@Deprecated
public interface ValueConverter extends FunctionWithPropertiesAsInput<Object> {

	Object getValue();
	ValueConverter setValue(Object value);
	
	Class<?> getClazz();
	ValueConverter setClazz(Class<?> aClass);
	
	ValueConverter execute(Object value,Class<?> aClass);
}
