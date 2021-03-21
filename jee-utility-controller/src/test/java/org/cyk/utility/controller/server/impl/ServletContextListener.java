package org.cyk.utility.controller.server.impl;

import java.io.Serializable;

import javax.servlet.ServletContext;

import org.cyk.utility.persistence.server.hibernate.Initializer;
import org.cyk.utility.server.deployment.AbstractServletContextListener;

public class ServletContextListener extends AbstractServletContextListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(ServletContext context) {		
		super.__initialize__(context);
		Initializer.initialize();
	}
}