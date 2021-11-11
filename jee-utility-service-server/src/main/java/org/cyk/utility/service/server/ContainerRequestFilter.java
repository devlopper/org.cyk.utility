package org.cyk.utility.service.server;

import java.io.IOException;

import org.cyk.utility.__kernel__.log.LogHelper;

@javax.ws.rs.ext.Provider
public class ContainerRequestFilter implements javax.ws.rs.container.ContainerRequestFilter {

	@Override
	public void filter(javax.ws.rs.container.ContainerRequestContext requestContext) throws IOException {
		LogHelper.logFine(String.format("%s %s", requestContext.getMethod(),requestContext.getUriInfo().getRequestUri()), getClass());
	}
}