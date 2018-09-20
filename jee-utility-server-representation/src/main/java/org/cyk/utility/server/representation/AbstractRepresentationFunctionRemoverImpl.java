package org.cyk.utility.server.representation;

import java.io.Serializable;

import org.cyk.utility.system.action.SystemActionDelete;

public abstract class AbstractRepresentationFunctionRemoverImpl extends AbstractRepresentationFunctionTransactionImpl implements RepresentationFunctionRemover, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionDelete.class));
	}

}
