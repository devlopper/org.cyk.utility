package org.cyk.utility.server.representation.impl;

import java.io.IOException;
import java.io.Serializable;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import org.cyk.utility.log.Log;

@Provider
public class ContainerResponseFilterImpl extends AbstractContainerFilter implements ContainerResponseFilter,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
		Log log = __injectLog__(containerRequestContext,"filter", "Response");
		log.getMessageBuilder(Boolean.TRUE).addParameter("Status", containerResponseContext.getStatus());
		log.execute();
	}
	
}