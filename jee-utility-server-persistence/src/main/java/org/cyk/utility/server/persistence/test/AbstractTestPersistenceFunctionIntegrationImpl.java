package org.cyk.utility.server.persistence.test;

import org.cyk.utility.server.persistence.Persistence;
import org.cyk.utility.test.AbstractTestSystemFunctionIntegrationImpl;

public abstract class AbstractTestPersistenceFunctionIntegrationImpl extends AbstractTestSystemFunctionIntegrationImpl implements TestPersistenceFunctionIntegration {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __createOne__(Object object) throws Exception {
		__inject__(Persistence.class).create(object);
	}
	
	@Override
	protected void __deleteOne__(Object object) {
		__inject__(Persistence.class).delete(object);
	}

}
