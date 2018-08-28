package org.cyk.utility.server.persistence.test;

import java.util.Collection;

public abstract class AbstractTestPersistenceServiceProviderTransactionImpl extends AbstractTestPersistenceServiceProviderFunctionImpl implements TestPersistenceServiceProviderTransaction {
	private static final long serialVersionUID = 1L;

	@Override
	protected Collection<Object> __getExecutionObjects__() throws Exception {
		return getObjects();
	}
	
}
