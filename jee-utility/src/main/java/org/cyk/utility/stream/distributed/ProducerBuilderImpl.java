package org.cyk.utility.stream.distributed;

import java.io.Serializable;

import org.cyk.utility.__kernel__.constant.ConstantTopic;
import org.cyk.utility.stream.distributed.kafka.network.MessageSerializer;
import org.cyk.utility.stream.distributed.kafka.network.ProducerCallbackImpl;

public class ProducerBuilderImpl extends AbstractProducerConsumerBuilderImpl<Producer> implements ProducerBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Producer __execute__(Topic topic) throws Exception {
		Producer producer = __inject__(Producer.class);
		if(Topic.MAIL.equals(topic)) {
			producer.addTopics(ConstantTopic.MAIL);
			producer.setCallbackClass(ProducerCallbackImpl.class);
			producer.setValueSerialisationClass(MessageSerializer.class);	
		}
		return producer;
	}
	
	@Override
	public ProducerBuilder setTopic(Topic topic) {
		return (ProducerBuilder) super.setTopic(topic);
	}
}
