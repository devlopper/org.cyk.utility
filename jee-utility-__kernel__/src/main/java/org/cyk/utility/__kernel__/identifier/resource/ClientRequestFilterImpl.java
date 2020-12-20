package org.cyk.utility.__kernel__.identifier.resource;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.string.StringHelper;

public class ClientRequestFilterImpl implements ClientRequestFilter,Serializable {
	public static Level LOG_LEVEL = Level.FINE;
	public static Boolean LOGGABLE = Boolean.FALSE;
	public static Boolean BODY_LOGGABLE = Boolean.FALSE;

	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		if (Boolean.TRUE.equals(LOGGABLE)) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(requestContext.getMethod() + " " + requestContext.getUri().getPath());
			//stringBuilder.append(requestContext.getMethod() + " " + requestContext.getUri());
			if (StringHelper.isNotBlank(requestContext.getUri().getQuery()))
				stringBuilder.append("?" + requestContext.getUri().getQuery());
			if (Boolean.TRUE.equals(BODY_LOGGABLE)) {
				if (requestContext.getEntity() != null) {
					stringBuilder.append(" " + requestContext.getEntity().toString());
				}
			}
			LogHelper.log(stringBuilder.toString(), LOG_LEVEL, getClass());
		}
	}
}