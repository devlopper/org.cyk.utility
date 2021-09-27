package org.cyk.utility.service.server;

import java.io.Serializable;

import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.persistence.query.EntityCounter;
import org.cyk.utility.rest.RequestExecutor;
import org.cyk.utility.rest.ResponseBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class EntityCounterRequestImpl<PERSISTENCE_ENTITY,SERVICE_ENTITY> extends AbstractEntityRequestImpl<PERSISTENCE_ENTITY, SERVICE_ENTITY> implements RequestExecutor.Request,Serializable {

	public EntityCounterRequestImpl(Class<SERVICE_ENTITY> serviceEntityClass) {
		super(serviceEntityClass);
	}
	
	@Override
	public ResponseBuilder.Arguments execute() {
		if((queryExecutorArguments == null || queryExecutorArguments.getQuery() == null || StringHelper.isBlank(queryExecutorArguments.getQuery().getIdentifier()))) {
			if(persistence != null)
				query(persistence.getQueryIdentifierCountDynamic());
		}		
		validatePreConditions();
		countPersistenceEntities();
		return responseBuilderArguments;
	}
	
	@Override
	public EntityCounterRequestImpl<PERSISTENCE_ENTITY,SERVICE_ENTITY> filter(String string) {
		return (EntityCounterRequestImpl<PERSISTENCE_ENTITY, SERVICE_ENTITY>) super.filter(string);
	}
	
	protected Long getCountPersistenceEntities() {
		return EntityCounter.getInstance().count(persistenceEntityClass,queryExecutorArguments);
	}
	
	protected void countPersistenceEntities() {
		getResponseBuilderArguments(Boolean.TRUE).setEntity(getCountPersistenceEntities());		
	}
}