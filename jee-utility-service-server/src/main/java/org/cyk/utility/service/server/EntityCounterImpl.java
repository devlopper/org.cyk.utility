package org.cyk.utility.service.server;

import java.io.Serializable;
import java.util.logging.Level;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.log.LogMessages;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.rest.ResponseBuilder;
import org.cyk.utility.service.Arguments;
import org.cyk.utility.service.EntityCounter;

@ApplicationScoped
public class EntityCounterImpl extends AbstractExecutionImpl implements EntityCounter,Serializable {

	@Override
	protected QueryExecutorArguments instantiateQueryExecutorArguments(Arguments arguments) {
		QueryExecutorArguments queryExecutorArguments = super.instantiateQueryExecutorArguments(arguments);
		if(queryExecutorArguments.getIsResultProcessable() == null)
			queryExecutorArguments.setIsResultProcessable(Boolean.TRUE);
		return queryExecutorArguments;
	}
	
	@Override
	protected void __execute__(Arguments arguments,QueryExecutorArguments queryExecutorArguments, LogMessages logMessages,ResponseBuilder.Arguments responseBuilderArguments) {
		Long count = org.cyk.utility.persistence.query.EntityCounter.getInstance().count(arguments.getPersistenceEntityClass(),queryExecutorArguments);
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
		//arguments.setRepresentationEntityClassName(representationEntityClassName);
		arguments.setQueryExecutorArguments(new QueryExecutorArguments.Dto().setQueryIdentifier(queryIdentifier));
		return count(arguments);
	}
	
	public static Boolean LOGGABLE = Boolean.FALSE;
	public static Level LOG_LEVEL = Level.FINEST;	
}