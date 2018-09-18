package org.cyk.utility.server.persistence.test;

import java.util.Collection;

public abstract class AbstractTestPersistenceTransactionIntegrationImpl extends AbstractTestPersistenceFunctionIntegrationImpl implements TestPersistenceTransactionIntegration {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setIsTransactional(Boolean.TRUE);
		setIsContainerManagedTransaction(Boolean.FALSE);
	}
	
	@Override
	protected Collection<Object> __getExecutionObjects__() throws Exception {
		return getObjects();
	}
	
}
