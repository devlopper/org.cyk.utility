package org.cyk.utility.server.representation.test;

import java.net.URI;

import org.cyk.utility.test.AbstractTestSystemFunctionIntegrationImpl;

public abstract class AbstractTestRepresentationFunctionIntegrationImpl extends AbstractTestSystemFunctionIntegrationImpl implements TestRepresentationFunctionIntegration {
	private static final long serialVersionUID = 1L;

	private URI uri;
	
	@Override
	public URI getUniformResourceIdentifier() {
		return uri;
	}
	
	@Override
	public TestRepresentationFunctionIntegration setUniformResourceIdentifier(String uri) {
		setUniformResourceIdentifier(URI.create(uri));
		return this;
	}
	
	@Override
	public TestRepresentationFunctionIntegration setUniformResourceIdentifier(URI uri) {
		this.uri = uri;
		return this;
	}
	
	@Override
	protected void __createOne__(Object object) throws Exception {
		//__inject__(Representation.class).create(object);
	}
	
	@Override
	protected void __deleteOne__(Object object) {
		//__inject__(Representation.class).delete(object);
	}

}
