package org.cyk.utility.client.controller.proxy;

import java.io.Serializable;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class ProxyGetterRestEasyFunctionRunnableImpl extends AbstractFunctionRunnableImpl<ProxyGetter> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public ProxyGetterRestEasyFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				/*
				String uri = null;
				if(__inject__(StringHelper.class).isBlank(uri)) {
					uri = __inject__(ProxyClassUniformResourceIdentifierStringProvider.class).execute().getOutput();
				}
				
				if(__inject__(StringHelper.class).isBlank(uri)) {
					ProxyClassUniformResourceIdentifierStringBuilder classUniformResourceIdentifierStringBuilder = getFunction().getClassUniformResourceIdentifierString(Boolean.TRUE);			
					uri = classUniformResourceIdentifierStringBuilder.execute().getOutput();
					uri = System.getenv("ci.gouv.dgbf.system.actor.server.url");
				}
				ResteasyClient client = new ResteasyClientBuilder().build();
				ResteasyWebTarget target = client.target(UriBuilder.fromPath(uri));
				Class<?> aClass = getFunction().getClazz();
				Object proxy = target.proxy(aClass);
				setOutput(proxy);
				*/
				URI uri = __inject__(ProxyClassUniformResourceIdentifierGetter.class).setStringBuilder(getFunction().getClassUniformResourceIdentifierString())
						.setClazz(getFunction().getClazz()).execute().getOutput();
				ResteasyClient client = new ResteasyClientBuilder().build();
				ResteasyWebTarget target = client.target(UriBuilder.fromUri(uri));
				Class<?> aClass = getFunction().getClazz();
				Object proxy = target.proxy(aClass);
				setOutput(proxy);
			}
		});
	}
	
}