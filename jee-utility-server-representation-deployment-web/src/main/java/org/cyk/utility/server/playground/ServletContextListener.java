package org.cyk.utility.server.playground;

import java.io.Serializable;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;

import org.cyk.utility.server.deployment.AbstractServletContextListener;
import org.cyk.utility.server.representation.api.MyEntityRepresentation;
import org.cyk.utility.server.representation.api.NodeRepresentation;
import org.cyk.utility.server.representation.entities.MyEntityDto;
import org.cyk.utility.server.representation.entities.NodeDto;

@WebListener
public class ServletContextListener extends AbstractServletContextListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(ServletContext context) {
		super.__initialize__(context);
		__inject__(ApplicationScopeLifeCycleListenerEntities.class).initialize(null);
		__inject__(MyEntityRepresentation.class).createOne(new MyEntityDto().setCode("c01").setName("Test"));
		__inject__(NodeRepresentation.class).createOne(new NodeDto().setIdentifier("1").setCode("c01").setName("n01"));
	}
	
}
