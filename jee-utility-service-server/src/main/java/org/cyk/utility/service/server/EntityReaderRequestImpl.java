package org.cyk.utility.service.server;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.mapping.Mapper;
import org.cyk.utility.persistence.query.EntityCounter;
import org.cyk.utility.persistence.query.EntityReader;
import org.cyk.utility.persistence.query.Query;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.query.QueryGetter;
import org.cyk.utility.rest.RequestExecutor;
import org.cyk.utility.rest.ResponseBuilder;
import org.cyk.utility.rest.ResponseHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class EntityReaderRequestImpl<PERSISTENCE_ENTITY,SERVICE_ENTITY> implements RequestExecutor.Request,Serializable {

	private Class<PERSISTENCE_ENTITY> persistenceEntityClass;
	private Class<SERVICE_ENTITY> serviceEntityClass;
	private QueryExecutorArguments queryExecutorArguments;
	private Mapper<PERSISTENCE_ENTITY,SERVICE_ENTITY> mapper;
	private Boolean countable;
	private String countQueryIdentifier;
	private ResponseBuilder.Arguments responseBuilderArguments;
	
	public EntityReaderRequestImpl(String queryIdentifier,Class<PERSISTENCE_ENTITY> persistenceEntityClass,Class<SERVICE_ENTITY> serviceEntityClass
			,Mapper<PERSISTENCE_ENTITY,SERVICE_ENTITY> mapper,Collection<String> projections,Collection<String> transientFieldsNames) {
		if(StringHelper.isNotBlank(queryIdentifier))
			query(queryIdentifier);
		this.persistenceEntityClass = persistenceEntityClass;
		this.serviceEntityClass = serviceEntityClass;
		this.mapper = mapper;
		if(CollectionHelper.isNotEmpty(projections))
			projections(projections);
		if(CollectionHelper.isNotEmpty(transientFieldsNames))
			transientFieldsNames(transientFieldsNames);
	}
	
	public QueryExecutorArguments getQueryExecutorArguments(Boolean instantiateIfNull) {
		if(queryExecutorArguments == null && Boolean.TRUE.equals(instantiateIfNull))
			queryExecutorArguments = new QueryExecutorArguments();
		return queryExecutorArguments;
	}
	
	public EntityReaderRequestImpl<PERSISTENCE_ENTITY,SERVICE_ENTITY> query(String identifier) {
		getQueryExecutorArguments(Boolean.TRUE).setQuery(new Query().setIdentifier(identifier));
		return this;
	}
	
	public EntityReaderRequestImpl<PERSISTENCE_ENTITY,SERVICE_ENTITY> projections(Collection<String> strings) {
		getQueryExecutorArguments(Boolean.TRUE).addProjectionsFromStrings(strings);
		return this;
	}
	
	public EntityReaderRequestImpl<PERSISTENCE_ENTITY,SERVICE_ENTITY> projections(String...strings) {
		getQueryExecutorArguments(Boolean.TRUE).addProjectionsFromStrings(strings);
		return this;
	}
	
	public EntityReaderRequestImpl<PERSISTENCE_ENTITY,SERVICE_ENTITY> transientFieldsNames(Collection<String> processableTransientFieldsNames) {
		getQueryExecutorArguments(Boolean.TRUE).addProcessableTransientFieldsNames(processableTransientFieldsNames);
		return this;
	}
	
	public EntityReaderRequestImpl<PERSISTENCE_ENTITY,SERVICE_ENTITY> transientFieldsNames(String...processableTransientFieldsNames) {
		getQueryExecutorArguments(Boolean.TRUE).addProcessableTransientFieldsNames(processableTransientFieldsNames);
		return this;
	}
	
	public EntityReaderRequestImpl<PERSISTENCE_ENTITY,SERVICE_ENTITY> page(Integer firstTupleIndex,Integer numberOfTuples) {
		getQueryExecutorArguments(Boolean.TRUE).setFirstTupleIndex(firstTupleIndex).setNumberOfTuples(numberOfTuples);
		return this;
	}
	
	public EntityReaderRequestImpl<PERSISTENCE_ENTITY,SERVICE_ENTITY> count(String queryIdentifier) {
		setCountable(StringHelper.isNotBlank(queryIdentifier));
		setCountQueryIdentifier(queryIdentifier);
		return this;
	}
	
	public ResponseBuilder.Arguments getResponseBuilderArguments(Boolean instantiateIfNull) {
		if(responseBuilderArguments == null && Boolean.TRUE.equals(instantiateIfNull))
			responseBuilderArguments = new ResponseBuilder.Arguments();
		return responseBuilderArguments;
	}
	
	@Override
	public ResponseBuilder.Arguments execute() {
		validatePreConditions();
		readPersistenceEntities();
		if(Boolean.TRUE.equals(countable))
			countPersistenceEntities();
		return responseBuilderArguments;
	}
	
	protected void validatePreConditions() {
		if(persistenceEntityClass == null)
			throw new RuntimeException("Persistence entity class is required");
		if(queryExecutorArguments == null)
			throw new RuntimeException("Query executor arguments is required");
		if(serviceEntityClass == null)
			throw new RuntimeException("Service entity class is required");
		if(mapper == null)
			throw new RuntimeException("Persistence entity to service entity mapper is required");
	}
	
	protected Collection<PERSISTENCE_ENTITY> getPersistenceEntities() {
		return EntityReader.getInstance().readMany(persistenceEntityClass,queryExecutorArguments);
	}
	
	protected Collection<SERVICE_ENTITY> mapPersistenceEntitiesToServiceEntities(Collection<PERSISTENCE_ENTITY> entities) {
		return mapper.mapSourcesToDestinations(entities);
	}
	
	protected void readPersistenceEntities() {
		Collection<PERSISTENCE_ENTITY> persistenceEntities = getPersistenceEntities();
		Collection<SERVICE_ENTITY> serviceEntities =  CollectionHelper.isEmpty(persistenceEntities) ? null : mapPersistenceEntitiesToServiceEntities(persistenceEntities);
		getResponseBuilderArguments(Boolean.TRUE).setEntities(serviceEntities);
	}
	
	protected void countPersistenceEntities() {
		//String readQueryIdentifier = queryExecutorArguments.getQuery().getIdentifier();
		if(StringHelper.isBlank(countQueryIdentifier))
			throw new RuntimeException(String.format("Count query identifier is required to count %s",queryExecutorArguments.getQuery().getTupleClass().getSimpleName()));
		//String countQueryIdentifier =  CountQueryIdentifierGetter.getInstance().get(queryExecutorArguments.getQuery().getIdentifier());
		//if(StringHelper.isBlank(countQueryIdentifier))
		//	return;
		queryExecutorArguments.setQuery(QueryGetter.getInstance().get(countQueryIdentifier));
		if(queryExecutorArguments.getQuery() == null)
			queryExecutorArguments.setQuery(new Query().setIdentifier(countQueryIdentifier));
		if(queryExecutorArguments.getFilterBackup() != null)
			queryExecutorArguments.setFilter(queryExecutorArguments.getFilterBackup());		
		getResponseBuilderArguments(Boolean.TRUE).setHeader(ResponseHelper.HEADER_X_TOTAL_COUNT, EntityCounter.getInstance().count(persistenceEntityClass,queryExecutorArguments));
	}
}