package org.cyk.utility.server.business.test;

import org.cyk.utility.server.business.Business;

public abstract class AbstractTestBusinessDeleteIntegrationImpl extends AbstractTestBusinessTransactionIntegrationImpl implements TestBusinessDeleteIntegration {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __perform__(Object object) throws Exception {
		__inject__(Business.class).delete(object);
	}
	
}
