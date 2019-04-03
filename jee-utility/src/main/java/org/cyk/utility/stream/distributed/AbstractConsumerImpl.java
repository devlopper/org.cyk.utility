package org.cyk.utility.stream.distributed;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;

public abstract class AbstractConsumerImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements Consumer,Serializable {
	private static final long serialVersionUID = 1L;
	
	private String topic;
	private Class<? extends ConsumerMessageProcessor> messageProcessorClass;
	
	@Override
	protected void ____execute____() throws Exception {
		String topic = __injectValueHelper__().returnOrThrowIfBlank("topic",getTopic());
		Class<? extends ConsumerMessageProcessor> messageProcessorClass = __injectValueHelper__().returnOrThrowIfBlank("consumer message processor",getMessageProcessorClass());
		__execute__(topic,messageProcessorClass);
	}
	
	protected abstract void __execute__(String topic,Class<? extends ConsumerMessageProcessor> messageProcessorClass);
	
	@Override
	public String getTopic() {
		return topic;
	}
	
	@Override
	public Consumer setTopic(String topic) {
		this.topic = topic;
		return this;
	}
	
	@Override
	public Class<? extends ConsumerMessageProcessor> getMessageProcessorClass() {
		return messageProcessorClass;
	}
	
	@Override
	public Consumer setMessageProcessorClass(Class<? extends ConsumerMessageProcessor> messageProcessorClass) {
		this.messageProcessorClass = messageProcessorClass;
		return this;
	}

}
