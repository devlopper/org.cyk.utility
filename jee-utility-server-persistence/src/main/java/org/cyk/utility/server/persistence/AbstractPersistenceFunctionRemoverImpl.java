package org.cyk.utility.server.persistence;

import java.io.Serializable;

import org.cyk.utility.__kernel__.system.action.SystemActionDelete;

public abstract class AbstractPersistenceFunctionRemoverImpl extends AbstractPersistenceFunctionTransactionImpl implements PersistenceFunctionRemover, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionDelete.class));
	}
	
}
