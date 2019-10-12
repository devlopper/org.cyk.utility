package org.cyk.utility.instance;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

@Deprecated
public interface InstanceBuilder extends FunctionWithPropertiesAsInput<Object> {

	Class<?> getClazz();
	InstanceBuilder setClazz(Class<?> aClass);
	
	Object[] getConstructorParameters();
	InstanceBuilder setConstructorParameters(Object[] parameters);
	
	Object getFieldsValuesObject();
	InstanceBuilder setFieldsValuesObject(Object fieldsValuesObject);
	
}
