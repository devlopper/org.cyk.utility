package org.cyk.utility.client.controller.web.jsf;

import java.io.Serializable;

import javax.servlet.ServletContext;

public abstract class AbstractServletContextListener extends org.cyk.utility.client.controller.web.AbstractServletContextListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __initialize__(ServletContext context) {
		super.__initialize__(context);
		__inject__(ApplicationScopeLifeCycleListener.class).initialize(null);
	}
	
}
