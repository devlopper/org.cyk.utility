package org.cyk.utility.stream.distributed;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public abstract class AbstractProducerConsumerBuilderImpl<OUTPUT extends ProducerConsumer> extends AbstractFunctionWithPropertiesAsInputImpl<OUTPUT> implements ProducerConsumerBuilder<OUTPUT>,Serializable {
	private static final long serialVersionUID = 1L;

	private Topic topic;
	
	@Override
	protected OUTPUT __execute__() throws Exception {
		Topic topic = __injectValueHelper__().returnOrThrowIfBlank("topic", getTopic());
		return __execute__(topic);
	}
	
	protected abstract OUTPUT __execute__(Topic topic) throws Exception;
	
	@Override
	public Topic getTopic() {
		return topic;
	}

	@Override
	public ProducerConsumerBuilder<OUTPUT> setTopic(Topic topic) {
		this.topic = topic;
		return this;
	}

}
