package org.cyk.utility.stream.distributed;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndVoidAsOutput;

public interface ConsumerMessageProcessor extends FunctionWithPropertiesAsInputAndVoidAsOutput {

	Message getMessage();
	ConsumerMessageProcessor setMessage(Message message);
	
}
