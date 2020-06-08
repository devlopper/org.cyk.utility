package org.cyk.utility.client.controller.web;

import java.io.Serializable;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.context.ContextHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.cyk.utility.client.controller.AbstractSystemContextListener;
import org.cyk.utility.client.controller.component.theme.AbstractThemeClassGetterImpl;
import org.cyk.utility.system.node.SystemNodeClient;

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
		initializeFromStatic1(context);
		super.__initialize__(context);
		initializeFromStatic2(__getThemeClass__());
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
	
	public static void initializeFromStatic(ServletContext context,Class<?> themeClass) {
		initializeFromStatic1(context);
		AbstractSystemContextListener.initializeFromStatic();
		initializeFromStatic2(themeClass);
	}
	
	public static void initializeFromStatic1(ServletContext context) {
		SystemNodeClient.DEPLOYMENT.setUniformResourceIdentifierPathRoot(context.getContextPath());
		org.cyk.utility.__kernel__.identifier.resource.Component.PATH_ROOT = context.getContextPath();		
		ContextHelper.set(context);
	}
	
	public static void initializeFromStatic2(Class<?> themeClass) {
		AbstractThemeClassGetterImpl.CLASS_NAME = ConfigurationHelper.getValueAsString(VariableName.USER_INTERFACE_THEME_CLASS_NAME);
		if(StringHelper.isBlank(AbstractThemeClassGetterImpl.CLASS_NAME))
			AbstractThemeClassGetterImpl.CLASS = themeClass;
		else
			AbstractThemeClassGetterImpl.CLASS = ClassHelper.getByName(AbstractThemeClassGetterImpl.CLASS_NAME);
	}
	
}
