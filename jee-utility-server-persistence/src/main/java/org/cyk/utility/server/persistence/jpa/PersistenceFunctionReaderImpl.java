package org.cyk.utility.server.persistence.jpa;

import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.server.persistence.AbstractPersistenceFunctionReaderImpl;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.value.ValueUsageType;

public class PersistenceFunctionReaderImpl extends AbstractPersistenceFunctionReaderImpl implements PersistenceFunctionReader {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;
	
	@Override
	protected void __executeQuery__(SystemAction action) {
		Class<?> aClass = getEntityClass();
		Object entityIdentifier = getEntityIdentifier();
		ValueUsageType valueUsageType = getEntityIdentifierValueUsageType();
		if(valueUsageType == null)
			valueUsageType = ValueUsageType.SYSTEM;
		Object entity;
		if(ValueUsageType.SYSTEM.equals(valueUsageType))
			entity = getEntityManager().find(aClass,entityIdentifier);
		else{
			Collection<?> objects = getEntityManager().createNamedQuery("", aClass).setParameter("", entityIdentifier).getResultList();
			entity = __inject__(CollectionHelper.class).getFirst(objects);
		}
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
