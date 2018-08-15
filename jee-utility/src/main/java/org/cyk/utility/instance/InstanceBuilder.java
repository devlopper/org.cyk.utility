package org.cyk.utility.instance;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface InstanceBuilder<INSTANCE> extends FunctionWithPropertiesAsInput<INSTANCE> {

	Class<INSTANCE> getClazz();
	InstanceBuilder<INSTANCE> setClazz(Class<?> aClass);
	
	Object[] getParameters();
	InstanceBuilder<INSTANCE> setParameters(Object[] parameters);
	
	Object getCopy();
	InstanceBuilder<INSTANCE> setCopy(Object copy);
}
