package org.cyk.utility.stream.distributed;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface ProducerConsumerBuilder<OUTPUT> extends FunctionWithPropertiesAsInput<OUTPUT> {

	Topic getTopic();
	ProducerConsumerBuilder<OUTPUT> setTopic(Topic topic);
	
	/**/
	
	
}
