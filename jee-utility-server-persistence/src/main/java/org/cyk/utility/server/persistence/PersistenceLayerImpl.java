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
		Class<PersistenceEntity<ENTITY>> aClass = (Class<PersistenceEntity<ENTITY>>) __inject__(SystemLayerPersistence.class).getInterfaceClassFromEntityClassName(entityClass);
		PersistenceEntity<ENTITY> persistence = null;
		if(aClass == null)
			System.err.println("No persistence interface found for persistence entity class "+entityClass);
		else
			persistence = (PersistenceEntity<ENTITY>) __inject__(SystemLayerPersistence.class).injectInterfaceClassFromEntityClassName(entityClass);
		return persistence;
		//return __inject__(SystemLayerPersistence.class).injectInterfaceClassFromEntityClassName(entityClass,PersistenceEntity.class);
	}

}
