package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.annotation.Web;
import org.cyk.utility.client.controller.session.SessionAttributeGetter;
import org.cyk.utility.client.controller.session.SessionAttributeSetter;

@WebListener
public class ServletContextListener extends AbstractServletContextListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenContextInitialized__(ServletContextEvent servletContextEvent) {
		DependencyInjection.setQualifierClass(SessionAttributeSetter.class, Web.class);
		DependencyInjection.setQualifierClass(SessionAttributeGetter.class, Web.class);
	}

	@Override
	protected void __listenContextDestroyed__(ServletContextEvent servletContextEvent) {
		
	}

}
