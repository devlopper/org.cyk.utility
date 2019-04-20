package org.cyk.utility.server.deployment;
import java.io.Serializable;

import javax.servlet.ServletContextEvent;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.server.representation.impl.ApplicationScopeLifeCycleListener;

public abstract class AbstractServletContextListener extends AbstractObject implements javax.servlet.ServletContextListener,Objectable,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		__listenContextInitialized__(servletContextEvent);
		__inject__(ApplicationScopeLifeCycleListener.class).initialize(null);
	}
	
	protected abstract void __listenContextInitialized__(ServletContextEvent servletContextEvent);

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		__listenContextDestroyed__(servletContextEvent);
	}
	
	protected abstract void __listenContextDestroyed__(ServletContextEvent servletContextEvent);

}
