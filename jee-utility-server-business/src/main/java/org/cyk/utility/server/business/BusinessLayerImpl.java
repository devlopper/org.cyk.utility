package org.cyk.utility.server.business;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.object.dynamic.AbstractSingleton;
import org.cyk.utility.system.layer.SystemLayerBusiness;

@ApplicationScoped
public class BusinessLayerImpl extends AbstractSingleton implements BusinessLayer, Serializable {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public <ENTITY> Class<BusinessEntity<ENTITY>> getInterfaceClassFromPersistenceEntityClass(Class<ENTITY> persistenceEntityClass) {
		return (Class<BusinessEntity<ENTITY>>) __inject__(SystemLayerBusiness.class).getInterfaceClassFromPersistenceEntityClassName(persistenceEntityClass);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <ENTITY> BusinessEntity<ENTITY> injectInterfaceClassFromPersistenceEntityClass(Class<ENTITY> persistenceEntityClass) {
		Class<BusinessEntity<ENTITY>> aClass = getInterfaceClassFromPersistenceEntityClass(persistenceEntityClass);
		BusinessEntity<ENTITY> business = null;
		if(aClass == null)
			System.err.println("No business interface found for persistence entity class "+persistenceEntityClass);
		else
			business = __inject__(SystemLayerBusiness.class).injectInterfaceClassFromPersistenceEntityClassName(persistenceEntityClass,BusinessEntity.class);
		return business;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <ENTITY> Class<BusinessEntity<ENTITY>> getInterfaceClassFromPersistenceEntity(ENTITY persistenceEntity) {
		return persistenceEntity == null ? null : getInterfaceClassFromPersistenceEntityClass((Class<ENTITY>)persistenceEntity.getClass());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <ENTITY> BusinessEntity<ENTITY> injectInterfaceClassFromPersistenceEntity(ENTITY persistenceEntity) {
		return persistenceEntity == null ? null : (BusinessEntity<ENTITY>) injectInterfaceClassFromPersistenceEntityClass(persistenceEntity.getClass());
	}

}
