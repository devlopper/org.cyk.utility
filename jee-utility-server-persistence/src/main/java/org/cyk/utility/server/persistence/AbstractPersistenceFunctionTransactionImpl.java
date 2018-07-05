package org.cyk.utility.server.persistence;

import java.io.Serializable;

import org.cyk.utility.architecture.system.SystemAction;

public abstract class AbstractPersistenceFunctionTransactionImpl extends AbstractPersistenceFunctionImpl implements PersistenceFunctionTransaction, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(SystemAction action) {
		Object entity = getEntity();
		if(entity == null){
			//TODO log entity must not be null
		}else{
			__execute__(action, entity);	
		}
	}
	
	protected abstract void __execute__(SystemAction action,Object entity);
	
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
