package org.cyk.utility.server.representation;

import java.io.Serializable;

import org.cyk.utility.system.action.SystemActionCreate;

public abstract class AbstractRepresentationFunctionCreatorImpl extends AbstractRepresentationFunctionTransactionImpl implements RepresentationFunctionCreator, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionCreate.class));
	}

}
