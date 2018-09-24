package org.cyk.utility.server.persistence;

import org.cyk.utility.__kernel__.object.dynamic.Singleton;

public interface PersistenceLayer extends Singleton {

	<ENTITY> Class<PersistenceEntity<ENTITY>> getInterfaceClassFromEntityClass(Class<ENTITY> entityClass);
	<ENTITY> PersistenceEntity<ENTITY> injectInterfaceClassFromEntityClass(Class<ENTITY> entityClass);
	
	<ENTITY> Class<PersistenceEntity<ENTITY>> getInterfaceClassFromEntity(ENTITY entity);
	<ENTITY> PersistenceEntity<ENTITY> injectInterfaceClassFromEntity(ENTITY entity);
	
}
