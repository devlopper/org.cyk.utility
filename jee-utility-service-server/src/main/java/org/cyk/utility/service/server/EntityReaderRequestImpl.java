package org.cyk.utility.service.server;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.mapping.Mapper;
import org.cyk.utility.persistence.SpecificPersistence;
import org.cyk.utility.persistence.query.EntityCounter;
import org.cyk.utility.persistence.query.EntityReader;
import org.cyk.utility.persistence.query.Query;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.query.QueryGetter;
import org.cyk.utility.persistence.server.SpecificPersistenceGetter;
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
	private SpecificPersistence<?> persistence;
	private QueryExecutorArguments queryExecutorArguments;
	private Mapper<PERSISTENCE_ENTITY,SERVICE_ENTITY> mapper;
	private Boolean countable;
	private String countQueryIdentifier;	
	private ResponseBuilder.Arguments responseBuilderArguments;
	
	@SuppressWarnings("unchecked")
	public EntityReaderRequestImpl(Class<SERVICE_ENTITY> serviceEntityClass) {
		this.serviceEntityClass = serviceEntityClass;
		this.persistenceEntityClass = (Class<PERSISTENCE_ENTITY>) DependencyInjection.inject(PersistenceEntityClassGetter.class).get(this.serviceEntityClass);
		if(persistenceEntityClass != null)
			persistence = DependencyInjection.inject(SpecificPersistenceGetter.class).get(persistenceEntityClass);
		if(serviceEntityClass != null && persistenceEntityClass != null)
			mapper = DependencyInjection.inject(MapperGetter.class).get(persistenceEntityClass, serviceEntityClass);
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
	
	public EntityReaderRequestImpl<PERSISTENCE_ENTITY,SERVICE_ENTITY> filter(String string) {
		if(StringHelper.isNotBlank(string))
			getQueryExecutorArguments(Boolean.TRUE).addFilterFieldsValues(persistence.getFilterAsStringParameterName(),string);
		return this;
	}
	
	public EntityReaderRequestImpl<PERSISTENCE_ENTITY,SERVICE_ENTITY> page(Boolean pageable,Integer firstTupleIndex,Integer numberOfTuples) {
		if(pageable == null || Boolean.TRUE.equals(pageable))
			getQueryExecutorArguments(Boolean.TRUE).setFirstTupleIndex(firstTupleIndex).setNumberOfTuples(numberOfTuples);
		return this;
	}
	
	public EntityReaderRequestImpl<PERSISTENCE_ENTITY,SERVICE_ENTITY> count(Boolean countable,String queryIdentifier) {
		if(Boolean.TRUE.equals(countable) && StringHelper.isBlank(queryIdentifier) && persistence != null)
			queryIdentifier = persistence.getQueryIdentifierCountDynamic();
		setCountable(StringHelper.isNotBlank(queryIdentifier));
		setCountQueryIdentifier(queryIdentifier);
		return this;
	}
	
	public EntityReaderRequestImpl<PERSISTENCE_ENTITY,SERVICE_ENTITY> count(Boolean countable) {
		return count(countable, null);
	}
	
	public EntityReaderRequestImpl<PERSISTENCE_ENTITY,SERVICE_ENTITY> enableResponseHeadersCORS() {
		getResponseBuilderArguments(Boolean.TRUE).setHeadersCORS();
		return this;
	}
	
	public ResponseBuilder.Arguments getResponseBuilderArguments(Boolean instantiateIfNull) {
		if(responseBuilderArguments == null && Boolean.TRUE.equals(instantiateIfNull))
			responseBuilderArguments = new ResponseBuilder.Arguments();
		return responseBuilderArguments;
	}
	
	@Override
	public ResponseBuilder.Arguments execute() {
		if((queryExecutorArguments == null || queryExecutorArguments.getQuery() == null || StringHelper.isBlank(queryExecutorArguments.getQuery().getIdentifier()))) {
			if(persistence != null)
				query(persistence.getQueryIdentifierReadDynamic());
		}
		validatePreConditions();
		readPersistenceEntities();
		if(Boolean.TRUE.equals(countable))
			countPersistenceEntities();
		return responseBuilderArguments;
	}
	
	protected void validatePreConditions() {
		if(serviceEntityClass == null)
			throw new RuntimeException("Service entity class is required");
		if(persistenceEntityClass == null)
			throw new RuntimeException("Persistence entity class is required");
		if(queryExecutorArguments == null)
			throw new RuntimeException("Query executor arguments is required");		
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
	
	/**/
	
	/*public static <PERSISTENCE_ENTITY,PERSISTENCE_ENTITY_IMPL,SERVICE_ENTITY> EntityReaderRequestImpl<PERSISTENCE_ENTITY_IMPL,SERVICE_ENTITY> instantiate(
			Class<PERSISTENCE_ENTITY> persistenceEntityClass,Class<PERSISTENCE_ENTITY_IMPL> persistenceEntityImplClass,Class<SERVICE_ENTITY> serviceEntityClass
			,SpecificPersistence<PERSISTENCE_ENTITY> persistence,Mapper<PERSISTENCE_ENTITY_IMPL, SERVICE_ENTITY> mapper
			,String filterAsString,List<String> projections,Boolean countable,Boolean pageable,Integer firstTupleIndex,Integer numberOfTuples) {		
		EntityReaderRequestImpl<PERSISTENCE_ENTITY_IMPL,SERVICE_ENTITY> request = new EntityReaderRequestImpl<PERSISTENCE_ENTITY_IMPL,SERVICE_ENTITY>(
				persistenceEntityImplClass,serviceEntityClass);
		request.projections(projections).filter(filterAsString).count(countable).page(pageable,firstTupleIndex, numberOfTuples);
		return request;
	}*/
}