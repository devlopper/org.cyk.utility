package org.cyk.utility.server.representation.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@ApplicationPath("/")
@Path("/")
@ApplicationScoped
public class Application extends javax.ws.rs.core.Application {

	@GET
	@Path("/")
	@Produces(javax.ws.rs.core.MediaType.TEXT_PLAIN)
	public String get() {
		//TODO this message could come from database
		return "Your API is running. Time is "+new java.util.Date();
	}
	
}