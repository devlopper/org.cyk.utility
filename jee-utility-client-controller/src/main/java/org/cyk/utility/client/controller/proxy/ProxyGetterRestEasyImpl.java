package org.cyk.utility.client.controller.proxy;

import java.io.Serializable;

import javax.ws.rs.core.UriBuilder;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class ProxyGetterRestEasyImpl extends AbstractProxyGetterImpl implements ProxyGetterRestEasy,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Object ____execute____(String uri,Class<?> aClass) throws Exception {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(UriBuilder.fromPath(uri));
		return target.proxy(aClass);
	}
	
	@Override
	public ProxyGetterRestEasy execute() {
		return (ProxyGetterRestEasy) super.execute();
	}
	
	@Override
	public ProxyGetterRestEasy execute(Class<?> aClass) {
		return (ProxyGetterRestEasy) super.execute();
	}
	
	@Override
	public ProxyGetterRestEasy setClazz(Class<?> aClass) {
		return (ProxyGetterRestEasy) super.setClazz(aClass);
	}
	
}
