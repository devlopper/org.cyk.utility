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
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.value.ValueHelper;

import io.swagger.annotations.Api;
import io.swagger.jaxrs.config.BeanConfig;

@ApplicationPath(ApplicationProgrammingInterface.PATH) @Path("/")
@ApplicationScoped
@Api
public class ApplicationProgrammingInterface extends javax.ws.rs.core.Application implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/*
	public ApplicationProgrammingInterface() {
		if(ConfigurationHelper.is(VariableName.SWAGGER_ENABLED)) {
			BeanConfig beanConfig = new BeanConfig();
			beanConfig.setVersion(ValueHelper.defaultToIfBlank(ConfigurationHelper.getValueAsString(VariableName.SYSTEM_VERSION),"version not defined"));
			beanConfig.setSchemes(new String[] { "http" });
			String host = ValueHelper.defaultToIfBlank(ConfigurationHelper.getValueAsString(VariableName.SYSTEM_HOST),"localhost");
			String port = ValueHelper.defaultToIfBlank(ConfigurationHelper.getValueAsString(VariableName.SYSTEM_PORT),"8080");
			beanConfig.setHost(host+":"+port);
			beanConfig.setBasePath("/"+ConfigurationHelper.getValueAsString(VariableName.SYSTEM_WEB_CONTEXT, null, null,ConstantEmpty.STRING)+PATH);
			
			beanConfig.setResourcePackage(ConfigurationHelper.getValueAsString(VariableName.SWAGGER_BEAN_CONFIG_RESOURCE_PACKAGE));
			
			beanConfig.setTitle(ValueHelper.defaultToIfBlank(ConfigurationHelper.getValueAsString(VariableName.SYSTEM_NAME),"System name not defined")
					+" : Application Programming Interface Documentation");
			beanConfig.setDescription("Documentation of API using Swagger");
			beanConfig.setScan(true);	
		}		
	}
	*/
	
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