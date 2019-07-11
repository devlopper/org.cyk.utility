package org.cyk.utility.server.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.transaction.Transactional;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.Persistence;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.type.BooleanHelper;

@Dependent
public class BusinessFunctionCreatorImpl extends AbstractBusinessFunctionCreatorImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override @Transactional
	public BusinessFunctionCreator execute() {
		return super.execute();
	}
	
	@Override
	protected void __execute__(SystemAction action) {
		Properties properties = getProperties();
		Collection<Object> entities = __getEntities__();
		if(__injectCollectionHelper__().isEmpty(entities)) {
			System.err.println("No entity to "+action);
		}else {
			__listenBeforePersistenceCreate__(entities, properties);
			__inject__(Persistence.class).createMany(entities);
			__listenAfterPersistenceCreate__(entities, properties);
			addLogMessageBuilderParameter("count", entities.size());	
		}
		
		/*
		if(getEntities()!=null) {
			Collection<Object> collection = (Collection<Object>) getEntities();
			__inject__(Persistence.class).createMany(collection);
			addLogMessageBuilderParameter("count", collection.size());
		}else if(getEntity()!=null)
			__inject__(Persistence.class).create(getEntity());
		else
			System.err.println("No entity to "+action);
		*/
	}
	
	private Collection<Object> __getEntities__() {
		Collection<Object> entities = new ArrayList<>();
		Collection<Object> collection = new ArrayList<>();
		if(getEntities()!=null)
			collection.addAll(getEntities());
		if(getEntity()!=null)
			collection.add(getEntity());
		for(Object index : collection) {
			Object isCreateIfSystemIdentifierIsBlank = Properties.getFromPath(getProperties(), Properties.IS_CREATE_IF_SYSTEM_IDENTIFIER_IS_BLANK);
			if(isCreateIfSystemIdentifierIsBlank == null) {
				entities.add(index);
			}else {
				if(Boolean.TRUE.equals(__inject__(BooleanHelper.class).get(isCreateIfSystemIdentifierIsBlank))) {
					Object identifier = __injectFieldHelper__().getFieldValueSystemIdentifier(index);
					if(Boolean.TRUE.equals(__injectValueHelper__().isBlank(identifier)))
						entities.add(index);
				}
			}	
		}
		return entities;
	}
	
	private void __listenBeforePersistenceCreate__(Collection<Object> entities,Properties properties) {
		//TODO validation
	}
	
	private void __listenAfterPersistenceCreate__(Collection<Object> entities,Properties properties) {
		//TODO validation
	}

}
