package org.cyk.utility.__kernel__.representation;

import java.io.Serializable;
import java.util.logging.Level;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.log.LogMessages;
import org.cyk.utility.__kernel__.mapping.MappingHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutorArguments;
import org.cyk.utility.__kernel__.rest.ResponseBuilder;

public abstract class AbstractExecutionImpl extends AbstractObject implements Serializable {

	public Response execute(Arguments arguments) {
		if(arguments == null)
			return ResponseBuilder.getInstance().buildRuntimeException(null,"arguments are required");
		Arguments.Internal internal = instantiateInternal(arguments);
		LogMessages logMessages = new LogMessages().setKlass(getClass());
		if(Boolean.TRUE.equals(arguments.getLoggableAsInfo()))
			logMessages.setLoggable(Boolean.TRUE).setLevel(Level.INFO);
		try {
			ResponseBuilder.Arguments responseBuilderArguments = new ResponseBuilder.Arguments().setProcessingStartTime(System.currentTimeMillis());
			internal = new Arguments.Internal(arguments, EntityReader.class);
			QueryExecutorArguments queryExecutorArguments = instantiateQueryExecutorArguments(arguments);	
			__execute__(arguments,internal,queryExecutorArguments,logMessages,responseBuilderArguments);
			Response response = buildResponse(responseBuilderArguments);
			logMessages.add("SUCCESS");
			logMessages.add(responseBuilderArguments.getProcessingDuration()+"");
			return response;
		} catch (Exception exception) {
			logMessages.add("ERROR!!!");
			LogHelper.log(exception, getClass());
			return ResponseBuilder.getInstance().build(exception);
		} finally {
			if(logMessages.getLoggable() == null)
				logMessages.setLoggable(getLoggable());
			if(logMessages.getLevel() == null)
				logMessages.setLevel(getLogLevel());
			LogHelper.log(logMessages,getClass());
		}
	}
	protected abstract Arguments.Internal instantiateInternal(Arguments arguments);
	protected QueryExecutorArguments instantiateQueryExecutorArguments(Arguments arguments) {
		QueryExecutorArguments queryExecutorArguments = null;
		if(arguments.getQueryExecutorArguments() == null)
			queryExecutorArguments = new QueryExecutorArguments();
		else {
			arguments.getQueryExecutorArguments().setLoggableAsInfo(arguments.getLoggableAsInfo());
			queryExecutorArguments = MappingHelper.getDestination(arguments.getQueryExecutorArguments(), QueryExecutorArguments.class);
		}
		/*if(queryExecutorArguments.getIsResultProcessable() == null)
			queryExecutorArguments.setIsResultProcessable(Boolean.TRUE);
		if(queryExecutorArguments.getCollectionable() == null)
			queryExecutorArguments.setCollectionable(Boolean.TRUE);
		*/
		return queryExecutorArguments;
	}
	protected abstract void __execute__(Arguments arguments,Arguments.Internal internal,QueryExecutorArguments queryExecutorArguments,LogMessages logMessages,ResponseBuilder.Arguments responseBuilderArguments);
	protected Response buildResponse(ResponseBuilder.Arguments responseBuilderArguments) {
		return ResponseBuilder.getInstance().build(responseBuilderArguments);
	}
	protected abstract Boolean getLoggable();
	protected abstract Level getLogLevel();
}