package org.cyk.utility.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.variable.VariableName;

@ApplicationPath(ApplicationProgrammingInterface.PATH) @Path("/")
@ApplicationScoped
public class ApplicationProgrammingInterface extends javax.ws.rs.core.Application implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@GET
	@Path("/")
	@Produces(javax.ws.rs.core.MediaType.TEXT_PLAIN)
	public String get() {
		return ConfigurationHelper.getValueAsString(VariableName.SYSTEM_NAME)+" Application Programming Interface. Time is "+new java.util.Date();
	}
	
	/**/
	
	public static final String PATH = "/api";
	
}