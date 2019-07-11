package org.cyk.utility.mapping;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface MapperSourceDestinationClassNameGetter extends FunctionWithPropertiesAsInput<String> {

	Class<?> getKlass();
	MapperSourceDestinationClassNameGetter setKlass(Class<?> klass);
	
}
