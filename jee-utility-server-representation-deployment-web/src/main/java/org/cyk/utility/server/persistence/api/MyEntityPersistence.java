package org.cyk.utility.server.persistence.api;

import org.cyk.utility.server.persistence.PersistenceEntity;
import org.cyk.utility.server.persistence.entities.MyEntity;

public interface MyEntityPersistence extends PersistenceEntity<MyEntity> {

	MyEntityPersistence executeIncrementLong2(Integer value);
	
}
