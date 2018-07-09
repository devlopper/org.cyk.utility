package org.cyk.utility.server.persistence.jpa;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.cyk.utility.server.persistence.AbstractPersistenceFunctionReaderImpl;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionRead;

public class PersistenceFunctionReaderImpl extends AbstractPersistenceFunctionReaderImpl implements PersistenceFunctionReader {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;
	
	@Override
	protected void __executeQuery__(SystemAction action) {
		Class<?> aClass = getEntityClass();
		Object entityIdentifier = getEntityIdentifier();
		Object entity = getEntityManager().find(aClass,entityIdentifier);
		getProperties().setEntity(entity);
		if(entity == null)
			__addLogMessageBuilderParameter__(MESSAGE_NOT_FOUND);
	}
	
	@Override
	protected void __listenPostConstruct__() {
		getProperties().setEntityManager(entityManager).setAction(__inject__(SystemActionRead.class));
		super.__listenPostConstruct__();
	}
	
	@Override
	public EntityManager getEntityManager() {
		return (EntityManager) getProperties().getEntityManager();
	}
	
	@Override
	public PersistenceFunctionReader setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
		getProperties().setEntityManager(entityManager);
		return this;
	}
}
