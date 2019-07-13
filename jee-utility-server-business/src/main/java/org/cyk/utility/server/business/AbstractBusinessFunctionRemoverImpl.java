package org.cyk.utility.server.business;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.cyk.utility.server.persistence.Persistence;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionDelete;

public abstract class AbstractBusinessFunctionRemoverImpl extends AbstractBusinessFunctionTransactionImpl implements BusinessFunctionRemover, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionDelete.class));
	}
	
	@Override
	protected void __execute__(SystemAction action) {
		if(Boolean.TRUE.equals(__injectCollectionHelper__().isNotEmpty(__entitiesSystemIdentifiers__)))
			__inject__(Persistence.class).deleteBySystemIdentifiers(getEntityClass(), __entitiesSystemIdentifiers__);
		
		if(Boolean.TRUE.equals(__injectCollectionHelper__().isNotEmpty(__entitiesBusinessIdentifiers__)))
			__inject__(Persistence.class).deleteByBusinessIdentifiers(getEntityClass(), __entitiesBusinessIdentifiers__);
	}
	/*
	@Override
	protected void __execute__(SystemAction action) {
		//Delete by identifiers
		Integer count = 0;
		if(Boolean.TRUE.equals(__injectCollectionHelper__().isNotEmpty(getAction().getEntitiesIdentifiers()))) {
			__inject__(Persistence.class).deleteByIdentifiers(getEntityClass(), getAction().getEntitiesIdentifiers().get(), getEntityIdentifierValueUsageType());
			count = count + getAction().getEntitiesIdentifiers().get().size();
		}
		
		//Delete by entities
		if(getEntity() != null || getEntities() != null) {
			Collection<Object> entities = new ArrayList<Object>();
			if(getEntities() != null)
				entities.addAll(getEntities());
			if(getEntity() != null)
				entities.add(getEntity());
			if(!entities.isEmpty()) {
				__inject__(Persistence.class).deleteMany(entities);
				count = count + entities.size();
			}
		}		
		addLogMessageBuilderParameter("count", count);
	}
	*/
	@Override @Transactional
	public BusinessFunctionRemover execute() {
		return (BusinessFunctionRemover) super.execute();
	}
	
	@Override
	public BusinessFunctionRemover setAll(Boolean value) {
		getProperties().setAll(value);
		return this;
	}
	
	@Override
	public Boolean getAll() {
		return (Boolean) getProperties().getAll();
	}

}
