package org.cyk.utility.representation.server.filter;

import java.io.Serializable;
import java.util.stream.Collectors;

import javax.ws.rs.container.ContainerRequestContext;

import org.cyk.utility.__kernel__.log.LogMessages;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public abstract class AbstractContainerFilter extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected LogMessages getLogMessages(ContainerRequestContext containerRequestContext,String marker) {
		LogMessages logMessages = new LogMessages();
		logMessages.setLoggable(Boolean.TRUE).setLevel(java.util.logging.Level.INFO);
		String path = containerRequestContext.getUriInfo().getPath();
		logMessages.add(marker,containerRequestContext.getMethod(),path);
		
		if(containerRequestContext.getUriInfo().getQueryParameters()!=null && !containerRequestContext.getUriInfo().getQueryParameters().isEmpty())
			logMessages.add(
					containerRequestContext.getUriInfo().getQueryParameters().entrySet().stream().map(x -> x.getKey()+":"+x.getValue()).collect(Collectors.toList()));
		if("/cyk/entity/reader/read".equals(path)) {
			
		}
		if(containerRequestContext.getLength() > -1)
			logMessages.add(String.format("Size : %s", containerRequestContext.getLength()));
		return logMessages;
	}
}
