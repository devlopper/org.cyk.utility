package org.cyk.utility.stream.distributed;

import java.io.Serializable;

import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.__kernel__.value.ValueHelper;

public abstract class AbstractProducerImpl extends AbstractProducerConsumerImpl implements Producer,Serializable {
	private static final long serialVersionUID = 1L;
	
	private Message message;
	private Class<? extends ProducerCallback> callbackClass;
	
	@Override
	protected void __execute__(Strings topics) throws Exception {
		Message message = ValueHelper.returnOrThrowIfBlank("produced message", getMessage());
		Class<? extends ProducerCallback> callbackClass = getCallbackClass();
		__prepare__(topics);
		for(String index : topics.get())
			__send__(index,message,callbackClass);
		__close__();
	}
	
	protected abstract void __send__(String topic,Message message,Class<? extends ProducerCallback> callbackClass);
	
	@Override
	public Message getMessage() {
		return message;
	}
	
	@Override
	public Message getMessage(Boolean injectIfNull) {
		if(message == null && Boolean.TRUE.equals(injectIfNull))
			message = __inject__(Message.class);
		return message;
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
	
	@Override
	public Class<? extends ProducerCallback> getCallbackClass() {
		return callbackClass;
	}
	
	@Override
	public Producer setCallbackClass(Class<? extends ProducerCallback> callbackClass) {
		this.callbackClass = callbackClass;
		return this;
	}
	
	@Override
	public Producer setKeySerialisationClass(Class<?> keySerialisationClass) {
		return (Producer) super.setKeySerialisationClass(keySerialisationClass);
	}
	
	@Override
	public Producer setValueSerialisationClass(Class<?> valueSerialisationClass) {
		return (Producer) super.setValueSerialisationClass(valueSerialisationClass);
	}
	
	/**/
	
}
