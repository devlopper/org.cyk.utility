package org.cyk.utility.server.representation.test;

import java.net.URI;
import java.util.Collection;

import org.cyk.utility.test.TestSystemFunctionIntegration;
import org.cyk.utility.value.ValueUsageType;

public interface TestRepresentationFunctionIntegration extends TestSystemFunctionIntegration {

	@Override TestRepresentationFunctionIntegration addObjects(Object... objects);
	@Override TestRepresentationFunctionIntegration addObjectIdentifiers(Object... objectIdentifiers);
	@Override TestRepresentationFunctionIntegration addUnexistingObjectIdentifiers(Object... unexistingObjectIdentifiers);
	@Override TestRepresentationFunctionIntegration addObjectsToBeCreatedArray(Object... objects);
	@Override TestRepresentationFunctionIntegration setObjectClass(Class<?> objectClass);
	@Override TestRepresentationFunctionIntegration setIdentifierValueUsageType(ValueUsageType valueUsageType);
	@Override TestRepresentationFunctionIntegration addGarbagesArray(Object... objects);
	@Override TestRepresentationFunctionIntegration addNotGarbagableArray(Object... objects);
	@Override TestRepresentationFunctionIntegration addObjectBusinessIdentifiersToBeDeletedOnCleanArray(Class<?> aClass, Object... identifiers);
	
	TestRepresentationFunctionIntegration setUniformResourceIdentifier(URI uri);
	TestRepresentationFunctionIntegration setUniformResourceIdentifier(String uri);
	URI getUniformResourceIdentifier();
	
	TestRepresentationFunctionIntegration setExpectedResponseStatusCode(Object responseStatusCode);
	Object getExpectedResponseStatusCode();
	
	TestRepresentationFunctionIntegration setExpectedResponseEntityClass(Class<?> expectedResponseEntityClass);
	Class<?> getExpectedResponseEntityClass();
	
	TestRepresentationFunctionIntegration setExpectedResponseEntity(Object responseEntity);
	Object getExpectedResponseEntity();
	
	TestRepresentationFunctionIntegration setExpectedResponseEntityIsNull(Boolean responseEntityIsNull);
	Boolean getExpectedResponseEntityIsNull();
	
	TestRepresentationFunctionIntegration setExpectedResponseEntityDtoMessages(Collection<ExpectedMessageDto> messages);
	Collection<ExpectedMessageDto> getExpectedResponseEntityDtoMessages();
	TestRepresentationFunctionIntegration addExpectedResponseEntityDtoMessages(Collection<ExpectedMessageDto> messages);
	TestRepresentationFunctionIntegration addExpectedResponseEntityDtoMessages(ExpectedMessageDto...messages);
	
}
