package org.cyk.utility.server.representation;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@ApplicationPath("/")
@Path("/")
@ApplicationScoped
//TODO should be moved to another project folder called representation-impl like persistence-impl
public class MyApplication extends javax.ws.rs.core.Application {

	@GET
	@Path("/")
	@Produces(javax.ws.rs.core.MediaType.TEXT_PLAIN)
	public String get() {
		//TODO this message could come from database
		return "Your API is running. Time is "+new java.util.Date();
	}
	
	@Override
	public Set<Class<?>> getClasses() {
	    Set<Class<?>> classes = new HashSet<Class<?>>();
	    classes.add(MyRepresentationImpl.class);
	    return classes;
	}
	
}