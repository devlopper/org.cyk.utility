package org.cyk.utility.server.persistence.test;

import org.cyk.utility.server.persistence.Persistence;

public abstract class AbstractTestPersistenceDeleteIntegrationImpl extends AbstractTestPersistenceTransactionIntegrationImpl implements TestPersistenceDeleteIntegration {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __perform__(Object object) throws Exception {
		__inject__(Persistence.class).delete(object);
	}
	
}
