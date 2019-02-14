package org.cyk.utility.server.representation.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.system.node.SystemNodeServer;

@ApplicationPath("/")
@Path("/")
@ApplicationScoped
public class Application extends javax.ws.rs.core.Application {

	@GET
	@Path("/")
	@Produces(javax.ws.rs.core.MediaType.TEXT_PLAIN)
	public String get() {
		return DependencyInjection.inject(SystemNodeServer.class).getName()+" REST API is running. Time is "+new java.util.Date();
	}
	
}