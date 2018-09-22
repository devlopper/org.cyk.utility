package org.cyk.utility.server.representation.impl;

import java.io.IOException;
import java.io.Serializable;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.cyk.utility.log.Log;

@Provider
public class ContainerRequestFilterImpl extends AbstractContainerFilter implements ContainerRequestFilter,Serializable {
	private static final long serialVersionUID = 1L;

	public void filter(ContainerRequestContext containerRequestContext) throws IOException {
		Log log = __injectLog__(containerRequestContext,"filter","Request");
		
		log.execute();
	}
	
}