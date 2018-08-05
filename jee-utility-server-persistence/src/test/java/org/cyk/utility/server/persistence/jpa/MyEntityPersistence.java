package org.cyk.utility.server.persistence.jpa;

import java.util.Collection;

import org.cyk.utility.server.persistence.PersistenceEntity;
import org.cyk.utility.sql.builder.QueryStringBuilderSelect;

public interface MyEntityPersistence extends PersistenceEntity<MyEntity> {

	QueryStringBuilderSelect instanciateReadByIntegerValueQueryStringBuilder();
	Collection<MyEntity> readByIntegerValue(Integer value);
	Long countByIntegerValue(Integer value);
	
	Long executeIncrementIntegerValue(Integer value);
}
