package org.cyk.utility.representation.server;

import java.io.Serializable;
import java.util.Collection;
import java.util.logging.Level;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.log.LogMessages;
import org.cyk.utility.__kernel__.mapping.MapperSourceDestination;
import org.cyk.utility.__kernel__.mapping.MappingHelper;
import org.cyk.utility.__kernel__.mapping.MappingSourceBuilder;
import org.cyk.utility.__kernel__.rest.ResponseBuilder;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.time.TimeHelper;
import org.cyk.utility.persistence.query.CountQueryIdentifierGetter;
import org.cyk.utility.persistence.query.EntityCounter;
import org.cyk.utility.persistence.query.Query;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.query.QueryGetter;
import org.cyk.utility.representation.Arguments;
import org.cyk.utility.representation.DataTransferObjectProcessor;
import org.cyk.utility.representation.EntityReader;
import org.cyk.utility.representation.Arguments.Internal;

@ApplicationScoped
public class EntityReaderImpl extends EntityReader.AbstractImpl implements Serializable {
	
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
	
	@SuppressWarnings("unchecked")
	@Override
	protected void __execute__(Arguments arguments, Internal internal,QueryExecutorArguments queryExecutorArguments,LogMessages logMessages,ResponseBuilder.Arguments responseBuilderArguments) {
		logMessages.add("get");
		if(Boolean.TRUE.equals(queryExecutorArguments.getCollectionable())) {
			logMessages.add("many");
			Long t = System.currentTimeMillis();
			Collection<?> persistences = org.cyk.utility.persistence.query.EntityReader.getInstance().readMany(internal.persistenceEntityClass,queryExecutorArguments);				
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
					if(queryExecutorArguments.getQuery() == null)
						queryExecutorArguments.setQuery(new Query().setIdentifier(countQueryIdentifier));
					if(queryExecutorArguments.getQuery() != null) {
						//queryExecutorArguments.set__query__(null);
						//queryExecutorArguments.set__filter__(null);
						if(queryExecutorArguments.getFilterBackup() != null)
							queryExecutorArguments.setFilter(queryExecutorArguments.getFilterBackup());
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
			responseBuilderArguments.setIsCollection(Boolean.TRUE).setEntities(representations).setXTotalCount(xTotalCount);
		}else {
			logMessages.add("one");
			Object persistence = org.cyk.utility.persistence.query.EntityReader.getInstance().readOne(internal.persistenceEntityClass,queryExecutorArguments);
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