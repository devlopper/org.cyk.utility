package org.cyk.utility.mapping;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

@SuppressWarnings("rawtypes")
public interface MapperSourceDestinationClassGetter extends FunctionWithPropertiesAsInput<Class> {

	Class<?> getKlass();
	MapperSourceDestinationClassGetter setKlass(Class<?> klass);
	
}
