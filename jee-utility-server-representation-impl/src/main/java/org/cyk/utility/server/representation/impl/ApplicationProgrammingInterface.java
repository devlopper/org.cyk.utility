package org.cyk.utility.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.configuration.VariableName;

import io.swagger.annotations.Api;

@ApplicationPath(ApplicationProgrammingInterface.PATH) @Path("/")
@ApplicationScoped
@Api
public class ApplicationProgrammingInterface extends javax.ws.rs.core.Application implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@GET
	@Path("/")
	@Produces(javax.ws.rs.core.MediaType.TEXT_PLAIN)
	public String get() {
		return ConfigurationHelper.getValueAsString(VariableName.SYSTEM_NAME)+" Application Programming Interface. Time is "+new java.util.Date();
	}
	
	@POST
	@Path("/__internal__/data/load")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response loadData() {
		return DependencyInjection.inject(DataLoader.class).execute().getOutput();
	}
	
	/**/
	
	public static final String PATH = "/api";
	
}