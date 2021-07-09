package org.cyk.utility.representation.server;

import java.io.Serializable;
import java.util.logging.Level;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.log.LogMessages;
import org.cyk.utility.__kernel__.rest.ResponseBuilder;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.representation.Arguments;
import org.cyk.utility.representation.EntityCounter;
import org.cyk.utility.representation.Arguments.Internal;

@ApplicationScoped
public class EntityCounterImpl extends org.cyk.utility.representation.EntityCounter.AbstractImpl implements Serializable {

	@Override
	protected Internal instantiateInternal(Arguments arguments) {
		return new Arguments.Internal(arguments, EntityCounter.class);
	}
	
	@Override
	protected QueryExecutorArguments instantiateQueryExecutorArguments(Arguments arguments) {
		QueryExecutorArguments queryExecutorArguments = super.instantiateQueryExecutorArguments(arguments);
		if(queryExecutorArguments.getIsResultProcessable() == null)
			queryExecutorArguments.setIsResultProcessable(Boolean.TRUE);
		return queryExecutorArguments;
	}
	
	@Override
	protected void __execute__(Arguments arguments, Internal internal,QueryExecutorArguments queryExecutorArguments, LogMessages logMessages,ResponseBuilder.Arguments responseBuilderArguments) {
		Long count = org.cyk.utility.persistence.query.EntityCounter.getInstance().count(internal.persistenceEntityClass,queryExecutorArguments);
		responseBuilderArguments.setEntity(count);
	}
	
	@Override
	protected Boolean getLoggable() {
		return LOGGABLE;
	}
	
	@Override
	protected Level getLogLevel() {
		return LOG_LEVEL;
	}
	
	@Override
	public Response count(Arguments arguments) {
		return execute(arguments);
	}
	
	@Override
	public Response count(String representationEntityClassName,String queryIdentifier, String filterAsJson) {
		Arguments arguments = new Arguments();
		arguments.setRepresentationEntityClassName(representationEntityClassName);
		arguments.setQueryExecutorArguments(new QueryExecutorArguments.Dto().setQueryIdentifier(queryIdentifier));
		return count(arguments);
	}
	
	public static Boolean LOGGABLE = Boolean.FALSE;
	public static Level LOG_LEVEL = Level.FINEST;	
}