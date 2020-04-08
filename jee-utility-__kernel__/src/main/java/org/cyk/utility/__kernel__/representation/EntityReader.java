package org.cyk.utility.__kernel__.representation;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.mapping.MapperSourceDestination;
import org.cyk.utility.__kernel__.mapping.MappingHelper;
import org.cyk.utility.__kernel__.mapping.MappingSourceBuilder;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutor;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutorArguments;
import org.cyk.utility.__kernel__.persistence.query.QueryGetter;
import org.cyk.utility.__kernel__.rest.ResponseBuilder;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Path(EntityReader.PATH)
@Api
public interface EntityReader {

	@POST
	@Path(PATH_READ)
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML })
	@ApiOperation(value = "read",tags = {"read"})
	Response read(Arguments arguments);
	
	/**/
	
	public abstract static class AbstractImpl extends AbstractObject implements EntityReader,Serializable {
		
		@Override
		public Response read(Arguments arguments) {
			if(arguments == null)
				return ResponseBuilder.getInstance().buildRuntimeException(null,"arguments are required");
			Arguments.Internal internal;
			try {
				internal = new Arguments.Internal(arguments, EntityReader.class);
				QueryExecutorArguments queryExecutorArguments = null;
				if(arguments.getQueryExecutorArguments() == null)
					queryExecutorArguments = new QueryExecutorArguments();
				else
					queryExecutorArguments = MappingHelper.getDestination(arguments.getQueryExecutorArguments(), QueryExecutorArguments.class);
				if(queryExecutorArguments.getIsResultProcessable() == null)
					queryExecutorArguments.setIsResultProcessable(Boolean.TRUE);
				Collection<?> persistences = org.cyk.utility.__kernel__.persistence.query.EntityReader.getInstance().readMany(internal.persistenceEntityClass,queryExecutorArguments);				
				MapperSourceDestination.Arguments mapperSourceDestinationArguments = null;
				if(arguments.getMappingArguments() != null)
					mapperSourceDestinationArguments = MappingHelper.getDestination(arguments.getMappingArguments(), MapperSourceDestination.Arguments.class);				
				Collection<?> representations =  CollectionHelper.isEmpty(persistences) ? null : MappingSourceBuilder.getInstance().build(persistences, internal.representationEntityClass
						,mapperSourceDestinationArguments);
				
				Long xTotalCount = null;
				if(Boolean.TRUE.equals(arguments.getCountable())) {			
					String countQueryIdentifier = queryExecutorArguments.getQuery() == null ? null : queryExecutorArguments.getQuery().getIdentifier();
					if(StringHelper.isNotBlank(countQueryIdentifier)) {
						if(StringUtils.contains(countQueryIdentifier, ".read")) {
							countQueryIdentifier = StringUtils.replaceOnce(countQueryIdentifier, ".read", ".count");
							queryExecutorArguments.setQuery(QueryGetter.getInstance().get(countQueryIdentifier));
							if(queryExecutorArguments.getQuery() != null) {
								xTotalCount = QueryExecutor.getInstance().executeCount(queryExecutorArguments);
							}
						}					
					}
				}				
				return ResponseBuilder.getInstance().build(new ResponseBuilder.Arguments().setEntities(representations).setXTotalCount(xTotalCount));
			} catch (Exception exception) {
				LogHelper.log(exception, getClass());
				return ResponseBuilder.getInstance().build(exception);
			}
		}
	}
	
	/**/
	
	static EntityReader getInstance() {
		return Helper.getInstance(EntityReader.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
	
	String PATH = "/cyk/entity/reader";
	String PATH_READ = "read";
}