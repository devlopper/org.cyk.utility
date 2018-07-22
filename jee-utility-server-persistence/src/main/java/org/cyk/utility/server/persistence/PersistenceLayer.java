package org.cyk.utility.server.persistence;

import org.cyk.utility.__kernel__.object.dynamic.Singleton;

public interface PersistenceLayer extends Singleton {

	<ENTITY> PersistenceEntity<ENTITY> injectInterfaceClassFromEntityClass(Class<ENTITY> entityClass);
	
}
