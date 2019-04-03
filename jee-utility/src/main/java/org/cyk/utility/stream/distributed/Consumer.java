package org.cyk.utility.stream.distributed;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndVoidAsOutput;

public interface Consumer extends FunctionWithPropertiesAsInputAndVoidAsOutput {

	String getTopic();
	Consumer setTopic(String topic);
	
	Class<? extends ConsumerMessageProcessor> getMessageProcessorClass();
	Consumer setMessageProcessorClass(Class<? extends ConsumerMessageProcessor> messageProcessorClass);
	
}
