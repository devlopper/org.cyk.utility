package org.cyk.utility.enumeration;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface EnumGetter extends FunctionWithPropertiesAsInput<Enum<?>> {

	EnumGetter setClazz(Class<? extends Enum<?>> clazz);
	Class<? extends Enum<?>> getClazz();
	
	EnumGetter setName(Object name);
	Object getName();
	
	EnumGetter setIsNameCaseSensitive(Boolean isNameCaseSensitive);
	Boolean getIsNameCaseSensitive();
	
}
