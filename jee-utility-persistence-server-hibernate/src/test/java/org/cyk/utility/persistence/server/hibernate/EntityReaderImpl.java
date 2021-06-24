package org.cyk.utility.persistence.server.hibernate;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.annotation.Test;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.query.QueryIdentifierBuilder;

@Test
public class EntityReaderImpl extends org.cyk.utility.persistence.server.query.EntityReaderImpl implements Serializable {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T readOne(Class<T> tupleClass, QueryExecutorArguments arguments) {
		if(Boolean.TRUE.equals(QueryIdentifierBuilder.builtFrom(arguments, DataAudited.class)))
			return (T) DataAuditedQuerier.getInstance().readOne(arguments);
		return super.readOne(tupleClass, arguments);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Collection<T> readMany(Class<T> tupleClass, QueryExecutorArguments arguments) {
		if(Boolean.TRUE.equals(QueryIdentifierBuilder.builtFrom(arguments, DataAudited.class)))
			return (Collection<T>) DataAuditedQuerier.getInstance().readMany(arguments);
		return super.readMany(tupleClass, arguments);
	}	
}