package org.cyk.utility.client.deployment;
import java.io.Serializable;

import javax.servlet.ServletContext;

import org.cyk.utility.__kernel__.configuration.ConstantParameterName;
import org.cyk.utility.system.node.SystemNodeClient;

public abstract class AbstractServletContextListener extends org.cyk.utility.client.controller.web.jsf.primefaces.AbstractServletContextListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(ServletContext context) {
		super.__initialize__(context);
		__inject__(SystemNodeClient.class).setName(getConfigurationParameterValue(ConstantParameterName.SYSTEM_NAME));
	}
	
}
