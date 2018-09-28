package org.cyk.utility.server.representation.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.server.representation.MessageDto;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.RepresentationLayer;

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
		
		Object expectedResponseEntity = getExpectedResponseEntity();
		if(expectedResponseEntity!=null)
			assertThat(response.getEntity()).isEqualTo(expectedResponseEntity);
		
		Collection<ExpectedMessageDto> expectedMessageDtos = getExpectedResponseEntityMessages();
		if(__injectCollectionHelper__().isNotEmpty(expectedMessageDtos)) {
			if(expectedMessageDtos.size() == 1) {
				assertThat(response.getEntity()).isInstanceOf(MessageDto.class);
				MessageDto message = (MessageDto) response.getEntity();
				ExpectedMessageDto expectedMessageDto = expectedMessageDtos.iterator().next();
				if(message.getCode()!=null)
					assertThat(message.getCode()).isEqualTo(expectedMessageDto.getCode());
				if(message.getValue()!=null) {
					if(expectedMessageDto.getValue()!=null)
						assertThat(message.getValue()).isEqualTo(expectedMessageDto.getValue());
					if(expectedMessageDto.getValueContains()!=null)
						assertThat(message.getValue()).contains(expectedMessageDto.getValueContains());
				}
			}
		}
	}
	
}
