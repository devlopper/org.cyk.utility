package org.cyk.utility.server.business;

import org.cyk.utility.__kernel__.object.dynamic.Singleton;

public interface BusinessLayer extends Singleton {

	<ENTITY> BusinessEntity<ENTITY> injectInterfaceClassFromEntityClass(Class<ENTITY> entityClass);
	
}
