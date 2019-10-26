package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import javax.servlet.ServletContext;

import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.cyk.utility.client.controller.web.jsf.primefaces.theme.ThemeDesktopDefault;

public abstract class AbstractServletContextListener extends org.cyk.utility.client.controller.web.jsf.AbstractServletContextListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __initialize__(ServletContext context) {
		super.__initialize__(context);
		String primefacesTheme = ConfigurationHelper.getValueAsString(VariableName.USER_INTERFACE_THEME_PRIMEFACES);
		if(StringHelper.isNotBlank(primefacesTheme)) {
			context.setInitParameter("primefaces.THEME", primefacesTheme);
		}
		__inject__(ApplicationScopeLifeCycleListener.class).initialize(null);
	}
	
	@Override
	protected Class<?> __getThemeClass__() {
		return ThemeDesktopDefault.class;
	}
	
	/**/
	
}
