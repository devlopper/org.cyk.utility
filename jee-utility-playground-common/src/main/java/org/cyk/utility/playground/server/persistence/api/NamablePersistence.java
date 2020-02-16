package org.cyk.utility.playground.server.persistence.api;

import org.cyk.utility.__kernel__.persistence.query.QueryIdentifierBuilder;
import org.cyk.utility.playground.server.persistence.entities.Namable;
import org.cyk.utility.server.persistence.PersistenceEntity;

public interface NamablePersistence extends PersistenceEntity<Namable> {

	String READ_WHERE_CODE_OR_NAME_CONTAINS = QueryIdentifierBuilder.getInstance().build(Namable.class,FIELD_NAME_READ_WHERE_CODE_OR_NAME_CONTAINS);
	String COUNT_WHERE_CODE_OR_NAME_CONTAINS = QueryIdentifierBuilder.getInstance().build(Namable.class,FIELD_NAME_COUNT_WHERE_CODE_OR_NAME_CONTAINS);
	
}
