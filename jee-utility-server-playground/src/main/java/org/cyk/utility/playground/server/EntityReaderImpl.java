package org.cyk.utility.playground.server;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.persistence.query.EntityReader;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutorArguments;
import org.cyk.utility.playground.server.persistence.api.NamableQuerier;

@System
public class EntityReaderImpl extends EntityReader.AbstractImpl implements Serializable {

	@SuppressWarnings("unchecked")
	@Override
	public <T> Collection<T> readMany(Class<T> resultClass, QueryExecutorArguments arguments) {
		Collection<T> result;
		if(arguments != null && arguments.getQuery() != null && NamableQuerier.QUERY_IDENTIFIER_READ_VIEW_01.equals(arguments.getQuery().getIdentifier()))
			result = (Collection<T>) NamableQuerier.getInstance().readMany(arguments);
		else
			result = super.readMany(resultClass, arguments);
		return result;
	}	
}