package org.cyk.utility.server.business.test;

import java.util.Collection;

public abstract class AbstractTestBusinessTransactionIntegrationImpl extends AbstractTestBusinessFunctionIntegrationImpl implements TestBusinessTransactionIntegration {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setIsTransactional(Boolean.TRUE);
		setIsContainerManagedTransaction(Boolean.TRUE);
	}
	
	@Override
	protected Collection<Object> __getExecutionObjects__() throws Exception {
		return getObjects();
	}
	
}
