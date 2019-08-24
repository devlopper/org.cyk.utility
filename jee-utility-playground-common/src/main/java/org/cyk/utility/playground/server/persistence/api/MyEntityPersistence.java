package org.cyk.utility.playground.server.persistence.api;

import org.cyk.utility.playground.server.persistence.entities.MyEntity;
import org.cyk.utility.server.persistence.PersistenceEntity;
import org.cyk.utility.playground.server.persistence.api.MyEntityPersistence;

public interface MyEntityPersistence extends PersistenceEntity<MyEntity> {

	MyEntityPersistence executeIncrementLong2(Integer value);
	
}
