package org.cyk.utility.server.persistence.api;

import java.util.Collection;

import org.cyk.utility.__kernel__.persistence.QueryIdentifierBuilder;
import org.cyk.utility.server.persistence.PersistenceEntity;
import org.cyk.utility.server.persistence.entities.MyEntity2;
import org.cyk.utility.sql.builder.QueryStringBuilderSelect;

public interface MyEntity2Persistence extends PersistenceEntity<MyEntity2> {

	QueryStringBuilderSelect instanciateReadByIntegerValueQueryStringBuilder();
	Collection<MyEntity2> readByIntegerValue(Integer value);
	Long countByIntegerValue(Integer value);
	
	Long executeIncrementIntegerValue(Integer value);
	
	String READ_WHERE_CODE_OR_NAME_CONTAINS = QueryIdentifierBuilder.getInstance().build(MyEntity2.class,"readWhereBusinessIdentifierOrNameContains");
	String COUNT_WHERE_CODE_OR_NAME_CONTAINS = QueryIdentifierBuilder.getInstance().build(MyEntity2.class,"countWhereBusinessIdentifierOrNameContains");
}
