package org.cyk.utility.client.controller.web;

import java.io.Serializable;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletContextListenerImpl implements ServletContextListener,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}

}
