package org.cyk.utility.log;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface LogEventEntityBuilder extends FunctionWithPropertiesAsInput<LogEventEntity> {

	LogEventEntityBuilder setEvent(Object event);
	Object getEvent();
	
	LogEventEntityBuilder execute(Object event);
}
