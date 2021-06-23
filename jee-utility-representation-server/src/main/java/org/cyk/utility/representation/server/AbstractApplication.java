package org.cyk.utility.representation.server;

import java.io.Serializable;
import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.rest.RestHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.variable.VariableName;

public abstract class AbstractApplication extends javax.ws.rs.core.Application implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public AbstractApplication() {
		RestHelper.APPLICATION_PATH.set(PATH);
	}
	
	@GET
	@Path("/")
	@Produces(javax.ws.rs.core.MediaType.TEXT_PLAIN)
	public String get() {
		return ConfigurationHelper.getValueAsString(VariableName.SYSTEM_NAME)
				+" Application Programming Interface."
				+" Build Timestamp : "+ConfigurationHelper.getValueAsString(VariableName.SYSTEM_TIMESTAMP_AS_STRING)
				+" Time is "+new java.util.Date();
	}
	
	/**/
	
	public static String buildResourcePath(String resourceContainerPath,String resourcePath) {
		return String.format(RESOURCE_PATH_FORMAT, PATH,StringHelper.addToBeginIfDoesNotStartWith(resourceContainerPath, "/")
				,StringHelper.addToBeginIfDoesNotStartWith(resourcePath,"/"));
	}
	
	public static String buildResourceIdentifier(String resourceContainerPath,String resourcePath) {
		HttpServletRequest httpServletRequest = DependencyInjection.inject(HttpServletRequest.class);
		URI uri = URI.create(httpServletRequest.getRequestURL().toString());
		String context = StringUtils.substringBefore(httpServletRequest.getRequestURI(), PATH);
		return String.format(RESOURCE_IDENTIFIER_FORMAT, uri.getScheme(),uri.getHost(),uri.getPort() == -1 ? 80 : uri.getPort(),context,buildResourcePath(resourceContainerPath, resourcePath));
	}
	
	/**/
	
	public static final String PATH = "/api";
	public static final String RESOURCE_PATH_FORMAT = "%s%s%s";
	public static final String RESOURCE_IDENTIFIER_FORMAT = "%s://%s:%s%s%s";
}