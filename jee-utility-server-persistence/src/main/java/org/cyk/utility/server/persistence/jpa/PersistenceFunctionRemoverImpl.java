package org.cyk.utility.server.persistence.jpa;

import javax.persistence.EntityManager;

import org.cyk.utility.server.persistence.AbstractPersistenceFunctionRemoverImpl;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionDelete;

public class PersistenceFunctionRemoverImpl extends AbstractPersistenceFunctionRemoverImpl implements PersistenceFunctionRemover {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __executeQuery__(SystemAction action) {
		getEntityManager().remove(getEntityManager().merge(getEntity()));		
	}
	
	@Override
	protected void __listenPostConstruct__() {
		setEntityManager(____inject____(EntityManager.class)).setAction(__inject__(SystemActionDelete.class));
		super.__listenPostConstruct__();
	}
	
	@Override
	public EntityManager getEntityManager() {
		return (EntityManager) getProperties().getEntityManager();
	}
	
	@Override
	public PersistenceFunctionRemover setEntityManager(EntityManager entityManager) {
		getProperties().setEntityManager(entityManager);
		return this;
	}
}
