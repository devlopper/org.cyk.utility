package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.system.action.SystemAction;

public abstract class AbstractPersistenceFunctionTransactionImpl extends AbstractPersistenceFunctionImpl implements PersistenceFunctionTransaction, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __executeQuery__(SystemAction action) {
		__executeQuery__(action, getEntity());	
	}
	
	@Override
	protected Boolean __isQueryExecutable__(SystemAction action) {
		return getEntity() != null;
	}
	
	protected abstract void __executeQuery__(SystemAction action,Object entity);
	
	@Override
	public Object getEntity() {
		return getProperties().getEntity();
	}
	
	@Override
	public PersistenceFunctionTransaction setEntity(Object entity) {
		getProperties().setEntity(entity);
		if(entity!=null)
			setEntityClass(entity.getClass());
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<?> getEntities() {
		return (Collection<Object>) getProperties().getEntities();
	}
	
	@Override
	public PersistenceFunctionTransaction setEntities(Collection<?> entities) {
		getProperties().setEntities(entities);
		if(__inject__(CollectionHelper.class).isNotEmpty(entities))
			setEntityClass(entities.iterator().next().getClass());
		return this;
	}
	
	@Override
	public PersistenceFunctionTransaction setAction(SystemAction action) {
		return (PersistenceFunctionTransaction) super.setAction(action);
	}
}
