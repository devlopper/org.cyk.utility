package org.cyk.utility.server.representation.test;

import javax.ws.rs.core.Response;

import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.RepresentationLayer;

public abstract class AbstractTestRepresentationCreateIntegrationImpl extends AbstractTestRepresentationTransactionIntegrationImpl implements TestRepresentationCreateIntegration {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setName("create representation entity");
		__expectedResponseStatusCode__ = Response.Status.CREATED.getStatusCode();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void ____perform____(Object object) throws Exception {
		@SuppressWarnings("rawtypes")
		RepresentationEntity representation = __inject__(RepresentationLayer.class).injectInterfaceClassFromEntityClass(object.getClass());
		__response__ = representation.createOne(object);
		
		if(Response.Status.CREATED.getStatusCode() == __response__.getStatus()) {
			addGarbagesArray(object);
		}
	}
	
}
