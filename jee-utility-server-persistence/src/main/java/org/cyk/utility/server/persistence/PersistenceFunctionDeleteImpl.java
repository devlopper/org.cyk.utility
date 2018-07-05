package org.cyk.utility.server.persistence;

import java.io.Serializable;

import org.cyk.utility.architecture.system.SystemAction;

public class PersistenceFunctionDeleteImpl extends AbstractPersistenceFunctionTransactionImpl implements PersistenceFunctionDelete, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __executeQuery__(SystemAction action,Object entity) {
		getEntityManager().remove(getEntityManager().merge(entity));
	}

}
