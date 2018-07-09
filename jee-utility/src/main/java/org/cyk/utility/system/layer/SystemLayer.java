package org.cyk.utility.system.layer;

import org.cyk.utility.__kernel__.object.dynamic.Singleton;

public interface SystemLayer extends Singleton {

	SystemLayer setEntityPackageNameToken(String token);
	String getEntityPackageNameToken();
	
	SystemLayer setInterfacePackageNameToken(String token);
	String getInterfacePackageNameToken();
	
	SystemLayer setInterfaceNameSuffix(String suffix);
	String getInterfaceNameSuffix();
	
	String getInterfaceNameFromEntityClassName(String entityClassName);
	Class<?> getInterfaceClassFromEntityClassName(String entityClassName);
	Class<?> getInterfaceClassFromEntityClassName(Class<?> entityClass);
}
