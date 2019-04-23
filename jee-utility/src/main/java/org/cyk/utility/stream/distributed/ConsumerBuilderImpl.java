package org.cyk.utility.stream.distributed;

import java.io.Serializable;

import org.cyk.utility.stream.distributed.kafka.network.ConsumerMessageProcessorImpl;
import org.cyk.utility.stream.distributed.kafka.network.MessageDeserializer;

public class ConsumerBuilderImpl extends AbstractProducerConsumerBuilderImpl<Consumer> implements ConsumerBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Consumer __execute__(Topic topic) throws Exception {
		Consumer consumer = __inject__(Consumer.class);
		consumer.addTopics(topic.getIdentifier());
		if(Topic.MAIL.equals(topic)) {
			consumer.setMessageProcessorClass(ConsumerMessageProcessorImpl.class);
			consumer.setValueSerialisationClass(MessageDeserializer.class);	
		}
		return consumer;
	}
	
	@Override
	public ConsumerBuilder setTopic(Topic topic) {
		return (ConsumerBuilder) super.setTopic(topic);
	}
}
