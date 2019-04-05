package org.cyk.utility.stream.distributed;

import java.io.Serializable;

import org.cyk.utility.string.Strings;

public abstract class AbstractProducerImpl extends AbstractProducerConsumerImpl implements Producer,Serializable {
	private static final long serialVersionUID = 1L;
	
	private Message message;
	
	@Override
	protected void __execute__(Strings topics) throws Exception {
		Message message = __injectValueHelper__().returnOrThrowIfBlank("produced message", getMessage());
		__prepare__(topics);
		for(String index : topics.get())
			__send__(index,message);
		__close__();
	}
	
	protected abstract void __send__(String topic,Message message);
	
	@Override
	public Message getMessage() {
		return message;
	}
	
	@Override
	public Message getMessage(Boolean injectIfNull) {
		return (Message) __getInjectIfNull__(FIELD_MESSAGE, injectIfNull);
	}

	@Override
	public Producer setMessage(Message message) {
		this.message = message;
		return this;
	}
	
	@Override
	public Producer setMessage(Object key, Object value) {
		getMessage(Boolean.TRUE).setKey(key);
		getMessage(Boolean.TRUE).setValue(value);
		return this;
	}
	
	@Override
	public Producer setMessageValue(Object value) {
		getMessage(Boolean.TRUE).setValue(value);
		return this;
	}
	
	@Override
	public Producer addTopics(String... topics) {
		return (Producer) super.addTopics(topics);
	}
	
	/**/
	
	private static final String FIELD_MESSAGE = "message";

}
