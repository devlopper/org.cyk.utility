package org.cyk.utility.__kernel__.representation;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.mapping.MapperSourceDestination;
import org.cyk.utility.__kernel__.mapping.MappingHelper;
import org.cyk.utility.__kernel__.mapping.MappingSourceBuilder;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutorArguments;
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
			if(StringHelper.isBlank(arguments.getRepresentationEntityClassName()))
				return ResponseBuilder.getInstance().buildRuntimeException(null,"representation entity class name is required");
			Class<?> representationEntityClass = ClassHelper.getByName(arguments.getRepresentationEntityClassName());
			if(representationEntityClass == null)
				return ResponseBuilder.getInstance().buildRuntimeException(null,"representation entity class named "+arguments.getRepresentationEntityClassName()+" not found");			
			Class<?> persistenceEntityClass = null;
			String persistenceEntityClassName = arguments.getPersistenceEntityClassName();
			if(StringHelper.isNotBlank(persistenceEntityClassName))
				persistenceEntityClass = ClassHelper.getByName(persistenceEntityClassName);
			if(persistenceEntityClass == null)
				persistenceEntityClass = PersistenceEntityClassGetter.getInstance().get(representationEntityClass);
			if(persistenceEntityClass == null)
				return ResponseBuilder.getInstance().buildRuntimeException(null,"persistence entity class of representation entity class "+representationEntityClass+" not found");
			QueryExecutorArguments queryExecutorArguments = null;
			if(arguments.getQueryExecutorArguments() != null)
				queryExecutorArguments = MappingHelper.getDestination(arguments.getQueryExecutorArguments(), QueryExecutorArguments.class);
			Collection<?> persistences;
			try {
				persistences = org.cyk.utility.__kernel__.persistence.query.EntityReader.getInstance().readMany(persistenceEntityClass,queryExecutorArguments);
			} catch (Exception exception) {
				return ResponseBuilder.getInstance().build(exception);
			}
			MapperSourceDestination.Arguments mapperSourceDestinationArguments = null;
			if(arguments.getMappingArguments() != null)
				mapperSourceDestinationArguments = MappingHelper.getDestination(arguments.getMappingArguments(), MapperSourceDestination.Arguments.class);
			Collection<?> representations =  CollectionHelper.isEmpty(persistences) ? null : MappingSourceBuilder.getInstance().build(persistences, representationEntityClass
					,mapperSourceDestinationArguments);
			return ResponseBuilder.getInstance().build(new ResponseBuilder.Arguments().setEntities(representations));
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