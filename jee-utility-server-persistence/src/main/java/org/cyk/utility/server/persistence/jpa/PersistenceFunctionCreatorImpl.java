package org.cyk.utility.server.persistence.jpa;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.cyk.utility.server.persistence.AbstractPersistenceFunctionCreatorImpl;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionCreate;

public class PersistenceFunctionCreatorImpl extends AbstractPersistenceFunctionCreatorImpl implements PersistenceFunctionCreator {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;
	
	@Override
	protected void __executeQuery__(SystemAction action,Object entity) {
		getEntityManager().persist(entity);		
	}
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		getProperties().setEntityManager(entityManager).setAction(__inject__(SystemActionCreate.class));
	}
	
	@Override
	public EntityManager getEntityManager() {
		return (EntityManager) getProperties().getEntityManager();
	}
	
	@Override
	public PersistenceFunctionCreator setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
		getProperties().setEntityManager(entityManager);
		return this;
	}
}
