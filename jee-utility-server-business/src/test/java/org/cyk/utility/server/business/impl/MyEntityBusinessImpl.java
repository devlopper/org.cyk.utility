package org.cyk.utility.server.business.impl;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;
import org.cyk.utility.server.business.BusinessFunctionCreator;
import org.cyk.utility.server.business.api.MyEntityBusiness;
import org.cyk.utility.server.persistence.api.MyEntityPersistence;
import org.cyk.utility.server.persistence.entities.MyEntity;

public class MyEntityBusinessImpl extends AbstractBusinessEntityImpl<MyEntity,MyEntityPersistence> implements MyEntityBusiness,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenExecuteCreateBefore__(MyEntity myEntity, Properties properties,BusinessFunctionCreator function) {
		super.__listenExecuteCreateBefore__(myEntity, properties, function);
		myEntity.setTimestamp(System.currentTimeMillis());
	}

	@Override
	protected Class<MyEntity> __getPersistenceEntityClass__() {
		return MyEntity.class;
	}
	
}
