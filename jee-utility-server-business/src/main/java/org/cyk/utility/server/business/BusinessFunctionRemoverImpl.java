package org.cyk.utility.server.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.transaction.Transactional;

import org.cyk.utility.server.persistence.Persistence;
import org.cyk.utility.system.action.SystemAction;

@Dependent
public class BusinessFunctionRemoverImpl extends AbstractBusinessFunctionRemoverImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override @Transactional
	public BusinessFunctionRemover execute() {
		return super.execute();
	}
	
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

}
