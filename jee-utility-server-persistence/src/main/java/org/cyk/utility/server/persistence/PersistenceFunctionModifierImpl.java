package org.cyk.utility.server.persistence;

import java.io.Serializable;

import org.cyk.utility.system.action.SystemAction;

public class PersistenceFunctionModifierImpl extends AbstractPersistenceFunctionTransactionImpl implements PersistenceFunctionModifier, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __executeQuery__(SystemAction action,Object entity) {
		getEntityManager().merge(entity);
	}
	
}
