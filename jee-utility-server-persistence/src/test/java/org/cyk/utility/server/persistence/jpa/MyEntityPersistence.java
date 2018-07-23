package org.cyk.utility.server.persistence.jpa;

import java.util.Collection;

import org.cyk.utility.server.persistence.PersistenceEntity;

public interface MyEntityPersistence extends PersistenceEntity<MyEntity> {

	Collection<MyEntity> readByIntegerValue(Integer value);
	Long countByIntegerValue(Integer value);
}
