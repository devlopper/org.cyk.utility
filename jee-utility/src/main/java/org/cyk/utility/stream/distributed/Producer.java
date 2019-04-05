package org.cyk.utility.stream.distributed;

public interface Producer extends ProducerConsumer {

	Message getMessage();
	Message getMessage(Boolean injectIfNull);
	Producer setMessage(Message message);
	Producer setMessage(Object key,Object value);
	Producer setMessageValue(Object value);
	
	@Override Producer addTopics(String... topics);
}
