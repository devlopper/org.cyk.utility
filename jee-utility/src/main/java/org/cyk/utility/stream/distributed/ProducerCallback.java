package org.cyk.utility.stream.distributed;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndVoidAsOutput;

public interface ProducerCallback extends FunctionWithPropertiesAsInputAndVoidAsOutput {

	String getTopic();
	ProducerCallback setTopic(String topic);
	
	Message getMessage();
	ProducerCallback setMessage(Message message);
	
	Throwable getThrowable();
	ProducerCallback setThrowable(Throwable throwable);
}
