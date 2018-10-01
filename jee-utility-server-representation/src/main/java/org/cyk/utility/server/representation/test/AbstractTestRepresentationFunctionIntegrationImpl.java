package org.cyk.utility.server.representation.test;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.server.representation.Representation;
import org.cyk.utility.test.AbstractTestSystemFunctionIntegrationImpl;

public abstract class AbstractTestRepresentationFunctionIntegrationImpl extends AbstractTestSystemFunctionIntegrationImpl implements TestRepresentationFunctionIntegration {
	private static final long serialVersionUID = 1L;

	private URI uri;
	private Class<?> expectedResponseEntityClass;
	private Object responseStatusCode,responseEntity;
	private Collection<ExpectedMessageDto> expectedResponseEntityDtoMessages;
	
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
	public Class<?> getExpectedResponseEntityClass() {
		return expectedResponseEntityClass;
	}
	
	@Override
	public TestRepresentationFunctionIntegration setExpectedResponseEntityClass(Class<?> expectedResponseEntityClass) {
		this.expectedResponseEntityClass = expectedResponseEntityClass;
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
	public TestRepresentationFunctionIntegration setExpectedResponseEntityDtoMessages(Collection<ExpectedMessageDto> messages) {
		this.expectedResponseEntityDtoMessages = messages;
		return this;
	}
	
	@Override
	public Collection<ExpectedMessageDto> getExpectedResponseEntityDtoMessages() {
		return expectedResponseEntityDtoMessages;
	}
	
	@Override
	public TestRepresentationFunctionIntegration addExpectedResponseEntityDtoMessages(Collection<ExpectedMessageDto> messages) {
		expectedResponseEntityDtoMessages = __injectCollectionHelper__().add(ArrayList.class, expectedResponseEntityDtoMessages, Boolean.TRUE, messages);
		return this;
	}
	
	@Override
	public TestRepresentationFunctionIntegration addExpectedResponseEntityDtoMessages(ExpectedMessageDto... messages) {
		addExpectedResponseEntityDtoMessages(__injectCollectionHelper__().instanciate(messages));
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
