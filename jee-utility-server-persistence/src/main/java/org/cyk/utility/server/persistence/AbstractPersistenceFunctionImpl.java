package org.cyk.utility.server.persistence;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.cyk.utility.architecture.system.AbstractSystemFunctionImpl;
import org.cyk.utility.architecture.system.SystemAction;

public abstract class AbstractPersistenceFunctionImpl extends AbstractSystemFunctionImpl implements PersistenceFunction, Serializable {
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		getProperties().setEntityManager(entityManager);
	}
	
	@Override
	public PersistenceFunction setAction(SystemAction action) {
		return (PersistenceFunction) super.setAction(action);
	}
	
	@Override
	public EntityManager getEntityManager() {
		return (EntityManager) getProperties().getEntityManager();
	}
	
	@Override
	public PersistenceFunction setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
		getProperties().setEntityManager(entityManager);
		return this;
	}

}
