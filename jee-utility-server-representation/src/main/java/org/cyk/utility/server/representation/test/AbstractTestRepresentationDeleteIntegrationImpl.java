package org.cyk.utility.server.representation.test;

public abstract class AbstractTestRepresentationDeleteIntegrationImpl extends AbstractTestRepresentationTransactionIntegrationImpl implements TestRepresentationDeleteIntegration {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __perform__(Object object) throws Exception {
		//__inject__(Representation.class).delete(object);
	}
	
}
