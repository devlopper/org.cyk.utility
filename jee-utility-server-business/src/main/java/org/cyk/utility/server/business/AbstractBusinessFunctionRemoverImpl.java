package org.cyk.utility.server.business;

import java.io.Serializable;

import org.cyk.utility.system.action.SystemActionDelete;

public abstract class AbstractBusinessFunctionRemoverImpl extends AbstractBusinessFunctionTransactionImpl implements BusinessFunctionRemover, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionDelete.class));
	}
	
	@Override
	public BusinessFunctionRemover setAll(Boolean value) {
		getProperties().setAll(value);
		return this;
	}
	
	@Override
	public Boolean getAll() {
		return (Boolean) getProperties().getAll();
	}

}
