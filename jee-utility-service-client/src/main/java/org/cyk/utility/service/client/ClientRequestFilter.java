package org.cyk.utility.service.client;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;

import org.cyk.utility.__kernel__.log.LogHelper;

public class ClientRequestFilter implements javax.ws.rs.client.ClientRequestFilter {

	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		LogHelper.logFine(String.format("%s %s", requestContext.getMethod(),requestContext.getUri()), getClass());
	}

}