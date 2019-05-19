package org.cyk.utility.stream.distributed.kafka.function;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.FunctionExecutionMessage;
import org.cyk.utility.stream.distributed.kafka.AbstractMessageSerializer;

public class MessageSerializer extends AbstractMessageSerializer<FunctionExecutionMessage> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String[] __getFieldNamesStrings__() {
		return new String[] {FunctionExecutionMessage.PROPERTY_FUNCTION,FunctionExecutionMessage.PROPERTY_INPUTS,FunctionExecutionMessage.PROPERTY_OUTPUTS};
	}
	
}