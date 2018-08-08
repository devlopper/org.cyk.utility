package org.cyk.jee.utility.server.representation.application;

import org.cyk.utility.server.persistence.PersistenceEntity;

public interface MyEntityPersistence extends PersistenceEntity<MyEntity> {

	MyEntityPersistence executeIncrementLong2(Integer value);
	
}
