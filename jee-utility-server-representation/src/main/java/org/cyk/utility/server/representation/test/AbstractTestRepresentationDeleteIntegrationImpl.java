package org.cyk.utility.server.representation.test;

import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.RepresentationLayer;

public abstract class AbstractTestRepresentationDeleteIntegrationImpl extends AbstractTestRepresentationTransactionIntegrationImpl implements TestRepresentationDeleteIntegration {
	private static final long serialVersionUID = 1L;

	@Override
	protected void ____perform____(Object object) throws Exception {
		@SuppressWarnings("rawtypes")
		RepresentationEntity representation = __inject__(RepresentationLayer.class).injectInterfaceClassFromEntityClass(getObjectClass());
		__response__ = representation.deleteOne(object.toString(),getIdentifierValueUsageType().name());
	}
	
}
