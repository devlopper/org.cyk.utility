package org.cyk.utility.client.controller.proxy;

import java.io.Serializable;

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
				ProxyClassUniformResourceIdentifierStringBuilder classUniformResourceIdentifierStringBuilder = getFunction().getClassUniformResourceIdentifierString();
				String uri = classUniformResourceIdentifierStringBuilder.execute().getOutput();
				ResteasyClient client = new ResteasyClientBuilder().build();
				ResteasyWebTarget target = client.target(UriBuilder.fromPath(uri));
				Class<?> aClass = getFunction().getClazz();
				Object proxy = target.proxy(aClass);
				setOutput(proxy);
			}
		});
	}
	
}