package org.cyk.utility.server.representation.impl;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import org.cyk.utility.log.Log;
import org.cyk.utility.time.DurationStringBuilder;

@Dependent @Provider
public class ContainerResponseFilterImpl extends AbstractContainerFilter implements ContainerResponseFilter,Serializable {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
		Log log = __injectLog__(containerRequestContext,"filter", "Response");
		log.getMessageBuilder(Boolean.TRUE).addParameter("Status", containerResponseContext.getStatus())
			.addParameter("Duration", __inject__(DurationStringBuilder.class)
					.setDurationBuilder(ContainerRequestFilterImpl.DURATION_BUILDER_MAP.remove(containerResponseContext).setEndNow()).execute().getOutput());
		log.execute();
	}
	
}