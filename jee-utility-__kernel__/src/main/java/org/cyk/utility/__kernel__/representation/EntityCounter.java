package org.cyk.utility.__kernel__.representation;

import java.io.Serializable;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.mapping.MappingHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutorArguments;
import org.cyk.utility.__kernel__.rest.ResponseBuilder;
import org.cyk.utility.__kernel__.value.Value;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Path(EntityCounter.PATH)
@Api
public interface EntityCounter {

	@POST
	@Path(PATH_READ)
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML })
	@ApiOperation(value = "count",tags = {"count"})
	Response count(Arguments arguments);
	
	/**/
	
	public abstract static class AbstractImpl extends AbstractObject implements EntityCounter,Serializable {
		
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
	}
	
	/**/
	
	static EntityCounter getInstance() {
		return Helper.getInstance(EntityCounter.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
	
	String PATH = "/cyk/entity/counter";
	String PATH_READ = "count";
}