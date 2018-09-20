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
	public <ENTITY> RepresentationEntity<ENTITY, ?, ?> injectInterfaceClassFromEntityClass(Class<ENTITY> entityClass) {
		return __inject__(SystemLayerRepresentation.class).injectInterfaceClassFromEntityClassName(entityClass,RepresentationEntity.class);
	}

}
