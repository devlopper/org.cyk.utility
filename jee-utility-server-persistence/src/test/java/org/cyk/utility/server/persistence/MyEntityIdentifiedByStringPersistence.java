package org.cyk.utility.server.persistence;

import java.util.Collection;

import org.cyk.utility.server.persistence.PersistenceEntity;
import org.cyk.utility.sql.builder.QueryStringBuilderSelect;

public interface MyEntityIdentifiedByStringPersistence extends PersistenceEntity<MyEntityIdentifiedByString> {

	QueryStringBuilderSelect instanciateReadByIntegerValueQueryStringBuilder();
	Collection<MyEntityIdentifiedByString> readByIntegerValue(Integer value);
	Long countByIntegerValue(Integer value);
	
	Long executeIncrementIntegerValue(Integer value);
}
