package org.cyk.utility.playground.server.persistence.impl;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.playground.server.persistence.api.MyEntityPersistence;
import org.cyk.utility.playground.server.persistence.entities.MyEntity;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@Singleton
public class MyEntityPersistenceImpl extends AbstractPersistenceEntityImpl<MyEntity> implements MyEntityPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public MyEntityPersistence executeIncrementLong2(Integer value) {
		
		return this;
	}
	
}
