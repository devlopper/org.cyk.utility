package org.cyk.utility.server.representation.test;

import org.cyk.utility.server.representation.RepresentationEntity;

public abstract class AbstractTestRepresentationCreateIntegrationImpl extends AbstractTestRepresentationTransactionIntegrationImpl implements TestRepresentationCreateIntegration {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void __perform__(Object object) throws Exception {
		@SuppressWarnings("rawtypes")
		Class<RepresentationEntity> aClass = (Class<RepresentationEntity>)__getActionableSingleton__(object);
		__inject__(aClass).createOne(object);
		addGarbagesArray(object);
	}
	
}
