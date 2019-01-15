package org.cyk.utility.client.controller.test;

import java.util.Collection;

import org.cyk.utility.client.controller.Controller;
import org.cyk.utility.test.AbstractTestSystemFunctionIntegrationImpl;

public abstract class AbstractTestControllerFunctionIntegrationImpl extends AbstractTestSystemFunctionIntegrationImpl implements TestControllerFunctionIntegration {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __createOne__(Object object) throws Exception {
		__inject__(Controller.class).create(object);
	}
	
	@Override
	protected void __deleteOne__(Object object) {
		__inject__(Controller.class).delete(object);
	}
	
	@Override
	protected Collection<Object> __getExecutionObjects__() throws Exception {
		return getObjects();
	}

}
