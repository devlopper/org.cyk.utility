package org.cyk.utility.server.persistence.api;

import java.util.Collection;

import org.cyk.utility.server.persistence.PersistenceEntityIdentifiedByString;
import org.cyk.utility.server.persistence.entities.MyEntity;
import org.cyk.utility.sql.builder.QueryStringBuilderSelect;

public interface MyEntityPersistence extends PersistenceEntityIdentifiedByString<MyEntity> {

	QueryStringBuilderSelect instanciateReadByIntegerValueQueryStringBuilder();
	Collection<MyEntity> readByIntegerValue(Integer value);
	Long countByIntegerValue(Integer value);
	
	Long executeIncrementIntegerValue(Integer value);
}
