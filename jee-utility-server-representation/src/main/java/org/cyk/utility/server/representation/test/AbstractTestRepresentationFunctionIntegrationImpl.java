package org.cyk.utility.server.representation.test;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.server.representation.Representation;
import org.cyk.utility.test.AbstractTestSystemFunctionIntegrationImpl;

public abstract class AbstractTestRepresentationFunctionIntegrationImpl extends AbstractTestSystemFunctionIntegrationImpl implements TestRepresentationFunctionIntegration {
	private static final long serialVersionUID = 1L;

	private URI uri;
	private Object responseStatusCode,responseEntity;
	private Collection<ExpectedMessageDto> expectedResponseEntityMessages;
	
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
	public Object getExpectedResponseStatusCode() {
		return responseStatusCode;
	}
	
	@Override
	public TestRepresentationFunctionIntegration setExpectedResponseStatusCode(Object responseStatusCode) {
		this.responseStatusCode = responseStatusCode;
		return this;
	}
	
	@Override
	public Object getExpectedResponseEntity() {
		return responseEntity;
	}
	
	@Override
	public TestRepresentationFunctionIntegration setExpectedResponseEntity(Object responseEntity) {
		this.responseEntity = responseEntity;
		return this;
	}
	
	@Override
	public TestRepresentationFunctionIntegration setExpectedResponseEntityMessages(Collection<ExpectedMessageDto> messages) {
		this.expectedResponseEntityMessages = messages;
		return this;
	}
	
	@Override
	public Collection<ExpectedMessageDto> getExpectedResponseEntityMessages() {
		return expectedResponseEntityMessages;
	}
	
	@Override
	public TestRepresentationFunctionIntegration addExpectedResponseEntityMessages(Collection<ExpectedMessageDto> messages) {
		expectedResponseEntityMessages = __injectCollectionHelper__().add(ArrayList.class, expectedResponseEntityMessages, Boolean.TRUE, messages);
		return this;
	}
	
	@Override
	public TestRepresentationFunctionIntegration addExpectedResponseEntityMessages(ExpectedMessageDto... messages) {
		addExpectedResponseEntityMessages(__injectCollectionHelper__().instanciate(messages));
		return this;
	}
	
	@Override
	public TestRepresentationFunctionIntegration addObjects(Object... objects) {
		return (TestRepresentationFunctionIntegration) super.addObjects(objects);
	}
	
	@Override
	public TestRepresentationFunctionIntegration addObjectIdentifiers(Object... objectIdentifiers) {
		return (TestRepresentationFunctionIntegration) super.addObjectIdentifiers(objectIdentifiers);
	}
	
	@Override
	public TestRepresentationFunctionIntegration addObjectsToBeCreatedArray(Object... objects) {
		return (TestRepresentationFunctionIntegration) super.addObjectsToBeCreatedArray(objects);
	}
	
	@Override
	protected void __createOne__(Object object) throws Exception {
		__inject__(Representation.class).create(object);
	}
	
	@Override
	protected void __deleteOne__(Object object) {
		__inject__(Representation.class).delete(object);
	}

}
