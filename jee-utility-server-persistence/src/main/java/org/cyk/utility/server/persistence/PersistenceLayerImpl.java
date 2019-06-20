package org.cyk.utility.server.persistence;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.object.dynamic.AbstractSingleton;
import org.cyk.utility.system.layer.SystemLayerPersistence;

@ApplicationScoped
public class PersistenceLayerImpl extends AbstractSingleton implements PersistenceLayer, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public <ENTITY> Class<PersistenceEntity<ENTITY>> getInterfaceClassFromEntityClass(Class<ENTITY> entityClass) {
		@SuppressWarnings("unchecked")
		Class<PersistenceEntity<ENTITY>> aClass = (Class<PersistenceEntity<ENTITY>>) __inject__(SystemLayerPersistence.class).getInterfaceClassFromEntityClassName(entityClass);
		return aClass;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <ENTITY> PersistenceEntity<ENTITY> injectInterfaceClassFromEntityClass(Class<ENTITY> entityClass) {
		Class<PersistenceEntity<ENTITY>> aClass = getInterfaceClassFromEntityClass(entityClass);
		PersistenceEntity<ENTITY> persistence = null;
		if(aClass == null)
			System.err.println("No persistence interface found for persistence entity class "+entityClass);
		else
			persistence = (PersistenceEntity<ENTITY>) __inject__(SystemLayerPersistence.class).injectInterfaceClassFromEntityClassName(entityClass);
		return persistence;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <ENTITY> Class<PersistenceEntity<ENTITY>> getInterfaceClassFromEntity(ENTITY entity) {
		return entity == null ? null : getInterfaceClassFromEntityClass((Class<ENTITY>)entity.getClass());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <ENTITY> PersistenceEntity<ENTITY> injectInterfaceClassFromEntity(ENTITY entity) {
		return entity == null ? null : (PersistenceEntity<ENTITY>) injectInterfaceClassFromEntityClass(entity.getClass());
	}

}
