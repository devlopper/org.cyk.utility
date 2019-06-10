package org.cyk.utility.client.controller.web;

import java.io.Serializable;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.client.controller.AbstractSystemContextListener;
import org.cyk.utility.client.controller.component.theme.AbstractThemeClassGetterImpl;
import org.cyk.utility.string.StringHelper;

public abstract class AbstractServletContextListener extends AbstractSystemContextListener<ServletContext> implements javax.servlet.ServletContextListener,Objectable,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext context = servletContextEvent.getServletContext();
		initialize(context);	
		__inject__(ApplicationScopeLifeCycleListener.class).initialize(null);
	}
	
	@Override
	protected void __initialize__(ServletContext context) {
		AbstractContextGetterImpl.SERVLET_CONTEXT = context;
		super.__initialize__(context);
		AbstractThemeClassGetterImpl.CLASS_NAME = getConfigurationParameterValue(Constant.CONTEXT_PARAMETER_NAME_THEME_CLASS_NAME);
		if(__inject__(StringHelper.class).isBlank(AbstractThemeClassGetterImpl.CLASS_NAME))
			AbstractThemeClassGetterImpl.CLASS = __getThemeClass__();
		else
			AbstractThemeClassGetterImpl.CLASS = __inject__(ClassHelper.class).getByName(AbstractThemeClassGetterImpl.CLASS_NAME);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		ServletContext context = servletContextEvent.getServletContext();
		destroy(context);	
		//__inject__(ApplicationScopeLifeCycleListener.class).destroy(null);
	}
	
	/**/
	
	protected abstract Class<?> __getThemeClass__();
	
	/**/
	
}
