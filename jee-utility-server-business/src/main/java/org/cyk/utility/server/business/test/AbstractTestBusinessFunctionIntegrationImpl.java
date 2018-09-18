package org.cyk.utility.server.business.test;

import org.cyk.utility.server.business.Business;
import org.cyk.utility.test.AbstractTestSystemFunctionIntegrationImpl;

public abstract class AbstractTestBusinessFunctionIntegrationImpl extends AbstractTestSystemFunctionIntegrationImpl implements TestBusinessFunctionIntegration {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __createOne__(Object object) throws Exception {
		__inject__(Business.class).create(object);
	}
	
	@Override
	protected void __deleteOne__(Object object) {
		__inject__(Business.class).delete(object);
	}

}
