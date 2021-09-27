package org.cyk.utility.service.server;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.mapping.Mapper;
import org.cyk.utility.persistence.query.EntityCounter;
import org.cyk.utility.persistence.query.EntityReader;
import org.cyk.utility.persistence.query.Query;
import org.cyk.utility.persistence.query.QueryGetter;
import org.cyk.utility.rest.RequestExecutor;
import org.cyk.utility.rest.ResponseBuilder;
import org.cyk.utility.rest.ResponseHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class EntityReaderRequestImpl<PERSISTENCE_ENTITY,SERVICE_ENTITY> extends AbstractEntityRequestImpl<PERSISTENCE_ENTITY, SERVICE_ENTITY> implements RequestExecutor.Request,Serializable {

	private Mapper<PERSISTENCE_ENTITY,SERVICE_ENTITY> mapper;
	private Boolean countable,collectionable;
	private String countQueryIdentifier;

	public EntityReaderRequestImpl(Class<SERVICE_ENTITY> serviceEntityClass) {
		super(serviceEntityClass);
		if(serviceEntityClass != null && persistenceEntityClass != null)
			mapper = DependencyInjection.inject(MapperGetter.class).get(persistenceEntityClass, serviceEntityClass);
	}
	
	public EntityReaderRequestImpl<PERSISTENCE_ENTITY,SERVICE_ENTITY> page(Boolean pageable,Integer firstTupleIndex,Integer numberOfTuples) {
		if(pageable == null || Boolean.TRUE.equals(pageable))
			getQueryExecutorArguments(Boolean.TRUE).setFirstTupleIndex(firstTupleIndex).setNumberOfTuples(numberOfTuples);
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
	
	public EntityReaderRequestImpl<PERSISTENCE_ENTITY,SERVICE_ENTITY> collect(Boolean collectionable) {
		this.collectionable = collectionable;
		return this;
	}
	
	@Override
	public EntityReaderRequestImpl<PERSISTENCE_ENTITY, SERVICE_ENTITY> filter(String string) {
		return (EntityReaderRequestImpl<PERSISTENCE_ENTITY, SERVICE_ENTITY>) super.filter(string);
	}
	
	@Override
	public EntityReaderRequestImpl<PERSISTENCE_ENTITY, SERVICE_ENTITY> filterByIdentifier(String identifier) {
		collect(Boolean.FALSE);
		return (EntityReaderRequestImpl<PERSISTENCE_ENTITY, SERVICE_ENTITY>) super.filterByIdentifier(identifier);
	}
	
	@Override
	public ResponseBuilder.Arguments execute() {
		if(collectionable == null)
			collectionable = Boolean.TRUE;
		if((queryExecutorArguments == null || queryExecutorArguments.getQuery() == null || StringHelper.isBlank(queryExecutorArguments.getQuery().getIdentifier()))) {
			if(persistence != null)
				query(Boolean.TRUE.equals(collectionable) ? persistence.getQueryIdentifierReadDynamic() : persistence.getQueryIdentifierReadDynamicOne());
		}		
		validatePreConditions();
		if(Boolean.TRUE.equals(collectionable))
			readPersistenceEntities();
		else
			readPersistenceEntity();
		if(Boolean.TRUE.equals(collectionable) && Boolean.TRUE.equals(countable))
			countPersistenceEntities();
		return responseBuilderArguments;
	}
	
	protected void validatePreConditions() {
		super.validatePreConditions();
		if(mapper == null)
			throw new RuntimeException("Persistence entity to service entity mapper is required");
	}
	
	protected Collection<PERSISTENCE_ENTITY> getPersistenceEntities() {
		return EntityReader.getInstance().readMany(persistenceEntityClass,queryExecutorArguments);
	}
	
	protected PERSISTENCE_ENTITY getPersistenceEntity() {
		return EntityReader.getInstance().readOne(persistenceEntityClass,queryExecutorArguments);
	}
	
	protected Collection<SERVICE_ENTITY> mapPersistenceEntitiesToServiceEntities(Collection<PERSISTENCE_ENTITY> entities) {
		return mapper.mapSourcesToDestinations(entities);
	}
	
	protected SERVICE_ENTITY mapPersistenceEntityToServiceEntity(PERSISTENCE_ENTITY entity) {
		return mapper.mapSourceToDestination(entity);
	}
	
	protected void readPersistenceEntities() {		
		Collection<PERSISTENCE_ENTITY> persistenceEntities = getPersistenceEntities();
		Collection<SERVICE_ENTITY> serviceEntities =  CollectionHelper.isEmpty(persistenceEntities) ? null : mapPersistenceEntitiesToServiceEntities(persistenceEntities);
		getResponseBuilderArguments(Boolean.TRUE).setEntities(serviceEntities);		
	}
	
	protected void readPersistenceEntity() {		
		PERSISTENCE_ENTITY persistenceEntity = getPersistenceEntity();
		SERVICE_ENTITY serviceEntity =  persistenceEntity == null ? null : mapPersistenceEntityToServiceEntity(persistenceEntity);
		getResponseBuilderArguments(Boolean.TRUE).setEntity(serviceEntity);
	}
	
	protected void countPersistenceEntities() {
		if(StringHelper.isBlank(countQueryIdentifier))
			throw new RuntimeException(String.format("Count query identifier is required to count %s",queryExecutorArguments.getQuery().getTupleClass().getSimpleName()));
		queryExecutorArguments.setQuery(QueryGetter.getInstance().get(countQueryIdentifier));
		if(queryExecutorArguments.getQuery() == null)
			queryExecutorArguments.setQuery(new Query().setIdentifier(countQueryIdentifier));
		if(queryExecutorArguments.getFilterBackup() != null)
			queryExecutorArguments.setFilter(queryExecutorArguments.getFilterBackup());		
		getResponseBuilderArguments(Boolean.TRUE).setHeader(ResponseHelper.HEADER_X_TOTAL_COUNT, EntityCounter.getInstance().count(persistenceEntityClass,queryExecutorArguments));
	}
}