package org.cyk.utility.server.representation.test;

import org.cyk.utility.server.representation.RepresentationEntity;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public abstract class AbstractTestRepresentationCreateIntegrationImpl extends AbstractTestRepresentationTransactionIntegrationImpl implements TestRepresentationCreateIntegration {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void __perform__(Object object) throws Exception {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(getUniformResourceIdentifier());
		@SuppressWarnings("rawtypes")
		RepresentationEntity proxy = (RepresentationEntity) target.proxy((Class<?>)__getActionableSingleton__(object.getClass()));
		proxy.createOne(object);
		addGarbagesArray(object);
	}
	
}
