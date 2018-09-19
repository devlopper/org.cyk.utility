package org.cyk.utility.server.representation.test;

import java.util.Collection;

public abstract class AbstractTestRepresentationTransactionIntegrationImpl extends AbstractTestRepresentationFunctionIntegrationImpl implements TestRepresentationTransactionIntegration {
	private static final long serialVersionUID = 1L;

	@Override
	protected Collection<Object> __getExecutionObjects__() throws Exception {
		return getObjects();
	}
	
}
