package org.cyk.utility.client.controller.web.jsf;

import java.io.Serializable;

import javax.servlet.ServletContext;

import org.cyk.utility.__kernel__.DependencyInjection;

public abstract class AbstractServletContextListener extends org.cyk.utility.client.controller.web.AbstractServletContextListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __initialize__(ServletContext context) {
		super.__initialize__(context);
		initializeFromStatic(context,__getThemeClass__());
	}
	
	public static void initializeFromStatic(ServletContext context,Class<?> themeClass) {
		org.cyk.utility.client.controller.web.AbstractServletContextListener.initializeFromStatic(context, themeClass);
		DependencyInjection.inject(ApplicationScopeLifeCycleListener.class).initialize(null);
	}
}
