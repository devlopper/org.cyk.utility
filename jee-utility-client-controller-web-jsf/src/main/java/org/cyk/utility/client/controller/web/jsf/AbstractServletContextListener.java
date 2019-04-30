package org.cyk.utility.client.controller.web.jsf;

import java.io.Serializable;

import javax.servlet.ServletContextEvent;

public abstract class AbstractServletContextListener extends org.cyk.utility.client.controller.web.AbstractServletContextListener implements Serializable {
	private static final long serialVersionUID = 1L;

	protected void __listenContextInitialized__(ServletContextEvent servletContextEvent) {
		__inject__(ApplicationScopeLifeCycleListener.class).initialize(null);
	}

}
