package org.cyk.utility.server.representation.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.server.representation.MessageDto;
import org.cyk.utility.server.representation.MessageDtoCollection;
import org.cyk.utility.server.representation.Representation;
import org.cyk.utility.server.representation.ResponseEntityDto;
import org.cyk.utility.test.AbstractTestSystemFunctionIntegrationImpl;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractTestRepresentationFunctionIntegrationImpl extends AbstractTestSystemFunctionIntegrationImpl implements TestRepresentationFunctionIntegration {
	private static final long serialVersionUID = 1L;

	private URI uri;
	private Class<?> expectedResponseEntityClass;
	private Object responseStatusCode,responseEntity;
	private Boolean responseEntityIsNull;
	private Collection<ExpectedMessageDto> expectedResponseEntityDtoMessages;
	
	protected Response __response__;
	protected Object __expectedResponseStatusCode__;
	
	@Override
	protected void __perform__(Object object) throws Exception {
		__listenPerformBefore__(object);
		____perform____(object);
		__listenPerformAfter__(object);
	}
	
	protected abstract void ____perform____(Object object) throws Exception;
	protected void __listenPerformBefore__(Object object) throws Exception {}
	
	protected void __listenPerformAfter__(Object object) throws Exception {
		assertionHelper.assertNotNull("Response is null", __response__);
		Object expectedResponseStatusCode = getExpectedResponseStatusCode();
		if(expectedResponseStatusCode == null)
			expectedResponseStatusCode = __expectedResponseStatusCode__;
		
		if(expectedResponseStatusCode!=null)
			assertThat(__response__.getStatus()).isEqualTo(expectedResponseStatusCode);
		
		Boolean expectedResponseEntityIsNull = getExpectedResponseEntityIsNull();
		if(expectedResponseEntityIsNull!=null)
			assertThat(__response__.getEntity()).isNull();
		
		Class<?> expectedResponseEntityClass = getExpectedResponseEntityClass();
		if(expectedResponseEntityClass!=null)
			assertThat(__response__.getEntity()).isExactlyInstanceOf(expectedResponseEntityClass);
		
		Object expectedResponseEntity = getExpectedResponseEntity();
		if(expectedResponseEntity!=null)
			assertThat(__response__.getEntity()).isEqualTo(expectedResponseEntity);
		
		Collection<ExpectedMessageDto> expectedMessageDtos = getExpectedResponseEntityDtoMessages();
		if(__injectCollectionHelper__().isNotEmpty(expectedMessageDtos)) {
			Collection<MessageDto> messages = new ArrayList<>();
			if(__response__.getEntity() instanceof MessageDto) {
				messages.add((MessageDto) __response__.getEntity());
			}else if(__response__.getEntity() instanceof MessageDtoCollection) {
				messages = __injectCollectionHelper__().add(ArrayList.class, messages, Boolean.TRUE, ((MessageDtoCollection)__response__.getEntity()).getCollection());
			}else if(__response__.getEntity() instanceof ResponseEntityDto) {
				messages = __injectCollectionHelper__().add(ArrayList.class, messages, Boolean.TRUE, ((ResponseEntityDto)__response__.getEntity()).getMessages());
			}
			if(expectedMessageDtos.size() == 1) {
				MessageDto message = messages.iterator().next();
				ExpectedMessageDto expectedMessageDto = expectedMessageDtos.iterator().next();
				expectedMessageDto.getCodeExpectedString().evaluate(message.getCode());
				expectedMessageDto.getHeadExpectedString().evaluate(message.getHead());
				expectedMessageDto.getBodyExpectedString().evaluate(message.getBody());
			}
		}
	}
	
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
	
	public TestRepresentationFunctionIntegration setExpectedResponseEntityIsNull(Boolean responseEntityIsNull) {
		this.responseEntityIsNull = responseEntityIsNull;
		return this;
	}
	
	public Boolean getExpectedResponseEntityIsNull() {
		return responseEntityIsNull;
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
	public TestRepresentationFunctionIntegration setObjectClass(Class<?> aClass) {
		return (TestRepresentationFunctionIntegration) super.setObjectClass(aClass);
	}
	
	@Override
	public TestRepresentationFunctionIntegration setIdentifierValueUsageType(ValueUsageType valueUsageType) {
		return (TestRepresentationFunctionIntegration) super.setIdentifierValueUsageType(valueUsageType);
	}
	
	@Override
	public TestRepresentationFunctionIntegration addGarbagesArray(Object... objects) {
		return (TestRepresentationFunctionIntegration) super.addGarbagesArray(objects);
	}
	
	@Override
	public TestRepresentationFunctionIntegration addUnexistingObjectIdentifiers(Object... unexistingObjectIdentifiers) {
		return (TestRepresentationFunctionIntegration) super.addUnexistingObjectIdentifiers(unexistingObjectIdentifiers);
	}
	
	@Override
	public TestRepresentationFunctionIntegration addNotGarbagableArray(Object... objects) {
		return (TestRepresentationFunctionIntegration) super.addNotGarbagableArray(objects);
	}
	
	@Override
	public TestRepresentationFunctionIntegration addObjectBusinessIdentifiersToBeDeletedOnCleanArray(Class<?> aClass, Object... identifiers) {
		return (TestRepresentationFunctionIntegration) super.addObjectBusinessIdentifiersToBeDeletedOnCleanArray(aClass, identifiers);
	}
	
	@Override
	protected void __createOne__(Object object) throws Exception {
		__inject__(Representation.class).create(object);
	}
	
	@Override
	protected Object __readOneByBusinessIdentifier__(Class<?> aClass, Object identifier) {
		return __inject__(Representation.class).getOne(aClass, identifier, ValueUsageType.BUSINESS);
	}
	
	@Override
	public TestRepresentationFunctionIntegration setIsCatchThrowable(Boolean value) {
		return (TestRepresentationFunctionIntegration) super.setIsCatchThrowable(value);
	}
	
	@Override
	protected void __deleteOne__(Object object) {
		__inject__(Representation.class).delete(object);
	}

}
