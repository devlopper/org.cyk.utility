package org.cyk.utility.mapping;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
@Deprecated
public interface MapperSourceDestinationClassNameGetter extends FunctionWithPropertiesAsInput<String> {

	Class<?> getKlass();
	MapperSourceDestinationClassNameGetter setKlass(Class<?> klass);
	
	String getClassName();
	MapperSourceDestinationClassNameGetter setClassName(String className);
}
