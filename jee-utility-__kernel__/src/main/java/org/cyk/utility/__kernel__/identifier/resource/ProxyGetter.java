package org.cyk.utility.__kernel__.identifier.resource;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.util.logging.Level;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.UriBuilder;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public interface ProxyGetter {

	<T> T get(Class<T> klass,URI uri);
	
	<T> T get(Class<T> klass,ProxyUniformResourceIdentifierGetter uniformResourceIdentifierGetter);
	
	<T> T get(Class<T> klass);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements ProxyGetter,Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		public <T> T get(Class<T> klass, ProxyUniformResourceIdentifierGetter uniformResourceIdentifierGetter) {
			if(klass == null)
				return null;
			if(uniformResourceIdentifierGetter == null)
				uniformResourceIdentifierGetter = ProxyUniformResourceIdentifierGetter.getInstance();
			return get(klass,uniformResourceIdentifierGetter.get(klass));
		}
		
		@Override
		public <T> T get(Class<T> klass) {
			if(klass == null)
				return null;
			return get(klass,ProxyUniformResourceIdentifierGetter.getInstance());
		}
		
		public abstract static class AbstractResteasyImpl extends AbstractImpl implements Serializable {
			private static final long serialVersionUID = 1L;

			@Override
			public <T> T get(Class<T> klass,URI uri) {
				if(klass == null || uri == null)
					return null;
				ResteasyClient client = new ResteasyClientBuilder().build();
				client.register(new ClientRequestFilterImpl());
				ResteasyWebTarget target = client.target(UriBuilder.fromUri(uri));
				try {
					return target.proxy(klass);
				} catch (Exception exception) {
					throw new RuntimeException("Cannot get proxy for class <<"+klass+">> at uri <<"+uri+">>",exception);
				}
			}
		}
		
		/**/
		
		public static class ClientRequestFilterImpl implements ClientRequestFilter {
			public static Level LOG_LEVEL = Level.INFO;
			public static Boolean LOGGABLE = Boolean.TRUE;
			public static Boolean BODY_LOGGABLE = Boolean.TRUE;
		    @Override
		    public void filter(ClientRequestContext requestContext) throws IOException {
		    	if(Boolean.TRUE.equals(LOGGABLE)) {
		    		StringBuilder stringBuilder = new StringBuilder();
		    		stringBuilder.append(requestContext.getMethod()+" "+requestContext.getUri().getPath());
		    		if(StringHelper.isNotBlank(requestContext.getUri().getQuery()))
		    			stringBuilder.append("?"+requestContext.getUri().getQuery());
		    		if(Boolean.TRUE.equals(BODY_LOGGABLE)) {
		    			if(requestContext.getEntity() != null) {
		    				stringBuilder.append(" "+requestContext.getEntity().toString());
		    			}
		    		}
		    		LogHelper.log(stringBuilder.toString(), LOG_LEVEL,getClass());
		    	}
		    }
		}
	}

	/**/
	
	static ProxyGetter getInstance() {
		return Helper.getInstance(ProxyGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}