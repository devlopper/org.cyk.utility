package org.cyk.utility.__kernel__.representation;

import java.io.Serializable;
import java.util.logging.Level;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.log.LogMessages;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutorArguments;
import org.cyk.utility.__kernel__.representation.Arguments.Internal;
import org.cyk.utility.__kernel__.rest.ResponseBuilder;
import org.cyk.utility.__kernel__.value.Value;

@Path(EntityCounter.PATH)
//@Tag(name = EntityCounter.TAG)
public interface EntityCounter {

	@POST
	@Path(PATH_READ)
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML })
	//@Operation(description = "count")
	Response count(Arguments arguments);
	
	/**/
	
	public abstract static class AbstractImpl extends AbstractExecutionImpl implements EntityCounter,Serializable {
		
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
			Long count = org.cyk.utility.__kernel__.persistence.query.EntityCounter.getInstance().count(internal.persistenceEntityClass,queryExecutorArguments);
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
		
		/*
		@Override
		public Response count(Arguments arguments) {
			if(arguments == null)
				return ResponseBuilder.getInstance().buildRuntimeException(null,"arguments are required");
			Arguments.Internal internal;
			try {
				internal = new Arguments.Internal(arguments, EntityCounter.class);
				QueryExecutorArguments queryExecutorArguments = null;
				if(arguments.getQueryExecutorArguments() == null)
					queryExecutorArguments = new QueryExecutorArguments();
				else
					queryExecutorArguments = MappingHelper.getDestination(arguments.getQueryExecutorArguments(), QueryExecutorArguments.class);
				if(queryExecutorArguments.getIsResultProcessable() == null)
					queryExecutorArguments.setIsResultProcessable(Boolean.TRUE);
				Long count = org.cyk.utility.__kernel__.persistence.query.EntityCounter.getInstance().count(internal.persistenceEntityClass,queryExecutorArguments);				
				return ResponseBuilder.getInstance().build(new ResponseBuilder.Arguments().setEntity(count));
			} catch (Exception exception) {
				LogHelper.log(exception, getClass());
				return ResponseBuilder.getInstance().build(exception);
			}
		}
		*/
		
		public static Boolean LOGGABLE = Boolean.FALSE;
		public static Level LOG_LEVEL = Level.FINEST;
	}
	
	/**/
	
	static EntityCounter getInstance() {
		return Helper.getInstance(EntityCounter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
	String TAG = "Generic Count Interface";
	
	String PATH = "/cyk/entity/counter";
	String PATH_READ = "count";
}