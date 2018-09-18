package org.cyk.utility.server.persistence.test;

import org.cyk.utility.server.persistence.Persistence;

public abstract class AbstractTestPersistenceCreateIntegrationImpl extends AbstractTestPersistenceTransactionIntegrationImpl implements TestPersistenceCreateIntegration {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __perform__(Object object) throws Exception {
		__inject__(Persistence.class).create(object);
		addGarbagesArray(object);
	}
	
}
