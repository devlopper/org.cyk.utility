package org.cyk.utility.client.controller.web;

import java.io.Serializable;

import javax.servlet.ServletContextEvent;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.client.controller.component.theme.AbstractThemeClassGetterImpl;
import org.cyk.utility.string.StringHelper;

public abstract class AbstractServletContextListener extends AbstractObject implements javax.servlet.ServletContextListener,Objectable,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		AbstractThemeClassGetterImpl.CLASS_NAME = getConfigurationParameterValue(Constant.CONTEXT_PARAMETER_NAME_THEME_CLASS_NAME, servletContextEvent);
		if(__inject__(StringHelper.class).isBlank(AbstractThemeClassGetterImpl.CLASS_NAME))
			AbstractThemeClassGetterImpl.CLASS = __getThemeClass__();
		else
			AbstractThemeClassGetterImpl.CLASS = __inject__(ClassHelper.class).getByName(AbstractThemeClassGetterImpl.CLASS_NAME);		
		__listenContextInitialized__(servletContextEvent);
		__inject__(ApplicationScopeLifeCycleListener.class).initialize(null);
	}
	
	protected abstract void __listenContextInitialized__(ServletContextEvent servletContextEvent);

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		__listenContextDestroyed__(servletContextEvent);
	}
	
	protected abstract void __listenContextDestroyed__(ServletContextEvent servletContextEvent);

	/**/
	
	protected abstract Class<?> __getThemeClass__();
	
	/**/
	
	public static String getConfigurationParameterValue(String name,ServletContextEvent servletContextEvent) {
		return org.cyk.utility.client.controller.Constant.getConfigurationParameterValue(name, servletContextEvent.getServletContext(), null, null);
	}
}
