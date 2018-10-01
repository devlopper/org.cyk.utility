package org.cyk.utility.server.representation.test;

import java.net.URI;
import java.util.Collection;

import org.cyk.utility.test.TestSystemFunctionIntegration;

public interface TestRepresentationFunctionIntegration extends TestSystemFunctionIntegration {

	@Override TestRepresentationFunctionIntegration addObjects(Object... objects);
	@Override TestRepresentationFunctionIntegration addObjectIdentifiers(Object... objectIdentifiers);
	@Override TestRepresentationFunctionIntegration addObjectsToBeCreatedArray(Object... objects);
	
	TestRepresentationFunctionIntegration setUniformResourceIdentifier(URI uri);
	TestRepresentationFunctionIntegration setUniformResourceIdentifier(String uri);
	URI getUniformResourceIdentifier();
	
	TestRepresentationFunctionIntegration setExpectedResponseStatusCode(Object responseStatusCode);
	Object getExpectedResponseStatusCode();
	
	TestRepresentationFunctionIntegration setExpectedResponseEntityClass(Class<?> expectedResponseEntityClass);
	Class<?> getExpectedResponseEntityClass();
	
	TestRepresentationFunctionIntegration setExpectedResponseEntity(Object responseEntity);
	Object getExpectedResponseEntity();
	
	TestRepresentationFunctionIntegration setExpectedResponseEntityDtoMessages(Collection<ExpectedMessageDto> messages);
	Collection<ExpectedMessageDto> getExpectedResponseEntityDtoMessages();
	TestRepresentationFunctionIntegration addExpectedResponseEntityDtoMessages(Collection<ExpectedMessageDto> messages);
	TestRepresentationFunctionIntegration addExpectedResponseEntityDtoMessages(ExpectedMessageDto...messages);
	
}
