package org.cyk.utility.__kernel__.identifier.resource;

import java.io.Serializable;
import java.net.URI;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.UriBuilder;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

@ApplicationScoped
public class ProxyGetterResteasyImpl extends AbstractProxyGetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public <T> T get(Class<T> klass,URI uri) {
		if(klass == null || uri == null)
			return null;
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(UriBuilder.fromUri(uri));
		try {
			return target.proxy(klass);
		} catch (Exception exception) {
			throw new RuntimeException("Cannot get proxy for class <<"+klass+">> at uri <<"+uri+">>",exception);
		}
	}
	
}
