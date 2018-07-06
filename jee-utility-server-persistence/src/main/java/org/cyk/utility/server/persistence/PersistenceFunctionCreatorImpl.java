package org.cyk.utility.server.persistence;

import java.io.Serializable;

import org.cyk.utility.system.action.SystemAction;

public class PersistenceFunctionCreatorImpl extends AbstractPersistenceFunctionTransactionImpl implements PersistenceFunctionCreator, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __executeQuery__(SystemAction action,Object entity) {
		getEntityManager().persist(entity);		
	}

}
