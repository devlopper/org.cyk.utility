package org.cyk.utility.system.layer;

import org.cyk.utility.__kernel__.object.dynamic.Singleton;

public interface SystemLayer extends Singleton {

	String getInterfaceNameFromEntityClassName(String entityClassName);
	Class<?> getInterfaceClassFromEntityClassName(String entityClassName);
	Class<?> getInterfaceClassFromEntityClassName(Class<?> entityClass);
	
	<T> T injectInterfaceClassFromEntityClassName(Class<?> entityClass,Class<T> type);
	Object injectInterfaceClassFromEntityClassName(Class<?> entityClass);
	
	SystemLayer setEntityLayer(SystemSubLayerEntity systemLayerSubLayerEntity);
	SystemSubLayerEntity getEntityLayer();
	
	SystemLayer setInterfaceLayer(SystemSubLayerInterface systemLayerSubLayerInterface);
	SystemSubLayerInterface getInterfaceLayer();
	
	SystemLayer setImplementationLayer(SystemSubLayerImplementation systemLayerSubLayerImplementation);
	SystemSubLayerImplementation getImplementationLayer();
}
