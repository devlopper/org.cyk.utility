package org.cyk.utility.service.server;

import java.io.Serializable;

import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.persistence.query.EntityCounter;
import org.cyk.utility.service.FilterFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class EntityCounterRequestImpl<SERVICE_ENTITY,PERSISTENCE_ENTITY> extends AbstractEntityRequestImpl<SERVICE_ENTITY,PERSISTENCE_ENTITY> implements Serializable {

	public EntityCounterRequestImpl(Class<SERVICE_ENTITY> serviceEntityClass) {
		super(serviceEntityClass);
	}
	
	@Override
	protected void prepare() {
		super.prepare();
		if((queryExecutorArguments == null || queryExecutorArguments.getQuery() == null || StringHelper.isBlank(queryExecutorArguments.getQuery().getIdentifier()))) {
			if(persistence != null)
				query(persistence.getQueryIdentifierCountDynamic());
		}		
	}
	
	@Override
	protected void __execute__() {
		responseBuilderArguments.setEntity(countPersistenceEntities());	
	}
	
	@Override
	public EntityCounterRequestImpl<SERVICE_ENTITY,PERSISTENCE_ENTITY> filter(String string) {
		return (EntityCounterRequestImpl<SERVICE_ENTITY,PERSISTENCE_ENTITY>) super.filter(string);
	}
	
	@Override
	public EntityCounterRequestImpl<SERVICE_ENTITY, PERSISTENCE_ENTITY> filter(String string, FilterFormat format) {
		return (EntityCounterRequestImpl<SERVICE_ENTITY, PERSISTENCE_ENTITY>) super.filter(string, format);
	}
	
	protected Long countPersistenceEntities() {
		return EntityCounter.getInstance().count(persistenceEntityClass,queryExecutorArguments);
	}
}