package org.cyk.utility.playground.server;

import java.io.Serializable;

import org.cyk.utility.__kernel__.persistence.query.EntityCounter;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutorArguments;
import org.cyk.utility.playground.server.persistence.api.NamableQuerier;

@System
public class EntityCounterImpl extends EntityCounter.AbstractImpl implements Serializable {

	@Override
	public <T> Long count(Class<T> tupleClass, QueryExecutorArguments arguments) {
		Long count;
		if(arguments != null && arguments.getQuery() != null && NamableQuerier.QUERY_IDENTIFIER_COUNT_VIEW_01.equals(arguments.getQuery().getIdentifier()))
			count = NamableQuerier.getInstance().count(arguments);
		else
			count = super.count(tupleClass, arguments);
		return count;
	}
}