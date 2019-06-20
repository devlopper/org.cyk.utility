package org.cyk.utility.server.representation;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.system.action.SystemAction;

@Dependent
public class ResponseEntityDtoBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<ResponseEntityDto> implements ResponseEntityDtoBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private ResponseEntityDto responseEntityDto;
	private Throwable throwable;
	private SystemAction systemAction;
	
	@Override
	protected ResponseEntityDto __execute__() throws Exception {
		ResponseEntityDto responseEntityDto = getResponseEntityDto();
		if(responseEntityDto == null)
			responseEntityDto = new ResponseEntityDto();
		SystemAction systemAction = getSystemAction();
		Throwable throwable = getThrowable();
		if(throwable == null) {
			responseEntityDto.setStatusUsingEnumeration(ResponseEntityDto.Status.FAILURE);
			Throwable cause = __injectThrowableHelper__().getFirstCause(throwable);	
			//TODO cause can be null , find the cause which is not null otherwise use throwable itself
			//throwable.printStackTrace();
			String persistenceEntityClassAsString = systemAction.getEntityClass().getName();
			responseEntityDto.addMessage(new MessageDto().setHead("Une erreur est survenue lors de "+systemAction.getIdentifier()+" de "+persistenceEntityClassAsString+". "+cause.getMessage()));	
		}
		return responseEntityDto;
	}
	
	@Override
	public ResponseEntityDto getResponseEntityDto() {
		return responseEntityDto;
	}
	
	@Override
	public ResponseEntityDtoBuilder setResponseEntityDto(ResponseEntityDto responseEntityDto) {
		this.responseEntityDto = responseEntityDto;
		return this;
	}
	
	public ResponseEntityDtoBuilder setThrowable(Throwable throwable) {
		this.throwable = throwable;
		return this;
	}
	
	@Override
	public Throwable getThrowable() {
		return throwable;
	}
	
	@Override
	public SystemAction getSystemAction() {
		return systemAction;
	}
	
	@Override
	public ResponseEntityDtoBuilder setSystemAction(SystemAction systemAction) {
		this.systemAction = systemAction;
		return this;
	}
	
}
