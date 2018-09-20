package org.cyk.utility.system.layer;

public interface SystemLayerBusiness extends SystemLayer {

	String getInterfaceNameFromPersistenceEntityClassName(String persistenceEntityClassName);
	Class<?> getInterfaceClassFromPersistenceEntityClassName(String persistenceEntityClassName);
	Class<?> getInterfaceClassFromPersistenceEntityClassName(Class<?> persistenceEntityClass);
	
	<T> T injectInterfaceClassFromPersistenceEntityClassName(Class<?> persistenceEntityClass,Class<T> type);
	Object injectInterfaceClassFromPersistenceEntityClassName(Class<?> persistenceEntityClass);
	
}
