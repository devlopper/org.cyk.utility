package org.cyk.utility.server.business;

import org.cyk.utility.__kernel__.object.dynamic.Singleton;

public interface BusinessLayer extends Singleton {

	<ENTITY> Class<BusinessEntity<ENTITY>> getInterfaceClassFromPersistenceEntityClass(Class<ENTITY> persistenceEntityClass);
	<ENTITY> BusinessEntity<ENTITY> injectInterfaceClassFromPersistenceEntityClass(Class<ENTITY> persistenceEntityClass);
	
	<ENTITY> Class<BusinessEntity<ENTITY>> getInterfaceClassFromPersistenceEntity(ENTITY persistenceEntity);
	<ENTITY> BusinessEntity<ENTITY> injectInterfaceClassFromPersistenceEntity(ENTITY persistenceEntity);
}
