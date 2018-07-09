package org.cyk.utility.server.persistence.jpa;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.cyk.utility.server.persistence.AbstractPersistenceFunctionRemoverImpl;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionDelete;

public class PersistenceFunctionRemoverImpl extends AbstractPersistenceFunctionRemoverImpl implements PersistenceFunctionRemover {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;
	
	@Override
	protected void __executeQuery__(SystemAction action,Object entity) {
		getEntityManager().remove(getEntityManager().merge(entity));		
	}
	
	@Override
	protected void __listenPostConstruct__() {
		getProperties().setEntityManager(entityManager).setAction(__inject__(SystemActionDelete.class));
		super.__listenPostConstruct__();
	}
	
	@Override
	public EntityManager getEntityManager() {
		return (EntityManager) getProperties().getEntityManager();
	}
	
	@Override
	public PersistenceFunctionRemover setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
		getProperties().setEntityManager(entityManager);
		return this;
	}
}
