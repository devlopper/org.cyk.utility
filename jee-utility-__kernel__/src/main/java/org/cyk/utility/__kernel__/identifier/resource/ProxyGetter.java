package org.cyk.utility.__kernel__.identifier.resource;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public interface ProxyGetter {

	<T> T get(Class<T> klass,URI uri);
	
	default <T> T get(Class<T> klass,ProxyUniformResourceIdentifierGetter uniformResourceIdentifierGetter) {
		if(klass == null)
			return null;
		if(uniformResourceIdentifierGetter == null)
			uniformResourceIdentifierGetter = ProxyUniformResourceIdentifierGetter.INSTANCE;
		return get(klass,uniformResourceIdentifierGetter.get(klass));
	}
	
	default <T> T get(Class<T> klass) {
		if(klass == null)
			return null;
		return get(klass,ProxyUniformResourceIdentifierGetter.INSTANCE);
	}
	
	ProxyGetter INSTANCE = new ProxyGetter() {
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
	};
	
}
