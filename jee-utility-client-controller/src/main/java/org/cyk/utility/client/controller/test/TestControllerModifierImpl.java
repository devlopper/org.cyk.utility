package org.cyk.utility.client.controller.test;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.Controller;
import org.cyk.utility.server.representation.ResponseHelper;

public class TestControllerModifierImpl extends AbstractTestControllerFunctionIntegrationImpl implements TestControllerModifier {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __perform__(Object object) throws Exception {
		Properties properties = new Properties();
		properties.setFields(getProperties().getFields());
		__inject__(Controller.class).update(object,properties);
		Response response = (Response) properties.getResponse();
		assertionHelper.assertEquals("Response status code", Response.Status.OK, __inject__(ResponseHelper.class).getStatus(response));
		addGarbagesArray(object);
	}
	
}
