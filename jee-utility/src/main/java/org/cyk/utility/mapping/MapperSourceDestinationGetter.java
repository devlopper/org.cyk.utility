package org.cyk.utility.mapping;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

@SuppressWarnings("rawtypes") @Deprecated
public interface MapperSourceDestinationGetter extends FunctionWithPropertiesAsInput<MapperSourceDestination> {

	Class<?> getSourceClass();
	MapperSourceDestinationGetter setSourceClass(Class<?> sourceClass);
	
	Object getSource();
	MapperSourceDestinationGetter setSource(Object source);
	
	Class<?> getDestinationClass();
	MapperSourceDestinationGetter setDestinationClass(Class<?> destinationClass);
	
	Object getDestination();
	MapperSourceDestinationGetter setDestination(Object destintation);
}
