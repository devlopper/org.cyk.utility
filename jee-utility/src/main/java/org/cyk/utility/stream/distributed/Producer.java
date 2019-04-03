package org.cyk.utility.stream.distributed;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndVoidAsOutput;

public interface Producer extends FunctionWithPropertiesAsInputAndVoidAsOutput {

	String getTopic();
	Producer setTopic(String topic);
	
	Object getMessage();
	Producer setMessage(Object message);
	
}
