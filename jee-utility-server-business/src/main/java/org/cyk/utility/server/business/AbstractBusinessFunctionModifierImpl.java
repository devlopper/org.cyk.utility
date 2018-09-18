package org.cyk.utility.server.business;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.cyk.utility.system.action.SystemActionUpdate;

public abstract class AbstractBusinessFunctionModifierImpl extends AbstractBusinessFunctionTransactionImpl implements BusinessFunctionModifier, Serializable {
	private static final long serialVersionUID = 1L;

	@Override @Transactional
	public BusinessFunctionModifier execute() {
		return (BusinessFunctionModifier) super.execute();
	}
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionUpdate.class));
	}

}
