package org.cyk.utility.server.business.impl;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;
import org.cyk.utility.server.business.BusinessServiceProvider;
import org.cyk.utility.server.business.api.MyEntityBusiness;
import org.cyk.utility.server.persistence.api.MyEntityPersistence;
import org.cyk.utility.server.persistence.entities.MyEntity;

public class MyEntityBusinessImpl extends AbstractBusinessEntityImpl<MyEntity,MyEntityPersistence> implements MyEntityBusiness,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public BusinessServiceProvider<MyEntity> create(MyEntity myEntity, Properties properties) {
		myEntity.setTimestamp(System.currentTimeMillis());
		return super.create(myEntity, properties);
	}

	@Override
	protected Class<MyEntity> __getPersistenceEntityClass__() {
		return MyEntity.class;
	}
	
}
