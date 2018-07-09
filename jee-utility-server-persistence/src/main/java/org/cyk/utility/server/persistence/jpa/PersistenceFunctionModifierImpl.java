package org.cyk.utility.server.persistence.jpa;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.cyk.utility.server.persistence.AbstractPersistenceFunctionModifierImpl;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionUpdate;

public class PersistenceFunctionModifierImpl extends AbstractPersistenceFunctionModifierImpl implements PersistenceFunctionModifier {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;
	
	@Override
	protected void __executeQuery__(SystemAction action,Object entity) {
		getEntityManager().merge(entity);		
	}
	
	@Override
	protected void __listenPostConstruct__() {
		getProperties().setEntityManager(entityManager).setAction(__inject__(SystemActionUpdate.class));
		super.__listenPostConstruct__();
	}
	
	@Override
	public EntityManager getEntityManager() {
		return (EntityManager) getProperties().getEntityManager();
	}
	
	@Override
	public PersistenceFunctionModifier setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
		getProperties().setEntityManager(entityManager);
		return this;
	}
}
