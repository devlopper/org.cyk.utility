package org.cyk.utility.__kernel__.representation;

import java.io.Serializable;
import java.util.Collection;
import java.util.logging.Level;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.log.LogMessages;
import org.cyk.utility.__kernel__.mapping.MapperSourceDestination;
import org.cyk.utility.__kernel__.mapping.MappingHelper;
import org.cyk.utility.__kernel__.mapping.MappingSourceBuilder;
import org.cyk.utility.__kernel__.persistence.query.CountQueryIdentifierGetter;
import org.cyk.utility.__kernel__.persistence.query.EntityCounter;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutorArguments;
import org.cyk.utility.__kernel__.persistence.query.QueryGetter;
import org.cyk.utility.__kernel__.representation.Arguments.Internal;
import org.cyk.utility.__kernel__.rest.ResponseBuilder;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.time.TimeHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

//@Path(EntityReader.PATH)
//@Tag(name = EntityReader.TAG)
public interface EntityReader {
	/*
	@POST
	@Path(PATH_READ_BY_POST)
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML })
	@Operation(description = "Read using post method")
	*/
	Response read(Arguments arguments);
	/*
	@GET
	@Path(PATH_READ_BY_GET)
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML })
	@Operation(description = "Read using get method")
	*/
	Response read(
			@QueryParam(PARAMETER_NAME_REPRESENTATION_ENTITY_CLASS_NAME)String representationEntityClassName
			,@QueryParam(PARAMETER_NAME_QUERY_IDENTIFIER) String queryIdentifier
			,@QueryParam(PARAMETER_NAME_FILTER_AS_JSON)String filterAsJson
			,@QueryParam(PARAMETER_NAME_FIRST_TUPLE_INDEX)Integer firstTupleIndex
			,@QueryParam(PARAMETER_NAME_NUMBER_OF_TUPLES)Integer numberOfTuples
			,@QueryParam(PARAMETER_NAME_COLLECTIONABLE)Boolean collectionable
			,@QueryParam(PARAMETER_NAME_QUERY_COUNTABLE)Boolean countable
		);
	
	/**/
	
	public abstract static class AbstractImpl extends AbstractExecutionImpl implements EntityReader,Serializable {
		
		@Override
		protected Internal instantiateInternal(Arguments arguments) {
			return new Arguments.Internal(arguments, EntityReader.class);
		}
		
		@Override
		protected QueryExecutorArguments instantiateQueryExecutorArguments(Arguments arguments) {
			QueryExecutorArguments queryExecutorArguments = super.instantiateQueryExecutorArguments(arguments);
			if(queryExecutorArguments.getIsResultProcessable() == null)
				queryExecutorArguments.setIsResultProcessable(Boolean.TRUE);
			if(queryExecutorArguments.getCollectionable() == null)
				queryExecutorArguments.setCollectionable(Boolean.TRUE);
			return queryExecutorArguments;
		}
		
		@Override
		protected void __execute__(Arguments arguments, Internal internal,QueryExecutorArguments queryExecutorArguments,LogMessages logMessages,ResponseBuilder.Arguments responseBuilderArguments) {
			logMessages.add("get");
			if(Boolean.TRUE.equals(queryExecutorArguments.getCollectionable())) {
				logMessages.add("many");
				Long t = System.currentTimeMillis();
				Collection<?> persistences = org.cyk.utility.__kernel__.persistence.query.EntityReader.getInstance().readMany(internal.persistenceEntityClass,queryExecutorArguments);				
				logMessages.add("persistence.read.duration:"+TimeHelper.formatDuration(System.currentTimeMillis()-t));
				if(arguments.getListener() != null)
					arguments.getListener().processPersistenceEntities(persistences);
				MapperSourceDestination.Arguments mapperSourceDestinationArguments = null;
				if(arguments.getMappingArguments() != null)
					mapperSourceDestinationArguments = MappingHelper.getDestination(arguments.getMappingArguments(), MapperSourceDestination.Arguments.class);				
				t = System.currentTimeMillis();
				Collection<?> representations =  CollectionHelper.isEmpty(persistences) ? null : MappingSourceBuilder.getInstance().build(persistences, internal.representationEntityClass
						,mapperSourceDestinationArguments);
				logMessages.add("persistences.mapping.duration:"+TimeHelper.formatDuration(System.currentTimeMillis()-t));
				if(arguments.getListener() != null)
					arguments.getListener().processRepresentationEntities(representations);
				Long xTotalCount = null;
				Boolean countable = Boolean.TRUE.equals(arguments.getCountable()) && queryExecutorArguments.getQuery() != null;
				if(Boolean.TRUE.equals(countable)) {
					logMessages.add("countable");
					String readQueryIdentifier = queryExecutorArguments.getQuery().getIdentifier();
					String countQueryIdentifier =  CountQueryIdentifierGetter.getInstance().get(queryExecutorArguments.getQuery().getIdentifier());
					if(StringHelper.isNotBlank(countQueryIdentifier)) {							
						queryExecutorArguments.setQuery(QueryGetter.getInstance().get(countQueryIdentifier));
						if(queryExecutorArguments.getQuery() != null) {
							t = System.currentTimeMillis();
							xTotalCount = EntityCounter.getInstance().count(internal.persistenceEntityClass,queryExecutorArguments);	
							logMessages.add(StringHelper.get(xTotalCount));
							logMessages.add("persistences.count.duration:"+TimeHelper.formatDuration(System.currentTimeMillis()-t));							
						}else
							logMessages.add("count query <<"+countQueryIdentifier+">> of read query <<"+readQueryIdentifier+">> not found");
					}else
						logMessages.add("count query identifier of <<"+queryExecutorArguments.getQuery().getIdentifier()+">> cannot be built");
				}
				if(CollectionHelper.isNotEmpty(representations))
					DataTransferObjectProcessor.getInstance().processRead((Class<Object>) internal.representationEntityClass,arguments, (Collection<Object>)representations);
				responseBuilderArguments.setEntities(representations).setXTotalCount(xTotalCount);
			}else {
				logMessages.add("one");
				Object persistence = org.cyk.utility.__kernel__.persistence.query.EntityReader.getInstance().readOne(internal.persistenceEntityClass,queryExecutorArguments);
				MapperSourceDestination.Arguments mapperSourceDestinationArguments = null;
				if(arguments.getMappingArguments() != null)
					mapperSourceDestinationArguments = MappingHelper.getDestination(arguments.getMappingArguments(), MapperSourceDestination.Arguments.class);				
				Object representation =  persistence == null ? null : MappingSourceBuilder.getInstance().build(persistence, internal.representationEntityClass
						,mapperSourceDestinationArguments);
				if(representation != null)
					DataTransferObjectProcessor.getInstance().processRead((Class<Object>) internal.representationEntityClass,arguments, representation);
				responseBuilderArguments.setEntity(representation);
			}
		}
		
		@Override
		protected Boolean getLoggable() {
			return LOGGABLE;
		}
		
		protected Level getLogLevel() {
			return LOG_LEVEL;
		}
		
		
		@Override
		public Response read(Arguments arguments) {
			return execute(arguments);
		}
		
		@Override
		public Response read(String representationEntityClassName,String queryIdentifier, String filterAsJson, Integer firstTupleIndex,Integer numberOfTuples
				, Boolean collectionable,Boolean countable) {
			Arguments arguments = new Arguments();
			arguments.setRepresentationEntityClassName(representationEntityClassName);
			arguments.setCountable(countable);
			arguments.setQueryExecutorArguments(new QueryExecutorArguments.Dto().setQueryIdentifier(queryIdentifier).setFirstTupleIndex(firstTupleIndex)
					.setNumberOfTuples(numberOfTuples).setCollectionable(collectionable));
			return read(arguments);
		}
		
		public static Boolean LOGGABLE = Boolean.FALSE;
		public static Level LOG_LEVEL = Level.FINEST;
	}
	
	/**/
	
	static EntityReader getInstance() {
		return Helper.getInstance(EntityReader.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
	String PATH = "/cyk/entity/reader";
	String PATH_READ_BY_POST = "post";
	String PATH_READ_BY_GET = "get";
	
	String TAG = "Generic Read Interface";
	
	String PARAMETER_NAME_REPRESENTATION_ENTITY_CLASS_NAME = "representationEntityClassName";
	String PARAMETER_NAME_QUERY_IDENTIFIER = "queryIdentifier";
	String PARAMETER_NAME_FILTER_AS_JSON = "filterAsJson";
	String PARAMETER_NAME_FIRST_TUPLE_INDEX = "firstTupleIndex";
	String PARAMETER_NAME_NUMBER_OF_TUPLES = "numberOfTuples";
	String PARAMETER_NAME_COLLECTIONABLE = "collectionable";
	String PARAMETER_NAME_QUERY_COUNTABLE = "countable";
}