package org.cyk.utility.server.representation;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.apache.commons.io.IOUtils;

@Dependent @Provider
public class ContainerRequestFilterImpl implements ContainerRequestFilter,Serializable {
	private static final long serialVersionUID = 1L;

	public void filter(ContainerRequestContext containerRequestContext) throws IOException {
		if(containerRequestContext.getMethod().equals("POST")) {
			byte[] bytes = IOUtils.toByteArray(containerRequestContext.getEntityStream());
			System.out.println("REQUEST BODY : "+new String(bytes));
			containerRequestContext.setEntityStream(new ByteArrayInputStream(bytes));
		}
	}
	
}