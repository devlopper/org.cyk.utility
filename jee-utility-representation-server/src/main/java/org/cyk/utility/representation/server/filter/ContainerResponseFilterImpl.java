package org.cyk.utility.representation.server.filter;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;

import javax.enterprise.context.Dependent;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.log.LogMessages;
import org.cyk.utility.__kernel__.time.TimeHelper;

@Dependent @Provider
public class ContainerResponseFilterImpl extends AbstractContainerFilter implements ContainerResponseFilter,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
		setCORS(containerRequestContext, containerResponseContext);
		LogMessages logMessages = getLogMessages(containerRequestContext, "Response");
		logMessages.add(String.format("Status : %s", containerResponseContext.getStatus()));
		Long from = (Long) containerRequestContext.getProperty(LocalDateTime.class.getName());
		if(from != null)
			logMessages.add(String.format("Duration : %s", TimeHelper.formatDuration(System.currentTimeMillis() - from)));
		LogHelper.log(logMessages);
	}

	private static void setCORS(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) {
		containerRequestContext.getHeaders().add("Access-Control-Allow-Origin", "*");
		containerRequestContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
		containerRequestContext.getHeaders().add("Access-Control-Allow-Headers","origin, content-type, accept, authorization");
		containerRequestContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
	}
}