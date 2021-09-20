package org.cyk.utility.service.server;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.mapping.Mapper;
import org.cyk.utility.persistence.query.EntityReader;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.rest.RequestExecutor;
import org.cyk.utility.rest.ResponseBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class EntityReaderRequestImpl<PERSISTENCE_ENTITY,SERVICE_ENTITY> implements RequestExecutor.Request,Serializable {

	private Class<PERSISTENCE_ENTITY> persistenceEntityClass;
	private Class<SERVICE_ENTITY> serviceEntityClass;
	private QueryExecutorArguments queryExecutorArguments;
	private Mapper<PERSISTENCE_ENTITY,SERVICE_ENTITY> mapper;
	
	@Override
	public ResponseBuilder.Arguments execute() {
		if(persistenceEntityClass == null)
			throw new RuntimeException("Persistence entity class is required");
		if(serviceEntityClass == null)
			throw new RuntimeException("Service entity class is required");
		if(queryExecutorArguments == null)
			throw new RuntimeException("Query executor arguments is required");
		if(mapper == null)
			throw new RuntimeException("Persistence entity to service entity mapper is required");
		Collection<PERSISTENCE_ENTITY> persistenceEntities = getPersistenceEntities();
		System.out.println("EntityReaderRequestImpl.execute() 1");
		System.out.println(persistenceEntities);
		Collection<SERVICE_ENTITY> serviceEntities =  CollectionHelper.isEmpty(persistenceEntities) ? null : mapPersistenceEntitiesToServiceEntities(persistenceEntities);
		System.out.println("EntityReaderRequestImpl.execute() 2");
		System.out.println(serviceEntities);
		ResponseBuilder.Arguments responseBuilderArguments = new ResponseBuilder.Arguments();
		responseBuilderArguments.setEntities(serviceEntities);
		return responseBuilderArguments;
	}
	
	protected Collection<PERSISTENCE_ENTITY> getPersistenceEntities() {
		return EntityReader.getInstance().readMany(persistenceEntityClass,queryExecutorArguments);
	}
	
	protected Collection<SERVICE_ENTITY> mapPersistenceEntitiesToServiceEntities(Collection<PERSISTENCE_ENTITY> entities) {
		return mapper.mapSourcesToDestinations(entities);
	}
}