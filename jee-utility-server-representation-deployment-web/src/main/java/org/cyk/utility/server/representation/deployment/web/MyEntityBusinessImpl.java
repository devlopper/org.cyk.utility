package org.cyk.utility.server.representation.deployment.web;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@Singleton
public class MyEntityBusinessImpl extends AbstractBusinessEntityImpl<MyEntity,MyEntityPersistence> implements MyEntityBusiness,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Class<MyEntity> getPersistenceEntityClass() {
		return MyEntity.class;
	}
	
}
