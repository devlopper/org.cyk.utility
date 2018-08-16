package org.cyk.utility.server.representation;

import org.cyk.utility.__kernel__.object.dynamic.Singleton;

public interface RepresentationLayer extends Singleton {

	<ENTITY> RepresentationEntity<ENTITY,?,?> injectInterfaceClassFromEntityClass(Class<ENTITY> entityClass);
	
}
