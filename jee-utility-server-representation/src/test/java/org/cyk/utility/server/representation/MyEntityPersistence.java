package org.cyk.utility.server.representation;

import org.cyk.utility.server.persistence.PersistenceEntity;

public interface MyEntityPersistence extends PersistenceEntity<MyEntity> {

	MyEntityPersistence executeIncrementLong2(Integer value);
	
}
