package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import javax.servlet.ServletContextEvent;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.client.controller.component.theme.AbstractThemeClassGetterImpl;
import org.cyk.utility.client.controller.component.theme.ThemeClassGetterImpl;
import org.cyk.utility.client.controller.web.Constant;
import org.cyk.utility.client.controller.web.jsf.primefaces.theme.ThemeDesktopDefault;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.system.SystemHelper;

public abstract class AbstractServletContextListener extends AbstractObject implements javax.servlet.ServletContextListener,Objectable,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		String themeClassName = servletContextEvent.getServletContext().getInitParameter("cyk.user.interface.theme.class.name");
		if(__inject__(StringHelper.class).isNotBlank(themeClassName)) {
			AbstractThemeClassGetterImpl.CLASS = __inject__(ClassHelper.class).getByName(themeClassName);
		}else
			ThemeClassGetterImpl.CLASS_NAME = ThemeDesktopDefault.class.getName();
		
		__listenContextInitialized__(servletContextEvent);
		String theme = __inject__(SystemHelper.class).getProperty(Constant.CONTEXT_PARAMETER_NAME_THEME_IDENTIFIER, Boolean.TRUE);
		if(__inject__(StringHelper.class).isNotBlank(theme)) {
			servletContextEvent.getServletContext().setInitParameter(Constant.CONTEXT_PARAMETER_NAME_THEME, theme);
			System.out.println("AbstractServletContextListener.contextInitialized() ::: THEME ::: "+theme);
			__logConfig__(Constant.CONTEXT_PARAMETER_NAME_THEME+" : "+theme);
		}
		__inject__(ApplicationScopeLifeCycleListener.class).initialize(null);
	}
	
	protected abstract void __listenContextInitialized__(ServletContextEvent servletContextEvent);

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		__listenContextDestroyed__(servletContextEvent);
	}
	
	protected abstract void __listenContextDestroyed__(ServletContextEvent servletContextEvent);

	/**/
	
	
}
