package org.cyk.utility.service.client;

import java.io.IOException;
import java.util.logging.Level;

import javax.ws.rs.client.ClientRequestContext;

import org.cyk.utility.__kernel__.log.LogHelper;

public class ClientRequestFilter implements javax.ws.rs.client.ClientRequestFilter {

	public static Level LOG_LEVEL = Level.FINE;
	
	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		LogHelper.log(String.format("%s %s", requestContext.getMethod(),requestContext.getUri()),LOG_LEVEL, getClass());
	}

}