package org.cyk.utility.server.business;

import java.io.Serializable;

import org.cyk.utility.system.action.SystemActionUpdate;

public abstract class AbstractBusinessFunctionModifierImpl extends AbstractBusinessFunctionTransactionImpl implements BusinessFunctionModifier, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionUpdate.class));
	}

}
