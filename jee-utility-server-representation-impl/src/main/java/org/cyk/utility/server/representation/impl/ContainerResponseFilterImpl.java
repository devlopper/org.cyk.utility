package org.cyk.utility.server.representation.impl;

import java.io.IOException;
import java.io.Serializable;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.cyk.utility.log.Log;
import org.cyk.utility.time.DurationBuilder;
import org.cyk.utility.time.DurationStringBuilder;

//@Dependent @Provider
@Deprecated
public class ContainerResponseFilterImpl extends AbstractContainerFilter implements ContainerResponseFilter,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
		Log log = __injectLog__(containerRequestContext,"filter", "Response");
		log.getMessageBuilder(Boolean.TRUE).addParameter("Status", containerResponseContext.getStatus());
		DurationBuilder durationBuilder = (DurationBuilder) containerRequestContext.getProperty(DurationBuilder.class.getName());
		if(durationBuilder != null)
			log.getMessageBuilder(Boolean.TRUE).addParameter("Duration", __inject__(DurationStringBuilder.class).setDurationBuilder(durationBuilder.setEndToNow()).execute()
					.getOutput());
		log.execute();
	}
	
}