package org.cyk.utility.server.representation.deployment.web;

import org.cyk.utility.server.persistence.PersistenceEntity;

public interface MyEntityPersistence extends PersistenceEntity<MyEntity> {

	MyEntityPersistence executeIncrementLong2(Integer value);
	
}
