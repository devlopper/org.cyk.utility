package org.cyk.utility.server.representation;

import org.cyk.utility.__kernel__.object.dynamic.Singleton;

public interface RepresentationLayer extends Singleton {

	<DTO> Class<RepresentationEntity<?,DTO,?>> getInterfaceClassFromEntityClass(Class<DTO> entityClass);
	<DTO> RepresentationEntity<?,DTO,?> injectInterfaceClassFromEntityClass(Class<DTO> entityClass);
	
	<DTO> Class<RepresentationEntity<?,DTO,?>> getInterfaceClassFromEntity(DTO entity);
	<DTO> RepresentationEntity<?,DTO,?> injectInterfaceClassFromEntity(DTO entity);

}
