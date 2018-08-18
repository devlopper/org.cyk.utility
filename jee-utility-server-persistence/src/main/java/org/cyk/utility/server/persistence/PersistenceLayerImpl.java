package org.cyk.utility.server.persistence;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.__kernel__.object.dynamic.AbstractSingleton;
import org.cyk.utility.system.layer.SystemLayerPersistence;

@Singleton
public class PersistenceLayerImpl extends AbstractSingleton implements PersistenceLayer, Serializable {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public <ENTITY> PersistenceEntity<ENTITY> injectInterfaceClassFromEntityClass(Class<ENTITY> entityClass) {
		return __inject__(SystemLayerPersistence.class).injectInterfaceClassFromEntityClassName(entityClass,PersistenceEntity.class);
	}

}
