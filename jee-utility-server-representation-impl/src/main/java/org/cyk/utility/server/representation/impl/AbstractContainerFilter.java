package org.cyk.utility.server.representation.impl;

import java.io.Serializable;
import java.util.stream.Collectors;

import javax.ws.rs.container.ContainerRequestContext;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.log.Log;

public abstract class AbstractContainerFilter extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Log __injectLog__(ContainerRequestContext containerRequestContext,String methodName,String marker) {
		Log log = __inject__(Log.class);
		String path = containerRequestContext.getUriInfo().getPath();		
		log.setSourceClassName(getClass().getName()).setSourceMethodName(methodName).addMarkers("Server",marker);
		log.getMessageBuilder(Boolean.TRUE).addParameter("Method", containerRequestContext.getMethod())
		.addParameter("Path", path);
		if(containerRequestContext.getUriInfo().getQueryParameters()!=null && !containerRequestContext.getUriInfo().getQueryParameters().isEmpty())
			log.getMessageBuilder(Boolean.TRUE).addParameter("Parameters"
					, containerRequestContext.getUriInfo().getQueryParameters().entrySet().stream().map(x -> x.getKey()+":"+x.getValue()).collect(Collectors.toList()));
		if("/cyk/entity/reader/read".equals(path)) {
			
		}
		if(containerRequestContext.getLength() > -1)
			log.getMessageBuilder(Boolean.TRUE).addParameter("Size", containerRequestContext.getLength());
		return log;
	}
	
}
