package org.cyk.utility.server.representation;

import java.io.Serializable;

import org.cyk.utility.system.action.SystemActionUpdate;

public abstract class AbstractRepresentationFunctionModifierImpl extends AbstractRepresentationFunctionTransactionImpl implements RepresentationFunctionModifier, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionUpdate.class));
	}

}
