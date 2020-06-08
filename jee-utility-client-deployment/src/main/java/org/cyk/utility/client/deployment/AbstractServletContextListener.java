package org.cyk.utility.client.deployment;
import java.io.Serializable;

import javax.servlet.ServletContext;

public abstract class AbstractServletContextListener extends org.cyk.utility.client.controller.web.jsf.primefaces.AbstractServletContextListener implements Serializable {
	private static final long serialVersionUID = 1L;

	public static void initializeFromStatic(ServletContext context,Class<?> themeClass) {
		org.cyk.utility.client.controller.web.jsf.primefaces.AbstractServletContextListener.initializeFromStatic(context, themeClass);
	}
}