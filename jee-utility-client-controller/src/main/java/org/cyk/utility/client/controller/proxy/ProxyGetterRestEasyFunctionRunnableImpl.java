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
				Class<?> clazz = getFunction().getClazz();
				URI uri = __inject__(ProxyClassUniformResourceIdentifierGetter.class).setStringBuilder(getFunction().getClassUniformResourceIdentifierString())
						.setClazz(clazz).execute().getOutput();
				ResteasyClient client = new ResteasyClientBuilder().build();
				ResteasyWebTarget target = client.target(UriBuilder.fromUri(uri));
				try {
					Object proxy = target.proxy(clazz);
					setOutput(proxy);
				} catch (Exception exception) {
					exception.printStackTrace();
					throw new RuntimeException("Cannot get proxy for CLASS <<"+clazz+">> at URI <<"+uri+">>");
				}
			}
		});
	}
	
}