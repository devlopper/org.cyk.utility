package org.cyk.utility.server.representation.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.server.representation.MessageDto;
import org.cyk.utility.server.representation.MessageDtoCollection;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.RepresentationLayer;
import org.cyk.utility.server.representation.ResponseEntityDto;

public abstract class AbstractTestRepresentationCreateIntegrationImpl extends AbstractTestRepresentationTransactionIntegrationImpl implements TestRepresentationCreateIntegration {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setName("create representation entity");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void __perform__(Object object) throws Exception {
		@SuppressWarnings("rawtypes")
		RepresentationEntity representation = __inject__(RepresentationLayer.class).injectInterfaceClassFromEntityClass(object.getClass());
		Response response = representation.createOne(object);
		
		if(Response.Status.CREATED.getStatusCode() == response.getStatus()) {
			addGarbagesArray(object);
		}
		
		Object expectedResponseStatusCode = getExpectedResponseStatusCode();
		if(expectedResponseStatusCode == null)
			expectedResponseStatusCode = Response.Status.CREATED.getStatusCode();
		if(expectedResponseStatusCode!=null)
			assertThat(response.getStatus()).isEqualTo(expectedResponseStatusCode);
		
		Class<?> expectedResponseEntityClass = getExpectedResponseEntityClass();
		if(expectedResponseEntityClass!=null)
			assertThat(response.getEntity()).isExactlyInstanceOf(expectedResponseEntityClass);
		
		Object expectedResponseEntity = getExpectedResponseEntity();
		if(expectedResponseEntity!=null)
			assertThat(response.getEntity()).isEqualTo(expectedResponseEntity);
		
		Collection<ExpectedMessageDto> expectedMessageDtos = getExpectedResponseEntityDtoMessages();
		if(__injectCollectionHelper__().isNotEmpty(expectedMessageDtos)) {
			Collection<MessageDto> messages = new ArrayList<>();
			if(response.getEntity() instanceof MessageDto) {
				messages.add((MessageDto) response.getEntity());
			}else if(response.getEntity() instanceof MessageDtoCollection) {
				messages = __injectCollectionHelper__().add(ArrayList.class, messages, Boolean.TRUE, ((MessageDtoCollection)response.getEntity()).getCollection());
			}else if(response.getEntity() instanceof ResponseEntityDto) {
				messages = __injectCollectionHelper__().add(ArrayList.class, messages, Boolean.TRUE, ((ResponseEntityDto)response.getEntity()).getMessages());
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
	
}
