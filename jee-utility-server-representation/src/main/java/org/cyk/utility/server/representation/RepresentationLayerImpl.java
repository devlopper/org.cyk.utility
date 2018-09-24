package org.cyk.utility.server.representation;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.__kernel__.object.dynamic.AbstractSingleton;
import org.cyk.utility.system.layer.SystemLayerRepresentation;

@Singleton
public class RepresentationLayerImpl extends AbstractSingleton implements RepresentationLayer, Serializable {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public <DTO> Class<RepresentationEntity<?, DTO, ?>> getInterfaceClassFromEntityClass(Class<DTO> entityClass) {
		return (Class<RepresentationEntity<?,DTO,?>>) __inject__(SystemLayerRepresentation.class).getInterfaceClassFromEntityClassName(entityClass);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <DTO> RepresentationEntity<?, DTO, ?> injectInterfaceClassFromEntityClass(Class<DTO> entityClass) {
		Class<RepresentationEntity<?,DTO,?>> aClass = getInterfaceClassFromEntityClass(entityClass);
		RepresentationEntity<?,DTO,?> representation = null;
		if(aClass == null)
			System.err.println("No representation interface found for representation entity class "+entityClass);
		else
			representation = (RepresentationEntity<?,DTO,?>) __inject__(SystemLayerRepresentation.class).injectInterfaceClassFromEntityClassName(entityClass);
		return representation;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <DTO> Class<RepresentationEntity<?, DTO, ?>> getInterfaceClassFromEntity(DTO entity) {
		return entity == null ? null : getInterfaceClassFromEntityClass((Class<DTO>)entity.getClass());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <DTO> RepresentationEntity<?, DTO, ?> injectInterfaceClassFromEntity(DTO entity) {
		return entity == null ? null : (RepresentationEntity<?, DTO, ?>) injectInterfaceClassFromEntityClass(entity.getClass());
	}

}
