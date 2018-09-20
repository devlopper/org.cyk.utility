package org.cyk.utility.server.representation.test;

import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.RepresentationLayer;

public abstract class AbstractTestRepresentationCreateIntegrationImpl extends AbstractTestRepresentationTransactionIntegrationImpl implements TestRepresentationCreateIntegration {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void __perform__(Object object) throws Exception {
		@SuppressWarnings("rawtypes")
		RepresentationEntity representation = __inject__(RepresentationLayer.class).injectInterfaceClassFromEntityClass(object.getClass());
		representation.createOne(object);
		addGarbagesArray(object);
	}
	
}
