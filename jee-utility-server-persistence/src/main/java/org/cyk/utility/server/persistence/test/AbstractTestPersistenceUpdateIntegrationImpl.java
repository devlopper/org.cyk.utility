package org.cyk.utility.server.persistence.test;

import org.cyk.utility.server.persistence.Persistence;

public abstract class AbstractTestPersistenceUpdateIntegrationImpl extends AbstractTestPersistenceTransactionIntegrationImpl implements TestPersistenceUpdateIntegration {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __perform__(Object object) throws Exception {
		__inject__(Persistence.class).update(object);
	}
	
	
	
}
