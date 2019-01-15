package org.cyk.utility.client.controller.test;

import org.cyk.utility.client.controller.Controller;

public class TestControllerCreateImpl extends AbstractTestControllerFunctionIntegrationImpl implements TestControllerCreate {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __perform__(Object object) throws Exception {
		__inject__(Controller.class).create(object);
		addGarbagesArray(object);
	}
	
}
