package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import javax.servlet.ServletContextEvent;

import org.cyk.utility.client.controller.web.Constant;
import org.cyk.utility.client.controller.web.jsf.primefaces.theme.ThemeDesktopDefault;
import org.cyk.utility.string.StringHelper;

public abstract class AbstractServletContextListener extends org.cyk.utility.client.controller.web.jsf.AbstractServletContextListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenContextInitialized__(ServletContextEvent servletContextEvent) {
		super.__listenContextInitialized__(servletContextEvent);
		String primefacesTheme = getConfigurationParameterValue(Constant.CONTEXT_PARAMETER_NAME_THEME_PRIMEFACES,servletContextEvent);
		if(__inject__(StringHelper.class).isNotBlank(primefacesTheme)) {
			servletContextEvent.getServletContext().setInitParameter("primefaces.THEME", primefacesTheme);
		}
		__inject__(ApplicationScopeLifeCycleListener.class).initialize(null);
	}
	
	@Override
	protected Class<?> __getThemeClass__() {
		return ThemeDesktopDefault.class;
	}
	
	/**/
	
}
