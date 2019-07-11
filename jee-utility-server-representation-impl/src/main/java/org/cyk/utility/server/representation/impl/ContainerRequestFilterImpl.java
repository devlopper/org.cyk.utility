package org.cyk.utility.server.representation.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.cyk.utility.log.Log;
import org.cyk.utility.time.DurationBuilder;

@Dependent @Provider
public class ContainerRequestFilterImpl extends AbstractContainerFilter implements ContainerRequestFilter,Serializable {
	private static final long serialVersionUID = 1L;

	public static final Map<ContainerRequestContext,DurationBuilder> DURATION_BUILDER_MAP = new HashMap<>();
	
	public void filter(ContainerRequestContext containerRequestContext) throws IOException {
		DURATION_BUILDER_MAP.put(containerRequestContext, __inject__(DurationBuilder.class).setBeginToNow());
		Log log = __injectLog__(containerRequestContext,"filter","Request");
		
		log.execute();
	}
	
}