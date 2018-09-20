package org.cyk.utility.server.representation;

import org.cyk.utility.__kernel__.object.dynamic.Singleton;

public interface RepresentationLayer extends Singleton {

	<DTO> RepresentationEntity<?,DTO,?> injectInterfaceClassFromEntityClass(Class<DTO> entityClass);
	
}
