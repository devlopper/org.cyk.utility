package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.annotation.Web;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.client.controller.session.SessionAttributeGetter;
import org.cyk.utility.client.controller.session.SessionAttributeSetter;

@WebListener
public class ServletContextListener extends AbstractObject implements javax.servlet.ServletContextListener,Objectable,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		DependencyInjection.setQualifierClass(SessionAttributeSetter.class, Web.class);
		DependencyInjection.setQualifierClass(SessionAttributeGetter.class, Web.class);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
