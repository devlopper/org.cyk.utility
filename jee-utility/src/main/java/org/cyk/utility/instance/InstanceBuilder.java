package org.cyk.utility.instance;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface InstanceBuilder<INSTANCE> extends FunctionWithPropertiesAsInput<INSTANCE> {

	Class<INSTANCE> getClazz();
	InstanceBuilder<INSTANCE> setClazz(Class<?> aClass);
	
	Object[] getConstructorParameters();
	InstanceBuilder<INSTANCE> setConstructorParameters(Object[] parameters);
	
	Object getFieldsValuesObject();
	InstanceBuilder<INSTANCE> setFieldsValuesObject(Object fieldsValuesObject);
	
}
