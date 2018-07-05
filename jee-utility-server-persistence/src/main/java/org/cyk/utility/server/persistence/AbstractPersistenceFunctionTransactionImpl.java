package org.cyk.utility.server.persistence;

import java.io.Serializable;

import org.cyk.utility.architecture.system.SystemAction;

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
		return this;
	}
	
	@Override
	public PersistenceFunctionTransaction setAction(SystemAction action) {
		return (PersistenceFunctionTransaction) super.setAction(action);
	}
}
