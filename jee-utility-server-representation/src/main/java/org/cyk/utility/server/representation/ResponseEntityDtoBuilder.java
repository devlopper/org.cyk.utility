package org.cyk.utility.server.representation;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.__kernel__.system.action.SystemAction;

public interface ResponseEntityDtoBuilder extends FunctionWithPropertiesAsInput<ResponseEntityDto> {

	ResponseEntityDto getResponseEntityDto();
	ResponseEntityDtoBuilder setResponseEntityDto(ResponseEntityDto responseEntityDto);
	
	SystemAction getSystemAction();
	ResponseEntityDtoBuilder setSystemAction(SystemAction systemAction);
	
	Throwable getThrowable();
	ResponseEntityDtoBuilder setThrowable(Throwable throwable);
	
}
