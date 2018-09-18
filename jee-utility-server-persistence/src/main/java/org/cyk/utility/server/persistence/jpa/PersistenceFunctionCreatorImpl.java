package org.cyk.utility.server.persistence.jpa;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EntityManager;

import org.cyk.utility.server.persistence.AbstractPersistenceFunctionCreatorImpl;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionCreate;

public class PersistenceFunctionCreatorImpl extends AbstractPersistenceFunctionCreatorImpl implements PersistenceFunctionCreator {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __executeQuery__(SystemAction action) {
		Collection<Object> entities = new ArrayList<>();
		if(getEntities()!=null)
			entities.addAll(getEntities());
		if(getEntity()!=null)
			entities.add(getEntity());
		for(Object index : entities)
			getEntityManager().persist(index);		
	}
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setEntityManager(__inject__(EntityManager.class)).setAction(__inject__(SystemActionCreate.class));
	}
	
	@Override
	public EntityManager getEntityManager() {
		return (EntityManager) getProperties().getEntityManager();
	}
	
	@Override
	public PersistenceFunctionCreator setEntityManager(EntityManager entityManager) {
		getProperties().setEntityManager(entityManager);
		return this;
	}
}
