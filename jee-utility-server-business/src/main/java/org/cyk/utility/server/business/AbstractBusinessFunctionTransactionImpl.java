package org.cyk.utility.server.business;

import java.io.Serializable;
import java.util.Collection;

import javax.transaction.Transactional;

import org.cyk.utility.__kernel__.system.action.SystemAction;

public abstract class AbstractBusinessFunctionTransactionImpl extends AbstractBusinessFunctionImpl implements BusinessFunctionTransaction, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override @Transactional
	public BusinessFunctionTransaction execute() {
		return (BusinessFunctionTransaction) super.execute();
	}
	
	@Override
	public BusinessFunctionTransaction setAction(SystemAction action) {
		return (BusinessFunctionTransaction) super.setAction(action);
	}
	
	@Override
	public BusinessFunctionTransaction setEntity(Object entity) {
		return (BusinessFunctionTransaction) super.setEntity(entity);
	}
	
	@Override
	public BusinessFunctionTransaction setEntities(Collection<?> entities) {
		return (BusinessFunctionTransaction) super.setEntities(entities);
	}
	
	@Override
	protected Boolean __getIsSetConditionsAssertionsProviderFromEntity__() {
		return Boolean.TRUE;
	}
}
