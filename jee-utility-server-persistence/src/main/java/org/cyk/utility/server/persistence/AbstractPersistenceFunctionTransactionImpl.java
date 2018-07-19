package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.system.action.SystemAction;

public abstract class AbstractPersistenceFunctionTransactionImpl extends AbstractPersistenceFunctionImpl implements PersistenceFunctionTransaction, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Boolean __executeGetIsExecutable__(Boolean value) {
		return getEntity() != null;
	}
	
	@Override
	protected void __execute__(SystemAction action) {
		__executeQuery__(action, getEntity());	
	}
	
	protected abstract void __executeQuery__(SystemAction action,Object entity);
	
	@Override
	public PersistenceFunctionTransaction setEntity(Object entity) {
		return (PersistenceFunctionTransaction) super.setEntity(entity);
	}
	
	@Override
	public PersistenceFunctionTransaction setEntities(Collection<?> entities) {
		return (PersistenceFunctionTransaction) super.setEntities(entities);
	}
	
	@Override
	public PersistenceFunctionTransaction setAction(SystemAction action) {
		return (PersistenceFunctionTransaction) super.setAction(action);
	}
}
