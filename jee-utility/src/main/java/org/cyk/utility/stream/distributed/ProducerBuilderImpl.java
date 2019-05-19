package org.cyk.utility.stream.distributed;

import java.io.Serializable;

import org.cyk.utility.stream.distributed.kafka.network.MessageSerializer;
import org.cyk.utility.stream.distributed.kafka.network.ProducerCallbackImpl;

public class ProducerBuilderImpl extends AbstractProducerConsumerBuilderImpl<Producer> implements ProducerBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Producer __execute__(Topic topic) throws Exception {
		Producer producer = __inject__(Producer.class);
		producer.addTopics(topic.getIdentifier());
		if(Topic.MAIL.equals(topic)) {
			producer.setCallbackClass(ProducerCallbackImpl.class);
			producer.setValueSerialisationClass(MessageSerializer.class);	
		}else if(Topic.FUNCTION.equals(topic)) {
			producer.setCallbackClass(org.cyk.utility.stream.distributed.kafka.function.ProducerCallbackImpl.class);
			producer.setValueSerialisationClass(org.cyk.utility.stream.distributed.kafka.function.MessageSerializer.class);	
		}
		return producer;
	}
	
	@Override
	public ProducerBuilder setTopic(Topic topic) {
		return (ProducerBuilder) super.setTopic(topic);
	}
}
