package org.cyk.utility.server.business;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.__kernel__.object.dynamic.AbstractSingleton;
import org.cyk.utility.system.layer.SystemLayerBusiness;

@Singleton
public class BusinessLayerImpl extends AbstractSingleton implements BusinessLayer, Serializable {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public <ENTITY> BusinessEntity<ENTITY> injectInterfaceClassFromPersistenceEntityClass(Class<ENTITY> persistenceEntityClass) {
		return __inject__(SystemLayerBusiness.class).injectInterfaceClassFromEntityClassName(persistenceEntityClass,BusinessEntity.class);
	}

}
