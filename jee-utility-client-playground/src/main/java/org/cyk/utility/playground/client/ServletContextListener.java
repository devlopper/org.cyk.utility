package org.cyk.utility.playground.client;
import java.io.Serializable;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;

import org.cyk.utility.client.controller.web.jsf.primefaces.ApplicationScopeLifeCycleListener;
import org.cyk.utility.client.deployment.AbstractServletContextListener;

@WebListener
public class ServletContextListener extends AbstractServletContextListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(ServletContext context) {
		super.__initialize__(context);
		__inject__(ApplicationScopeLifeCycleListener.class).initialize(null);
	}
	
}