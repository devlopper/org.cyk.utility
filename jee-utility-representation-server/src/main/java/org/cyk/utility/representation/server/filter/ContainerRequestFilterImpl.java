package org.cyk.utility.representation.server.filter;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;

import javax.enterprise.context.Dependent;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.log.LogMessages;

@Dependent @Provider
public class ContainerRequestFilterImpl extends AbstractContainerFilter implements ContainerRequestFilter,Serializable {
	private static final long serialVersionUID = 1L;

	public void filter(ContainerRequestContext containerRequestContext) throws IOException {
		containerRequestContext.setProperty(LocalDateTime.class.getName(), System.currentTimeMillis());
		LogMessages logMessages = getLogMessages(containerRequestContext, "Request");
		LogHelper.log(logMessages, getClass());
	}	
}